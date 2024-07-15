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
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "seg_usuario_estado_usuario", schema = "public")
public class SegUsuarioEstadoUsuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "n_id_usuario_status", unique = true, nullable = false)
	int  id;
  
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="n_id_usuario", referencedColumnName="n_id_usuario")
	SegUsuarios  idUsuario;
  
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="n_id_estado_usuario", referencedColumnName="n_id_estado_usuario")
	SegCatEstadoUsuario  idEstadoUsuario;
  
	@Column(name = "fingerprint_dispositivo")
	String  fingerprintDispositivo;
  
	@Column(name = "d_fecha_status")
	Date  fechaStatus;
  
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="n_session_id", referencedColumnName="n_session_id")
	SegLogSesion  sessionId;

	
	
}