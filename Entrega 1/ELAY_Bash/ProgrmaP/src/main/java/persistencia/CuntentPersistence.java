package persistencia;

import dto.CountentDTO;
import entidad.Countent;
import java.util.List;
import java.util.logging.Level;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

public class CuntentPersistence extends Persistencer<Countent, String>{
	
	public CuntentPersistence() {
		this.entityClass = Countent.class;
	}
      
        
	
}