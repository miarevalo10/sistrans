package vos;

import java.sql.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class Granel extends Carga{
	@JsonProperty(value="tipo-granel")
	private String tipoGranel;
	
	@JsonProperty(value="id-multi-proposito")
	private int idMultiProposito;
	
	public Granel(@JsonProperty(value="id") int pId,  
			@JsonProperty(value="volumen") int pVolumen,
			@JsonProperty(value="fecha-ingreso") Date pFechaIngreso,
			@JsonProperty(value="id-responsable") int pIdResponsable,
			@JsonProperty(value="viene-de") String pVieneDe,
			@JsonProperty(value="id-buque") int pIdBuque,
			@JsonProperty(value="id-camion") int pIdCamion, 
			@JsonProperty(value="tipo-granel") String pTipoGranel,
			@JsonProperty(value="id-multi-proposito") int pIdMultiProposito
			) throws Exception {
		super(pId, pVolumen, pFechaIngreso, pIdResponsable, pVieneDe, pIdBuque, pIdCamion);
		setTipoGranel(pTipoGranel);
		setIdMultiProposito(pIdMultiProposito);
	}

	/**
	 * @return the tipoGranel
	 */
	public String getTipoGranel() {
		return tipoGranel;
	}

	/**
	 * @param tipoGranel the tipoGranel to set
	 */
	public void setTipoGranel(String tipoGranel) {
		this.tipoGranel = tipoGranel;
	}

	/**
	 * @return the idMultiProposito
	 */
	public int getIdMultiProposito() {
		return idMultiProposito;
	}

	/**
	 * @param idMultiProposito the idMultiProposito to set
	 */
	public void setIdMultiProposito(int idMultiProposito) {
		this.idMultiProposito = idMultiProposito;
	}

	
}
