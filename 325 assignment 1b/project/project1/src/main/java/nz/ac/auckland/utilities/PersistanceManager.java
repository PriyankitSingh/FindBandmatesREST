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
	 * Adds musician to the database
	 * @param musician
	 */
	public void addMusicianToDatabase(Musician musician){
		logger.info("Creating entity manager");
		manager = factory.createEntityManager();
		manager.getTransaction().begin();
		logger.info("began a transaction");
		
		try{
			manager.persist(musician);
		}catch(Exception e){
			e.printStackTrace();
		}
		logger.info("Sent to persist");
		manager.getTransaction().commit();
		manager.close();
	}
	
	/**
	 * Gets all musicians from the database.
	 * @return
	 */
	public List<Musician> getMusiciansFromDatabase(){
		manager = factory.createEntityManager();
		manager.getTransaction().begin();
		List<Musician> musicians = manager.createQuery("select m from Musician m", Musician.class).getResultList();
		
		manager.close();
		return musicians;
	}
	
	/**
	 * Adds a band to H2 database
	 * @param band
	 */
	public void addBandToDatabase(Band band){
		logger.info("Creating entity manager");
		manager = factory.createEntityManager();
		manager.getTransaction().begin();
		logger.info("began a transaction");
		try{
			manager.persist(band);
		}catch(Exception e){
			e.printStackTrace();
		}
		logger.info("Sent to persist");
		manager.getTransaction().commit();
		manager.close();
	}
	
	/**
	 * Gets all bands from the database
	 * @return
	 */
	public List<Band> getBandsFromDatabase(){
		manager = factory.createEntityManager();
		manager.getTransaction().begin();
		List<Band> bands = manager.createQuery("select b from Band b", Band.class).getResultList();
		
		manager.close();
		return bands;
	}
	
	public void close(){
		factory.close();
	}
}
