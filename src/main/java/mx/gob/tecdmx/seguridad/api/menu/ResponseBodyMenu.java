package mx.gob.tecdmx.seguridad.api.menu;

import java.util.List;

public class ResponseBodyMenu {
	String aplicacion;
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
	
}
