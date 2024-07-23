package mx.gob.tecdmx.seguridad.api.menu;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import mx.gob.tecdmx.seguridad.utils.DTOResponse;
import mx.gob.tecdmx.seguridad.utils.MetodosUtils;


@RestController
@RequestMapping(path = "/api/v1")
public class RestControllerMenu {
	
	@Autowired
	ServiceMenu menuService;

	@CrossOrigin()
	@RequestMapping(method = RequestMethod.POST, path = "/create-menu", produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> createMenu(@RequestBody PayloadMenu payload) {
		MetodosUtils utils = new MetodosUtils();
		DTOResponse response = new DTOResponse();
		menuService.createMenu(payload, response);
		return ResponseEntity.ok().header(null).body(utils.objectToJson(response));
	}
	
}
