package vos;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaRegistrosArea {

	@JsonProperty(value="registrosIO")
	private List<RegistroArea> registros;

	public ListaRegistrosArea(){
		registros = new ArrayList<RegistroArea>();
		
	}

	public ListaRegistrosArea(ArrayList<RegistroArea> registrosArea){
		registros = registrosArea;
	}
	
	//TODO?
	public void add(ArrayList<RegistroArea> registrosArea) {
	
	}

	public List<RegistroArea> getRegistrosArea() {
		return registros;
	}
}
