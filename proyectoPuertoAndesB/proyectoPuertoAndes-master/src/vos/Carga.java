package vos;

import java.util.ArrayList;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class Carga 
{
	//Tipo de carga
	public final static String CONTENEDOR = "CONTENEDOR";
	
	public final static String GRANEL = "GRANEL";
	
	public final static String GENERAL = "GENERAL";
	
	public final static String VEHICULAR = "VEHICULAR";
	
	//Viene de: Constantes
	public final static String BUQUE = "BUQUE";
	
	public final static String CAMION = "CAMION";

	@JsonProperty(value="id")
	private int id;
	
	@JsonProperty(value="volumen")
	private int volumen;
	
	@JsonProperty(value="fecha-ingreso")
	private Date fechaIngreso;
	
	@JsonProperty(value="fecha-salida")
	private Date fechaSalida;
	
	@JsonProperty(value="id-responsable")
	private int idResponsable;
	
	@JsonProperty(value="viene-de")
	private String vieneDe;
	
	@JsonProperty(value="id-buque")
	private int idBuque;
	
	@JsonProperty(value="id-camion")
	private int idCamion;

	private ArrayList<Equipo> equiposUsados;
	
	public Carga(@JsonProperty(value="id") int pId,  
				@JsonProperty(value="volumen") int pVolumen,
				@JsonProperty(value="fecha-ingreso") Date pFechaIngreso,
				@JsonProperty(value="id-responsable") int pIdResponsable,
				@JsonProperty(value="viene-de") String pVieneDe,
				@JsonProperty(value="id-buque") int pIdBuque,
				@JsonProperty(value="id-camion") int pIdCamion
			) throws Exception{
		setId(pId);
		setVolumen(pVolumen);
		setFechaIngreso(pFechaIngreso);
		setFechaSalida(null);
		setIdResponsable(pIdResponsable);
		setVieneDe(pVieneDe);
		if(pVieneDe.equals(BUQUE)){
			setIdBuque(pIdBuque);
			setIdCamion(-1);
			setVieneDe(BUQUE);
		}
		else if(pVieneDe.equals(CAMION)){
			setIdBuque(-1);
			setIdCamion(pIdCamion);
			setVieneDe(CAMION);
		}
		else{
			throw new Exception("Debe especificar el origen de la carga");
		}
		setEquiposUsados(new ArrayList<Equipo>());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	/**
	 * @return the fechaSalida
	 */
	public Date getFechaSalida() {
		return fechaSalida;
	}

	/**
	 * @param fechaSalida the fechaSalida to set
	 */
	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	/**
	 * @return the volumen
	 */
	public int getVolumen() {
		return volumen;
	}

	/**
	 * @param volumen the volumen to set
	 */
	public void setVolumen(int volumen) {
		this.volumen = volumen;
	}

	/**
	 * @return the responsable
	 */
	public int getIdResponsable() {
		return idResponsable;
	}

	/**
	 * @param responsable the responsable to set
	 */
	public void setIdResponsable(int pIdResponsable) {
		this.idResponsable = pIdResponsable;
	}

	/**
	 * @return the vieneDe
	 */
	public String getVieneDe() {
		return vieneDe;
	}

	/**
	 * @param vieneDe the vieneDe to set
	 */
	public void setVieneDe(String vieneDe) {
		this.vieneDe = vieneDe;
	}

	/**
	 * @return the equiposUsados
	 */
	public ArrayList<Equipo> getEquiposUsados() {
		return equiposUsados;
	}

	/**
	 * @param equiposUsados the equiposUsados to set
	 */
	public void setEquiposUsados(ArrayList<Equipo> equiposUsados) {
		this.equiposUsados = equiposUsados;
	}
	
	/**
	 * 
	 */
	public int getIdBuque(){
		return this.idBuque;
	}
	
	/**
	 * 
	 */
	public void setIdBuque(int pIdBuque){
		this.idBuque=pIdBuque;
	}
	
	/**
	 * 
	 */
	public int getIdCamion(){
		return this.idCamion;
	}
	
	/**
	 * 
	 */
	public void setIdCamion(int pIdCamion){
		this.idCamion=pIdCamion;
	}
}
