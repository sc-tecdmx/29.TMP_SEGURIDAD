package mx.gob.tecdmx.seguridad.api.menu;

import java.util.List;

public class PayloadMenu {
	
	String nivelModulo;
	String nombreModulo;
	String url;
	int pos;
	List<PayloadMenu> modulos;
	List<DTOPermisos> permisos;
	
	public String getNivelModulo() {
		return nivelModulo;
	}
	public void setNivelModulo(String nivelModulo) {
		this.nivelModulo = nivelModulo;
	}
	public String getNombreModulo() {
		return nombreModulo;
	}
	public void setNombreModulo(String nombreModulo) {
		this.nombreModulo = nombreModulo;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getPos() {
		return pos;
	}
	public void setPos(int pos) {
		this.pos = pos;
	}
	public List<PayloadMenu> getModulos() {
		return modulos;
	}
	public void setModulos(List<PayloadMenu> modulos) {
		this.modulos = modulos;
	}
	public List<DTOPermisos> getPermisos() {
		return permisos;
	}
	public void setPermisos(List<DTOPermisos> permisos) {
		this.permisos = permisos;
	}
	
	
	
}
