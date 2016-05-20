package vos;

import org.codehaus.jackson.annotate.*;

public class Bodega extends Area{
	@JsonProperty(value="ancho")
	private int ancho;
	
	@JsonProperty(value="largo")
	private int largo;
	
	@JsonProperty(value="plataforma-externa")
	private int plataformaExterna;
	
	@JsonProperty(value="separacion-entre-columnas")
	private int separacionEntreColumnas;

	@JsonProperty(value="cuarto-frio")
	private int cuartoFrio;
	
	public Bodega( 
				@JsonProperty(value="ancho") int pAncho, 
				@JsonProperty(value="largo")int pLargo, 
				@JsonProperty(value="plataforma-externa") int pPlataformaExterna, 
				@JsonProperty(value="separacion-entre-columnas") int pSeparacionEntreColumnas,
				@JsonProperty(value="id-cuarto-frio")int cuartoFrio
				) {
		super(Area.BODEGA, cuartoFrio);
		this.setAncho(pAncho);
		this.setLargo(pLargo);
		this.setPlataformaExterna(pPlataformaExterna);
		this.setSeparacionEntreColumnas(pSeparacionEntreColumnas);
	}

	public int getAncho() {
		return ancho;
	}

	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	public int getLargo() {
		return largo;
	}

	public void setLargo(int largo) {
		this.largo = largo;
	}

	public int getPlataformaExterna() {
		return plataformaExterna;
	}

	public void setPlataformaExterna(int plataformaExterna) {
		this.plataformaExterna = plataformaExterna;
	}

	public int getSeparacionEntreColumnas() {
		return separacionEntreColumnas;
	}

	public void setSeparacionEntreColumnas(int separacionEntreColumnas) {
		this.separacionEntreColumnas = separacionEntreColumnas;
	}
}
