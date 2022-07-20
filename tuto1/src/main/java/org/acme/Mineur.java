package org.acme;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;

import io.quarkus.arc.Priority;

@Alternative
@Priority(1)
@ApplicationScoped
public class Mineur implements AgeAlternative {
    //private int maxAttempts = 6;

	@Override
	public int getMaxAage() {
		// TODO Auto-generated method stub
		return 17;
	}
    
}