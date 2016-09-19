package nz.ac.auckland.musician.test;

import static org.junit.Assert.*;

import java.util.Calendar;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	}
	
	@AfterClass
	public static void tearDown(){
		client.close();
	}
	
	/**
	 * Testing POST and GET responses. Marshalling should be done automatically.
	 */
	@Test
	public void testCreateMusician(){
		logger.debug("Starting testCreateMusician");
		
//		if (response.getStatus() != 201) {
//			fail("Failed to create new Musician");
//		}
		
//		String location = response.getLocation().toString();
//		response.close();
		
		logger.info("Making GET request"); // Doesn't seem to work
		
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
	
	@Test
	public void testBandResource(){
		
	}

}
