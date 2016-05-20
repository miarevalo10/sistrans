package vos;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

public class Area {
	public final static String BODEGA ="BODEGA";
	
	public final static String PATIO ="PATIO";
	
	public final static String COBERTIZO = "COBERTIZO";
	
	public final static String SILO = "SILO";
	
	
	// CONSTANTES PARA EL ESTADO
	
	public final static String RESERVADA = "RESERVADA";
	public final static String LIBERADA = "LIBERADA";
	public final static String MANTENIMIENTO = "MANTENIMIENTO";
	public final static String SEGURIDAD = "SEGURIDAD";
	public final static String DEMOLICION = "DEMOLICION";
	public final static String AMPLIACION = "AMPLIACION";
	public final static String DESHABILITADA = "DESHABILITADA";
	
	
	@JsonProperty(value="id-cuarto-frio")
	private int idCuartoFrio;
	
	@JsonProperty(value="tipoArea")
	private String tipoArea;
	
	public Area(@JsonProperty(value="tipoArea") String pTipoArea, 
			@JsonProperty(value="id-cuarto-frio") int idCuartoFrio){	
		if(idCuartoFrio < 0)
			this.idCuartoFrio = -1;
		else
			this.idCuartoFrio = idCuartoFrio;
			
		if(pTipoArea.equals(BODEGA))setTipoArea(BODEGA);
		if(pTipoArea.equals(PATIO))setTipoArea(PATIO);
		if(pTipoArea.equals(COBERTIZO))setTipoArea(COBERTIZO);
		if(pTipoArea.equals(SILO))setTipoArea(SILO);
	}


	public int getIdCuartoFrio() {
		return idCuartoFrio;
	}

	public void setIdCuartoFrio(int idCuartoFrio) {
		this.idCuartoFrio = idCuartoFrio;
	}

	/**
	 * @return the tipoArea
	 */
	public String getTipoArea() {
		return tipoArea;
	}

	/**
	 * @param tipoArea the tipoArea to set
	 */
	public void setTipoArea(String tipoArea) {
		this.tipoArea = tipoArea;
	}
	
	/**
	 * @param tipo : el tipo de carga que se quiere guardar en el área
	 */
	public static ArrayList<String> getTiposAreaSegunTipoCarga (String tipo) throws Exception{
		ArrayList<String> tiposPosiblesDeArea = new ArrayList<>();
		
		// aqui toca hacer un lindo if
		if(tipo.equals(Carga.GENERAL)){
			tiposPosiblesDeArea.add(BODEGA);
			tiposPosiblesDeArea.add(COBERTIZO);
		}
		else if(tipo.equals(Carga.CONTENEDOR)){
			tiposPosiblesDeArea.add(PATIO);
		}
		else if (tipo.equals(Carga.GRANEL)){
			tiposPosiblesDeArea.add(SILO);
		}
		else {
			throw new Exception ("Tipo de carga no valido, no se puede hacer la verificacion");
		}
		
		return tiposPosiblesDeArea;
	}
	
}
