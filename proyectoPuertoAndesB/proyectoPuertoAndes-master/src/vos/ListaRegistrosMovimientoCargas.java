package vos;

import org.codehaus.jackson.annotate.JsonProperty;
import java.util.ArrayList;
import java.util.List;

public class ListaRegistrosMovimientoCargas {
	/**
	 * Lista de REgistroIO de cargas
	 */
	@JsonProperty(value = "registrosIOCargas")
	private List<RegistroMovimientoCarga> registrosIOCargas;

	/**
	 * Constructor que no recibe paramatros y solo inicializa la List como
	 * ArrayList
	 */

	public ListaRegistrosMovimientoCargas() {
		registrosIOCargas = new ArrayList<>();

	}

	/**
	 * Construtor que recibe una ArrayList como parametros e inicializa la List
	 * como ese ArrayList
	 */
	public ListaRegistrosMovimientoCargas(
			ArrayList<RegistroMovimientoCarga> registrosIOCargas) {
		registrosIOCargas = registrosIOCargas;
	}

	public List<RegistroMovimientoCarga> getRegistrosIOCargas() {
		return registrosIOCargas;
	}

	public void setRegistrosIOCargas(
			List<RegistroMovimientoCarga> registrosIOCargas) {
		this.registrosIOCargas = registrosIOCargas;
	}

	/**
	 * Metodo que recibe un ArrayList de Regsitros Arribo o Salida para retornar
	 * 
	 * @param registrosIOSalida
	 */
}
