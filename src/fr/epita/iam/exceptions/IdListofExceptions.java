/*
 * @author Devansh D. SHARMA  
 * EPITA - M.Sc Fundamental SP 2018 - Data Science & Analytics
 */

package fr.epita.iam.exceptions;

import fr.epita.iam.datamodell.Identity;

public class IdListofExceptions extends Exception{
	
	private static final long serialVersionUID = -2051661636153591574L;
	
	final transient Identity faultyId;

	
	public IdListofExceptions(Identity identity, Exception originalCause) {
		faultyId = identity;
		initCause(originalCause);

	}
	@Override
	public String getMessage() {
		return "Could not list the ID" + faultyId.toString();
		//Message output to the console
	}

}
