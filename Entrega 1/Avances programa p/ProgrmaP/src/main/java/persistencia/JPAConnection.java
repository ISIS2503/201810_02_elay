package persistencia;

import com.impetus.client.cassandra.common.CassandraConstants;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAConnection {

	public static final String DB = "cassandra_db";

	private EntityManager entityManager;
	public static final JPAConnection CONNECTION = new JPAConnection();
	

	public JPAConnection() {
		if (entityManager == null) {
			//System.err.println("1");
			EntityManagerFactory emf;
			//System.err.println("2");
			Map<String, String> propertyMap = new HashMap<>();
			//System.err.println("3");
			//System.err.println("3.1 "+CassandraConstants.CQL_VERSION);
			//System.err.println("3.2 "+CassandraConstants.CQL_VERSION_3_0);
			propertyMap.put(CassandraConstants.CQL_VERSION, CassandraConstants.CQL_VERSION_3_0);
			//System.err.println("4");
			emf = Persistence.createEntityManagerFactory(DB, propertyMap);
			//System.err.println("5");
			entityManager = emf.createEntityManager();
			//System.err.println("6");
		}
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

}
