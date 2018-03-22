package persistencia;

import entidad.Countent;

public class CuntentPersistence extends Persistencer<Countent, String>{
	
	public CuntentPersistence() {
		this.entityClass = Countent.class;
	}
	
}