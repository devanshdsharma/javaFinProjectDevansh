/*
 * @author Devansh D. SHARMA  
 * EPITA - M.Sc Fundamental SP 2018 - Data Science & Analytics
 * 
 * Contained by the services package, Handles the actual operation of the program?????????????????????????????????????????????????????????
 * 
 */

package fr.epita.iam.services;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import fr.epita.iam.datamodell.Identity;
import fr.epita.iam.exceptions.AuthExceptions;
import fr.epita.iam.exceptions.IdCreateExceptions;
import fr.epita.iam.exceptions.IdDelExceptions;
import fr.epita.iam.exceptions.IdListofExceptions;
import fr.epita.iam.exceptions.IdUpdateExceptions;
import fr.epita.iam.services.Config;


public class IdJDBC implements IdDAO{
	
   Logger log = Logger.getLogger(IdJDBC.class);
   
   ResultSet resultSet = null;
   
   PreparedStatement preparedStatement = null;
	
   //1. Set connection String
   private final String connectionString = Config.getInstance().getProperty("db.url");   
      
   private final String loginName = Config.getInstance().getProperty("db.user");
   
   //3. Set User Password
   private final String password = Config.getInstance().getProperty("db.pswd");
			
   //AUTH QUER SELECTION
   private final String selectAuthQuery = Config.getInstance().getProperty("db.auth.query");
			
   //ID create query insertion
   private final String queryCreateinsert = Config.getInstance().getProperty("db.create.query");
	
   //ID update query
   private final String updateQuery = Config.getInstance().getProperty("db.update.query");
		
   //ID delete query
   private final String delQuery = Config.getInstance().getProperty("db.del.query");
	
   //ID list query
   private final String listQuery = Config.getInstance().getProperty("db.list.query");
			
   private static final String IDENTITY_STRING = "Identity";
   
   Connection connection = null;
   
   /**
    * Using the LoginName, Password, Driver Name, Connection String this program makes the connection to the DERBY DB									
    * 
    * @throws SQLException Exception List
    * @throws ClassNotFoundException Exception List
    * @return Returns the established connection
    * 
    */
   private Connection getConn() throws ClassNotFoundException,SQLException {
		if (this.connection == null || this.connection.isClosed()){
			try {
					Class.forName("org.apache.derby.jdbc.ClientDriver");
					} catch (ClassNotFoundException e) {
						
						log.error(e.getMessage());
					}
					this.connection = DriverManager.getConnection(connectionString,loginName,password);
				}
				return this.connection;
				
	}
	
    /**
     * This is the user credential verification process. It takes user input and compares to data in the USERS table.
     * if matches, the user is logged in. If not the user cannot log in, but can try again.
     *
     * @param loginName 				 :	 	User Name for authentication
     * @param passwd  					 : 		Password for authentication
     * @throws ClassNotFoundExceptions 	 : 		Exceptions
     * @throws SQLExceptions  			 : 		Exceptions
     * @throws AuthenticationExceptions  : 		Exceptions
     * @return        					 : 		True(Authorized_User)/False(Unauthorized_User) 
     *
     */
	public boolean authentication(String loginName, String passwd) throws AuthExceptions, SQLException, ClassNotFoundException{

		     
		try{
			
		connection = getConn();
		
		//Auth Select Query
		preparedStatement  = connection.prepareStatement(selectAuthQuery);		
		
		//The selected parameter is set to the Prepared statement query
		preparedStatement.setString(1, loginName); // User name
		preparedStatement.setString(2, passwd); // Password
		
		/*
		 *  Makes result using select query
		 */
		
		resultSet = preparedStatement.executeQuery();
				
		if (resultSet.next()) {		
			/*
			 * If the user is verified, method says True
			 */
			connection.close();
			return true;
		}
		else {
			/*
			 * If user is not unsuccessfully verified, method says False
			 */
			connection.close();
		    return false;		
			}
		
		}
		
		catch ( ClassNotFoundException | SQLException e) {
			
			//Auth exception raised here
			final AuthExceptions businessException = new AuthExceptions(loginName, e);			
			throw businessException;
		}
		
		finally {
			try {
				if (connection != null) {
					connection.close();
				}
				
				if(resultSet != null) {
					resultSet.close();
				}
				
				if(preparedStatement != null) {
					preparedStatement.close();
				}
			} catch (final SQLException e) {
				log.error(e.getMessage());
				
			}
		}
			
		}    
    
	/**
	 * Here is the create identity method , It takes input from the user about desired parameters and creates Id
	 * based on Login name, Email ID, UID.
	 * Then the new ID created is stored in the DB
     *
     * @throws IdentityCreationException  	:		 Exception List
     * @param identity 						: 		 Identity with UID, Login Name and Email ID.
     * 
     */
    public void createAnIdentity(Identity identity) throws IdCreateExceptions{
    	
    	try{
    		connection = getConn();
    		
    		//ID create query
    		
    		preparedStatement  = connection.prepareStatement(queryCreateinsert);		
    		
    		//Makes the selected parameter to the PreparedStatement query    
    		
    		preparedStatement.setString(1, identity.getUid());   //UID
    		preparedStatement.setString(2, identity.getLoginName()); //Login Name
    		preparedStatement.setString(3, identity.getEmail()); // Email 		
    		int result = preparedStatement.executeUpdate(); // Execute the query 
    		
    		if(result > 0){
    			
    	    // Query successful
    			
    		log.info(IDENTITY_STRING +identity.getUid()+" Create successful!");
    		}
    		else{
        	    // Query was not executed successfully    			
    			log.info("ID create Unsuccessful "+identity.getUid());
    		}
    		   		
    	}
    	
    	catch ( ClassNotFoundException | SQLException e) {
    		// Raise Custom Identity Creation exception 
			final IdCreateExceptions businessException = new IdCreateExceptions(identity, e);
			throw businessException;
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (final SQLException e) {
				log.error(e.getMessage());
				
			}
    }
    	
}
    
