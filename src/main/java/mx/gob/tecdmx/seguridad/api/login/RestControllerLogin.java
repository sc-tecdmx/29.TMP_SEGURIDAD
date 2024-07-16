package mx.gob.tecdmx.seguridad.api.login;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/api/v1")
public class RestControllerLogin {
	
	@Autowired
	ServiceLogin loginService;

	@CrossOrigin()
	@RequestMapping(method = RequestMethod.POST, path = "/login", produces = "application/json")
	@ResponseBody
	public DTOResponseLogin login(@RequestBody DTOPayloadLogin payload, HttpServletResponse response) {
		return loginService.login(payload, response);
	}
	
	@CrossOrigin()
	@RequestMapping(method = RequestMethod.POST, path = "/logout", produces = "application/json")
	@ResponseBody
	public boolean logOut(Authentication auth) {
		return loginService.logout(auth);
	}
	
	@CrossOrigin()
	@RequestMapping(method = RequestMethod.PUT, path = "/update-password", produces = "application/json")
	@ResponseBody
	public DTOResponseLogin updatePassword(@RequestBody DTOPayloadLogin payload) {
		return loginService.updatePassword(payload);
	}
}
