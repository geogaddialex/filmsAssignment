package viewer;

import model.Film;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.*;
import com.google.gson.Gson;


public class FilmViewer {
	
	public String toJson( List<Film> input ) {
		Gson gson = new Gson();
		return gson.toJson( input );
	}
	
	public String toXML( List<Film> input) throws JsonProcessingException {

		JacksonXmlModule module = new JacksonXmlModule();
	    module.setDefaultUseWrapper(true);
		ObjectMapper mapper = new XmlMapper(module);
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

		String xml = mapper.writer().withRootName("Films").writeValueAsString(input);
		System.out.println(xml);
		
		return xml;
	}
	
}
