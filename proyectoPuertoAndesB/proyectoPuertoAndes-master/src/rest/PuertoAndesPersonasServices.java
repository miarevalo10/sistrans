package rest;


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

import tm.PuertoAndesMaster;
import vos.*;

@Path("personas")
public class PuertoAndesPersonasServices {
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


  // servicios de tipo REST
  
  
  /**
   * Método que expone servicio REST usando PUT que agrega el importador que recibe en Json
   * <b>URL: </b> http://"ip o nombre de host":8080/PuertoAndes2/rest/personas/importador
   * @param video - importador a agregar
   * @return Json con el importador que agrego o Json con el error que se produjo
   */
  @POST
  @Path("/importador")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response addImportador(Importador importador) {
      PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
      try {
          tm.addImportador(importador);
      } catch (Exception e) {
          return Response.status(500).entity(doErrorMessage(e)).build();
      }
      return Response.status(200).entity(importador).build();
  }

  /**
   * Método que expone servicio REST usando PUT que agrega el exportador que recibe en Json
   * <b>URL: </b> http://"ip o nombre de host":8080/PuertoAndes2/rest/personas/importador
   * @param video - exportador a agregar
   * @return Json con el exportador que agrego o Json con el error que se produjo
   */
  @POST
  @Path("/exportador")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response addExportador(Exportador exportador) {
      PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
      try {
          tm.addExportador(exportador);
      } catch (Exception e) {
          return Response.status(500).entity(doErrorMessage(e)).build();
      }
      return Response.status(200).entity(exportador).build();
  }
  
  
  /**
   * Método que expone servicio REST usando POST que agrega el registrode la fcatura que recibe en Json
   * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/
   * @param video - exportador a agregar
   * @return Json con el exportador que agrego o Json con el error que se produjo
   */
  @POST
  @Path("/admin/factura_exportador")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response addRegistroFacturaExportador(RegistroFacturaExportador registroFacturaExportador) {
      PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
      try {
          tm.addRegistroFactura(registroFacturaExportador);
      } catch (Exception e) {
          return Response.status(500).entity(doErrorMessage(e)).build();
      }
      return Response.status(200).entity(registroFacturaExportador).build();
  }
  
  
  /**
   * Método que expone servicio REST usando PUT que hace la consulta del exportador de la fcatura que recibe en Json
   * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/
   * @param consultaExportador -consultaExportador
   * @return Json con la consulta del exportador consultaExportador
   */
  @POST
  @Path("/admin/consulta-exportador")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response getConsultaExportador(RegistroFacturaExportador registroFacturaExportador) {
      PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
      try {
          tm.addRegistroFactura(registroFacturaExportador);
      } catch (Exception e) {
          return Response.status(500).entity(doErrorMessage(e)).build();
      }
      return Response.status(200).entity(registroFacturaExportador).build();
  }
  
  
  
}
