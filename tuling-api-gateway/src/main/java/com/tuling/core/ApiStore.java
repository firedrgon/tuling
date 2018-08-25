package com.tuling.core;

import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by Yuanp on 2018/8/25.
 */
public class ApiStore {
    private ApplicationContext applicationContext;
    private HashMap<String, ApiRunnable> apiMap = new HashMap<>();
    // spring ioc
    public ApiStore(ApplicationContext applicationContext) {
        Assert.notNull(applicationContext);
        this.applicationContext = applicationContext;
    }

    //加载所有的注解为ApiMapping的方法
    public void loadApiFromSpringBeans() {
        String[] definitionNames = applicationContext.getBeanDefinitionNames();
        Class<?> type;
        //反射
        for (String definitionName : definitionNames) {
            type = applicationContext.getType(definitionName);
            for (Method method : type.getDeclaredMethods()) {
                ApiMapping apiMapping = method.getAnnotation(ApiMapping.class);
                if (apiMapping != null) {
                    addApiItem(apiMapping, definitionName, method);
                }
            }
        }
    }

    /**
     *
     * 添加api <br/>
     *
     * @param apiMapping
     *            api配置
     * @param beanName
     *            beanq在spring context中的名称
     * @param method
     */
    private void addApiItem(ApiMapping apiMapping, String beanName, Method method) {
        ApiRunnable apiRun = new ApiRunnable();
        apiRun.apiName = apiMapping.value();
        apiRun.targetMethod = method;
        apiRun.targetName = beanName;
        apiRun.apiMapping=apiMapping;
        apiMap.put(apiMapping.value(), apiRun);
    }

    public ApiRunnable findApiRunnable(String apiName, String version) {
        return (ApiRunnable) apiMap.get(apiName + "_" + version);
    }

    public ApiRunnable findApiRunnable(String apiName) {
        return apiMap.get(apiName);
    }

    public List<ApiRunnable> findApiRunnables(String apiName) {
        if (apiName == null) {
            throw new IllegalArgumentException("api name must not null!");
        }
        List<ApiRunnable> list = new ArrayList<ApiRunnable>(20);
        for (ApiRunnable api : apiMap.values()) {
            if (api.apiName.equals(apiName)) {
                list.add(api);
            }
        }
        return list;
    }

    public List<ApiRunnable> getAll() {
        List<ApiRunnable> list = new ArrayList<ApiRunnable>(20);
        list.addAll(apiMap.values());
        Collections.sort(list, new Comparator<ApiRunnable>() {
            public int compare(ApiRunnable o1, ApiRunnable o2) {
                return o1.getApiName().compareTo(o2.getApiName());
            }
        });
        return list;
    }

    public boolean containsApi(String apiName, String version) {
        return apiMap.containsKey(apiName + "_" + version);
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public class ApiRunnable {
        String apiName;
        String targetName;
        Object target;
        Method targetMethod;
        ApiMapping apiMapping;

        public Object run(Object... args) throws InvocationTargetException, IllegalAccessException {
            if (target == null) {
                target = applicationContext.getBean(targetName);
            }
            return targetMethod.invoke(target, args);
        }

        public String getApiName() {
            return apiName;
        }

        public void setApiName(String apiName) {
            this.apiName = apiName;
        }

        public String getTargetName() {
            return targetName;
        }

        public void setTargetName(String targetName) {
            this.targetName = targetName;
        }

        public Object getTarget() {
            return target;
        }

        public void setTarget(Object target) {
            this.target = target;
        }

        public Method getTargetMethod() {
            return targetMethod;
        }

        public void setTargetMethod(Method targetMethod) {
            this.targetMethod = targetMethod;
        }

        public ApiMapping getApiMapping() {
            return apiMapping;
        }

        public void setApiMapping(ApiMapping apiMapping) {
            this.apiMapping = apiMapping;
        }
    }
}
