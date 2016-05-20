	package vos;

import org.codehaus.jackson.annotate.JsonProperty;

import java.sql.Date;


/**
 * Clase contenedora que representa el registro  de un factura con un buque y facha de genreacion especifica
 */
public class RegistroFacturaExportador {

    /*Atributos de la clase contenedora*/


    /**
     * id Buque general para registrar en la factura
     */
    @JsonProperty(value="id-buque-factura")
    int idBuqueGeneralFactura;

    /**
     * id de la carga para registrar en la factura
     */
    @JsonProperty(value="id-carga-factura")
    int idCargaFactura;


    /**
     * Date de la factura
     */
    @JsonProperty(value="fecha-factura")
    Date fechaFactura;
    
    /**
     * id del exportador
     */
    @JsonProperty(value="id-exportador")
    int idExportador;
    



    /**
     * Método constructor de la clase RegistroArribo
     * <b>post: </b> Crea un objeto de tipo registroArribo, que representa los objetos necesarios
     * para registrar el arribo de un buque en el puerto
     * @param idBuqueGeneralFactura - id Buque que arriba en el puerto
     * @param idCargaFactura - id de la carga de la factura
     * @param fechaFactura - fecha en que arriba el buque
     */
    public RegistroFacturaExportador(
            @JsonProperty(value="id-buque-factura") int idBuqueGeneralFactura,
            @JsonProperty(value="id-carga-factura") int idCargaFactura,
            @JsonProperty(value="id-exportador") int idExportador,
            @JsonProperty(value="fecha-factura")  Date fechaFactura
    ) {
        this.idBuqueGeneralFactura = idBuqueGeneralFactura;
        this.idCargaFactura = idCargaFactura;
        this.fechaFactura = fechaFactura;
        this.idExportador = idExportador;

    }

    /**
     * Método getter idBuqueGeneralFactura
     * @return idBuqueGeneralFactura
     */
    public int getIdBuqueGeneralFactura() {
        return this.idBuqueGeneralFactura;
    }


    /**
     * Método getter idCargaFactura
     * @return idCargaFactura
     */
    public int getIdCargaFacturaFactura() {
        return this.idCargaFactura;
    }



    /**
     * Método getter fechaFactura
     * @return fechaFactura
     */
    public Date getFechaFactura() {
        return this.fechaFactura;
    }


    /**
     * Método setter del atributo buqueGeneral <b>post: </b> buqueGeneral que arriba
     * ha sido cambiado con el valor que entra como parámetro
     * @param idBuqueGeneralFactura - buqueGeneral que arriba.
     */
    public void setIdBuqueGeneralFactura(int  idBuqueGeneralFactura) {
        this.idBuqueGeneralFactura = idBuqueGeneralFactura;
    }

    /**
     * Método setter del atributo buqueGeneral <b>post: </b> carga
     * ha sido cambiado con el valor que entra como parámetro
     * @param idCargaFactura - buqueGeneral que arriba.
     */
    public void setIdCargaFactura(int  idCargaFactura) {
        this.idCargaFactura = idCargaFactura;
    }


    /**
     * Método setter del atributo fechaFactura <b>post: </b> fecha de generacion de la factura
     * ha sido cambiado con el valor que entra como parámetro
     * @param fechaFactura - fechaFactura del buque.
     */
    public void setFechaFactura(Date fechaFactura) {
        this.fechaFactura = fechaFactura;
    }
    
    
    // exportador
    
    public void setIdExportador(int  idExportador) {
      this.idExportador = idExportador;
    }
    
    public int getIdExportador (){
    	return this.idExportador;
    }
    
    

}
