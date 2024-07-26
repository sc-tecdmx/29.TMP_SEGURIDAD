package mx.gob.tecdmx.seguridad.api.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
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
public class RestControllerUsuarios {

	@Autowired
	UsuarioService usuarioService;
	
	@CrossOrigin()
	@RequestMapping(method = RequestMethod.POST, path = "/registrar-usuario-no-auth", produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> createUsuario(@RequestBody DTOPayloadUsuario user) {
		SeguridadUtils utils = new SeguridadUtils();
		DTOResponse response = new DTOResponse();
		usuarioService.createUser(user, response);
		return ResponseEntity.ok().header(null).body(utils.objectToJson(response));
	}
	
	@CrossOrigin()
	@RequestMapping(method = RequestMethod.POST, path = "/user-rol-moduls", produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> UsuarioRolModulo(@RequestBody DTOPayloadUsuario user, Authentication auth) {
		SeguridadUtils utils = new SeguridadUtils();
		DTOResponse response = new DTOResponse();
		usuarioService.rolAndModulosByUser(user, response, auth);
		return ResponseEntity.ok().header(null).body(utils.objectToJson(response));
	}
	
	@CrossOrigin()
	@RequestMapping(method = RequestMethod.POST, path = "/userinfo", produces = "application/json")
	@ResponseBody
	public DTOResponse userInfo(Authentication auth) {
		DTOResponse response = new DTOResponse();
		return usuarioService.userInfo(auth, response);
	}
	
	@CrossOrigin()
	@RequestMapping(method = RequestMethod.GET, path = "/usuarios", produces = "application/json")
	public DTOResponse getusuarios(Authentication auth,
		        @RequestParam(defaultValue = "0") int pagina,
		        @RequestParam(defaultValue = "10") int tamano) {
		DTOResponse response = new DTOResponse();
		return usuarioService.getUsuarios(response, auth, pagina, tamano);
	}
	
}
