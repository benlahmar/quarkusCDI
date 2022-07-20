package org.acme.models;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;

import io.quarkus.logging.Log;

import io.quarkus.arc.Priority;

@Decorator
@Priority(11)
public class UserDecorator implements IUser {

	@Inject 
	@Delegate 
	IUser ius;
	
	@Override
	public String check() {

		Log.info("decorator..............");
		
		
		Log.info("[Decorator] User check  ");
        return "****"+ ius.check();
		
	}

}
