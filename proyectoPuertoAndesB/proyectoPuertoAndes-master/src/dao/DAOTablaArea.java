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
import vos.ConsultaAreasAlmacenamientoGerente;
import vos.RespuestaConsultaArea;

public class DAOTablaArea {
	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Método constructor que crea DAOBuque <b>post: </b> Crea la instancia del
	 * DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaArea() {
		recursos = new ArrayList<Object>();
	}

	/**
	 * Método que cierra todos los recursos que estan en el arreglo de recursos
	 * <b>post: </b> Todos los recurso del arreglo de recursos han sido cerrados
	 */
	public void cerrarRecursos() {
		for (Object ob : recursos) {
			if (ob instanceof PreparedStatement)
				try {
					((PreparedStatement) ob).close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		}
	}

	/**
	 * Método que inicializa la connection del DAO a la base de datos con la
	 * conexión que entra como parámetro.
	 * 
	 * @param con
	 *          - connection a la base de datos
	 */
	public void setConn(Connection con) {
		this.conn = con;
	}

	/**
	 * Método que agrega el area de almacenamiento que entra como parámetro a la
	 * base de datos.
	 * 
	 * @param area
	 *          - el area a agregar. area != null <b> post: </b> se ha agregado el
	 *          area a la base de datos en la transaction actual.
	 * @throws SQLException
	 *           - Error con base de datos
	 * @throws Exception
	 *           - No pudo agregar el area a la base de datos
	 */
	public void addArea(Area area) throws SQLException, Exception {
		String SQL_INSERT_AREA = "";

		if (area.getIdCuartoFrio() != -1) {
			SQL_INSERT_AREA = "INSERT INTO AREA (ID, CUARTO_FRIO, TIPO) VALUES (";
			SQL_INSERT_AREA += "seq_area.nextval,"; // agregamos la secuencia para el
																							// id del area
			SQL_INSERT_AREA += area.getIdCuartoFrio() + ",'";
			SQL_INSERT_AREA += area.getTipoArea() + "')";
		} else {
			SQL_INSERT_AREA = "INSERT INTO AREA (ID, TIPO) VALUES (";
			SQL_INSERT_AREA += "seq_area.nextval," + ",'"; // agregamos la secuencia
																											// para el id del area
			SQL_INSERT_AREA += area.getTipoArea() + "')";
		}

		PreparedStatement prepStmt = conn.prepareStatement(SQL_INSERT_AREA);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	public Integer darIdAreaDisponiblePorCapacidadyTipo(int numeroCargas,
			String tipo) throws Exception {

		// como los tipos de area y carga no son iguales, sino que algunos tipos de
		// area pueden almacenar varios tipos de carga,
		// es necesario hacer una transformacion de los tipos. Eso, por mudularidad,
		// debe ser otro método en la clase area, creo.

		// 1. Miramos en que tipos de area se puede guardar el tipo de carga que
		// queremos descargar:
		ArrayList listaTiposPosibles = Area.getTiposAreaSegunTipoCarga(tipo);
		String sql = "SELECT ID FROM AREA WHERE ESTADO= " + "'" + Area.LIBERADA
				+ "'" + " ";
		sql += "AND CAPACIDAD_DISPONIBLE >= " + numeroCargas;

		// 2. Dependiendo de cuantos tipos se pueda reducimos el resultSet
		// concatenando AND a la sentencia sql
		for (int i = 0; i < listaTiposPosibles.size(); i++) {
			sql += " AND TIPO = " + "'" + listaTiposPosibles.get(i) + "'";
		}

		System.out.println(sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		rs.next();
		Integer id = rs.getInt("ID");
		if (id == null) {
			throw new Exception(
					"No hay areas que cumplan las condiciones solicitadas");
		} else {
			return id;
		}

	}

	public void reservarArea(Integer idAreaDisponible, Date fechaReserva)
			throws Exception {

		// primero cambiamos el estado del area en la tabla area
		String sql = "UPDATE AREA SET ESTADO = " + "'" + Area.RESERVADA + "'";
		sql += " WHERE ID = " + idAreaDisponible;
		System.out.println(sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		conn.commit();

		// ahora registramos la reserva en la tabla de reservas
		sql = "INSERT INTO RESERVAS_AREAS (NUM_ID_RESERVA,ID_AREA,FECHA_INICIO)";
		sql += "VALUES (" + "SEQ_RESERVAS_AREAS.nextval" + "," + idAreaDisponible
				+ "TO_DATE('" + fechaReserva + "', 'YYYY-MM-DD') ";
		sql += ")";

		conn.commit();
	}

	private int getCapacidad(int idArea) throws SQLException {
		int capacidad = -1;
		// String sql = "SET TRANSACTION ISOLATION LEVEL READONLY;";
		String sql = "SELECT CAPACIDAD_DISPONIBLE FROM AREA WHERE ID=" + idArea;
		PreparedStatement pS = conn.prepareStatement(sql);
		recursos.add(pS);
		ResultSet rS = pS.executeQuery();
		rS.next();
		String sCapacidad = rS.getString("CAPACIDAD_DISPONIBLE");
		if (sCapacidad != null && sCapacidad != "") {
			capacidad = Integer.parseInt(sCapacidad);
		}
		return capacidad;
	}

	public int getCapacidadMaxima(int idArea) throws SQLException {
		int capacidad = -1;
		// String sql = "SET TRANSACTION ISOLATION LEVEL READONLY;";
		String sql = "SELECT CAPACIDAD_MAXIMA FROM AREA WHERE ID=" + idArea;
		PreparedStatement pS = conn.prepareStatement(sql);
		recursos.add(pS);
		ResultSet rS = pS.executeQuery();
		rS.next();
		String sCapacidad = rS.getString("CAPACIDAD_MAXIMA");
		if (sCapacidad != null && sCapacidad != "") {
			capacidad = Integer.parseInt(sCapacidad);
		}
		return capacidad;
	}

	public void reducirCapacidad(int numeroCargas, Integer idArea)
			throws Exception {
		int capacidad = getCapacidad(idArea);
		int nuevaCapacidad = capacidad - numeroCargas;

		if (capacidad != -1 && nuevaCapacidad >= 0) {
			// String sql = "SET TRANSACTION ISOLATION LEVEL READ COMMITTED;";
			String sql = "UPDATE BUQUE SET CAPACIDAD_DISPONIBLE =" + nuevaCapacidad
					+ " WHERE ID=" + idArea;
			PreparedStatement pS = conn.prepareStatement(sql);
			recursos.add(pS);
			pS.executeQuery();
			conn.commit();
		} else {
			throw new Exception("Error al reducir la capacidad del area");
		}
	}

	public void aumentarCapacidad(int numeroCargas, Integer idArea)
			throws Exception {
		int capacidad = getCapacidad(idArea);
		int capacidadMaxima = getCapacidadMaxima(idArea);
		int nuevaCapacidad = capacidad + numeroCargas;
		if (nuevaCapacidad <= capacidadMaxima) {
			// String sql = "SET TRANSACTION ISOLATION LEVEL READ COMMITTED;";
			String sql = "UPDATE BUQUE SET CAPACIDAD_DISPONIBLE=" + nuevaCapacidad
					+ " WHERE ID=" + idArea;
			PreparedStatement pS = conn.prepareStatement(sql);
			recursos.add(pS);
			pS.executeQuery();
			conn.commit();
		} else {
			throw new Exception("Error al aumentar la capacidad del area");
		}
	}

	// CR
	public void cambiarEstado(String estado, Integer idArea) throws SQLException {
		// String sql = "SET TRANSACTION ISOLATION LEVEL READ COMMITTED;";
		String sql = "UPDATE AREA SET ESTADO=" + estado + " WHERE ID=" + idArea;
		PreparedStatement pS = conn.prepareStatement(sql);
		recursos.add(pS);
		pS.executeQuery();
		conn.commit();
	}

	public int getCapacidadDisponible(Integer idArea) throws SQLException {
		// String sql = "SET TRANSACTION ISOLATION LEVEL READONLY;";
		String sql = "SELECT CAPACIDAD_DISPONIBLE FROM AREA WHERE ID=" + idArea;
		PreparedStatement pS = conn.prepareStatement(sql);
		recursos.add(pS);
		ResultSet rS = pS.executeQuery();
		rS.next();
		return rS.getInt("CAPACIDAD_DISPONIBLE");
	}

	public String getTipo(int idArea) throws SQLException {
		// String sql = "SET TRANSACTION ISOLATION LEVEL READONLY;";
		String sql = "SELECT TIPO FROM AREA WHERE ID=" + idArea;
		PreparedStatement pS = conn.prepareStatement(sql);
		recursos.add(pS);
		ResultSet rS = pS.executeQuery();
		rS.next();
		return rS.getString("TIPO");
	}

	public ArrayList<RespuestaConsultaArea> consultarAreasAlmacenamiento(
			ConsultaAreasAlmacenamientoGerente consulta) throws Exception {
		ArrayList<RespuestaConsultaArea> respuestas = new ArrayList();
		int filtrarEstado = consulta.getFiltrarEstado();
		int filtrarFechas = consulta.getFiltrarFechas();

		String sql = "SELECT * FROM AREA WHERE ";
		if (filtrarEstado == 1) {
			sql += "ESTADO = " + "'" + consulta.getEstado() + "'" + " ";
		}
		System.out.println(sql);
		PreparedStatement pS = conn.prepareStatement(sql);
		recursos.add(pS);
		ResultSet rs = pS.executeQuery();
		while (rs.next()) {
			int idArea = rs.getInt("ID");
			int capacidad_disponible = rs.getInt("CAPACIDAD_DISPONIBLE");
			String estado = rs.getString("ESTADO");
			int capacidad_maxima = rs.getInt("CAPACIDAD_MAXIMA");
			int numeroCargas = capacidad_maxima - capacidad_disponible;
			RespuestaConsultaArea rca = new RespuestaConsultaArea(idArea,
					capacidad_maxima, estado, numeroCargas);
			respuestas.add(rca);
		}
		return respuestas;
	}

	public boolean ExisteAreaDisponibleConCapacidad(int capacidad, String tipoArea) throws Exception{
		boolean respuesta  = false;
		String sql = "SElECT * FROM AREA WHERE";
		sql += " ESTADO = " + Area.LIBERADA;
		sql += " AND TIPO = " + tipoArea;
		sql += " AND CAPACIDAD_DISPONIBLE >= " + capacidad;
		System.out.println(sql);
		PreparedStatement pS = conn.prepareStatement(sql);
		recursos.add(pS);
		ResultSet rs = pS.executeQuery();
		respuesta = rs.next();
		return respuesta;
	}
	
	public int darIdAreaDisponibleConCapacidad(int capacidad, String tipoArea) throws Exception {
		String sql = "SElECT * FROM AREA WHERE";
		sql += " ESTADO = " + Area.LIBERADA;
		sql += " AND TIPO = " + tipoArea;
		sql += " AND CAPACIDAD_DISPONIBLE >= " + capacidad;
		System.out.println(sql);
		PreparedStatement pS = conn.prepareStatement(sql);
		recursos.add(pS);
		ResultSet rs = pS.executeQuery();
		boolean existe = rs.next();
		if(existe){
			return rs.getInt("ID");
		}
		return -1;
	}
	
	

	/*
	 * public Area getAreaPorId(int idArea) throws SQLException { return
	 * selectArea(idArea); }
	 */

	/*
	 * private Area selectArea(int idArea) throws SQLException{ String SQL =
	 * "SELECT * FROM AREA WHERE ID="+idArea+";"; PreparedStatement
	 * prepStmt=conn.prepareStatement(SQL); recursos.add(prepStmt); ResultSet rS =
	 * prepStmt.executeQuery(); String rId = rS.getString(1); String rCuartoFrio =
	 * rS.getString(2); String rTipo = rS.getString(3); Area a = null;
	 * if(rId!=null&&rTipo!=null&&rCuartoFrio==null){ int id =
	 * Integer.parseInt(rId); String tipo = rTipo; a = new Area(id, tipo); } else
	 * if(rId!=null&&rTipo!=null&&rCuartoFrio!=null){ int id =
	 * Integer.parseInt(rId); String tipo = rTipo; int cuartoFrio =
	 * Integer.parseInt(rS.getString(2)); a = new Area(id, tipo);
	 * a.setCuartoFrio(cuartoFrio); } return a; }
	 */

}
