package dao;
/*Importaciones de java*/
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import vos.Area;
import vos.BuqueGeneral;
import vos.Carga;
import vos.ItemAreaCargaDestino;
public class DAOTablaDestinosBuques {
	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Método constructor que crea DAOBuque
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaDestinosBuques() {
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


	public void addDestinoBuque (int idBuque, String destino) throws SQLException, Exception{
		String sql  = "";
		sql+="INSERT INTO DESTINOS_BUQUES VALUES(";
		sql+=idBuque+","+destino+");";
		PreparedStatement prepStmt=conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();	
	}
	/**
	 * Metodo que retorna el id de las areas que contienen las cargas con el destino dado por parametro.
	 * @param destino
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public ArrayList<ItemAreaCargaDestino> darItemsAreaCargaDestino(String destino) throws SQLException, Exception{
		ArrayList<ItemAreaCargaDestino> items = new ArrayList<ItemAreaCargaDestino>();
		String sql = "SET TRANSACTION ISOLATION LEVEL READONLY;";
		sql+="SELECT INGRESO_SALIDA_AREA.ID_AREA, INGRESO_SALIDA_AREA.ID_CARGA, CARGA.DESTINO FROM (SELECT CARGA.ID FROM CARGA JOIN DESTINOS_BUQUES ON CARGA.DESTINO = DESTINOS_BUQUES.DESTINO WHERE DESTINOS_BUQUES.DESTINO = ";
		sql+=destino+" ) JOIN INGRESO_SALIDA_AREA ON INGRESO_SALIDA_AREA.ID_CARGA = CARGA.ID;";
		PreparedStatement prepStmt=conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rS = prepStmt.executeQuery();
		while(rS.next()){
			int idArea = rS.getInt("INGRESO_SALIDA_AREA.ID_AREA");
			int idCarga = rS.getInt("INGRESO_SALIDA_AREA.ID_CARGA");
			String rDestino = rS.getString("CARGA.DESTINO");
			ItemAreaCargaDestino item = new ItemAreaCargaDestino(idArea, idCarga, rDestino);
			items.add(item);
		}
		return items;
	}

	public ArrayList<String> darDestinosBuque(int idBuque) throws SQLException {
		ArrayList<String> destinos = new ArrayList<String>();
		String sql = "SET TRANSACTION ISOLATION LEVEL READONLY;";
		sql += "SELECT DESTINO FROM DESTINOS_BUQUES WHERE ID_BUQUE="+idBuque+";";
		PreparedStatement pS = conn.prepareStatement(sql);
		recursos.add(pS);
		ResultSet rS = pS.executeQuery();
		while(rS.next()){
			destinos.add(rS.getString("DESTNO"));
		}
		return destinos;
	}
	
}
