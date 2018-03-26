package entidad;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2018-03-25T04:41:41.065-0500")
@StaticMetamodel(Countent.class)
public class Countent_ {
	public static volatile SingularAttribute<Countent, String> id;
	public static volatile SingularAttribute<Countent, String> timestamp;
	public static volatile SingularAttribute<Countent, Integer> alertaId;
	public static volatile SingularAttribute<Countent, String> mensajeAlerta;
	public static volatile SingularAttribute<Countent, Integer> idDispositivo;
	public static volatile SingularAttribute<Countent, Integer> torre;
	public static volatile SingularAttribute<Countent, Integer> apto;
}
