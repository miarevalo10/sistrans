package vos;

public class ItemAreaCargaDestino {
	private int idArea;
	private int idCarga;
	private String destino;
	public ItemAreaCargaDestino(int pIdArea, int pIdCarga, String pDestino){
		this.setIdArea(pIdArea);
		this.setIdCarga(pIdCarga);
		this.setDestino(pDestino);
	}
	/**
	 * @return the idArea
	 */
	public int getIdArea() {
		return idArea;
	}
	/**
	 * @param idArea the idArea to set
	 */
	public void setIdArea(int idArea) {
		this.idArea = idArea;
	}
	/**
	 * @return the idCarga
	 */
	public int getIdCarga() {
		return idCarga;
	}
	/**
	 * @param idCarga the idCarga to set
	 */
	public void setIdCarga(int idCarga) {
		this.idCarga = idCarga;
	}
	/**
	 * @return the destino
	 */
	public String getDestino() {
		return destino;
	}
	/**
	 * @param destino the destino to set
	 */
	public void setDestino(String destino) {
		this.destino = destino;
	}
}
