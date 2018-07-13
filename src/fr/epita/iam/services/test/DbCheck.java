package fr.epita.iam.services.test;

import java.util.List;

import org.apache.log4j.Logger;

import fr.epita.iam.datamodell.Identity;
import fr.epita.iam.exceptions.IdCreateExceptions;
import fr.epita.iam.exceptions.IdDelExceptions;
import fr.epita.iam.exceptions.IdListofExceptions;
import fr.epita.iam.exceptions.IdUpdateExceptions;
import fr.epita.iam.services.IdJDBC;

public class DbCheck {
	
	static Logger log = Logger.getLogger(DbCheck.class);

	public static void main(String[] args) throws IdCreateExceptions, IdDelExceptions, 
	        IdUpdateExceptions, IdListofExceptions{
		
		IdJDBC idJdbcObject = new IdJDBC();
		
		List<Identity> listOfAllIdentities = null;
		
		//del statement
		
		Identity identityInit = new Identity();
		
		identityInit.setLoginName(null);
		identityInit.setEmail(null);
		identityInit.setUid("666");
		
		log.debug("Id chk for UID: " + identityInit.getUid());
		idJdbcObject.deleteAnIdentity(identityInit);
		
		
		Identity identity = new Identity("Devansh", "devanshdsharma@gmail.com","666");	
		
		log.debug("Id Crt Tst ");
		idJdbcObject.createAnIdentity(identity);
		
		listOfAllIdentities = idJdbcObject.listAllIdentities(identity);
		log.debug(listOfAllIdentities);
		
		log.debug("Id Updt Tst ");
		identity.setLoginName("Carrefour");
		idJdbcObject.updateAnIdentity(identity);
		
		listOfAllIdentities = idJdbcObject.listAllIdentities(identity);
		log.debug(listOfAllIdentities);
		
		log.debug("Id Del tst ");
		identity.setLoginName(null);
		identity.setEmail(null);
		identity.setUid("666");
		idJdbcObject.deleteAnIdentity(identity);
		
		listOfAllIdentities = idJdbcObject.listAllIdentities(identity);
		log.debug(listOfAllIdentities);	
		
		
		
	}

}
