package mx.gob.tecdmx.seguridad.api.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import mx.gob.tecdmx.seguridad.entity.SegCatNivelModulo;
import mx.gob.tecdmx.seguridad.entity.SegModulos;
import mx.gob.tecdmx.seguridad.entity.SegRoles;
import mx.gob.tecdmx.seguridad.entity.SegRolesModulos;
import mx.gob.tecdmx.seguridad.entity.SegRolesUsuarios;
import mx.gob.tecdmx.seguridad.entity.SegUsuarios;
import mx.gob.tecdmx.seguridad.repository.SegCatNivelModuloRepository;
import mx.gob.tecdmx.seguridad.repository.SegLogSesionRepository;
import mx.gob.tecdmx.seguridad.repository.SegModulosRepository;
import mx.gob.tecdmx.seguridad.repository.SegRolesModulosRepository;
import mx.gob.tecdmx.seguridad.repository.SegRolesRepository;
import mx.gob.tecdmx.seguridad.repository.SegRolesUsuariosRepository;
import mx.gob.tecdmx.seguridad.repository.SegUsuariosRepository;
import mx.gob.tecdmx.seguridad.security.config.UsuarioSecurityDTO;
import mx.gob.tecdmx.seguridad.utils.DTOResponse;

@Service
public class ServiceMenu {

	@Autowired
	private SegUsuariosRepository SegUsuariosRepository;

	@Autowired
	private SegRolesUsuariosRepository SegRolesUsuariosRepository;

	@Autowired
	private SegRolesModulosRepository SegRolesModulosRepository;

	@Autowired
	SegModulosRepository SegModulosRepository;

	@Autowired
	SegCatNivelModuloRepository segCatNivelModuloRepository;

	@Autowired
	SegRolesRepository SegRolesRepository;

	@Autowired
	SegLogSesionRepository SegLogSesionRepository;


	@Value("${spring.application.name}")
	private String nombreAplicativo;

	public List<PayloadMenu> fillMenu(List<SegRolesModulos> rolesModulos, List<PayloadMenu> menu, int parentId) {
		PayloadMenu menuChild = null;
		DTOPermisos permisos = null;
		for (SegRolesModulos rolMod : rolesModulos) {
			if (rolMod.getSegModulos().getNIdModuloPadre().getId() == parentId) {
				menuChild = new PayloadMenu();
				permisos = new DTOPermisos();
				menuChild.setNombreModulo(rolMod.getSegModulos().getDescModulo());
				menuChild.setPos(rolMod.getSegModulos().getMenuPos());
				menuChild.setNivelModulo(rolMod.getSegModulos().getNIdNivel().getDescNivel());
				permisos.setCrear(rolMod.getCrear().equals("S") ? true : false);
				permisos.setEditar(rolMod.getEditar().equals("S") ? true : false);
				permisos.setEliminar(rolMod.getEliminar().equals("S") ? true : false);
				permisos.setLeer(rolMod.getLeer().equals("S") ? true : false);
				permisos.setCodigoRol(rolMod.getSegRoles().getEtiquetaRol());

				List<DTOPermisos> permisosList = new ArrayList<DTOPermisos>();
				permisosList.add(permisos);
				menuChild.setPermisos(permisosList);

				menuChild.setUrl(rolMod.getSegModulos().getMenuUrl());
				List<PayloadMenu> childMenus = new ArrayList<PayloadMenu>();
				fillMenu(rolesModulos, childMenus, rolMod.getSegModulos().getId());
				menuChild.setModulos(childMenus);
				menu.add(menuChild);
			}

		}
		return menu;
	}

	public List<PerfilDTO> getMenu(ResponseBodyMenu acceso, SegUsuarios usuario, String sys) {
		List<SegRolesUsuarios> rolesUsuario = SegRolesUsuariosRepository.findByIdUsuario(usuario);
		List<ResponseBodyMenu> accsesosRolesPeril = new ArrayList<ResponseBodyMenu>();
		List<PerfilDTO> perfiles = new ArrayList<PerfilDTO>();

		// Buscar los menus a los que tiene acceso en una determinada aplicación
		for (SegRolesUsuarios usuarioRol : rolesUsuario) {
			acceso = new ResponseBodyMenu();
//			System.out.println(usuarioRol.getIdRol().getId());
//			System.out.println(usuarioRol.getIdRol().getDescripcion());

			// Se devuelve siempre y cuando se se encuentre activo
			List<SegRolesModulos> rolesModulos = SegRolesModulosRepository
					.findBySegRoles(usuarioRol.getIdRol());
			List<PayloadMenu> menu = new ArrayList<PayloadMenu>();
			for (SegRolesModulos rolMod : rolesModulos) {
				if (rolMod.getSegModulos().getDescModulo().equals(sys)
						&& rolMod.getSegModulos().getNIdNivel().getDescNivel().equals("Aplicación")) {
					acceso.setAplicacion(rolMod.getSegModulos().getDescModulo());
					int idAplicacion = rolMod.getSegModulos().getId();
					fillMenu(rolesModulos, menu, idAplicacion);
					acceso.setRol(usuarioRol.getIdRol().getDescripcion());
					acceso.setMenu(menu);
				}
				System.out.println(rolMod.getSegModulos().getDescModulo());
				System.out.println(rolMod.getSegModulos().getNIdNivel().getDescNivel());
			}
			
			PerfilDTO perfil = new PerfilDTO();
			perfil.setPerfil("INTERNO");
			perfil.setMenu(acceso);
			perfiles.add(perfil);
		}
		return perfiles;
	}

	
	public List<PerfilDTO> getMenu(Authentication auth) {
		UsuarioSecurityDTO usuario = (UsuarioSecurityDTO) auth.getDetails();
		Optional<SegUsuarios> credentials = SegUsuariosRepository.findBysEmail(usuario.getEmail());
		ResponseBodyMenu acceso = new ResponseBodyMenu();
		List<PerfilDTO> perfiles = null;
		if (credentials.isPresent()) {
			 perfiles = getMenu(acceso, credentials.get(), usuario.getSys());
		}
		return perfiles;
	}
	

