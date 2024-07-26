package mx.gob.tecdmx.seguridad.api.menu;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PerfilDTO {
	
	String perfil;
	ResponseBodyMenu menu;
	
	public String getPerfil() {
		return perfil;
	}
	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
	public ResponseBodyMenu getMenu() {
		return menu;
	}
	public void setMenu(ResponseBodyMenu menu) {
		this.menu = menu;
	}
	

}
