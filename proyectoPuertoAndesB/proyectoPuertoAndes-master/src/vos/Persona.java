package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Persona {
	////Atributos
	
	
	public static final String NATURAL = "NATURAL";
	public static final String JURIDICA = "JURIDICA";
	/**
	* Id de la persona
	*/
	@JsonProperty(value="id")
	private int id;
	
	/**
	* Nombre de la persona
	*/
	@JsonProperty(value="nombre")
	private String nombre;
	
	/**
	* Tipo de la persona (natural o juridica)
	*/
	@JsonProperty(value="tipo-persona")
	private String tipoPersona;
	
	
	/**
	 * Método constructor de la clase persona
	 * <b>post: </b> Crea el persona con los valores que entran como parámetro
	 * @param id - Id del persona.
	 * @param tipoPersona - tipo del persona.
	 */
	public Persona(
			@JsonProperty(value="id")int id,
			@JsonProperty(value="nombre")String nombre,
			@JsonProperty(value="tipo-persona") String tipoPersona) {
		this.id = id;
		this.nombre = nombre;
		this.tipoPersona = tipoPersona;
	}
	
	/**
	 * Método getter del atributo id
	 * @return id de la Persona
	 */
	public int getId(){
		return this.id;
	}
	
	/**
	 * Método getter del atributo persona
	 * @return nombre de la persona
	 */
	public String getNombre(){
		return this.nombre;
	}
	
	/**
	 * Método getter del tipoPersona
	 * @return tipo de la persona 
	 */
	public String getTipoPersona() {
		return this.tipoPersona;
	}

	/**
	 * Método setter del atributo id <b>post: </b> id de la persona
	 * ha sido cambiado con el valor que entra como parámetro
	 * @param id - id de la persona.
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Método setter del atributo nombre <b>post: </b> nombre de la persona
	 * ha sido cambiado con el valor que entra como parámetro
	 * @param nombre - nombre de la persona.
	 */
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 * Método setter del atributo tipoPersona <b>post: </b> tipo de la Persona
	 * ha sido cambiado con el valor que entra como parámetro
	 * @param tipoPersona - tipoPersona de la persona.
	 */
	public void setTipoPersona(String tipoPersona){
		this.tipoPersona = tipoPersona;
	}
	
	
	
}
