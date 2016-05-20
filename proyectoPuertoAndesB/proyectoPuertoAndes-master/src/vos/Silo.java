package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Silo extends Area
{
	@JsonProperty(value="nombre")
	private String nombre;
	
	@JsonProperty(value="capacidad")
	private int capacidad;

	public Silo(@JsonProperty(value="id") int pId, 
				@JsonProperty(value="nombre") String pNombe, 
				@JsonProperty(value="capacidad") int pCapacidad) {
		super(Area.SILO,-1);
		this.setNombre(pNombe);
		this.setCapacidad(pCapacidad);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

}
