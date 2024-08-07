package mx.gob.tecdmx.seguridad.api.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import mx.gob.tecdmx.seguridad.entity.SegCatNivelModulo;
import mx.gob.tecdmx.seguridad.entity.SegModulos;
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

	

	public List<PayloadMenu> fillMenu(List<SegRolesModulos> rolesModulos, List<PayloadMenu> menu, int parentId) {
		PayloadMenu menuChild = null;
		DTOPermisos permisos = null;
		for (SegRolesModulos rolMod : rolesModulos) {
			if (rolMod.getSegModulos().getNIdModuloPadre().getId() == parentId) {
				menuChild = new PayloadMenu();
				permisos = new DTOPermisos();
				menuChild.setIdModulo(rolMod.getnIdModulo());
				menuChild.setNombreModulo(rolMod.getSegModulos().getDescModulo());
				menuChild.setPos(rolMod.getSegModulos().getMenuPos());
				menuChild.setCodigo(rolMod.getSegModulos().getCodigo());
				menuChild.setNivelModulo(rolMod.getSegModulos().getNIdNivel().getDescNivel());
				permisos.setCrear(rolMod.getCrear().equals("S") ? true : false);
				permisos.setEditar(rolMod.getEditar().equals("S") ? true : false);
				permisos.setEliminar(rolMod.getEliminar().equals("S") ? true : false);
				permisos.setLeer(rolMod.getLeer().equals("S") ? true : false);
				
//				permisos.setCodigoRol(rolMod.getSegRoles().getEtiquetaRol());

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
		//aqui se debe buscar los roles de usuario por sistema
		List<SegRolesUsuarios> rolesUsuario = SegRolesUsuariosRepository.findByIdUsuario(usuario);
		List<PerfilDTO> perfiles = new ArrayList<PerfilDTO>();
		Optional<SegModulos> sistema = SegModulosRepository.findByCodigo(sys);
		// Buscar los menus a los que tiene acceso en una determinada aplicación
		for (SegRolesUsuarios usuarioRol : rolesUsuario) {
			acceso = new ResponseBodyMenu();

			// Se devuelve siempre y cuando se se encuentre activo
			List<SegRolesModulos> rolesModulos = SegRolesModulosRepository.findBynIdRol(usuarioRol.getIdRol().getId());
			List<PayloadMenu> menu = new ArrayList<PayloadMenu>();
			for (SegRolesModulos rolMod : rolesModulos) {
				if (rolMod.getSegModulos().getDescModulo().equals(sistema.get().getDescModulo())
						&& rolMod.getSegModulos().getNIdNivel().getDescNivel().equals("Aplicación")) {
					acceso.setAplicacion(rolMod.getSegModulos().getDescModulo());
					int idAplicacion = rolMod.getSegModulos().getId();
					acceso.setIdAplicacion(idAplicacion);
					fillMenu(rolesModulos, menu, idAplicacion);
					acceso.setRol(usuarioRol.getIdRol().getDescripcion());
					acceso.setIdRol(usuarioRol.getIdRol().getId());
					acceso.setMenu(menu);
				}
				
			}

			PerfilDTO perfil = new PerfilDTO();
			perfil.setMenu(acceso);
			perfiles.add(perfil);
		}
		return perfiles;
	}

	public List<PerfilDTO> getMenu(Authentication auth) {
		UsuarioSecurityDTO usuario = (UsuarioSecurityDTO) auth.getDetails();
		Optional<SegModulos> sistema = SegModulosRepository.findByDescModulo(usuario.getSys());
		Optional<SegUsuarios> credentials = SegUsuariosRepository.findByEmail(usuario.getEmail());
		ResponseBodyMenu acceso = new ResponseBodyMenu();
		List<PerfilDTO> perfiles = null;
		if (credentials.isPresent()) {
			perfiles = getMenu(acceso, credentials.get(), sistema.get().getCodigo());
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

//		if (payload.getPermisos() != null) {
//			for (DTOPermisos permiso : payload.getPermisos()) {
//				Optional<SegRoles> rol = SegRolesRepository.findByEtiquetaRol(permiso.getCodigoRol());
//				if (!rol.isPresent()) {
//					return false;
//				}
//			}
//		}
		return true;
	}

	public SegModulos storeMenu(PayloadMenu payload, int pos, SegModulos padreid, DTOResponse response) {
		SegModulos moduloPadre = null;

		SegModulos modulo = new SegModulos();

		SegCatNivelModulo nivelModulo = findNivel(payload.getNivelModulo());

		modulo.setNIdNivel(nivelModulo);
		modulo.setDescModulo(payload.getNombreModulo());
		modulo.setCodigo(payload.getCodigo());

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

		if (payload.getCodigo() == null) {
			response.setMessage("El código de menú ingresado no existe");
			response.setStatus("Fail");
			return response;
		} else {
			Optional<SegModulos> moduloExist = SegModulosRepository.findByCodigo(payload.getCodigo());
			if (moduloExist.isPresent()) {
				response.setMessage("El sistema que deseas registrar ya existe");
				response.setStatus("Fail");
				return response;
			}
		}
		SegModulos moduloStored = storeMenu(payload, 1, null, response);
		if (moduloStored != null) {
			response.setMessage("EL menú se ha guardado exitósamente");
			response.setStatus("Success");
		} else {
			response.setMessage("El menú que quieres crear ya existe");
			response.setStatus("Fail");
		}
		return response;
	}

	public DTOResponse createModulo(PayloadMenu payload, DTOResponse response, Authentication auth) {
		
		UsuarioSecurityDTO usuarioVO = (UsuarioSecurityDTO) auth.getDetails();
		if(usuarioVO==null) {
			response.setMessage("No tienes permisos para crear un menú");
			response.setStatus("Fail");
			return response;
		}
		boolean hasNivel = validateNivelAndPermisos(payload);
		
		if (!hasNivel) {
			response.setMessage("El nivel especificado no existe");
			response.setStatus("Fail");
			return response;
		}
		if (payload.getCodigo() == null) {
			response.setMessage("Ingresa un código para el módulo a registrar");
			response.setStatus("Fail");
			return response;
		} else {
			Optional<SegModulos> moduloExist = SegModulosRepository.findByCodigo(payload.getCodigo());
			if (moduloExist.isPresent()) {
				response.setMessage("El código "+payload.getCodigo()+" que deseas registrar ya existe para otro módulo");
				response.setStatus("Fail");
				return response;
			}
		}
		if(payload.getPadreId()==0) {
			payload.setPadreId(-1);	
		}
		if(payload.getPos()==0) {
			if(payload.getPadreId()==-1) {
				payload.setPos(1);	
			}else {
				Optional<SegModulos> moduloPadre = SegModulosRepository.findById(payload.getPadreId()); 
				if(moduloPadre.get() != null) {
					List<SegModulos> modulosHijos = SegModulosRepository.findBynIdModuloPadre(moduloPadre.get());
					if(modulosHijos.size()<=0) {
						payload.setPos(1);
					}else {
						payload.setPos(modulosHijos.size()+1);
					}
				}
				
			}
		}
		//se inicia el guardado del módulo
		SegModulos moduloStored = storeModulo(payload, response);
		if (moduloStored != null) {
			response.setMessage("EL módulo se ha guardado exitósamente");
			response.setStatus("Success");
		} else {
			response.setMessage("No fue posible crear el módulo");
			response.setStatus("Fail");
		}
		return response;
		
	}
	
	public SegModulos storeModulo(PayloadMenu payload, DTOResponse response) {
		
		SegModulos newModulo = null;

		SegModulos modulo = new SegModulos();

		SegCatNivelModulo nivelModulo = findNivel(payload.getNivelModulo());

		modulo.setNIdNivel(nivelModulo);
		modulo.setDescModulo(payload.getNombreModulo());
		modulo.setCodigo(payload.getCodigo());

		if (payload.getNivelModulo().equals("Aplicación")) {
			Optional<SegModulos> moduloNoParent = SegModulosRepository.findById(-1);
			modulo.setNIdModuloPadre(moduloNoParent.get());
			modulo.setMenu("N");
			modulo.setMenuUrl(payload.getUrl());
			modulo.setMenuPos(1);

		} else {
			Optional<SegModulos> moduloParent = SegModulosRepository.findById(payload.getPadreId());
			modulo.setNIdModuloPadre(moduloParent.get());
			modulo.setMenu("S");
			modulo.setMenuUrl(payload.getUrl());
			modulo.setMenuPos(payload.getPos());

		}

		newModulo = SegModulosRepository.save(modulo);
		
		//response.setData(newModulo);
		return newModulo;
	}

	public PayloadMenu getModulo(PayloadMenu response, int idModulo) {
		Optional<SegModulos> moduloExist = SegModulosRepository.findById(idModulo);
		if(moduloExist.isPresent()) {
			response.setIdModulo(moduloExist.get().getId());
			response.setCodigo(moduloExist.get().getCodigo());
			response.setNivelModulo(moduloExist.get().getnIdNivel().getDescNivel());
			response.setNombreModulo(moduloExist.get().getDescModulo());
			response.setUrl(moduloExist.get().getMenuUrl());
			response.setPadreId(moduloExist.get().getnIdModuloPadre().getId());
			return response;
		}
		// TODO Auto-generated method stub
		return null;
	}

}
