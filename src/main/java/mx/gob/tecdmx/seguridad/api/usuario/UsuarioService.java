/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.tecdmx.seguridad.api.usuario;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import mx.gob.tecdmx.seguridad.api.login.ServiceLogin;
import mx.gob.tecdmx.seguridad.api.menu.PerfilDTO;
import mx.gob.tecdmx.seguridad.api.menu.ResponseBodyMenu;
import mx.gob.tecdmx.seguridad.api.menu.ServiceMenu;
import mx.gob.tecdmx.seguridad.entity.IDUsuariosModulos;
import mx.gob.tecdmx.seguridad.entity.SegCatEstadoUsuario;
import mx.gob.tecdmx.seguridad.entity.SegLogSesion;
import mx.gob.tecdmx.seguridad.entity.SegLogUsuario;
import mx.gob.tecdmx.seguridad.entity.SegModulos;
import mx.gob.tecdmx.seguridad.entity.SegRoles;
import mx.gob.tecdmx.seguridad.entity.SegRolesUsuarios;
import mx.gob.tecdmx.seguridad.entity.SegUsuarioEstadoUsuario;
import mx.gob.tecdmx.seguridad.entity.SegUsuarios;
import mx.gob.tecdmx.seguridad.entity.SegUsuariosModulos;
import mx.gob.tecdmx.seguridad.repository.SegCatEstadoUsuarioRepository;
import mx.gob.tecdmx.seguridad.repository.SegLogSesionRepository;
import mx.gob.tecdmx.seguridad.repository.SegLogUsuarioRepository;
import mx.gob.tecdmx.seguridad.repository.SegModulosRepository;
import mx.gob.tecdmx.seguridad.repository.SegRolesRepository;
import mx.gob.tecdmx.seguridad.repository.SegRolesUsuariosRepository;
import mx.gob.tecdmx.seguridad.repository.SegUsuarioEstadoUsuarioRepository;
import mx.gob.tecdmx.seguridad.repository.SegUsuariosModulosRepository;
import mx.gob.tecdmx.seguridad.repository.SegUsuariosRepository;
import mx.gob.tecdmx.seguridad.security.config.UsuarioSecurityDTO;
import mx.gob.tecdmx.seguridad.utils.DTOResponse;

@Service
public class UsuarioService {

	@Autowired
	private SegUsuariosRepository SegUsuariosRepository;

	@Autowired
	SegLogUsuarioRepository segLogUsuarioRepository;

	@Autowired
	SegCatEstadoUsuarioRepository segCatEstadoUsuarioRepository;

	@Autowired
	SegUsuarioEstadoUsuarioRepository SegUsuarioEstadoUsuarioRepository;

	@Autowired
	SegRolesRepository SegRolesRepository;

	@Autowired
	SegLogSesionRepository SegLogSesionRepository;

	@Autowired
	SegRolesUsuariosRepository SegRolesUsuariosRepository;

	@Autowired
	SegUsuariosModulosRepository segUsuariosModulosRepository;

	@Autowired
	SegModulosRepository segModulosRepository;

	@Autowired
	ServiceLogin serviceLogin;

	@Autowired
	ServiceMenu menuService;

	public boolean storeLogUsuario(SegUsuarios usuario, String estatus) {
		// almacena el Log del usuario
		SegLogUsuario userLog = new SegLogUsuario();
		userLog.setIdUsuario(usuario);
//						userLog.setN_session_id(sesionExist.get().getId());
		userLog.setDSistema(new Date());
		userLog.setBitacora(estatus);
		segLogUsuarioRepository.save(userLog);
		return true;
	}

