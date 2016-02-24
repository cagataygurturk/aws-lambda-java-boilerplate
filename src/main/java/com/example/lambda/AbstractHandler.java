package com.example.lambda;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.ParameterizedType;

/**
 * Every lambda handler class should extend this abstract base class
 * <p>
 * T type parameter is used to determine the Spring @Configuration class, thus T should be a class that declares @Configuration annotation. Otherwise, an exception is thrown.
 *
 * @param <T>
 */
@SuppressWarnings("unused")
public abstract class AbstractHandler<T> {

    /**
     * Spring IOC application context
     */
    private ApplicationContext applicationContext;

    /**
     * Every handler can override this String variable to provide an example event JSON for local running
     */
    protected String exampleEvent = "{}";

    public AbstractHandler() {
        /**
         * Gets config class to create an Application context
         */
        Class typeParameterClass = ((Class) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0]);

        /**
         * Check if T has @Configuration annotation
         */
        if (!typeParameterClass.isAnnotationPresent(Configuration.class)) {
            throw new RuntimeException(typeParameterClass + " is not a @Configuration class");
        }

        /**
         * Create Spring application context
         */
        applicationContext = new AnnotationConfigApplicationContext(typeParameterClass);
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public String getExampleEvent() {
        return exampleEvent;
    }
}
