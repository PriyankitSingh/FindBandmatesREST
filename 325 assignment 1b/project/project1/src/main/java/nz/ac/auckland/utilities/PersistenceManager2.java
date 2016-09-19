package nz.ac.auckland.utilities;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Singleton class to manages an EntityManagerFactory. When a
 * PersistenceManager is instantiated, it creates an EntityManagerFactory. When
 * a Web service application component (e.g. an interceptor or resource object)
 * requires a persistence context (database session), it should call the 
 * PersistentManager's createEntityManager() method to acquire one. The 
 * requester is responsible for closing the session. 
 * 
 * @author Ian Warren
 *
 */
public class PersistenceManager2 {
	private static PersistenceManager2 _instance = null;
	
	private EntityManagerFactory _entityManagerFactory;
	
	public PersistenceManager2() {
		_entityManagerFactory = Persistence.createEntityManagerFactory("nz.ac.auckland.musicianPU");
	}
	
	public EntityManager createEntityManager() {
		return _entityManagerFactory.createEntityManager();
	}
	
	public static PersistenceManager2 instance() {
		if(_instance == null) {
			_instance = new PersistenceManager2();
		}
		return _instance;
	}

}