
package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Camion;  // hay que corregirlo

/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicación
 *
 */
public class DAOTablaCamion {


	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Método constructor que crea DAOCamion
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaCamion() {
		recursos = new ArrayList<Object>();
	}

	/**
	 * Método que cierra todos los recursos que estan enel arreglo de recursos
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



	/**
	 * Método que, usando la conexión a la base de datos, saca todos los camiones de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM CAMNIONES;
	 * @return Arraylist con los camiones de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Camion> darCamiones() throws SQLException, Exception {
		ArrayList<Camion> camiones = new ArrayList<Camion>();

		String sql = "SELECT * FROM CAMION";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String placa = rs.getString("PLACA");
			int capacidad = Integer.parseInt(rs.getString("CAPACIDAD"));
			int  id_importador = Integer.parseInt(rs.getString("ID_IMPORTADOR"));
			camiones.add(new Camion(placa, capacidad, id_importador));
		}
		return camiones;
	}


	/**
	 * Método que busca el/los camiones con la placa que entra como parámetro.
	 * @param name - Nombre de el/los camiones a buscar
	 * @return Arraylist con los camions encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Camion> buscarCamionPorPlaca(String placa) throws SQLException, Exception {
		ArrayList<Camion> camiones = new ArrayList<Camion>();

		String sql = "SELECT * FROM CAMION WHERE PLACA ='" + placa + "'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String placa2 = rs.getString("PLACA");
			int capacidad = Integer.parseInt(rs.getString("CAPACIDAD"));
			int  id_importador = Integer.parseInt(rs.getString("ID_IMPORTADOR"));
			camiones.add(new Camion(placa2, capacidad, id_importador));
		}

		return camiones;
	}

	/**
	 * Método que agrega el camion que entra como parámetro a la base de datos.
	 * @param camion - el camion a agregar. camion !=  null
	 * <b> post: </b> se ha agregado el camion a la base de datos en la transaction actual. pendiente que el camion master
	 * haga commit para que el camion baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el camion a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addCamion(Camion camion) throws SQLException, Exception {

		String sql = "INSERT INTO CAMION VALUES (";
		sql += camion.getPlaca() + ",'";
		sql += camion.getCapacidad() + "',";
		sql += camion.getIdImportador() + ")";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	/**
	 * Método que actualiza el camion que entra como parámetro en la base de datos.
	 * @param camion - el camion a actualizar. camion !=  null
	 * <b> post: </b> se ha actualizado el camion en la base de datos en la transaction actual. pendiente que el camion master
	 * haga commit para que los cambios bajen a la base de datos. NO se puede actualizar la placa
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el camion.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateCamion(Camion camion) throws SQLException, Exception {

		String sql = "UPDATE CAMION SET ";
		sql += "capacidad='" + camion.getCapacidad() + "',";
		sql += "id_importador=" + camion.getIdImportador();
		sql += " WHERE placa = " + camion.getPlaca();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Método que elimina el camion que entra como parámetro en la base de datos.
	 * @param camion - el camion a borrar. camion !=  null
	 * <b> post: </b> se ha borrado el camion en la base de datos en la transaction actual. pendiente que el camion master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el camion.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteCamion(Camion camion) throws SQLException, Exception {

		String sql = "DELETE FROM CAMION";
		sql += " WHERE placa = " + camion.getplaca();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

}
