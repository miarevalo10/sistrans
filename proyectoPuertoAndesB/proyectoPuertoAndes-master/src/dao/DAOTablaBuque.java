package dao;

/*Importaciones de java*/
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*Importaciones de vos*/
import vos.BuqueGeneral;
import vos.Buque;
import vos.MultiProposito;
import vos.PortaContenedor;
import vos.RoRo;

public class DAOTablaBuque {
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
	public DAOTablaBuque() {
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


	/*Metodos de las transacciones*/

	/**
	 * Método que agrega el exportador que entra como parámetro a la base de datos.
	 * @param buqueGeneral - el buque a agregar. buque !=  null
	 * <b> post: </b> se ha agregado el video a la base de datos en la transaction actual. pendiente que el buque master
	 * haga commit para que el buque baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el buque a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addBuque (BuqueGeneral buqueGeneral) throws SQLException, Exception{

		/*primero insertamos en la tabla madre (PERSONA), y luego en la tabla hija (EXPORTADOR) */

		// Primero el buque en la tabla BUQUE
		addBuqueT(buqueGeneral);

		// Para insertar en la tabla correcta, debemos crear los objetos correctos.
		String tipoDelBuque = buqueGeneral.getTipoBuqueGeneral();


		if (tipoDelBuque.equals("porta-contenedor")) {
			addBuquePortaContenedor(buqueGeneral);
		}
		else if (tipoDelBuque.equals("multiproposito")) {
			addBuqueMultiProposito(buqueGeneral);
		}
		else if (tipoDelBuque.equals("roro")) {
			addBuqueRoRo(buqueGeneral);
		}
		else {
			throw new Exception ("El tipo de buque no es valido");
		}

	}

