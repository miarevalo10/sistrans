package rest;


import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;

import dateUtilities.DateParser;
import tm.PuertoAndesMaster;
import vos.*;

@Path("buque")
public class BuqueServices {
	/**
     * Atributo que usa la anotación @Context para tener el ServletContext de la conexión actual.
     */
    @Context
    private ServletContext context;

    /**
     * Método que retorna el path de la carpeta WEB-INF/ConnectionData en el deploy actual dentro del servidor.
     * @return path de la carpeta WEB-INF/ConnectionData en el deploy actual.
     */
    private String getPath() {
        return context.getRealPath("WEB-INF/ConnectionData");
    }


    private String doErrorMessage(Exception e){
        return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
    }
    
    //RFC1
    
    @GET
    @Path("/consultaActividadBuque")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultaActividadBuques(int porCapacidad, int capacidadMenor, int capacidadMayor, int porTipoBuque, int porPortacontenedor, int porMultiproposito, int porRoro, int porRangoFecha, String fechaMenor, String fechaMayor, int porAgrupamiento, int porOrdenamiento) throws SQLException {
    	System.out.println("Entro :)");
        PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
        boolean filtroCapacidad = false;
        boolean filtroTipoBuque = false;
        boolean filtroRangoFecha = false;
        boolean filtroAgrupamiento = false;
        boolean filtroOrdenamiento = false;
        ArrayList<Buque> resultado = null;
        if(porCapacidad==1&&capacidadMayor>capacidadMenor){
        	filtroCapacidad=true;
        }
        if(porTipoBuque==1&&((porMultiproposito==1&&porPortacontenedor==0&&porRoro==0)||(porMultiproposito==0&&porPortacontenedor==1&&porRoro==0)||porMultiproposito==0&&porPortacontenedor==0&&porRoro==1)){
        	filtroTipoBuque=true;
        }
        if(porRangoFecha==1){
        	filtroRangoFecha=true;
        }
        if(porAgrupamiento==1){
        	filtroAgrupamiento=true;
        }
        if(porOrdenamiento==1){
        	filtroOrdenamiento=true;
        }
        if((filtroCapacidad||filtroTipoBuque||filtroRangoFecha)&&(!(filtroAgrupamiento||filtroOrdenamiento)||(filtroAgrupamiento||filtroOrdenamiento))){
        	//resultado = tm.consultaActividad(porCapacidad,capacidadMenor,capacidadMayor,porTipoBuque,porPortacontenedor,porMultiproposito,porRoro,porRangoFecha,fechaMenor,fechaMayor,porAgrupamiento,porOrdenamiento);
        }
        
        return Response.status(200).entity(resultado).build();
    }
    
}