	// Este método valida si el usuario existe para recuperarlo o para crearlo
	public SegUsuarios retrieveOrCreateUser(DTOPayloadUsuario userDTO, SegCatEstadoUsuario statusUsuario) {

		SegUsuarios usuarioStored = null;

		// Validación Existe Usuario
		Optional<SegUsuarios> usuarioExist = SegUsuariosRepository.findByEmailOrUsuario(userDTO.getEmail(),
				userDTO.getUsuario());

		if (!usuarioExist.isPresent()) {// El usuario no existe, entonces se crea

			// Tabla usuario
			SegUsuarios usuario = new SegUsuarios();
			usuario.setUsuario(userDTO.getUsuario());
			usuario.setsContrasenia(serviceLogin.encryptPassword(userDTO.getContrasenia()));
			usuario.setsDescUsuario(null);
			usuario.setEmail(userDTO.getEmail());

			usuario.setnIdEstadoUsuario(statusUsuario);
			usuario.setsToken(null);
			usuarioStored = SegUsuariosRepository.save(usuario);

			// Tabla usuario-estadoUsuario
			SegUsuarioEstadoUsuario bitacoraEstatusUsuario = new SegUsuarioEstadoUsuario();
			bitacoraEstatusUsuario.setIdUsuario(usuarioStored);
			bitacoraEstatusUsuario.setIdEstadoUsuario(statusUsuario);
			bitacoraEstatusUsuario.setFingerprintDispositivo(null);
			bitacoraEstatusUsuario.setFechaStatus(new Date());
			// NOTA: No se setea la sesión ya que no hay autenticación
			SegUsuarioEstadoUsuarioRepository.save(bitacoraEstatusUsuario);

			// ALmacenamos en el log del usuario
			storeLogUsuario(usuarioStored, "Creado");
			return usuarioStored;
		}
		return usuarioExist.get();
	}

	public boolean validateUsuarioEstaAsignadoSistema(SegModulos modulo, SegUsuarios usuario) {
		IDUsuariosModulos usuModId = new IDUsuariosModulos();
		usuModId.setNIdModulo(modulo.getId());
		usuModId.setNIdUsuario(usuario.getnIdUsuario());

		Optional<SegUsuariosModulos> usuMod = segUsuariosModulosRepository.findById(usuModId);
		if (usuMod.isPresent()) {
			return true;

		}
		return false;
	}

	public SegUsuariosModulos registrarUsuarioEnSistema(SegModulos modulo, SegUsuarios usuario) {
		// Tabla usuario-modulos
		SegUsuariosModulos usuariosModulos = new SegUsuariosModulos();
		usuariosModulos.setNIdUsuario(usuario.getnIdUsuario());
		usuariosModulos.setNIdModulo(modulo.getId());
		usuariosModulos.setFechaAlta(new Date());
		usuariosModulos.setFechaBaja(null);
		// Nota: No guardo la sesión
		SegUsuariosModulos usuariosModulosStored = segUsuariosModulosRepository.save(usuariosModulos);
		// ALmacenamos en el log del usuario
		storeLogUsuario(usuario, "Se registra al usuario en el sistema " + modulo.getMenuDesc());
		return usuariosModulosStored;
	}

	public DTOResponse createUser(DTOPayloadUsuario userDTO, DTOResponse response) {

		List<String> codigoRol = new ArrayList<String>();

		Optional<SegCatEstadoUsuario> statusUsuario = segCatEstadoUsuarioRepository
				.findById(userDTO.getStatusCuenta());
		if (!statusUsuario.isPresent()) {
			response.setMessage("El estatus de la cuenta del usuario no existe: " + userDTO.getStatusCuenta());
			return response;
		}

		SegUsuarios usuario = retrieveOrCreateUser(userDTO, statusUsuario.get());

		// Validación Usuario Asignado al Sistema
		Optional<SegModulos> modulo = segModulosRepository.findByCodigo(userDTO.getCodigoSistema());
		if (validateUsuarioEstaAsignadoSistema(modulo.get(), usuario)) {
			// El usuario ya ha sido asignado a ese sistema
			response.setMessage("El usuario ya se encuentra registrado en el sistema: " + userDTO.getCodigoSistema());
			return response;
		}

		// Se registra al usuario en el sistema
		SegUsuariosModulos usuarioModulo = registrarUsuarioEnSistema(modulo.get(), usuario);

		// Se asocia el rol al usuario
		for (String codigo : userDTO.getCodigoRol()) {
			Optional<SegRoles> rol = SegRolesRepository.findByEtiquetaRol(codigo);
			if (!rol.isPresent()) {
				response.setMessage("El Código de Rol " + codigo + " que intentas asociar no existe en el sistema");
				return response;
			}

			SegRolesUsuarios rolesUsuarios = new SegRolesUsuarios();
			rolesUsuarios.setIdRol(rol.get());
			rolesUsuarios.setIdUsuario(usuario);
			SegRolesUsuariosRepository.save(rolesUsuarios);

			// ALmacenamos en el log del usuario
			storeLogUsuario(usuario,
					"Se asoció el rol" + rol.get().getDescripcion() + " al usuario " + usuario.getUsuario());

			codigoRol.add(rol.get().getEtiquetaRol());
		}

		DTOResponseUsuario dtoUser = new DTOResponseUsuario();
		dtoUser.setIdUsuario(usuario.getnIdUsuario());
		dtoUser.setEmail(usuario.getEmail());
		dtoUser.setUsuario(usuario.getUsuario());
		dtoUser.setCodigoRol(codigoRol);
		dtoUser.setEstatusCuenta(statusUsuario.get().getDescripcion());
		dtoUser.setCodigoSistema(userDTO.getCodigoSistema());

		response.setData(dtoUser);
		response.setMessage("El usuario se han creado correctamente");
		response.setStatus("Success");
		return response;
	}

