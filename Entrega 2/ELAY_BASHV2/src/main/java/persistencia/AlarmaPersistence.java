package persistencia;

import dto.AlarmaDTO;
import entidad.Alarma;
import java.util.List;
import java.util.logging.Level;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

public class AlarmaPersistence extends Persistencer<Alarma, String>{
	
	public AlarmaPersistence() {
		this.entityClass = Alarma.class;
	}
	
}