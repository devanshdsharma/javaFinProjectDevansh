/*
 * @author Devansh D. SHARMA  
 * EPITA - M.Sc Fundamental SP 2018 - Data Science & Analytics
 */


package fr.epita.iam.exceptions;

import fr.epita.iam.datamodell.Identity;

public class IdUpdateExceptions extends Exception{
	
	private static final long serialVersionUID = -4747149193924947777L;
	
	final transient Identity faultyId;

	
	public IdUpdateExceptions(Identity identity, Exception originalCause) {
		faultyId = identity;
		initCause(originalCause);

	}
	@Override
	public String getMessage() {
		return "Could not update system Id, Try again..." + faultyId.toString();
	//Message output to the console
	
	}

}
