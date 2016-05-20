package vos;

import org.codehaus.jackson.annotate.JsonProperty;

import dateUtilities.DateParser;

import org.codehaus.jackson.annotate.*;

import java.text.ParseException;
import java.util.Date;

import javax.swing.text.html.HTMLDocument.HTMLReader.ParagraphAction;

public class RegistroMovimientoCarga {

	@JsonProperty(value = "id-carga")
	private int idCarga;
	@JsonProperty(value = "tipo-carga")
	private String tipoCarga;
	@JsonProperty(value = "origen")
	private String origen;
	@JsonProperty(value = "destino")
	private String destino;
	@JsonProperty(value = "fecha-orden")
	private String fechaOrdenString;
	@JsonProperty(value = "id-importador")
	private int idImportador;
	@JsonProperty(value = "id-exportador")
	private int idExportador;
	

	Date fechaOrden;

	public RegistroMovimientoCarga(@JsonProperty(value = "id-carga") int idCarga,
			@JsonProperty(value = "tipo-carga") String tipoCarga,
			@JsonProperty(value = "origen") String origen,
			@JsonProperty(value = "destino") String destino,
			@JsonProperty(value = "fecha-orden") String fechaOrdenString,
			@JsonProperty(value = "id-importador") int idImportador,
			@JsonProperty(value = "id-exportador") int idExportador) {
		super();
		this.idCarga = idCarga;
		this.tipoCarga = tipoCarga;
		this.origen = origen;
		this.destino = destino;
		this.fechaOrdenString = fechaOrdenString;

		try {
			this.fechaOrden = DateParser.fromStringToDate(fechaOrdenString);

		}

		catch (Exception e) {
			System.out.println("Erroe parseando fechaOrdenString en constructor");
			e.printStackTrace();
		}

	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public int getIdCarga() {
		return idCarga;
	}

	public void setIdCarga(int idCarga) {
		this.idCarga = idCarga;
	}

	public String getTipoCarga() {
		return tipoCarga;
	}

	public void setTipoCarga(String tipoCarga) {
		this.tipoCarga = tipoCarga;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getFechaOrdenString() {
		return fechaOrdenString;
	}

	public void setFechaOrdenString(String fechaOrdenString) {
		this.fechaOrdenString = fechaOrdenString;
	}

	public Date getFechaOrden() {
		return fechaOrden;
	}

	public void setFechaOrden(Date fechaOrden) {
		this.fechaOrden = fechaOrden;
	}

}
