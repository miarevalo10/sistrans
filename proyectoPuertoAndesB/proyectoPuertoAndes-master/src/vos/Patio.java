package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Patio extends Area
{
	@JsonProperty(value="dimension")
	private int dimension;
	
	@JsonProperty(value="tipo-carga")
	private String tipoCarga;

	public Patio(@JsonProperty(value="id") int pId, 
				@JsonProperty(value="dimension") int pDimension,
				@JsonProperty(value="tipo-carga") String pTipoCarga) {
		super(Area.PATIO,-1);
		this.setDimension(pDimension);
	}

	public int getDimension() {
		return dimension;
	}

	public void setDimension(int dimension) {
		this.dimension = dimension;
	}
}
