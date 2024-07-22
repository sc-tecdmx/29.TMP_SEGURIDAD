package mx.gob.tecdmx.seguridad.api.Admin;

import java.util.List;

import mx.gob.tecdmx.seguridad.api.menu.DTOPermisos;

public class DTORolMenu {
	
	int nivelModulo;
	int moduloId;
	String codigo;
	int pos;
	List<DTORolMenu> modulos;
	List<DTOPermisos> permisos;
	
	public int getNivelModulo() {
		return nivelModulo;
	}
	public void setNivelModulo(int nivelModulo) {
		this.nivelModulo = nivelModulo;
	}
	public int getModuloId() {
		return moduloId;
	}
	public void setModuloId(int moduloId) {
		this.moduloId = moduloId;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public int getPos() {
		return pos;
	}
	public void setPos(int pos) {
		this.pos = pos;
	}
	public List<DTORolMenu> getModulos() {
		return modulos;
	}
	public void setModulos(List<DTORolMenu> modulos) {
		this.modulos = modulos;
	}
	public List<DTOPermisos> getPermisos() {
		return permisos;
	}
	public void setPermisos(List<DTOPermisos> permisos) {
		this.permisos = permisos;
	}
	
	

}
