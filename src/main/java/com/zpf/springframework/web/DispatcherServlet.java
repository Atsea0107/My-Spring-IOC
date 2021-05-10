package com.zpf.springframework.web;

import com.zpf.springframework.annotation.Controller;
import com.zpf.springframework.annotation.RequestMapping;
import com.zpf.springframework.context.ClassPathXmlApplicationContext;
import com.zpf.springframework.entity.BeanDefinition;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

/**
 * @author zpf
 * @createTime 2021-05-10 11:29
 * DispatcherServlet 中央调度器
 */
public class DispatcherServlet extends HttpServlet {
    private Properties properties = new Properties();

    private List<String> classNames = new ArrayList<>();
    // url 与方法的映射
    private Map<String, Method> handlerMapping = new HashMap<>();

    private HashSet<Class> classes = new HashSet<>();
    // url 与类的映射
    private Map<String, Object> controllerMap = new HashMap<>();

    private ClassPathXmlApplicationContext xmlApplicationContext;

    /**
     * Servlet 实例化后，Servlet容器会调用init()，来初始化该对象
     * 让Sevlet对象在处理客户请求前完成一些初始化工作
     * @param config : web.xml中的配置信息
     */
    @Override
    public void init(ServletConfig config) {
        try {
            xmlApplicationContext = new ClassPathXmlApplicationContext("application-annotation.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 加载web.xml 配置文件 到 properties 中
        doLoadConfig(config.getInitParameter("contextConfigLocation"));
        // 扫描 application.properties 中指定的包
        doScanner(properties.getProperty("scanPackage"));
        doInstance();
        initHandlerMapping();
    }

    private void initHandlerMapping() {
        if (classes.isEmpty()) return;
        for(Class<?> clazz :classes){
            String baseUrl = "";
            if(clazz.isAnnotationPresent(RequestMapping.class)){
                baseUrl = clazz.getAnnotation(RequestMapping.class).value();
            }
            Method[] methods = clazz.getMethods();
            try {
                for(Method method : methods){
                    if(!method.isAnnotationPresent(RequestMapping.class))continue;
                    String url = method.getAnnotation(RequestMapping.class).value();
                    url = (baseUrl + "/" + url).replaceAll("/+", "/");
                    handlerMapping.put(url, method);
                        controllerMap.put(url, xmlApplicationContext.getBean(clazz));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void doInstance() {
        if(classNames.isEmpty()) return;
        for (String className : classNames){
            try {
                // 找到需要实例化的类，通过反射来实例化
                Class<?> clazz = Class.forName(className);
                if(clazz.isAnnotationPresent(Controller.class)){
                    classes.add(clazz);
                    BeanDefinition beanDefinition = new BeanDefinition();
                    beanDefinition.setSingleton(true);
                    beanDefinition.setBeanClassName(clazz.getName());
                    xmlApplicationContext.addNewBeanDefinition(clazz.getName(), beanDefinition);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            xmlApplicationContext.refreshBeanFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doScanner(String scanPackage) {
        Class<? extends DispatcherServlet> aClass = this.getClass();
        ClassLoader classLoader = aClass.getClassLoader();
        URL r1 = aClass.getResource("");
        URL r2 = aClass.getResource("/");
        // getResource 从装载的类路径下获取指定的资源
        // classpath 是指
        URL url = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.", "/"));
        File dir = new File(url.getFile());
        for(File file : dir.listFiles()){
            if(file.isDirectory()){
                doScanner(scanPackage + "." + file.getName());
            }
            else {
                String className = scanPackage + "." + file.getName().replace(".class", "");
                classNames.add(className);
            }
        }
    }

    protected void doLoadConfig(String contextConfigLocation) {
        ClassLoader cl = this.getClass().getClassLoader();
        URL resource = cl.getResource(contextConfigLocation);

        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != resourceAsStream){
                try {
                    resourceAsStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 啊。。这。。。
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            doDispatch(req, resp);
        } catch (Exception e) {
           resp.getWriter().write("500!! ---- Server Exception");
        }
    }

    public void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (handlerMapping.isEmpty()) return;
        // uri 获取request的url
        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();
        uri = uri.replace(contextPath, "").replaceAll("/+", "/");
        if(!handlerMapping.containsKey(uri)){
            resp.getWriter().write("404 ---- NOT FOUND!");
            return;
        }
        // reflect 获取 method的信息
        Method method = handlerMapping.get(uri);
        // 方法形参类型
        Class<?>[] parameterTypes = method.getParameterTypes();
        Map<String, String[]> parameterMap = req.getParameterMap();
        Object[] paramValues = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            String requestParam = parameterTypes[i].getSimpleName();
            if(requestParam.equals("HttpServletRequest")){
                paramValues[i] = req;
                continue;
            }
            if(requestParam.equals("HttpServletResponse")){
                paramValues[i] = resp;
                continue;
            }
            if (requestParam.equals("String")) {
                for (Map.Entry<String, String[]> param : parameterMap.entrySet()){
                    String value = Arrays.toString(param.getValue()).replaceAll("\\[|\\]", "").replaceAll(",\\s","");
                    paramValues[i] = value;
                }
            }
        }
        method.invoke(controllerMap.get(uri), paramValues);
    }

}
