/*
 * @author Devansh D. SHARMA  
 * EPITA - M.Sc Fundamental SP 2018 - Data Science & Analytics
 * 
 */
package fr.epita.iam.exceptions;

import fr.epita.iam.datamodell.Identity;

public class IdCreateExceptions extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1460630578543245833L;
	
	final transient Identity faultyId;

	
	public IdCreateExceptions(Identity identity, Exception originalCause) {
		faultyId = identity;
		initCause(originalCause);

	}
	@Override
	public String getMessage() {
		return "Could not create Id, Please try again... " + faultyId.toString(); 
		//Message output to the console
		
	}

}
