package nz.ac.auckland.musician.services;

import javax.ws.rs.ext.ContextResolver;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import nz.ac.auckland.musician.domain.Band;
import nz.ac.auckland.musician.domain.Musician;

public class MusicianResolver implements ContextResolver<JAXBContext> {

	private JAXBContext context;
	
	public MusicianResolver(){
		try {
			// The JAXB Context should be able to marshall and unmarshall the
			// specified classes.
			context = JAXBContext.newInstance(Musician.class, Band.class);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public JAXBContext getContext(Class<?> type) {
		if (type.equals(Musician.class) || type.equals(Band.class)) {
			return context;
		} else {
			return null;
		}
	}

}
