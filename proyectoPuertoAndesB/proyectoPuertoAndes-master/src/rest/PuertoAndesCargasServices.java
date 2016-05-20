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

@Path("cargas")
public class PuertoAndesCargasServices {
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
    
    
    /*@PUT
    @Path("/operador/registro_entrega_carga_importador")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarTipoCargaEnBuque(int idCarga, int idBuque, String fecha) throws Exception {
        PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
        Carga c = tm.buscarCargaPorId(idCarga);
        Buque b = tm.buscarBuquePorId(idBuque);
        Date f = (Date) DateParser.fromStringToDate(fecha);
        try {
            tm.registrarTipoCargaEnBuque(c,b,f);
        } catch (Exception e) {
          System.out.println("error de tm");
          return Response.status(500).entity(doErrorMessage(e)).build();
        }
        return Response.status(200).entity(c).build();
    }
    */
    
    // RF7
    
    @POST
    @Path("/operador/registro_llegada_carga_area")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarLlegadaCargaArea (RegistroLlegadaCargaArea registroLlegada){
    	PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
      try {
          tm.registrarLlegadaCargaArea(registroLlegada);
      } catch (Exception e) {
        System.out.println("error de tm");
        return Response.status(500).entity(doErrorMessage(e)).build();
      }
      return Response.status(200).entity(registroLlegada).build();
    }
    
    
    //RF8
    
    @PUT
    @Path("/operador/registro_entrega_carga_importador")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarEntregaCargaImportador (RegistroEntregaCargaImportador registroEntregaCargaImportador) throws Exception {
        PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
        try {
            tm.registrarEntregaCargaImportador(registroEntregaCargaImportador);
        } catch (Exception e) {
          System.out.println("error de tm");
          return Response.status(500).entity(doErrorMessage(e)).build();
        }
        return Response.status(200).entity(registroEntregaCargaImportador).build();
    }

    //RFC5
    
    @POST // get
    @Path("/consultar-movimientos-carga")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultarMovimientosCarga(ConsultaMovimientosCarga consulta) {
        PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
        ArrayList<RespuestaConsultaMovimientos> respuestas;
        try {
         respuestas = tm.consultarMovimientosCarga(consulta);
      } catch (Exception e) {
          return Response.status(500).entity(doErrorMessage(e)).build();
      }
      return Response.status(200).entity(respuestas).build();
    }
    
  
    
}