	public SegCatNivelModulo findNivel(String nivel) {
		Optional<SegCatNivelModulo> nivelModulo = segCatNivelModuloRepository.findByDescNivel(nivel);
		if (nivelModulo.isPresent()) {
			return nivelModulo.get();
		}
		return null;
	}

	public boolean validateNivelAndPermisos(PayloadMenu payload) {
		SegCatNivelModulo nivelModulo = findNivel(payload.getNivelModulo());
		if (nivelModulo == null) {
			return false;
		}
		if (payload.getModulos() != null) {
			for (PayloadMenu moduloPayload : payload.getModulos()) {
				return validateNivelAndPermisos(moduloPayload);
			}
		}

		if (payload.getPermisos() != null) {
			for (DTOPermisos permiso : payload.getPermisos()) {
				Optional<SegRoles> rol = SegRolesRepository.findByEtiquetaRol(permiso.getCodigoRol());
				if (!rol.isPresent()) {
					return false;
				}
			}
		}
		return true;
	}

	public SegModulos storeMenu(PayloadMenu payload, int pos, SegModulos padreid, DTOResponse response) {
		SegModulos moduloPadre = null;

		SegModulos modulo = new SegModulos();

		SegCatNivelModulo nivelModulo = findNivel(payload.getNivelModulo());
		modulo.setNIdNivel(nivelModulo);
		modulo.setDescModulo(payload.getNombreModulo());

		if (payload.getNivelModulo().equals("Aplicación")) {
			Optional<SegModulos> moduloNoParent = SegModulosRepository.findById(-1);
			modulo.setNIdModuloPadre(moduloNoParent.get());
			modulo.setMenu("N");
			modulo.setMenuUrl(null);
			modulo.setMenuPos(1);

		} else {
			modulo.setNIdModuloPadre(padreid);
			modulo.setMenu("S");
			modulo.setMenuUrl(payload.getUrl());
			modulo.setMenuPos(pos);

		}

		moduloPadre = SegModulosRepository.save(modulo);
		if (payload.getModulos() != null) {
			for (int i = 0; i < payload.getModulos().size(); i++) {
				storeMenu(payload.getModulos().get(i), i + 1, moduloPadre, response);
			}
		}

		if (payload.getPermisos() != null) {
			for (DTOPermisos permiso : payload.getPermisos()) {

				Optional<SegRoles> rol = SegRolesRepository.findByEtiquetaRol(permiso.getCodigoRol());
				if (rol.isPresent()) {
					SegRolesModulos rolToSave = new SegRolesModulos();
					rolToSave.setNIdRol(rol.get().getId());
					rolToSave.setNIdModulo(moduloPadre.getId());
					rolToSave.setCrear(permiso.isCrear() ? "S" : "N");
					rolToSave.setLeer(permiso.isLeer() ? "S" : "N");
					rolToSave.setEditar(permiso.isEditar() ? "S" : "N");
					rolToSave.setEliminar(permiso.isEliminar() ? "S" : "N");
					rolToSave.setPublico(permiso.isPublico() ? "S" : "N");
//					rolToSave.setN_session_id(sesionExist.get().getId());
					SegRolesModulosRepository.save(rolToSave);
				}

			}
		}
		response.setData(moduloPadre);
		return moduloPadre;
	}

	public DTOResponse createMenu(PayloadMenu payload, DTOResponse response) {

		boolean hasNivel = validateNivelAndPermisos(payload);

		if (!hasNivel) {
			response.setMessage("El/Los nivel(es) especificado(s) no existe(n)");
			response.setStatus("Fail");
			return response;
		}
		SegModulos moduloStored = storeMenu(payload, 1, null, response);
		if (moduloStored != null) {
			response.setMessage("EL menú se ha guardado exitósamente");
			response.setStatus("Success");
		}
		return response;
	}

}
