package vos;

import org.codehaus.jackson.annotate.*;

/**
 * Clase que representa un Importador
 */
public class Importador extends Persona {

    ////Atributos
	public static final String HABITUAL = "HABITUAL";
	public static final String NO_HABITUAL = "NO_HABITUAL";
	
    /**
     * Numero de registro en Aduana
     */
    @JsonProperty(value = "registro-aduana")
    private String registroAduana;

    /**
     * Tipo del importador (habitual o no habitual)
     */
    @JsonProperty(value = "tipo-importador")
    private String tipoImportador;

    /**
     * Método constructor de la clase importador
     * <b>post: </b> Crea el importador con los valores que entran como parámetro
     * <b>post:</b> La estrategia usada es: aunque llegan los parametros al constructor,
     * esta clase no los tiene, los tiene son su supercalse
     *
     * @param id             Id del importador.
     * @param registroAduana Registro en la Aduana del importador. registroAduana != null
     * @param tipoImportador tipo del Importador.
     * @param nombre         nombre del importador
     * @param tipoPersona    tipo de la persona que es importador
     */
    public Importador(@JsonProperty(value = "id") int id,
                      @JsonProperty(value = "registro-aduana") String registroAduana,
                      @JsonProperty(value = "tipo-importador") String tipoImportador,
                      @JsonProperty(value = "nombre") String nombre,
                      @JsonProperty(value = "tipo-persona") String tipoPersona) {

        super(id, nombre, tipoPersona);
        this.registroAduana = registroAduana;
        this.tipoImportador = tipoImportador;
    }


    /**
     * Método getter del atributo registroAduana
     *
     * @return registro en la Aduana del importador
     */
    public String getRegistroAduana() {
        return this.registroAduana;
    }

    /**
     * Método getter del atributo tipoImportador
     *
     * @return tipo del importador
     */

    public String getTipoImportador() {

        return this.tipoImportador;
    }


    /**
     * Método setter del atributo tipoImportador <b>post: </b> tipo del Importador
     * ha sido cambiado con el valor que entra como parámetro
     *
     * @param tipoImportador tipoImportador del importador.
     */
    public void setRegistroAduana(String registroAduana) {
        this.registroAduana = registroAduana;
    }

    public void setTipoImoprtador(String tipoImportador){
    		this.tipoImportador = tipoImportador;
    }
}
            
            