    /**
     * This code is responsible to update the existing ID, HOwever take care that it can only update if the ID exists in the
     * first place so if the ID doesn't exist. it cannot update
     * it  also users features like UID, LoginName, Email ID
     * then the new data updates and overwrites the old data in the DB.
     * 
     * @throws IdentityUpdationException  	 :        Exception List
     * @param identity						 :		  Identity using UID, Login Name, EmailID.
     * 
     */
    public void updateAnIdentity(Identity identity) throws IdUpdateExceptions {
    	
    	try{
    		connection = getConn();
    		
    		//Update query
    		
    		preparedStatement  = connection.prepareStatement(updateQuery);	
    		//Makes the selected parameter to the PreparedStatement query   		
    		
    		preparedStatement.setString(3, identity.getUid())			;  			 //UID
    		preparedStatement.setString(1, identity.getLoginName())		;			//Login Name
    		preparedStatement.setString(2, identity.getEmail())			;		   //Email
    		
    		int result = preparedStatement.executeUpdate();
    		
    		if(result > 0){
    		// ID update successful
    		log.info(IDENTITY_STRING+identity.getUid()+" Updated successfully!");
    		}
    		else{
    			//UID D.N.E!
    			log.info(IDENTITY_STRING+identity.getUid()+" ID doesn't exist!, Don't try to fool Java, JAVA IS SMART!!!");
    		}
    		   		
    	}
    	
    	catch (ClassNotFoundException | SQLException e) {
    		
    		// UPDATE exception raised here
    		
			final IdUpdateExceptions businessException = new IdUpdateExceptions(identity, e);
			throw businessException;
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (final SQLException e) {
				log.error(e.getMessage());
				
			}
    }
    	
    }
    
    /**
     * This code deletes the existing Id and removes them form the database
     * again.
     * 
     * @param identity 							 :			  Id with UID, Login Name, Email_ID.
     * @throws IdentityDeletionException 		 :			  Exceptions
     */
    public void deleteAnIdentity(Identity identity) throws IdDelExceptions {
    	
    	try{
    		connection = getConn();
    		
    		//ID del query
    		
    		preparedStatement  = connection.prepareStatement(delQuery);		
    		
    		//Makes the selected parameter to the PreparedStatement query 
    		preparedStatement.setString(1, identity.getUid());	//UID
    		int result = preparedStatement.executeUpdate();
    		
    		if(result > 0){
    	    // ID update successful
    		log.info(IDENTITY_STRING+identity.getUid()+" Delete successful!");
    		}
    		else{
    			//UID D.N.E
    			log.info(IDENTITY_STRING+identity.getUid()+" Id Does not exist!, Don't Try to fool Java, JAVA is SMART!!!");
    		}
    		   		
    	}
    	
    	catch (ClassNotFoundException | SQLException e) {
    		
    		// Del Exception raised here
    		
			final IdDelExceptions businessException = new IdDelExceptions(identity, e);
			throw businessException;
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (final SQLException e) {
				log.error(e.getMessage());
				
			}
    }
    	
    }
    
    
    public List<Identity> listAllIdentities(Identity criteria) throws IdListofExceptions {
    	
    	final List<Identity> identities = new ArrayList<>();
    	
		Connection con = null;
		try {
			con = getConn();
			preparedStatement = con.prepareStatement(listQuery);
			
			preparedStatement.setString(1, criteria.getLoginName());
			preparedStatement.setString(2, criteria.getEmail());
			preparedStatement.setString(3, criteria.getUid());
			
			resultSet = preparedStatement.executeQuery();
			boolean rsFlag = false;
				
			while (resultSet.next()) {
				rsFlag = true;
				final Identity identity = new Identity();
				identity.setLoginName(resultSet.getString(2));
				identity.setEmail(resultSet.getString(3));
				identity.setUid(resultSet.getString(1));
				identities.add(identity);
				
			}//while loooooop

			if (!rsFlag){
				//UID D.N.E
				log.info("UID Doesn't Exist, Don't Try to Fool JAVA, JAVA is SMART!!!");			
			}
		} catch (ClassNotFoundException | SQLException e) {
			
    		// ID list Exception raised here
			
			final IdListofExceptions businessException = new IdListofExceptions(criteria, e);
			throw businessException;
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				
				if(resultSet != null) {
					resultSet.close();
				}
			} catch (final SQLException e) {
				log.error(e.getMessage());
			}// Catch block
		
		}//Finally block

		return identities;
		
    }

	
}
    

