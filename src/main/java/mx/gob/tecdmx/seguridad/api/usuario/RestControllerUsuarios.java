package mx.gob.tecdmx.seguridad.api.usuario;

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
public class RestControllerUsuarios {

	@Autowired
	UsuarioService usuarioService;
	
	@CrossOrigin()
	@RequestMapping(method = RequestMethod.POST, path = "/registrar-usuario-no-auth", produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> createUsuario(@RequestBody DTOUsuario user) {
		MetodosUtils utils = new MetodosUtils();
		DTOResponse response = new DTOResponse();
		usuarioService.createUserNoAuth(user, response);
		return ResponseEntity.ok().header(null).body(utils.objectToJson(response));
	}
	
	@CrossOrigin()
	@RequestMapping(method = RequestMethod.POST, path = "/userinfo", produces = "application/json")
	@ResponseBody
	public DTOResponse userInfo(Authentication auth) {
		DTOResponse response = new DTOResponse();
		return usuarioService.userInfo(auth, response);
	}
	
}
