package vos;

import java.sql.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class General extends Carga{
	@JsonProperty(value="marca")
	private String marca;
	
	@JsonProperty(value="peso")
	private int peso;
	
	public General(@JsonProperty(value="id") int pId,  
			@JsonProperty(value="volumen") int pVolumen,
			@JsonProperty(value="fecha-ingreso") Date pFechaIngreso,
			@JsonProperty(value="id-responsable") int pIdResponsable,
			@JsonProperty(value="viene-de") String pVieneDe,
			@JsonProperty(value="id-buque") int pIdBuque,
			@JsonProperty(value="id-camion") int pIdCamion, 
			@JsonProperty(value="tipo-granel") String pTipoGranel,
			@JsonProperty(value="id-multi-proposito") int pIdMultiProposito,
			@JsonProperty(value="marca") String pMarca,
			@JsonProperty(value="peso") int pPeso
			) throws Exception {
		super(pId, pVolumen, pFechaIngreso, pIdResponsable, pVieneDe, pIdBuque, pIdCamion);
		setMarca(pMarca);
		setPeso(pPeso);
	}

	/**
	 * @return the marca
	 */
	public String getMarca() {
		return marca;
	}

	/**
	 * @param marca the marca to set
	 */
	public void setMarca(String marca) {
		this.marca = marca;
	}

	/**
	 * @return the peso
	 */
	public int getPeso() {
		return peso;
	}

	/**
	 * @param peso the peso to set
	 */
	public void setPeso(int peso) {
		this.peso = peso;
	}

	
	
}
