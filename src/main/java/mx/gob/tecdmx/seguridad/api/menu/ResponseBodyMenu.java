package mx.gob.tecdmx.seguridad.api.menu;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseBodyMenu {
	int idAplicacion;
	String aplicacion;
	int idRol;
	String rol;
	List<PayloadMenu> menu;
	public String getAplicacion() {
		return aplicacion;
	}
	public void setAplicacion(String aplicacion) {
		this.aplicacion = aplicacion;
	}
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}
	public List<PayloadMenu> getMenu() {
		return menu;
	}
	public void setMenu(List<PayloadMenu> menu) {
		this.menu = menu;
	}
	public int getIdAplicacion() {
		return idAplicacion;
	}
	public void setIdAplicacion(int idAplicacion) {
		this.idAplicacion = idAplicacion;
	}
	public int getIdRol() {
		return idRol;
	}
	public void setIdRol(int idRol) {
		this.idRol = idRol;
	}
	
}
