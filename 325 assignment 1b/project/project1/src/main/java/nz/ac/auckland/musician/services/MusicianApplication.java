package nz.ac.auckland.musician.services;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/services")
public class MusicianApplication extends Application{
	   private Set<Object> singletons = new HashSet<Object>();
	   private Set<Class<?>> classes = new HashSet<Class<?>>();

	   public MusicianApplication(){
//		   singletons.add(new MusicianResource());
		   classes.add(MusicianResource.class);
		   classes.add(BandResource.class);
		   // Register the ContextResolver class for JAXB.
		   classes.add(MusicianResolver.class);
	   }
	   
	   /**
	    * Returns service object for Singleton model
	    */
	   @Override
	   public Set<Object> getSingletons(){
		   return singletons;
	   }
	   
	   @Override
	   public Set<Class<?>> getClasses()
	   {
	      return classes;
	   }
}
