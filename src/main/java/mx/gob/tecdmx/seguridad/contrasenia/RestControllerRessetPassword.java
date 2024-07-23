package mx.gob.tecdmx.seguridad.contrasenia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import mx.gob.tecdmx.seguridad.api.login.ServiceLogin;
import mx.gob.tecdmx.seguridad.repository.SegUsuariosRepository;

@RestController
@RequestMapping("/api/email")
public class RestControllerRessetPassword {

	@Autowired
	ServiceLogin serviceLogin;

	@Autowired
	private SegUsuariosRepository userRepository;

	@Autowired
	private ServiceRessetPassword emailService; // You need to implement this service

	@CrossOrigin()
	@RequestMapping(method = RequestMethod.POST, path = "/solicitud-reset-password", produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> requestPasswordReset(@RequestParam String email) {
//		DTOResponse response = new DTOResponse();
//
//		SegUsuarios user = userRepository.findBysEmail(email)
//				.orElseThrow(() -> new UsernameNotFoundException("El usuario no existe"));
//
//		String resetToken = UUID.randomUUID().toString();
//		user.setsToken(resetToken);
//		userRepository.save(user);
//
//		// Send email (Implement this in EmailService)
//
//		emailService.sendPasswordResetEmail(user.getsEmail(), resetToken);
//
//		response.setMessage("Se ha enviado un link para reestablecer la contraseña al correo electronico");
//		response.setStatus("Success");
////		return ResponseEntity.ok().header(null).body(utils.objectToJson(response));
		return null;

	}

	@CrossOrigin()
	@RequestMapping(method = RequestMethod.POST, path = "/reset-password", produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> resetPassword(@RequestBody DTOPayloadRessetPass payload) {
//		DTOResponse response = new DTOResponse();
//		Optional<SegUsuarios> credentials = userRepository.findBysToken(payload.token);
//		if (credentials.isPresent()) {
//			credentials.get().setsContrasenia(serviceLogin.encryptPassword(payload.getPassword()));
//			credentials.get().setsToken(null);
//			userRepository.save(credentials.get());
//
//			response.setStatus("Success");
//			response.setMessage("La contraseña se ha actualizado satisfactoriamente");
//		} else {
//			response.setStatus("Failed");
//			response.setStatus("Token inválido o expirado.");
//		}
//		//return ResponseEntity.ok().header(null).body(utils.objectToJson(response));
		return null;
		
	}

}
