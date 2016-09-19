package nz.ac.auckland.musician.test;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nz.ac.auckland.musician.domain.Experience;
import nz.ac.auckland.musician.domain.Gender;
import nz.ac.auckland.musician.domain.Instrument;
import nz.ac.auckland.musician.domain.Musician;
import nz.ac.auckland.utilities.DatabaseUtility;
import nz.ac.auckland.utilities.PersistanceManager;

public class MusicianPersistanceTest {
//	
//	private Logger logger = LoggerFactory.getLogger(MusicianPersistanceTest.class);
//	private EntityManagerFactory entityManagerFactory;
//	protected EntityManager entityManager = null;
//	
//	/**
//	 * Runs before each test to create a new EntityManagerFactory.
//	 * 
//	 */
//	@Before
//	public void setUp() throws Exception {
//		// Create an EntityManagerFactory from which an EntityManager can be 
//		// requested. The argument to createEntityManagerFactory() is the name
//		// of a persistence unit, named in META-INF/persistence.xml. The 
//		// factory configures itself based on reading the xml file. 
//		// persistence.xml must contain a persistence unit named, in this case,
//		// "nz.ac.auckland.hello".
//		entityManagerFactory = Persistence.createEntityManagerFactory( "nz.ac.auckland.musicianPU" );
//	}
//
//	/**
//	 * Runs after each test to destroy the EntityManagerFactory.
//	 * 
//	 */
//	@After
//	public void tearDown() throws Exception {
//		// Close the EntityManagerFactory once all tests have executed.
//		entityManagerFactory.close();
//	}
//	
//	/**
//	 * Runs after all tests have executed to print the contents of the 
//	 * database.
//	 */
//	@AfterClass
//	public static void dumpDatabase() throws Exception {
//		Set<String> tableNames = new HashSet<String>();
//		tableNames.add("MESSAGE");
//		DatabaseUtility.openDatabase();
//		DatabaseUtility.dumpDatabase(tableNames);
//		DatabaseUtility.closeDatabase();
//	}
//	
//	/**
//	 * Illustrates use of the JPA EntityManager. This test uses a transaction 
//	 * to store a Message object in the database. It then uses another
//	 * transaction to query the database for Messages. It prints the Message,
//	 * changes its state and updates the database. 
//	 */
//	@Test
//	public void testBasicUsage() {
//		// Acquire an EntityManager, representing a session with the database.
//		// Using the entityManager, create a transaction.
//		entityManager = entityManagerFactory.createEntityManager();
//		entityManager.getTransaction().begin();
//		
//		// create 1st musician
//		Calendar cal = Calendar.getInstance();
//		cal.set(1963, 8, 3);
//		Musician het = new Musician(1, "Hetfield", "James", Gender.MALE, Experience.PROFESSIONAL, cal, Instrument.GUITAR);
//		
//		// Request the the EntityManager stores the Message.
//		entityManager.persist(het);
//		
//		// Commit the transaction. This causes the JPA provider to execute the
//		// SQL statement: 
//		//   insert into MESSAGE (ID, TEXT) values (1, 'Hello, World!')
//		entityManager.getTransaction().commit();
//
//		// Now let's pull the Message from the database. Start a new 
//		// transaction. 
//		entityManager.getTransaction().begin();
//		
//		// Query the database for stored Messages. The query is expressed using
//		// JPQL (Java Persistence Query Language) which looks similar to SQL. 
//		// Rather than being written in terms of tables and columns, JPQL 
//		// queries are written in terms of classes and properties. This JPQL
//		// query generates the SQL query: select * from MESSAGE.
//        List<Musician> musicians = entityManager.createQuery("select m from Musician m", Musician.class).getResultList();
//		for (Musician m : musicians ) {
//			logger.info("Musician: " + m.getFirstname() + " " + m.getLastname());
//		}
//		// They query should return one Musician object.
//		assertEquals(1, musicians.size());
//		
//		// The text of the returned Message should be what was originally 
//		// persisted.
//		Musician retrievedMusician = musicians.get(0);
//		assertEquals("James", retrievedMusician.getFirstname());
//		
//		// The query actually returns a reference to the original Message
//		// object. This is because the persistence context is managing the
//		// original object. If a query returns an object that is already
//		// managed by the persistence context, a separate copy of the object
//		// isn't made.
//		assertSame(het, retrievedMusician);
//		
//		cal.set(1994, 7, 3);
//		Musician pri = new Musician(2, "Singh", "Priyankit", Gender.MALE, Experience.ADVANCED, cal, Instrument.GUITAR);
//		
//		entityManager.persist(pri);
//		
//        entityManager.getTransaction().commit();
//        
//        entityManager.getTransaction().begin();
//        musicians = entityManager.createQuery("select m from Musician m", Musician.class).getResultList();
//        for (Musician m : musicians ) {
//			logger.info("Musician: " + m.getFirstname() + " " + m.getLastname());
//		}
//        
//        // check number of musicians in database
//        assertEquals(2, musicians.size());
//	}
//	
//	/**
//	 * Testing persistance manager.
//	 */
//	@Test
//	public void testPersistanceManager(){
//		logger.info("Starting test 2");
//		PersistanceManager manager = new PersistanceManager();
//		
//		Calendar cal = Calendar.getInstance();
//		cal.set(1963, 8, 3);
//		Musician het = new Musician(1, "Hetfield", "James", Gender.MALE, Experience.PROFESSIONAL, cal, Instrument.GUITAR);
//		
//		cal.set(1994, 7, 3);
//		Musician pri = new Musician(2, "Singh", "Priyankit", Gender.MALE, Experience.ADVANCED, cal, Instrument.GUITAR);
//		
//		manager.addMusicianToDatabase(het);
//		manager.addMusicianToDatabase(pri);
//		
//		// Query the database here
//		logger.info("Attempting to get data from database");
//		
//		List<Musician> musicians = manager.getMusiciansFromDatabase();
//		
//		// check number of musicians in database
//		assertEquals(2, musicians.size());
//		
//		manager.close();
//	}

}
