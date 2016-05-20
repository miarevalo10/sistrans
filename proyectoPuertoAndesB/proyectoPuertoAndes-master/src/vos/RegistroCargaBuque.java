package vos;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.*;

import java.text.ParseException;
import java.util.Date;

import javax.swing.text.html.HTMLDocument.HTMLReader.ParagraphAction;

import dateUtilities.*;

/**
 * Clase contenedora (Wrapper class/ object) de los objetos necesarios para hacer la carga de un buque
 */
public class RegistroCargaBuque {
  /**
   *  id de la Carga general para registrar movimiento
   */
  @JsonProperty(value="id-carga")
  public int idCarga;
  
  /**
   *  
   */
  @JsonProperty(value="id-buque")
  public int idBuque;

  /**
   *  
   */
  @JsonProperty(value="fecha-salida")
  public Date fechaSalida;

  /**
   *  
   */
  @JsonProperty(value="viene-de")
  public String vieneDe;
  

  /**
   * Método constructor de la clase RegistroCargaBuque
   * <b>post: </b> Crea un objeto de tipo RegistroCargaBuque, que representa los objetos necesarios
   * para registrar la descarga de un buque en el puerto
   * @param id buque - id Buque que arriba en el puerto
   * @param fechaDescarga - fecha en que descarga el buque
   */
  	public RegistroCargaBuque(
  			@JsonProperty(value="id-carga") int idCarga,
  			@JsonProperty(value="id-buque") int idBuque,
  			@JsonProperty(value="fecha-salida") Date fechaSalida,
  			@JsonProperty(value="viene-de") String vieneDe
  			){
  			this.idCarga=idCarga;
  			this.idBuque=idBuque;  		
  			this.fechaSalida = fechaSalida;
  			this.vieneDe=vieneDe;
  			this.idBuque=idBuque;
  	}


	public int getIdCarga() {
		return idCarga;
	}


	public void setIdCarga(int idCarga) {
		this.idCarga = idCarga;
	}


	public int getIdBuque() {
		return idBuque;
	}


	public void setIdBuque(int idBuque) {
		this.idBuque = idBuque;
	}


	public Date getFechaSalida() {
		return fechaSalida;
	}


	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}


	public String getVieneDe() {
		return vieneDe;
	}


	public void setVieneDe(String vieneDe) {
		this.vieneDe = vieneDe;
	}
}
