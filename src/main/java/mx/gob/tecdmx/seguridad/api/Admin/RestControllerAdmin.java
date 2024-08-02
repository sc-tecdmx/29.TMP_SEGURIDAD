package mx.gob.tecdmx.seguridad.api.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import mx.gob.tecdmx.seguridad.api.menu.ResponseBodyMenu;


@RestController
@RequestMapping(path = "/api/v1")
public class RestControllerAdmin {

	@Autowired
	ServiceAdmin serviceAdmin;
	
	@CrossOrigin()
	@RequestMapping(method = RequestMethod.GET, path = "/rol-permisos/{idRol}", produces = "application/json")
	public DTOResponseAdmin getPermisoByRol(@PathVariable("idRol") int idRol, Authentication auth) {
		DTOResponseAdmin response = new DTOResponseAdmin();
		return serviceAdmin.getPermisosByRol(response, idRol,auth);
	}
	
	@CrossOrigin()
	@RequestMapping(method = RequestMethod.POST, path = "/create-rol", produces = "application/json")
	@ResponseBody
	public DTOResponseAdmin crearRol(@RequestBody PayloadRol payload,  Authentication auth) {
		DTOResponseAdmin response = new DTOResponseAdmin();
		return serviceAdmin.crearRol(payload,response,auth);
	}
	
	@CrossOrigin()
	@RequestMapping(method = RequestMethod.POST, path = "/rol-modulos", produces = "application/json")
	@ResponseBody
	public DTOResponseAdmin asociarRolModulos(@RequestBody PayloadRolMenu payload, Authentication auth) {
		DTOResponseAdmin response = new DTOResponseAdmin();
		return serviceAdmin.asociarRolModulos(payload,response, auth);
	}
	
	@CrossOrigin()
	@RequestMapping(method = RequestMethod.PUT, path = "/editar-permisos/{idRol}", produces = "application/json")
	@ResponseBody
	public DTOResponseAdmin editarPermisoByRol(@RequestBody ResponseBodyMenu payload, @PathVariable("idRol") int idRol, Authentication auth) {
		DTOResponseAdmin response = new DTOResponseAdmin();
		return serviceAdmin.editarPermisosByRol(payload,response, idRol, auth);
	}
}
