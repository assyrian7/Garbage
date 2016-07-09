package com.peerbuds.SocialSlack.Source;

import java.lang.reflect.Method;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;


public class MyAsyncUncaughtExceptionHandler implements AsyncUncaughtExceptionHandler {
    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        System.out.println("Method Name::"+method.getName());
        System.out.println("Exception occurred::"+ ex);
    }
} 