/*
 * @author Devansh D. SHARMA  
 * EPITA - M.Sc Fundamental SP 2018 - Data Science & Analytics 
 */
package fr.epita.iam.exceptions;

import fr.epita.iam.datamodell.Identity;

public class IdDelExceptions extends Exception{
	
	private static final long serialVersionUID = -2469569228619948325L;
	
	final transient Identity faultyId;

	
	public IdDelExceptions(Identity identity, Exception originalCause) {
		faultyId = identity;
		initCause(originalCause);

	}
	@Override
	public String getMessage() {
		return "Could not delete the Id, Please try again... " + faultyId.toString();
		//Message output to the console
	}

}
