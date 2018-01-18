package com.thbrandenburg.camunda.meister.docmanagement.genehmigung;

import org.camunda.bpm.engine.delegate.DelegateExecution;


import org.camunda.bpm.engine.delegate.JavaDelegate;


/**
 * service implementation
 * class as a BPMN 2.0 Service Task delegate.
 */
public class RechnungAnVorgesetztenA implements JavaDelegate {
	   
  public void execute(DelegateExecution execution) throws Exception {
    
    // Email an Vorgesetzten A senden
	  
	// java email https://javaee.github.io/javamail/
	// using a gmail dummy address 
	// https://stackoverflow.com/questions/31392100/eclipse-java-send-email-from-gmail-smtp-programatically
	  
	  
  }

}
