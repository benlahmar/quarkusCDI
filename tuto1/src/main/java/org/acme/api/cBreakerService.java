package org.acme.api;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.faulttolerance.CircuitBreaker;

@ApplicationScoped
public class cBreakerService {

	private AtomicLong counter = new AtomicLong(0);
	
	 @CircuitBreaker(requestVolumeThreshold = 4)
	    public Integer getAvailability(long id) {
	        maybeFail();
	        return new Random().nextInt(30);
	    }

	    private void maybeFail() {
	        // introduce some artificial failures
	        final Long invocationNumber = counter.getAndIncrement();
	        if (invocationNumber % 4 > 1) { // alternate 2 successful and 2 failing invocations
	            throw new RuntimeException("Service failed.");
	        }
	    }
}