	public DTOResponse userInfo(Authentication auth, DTOResponse response) {
		UsuarioSecurityDTO usuarioVO = (UsuarioSecurityDTO) auth.getDetails();
		Optional<SegUsuarios> usuario = SegUsuariosRepository.findById(usuarioVO.getIdUsuario());
		if (usuario.isPresent()) {
			DTOUserInfo userInfo = new DTOUserInfo();
			userInfo.setUsuario(usuario.get().getUsuario());
			userInfo.setEmail(usuario.get().getEmail());
			userInfo.setIdUsuario(usuario.get().getnIdUsuario());

			List<PerfilDTO> perfiles = menuService.getMenu(auth);
			userInfo.setPerfiles(perfiles);
			response.setData(userInfo);
			response.setMessage("EL token es válido");
			response.setStatus("Success");
			return response;
		}
		response.setMessage("EL token es inválido");
		response.setStatus("Fail");
		return response;
	}

	public DTOResponse getUsuarios(DTOResponse response, Authentication auth, int numeroPagina, int tamanoPagina) {
		UsuarioSecurityDTO usuarioVO = (UsuarioSecurityDTO) auth.getDetails();
		DTOUserInfo userInfo = null;
		List<DTOUserInfo> usuariosInfo = new ArrayList<>();
		Pageable pageable = PageRequest.of(numeroPagina, tamanoPagina);
		Page<SegUsuarios> usuariosPage = SegUsuariosRepository.findAll(pageable);
		List<SegUsuarios> usuarios = usuariosPage.getContent();

		for (SegUsuarios usuario : usuarios) {
			userInfo = new DTOUserInfo();
			userInfo.setIdUsuario(usuario.getnIdUsuario());
			userInfo.setUsuario(usuario.getUsuario());
			userInfo.setEmail(usuario.getEmail());

			Optional<SegCatEstadoUsuario> idEstado = segCatEstadoUsuarioRepository
					.findById(usuario.getnIdEstadoUsuario().getId());
			if (idEstado.isPresent()) {
				userInfo.setStatusCuenta(idEstado.get().getDescripcion());
			}

			ResponseBodyMenu menu = new ResponseBodyMenu();
			List<PerfilDTO> perfiles = menuService.getMenu(menu, usuario, usuarioVO.getSys());
			userInfo.setPerfiles(perfiles);

			usuariosInfo.add(userInfo);
		}

		response.setMessage("Información de los Usuarios");
		response.setStatus("Success");
		response.setData(usuariosInfo);
		return response;

	}

