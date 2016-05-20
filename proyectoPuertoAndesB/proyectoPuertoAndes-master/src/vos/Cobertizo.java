package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Cobertizo extends Area
{
	@JsonProperty(value="dimension")
	private int dimension;
	
	@JsonProperty(value="tipo-carga")
	private String tipoCarga;
	
	public Cobertizo(@JsonProperty(value="id") int pId, 
			@JsonProperty(value="dimension") int pDimension,
			@JsonProperty(value="tipo-carga") String pTipoCarga){
		super(Area.COBERTIZO,-1);
		this.setDimension(pDimension);
		this.setTipoCarga(pTipoCarga);
	}

	public int getDimension(){
		return dimension;
	}
	
	public void setDimension(int dimension){
		this.dimension=dimension;
	}

	public String getTipoCarga() {
		return tipoCarga;
	}

	public void setTipoCarga(String tipoCarga) {
		this.tipoCarga = tipoCarga;
	}
}
