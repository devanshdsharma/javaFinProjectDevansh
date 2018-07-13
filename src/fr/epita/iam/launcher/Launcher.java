/**
	 *
	 * @author Devansh D. SHARMA  
     * EPITA - M.Sc Fundamental SP 2018 - Data Science & Analytics
	 * 
	 * The launcher pkg is responsible for operating this project
	 * This program asks the user firstly to login using a user name and password which is of an administrator account.
	 * Next, Verification of the credentials will take place by comparing it to the Users table in the DB
	 * Then the actual functionality of the program starts where the user can select to perform any operations (CRUD)
	 * by pressing the key associated with the operation, And then user has the option to terminate the program as well with pressing a key 
	 * 
	 * 
	 * @param args   No Args to main method
	 * @throws ClassNotFoundException  List of Exceptions.
	 * @throws SQLException  List of Exceptions.
	 * @throws AuthExceptions  List of Exceptions.
	 */


package fr.epita.iam.launcher;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import fr.epita.iam.datamodell.Identity;
import fr.epita.iam.exceptions.AuthExceptions;
import fr.epita.iam.exceptions.IdCreateExceptions;
import fr.epita.iam.exceptions.IdDelExceptions;
import fr.epita.iam.exceptions.IdListofExceptions;
import fr.epita.iam.exceptions.IdUpdateExceptions;
import fr.epita.iam.services.IdJDBC;


public class Launcher {
	
	static Logger log = Logger.getLogger(Launcher.class);
    
	public static void main(String[] args) throws  ClassNotFoundException, SQLException, AuthExceptions{
		
		String loginName		;   			 //Login LoginName
        String password			;		    	//Login Password
        String uid				;       	   //User login UID
        String email			;       	  //Email-Id used to create account.
        String name				;            //Id name
        Boolean authStatus		; 			//Account validity status
        Identity identityObject ;
        
        BasicConfigurator.configure();
        
        // Taking user input for their choice of LoginName etc...
        Scanner scan = new Scanner(System.in);
        
        //For User Authentication 
        IdJDBC idDaoObject = new IdJDBC();
        log.debug("Bienvenue à IAM! \n Login or Signup using your credentials :) ");
        log.debug("Enter your Login credentials ");
        log.debug("LoginName: ");
        loginName = scan.nextLine();
        
        log.debug("Password: ");
        password = scan.nextLine();
        
        /**
         * Authenticate user method "authentication" 
         */
        authStatus = idDaoObject.authentication(loginName, password); 
        
        if(!authStatus){
        	/* If user login details are not in the "Users" table then user wont be allowed to login"*/
        	
        	log.debug("Incorrect LoginName or password, Please try again...");
           	scan.close();
           	return;
        	}	
        else{
        	log.debug( loginName + " Authentication Successful, Logging in, Please wait... ");
        }
        
        
        boolean iteration = true;
        String choice;
        
        //Options available to user.
        while(iteration){
        	log.debug("");
        	log.debug("Press the numbers corresponding to the operation that"
        			+ " you would like to execute:");
        	log.debug("");
        	log.debug("1. Create Id");
          	log.debug("2. Delete Id");
        	log.debug("3. Fetch list of all Ids Id");
        	log.debug("4. Update Id");
        	log.debug("5. Stop and Exit.");
        	log.debug("");
        	log.debug("Press key to start any operation now... (1,2,3,4,5)");
        	choice = scan.nextLine();
        	
        	//Giving choice to the user
        	switch(choice){
        	
        	//1. Id create
        	case "1" : 
        		log.debug("To Create an Identity please enter UID, Display Name and Email ID");
        		
        		log.debug("UID:");
        		uid = scan.nextLine(); //Reading uid from console i.e user provided
        		
        		log.debug("Login Name:");
        		name = scan.nextLine(); //Reading Id from console
        		
        		log.debug("Email ID:");
        		email = scan.nextLine(); //Reading email-id from console
        		
        		identityObject = new Identity(name,email,uid); // Creates the Id with the details user provided
        		
        		try{
        			// Calls Id_JDBC_DAO's Id class and creates Id
        			idDaoObject.createAnIdentity(identityObject);
        		}
        		catch(IdCreateExceptions e){ 
        			//Custom exception,
        			
        			log.error(e.getMessage());
        		}
        		
        		break;
        		
        		//2. Delete an Id
        	case "2" :
        		
        		log.debug("Enter UID to delete the Id");
        		
        		log.debug("UID:");
        		uid = scan.nextLine(); //Reads the ID UID
        		
        		identityObject = new Identity(null,null,uid); // Deletes using uid
        		
        		try{
        			// Deletes Id using calling id_del method of class Id_JDBC_DAO
        			idDaoObject.deleteAnIdentity(identityObject);
        		}
        		catch(IdDelExceptions e){ 
        			//ID deletion exception  			
        			log.error(e.getMessage());
        		}
        		
        		break;
        		
        	
        		
        	
        		
        	//3. List of all IDs
        	case "3" :
        		
        		log.debug("To  fetch containing all Ids, enter UID, Display Name, and Email-ID");
        		
        		log.debug("UID:");
        		uid = scan.nextLine(); //Reading the Id's UID
        		
        		log.debug("Login Name:");
        		name = scan.nextLine(); //Reading the login name
        		
        		log.debug("Email ID:");
        		email = scan.nextLine(); //Reads the email-id
        		
        		identityObject = new Identity(name,email,uid);
        		
        		try{
        		
        			  //Calls the  id_list of class Id_JDBC_DAO and lists the ids      			
        			
        			List<Identity> identities = idDaoObject.listAllIdentities(identityObject); 
        			
        			//gives all the ids with toString()       			
        			log.debug(identities);
        			

        		}
        		catch(IdListofExceptions e){ 
        			//ID list exception      			
        			log.error(e.getMessage());
        		}
        		
        		break;
        		
        		//4. Update Id
        	case "4" :
        		
        		log.debug("Enter UID, new User Name, Email-Id to update... ");
        		
        		log.debug("Enter UID ");
        		uid = scan.nextLine(); //Scan used to read the new uid entered by the user
        		
        		log.debug("Enter the desired new Login Name... ");
        		name = scan.nextLine(); //Reads the Identity Name entered by user
        		
        		log.debug("Enter new email-id to update... ");
        		email = scan.nextLine(); //Reads the Email-id entered by user
        		
        		
        		//Update identity object with Name email and UID

        		identityObject = new Identity(name,email,uid); 
        		
        		try{
        			
        			//  Updates the credentials by calling id_update of class Id_JDBC_DAO
        			
        			idDaoObject.updateAnIdentity(identityObject);
        		}
        		catch(IdUpdateExceptions e){ 
        		//Id creation exception			
        			log.error(e.getMessage());
        		}
        		
        		break;
            
        	//5. Stop and Exit
        	case "5" :
        		log.debug("Exiting... See you soon!");
        		iteration = false;
        		break;
        	
        	//Invalid choice not in 1,2,3,4 or 5.	
            default:
            	log.debug("Invalid response:" +choice+ ".Enter any number from the following choice(1,2,3,4,5)");
            	break;
        		
        	}
        }
        
        
        scan.close();
	}

}
