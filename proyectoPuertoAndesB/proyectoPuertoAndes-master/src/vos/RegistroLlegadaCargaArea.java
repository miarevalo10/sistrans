package vos;

import org.codehaus.jackson.annotate.JsonProperty;

import dateUtilities.DateParser;

import org.codehaus.jackson.annotate.*;

import java.text.ParseException;
import java.util.Date;

import javax.swing.text.html.HTMLDocument.HTMLReader.ParagraphAction;

/**
 * Clase contenedora que representa el registro  de un llegada de una carga a un area especifica
 */
public class RegistroLlegadaCargaArea {
	 @JsonProperty(value="id-carga")
	 private int idCarga;
	 @JsonProperty(value="id-area")
	 private int idArea;
	 @JsonProperty(value="viene-de")
	 private String vieneDe;
	 @JsonProperty(value="fecha-llegada")
	 private String fechaLlegadaString;
	 @JsonProperty(value="fecha-Salida")
	 private String fechaSalidaString;
	 @JsonProperty(value="id-importador")
	 private int idImportador;
	 
	 Date fechaLlegada;
	 Date fechaSalida;
	 
	 /**
    * Método constructor de la clase RegistroLlegadaCargaArea
    * <b>post: </b> Crea un objeto de tipo RegistroLlegadaCargaArea, que representa los objetos necesarios
    * para registrar el arribo de un carga en un area especifica
    * 
    */
   public RegistroLlegadaCargaArea(
           @JsonProperty(value="id-carga") int idCarga,
           @JsonProperty(value="id-area") int idArea,
           @JsonProperty(value="viene-de") String vieneDe,
           @JsonProperty(value="fecha-llegada") String fechaIngresoString,
           @JsonProperty(value="fecha-salida") String fechaSalidaString,
           @JsonProperty(value="id-importador") int idImportador
   ) {
       this.idCarga = idCarga;
       this.idArea = idArea;
       this.vieneDe = vieneDe;
       this.fechaLlegadaString = fechaIngresoString;
       this.fechaSalidaString = fechaSalidaString;
       this.idImportador = idImportador;
       
       // parseamos los strings de las fecha a util.Date
       
       try {
         this.fechaLlegada = DateParser.fromStringToDate(fechaLlegadaString);
         
         } 
         
         catch (Exception e){
        	 System.out.println("Erroe parseando fechaLLegadaString en constructor");
         	e.printStackTrace();
         }
         
         try {
         	this.fechaSalida = DateParser.fromStringToDate(fechaSalidaString);
         } catch (Exception e){
         	this.fechaSalida = null;
         	System.out.println("error parseando la fecha de salida. (Puede que sea normal) No es error si es registro de ingerso");
         }

   }
   
   public int getIdCarga(){
  	 return this.idCarga;
   }

	public int getIdArea() {
		return this.idArea;
	}

	public int getIdImportador() {
		return this.idImportador;
	}
   
  public String getFechaLlegadaString(){
  	return this.fechaLlegadaString;
  }
  
public String getFechaSalidaString(){
  	return this.fechaSalidaString;
  }

public String getVieneDe(){
		return this.vieneDe;
}
	
public Date getFechaLLegada(){
	return this.fechaLlegada;
}

public Date getFechaSalida(){
	return this.fechaSalida;
}

	// los metodos de set para despues

	
	
}
