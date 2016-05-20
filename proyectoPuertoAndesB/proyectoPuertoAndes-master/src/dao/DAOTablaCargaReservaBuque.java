package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import vos.Area;
import vos.Bodega;
import vos.Buque;
import vos.Carga;
import vos.RegistroCargaBuque;
import vos.RegistroEntregaCargaImportador;
import vos.RegistroLlegadaCargaArea;


public class DAOTablaCargaReservaBuque {
	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Método constructor que crea DAOTablaCargaReservaBuque
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaCargaReservaBuque() {
		recursos = new ArrayList<Object>();
	}
	
	/**
	 * Método que cierra todos los recursos que estan en el arreglo de recursos
	 * <b>post: </b> Todos los recurso del arreglo de recursos han sido cerrados
	 */
	public void cerrarRecursos() {
		for(Object ob : recursos){
			if(ob instanceof PreparedStatement)
				try {
					((PreparedStatement) ob).close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		}
	}

	/**
	 * Método que inicializa la connection del DAO a la base de datos con la conexión que entra como parámetro.
	 * @param con  - connection a la base de datos
	 */
	public void setConn(Connection con){
		this.conn = con;
	}

//<<<<<<< Updated upstream
//=======
//<<<<<<< Updated upstream
//>>>>>>> Stashed changes
	/**
	 * Método que retorna los id's de las cargas en un buque en unas fecha especifica.
	 * @param con  - connection a la base de datos
	 */
	//TODODONE 
	public ArrayList<Integer> getIdsCargasReservadasFechaBuque(int idBuque,
			Date fechaDescarga) throws Exception{
		ArrayList<Integer> listaIds = new ArrayList<>();
		java.sql.Date fechaDescargaSql = new java.sql.Date(fechaDescarga.getTime());
		
		//1. Primero sacamos un result set con todos los datos de la tabla que sean de ese buque. 
		//Como tambien tenemos que mirar que el destino sea puertAndes, hacemos un JOIN con un AND
		String sql = "SELECT ID_CARGA FROM CARGA_RESERVADA_BUQUE, CARGA WHERE ID_BUQUE = " + idBuque;
		sql += " AND FECHA_INICIO <=" + "TO_DATE('" +fechaDescargaSql+ "', 'YYYY-MM-DD') " ;
		sql += "AND FECHA_FIN >=" + "TO_DATE('" +fechaDescargaSql+ "', 'YYYY-MM-DD') ";
		sql += "AND CARGA.ID = CARGA_RESERVADA_BUQUE.ID_CARGA ";
		sql += "AND CARGA.DESTINO = '" + "PUERTOANDES" + "'";
		
		System.out.println(sql);
		PreparedStatement ps = conn.prepareStatement(sql);
		recursos.add(ps);
		ResultSet registrosIds = ps.executeQuery();
		// agregamos todos los ids a una lista para retornarla.
		int idActual = -1;
		while(registrosIds.next()){
			idActual = registrosIds.getInt("ID_CARGA");
			listaIds.add(idActual);
		}
		
		
		return listaIds;
	}
//<<<<<<< Updated upstream
//=======
//=======
//>>>>>>> Stashed changes
	public void registrarCargaBuque(RegistroCargaBuque registroCargaBuque) throws Exception{
		//String sql = "SET TRANSACTION ISOLATION SERIALIZABLE;";
		String sql ="UPDATE CARGA_RESERVADA_BUQUE SET FECHA_INICIO="+registroCargaBuque.fechaSalida+" WHERE ID_CARGA="+registroCargaBuque.idCarga+" AND ID_BUQUE="+registroCargaBuque.idBuque+" AND FECHA_INICIO IS NULL;";
		PreparedStatement pS = conn.prepareStatement(sql);
		recursos.add(pS);
		pS.executeQuery();
		conn.commit();
//<<<<<<< Updated upstream
//=======
//>>>>>>> Stashed changes
//>>>>>>> Stashed changes
	}
}