	public void addBuqueT(BuqueGeneral buqueGeneral) throws  SQLException {
		// SQL para insertar en la tabla BUQUE
		String sqlMadre = "INSERT INTO BUQUE VALUES (";
		sqlMadre += "seq_buque.nextval" + ",'";
		sqlMadre += buqueGeneral.getNombre() + "',";
		sqlMadre += buqueGeneral.getCapacidad() + ",'";
		sqlMadre += buqueGeneral.getRegistroCapitania() + "','";
		sqlMadre += buqueGeneral.getAgenteMaritimo() + "','";
		sqlMadre += buqueGeneral.getTipoBuqueGeneral() + "')";

		System.out.println("SQL stmt:" + sqlMadre);

		PreparedStatement prepStmt = conn.prepareStatement(sqlMadre);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public void addBuquePortaContenedor (BuqueGeneral buqueGeneral) throws SQLException{
		String sqlHija= "INSERT INTO PORTACONTENEDOR VALUES (";
		sqlHija += "seq_buque.currval" + ",";
		sqlHija += buqueGeneral.getCapacidadContenedores() + ")";

		PreparedStatement prepStmt = conn.prepareStatement(sqlHija);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public void addBuqueMultiProposito (BuqueGeneral buqueGeneral) throws SQLException{
		String sqlHija= "INSERT INTO PORTA_CONTENEDOR VALUES (";
		sqlHija += "seq_buque.currval" + ")";

		PreparedStatement prepStmt = conn.prepareStatement(sqlHija);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public void addBuqueRoRo (BuqueGeneral buqueGeneral) throws SQLException{
		String sqlHija= "INSERT INTO PORTA_CONTENEDOR VALUES (";
		sqlHija += "seq_buque.currval" + ")";

		PreparedStatement prepStmt = conn.prepareStatement(sqlHija);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
    
    public Buque getBuquePorId(int idBuque) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Buque selectBuque(int idBuque) throws SQLException{
		//String SQL = "SET TRANSACTION ISOLATION LEVEL READONLY;";
		String SQL = "SELECT * FROM BUQUE WHERE ID="+idBuque;
		PreparedStatement prepStmt=conn.prepareStatement(SQL);
		recursos.add(prepStmt);
		ResultSet rS = prepStmt.executeQuery();
		String rId = rS.getString(1);
		String rNombre = rS.getString(2);
		String rCapacidad = rS.getString(3);
		String rRegistroCapitania = rS.getString(4);
		String rAgenteMaritimo = rS.getString(5);
		String rTipoBuque = rS.getString(6);
		Buque b = null;
		if(rId!=null&&rNombre!=null&&rCapacidad!=null&&rRegistroCapitania!=null&&rAgenteMaritimo!=null&&rTipoBuque!=null){
			int id = Integer.parseInt(rId);
			int capacidad = Integer.parseInt(rCapacidad);
			b = new Buque(id,rNombre,capacidad,rRegistroCapitania,rAgenteMaritimo,rTipoBuque);
		}
		return b;
	}

	/**
	 * Método que cambia el estado del buque al dado por parametro 
	 * @param estado  - estado del buque que se quiere cambiar en la base de datos
	 * @param idBuque - id del buque al que le queremos cambiar el estado
	 */
	public void cambiarEstadoBuque(int idBuque, String estado) throws Exception {
		String sql  = "UPDATE BUQUE SET ESTADO = "+ "'" + estado+ "'" + " WHERE ID= " + idBuque;
		System.out.println(sql);
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		
		//conn.commit();
		
	}

	private int getCapacidad(int idBuque) throws SQLException{
		int capacidad = -1;
		String sql = "SELECT CAPACIDAD_DISPONIBLE FROM BUQUE WHERE ID="+idBuque;
		PreparedStatement pS = conn.prepareStatement(sql);
		recursos.add(pS);
		ResultSet rS = pS.executeQuery();
		rS.next();
		String sCapacidad = rS.getString("CAPACIDAD_DISPONIBLE");
		if(sCapacidad!=null&&sCapacidad!=""){
			capacidad=Integer.parseInt(sCapacidad);
		}
		return capacidad;
	}
	
	private int getCapacidadMaxima(int idBuque) throws SQLException{
		int capacidad = -1;
		String sql = "SELECT CAPACIDAD_MAXIMA FROM BUQUE WHERE ID="+idBuque;
		PreparedStatement pS = conn.prepareStatement(sql);
		recursos.add(pS);
		ResultSet rS = pS.executeQuery();
		rS.next();
		String sCapacidad = rS.getString("CAPACIDAD_MAXIMA");
		if(sCapacidad!=null&&sCapacidad!=""){
			capacidad=Integer.parseInt(sCapacidad);
		}
		return capacidad;
	}
	
	public void actualizarCapacidad(int cargasCargadas, int idBuque) throws Exception { // se debería llamar reducir capacidad
		int capacidad = getCapacidad(idBuque);
		int nuevaCapacidad = capacidad - cargasCargadas;
		if(nuevaCapacidad >= 0){
			String sql = "UPDATE BUQUE SET CAPACIDAD_DISPONIBLE="+nuevaCapacidad+" WHERE ID="+idBuque;
			PreparedStatement pS = conn.prepareStatement(sql);
			recursos.add(pS);
			pS.executeQuery();
			conn.commit();
		}
		else{
			throw new Exception("Error al reducir la capacidad del buque");
		}
	}
		public void aumentarCapacidad(int cargasDescargadas, int idBuque) throws Exception { 
			int capacidad = getCapacidad(idBuque);
			int capacidadMaxima = getCapacidadMaxima(idBuque);
			int nuevaCapacidad = capacidad + cargasDescargadas;
			if(nuevaCapacidad <= capacidadMaxima){
				String sql = "UPDATE BUQUE SET CAPACIDAD_DISPONIBLE="+nuevaCapacidad+" WHERE ID="+idBuque;
				PreparedStatement pS = conn.prepareStatement(sql);
				recursos.add(pS);
				pS.executeQuery();
				conn.commit();
			}
			else{
				throw new Exception("Error al aumentar la capacidad del buque");
			}
		
	}

	
	
}



