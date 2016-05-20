package vos;

import java.sql.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class Contenedor extends Carga
{	
	@JsonProperty(value="porta-contenedor")
	private PortaContenedor portaContenedor;
	
	@JsonProperty(value="roro")
	private RoRo roRo;
	
	@JsonProperty(value="multi-proposito")
	private MultiProposito multiProposito;

	public Contenedor(@JsonProperty(value="id") int pId,  
			@JsonProperty(value="volumen") int pVolumen,
			@JsonProperty(value="fecha-ingreso") Date pFechaIngreso,
			@JsonProperty(value="id-responsable") int pIdResponsable,
			@JsonProperty(value="viene-de") String pVieneDe,
			@JsonProperty(value="id-buque") int pIdBuque,
			@JsonProperty(value="id-camion") int pIdCamion,
			String tipoBuque) throws Exception 
	{
		super(pId, pVolumen, pFechaIngreso, pIdResponsable, pVieneDe, pIdBuque, pIdCamion);
		if(tipoBuque.equals(Buque.PORTACONTENEDOR))
		{
			setPortaContenedor(new PortaContenedor());
			setRoRo(null);
			setMultiProposito(null);
		}
		else if(tipoBuque.equals(Buque.RORO))
		{
			setPortaContenedor(null);
			setRoRo(new RoRo());
			setMultiProposito(null);
		}
		else if(tipoBuque.equals(Buque.MULTIPROPOSITO))
		{
			setPortaContenedor(null);
			setRoRo(null);
			setMultiProposito(new MultiProposito());
		}
	}

	public PortaContenedor getPortaContenedor() {
		return portaContenedor;
	}

	public void setPortaContenedor(PortaContenedor portaContenedor) {
		this.portaContenedor = portaContenedor;
	}

	public RoRo getRoRo() {
		return roRo;
	}

	public void setRoRo(RoRo roRo) {
		this.roRo = roRo;
	}

	public MultiProposito getMultiProposito() {
		return multiProposito;
	}

	public void setMultiProposito(MultiProposito multiProposito) {
		this.multiProposito = multiProposito;
	}
}
