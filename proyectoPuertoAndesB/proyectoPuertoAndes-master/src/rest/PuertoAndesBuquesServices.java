	package rest;
	
	import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletContext;
	import javax.ws.rs.Consumes;
	import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
	import javax.ws.rs.POST;
	import javax.ws.rs.PUT;
	import javax.ws.rs.Path;
	import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
	import javax.ws.rs.core.MediaType;
	import javax.ws.rs.core.Response;
	
	import org.codehaus.jackson.map.ObjectMapper;
	
	import tm.PuertoAndesMaster;
	import vos.*;
	
	@Path("buques")
	public class PuertoAndesBuquesServices {
			
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
		   * Método que expone servicio REST usando PUT que agrega el buque que recibe en Json
		   * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/
		   * @param buque - buque a agregar
		   * @return Json con el buque que agrego o Json con el error que se produjo
		   */
		  @POST
		  @Consumes(MediaType.APPLICATION_JSON)
		  @Produces(MediaType.APPLICATION_JSON)
		  public Response addBuque(BuqueGeneral buque) {
		      PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		      try {
		          tm.addBuque(buque);
		      } catch (Exception e) {
		        System.out.println("error de tm");
		        return Response.status(500).entity(doErrorMessage(e)).build();
		      }
		      return Response.status(200).entity(buque).build();
		  }
		  
		  /**
		   * Método que expone servicio REST usando PUT que agrega el ingreso buque que recibe en Json
		   * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/
		   * @param registroAS - delbuque a agregar
		   * @return Json con el registroAS que agrego o Json con el error que se produjo
		   */
		  @POST
		  @Path("/ingreso-buque")
		  @Consumes(MediaType.APPLICATION_JSON)
		  @Produces(MediaType.APPLICATION_JSON)
		  public Response addRegistroIngresoBuque(RegistroArriboOSalida registroAS) {
		      PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		      try {
		          tm.addRegistroArriboBuque(registroAS);
		      } catch (Exception e) {
		        System.out.println("error de tm");
		        return Response.status(500).entity(doErrorMessage(e)).build();
		      }
		      return Response.status(200).entity(registroAS).build();
		  }
		  
		  /**
		   * Método que expone servicio REST usando PUT que agrega la salida buque que recibe en Json
		   * <b>URL: </b> http://"ip o nombre de host":8080/PuertoAndes/rest/buques/salida-buque
		   * @param registroAS -  del buque a agregar
		   * @return Json con el registroAS que agrego o Json con el error que se produjo
		   */
		  @PUT
		  @Path("/salida-buque")
		  @Consumes(MediaType.APPLICATION_JSON)
		  @Produces(MediaType.APPLICATION_JSON)
		  public Response addRegistroSalidaBuque(RegistroArriboOSalida registroAS) {
		      PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		      try {
		          tm.addRegistroSalidaBuque(registroAS);
		      } catch (Exception e) {
		        System.out.println("error de tm");
		        return Response.status(500).entity(doErrorMessage(e)).build();
		      }
		      return Response.status(200).entity(registroAS).build();
		  }
		  
		  /**
		   * Método que expone servicio REST usando POST que agrega la DESCARGA DEL buque que recibe en Json
		   * <b>URL: </b> http://"ip o nombre de host":8080/PuertoAndes/rest/buques/descarga-buque
		   * @param registroDescargaBuque -  registro de la descarga del buque a agregar
		   * @return Json con el registroDescargaBuque que agrego o Json con el error que se produjo
		   */
		  
		 @POST
		 @Path("/descarga-buque")
		 @Consumes(MediaType.APPLICATION_JSON)
		 @Produces(MediaType.APPLICATION_JSON)
		 public Response addregistroDescargaBuque(RegistroDescargaBuque resDescarga){
			 PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		   try {
		       tm.addRegistroDescargaBuque(resDescarga);
		   } catch (Exception e) {
		     System.out.println("error de tm");
		     return Response.status(500).entity(doErrorMessage(e)).build();
		   }
		   return Response.status(200).entity(resDescarga).build();
		 }
		  
		 //RF6
		 /**
		  * Método que expone servicio REST usando POST que carga una CARGA de un tipo en un BUQUE de la informacion recibida en el JSON
		  * <b>URL: </b> http://"ip o nombre de host":8080/PuertoAndes/rest/buques/carga-buque
		  * @param registroCargaBuque -  registro de la Carga en un Buque
		  * @return Json con el registroDescargaBuque que agrego o Json con el error que se produjo
		  */
		 
		@POST
		@Path("/registro-carga-buque")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response addregistroCargaBuque(RegistroCargaBuque registroCargaBuque){
			System.out.println("Entra al servicio rest");
			 PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		  try {
		      tm.addRegistroCargaBuque(registroCargaBuque);
		  } catch (Exception e) {
		    System.out.println("error de tm");
		    return Response.status(500).entity(doErrorMessage(e)).build();
		  }
		  return Response.status(200).entity(registroCargaBuque).build();
		}
		
		//RF10
		
		@POST
		@Path("/carga-buque")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response cargarBuque(Integer idBuque){ // falta arreglar este metodo, NO PUEDE RECIBIR EL ENTERO......!!
			PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
			 try {
			     tm.cargarBuque(idBuque);
			 } catch (Exception e) {
			   System.out.println("error de tm");
			   return Response.status(500).entity(doErrorMessage(e)).build();
			 }
			 return Response.status(200).entity(idBuque).build();
		}
		
		@POST
		@Path("/deshabilitar-buque")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response deshabilitararBuque(RegistroDeshabilitacionBuque registroDeshabilitacionBuque){
			PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
			 try {
			     tm.deshabilitarBuque(registroDeshabilitacionBuque);
			 } catch (Exception e) {
			   System.out.println("error de tm");
			   return Response.status(500).entity(doErrorMessage(e)).build();
			 }
			 return Response.status(200).entity(registroDeshabilitacionBuque).build();
		}
		
		//RFC7
		@GET
		@Path("/consulta-ingresos-salidas-buques/query")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response consultaIOBuques(
				@DefaultValue("") @QueryParam("fechaInicio") String fechaInicio,
				@DefaultValue("") @QueryParam("fechaFin") String fechaFin,
				@DefaultValue("") @QueryParam("nombreBuque") String nombreBuque,
				@DefaultValue("") @QueryParam("tipoDeBuque") String tipoDeBuque)
				//@DefaultValue("") @QueryParam("tipo") String tipo)
      	
		{
			PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
			ListaRegistrosIngresoSalidaBuques lista = new ListaRegistrosIngresoSalidaBuques();
			 try {
				 lista.add(tm.consultarRegistrosIOSalida(fechaInicio, fechaFin, "", tipoDeBuque));
			 } catch (Exception e) {
			   System.out.println("error de tm");
			   return Response.status(500).entity(doErrorMessage(e)).build();
			 }
			 return Response.status(200).entity(lista).build();
		}
		
		//RFC8
		@GET
		@Path("/consulta-ingresos-salidas-buques-v2/query")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response consultaIOBuques_v2(
				@DefaultValue("") @QueryParam("fechaInicio") String fechaInicio,
				@DefaultValue("") @QueryParam("fechaFin") String fechaFin,
				@DefaultValue("") @QueryParam("nombreBuque") String nombreBuque,
				@DefaultValue("") @QueryParam("tipoDeBuque") String tipoBuque){
				//@DefaultValue("") @QueryParam("tipo") String tipo){
			PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
			ListaRegistrosIngresoSalidaBuques lista = new ListaRegistrosIngresoSalidaBuques();
			 try {
				 lista.add(tm.consultarRegistrosIOSalida_v2(fechaInicio, fechaFin, nombreBuque, tipoBuque));
			 } catch (Exception e) {
			   System.out.println("error de tm");
			   return Response.status(500).entity(doErrorMessage(e)).build();
			 }
			 return Response.status(200).entity(lista).build();
		}
		
		
		//RF14
		
		
		  
}
