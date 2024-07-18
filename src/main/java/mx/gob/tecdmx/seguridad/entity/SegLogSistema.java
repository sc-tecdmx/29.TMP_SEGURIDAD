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
@Table(name = "seg_log_sistema", schema = "public")
public class SegLogSistema {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id_log_sistema", unique = true, nullable = false)
	int  id_log_sistema;
  
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="n_id_usuario_org", referencedColumnName="n_id_usuario")
	SegUsuarios  idUsuarioOrg;
	
	@Column(name = "n_id_usuario_jel")
	int idUsuarioJel;
  
	@Column(name = "d_sistema")
	Date  dSistema;
  
	@Column(name = "bitacora")
	String  bitacora;
	
	public int getId_log_sistema() {
		return id_log_sistema;
	}

	public void setId_log_sistema(int id_log_sistema) {
		this.id_log_sistema = id_log_sistema;
	}

	public SegUsuarios getIdUsuarioOrg() {
		return idUsuarioOrg;
	}

	public void setIdUsuarioOrg(SegUsuarios idUsuarioOrg) {
		this.idUsuarioOrg = idUsuarioOrg;
	}

	public int getIdUsuarioJel() {
		return idUsuarioJel;
	}

	public void setIdUsuarioJel(int idUsuarioJel) {
		this.idUsuarioJel = idUsuarioJel;
	}

	public Date getdSistema() {
		return dSistema;
	}

	public void setdSistema(Date dSistema) {
		this.dSistema = dSistema;
	}

	public String getBitacora() {
		return bitacora;
	}

	public void setBitacora(String bitacora) {
		this.bitacora = bitacora;
	}
	
}