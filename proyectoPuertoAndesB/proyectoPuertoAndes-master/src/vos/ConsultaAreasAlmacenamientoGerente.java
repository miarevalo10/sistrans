package vos;
import org.codehaus.jackson.annotate.JsonProperty;

import dateUtilities.DateParser;

import org.codehaus.jackson.annotate.*;

import java.text.ParseException;
import java.util.Date;

import javax.swing.text.html.HTMLDocument.HTMLReader.ParagraphAction;

/**
 * Clase contenedora que representa la consulta de las areas de almacenamiento por varios filtros. 
 */
public class ConsultaAreasAlmacenamientoGerente {

	 @JsonProperty(value="filtrar-estado")
	 private int filtrarEstado;
	 @JsonProperty(value="estado")
	 private String estado;
	 @JsonProperty(value="fitrar-fechas")
	 private int filtrarFechas;
	 @JsonProperty(value="fecha-inicio")
	 private String fechaInicioString;
	 @JsonProperty(value="fecha-fin")
	 private String fechaFinString;
	 
	 Date fechaInicio;
	 Date fechaFin;
	 
	 /**
    * Método constructor de la clase RegistroInicioCargaArea
    * <b>post: </b> Crea un objeto de tipo RegistroInicioCargaArea, que representa los objetos necesarios
    * para registrar el arribo de un carga en un area especifica
    * 
    */
   public ConsultaAreasAlmacenamientoGerente (
  		     @JsonProperty(value="filtrar-estado") int filtrarEstado,
  		     @JsonProperty(value="estado") String estado,
  		     @JsonProperty(value="fitrar-fechas") int filtrarFechas,
  		     @JsonProperty(value="fecha-inicio") String fechaInicioString,
  		     @JsonProperty(value="fecha-fin")String fechaFinString
   ) throws Exception  {
       if(filtrarEstado == 0 || filtrarEstado ==1){
      	 this.filtrarEstado = filtrarEstado;
       }
       else {
      	 throw new Exception ("Filtro de estado invalido: el filtro del estado no es 1 o 0. ");
       }
       	
       this.estado = estado;
       
       if(filtrarFechas == 0 || filtrarFechas == 1){
      	 this.filtrarFechas = filtrarFechas;
       }
       else {
      	 throw new Exception ("Filtro de fechas invalido: el filtro de fechas no es 1 o 0");
       }
       this.fechaInicioString = fechaInicioString;
       this.fechaFinString = fechaFinString;
       
       // parseamos los strings de las fecha a util.Date
       
       try {
         this.fechaInicio = DateParser.fromStringToDate(fechaInicioString);
         
         } 
         
         catch (Exception e){
        	 System.out.println("Erroe parseando fechaLLegadaString en constructor");
         	//e.printStackTrace();
         }
         
         try {
         	this.fechaFin = DateParser.fromStringToDate(fechaFinString);
         } catch (Exception e){
         	this.fechaFin = null;
         	System.out.println("error parseando la fecha de salida. (Puede que sea normal) No es error si es registro de ingerso");
         }

   }

	public int getFiltrarEstado() {
		return filtrarEstado;
	}

	public void setFiltrarEstado(int filtrarEstado) {
		this.filtrarEstado = filtrarEstado;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public int getFiltrarFechas() {
		return filtrarFechas;
	}

	public void setFiltrarFechas(int filtrarFechas) {
		this.filtrarFechas = filtrarFechas;
	}

	public String getFechaInicioString() {
		return fechaInicioString;
	}

	public void setFechaInicioString(String fechaInicioString) {
		this.fechaInicioString = fechaInicioString;
	}

	public String getFechaFinString() {
		return fechaFinString;
	}

	public void setFechaFinString(String fechaFinString) {
		this.fechaFinString = fechaFinString;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	 
	 
}
