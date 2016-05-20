package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
//<<<<<<< HEAD
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Area;
import vos.Bodega;

//=======
import java.util.ArrayList;

//>>>>>>> origin/master
public class DAOTablaBodega {
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
	public DAOTablaBodega() {
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
	
	public void addBodega(Bodega bodega) throws SQLException, Exception{
		String SQL_STMT="INSERT INTO BODEGA VALUES (";
		SQL_STMT+="seq_area.currval" + ",";
		SQL_STMT+=bodega.getAncho()+",";
		SQL_STMT+=bodega.getLargo()+",";
		SQL_STMT+=bodega.getPlataformaExterna()+",";
		SQL_STMT+=bodega.getSeparacionEntreColumnas()+ ")";
		
		System.out.println(SQL_STMT);
		PreparedStatement prepStmt=conn.prepareStatement(SQL_STMT);
		recursos.add(prepStmt);
		prepStmt.executeQuery();	}
	
}
