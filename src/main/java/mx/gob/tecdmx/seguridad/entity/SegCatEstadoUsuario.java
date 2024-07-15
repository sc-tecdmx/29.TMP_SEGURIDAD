package mx.gob.tecdmx.seguridad.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "seg_cat_estado_usuario", schema = "public")
public class SegCatEstadoUsuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "n_id_estado_usuario", unique = true)
	int  n_id_estado_usuario;
  
	@Column(name = "s_descripcion")
	String  descripcion;
	
}