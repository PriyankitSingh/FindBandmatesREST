package nz.ac.auckland.musician.services;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nz.ac.auckland.musician.domain.Band;
import nz.ac.auckland.musician.domain.Musician;
import nz.ac.auckland.utilities.PersistanceManager;

@Path("/bands")
public class BandResource {

	// Setup a Logger.
		private static Logger logger = LoggerFactory.getLogger(MusicianResource.class);

		// A list of all musicians in the system
		private Map<Integer, Musician> musicianDB = new ConcurrentHashMap<Integer, Musician>();
		
		/**
		 * Default constructor
		 */
		public BandResource(){}
		
		/**
		 * 
		 * @param band
		 * @return
		 */
		@POST
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
			
			return Response.created(URI.create("/band/" + band.getId()))
					.build();
		}
		
}
