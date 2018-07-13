/*
 * @author Devansh D. SHARMA  
 * EPITA - M.Sc Fundamental SP 2018 - Data Science & Analytics
 */


package fr.epita.iam.services;

import java.util.List;

import fr.epita.iam.datamodell.Identity;
import fr.epita.iam.exceptions.IdCreateExceptions;
import fr.epita.iam.exceptions.IdDelExceptions;
import fr.epita.iam.exceptions.IdListofExceptions;
import fr.epita.iam.exceptions.IdUpdateExceptions;


public interface IdDAO {
	
	public void createAnIdentity(Identity identity) throws IdCreateExceptions;

	public void updateAnIdentity(Identity identity) throws IdUpdateExceptions;

	public void deleteAnIdentity(Identity identity) throws IdDelExceptions;

	public List<Identity> listAllIdentities(Identity criteria) throws IdListofExceptions;


}
