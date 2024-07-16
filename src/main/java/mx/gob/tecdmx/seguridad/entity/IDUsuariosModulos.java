package mx.gob.tecdmx.seguridad.entity;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class IDUsuariosModulos implements Serializable {
	
	private Integer nIdUsuario;
    private Integer nIdModulo;
    
	public Integer getNIdUsuario() {
		return nIdUsuario;
	}
	public void setNIdUsuario(Integer nIdUsuario) {
		this.nIdUsuario = nIdUsuario;
	}
	public Integer getNIdModulo() {
		return nIdModulo;
	}
	public void setNIdModulo(Integer nIdModulo) {
		this.nIdModulo = nIdModulo;
	}
    
    
	
}