	public DTOResponse rolAndModulosByUser(DTOPayloadUsuario userDTO, DTOResponse response, Authentication auth) {
		SegUsuarios usuarioStored = null;
		List<String> codigoRol = new ArrayList<String>();
		UsuarioSecurityDTO userSecurity = (UsuarioSecurityDTO) auth.getDetails();
		if(userSecurity==null) {
			response.setMessage("No cuenta con permisos para realizar esta acción");
			return response;
		}
		// verifica que el correo a utilizar no se encuentre en la bd de usuarios
		Optional<SegUsuarios> usuarioExist = SegUsuariosRepository.findByUsuario(userDTO.getUsuario());
		if (usuarioExist.isPresent()) {
			for (String codigo : userDTO.getCodigoRol()) {
				Optional<SegRoles> rol = SegRolesRepository.findByEtiquetaRol(codigo);
				if (rol.isPresent()) {
					// Tabla Roles-Usuarios
					SegRolesUsuarios rolesUsuarios = new SegRolesUsuarios();
					rolesUsuarios.setIdRol(rol.get());
					rolesUsuarios.setIdUsuario(usuarioExist.get());
					Optional<SegLogSesion> sesionExist = SegLogSesionRepository.findById(userSecurity.getIdSession());
					if (sesionExist.isPresent()) {
						rolesUsuarios.setnSessionId(sesionExist.get());
					}
					// NOTA: No se setea el empleado puesto area, ya que no tenemos este dato para
					// este ejecicio
					SegRolesUsuariosRepository.save(rolesUsuarios);

					Optional<SegModulos> modulo = segModulosRepository.findByCodigo(userDTO.getCodigoSistema());

					// Tabla usuario-modulos
					SegUsuariosModulos usuariosModulos = new SegUsuariosModulos();
					usuariosModulos.setNIdUsuario(usuarioExist.get().getnIdUsuario());
					usuariosModulos.setNIdModulo(modulo.get().getId());
					usuariosModulos.setFechaAlta(new Date());
					usuariosModulos.setStatus(true);
					usuariosModulos.setFechaBaja(null);
					usuariosModulos.setSessionId(userSecurity.getIdSession());
					segUsuariosModulosRepository.save(usuariosModulos);

					// Tabla usuario
					Optional<SegCatEstadoUsuario> statusUsuario = segCatEstadoUsuarioRepository
							.findByDescripcion("Activa");
					usuarioExist.get().setnIdEstadoUsuario(statusUsuario.get());
					usuarioStored = SegUsuariosRepository.save(usuarioExist.get());

					// Tabla usuario-estadoUsuario
					SegUsuarioEstadoUsuario bitacoraEstatusUsuario = new SegUsuarioEstadoUsuario();
					bitacoraEstatusUsuario.setIdUsuario(usuarioStored);
					bitacoraEstatusUsuario.setIdEstadoUsuario(statusUsuario.get());
					bitacoraEstatusUsuario.setFingerprintDispositivo(null);
					bitacoraEstatusUsuario.setFechaStatus(new Date());
					if (sesionExist.isPresent()) {
						bitacoraEstatusUsuario.setSessionId(sesionExist.get());
					}
					// NOTA: No se setea la sesión ya que no hay autenticación
					SegUsuarioEstadoUsuarioRepository.save(bitacoraEstatusUsuario);

					// almacena el Log del usuario
					SegLogUsuario userLog = new SegLogUsuario();
					userLog.setIdUsuario(usuarioStored);
					userLog.setSessionId(sesionExist.get().getId());
					userLog.setDSistema(new Date());
					userLog.setBitacora("Actualizado");
					segLogUsuarioRepository.save(userLog);

					DTOResponseUsuario dtoUser = new DTOResponseUsuario();
					dtoUser.setIdUsuario(usuarioStored.getnIdUsuario());
					dtoUser.setEmail(usuarioStored.getEmail());
					dtoUser.setUsuario(usuarioStored.getUsuario());
					dtoUser.setCodigoRol(codigoRol);
					dtoUser.setEstatusCuenta(statusUsuario.get().getDescripcion());
					dtoUser.setCodigoSistema(userDTO.getCodigoSistema());

					response.setData(dtoUser);
					response.setMessage("El usuario se ha asociado correctamente");
					response.setStatus("Success");

				} else {
					response.setMessage("El rol seleccionado no existe");
					response.setStatus("Fail");
				}
			}

		} else {
			response.setMessage("El usuario seleccionado no existe");
			response.setStatus("Fail");

		}

		return response;

	}

}
