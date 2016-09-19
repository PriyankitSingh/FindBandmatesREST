package nz.ac.auckland.musician.services;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nz.ac.auckland.musician.domain.Experience;
import nz.ac.auckland.musician.domain.Instrument;
import nz.ac.auckland.musician.domain.Musician;
import nz.ac.auckland.utilities.DatabaseUtility;
import nz.ac.auckland.utilities.PersistanceManager;
import nz.ac.auckland.utilities.PersistenceManager2;

@Path("/musicians")
public class MusicianResource {
	// Setup a Logger.
	private static Logger logger = LoggerFactory.getLogger(MusicianResource.class);

	// A list of all musicians in the system
	private Map<Integer, Musician> musicianDB = new ConcurrentHashMap<Integer, Musician>();
	
	// A counter to keep track of how many ids have been allocated to 
	//avoid having multiple users with the same ID
	private AtomicInteger idCounter = new AtomicInteger();
	
	/**
	 * Constructor for this class. Gets all data from database
	 */
	public MusicianResource(){
		// Load data from database
	}
	
	/**
	 * GET <base-uri>/musicians/?id={id}
	 * Retrieves a musician based on id and returns it
	 * @param id: ID of musician to be retrieved
	 * @return: Musician with the id
	 */
	@GET
	@Produces("application/xml")
	public Musician getMusicianQueryParam(@QueryParam("id") int id){
		logger.info("Retrieving parolee with id: " + id);
		final Musician musician = musicianDB.get(id);
		
		if (musician == null) {
			logger.info("Musician not found");
			// Return a HTTP 404 response if the specified Musician isn't found.
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return musician;
	}
	
	/**
	 * GET <base-uri>/musicians/{id}
	 * Retrieves a musician based on id and returns it
	 * @param id: ID of musician to be retrieved
	 * @return: Musician with the id
	 */
	@GET
	@Path("{id}")
	@Produces("application/xml")
	public Musician getMusicianPathParam(@PathParam("id") int id){
		logger.info("Retrieving Musician with id: " + id);
		
//		EntityManagerFactory entityManagerFactory= Persistence.createEntityManagerFactory("nz.ac.auckland.musicianPU");
//		EntityManager manager = entityManagerFactory.createEntityManager();
//		manager.getTransaction().begin();
//		
//		logger.info("Making Query to the database");
//		// Get all musicians from the database.
//		List<Musician> allMusicians = manager.createQuery("select m from Musician m", Musician.class).getResultList();
//		logger.info("Number of musicians: " + Integer.toString(allMusicians.size()) + "<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//		for (Musician m : allMusicians ) {
//			logger.info("Musician: " + m.getFirstname() + " " + m.getLastname());
//		}
		// not working 
		PersistanceManager manager = PersistanceManager.instance();
		List<Musician> allMusicians = manager.getMusiciansFromDatabase();
		manager.close();
		
//		entityManagerFactory.close();
		logger.info("Number of musicians found: " + Integer.toString(allMusicians.size()) );
		
		for (Musician muso : allMusicians){
			logger.info(muso.getFirstname()+" "+ muso.getLastname()+" " +muso.getId());
			if (muso.getId() == id){
				return muso;
			}
		}
		return null;
	}
	
	/**
	 * Gets all musicians who play a certain instrument.
	 * GET <base-uri>/musicians ? instrument = GUITAR TODO: use JAXB
	 * @param instrument as a query parameter
	 * @return
	 */
	@GET
	@Produces("application/xml")
	public StreamingOutput retrieveMusiciansByInstrument(@QueryParam("Instrument") Instrument instrument) {
		final List<Musician> musicians = new ArrayList<Musician>();

		for (int i=0; i < musicians.size(); i++){
			Musician currMusician = musicianDB.get(i);
			if(currMusician.getMainInstrument() == instrument){
				musicians.add(currMusician);
			}	
		}
		return new StreamingOutput() {
			public void write(OutputStream outputStream) throws IOException,
					WebApplicationException {
				outputMusicianList(outputStream, musicians);
			}
		};
	}
	
	/**
	 * Gets the first musician.
	 * FOR TESTING <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	 * @return
	 */
	@GET
	@Produces("application/xml")
	public List<Musician> getFirstMusician(){
		logger.info("Inside test GET method");
		
		logger.info("Getting all musicians from the database");
		
		PersistanceManager manager = new PersistanceManager();
		List<Musician> allMusicians = manager.getMusiciansFromDatabase();
		manager.close();
		
		logger.info("Retrieved data from database of size: " + Integer.toString(allMusicians.size()));
		return allMusicians;
//		if(allMusicians.size() == 0){
//			logger.info("No musicians present in the database");
//			return null;
//		}
	}
	
	/**
	 * Gets all musicians who play a certain instrument and have a certain skill level
	 * GET <base-uri>/musician ? instrument = {instrument}, experience = {xp} TODO: use JAXB
	 * @param: instrument: required instrument, xp: required skill level
	 * @return
	 */
	@GET
	@Produces("application/xml")
	public StreamingOutput retrieveMusicianByInstrumentAndExperience(@QueryParam("Instrument") Instrument instrument, 
			@QueryParam("Experience") Experience xp){
		final List<Musician> musicians = new ArrayList<Musician>();

		for (int i=0; i < musicians.size(); i++){
			Musician currMusician = musicianDB.get(i);
			if(currMusician.getMainInstrument() == instrument && currMusician.getSkillLevel() == xp){
				musicians.add(currMusician);
			}	
		}
		return new StreamingOutput() {
			public void write(OutputStream outputStream) throws IOException,
					WebApplicationException {
				outputMusicianList(outputStream, musicians);
			}
		};
	}
	
	/**
	 * Creates a new musician and adds it to the database
	 * POST <base-uri>/musician/{musician}
	 * @param musician: musician to be added to database
	 * @return
	 */
	@POST
	@Consumes("application/xml")
	public Response createMusician(Musician musician){
		logger.info("Reading musician: " + musician.getFirstname()
				+ " " + musician.getLastname());
		
		// Add musician to database and persist the musician
		// persist using JPA
		
		// Print contents of database before adding musician
		PersistanceManager manager = PersistanceManager.instance();
		List<Musician> allMusicians = manager.getMusiciansFromDatabase();
		logger.info(Integer.toString(allMusicians.size()) + "<<<<<<<<<<<<<<<<<<<<<<<<");
		
		// Option 1: Using PersistenceManager
		
		manager.addMusicianToDatabase(musician);
		
		allMusicians = manager.getMusiciansFromDatabase();
		logger.info(Integer.toString(allMusicians.size()) + "<<<<<<<<<<<<<<<<<<<<<<<<");

		// Don't use musicianDB
		musicianDB.put(musician.getId(), musician);
		
		// printing for debugging
		logger.info("Added " + musicianDB.get(musician.getId()).getFirstname()
				+ " " + musicianDB.get(musician.getId()).getLastname());
		logger.info(musician.getFirstname() + " " + musician.getLastname()
				+ " id: " + musician.getId());
		
		return Response.created(URI.create("/musician/" + musician.getId()))
				.build();
	}
	
	
	/**
	 * Writes a musician to the output stream
	 * TODO: use JAXB for marshalling and unmarshalling
	 * @param os
	 * @param musician
	 * @throws IOException
	 */
	protected void outputMusician(OutputStream os, Musician musician) throws IOException{
		String dateOfBirth = musician.getDateOfBirth().toString();
		
		PrintStream writer = new PrintStream(os);
		writer.println("<parolee id=\"" + musician.getId() + "\">");
		writer.println("   <first-name>" + musician.getFirstname()
				+ "</first-name>");
		writer.println("   <last-name>" + musician.getLastname()
				+ "</last-name>");
		writer.println("   <gender>" + musician.getGender() + "</gender>");
		writer.println("   <instrument>" + musician.getMainInstrument() +"</instrument>");
		writer.println("   <experience>" + musician.getSkillLevel()+ "</experience>");
		writer.println("   <date-of-birth>" + dateOfBirth + "</date-of-birth>");
		writer.println("   <band>" + musician.getBands() + "</band>"); 
		writer.println("</parolee>");
	}
	
	/**
	 * 
	 * @param os
	 * @param musician
	 * @throws JAXBException 
	 */
	protected void outputMarshalledObject(OutputStream os, Musician musician) throws JAXBException{
		JAXBContext context = JAXBContext.newInstance(Musician.class);
		Marshaller marshaller = context.createMarshaller();
		
		marshaller.marshal(musician, os);
		marshaller.marshal(musician, System.out);
		return;
	}
	
	/**
	 * Writes multiple musicians to the output stream
	 * @param os
	 * @param musicians
	 * @throws IOException
	 */
	protected void outputMusicianList(OutputStream os, List<Musician> musicians) throws IOException {
		for(Musician person : musicians){
			outputMusician(os, person);
		}
	}
}
