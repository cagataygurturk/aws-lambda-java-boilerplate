package com.example.lambda.local;

import com.amazonaws.services.lambda.runtime.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.lambda.AbstractHandler;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class LocalRunner<I, O> {

    public static <I, O> void main(final String[] args) throws
            Exception {

        Context context = new Context() {
            public String getAwsRequestId() {
                return null;
            }

            public String getLogGroupName() {
                return null;
            }

            public String getLogStreamName() {
                return null;
            }

            public String getFunctionName() {
                return null;
            }

            public String getFunctionVersion() {
                return null;
            }

            public String getInvokedFunctionArn() {
                return null;
            }

            public CognitoIdentity getIdentity() {
                return null;
            }

            public ClientContext getClientContext() {
                return null;
            }

            public int getRemainingTimeInMillis() {
                return 0;
            }

            public int getMemoryLimitInMB() {
                return 0;
            }

            public LambdaLogger getLogger() {
                return null;
            }
        };


        if (args.length != 1) {
            throw new RuntimeException("You should give handler class name as an argument");
        }

        String handlerClassName = args[0];

        Object object = null;
        try {
            Class<?> clazz = Class.forName(handlerClassName);
            Constructor<?> ctor = clazz.getConstructor();
            object = ctor.newInstance();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(handlerClassName + " not found in classpath");
        }

        if (!(object instanceof RequestHandler)) {
            throw new RuntimeException("Request handler class does not implement " + RequestHandler.class + " interface");
        }

        @SuppressWarnings("unchecked")
        RequestHandler<I, O> requestHandler = (RequestHandler<I, O>) object;
        I requestObject = getRequestObject(requestHandler);

        try {
            O output = requestHandler.handleRequest(requestObject, context);
            System.out.println("SUCCESS: " + (new ObjectMapper().writeValueAsString(output)));
        } catch (RuntimeException e) {
            System.out.println("FAIL:");
            e.printStackTrace();
            System.exit(1);
        }

    }

    private static <I> I getRequestObject(RequestHandler handler) throws IOException {

        Type requestClass = null;
        Type responseClass = null;

        for (Type genericInterface : handler.getClass().getGenericInterfaces()) {
            if (genericInterface instanceof ParameterizedType) {
                Type[] genericTypes = ((ParameterizedType) genericInterface).getActualTypeArguments();
                requestClass = genericTypes[0];
                responseClass = genericTypes[1];
            }
        }

        if (null == requestClass) {
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(((AbstractHandler) handler).getExampleEvent(), mapper.getTypeFactory().constructType(requestClass));
    }
}
