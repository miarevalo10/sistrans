package vos;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class RespuestaConsultaMovimientos {
	@JsonProperty(value="id")
	private int id;
	@JsonProperty(value="id-carga")
	private int idCarga;
	@JsonProperty(value="fecha-movimiento")
	private Date fechaMovimiento;
	public RespuestaConsultaMovimientos(@JsonProperty(value="id") int id,
			@JsonProperty(value="id-carga") int idCarga, 
			@JsonProperty(value="fecha-movimiento") Date fechaMovimiento
			){
		this.setId(id);
		this.setIdCarga(idCarga);
		this.setFechaMovimiento(fechaMovimiento);
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the idCarga
	 */
	public int getIdCarga() {
		return idCarga;
	}
	/**
	 * @param idCarga the idCarga to set
	 */
	public void setIdCarga(int idCarga) {
		this.idCarga = idCarga;
	}
	/**
	 * @return the fechaMovimiento
	 */
	public Date getFechaMovimiento() {
		return fechaMovimiento;
	}
	/**
	 * @param fechaMovimiento the fechaMovimiento to set
	 */
	public void setFechaMovimiento(Date fechaMovimiento) {
		this.fechaMovimiento = fechaMovimiento;
	}
}
