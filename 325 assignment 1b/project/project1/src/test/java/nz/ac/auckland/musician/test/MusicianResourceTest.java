package nz.ac.auckland.musician.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nz.ac.auckland.musician.domain.Band;
import nz.ac.auckland.musician.domain.Experience;
import nz.ac.auckland.musician.domain.Gender;
import nz.ac.auckland.musician.domain.Instrument;
import nz.ac.auckland.musician.domain.Musician;

public class MusicianResourceTest {
	
	private static String WEB_SERVICE_URI = "http://localhost:10000/services/musicians";

	private Logger logger = LoggerFactory.getLogger(MusicianResourceTest.class);
	private static Client client;
	private static Musician ozzy;
	private static Musician ti;
	private static Band blackSabbath;
	
	@BeforeClass
	public static void setUpDatabase(){
		client = ClientBuilder.newClient();
		// create 1st musician
		Calendar cal = Calendar.getInstance();
		cal.set(1948, 2, 19);
		ti = new Musician(1, "Iommi", "Tony", Gender.MALE, Experience.PROFESSIONAL, cal, Instrument.GUITAR);
		
		// Make a POST request to the service
		Response response = client
				.target(WEB_SERVICE_URI).request()
				.post(Entity.xml(ti));
		response.close();
		
		cal.set(1948, 12, 3);
		ozzy = new Musician(2, "Osbourne", "Ozzy", Gender.MALE, Experience.PROFESSIONAL, cal, Instrument.VOCALS);
		// Make a POST request to the service
		Response response2 = client
				.target(WEB_SERVICE_URI).request()
				.post(Entity.xml(ozzy));
		response2.close();
		
		// Add members to a band
		List<Musician> members = new ArrayList();
		members.add(ozzy);
		members.add(ti);
		blackSabbath = new Band("Black Sabbath");
		
		// Make a POST request to add them to the band
		Response response3 = client
				.target(WEB_SERVICE_URI+"/bands").request()
				.post(Entity.xml(blackSabbath));
		response3.close();
	}
	
	@AfterClass
	public static void tearDown(){
		client.close();
	}
	
	/**
	 * Testing musician resource using path parameters.
	 */
	@Test
	public void testCreateMusician(){
		logger.debug("Starting testCreateMusician");
		
		logger.info("Making GET request");
		
		// Make a GET request to the server using query parameters to get Toni Iommi
//		Musician musicianInResponse = client.target(WEB_SERVICE_URI).queryParam("id", "1")
//				.request().accept("application/xml").get(Musician.class);
		
		// Making GET request using path parameters to get musician with id = 1
		Musician musicianInResponse = client.target(WEB_SERVICE_URI).path("1").request()
				.accept("application/xml").get(Musician.class);
		
		if(musicianInResponse == null){
			fail("GET request did not return any object");
		}
		// Check if correct musician is recieved
		assertEquals(ti.getFirstname(), musicianInResponse.getFirstname());
		assertEquals(ti.getMainInstrument(), musicianInResponse.getMainInstrument());
		assertEquals(ti.getSkillLevel(), musicianInResponse.getSkillLevel());
		assertEquals(ti.getLastname(), musicianInResponse.getLastname());
		assertEquals(ti.getGender(), musicianInResponse.getGender());
	}
	
	/**
	 * Testing band resource using path parameters
	 */
	@Test
	public void testBandResource(){
		logger.debug("Starting testCreateMusician");
		
		logger.info("Making GET request");
		
		Band band = client.target(WEB_SERVICE_URI + "/band").path("1").request()
				.accept("application/xml").get(Band.class);
		
		logger.info("Band name : " + band.getBandName());
		assertEquals(blackSabbath.getBandName(), band.getBandName());
	}

}
