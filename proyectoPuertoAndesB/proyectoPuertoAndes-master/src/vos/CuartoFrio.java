package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class CuartoFrio {
	@JsonProperty(value="id")
	private int Id;
	
	@JsonProperty(value="area")
	private int area;
	
	//@JsonProperty(value="ancho")
	//private int ancho;
	
	@JsonProperty(value="largo")
	private int largo;
	
	@JsonProperty(value="altura")
	private int altura;
	
	@JsonProperty(value="area-funcion-bodega")
	private int areaFuncionBodega;

	public CuartoFrio (@JsonProperty(value="id") int pId, 
					@JsonProperty(value="area") int pArea, 
					//@JsonProperty(value="ancho") int pAncho, 
					@JsonProperty(value="largo") int pLargo, 
					@JsonProperty(value="altura") int pAltura, 
					@JsonProperty(value="area-funcion-bodega") int pAreaFuncionBodega){
		setId(pId);
		setArea(pArea);
		setLargo(pLargo);
		setAltura(pAltura);
		setAreaFuncionBodega(pAreaFuncionBodega);
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return Id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		Id = id;
	}

	/**
	 * @return the area
	 */
	public int getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(int area) {
		this.area = area;
	}

	/**
	 * @return the largo
	 */
	public int getLargo() {
		return largo;
	}

	/**
	 * @param largo the largo to set
	 */
	public void setLargo(int largo) {
		this.largo = largo;
	}

	/**
	 * @return the altura
	 */
	public int getAltura() {
		return altura;
	}

	/**
	 * @param altura the altura to set
	 */
	public void setAltura(int altura) {
		this.altura = altura;
	}

	/**
	 * @return the areaFuncionBodega
	 */
	public int getAreaFuncionBodega() {
		return areaFuncionBodega;
	}

	/**
	 * @param areaFuncionBodega the areaFuncionBodega to set
	 */
	public void setAreaFuncionBodega(int areaFuncionBodega) {
		this.areaFuncionBodega = areaFuncionBodega;
	}
	
}
