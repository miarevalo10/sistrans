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
public class RegistroArriboOSalida {

    /*Atributos de la clase contenedora*/


    /**
     *  id del Buque general para registrar movimiento
     */
    @JsonProperty(value="id-buque")
    int idBuque;

    /**
     * Date del arribo
     */
    @JsonProperty(value="fecha-arribo")
    String fechaArriboString;

    /**
     * Date de salida
     */
    @JsonProperty(value="fecha-salida")
    String fechaSalidaString;
    
    Date fechaArribo;
    Date fechaSalida;
    
    @JsonProperty(value="nombre-buque")
    String nombreBuque;



    /**
     * Método constructor de la clase RegistroArribo
     * <b>post: </b> Crea un objeto de tipo registroArribo, que representa los objetos necesarios
     * para registrar el arribo de un buque en el puerto
     * @param buqueGeneral - Buque que arriba en el puerto
     * @param fechaArribo - fecha en que arriba el buque
     * @param fechaSalida - fecha en que sale el buque
     */
    public RegistroArriboOSalida(
            @JsonProperty(value="id-buque") int idBuque,
            @JsonProperty(value="fecha-arribo")  String fechaArribo,
            @JsonProperty(value="fecha-salida")  String fechaSalida
    ) {
        this.idBuque = idBuque;
        
        try {
        this.fechaArribo = DateParser.fromStringToDate(fechaArribo);
        
        } 
        
        catch (ParseException e){
        	e.printStackTrace();
        }
        
        try {
        	this.fechaSalida = DateParser.fromStringToDate(fechaSalida);
        } catch (ParseException e){
        	this.fechaSalida = null;
        	System.out.println("error parseando la fecha de salida. No es error si es registro de ingerso");
        }
    }
    
    
    
    public RegistroArriboOSalida(
    		@JsonProperty(value="id-buque") int idBuque,
        @JsonProperty(value="fecha-arribo")  String fechaArribo,
        @JsonProperty(value="fecha-salida")  String fechaSalida,
        @JsonProperty(value="nombre-buque") String nombreBuque
    		){
    	this.idBuque = idBuque;
    	this.nombreBuque = nombreBuque;
      
      try {
      this.fechaArribo = DateParser.fromStringToDate(fechaArribo);
      
      } 
      
      catch (ParseException e){
      	e.printStackTrace();
      }
      
      try {
      	this.fechaSalida = DateParser.fromStringToDate(fechaSalida);
      } catch (ParseException e){
      	this.fechaSalida = null;
      	System.out.println("error parseando la fecha de salida. No es error si es registro de ingerso");
      }
    }
    
    
    

    /**
     * Método getter buqueGeneral
     * @return buqueGeneral
     */
    public int getIdBuque() {
        return this.idBuque;
    }


    /**
     * Método getter fechaArribo
     * @return fechaArribo
     */
    public Date getFechaArribo() {
        return this.fechaArribo;
    }

    /**
     * Método getter fechaSalida
     * @return fechaSalida
     */
    public Date getFechaSalida() {
        return this.fechaSalida;
    }

    /**
     * Método setter del atributo id de buque <b>post: </b> id del buque que arriba o sale
     * ha sido cambiado con el valor que entra como parámetro
     * @param buqueGeneral - buqueGeneral que arriba o sale.
     */
    public void setIdBuque(int idBuque) {
        this.idBuque = idBuque;
    }

    /**
     * Método setter del atributo fechaArribo <b>post: </b> fecha del arribo
     * ha sido cambiado con el valor que entra como parámetro
     * @param fechaArribo - fechaArribo del buque.
     */
    public void setFechaArribo(Date fechaArribo) {
        this.fechaArribo = fechaArribo;
    }

    /**
     * Método setter del atributo fechaSalida <b>post: </b> fecha de salida
     * ha sido cambiado con el valor que entra como parámetro
     * @param fechaSalida - fechaSalida del buque.
     */
    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }







}
