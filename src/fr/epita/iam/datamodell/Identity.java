/**
 * @author Devansh D. SHARMA  
 * EPITA - M.Sc Fundamental SP 2018 - Data Science & Analytics
 * Data Modell Package contains this class. and it creates identities using field like UID, email, name
 */

package fr.epita.iam.datamodell;


public class Identity {
	
	private String loginName;   //display name of account holder or creator.
	private String uid;         //UID of the person account holder or creator.
	private String email;       //Email-ID used account holder or creator.
	
	
	public Identity() {
	}

	/**
	 * @param uid : UID
	 * @param email : Email ID
	 * @param loginName : Login Name
	 *
	 */
	public Identity(String loginName, String email, String uid) {
		this.loginName = loginName;
		this.email = email;
		this.uid = uid;
	}
	 /*
	  * Getters & Setters for User Name
	  */
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	

	/*
	 *Getting the getters and setters for the UID 
	 */
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	
	/*
	 * Getters and Setters for Email-ID
	 */
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Id [LoginName=" + loginName + ", uid=" + uid + ", email=" + email + "]";
	}
	

}
