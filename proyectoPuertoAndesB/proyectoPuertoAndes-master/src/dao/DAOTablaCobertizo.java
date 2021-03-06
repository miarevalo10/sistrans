package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
//<<<<<<< HEAD
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Bodega;
import vos.Cobertizo;

//=======
import java.util.ArrayList;

//>>>>>>> origin/master
public class DAOTablaCobertizo {
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
	public DAOTablaCobertizo() {
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
//<<<<<<< HEAD
	
	
	private void insertCobertizo(Cobertizo cobertizo) throws SQLException{
		String SQL_STMT="INSERT INTO COBERTIZO VALUES (";
		SQL_STMT+=cobertizo.getDimension()+",'";
		SQL_STMT+=cobertizo.getTipoCarga()+",'";
		PreparedStatement prepStmt=conn.prepareStatement(SQL_STMT);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	public void addCobertizo(Cobertizo cobertizo) throws SQLException, Exception{
		this.insertCobertizo(cobertizo);
	}
//=======
//>>>>>>> origin/master
}
