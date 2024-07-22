package mx.gob.tecdmx.seguridad.api.Admin;

import mx.gob.tecdmx.seguridad.api.menu.DTOPermisos;

public class PayloadRolMenu {

	String codigoRol;
	int moduloId;
	DTOPermisos permisos;
	
	public String getCodigoRol() {
		return codigoRol;
	}
	public void setCodigoRol(String codigoRol) {
		this.codigoRol = codigoRol;
	}
	public int getModuloId() {
		return moduloId;
	}
	public void setModuloId(int moduloId) {
		this.moduloId = moduloId;
	}
	public DTOPermisos getPermisos() {
		return permisos;
	}
	public void setPermisos(DTOPermisos permisos) {
		this.permisos = permisos;
	}
	

}
