package org.acme;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.annotation.Priority;

@Logged 
@Priority(Interceptor.Priority.APPLICATION+1) 
@Interceptor 
public class LoggingInterceptor {

   @AroundInvoke
   Object logInvocation(InvocationContext context) {
       System.out.println("Entering method:" + context.getMethod().getName());

       try {
           Object ret = context.proceed();
           return ret;
       } catch (Exception e) {
           e.printStackTrace();
           return null;
       }

   }

}