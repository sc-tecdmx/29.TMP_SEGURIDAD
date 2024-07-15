package mx.gob.tecdmx.seguridad.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "seg_roles_modulos", schema = "public")
@IdClass(IDRolesModulos.class)
public class SegRolesModulos {
	@Id
	@Column(name="n_id_rol")
	Integer  nIdRol;
	
	@Id
	@Column(name="n_id_modulo")  
	Integer  nIdModulo;

	@Column(name = "crear")
	String  crear;
  
	@Column(name = "leer")
	String  leer;
  
	@Column(name = "editar")
	String  editar;
  
	@Column(name = "eliminar")
	String  eliminar;
  
	@Column(name = "publico")
	String  publico;
  
	@Column(name = "n_session_id")
	Integer  sessionId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="n_id_rol", referencedColumnName="n_id_rol", insertable = false, updatable = false)
    private SegRoles segRoles;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="n_id_modulo", referencedColumnName="n_id_modulo", insertable = false, updatable = false)
    private SegModulos segModulos;

	
}