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
@Table(name = "seg_log_usuario", schema = "public")
public class SegLogUsuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id_log_usuario", unique = true, nullable = false)
	int  id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="n_id_usuario", referencedColumnName="n_id_usuario")
	SegUsuarios  idUsuario;
  
	@Column(name = "d_sistema")
	Date  dSistema;
  
	@Column(name = "n_session_id")
	int  sessionId; 
  
	@Column(name = "bitacora")
	String  bitacora;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public SegUsuarios getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(SegUsuarios idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Date getDSistema() {
		return dSistema;
	}

	public void setDSistema(Date dSistema) {
		this.dSistema = dSistema;
	}

	public int getSessionId() {
		return sessionId;
	}

	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}

	public String getBitacora() {
		return bitacora;
	}

	public void setBitacora(String bitacora) {
		this.bitacora = bitacora;
	}

	
}