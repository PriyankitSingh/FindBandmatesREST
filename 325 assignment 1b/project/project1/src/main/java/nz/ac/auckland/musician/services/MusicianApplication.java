package nz.ac.auckland.musician.services;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/services")
public class MusicianApplication extends Application{
	   private Set<Object> singletons = new HashSet<Object>();

	   public MusicianApplication(){
		   singletons.add(new MusicianResource());
	   }
	   
	   /**
	    * Returns service object for Singleton model
	    */
	   @Override
	   public Set<Object> getSingletons(){
		   return singletons;
	   }
}
