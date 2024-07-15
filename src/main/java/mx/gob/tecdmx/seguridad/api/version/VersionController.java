package mx.gob.tecdmx.seguridad.api.version;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RequestMapping("/api/v1")
@RestController
public class VersionController {

	@Autowired
    private Environment environment;
	
	@RequestMapping(method = RequestMethod.GET, path = "/version")
	public String version() {
				String appname = environment.getProperty("spring.application.name");
		String version = "v"+environment.getProperty("application.version");
		String timestamp = environment.getProperty("application.build.timestamp");
		String details =  "Versión: " + appname + " " + version + "\n" + "Compilación: " +timestamp;
		return details;
	}

}