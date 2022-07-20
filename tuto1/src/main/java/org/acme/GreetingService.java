package org.acme;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;



@ApplicationScoped
public class GreetingService {

	@Inject
	AgeAlternative age;
	
	@ComplexLog
	public String greeting(String name) {
		
        return "hello x   " + name+"*******"+age.getMaxAage();
    }
}
