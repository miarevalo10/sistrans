package vos;

import org.codehaus.jackson.annotate.JsonProperty;


/**
* <b> Clase que representa al Buque que viene representado en el objeto JSON a traves del API REST. </b>
* <b>Tiene todos los atributos de todos los buques que existen en la base de datos, mas el tipo, para ya
* luego crear las instancias de las clases mas especificas para la transaccion
*/
public class BuqueGeneral {


	public final static int COSTO_BUQUE  = 10000;

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
	private String tipoBuqueGeneral;
	
	/**
	* Capacidad de los contenedores del buaue, en caso de que sea portaContenedor
	*/
	@JsonProperty(value="capacidad-contenedores")
	private int capacidadContenedores;
	
	/**
	 * Método constructor de la clase BuqueGeneral
	 * <b>post: </b> Crea el BuqueGeneral con los valores que entran como parámetro
	 * @param nombre - nombre del buque general
	 * @param capacidad - capacidad del buque general
	 * @param registroCapitania - Registro de la Capitania del buque. registroCapitania != null
	 * @param agenteMaritimo - Agente maritimo del buque. agenteMaritimo != null
	 * @param tipoBuqueGeneral - tipo del buque General.
	 */
	public BuqueGeneral(
			@JsonProperty(value="nombre")String nombre,
			@JsonProperty(value="capacidad") int capacidad,
			@JsonProperty(value="registro-capitania") String registroCapitania,
			@JsonProperty(value="agente-maritimo") String agenteMaritimo,
			@JsonProperty(value="tipo-buque") String tipoBuqueGeneral,
			@JsonProperty(value="capacidad-contenedores") int capacidadContenedores) {
		this.nombre = nombre;
		this.capacidad = capacidad;
		this.registroCapitania = registroCapitania;
		this.agenteMaritimo = agenteMaritimo;
		this.tipoBuqueGeneral = tipoBuqueGeneral;
		this.capacidadContenedores = capacidadContenedores;
	}
	
	
	/**
	 * Método getter del id
	 * @return id del buqueGeneral
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * Método getter de nombre
	 * @return nombre del buqueGeneral
	 */
	public String getNombre() {
		return this.nombre;
	}
	
	/**
	 * Método getter de capacidad
	 * @return capacidad del buqueGeneral
	 */
	public int getCapacidad() {
		return this.capacidad;
	}
	
	/**
	 * Método getter de registroCapitania
	 * @return registroCapitania del buqueGeneral
	 */
	public String getRegistroCapitania() {
		return this.registroCapitania;
	}
	
	/**
	 * Método getter de agenteMaritimo
	 * @return agenteMaritimo del buqueGeneral
	 */
	public String getAgenteMaritimo() {
		return this.agenteMaritimo;
	}
	
	/**
	 * Método getter tipoBuqueGeneral
	 * @return tipoBuqueGeneral
	 */
	public String getTipoBuqueGeneral() {
		return this.tipoBuqueGeneral;
	}
	/**
	 * Método getter capacidadContenedores
	 * @return capacidadContenedores
	 */
	public int getCapacidadContenedores() {
		return this.capacidadContenedores;
	}
	
	/**
	 * Método setter del atributo nombre <b>post: </b> nombre de la persona
	 * ha sido cambiado con el valor que entra como parámetro
	 * @param nombre - nombre del buqueGeneral.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * Método setter del atributo capacidad <b>post: </b> nombre del buque
	 * ha sido cambiado con el valor que entra como parámetro
	 * @param capacidad - capacidad del buque General.
	 */
	public void setCapacidad(int capacidad){
		this.capacidad = capacidad;
	}
	
	/**
	 * Método setter del atributo registroCapitania <b>post: </b> registroCapitania del buque
	 * ha sido cambiado con el valor que entra como parámetro
	 * @param registroCapitania - registroCapitania del buque General.
	 */
	public void setRegistroCapitania (String registroCapitania){
		this.registroCapitania = registroCapitania;
	}
	
	/**
	 * Método setter del atributo agenteMaritimo <b>post: </b> agenteMaritimo del buque
	 * ha sido cambiado con el valor que entra como parámetro
	 * @param agenteMaritimo - agenteMaritimo del buque General.
	 */
	public void setAgenteMaritimo( String agenteMaritimo){
		this.agenteMaritimo = agenteMaritimo;
	}
	
	/**
	 * Método setter del atributo tipoBuqueGeneral <b>post: </b> tipoBuqueGeneral del buque
	 * ha sido cambiado con el valor que entra como parámetro
	 * @param tipoBuqueGeneral - tipoBuqueGeneral del buque General.
	 */

	public void setTipoBuqueGeneral (String tipoBuqueGeneral){
		this.tipoBuqueGeneral = tipoBuqueGeneral; 
	}
	
	
	/**
	 * Método setter del atributo capacidadContenedores <b>post: </b> tipoBuqueGeneral del buque
	 * ha sido cambiado con el valor que entra como parámetro
	 * @param capacidadContenedores - capacidadContenedores del buque General.
	 */
	public void setCapacidadContenedores (int capacidadContenedores){
		this.capacidadContenedores = capacidadContenedores;
	}
	
	
	
	
	
	
	
}
