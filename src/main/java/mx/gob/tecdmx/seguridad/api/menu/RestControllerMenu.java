package mx.gob.tecdmx.seguridad.api.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import mx.gob.tecdmx.seguridad.utils.DTOResponse;
import mx.gob.tecdmx.seguridad.utils.SeguridadUtils;


@RestController
@RequestMapping(path = "/api/v1")
public class RestControllerMenu {
	
	@Autowired
	ServiceMenu menuService;

	@CrossOrigin()
	@RequestMapping(method = RequestMethod.POST, path = "/create-menu", produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> createMenu(@RequestBody PayloadMenu payload) {
		SeguridadUtils utils = new SeguridadUtils();
		DTOResponse response = new DTOResponse();
		menuService.createMenu(payload, response);
		return ResponseEntity.ok().header(null).body(utils.objectToJson(response));
	}
	
	@CrossOrigin()
	@RequestMapping(method = RequestMethod.POST, path = "/create-modulo", produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> createModulo(@RequestBody PayloadMenu payload) {
		SeguridadUtils utils = new SeguridadUtils();
		DTOResponse response = new DTOResponse();
		menuService.createModulo(payload, response);
		return ResponseEntity.ok().header(null).body(utils.objectToJson(response));
	}
	
	@CrossOrigin()
	@RequestMapping(method = RequestMethod.GET, path = "/modulo/{idModulo}", produces = "application/json")
	public PayloadMenu getModulo(@PathVariable("idModulo") int idModulo) {
		PayloadMenu response = new PayloadMenu();
		return menuService.getModulo(response,idModulo);
	}

	
}
