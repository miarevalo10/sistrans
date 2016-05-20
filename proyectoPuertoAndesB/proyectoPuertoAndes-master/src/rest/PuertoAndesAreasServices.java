package rest;

import java.util.ArrayList;

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

@Path("areas")
public class PuertoAndesAreasServices {
	/**
	 * Atributo que usa la anotación @Context para tener el ServletContext de la
	 * conexión actual.
	 */
	@Context
	private ServletContext context;

	/**
	 * Método que retorna el path de la carpeta WEB-INF/ConnectionData en el
	 * deploy actual dentro del servidor.
	 * 
	 * @return path de la carpeta WEB-INF/ConnectionData en el deploy actual.
	 */
	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}

	private String doErrorMessage(Exception e) {
		return "{ \"ERROR\": \"" + e.getMessage() + "\"}";
	}

	// RF2
	/**
	 * Método que expone servicio REST usando PUT que agrega el buque que recibe
	 * en Json <b>URL: </b> http://"ip o nombre de host"
	 * :8080/PuertoAndes2/rest/areas/admin/bodega
	 * 
	 * @param video
	 *          - buque a agregar
	 * @return Json con el buque que agrego o Json con el error que se produjo
	 */
	@POST
	@Path("/admin/bodega")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addBodega(Bodega bodega) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.addBodega(bodega);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(bodega).build();
	}

	// RF13
	@POST
	@Path("/admin/cerrar-area-de-almacenamiento")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response cerrarAreaDeAlmacenamiento(int idArea) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.cerrarAreaAlmacenamiento(idArea);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(idArea).build();
	}

	
	// RFC9
	@GET
	@Path("/consulta-movimientos-carga/query")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultaMovimientosCarga(
			@DefaultValue("") @QueryParam("volumen") int volumen,
			@DefaultValue("") @QueryParam("tipoCarga") String tipoCarga)
    	
	{
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		ListaRegistrosMovimientoCargas lista = new ListaRegistrosMovimientoCargas();
		 try {
			 lista.setRegistrosIOCargas(tm.consultarMovimientosCarga(volumen, tipoCarga));
		 } catch (Exception e) {
		   System.out.println("error de tm");
		   return Response.status(500).entity(doErrorMessage(e)).build();
		 }
		 return Response.status(200).entity(lista).build();
	}
	
		   
    
    
  //RFC6 
    @POST // get
    @Path("/admin/consultar-areas-de-almacenamiento")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultarAreasAlmacenamiento(ConsultaAreasAlmacenamientoGerente consulta) {
        PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
        ArrayList<RespuestaConsultaArea> respuestas;
        try {
         respuestas = tm.consultarAreasAlmacenamiento(consulta);
      } catch (Exception e) {
          return Response.status(500).entity(doErrorMessage(e)).build();
      }
      return Response.status(200).entity(respuestas).build();
    }
    
    //RFC10
    @GET
	@Path("/consulta-areas-de-almacenamiento/query")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultarAreasDeAlmacenamiento2(
			@DefaultValue("") @QueryParam("idArea1") int idArea1,
			@DefaultValue("") @QueryParam("idArea2") int idArea2){
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		ListaRegistrosArea lista = new ListaRegistrosArea();
		 try {
			 lista.add(tm.consultarAreasDeAlmacenamiento(idArea1, idArea2));
		 } catch (Exception e) {
		   System.out.println("error de tm");
		   return Response.status(500).entity(doErrorMessage(e)).build();
		 }
		 return Response.status(200).entity(lista).build();
	}
    
    
   
}
