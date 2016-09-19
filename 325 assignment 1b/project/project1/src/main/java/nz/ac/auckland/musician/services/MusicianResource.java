package nz.ac.auckland.musician.services;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nz.ac.auckland.musician.domain.Band;
import nz.ac.auckland.musician.domain.Experience;
import nz.ac.auckland.musician.domain.Instrument;
import nz.ac.auckland.musician.domain.Musician;
import nz.ac.auckland.utilities.PersistanceManager;

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
		logger.info("Retrieving Musician with id: " + id);
		
		PersistanceManager manager = PersistanceManager.instance();
		List<Musician> allMusicians = manager.getMusiciansFromDatabase();
		
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
		
		PersistanceManager manager = PersistanceManager.instance();
		List<Musician> allMusicians = manager.getMusiciansFromDatabase();
		
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
	 * GET <base-uri>/musicians/band/{id}
	 * Returns a band with appropriate id.
	 * @param id
	 * @return band with the required id
	 */
	@GET
	@Path("/band/{id}")
	@Produces("application/xml")
	public Band getBandPathParam(@PathParam("id") int id){
		logger.info("Retrieving band with id: " + id);
		
		PersistanceManager manager = PersistanceManager.instance();
		List<Band> bands = manager.getBandsFromDatabase();
		
		logger.info("Number of bands found : " + Integer.toString(bands.size()));
		
		for (Band band : bands){
			logger.info(band.getBandName());
			if(band.getId() == id){
				return band;
			}
		}
		return null;
	}
	
	/**
	 * Gets all musicians who play a certain instrument.
	 * GET <base-uri>/musicians ? instrument = {instrument}
	 * @param instrument as a query parameter
	 * @return
	 */
	@GET
	@Produces("application/xml")
	public List<Musician> retrieveMusiciansByInstrument(@QueryParam("Instrument") String instrument) {
		Instrument instr = Instrument.fromString(instrument.toUpperCase());
		
		PersistanceManager manager = PersistanceManager.instance();
		List<Musician> allMusicians = manager.getMusiciansFromDatabase();
		List<Musician> reqdMusicians = new ArrayList<Musician>();
		for(Musician muso : allMusicians){
			if(muso.getMainInstrument() == instr){
				reqdMusicians.add(muso);
			}
		}
		return reqdMusicians;
	}
	
	/**
	 * Gets all musicians who play a certain instrument and have a certain skill level
	 * GET <base-uri>/musician ? instrument = {instrument}, experience = {xp}
	 * @param: instrument: required instrument, xp: required skill level
	 * @return
	 */
	@GET
	@Produces("application/xml")
	public List<Musician> retrieveMusicianByInstrumentAndExperience(@QueryParam("Instrument") String instrument, 
			@QueryParam("Experience") String xp){
		Instrument instr = Instrument.fromString(instrument.toUpperCase());
		Experience exp = Experience.fromString(xp.toUpperCase());
		
		PersistanceManager manager = PersistanceManager.instance();
		List<Musician> allMusicians = manager.getMusiciansFromDatabase();
		List<Musician> reqdMusicians = new ArrayList<Musician>();
		for(Musician muso : allMusicians){
			if(muso.getMainInstrument() == instr && muso.getSkillLevel() == exp){
				reqdMusicians.add(muso);
			}
		}
		return reqdMusicians;
	}
	
	/**
	 * Creates a new musician and adds it to the database
	 * POST <base-uri>/musician/{musician}
	 * TODO: remove random print statements
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
	 * Adds a band to the database.
	 * @param band
	 * @return
	 */
	@POST
	@Path("/bands")
	@Consumes("application/xml")
	public Response createBand(Band band){
		logger.info("Adding band: " + band.getBandName());
		// Add musician to database and persist the musician
		// persist using JPA
		
		// Print contents of database before adding musician
		PersistanceManager manager = PersistanceManager.instance();
		
		// Option 1: Using PersistenceManager
		manager.addBandToDatabase(band);
		
		// printing for debugging
		logger.info("Added band: " + band.getBandName() + " to the database");
		
		return Response.created(URI.create("/musician/band/" + band.getId()))
				.build();
	}
}
