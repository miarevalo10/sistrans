package vos;

import org.codehaus.jackson.annotate.JsonProperty;
import java.util.ArrayList;
import java.util.List;


public class ListaRegistrosIngresoSalidaBuques {

	/**
	 * Lista de REgistroIO de buques
	 */
	@JsonProperty(value="registrosIO")
	private List<RegistroArriboOSalida> registrosIO;
	
	/**
	 * Constructor que no recibe paramatros y solo inicializa la List como ArrayList
	 */
	
	public ListaRegistrosIngresoSalidaBuques(){
		registrosIO = new ArrayList<>();
		
	}
	/**
	 * Construtor que recibe una ArrayList como parametros e inicializa la List como ese ArrayList
	 */
	public ListaRegistrosIngresoSalidaBuques(ArrayList<RegistroArriboOSalida> registrosIOSalida){
		registrosIO = registrosIOSalida;
	}
	
	/**
	 * Metodo que recibe un ArrayList de Regsitros Arribo o Salida para retornar
	 * @param registrosIOSalida
	 */
		
	public void add(ArrayList<RegistroArriboOSalida> registrosIOSalida) {
		registrosIO = registrosIOSalida;
	}
	
	/**
	 * Método que retorna la lista de registrosIO
	 * @return  List - List con los videos
	 */
	public List<RegistroArriboOSalida> getRegistrosIOBuques() {
		return registrosIO;
	}

}
