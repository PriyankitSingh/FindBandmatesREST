package nz.ac.auckland.utilities;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nz.ac.auckland.musician.domain.Band;
import nz.ac.auckland.musician.domain.Musician;

public class PersistanceManager {
	private Logger logger = LoggerFactory.getLogger(PersistanceManager.class);
	
	private static PersistanceManager instance = null;
	
	private EntityManager manager = null;
	
	@PersistenceContext
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("nz.ac.auckland.musicianPU");
	
	@Resource
	UserTransaction transaction;
	
	public PersistanceManager(){
		logger.info("Created manager");
	}
	
	
	public EntityManager createEntityManager() {
		return factory.createEntityManager();
	}
	
	public static PersistanceManager instance() {
		if(instance == null) {
			instance = new PersistanceManager();
		}
		return instance;
	}
	
	/**
	 * 
	 * @param musician
	 */
	public void addMusicianToDatabase(Musician musician){
		logger.info("Creating entity manager");
		manager = factory.createEntityManager();
		manager.getTransaction().begin();
		logger.info("begun transaction");
		
		try{
			manager.persist(musician);
		}catch(Exception e){
			e.printStackTrace();
		}
		logger.info("Sent to persist");
		manager.getTransaction().commit();
		manager.clear();
	}
	
	public List<Musician> getMusiciansFromDatabase(){
		manager.getTransaction().begin();
		List<Musician> musicians = manager.createQuery("select m from Musician m", Musician.class).getResultList();
		return musicians;
	}
	
	/**
	 * 
	 * @param band
	 */
	public void addBandToDatabase(Band band){
		EntityManager manager = factory.createEntityManager();
		try {
			transaction.begin();
			manager.persist(band);
			transaction.commit();
		} catch (NotSupportedException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HeuristicMixedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HeuristicRollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
