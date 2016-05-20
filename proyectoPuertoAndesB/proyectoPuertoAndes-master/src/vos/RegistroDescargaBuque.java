package vos;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.*;

import java.text.ParseException;
import java.util.Date;

import javax.swing.text.html.HTMLDocument.HTMLReader.ParagraphAction;

import dateUtilities.*;

/**
 * Clase contenedora (Wrapper class/ object) de los objetos necesarios para hacer la descarga de un buque
 */
public class RegistroDescargaBuque {
	
	/*Atributos de la clase contenedora*/
  /**
   *  id del Buque general para registrar movimiento
   */
  @JsonProperty(value="id-buque")
  int idBuque;

  /**
   * Date de la descarga
   */
  @JsonProperty(value="fecha-descarga")
  String fechaDescargaString;

  Date fechaDescarga;
  
 
  
  /**
   * Método constructor de la clase RegistroDescargaBuque
   * <b>post: </b> Crea un objeto de tipo RegistroDescargaBuque, que representa los objetos necesarios
   * para registrar la descarga de un buque en el puerto
   * @param id buque - id Buque que arriba en el puerto
   * @param fechaDescarga - fecha en que descarga el buque
   */
  public RegistroDescargaBuque(
          @JsonProperty(value="id-buque") int idBuque,
          @JsonProperty(value="fecha-descarga")  String fechaDescargaString
  ) {
      this.idBuque = idBuque;
      
      try {
      this.fechaDescarga = DateParser.fromStringToDate(fechaDescargaString);
      
      } 
      
      catch (ParseException e){
      	System.out.println("Error parseando la fecha de la descarga. Revisar el formato");
      	e.printStackTrace();
      }
      
      
  }


	public int getIdBuque() {
		return idBuque;
	}


	public void setIdBuque(int idBuque) {
		this.idBuque = idBuque;
	}


	public String getFechaDescargaString() {
		return fechaDescargaString;
	}


	public void setFechaDescargaString(String fechaDescargaString) {
		this.fechaDescargaString = fechaDescargaString;
	}


	public Date getFechaDescarga() {
		return fechaDescarga;
	}


	public void setFechaDescarga(Date fechaDescarga) {
		this.fechaDescarga = fechaDescarga;
	}


	
  
  
  
}
