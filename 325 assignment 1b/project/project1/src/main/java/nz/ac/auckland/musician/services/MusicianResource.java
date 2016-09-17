package nz.ac.auckland.musician.services;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nz.ac.auckland.musician.domain.Experience;
import nz.ac.auckland.musician.domain.Instrument;
import nz.ac.auckland.musician.domain.Musician;

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
	 * GET <base-uri>/musician/{id}
	 * Retrieves a musician based on their unique id. The format of the 
	 * returned data is XML.
	 * @param id
	 * @return
	 */
	@GET
	@Path("{id}")
	@Produces("application/xml")
	public StreamingOutput retrieveMusician(@PathParam("id") int id) {
		logger.info("Retrieving parolee with id: " + id);
		// Lookup the Parolee within the in-memory data structure.
		final Musician musician = musicianDB.get(id);
		if (musician == null) {
			// Return a HTTP 404 response if the specified Parolee isn't found.
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}

		// Return a StreamingOuput instance that the JAX-RS implementation will
		// use to set the body of the HTTP response message.
		return new StreamingOutput() {
			public void write(OutputStream outputStream) throws IOException,
					WebApplicationException {
				outputMusician(outputStream, musician);
			}
		};
	}
	
	/**
	 * Gets all musicians who play a certain instrument.
	 * GET <base-uri>/musician ? instrument = GUITAR
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
	 * Gets all musicians who play a certain instrument and have a certain skill level
	 * GET <base-uri>/musician ? instrument = {instrument}, experience = {xp}
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
