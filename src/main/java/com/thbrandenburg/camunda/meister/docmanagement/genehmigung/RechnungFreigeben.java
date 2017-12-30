package com.thbrandenburg.camunda.meister.docmanagement.genehmigung;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

/**
 * service implementation
 * class as a BPMN 2.0 Service Task delegate.
 */
public class RechnungFreigeben implements JavaDelegate {
	   
  public void execute(DelegateExecution execution) throws Exception {
    
    // Web service Aufruf um im Rechnungswesen die Rechnung als freigegeben zu markieren und Folgeprozesse zu starten
	  
	  
  }

}
