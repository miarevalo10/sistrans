package vos;

import org.codehaus.jackson.annotate.*;

public class Exportador extends Persona {
    ////Atributos

    /**
     * Numero de registro en Aduana
     */
    @JsonProperty(value = "rut")
    private int rut;


    /**
     * Método constructor de la clase Exportador
     * <b>post: </b> Crea el exportador con los valores que entran como parámetro
     * <b>post:</b> La estrategia usada es: aunque llegan los parametros al constructor,
     * esta clase no los tiene, los tiene son su supercalse
     *
     * @param id          Id del exportador.
     * @param rut         rut != null
     * @param nombre      nombre del exportador
     * @param tipoPersona tipo de la persona que es exportador
     */
    public Exportador(
            @JsonProperty(value = "id") int id,
            @JsonProperty(value = "rut") int rut,
            @JsonProperty(value = "nombre") String nombre,
            @JsonProperty(value = "tipo-persona") String tipoPersona) {

        super(id, nombre, tipoPersona);
        this.rut = rut;
    }

    /**
     * Método getter del atributo rut
     *
     * @return tipo del importador
     */

    public int getRut() {

        return this.rut;
    }

    /**
     * Método setter del atributo rut <b>post: </b> rut del exportador
     * ha sido cambiado con el valor que entra como parámetro
     *
     * @param rut rut del exportador.
     */
    public void setRut(int rut) {
        this.rut = rut;
    }

}

