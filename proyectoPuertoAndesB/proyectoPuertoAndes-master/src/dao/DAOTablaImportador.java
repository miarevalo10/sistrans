package dao;
/*Importaciones de java*/
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*Importaciones de vos*/
import vos.Importador;

public class DAOTablaImportador {
	
	
	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Método constructor que crea DAOImportador
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaImportador() {
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
	
	/*Metodos de las transacciones*/
	/**
	 * Método que agrega el importador que entra como parámetro a la base de datos.
	 * @param importador - el importador a agregar. importador !=  null
	 * <b> post: </b> se ha agregado el video a la base de datos en la transaction actual. pendiente que el importador master
	 * haga commit para que el importador baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el importador a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addImportador (Importador importador) throws SQLException, Exception{
		
		/*primero insertamos en la tabla madre (PERSONA), y luego en la tabla hija (IMPORTADOR) */
		
		String sqlMadre = "INSERT INTO PERSONA VALUES (";
		sqlMadre += importador.getId() + ",'";
		sqlMadre += importador.getNombre() + "','";
		sqlMadre += importador.getTipoPersona() + "')";
		
		System.out.println("SQL stmt:" + sqlMadre);

		PreparedStatement prepStmt = conn.prepareStatement(sqlMadre);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		
		
		
		String sqlHija= "INSERT INTO IMPORTADOR VALUES (";
		sqlHija += importador.getId() + ",'";
		sqlHija += importador.getRegistroAduana() + "','";
		sqlHija += importador.getTipoImportador() + "')";
		
		System.out.println("SQL stmt:" + sqlHija);

		prepStmt = conn.prepareStatement(sqlHija);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	
}
