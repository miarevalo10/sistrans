package vos;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.*;

import java.text.ParseException;
import java.util.Date;

import javax.swing.text.html.HTMLDocument.HTMLReader.ParagraphAction;

import dateUtilities.*;
/**
 * Clase contenedora (Wrapper class/ object) de los objetos necesarios para hacer
 */

public class RegistroEntregaCargaImportador {
	
	@JsonProperty(value="id-carga")
  int idCarga;
  @JsonProperty(value="tipo-carga")
  String tipoCarga;
  @JsonProperty(value="volumen-carga")
  int volumenCarga;
  @JsonProperty(value="id-area")
  int idArea;
  @JsonProperty(value="id-importador")
  int idImportador;
  @JsonProperty(value="fecha-entrega")
  String fechaEntregaString;
  
  Date fechaEntrega;
  /**
   * Método constructor de la clase RegistroEntregaCargaImportador
   * <b>post: </b> Crea un objeto de tipo registroEntregaCargaImportador, que representa los objetos necesarios
   * para registrar la entrega de una carga a un importador en el puerto
   * 
   */
  public RegistroEntregaCargaImportador(
         
          @JsonProperty(value="id-carga") int idCarga,
          @JsonProperty(value="tipo-carga") String tipoCarga,
          @JsonProperty(value="volumen-carga") int volumenCarga,
          @JsonProperty(value="id-area") int idArea,
          @JsonProperty(value="id-importador")  int idImportador,
          @JsonProperty(value="fecha-entrega") String fechaEntregaString
  ) {
      this.idCarga = idCarga;
      this.tipoCarga = tipoCarga;
      this.volumenCarga = volumenCarga;
      this.idArea = idArea;
      this.idImportador = idImportador;
      this.fechaEntregaString = fechaEntregaString;
      
      try {
        this.fechaEntrega = DateParser.fromStringToDate(fechaEntregaString);
        
        } 
        
        catch (ParseException e){
        	e.printStackTrace();
        }

  }
  
  public void setIdCarga (int idCarga){
  	this.idCarga = idCarga;
  }
  
  public void setTipoCarga (String tipoCarga){
  	this.tipoCarga = tipoCarga;
  }
  
  public void setVolumenCarga (int volumenCarga){
  	this.volumenCarga = volumenCarga;
  }
  
  public void setIdArea (int idArea){
  	this.idArea = idArea;
  }
  
  public void setIdImportador (int idImportador){
  	this.idImportador = idImportador;
  }
  
  public void setFechaEntregaString( String fechaEntregaString){
  	this.fechaEntregaString = fechaEntregaString;
  }
  public int getIdCarga (){
  	return this.idCarga;
  }
	public int getIdArea (){
		return this.idArea;
	}
	public int getVolumenCarga (){
		return this.volumenCarga;
	}
	public int getIdImportador (){
		return this.idImportador;
	}
  public String getFechaEntregaString(){
  	return this.fechaEntregaString;
  }
  
  public Date getFechaEntrega(){
  	return this.fechaEntrega;
  }
  
  
  
	
	

}



   