/*
 * @author Devansh D. SHARMA  
 * EPITA - M.Sc Fundamental SP 2018 - Data Science & Analytics
 */
package fr.epita.iam.exceptions;

public class AuthExceptions extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	final String username;
	
	public AuthExceptions(String uname,Exception originalCause) {
		username = uname;
		initCause(originalCause);

	}
	@Override
	public String getMessage() {
		return "Could not authenticate username, Please try again... " + username ;
		// Message output to the console
	}

}
