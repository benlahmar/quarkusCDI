package org.acme;


import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@ComplexLog
@Priority(Interceptor.Priority.APPLICATION+1) 
@Interceptor 
public class ComplexLogInterceptor {

	@AroundInvoke
	   Object logInvocation(InvocationContext context) {
	       System.out.println(" method:" + context.getMethod().getName());

	       try {
	           Object ret = context.proceed();
	           return ret;
	       } catch (Exception e) {
	           e.printStackTrace();
	           return null;
	       }

	   }
}
