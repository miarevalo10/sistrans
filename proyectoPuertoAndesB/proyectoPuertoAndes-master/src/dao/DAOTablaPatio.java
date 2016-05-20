package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
//<<<<<<< HEAD
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Cobertizo;
import vos.Patio;

//=======
import java.util.ArrayList;

//>>>>>>> origin/master
public class DAOTablaPatio {
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
	public DAOTablaPatio() {
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
	

	/*
	private void insertPatio(Patio patio) throws SQLException{
		String SQL_STMT="INSERT INTO COBERTIZO VALUES (";
		SQL_STMT+=patio.getId()+",'";
		SQL_STMT+=patio.getDimension()+",'";
		SQL_STMT+=patio.getTipoCarga()+",'";
		PreparedStatement prepStmt=conn.prepareStatement(SQL_STMT);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	public void addPatio(Patio patio) throws SQLException, Exception{
		this.insertPatio(patio);
	}*/
//=======
//>>>>>>> origin/master
}
