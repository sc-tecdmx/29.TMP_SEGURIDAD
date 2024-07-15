package mx.gob.tecdmx.seguridad.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "seg_log_sesion", schema = "public")
public class SegLogSesion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "n_session_id", unique = true, nullable = false)
	int id;
  
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="n_id_usuario", referencedColumnName="n_id_usuario")
	SegUsuarios idUsuario;
  
	@Column(name = "d_fecha_inicio")
	Date  fechaInicio;
  
	@Column(name = "d_fecha_fin")
	Date  fechaFin;
  
	@Column(name = "n_end_sesion")
	long  endSesion;
  
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="chain_n_session_id", referencedColumnName="n_session_id")
	SegLogSesion chainSessionId;

	
	
	
}