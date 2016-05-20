package vos;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.*;

import java.text.ParseException;
import java.util.Date;

import javax.swing.text.html.HTMLDocument.HTMLReader.ParagraphAction;

import dateUtilities.*;

/**
 * Clase contenedora (Wrapper class/ object) de los objetos necesarios para registrar la deshabilitacion de un buque
 */
public class RegistroDeshabilitacionBuque {

	/**
   *  id del Buque general para registrar movimiento
   */
  @JsonProperty(value="id-buque")
  int idBuque;

  /**
   * fecha string de la deshabilitacion
   */
  @JsonProperty(value="fecha-deshabilitacion")
  String fechaDeshabilitacionString;

  Date fechaDeshabilitacion;
  
  /**
   * Motivo de la des
   */
  @JsonProperty(value="motivo")
  String motivo;
  
  
  /**
   * Método constructor de la clase RegistroDeshabilitacionBuque
   * <b>post: </b> Crea un objeto de tipo RegistroDeshabilitacionBuque, que representa los objetos necesarios
   * para registrar la deshabilitacion de un buque en el puerto
   * @param id buque - id Buque que arriba en el puerto
   * @param fechaDescarga - fecha en que descarga el buque
   */
  public RegistroDeshabilitacionBuque(
          @JsonProperty(value="id-buque") int idBuque,
          @JsonProperty(value="fecha-deshabilitacion")  String fechaDeshabilitacionString,
          @JsonProperty(value="motivo") String motivo
  ) {
      this.idBuque = idBuque;
      this.fechaDeshabilitacionString = fechaDeshabilitacionString;
      this.motivo = motivo;
      try {
      this.fechaDeshabilitacion = DateParser.fromStringToDate(fechaDeshabilitacionString);
      
      } 
      
      catch (ParseException e){
      	System.out.println("Error parseando la fecha de la deshabilitacion. Revisar el formato");
      	e.printStackTrace();
      }
      
      
  }


	public int getIdBuque() {
		return idBuque;
	}


	public void setIdBuque(int idBuque) {
		this.idBuque = idBuque;
	}


	public String getFechaDeshabilitacionString() {
		return fechaDeshabilitacionString;
	}


	public void setFechaDeshabilitacionString(String fechaDeshabilitacionString) {
		this.fechaDeshabilitacionString = fechaDeshabilitacionString;
	}


	public Date getFechaDeshabilitacion() {
		return fechaDeshabilitacion;
	}


	public void setFechaDeshabilitacion(Date fechaDeshabilitacion) {
		this.fechaDeshabilitacion = fechaDeshabilitacion;
	}


	public String getMotivo() {
		return motivo;
	}


	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
}
