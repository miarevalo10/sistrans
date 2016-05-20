package vos;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;


/**
 * <b> Clase que representa al Buque de la tabla con el mismo nombre en la base de datos </b>
 */
public class Buque
{
	public static final String PORTACONTENEDOR = "PORTACONTENEDOR";

	public static final String RORO = "RORO";

	public static final String MULTIPROPOSITO = "MULTIPROPOSITO";
	
	
	// constantes del estado
	
	public static final String CARGADO = "CARGADO";
	public static final String PROCESO_DE_DESCARGA = "PROCESO_DE_DESCARGA";
	public static final String PROCESO_DE_CARGA = "PROCESO_DE_CARGA";
	public static final String NORMAL = "NORMAL";
	public static final String AVERIA = "AVERIA";
	public static final String MANTENIMIENTO = "MANTENIMIENTO";
	public static final String PROBLEMA_LEGAL = "PROBLEMA_LEGAL";


	/*Atributos de todos los buques*/
	/**
	 * Id del buque
	 */
	@JsonProperty(value="id")
	private int id;

	/**
	 * nombre del buque
	 */
	@JsonProperty(value="nombre")
	private String nombre;

	/**
	 * capacidad del buque
	 */
	@JsonProperty(value="capacidad")
	private int capacidad;

	/**
	 * Registro de capitania del buque
	 */
	@JsonProperty(value="registro-capitania")
	private String registroCapitania;

	/**
	 * Agente maritimo del buque
	 */
	@JsonProperty(value="agente-maritimo")
	private String agenteMaritimo;

	/**
	 * Tipo del buque
	 */
	@JsonProperty(value="tipo-buque")
	private String tipoBuque;
	
	private ArrayList<String> destinos;

	// va a haber dos metodos constructores, uno con anotaciones y otro que recibe como parametro Buque
	/**
	 * Método constructor de la clase Buque
	 * <b>post: </b> Crea el Buque con los valores que entran como parámetro
	 * @param id - Id del Buque
	 * @param nombre - nombre del buque
	 * @param capacidad - capacidad del buque
	 * @param registroCapitania - Registro de la Capitania del buque. registroCapitania != null
	 * @param agenteMaritimo - Agente maritimo del buque. agenteMaritimo != null
	 * @param tipoBuque - tipo del buque.
	 */
	public Buque(
			@JsonProperty(value="id")int id,
			@JsonProperty(value="nombre")String nombre,
			@JsonProperty(value="capacidad") int capacidad,
			@JsonProperty(value="registro-capitania") String registroCapitania,
			@JsonProperty(value="agente-maritimo") String agenteMaritimo,
			@JsonProperty(value="tipo-buque") String tipoBuque) {
		this.id = id;
		this.nombre = nombre;
		this.capacidad = capacidad;
		this.registroCapitania = registroCapitania;
		this.agenteMaritimo = agenteMaritimo;
		this.tipoBuque = tipoBuque;
		this.destinos = new ArrayList<String>();
	}


	/**
	 * Método constructor de la clase Buque / recibiendo BuqueGeneal
	 * <b>post: </b> Crea el Buque con los valores que entran como parámetro
	 * @param buque
	 */

	public Buque(Buque buque) {
		this.id = buque.getId();
		this.nombre = buque.getNombre();
		this.capacidad = buque.getCapacidad();
		this.registroCapitania = buque.getRegistroCapitania();
		this.agenteMaritimo = buque.getAgenteMaritimo();
		this.tipoBuque = buque.getTipoBuque();

	}


	/**
	 * Método getter del id
	 * @return id del buque
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Método getter de nombre
	 * @return nombre del buque
	 */
	public String getNombre() {
		return this.nombre;
	}

	/**
	 * Método getter de capacidad
	 * @return capacidad del buque
	 */
	public int getCapacidad() {
		return this.capacidad;
	}

	/**
	 * Método getter de registroCapitania
	 * @return registroCapitania del buque
	 */
	public String getRegistroCapitania() {
		return this.registroCapitania;
	}

	/**
	 * Método getter de agenteMaritimo
	 * @return agenteMaritimo del buque
	 */
	public String getAgenteMaritimo() {
		return this.agenteMaritimo;
	}

	/**
	 * Método getter tipoBuque
	 * @return tipoBuque
	 */
	public String getTipoBuque() {
		return this.tipoBuque;
	}

	/**
	 * Método setter del atributo id <b>post: </b> id de la buque
	 * ha sido cambiado con el valor que entra como parámetro
	 * @param id - id del buque.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Método setter del atributo nombre <b>post: </b> nombre de la persona
	 * ha sido cambiado con el valor que entra como parámetro
	 * @param nombre - nombre del buque.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * Método setter del atributo capacidad <b>post: </b> nombre del buque
	 * ha sido cambiado con el valor que entra como parámetro
	 * @param capacidad - capacidad del buque .
	 */
	public void setCapacidad(int capacidad){
		this.capacidad = capacidad;
	}

	/**
	 * Método setter del atributo registroCapitania <b>post: </b> registroCapitania del buque
	 * ha sido cambiado con el valor que entra como parámetro
	 * @param registroCapitania - registroCapitania del buque .
	 */
	public void setRegistroCapitania (String registroCapitania){
		this.registroCapitania = registroCapitania;
	}

	/**
	 * Método setter del atributo agenteMaritimo <b>post: </b> agenteMaritimo del buque
	 * ha sido cambiado con el valor que entra como parámetro
	 * @param agenteMaritimo - agenteMaritimo del buque .
	 */
	public void setAgenteMaritimo( String agenteMaritimo){
		this.agenteMaritimo = agenteMaritimo;
	}

	/**
	 * Método setter del atributo tipoBuque <b>post: </b> tipoBuque del buque
	 * ha sido cambiado con el valor que entra como parámetro
	 * @param tipoBuque - tipoBuque del buque .
	 */

	public void setTipoBuque (String tipoBuque){
		this.tipoBuque = tipoBuque;
	}


	/**
	 * @return the destinos
	 */
	public ArrayList<String> getDestinos() {
		return destinos;
	}


	/**
	 * @param destinos the distinos to set
	 */
	public void setDestinos(ArrayList<String> destinos) {
		this.destinos = destinos;
	}
	/**
	 * 
	 * @param tipoAEvaluar
	 * @return true si es un tipo valido, false de lo contrario
	 */
	public static boolean tipoValidoDeBuque(String tipoAEvaluar){
		return tipoAEvaluar.equals(MULTIPROPOSITO) || tipoAEvaluar.equals(PORTACONTENEDOR) ||  tipoAEvaluar.equals(RORO);
	}

}
