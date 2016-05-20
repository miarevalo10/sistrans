package vos;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.*;

import java.text.ParseException;
import java.util.Date;

import javax.swing.text.html.HTMLDocument.HTMLReader.ParagraphAction;

import dateUtilities.*;

public class RespuestaConsultaArea {
	
	@JsonProperty(value="id")
	private int idArea;
	
	@JsonProperty(value="capacidad")
	private int capacidad;
	
	@JsonProperty(value="estado")
	private String estado;
	
	@JsonProperty(value="numero-cargas")
	private int numeroCargas;
	
	public RespuestaConsultaArea (
			@JsonProperty(value="id")
			 int idArea,
			@JsonProperty(value="capacidad")
			 int capacidad,
			
			@JsonProperty(value="estado")
			 String estado,
			
			@JsonProperty(value="numero-cargas")
			 int numeroCargas
			
			){
		
		
		
		
	}
}
