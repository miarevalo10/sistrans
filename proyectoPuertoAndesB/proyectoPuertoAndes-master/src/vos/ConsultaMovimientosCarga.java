package vos;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class ConsultaMovimientosCarga {
	@JsonProperty(value="origen")
	private String origen;
	@JsonProperty(value="destino")
	private String destino;
	@JsonProperty(value="id-carga")
	private int idCarga;
	@JsonProperty(value="tipo-carga")
	private String tipoCarga;
	@JsonProperty(value="fecha-orden")
	private Date fechaOrden;
	@JsonProperty(value="fecha-movimiento")
	private Date fechaMovimiento;
	@JsonProperty(value="id-importador")
	private int idImportador;
	@JsonProperty(value="id-exportador")
	private int idExportador;
	public ConsultaMovimientosCarga(@JsonProperty(value="origen") String pOrigen, 
			@JsonProperty(value="destino") String pDestino, 
			@JsonProperty(value="id-carga") int pIdCarga, 
			@JsonProperty(value="tipo-carga") String pTipoCarga, 
			@JsonProperty(value="fecha-orden") Date pFechaOrden, 
			@JsonProperty(value="fecha-movimiento") Date pFechaMovimiento, 
			@JsonProperty(value="id-importador") int pIdImportador, 
			@JsonProperty(value="id-exportador") int pIdExportador){
		this.setOrigen(pOrigen);
		this.setDestino(pDestino);
		this.setIdCarga(pIdCarga);
		this.setTipoCarga(pTipoCarga);
		this.setFechaOrden(pFechaOrden);
		this.setFechaMovimiento(pFechaMovimiento);
		this.setIdImportador(pIdImportador);
		this.setIdExportador(pIdExportador);
	}
	/**
	 * @return the origen
	 */
	public String getOrigen() {
		return origen;
	}
	/**
	 * @param origen the origen to set
	 */
	public void setOrigen(String origen) {
		this.origen = origen;
	}
	/**
	 * @return the destino
	 */
	public String getDestino() {
		return destino;
	}
	/**
	 * @param destino the destino to set
	 */
	public void setDestino(String destino) {
		this.destino = destino;
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
	 * @return the tipoCarga
	 */
	public String getTipoCarga() {
		return tipoCarga;
	}
	/**
	 * @param tipoCarga the tipoCarga to set
	 */
	public void setTipoCarga(String tipoCarga) {
		this.tipoCarga = tipoCarga;
	}
	/**
	 * @return the fechaOrden
	 */
	public Date getFechaOrden() {
		return fechaOrden;
	}
	/**
	 * @param fechaOrden the fechaOrden to set
	 */
	public void setFechaOrden(Date fechaOrden) {
		this.fechaOrden = fechaOrden;
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
	/**
	 * @return the idImportador
	 */
	public int getIdImportador() {
		return idImportador;
	}
	/**
	 * @param idImportador the idImportador to set
	 */
	public void setIdImportador(int idImportador) {
		this.idImportador = idImportador;
	}
	/**
	 * @return the idExportador
	 */
	public int getIdExportador() {
		return idExportador;
	}
	/**
	 * @param idExportador the idExportador to set
	 */
	public void setIdExportador(int idExportador) {
		this.idExportador = idExportador;
	}
}
