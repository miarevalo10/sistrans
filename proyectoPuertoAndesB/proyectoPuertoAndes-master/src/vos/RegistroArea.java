package vos;

import java.text.ParseException;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

import dateUtilities.DateParser;

public class RegistroArea {
	@JsonProperty(value="id-carga")
    int idCarga;
	
	@JsonProperty(value="id-area")
    int idArea;
	
	@JsonProperty(value="viene-de")
    String vieneDe;
	
	@JsonProperty(value="fecha-ingreso")
    String fechaIngresoString;

	@JsonProperty(value="fecha-salida")
    String fechaSalidaString;
	
	@JsonProperty(value="estado")
    private String estado;
    
    Date fechaIngreso;
    
    Date fechaSalida;

    public RegistroArea(
    		@JsonProperty(value="id-carga") int idCarga,
            @JsonProperty(value="id-area")  int idArea,
            @JsonProperty(value="viene-de")  String vieneDe,
            @JsonProperty(value="fecha-ingreso")  String fechaIngreso,
            @JsonProperty(value="fecha-salida")  String fechaSalida,
            @JsonProperty(value="estado")  String estado
        		){
    	this.idCarga=idCarga;
    	this.idArea=idArea;
    	this.vieneDe=vieneDe;
    	try {
    		this.fechaIngreso = DateParser.fromStringToDate(fechaIngreso);
    	} 
    	catch (ParseException e){
    		e.printStackTrace();
    	}
    	try {
    		this.fechaSalida = DateParser.fromStringToDate(fechaSalida);
    	} 
    	catch (ParseException e){
    		e.printStackTrace();
    	}
    	this.setEstado(estado);
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
	 * @return the idArea
	 */
	public int getIdArea() {
		return idArea;
	}

	/**
	 * @param idArea the idArea to set
	 */
	public void setIdArea(int idArea) {
		this.idArea = idArea;
	}

	/**
	 * @return the vieneDe
	 */
	public String getVieneDe() {
		return vieneDe;
	}

	/**
	 * @param vieneDe the vieneDe to set
	 */
	public void setVieneDe(String vieneDe) {
		this.vieneDe = vieneDe;
	}

	/**
	 * @return the fechaIngreso
	 */
	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	/**
	 * @param fechaArribo the fechaArribo to set
	 */
	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	/**
	 * @return the fechaSalida
	 */
	public Date getFechaSalida() {
		return fechaSalida;
	}

	/**
	 * @param fechaSalida the fechaSalida to set
	 */
	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}
	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
}
