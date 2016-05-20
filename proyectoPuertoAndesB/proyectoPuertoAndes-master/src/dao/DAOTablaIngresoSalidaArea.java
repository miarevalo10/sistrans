package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import vos.Area;
import vos.Bodega;
import vos.Buque;
import vos.Carga;

import vos.RegistroArea;
import vos.RegistroArriboOSalida;
import vos.RegistroCargaBuque;
import vos.RegistroEntregaCargaImportador;
import vos.RegistroLlegadaCargaArea;
import vos.RegistroMovimientoCarga;

public class DAOTablaIngresoSalidaArea {
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
	public DAOTablaIngresoSalidaArea() {
		recursos = new ArrayList<Object>();
	}

	private int id = 0;

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
	/*
	 * public void addIngresoSalidaArea(Carga carga, Area area, Date fecha) throws
	 * SQLException { this.insertIngresoSalidaArea(carga,area,fecha); }
	 * 
	 * //<<<<<<< HEAD
	 * 
	 * private void insertIngresoSalidaArea(Carga carga, Area area, Date fecha)
	 * throws SQLException{ String SQL_STMT=
	 * "INSERT INTO INGRESO_SALIDA_AREA VALUES ("; //Secuencia SQL_STMT+=id; id++;
	 * 
	 * SQL_STMT+=carga.getId()+",'"; SQL_STMT+=area.getId()+",'";
	 * SQL_STMT+=carga.getVieneDe()+",'"; if(carga.getFechaIngreso()==null){
	 * SQL_STMT+=fecha.toString()+",'"; SQL_STMT+="NULL'"; } else{
	 * SQL_STMT+=carga.getFechaIngreso().toString(); SQL_STMT+=fecha.toString(); }
	 * PreparedStatement prepStmt=conn.prepareStatement(SQL_STMT);
	 * recursos.add(prepStmt); prepStmt.executeQuery(); } //======= //>>>>>>>
	 * origin/master public String[] darRegistroCarga(int idCarga) throws
	 * SQLException{ return this.selectRegistro(idCarga); }
	 * 
	 * private String[] selectRegistro(int idCarga) throws SQLException { String[]
	 * registro = new String[7]; String SQL =
	 * "SELECT * FROM INGRESO_SALIDA_AREA WHERE ID_CARGA="+idCarga+";";
	 * PreparedStatement pS = conn.prepareStatement(SQL); recursos.add(pS);
	 * ResultSet rS=pS.executeQuery(); registro[1]=rS.getString(1);
	 * registro[2]=rS.getString(2); registro[3]=rS.getString(3);
	 * registro[4]=rS.getString(4); registro[5]=rS.getString(5);
	 * registro[6]=rS.getString(6); registro[7]=rS.getString(7); return registro;
	 * }
	 * 
	 * public void registrarEntregaCargaImportador(Carga c, Area a) throws
	 * SQLException { this.insertEntregaCargaImportador(c,a); }
	 * 
	 * private void insertEntregaCargaImportador(Carga c, Area a) throws
	 * SQLException { String SQL = "INSERT INTO INGRESO_SALIDA_AREA " +
	 * "SELECT ID_CARGA, ID_AREA, VIENE_DE, FECHA_INGRESO, FECHA_SALIDA, ID_RESPONSABLE "
	 * +
	 * "FROM INGRESO_SALIDA_AREA JOIN CARGA ON INGRESO_SALIDA_AREA.ID_CARGA=CARGA.ID "
	 * + "WHERE ID_CARGA="+c.getId()+" AND ID_AREA="+a.getId()+
	 * " AND VIENE_DE=\"BUQUE\" AND FECHA_INGRESO="+c.getFechaIngreso()+
	 * " AND FECHA_SALIDA IS NULL AND CARGA.VOLUMEN="+c.getVolumen()+";";
	 * PreparedStatement pS = conn.prepareStatement(SQL); recursos.add(pS);
	 * pS.executeQuery(); }
	 */

	/*
	 * public void registrarSalidaAreaExportador(Carga c, Buque b, Date f) throws
	 * SQLException { this.updateEntregaCargaExportador(c,b,f); }
	 * 
	 * private void updateEntregaCargaExportador(Carga c, Buque b, Date f) throws
	 * SQLException { String SQL =
	 * "UPDATE INGRESO_SALIDA_AREA SET FECHA_SALIDA = "+f.toString()+
	 * " WHERE VIENE_DE=\"CAMION\" AND ID_CARGA="+c.getId()+";"; PreparedStatement
	 * pS = conn.prepareStatement(SQL); recursos.add(pS); pS.executeQuery(); }
	 */

	public void registrarLlegadaCargaArea(
			RegistroLlegadaCargaArea registroLlegada) throws Exception {
		java.util.Date fechaLlegada = registroLlegada.getFechaLLegada();
		java.sql.Date fechaLlegadaSql = new java.sql.Date(fechaLlegada.getTime());

		String sql = "INSERT INTO INGRESO_SALIDA_AREA ";
		sql += "(ID, ID_CARGA, ID_AREA, VIENE_DE, FECHA_INGRESO, ID_IMPORTADOR) ";
		sql += "VALUES (";
		sql += "SEQ_CARGA_INGRESO.nextval," + registroLlegada.getIdCarga() + ","
				+ registroLlegada.getIdArea() + ",";
		sql += "'" + registroLlegada.getVieneDe() + "',";
		sql += "TO_DATE('" + fechaLlegadaSql + "', 'YYYY-MM-DD') " + ",";
		sql += registroLlegada.getIdImportador() + ")";
		System.out.println(sql);
		PreparedStatement pS = conn.prepareStatement(sql);
		recursos.add(pS);
		pS.executeQuery();

		// conn.commit();

	}

	public void registrarEntregaCargaImportador(
			RegistroEntregaCargaImportador registroEntregaCargaImportador)
			throws Exception {

		String confirmacionDatos = "SELECT * FROM INGRESO_SALIDA_AREA WHERE ";
		confirmacionDatos += "ID_CARGA = "
				+ registroEntregaCargaImportador.getIdCarga();

		PreparedStatement pS = conn.prepareStatement(confirmacionDatos);
		recursos.add(pS);
		ResultSet rs = pS.executeQuery();
		rs.next();
		int idRegistro = rs.getInt("ID"); // obtenemos el id del registro para luego
																			// hacer el update.
		int idArea = rs.getInt("ID_AREA");
		int idImportador = rs.getInt("ID_IMPORTADOR");

		// revisamos si la informacion coincide, falta revisar el volumen desde la
		// tabla carga.
		boolean coincide = (idArea == registroEntregaCargaImportador.getIdArea()
				&& idImportador == registroEntregaCargaImportador.getIdImportador());
		if (coincide) {
			String registroEntrega = "UPDATE INGRESO_SALIDA_AREA SET FECHA_SALIDA = ";
			registroEntrega += "TO_DATE('"
					+ registroEntregaCargaImportador.getFechaEntrega()
					+ "', 'YYYY-MM-DD') ";

			PreparedStatement registro = conn.prepareStatement(registroEntrega);
			recursos.add(registro);
			pS.executeQuery();
		} else {
			throw new Exception(
					"La informacion no coincide, no se puede registrar la salida");
		}

	}

	public void registrarSalidaCargaArea(RegistroCargaBuque registroCargaBuque)
			throws Exception {
		System.out.println(
				"Entra a registrarSalidaCargaArea en DAOTablaIngresoSalidaArea");
		java.util.Date fechaSalida = (java.util.Date) registroCargaBuque.fechaSalida;
		java.sql.Date fechaSalidaSQL = new java.sql.Date(fechaSalida.getTime());
		// String sql = "SET TRANSACTION ISOLATION LEVEL READ COMMITTED;";
		String sql = "UPDATE INGRESO_SALIDA_AREA SET FECHA_SALIDA=" + fechaSalidaSQL
				+ " WHERE ID_CARGA=" + registroCargaBuque.idCarga + " AND VIENE_DE="
				+ Carga.CAMION + ";";
		PreparedStatement pS = conn.prepareStatement(sql);
		recursos.add(pS);
		pS.executeQuery();
		conn.commit();
	}

	public void ejecutarSavePointIngresoCargaArea() throws Exception {
		String sql = "SAVEPOINT carga-ingresada";
		PreparedStatement pS = conn.prepareStatement(sql);
		recursos.add(pS);
		pS.executeQuery();
	}

	// RF10
	/*
	 * public void cambiarCargaDeArea(int idAreaAnterior, int idAreaNueva, int
	 * idCarga, java.util.Date fechaCambio) throws SQLException{ // OBTENER
	 * INFORMACION DEL REGISTRO ANTERIOR <<<<<<< Updated upstream ======= <<<<<<<
	 * HEAD
	 * 
	 * //String sql1 = "SET TRANSACTION ISOLATION LEVEL READONLY;"; String sql1 =
	 * "SELECT ID, VIENE_DE, ID_IMPORTADOR FROM INGRESO_SALIDA_AREA WHERE ID_CARGA="
	 * +idCarga+" AND ID_AREA="+idAreaAnterior+";";;
	 * 
	 * ======= >>>>>>> Stashed changes String sql1MAl =
	 * "SET TRANSACTION ISOLATION LEVEL READONLY"; sql1 +=
	 * "SELECT ID, VIENE_DE, ID_IMPORTADOR FROM INGRESO_SALIDA_AREA WHERE ID_CARGA="
	 * +idCarga+" AND ID_AREA="+idAreaAnterior+";";; //String sql1 =
	 * "SET TRANSACTION ISOLATION LEVEL READONLY;"; String sql1 =
	 * "SELECT ID, VIENE_DE, ID_IMPORTADOR FROM INGRESO_SALIDA_AREA WHERE ID_CARGA="
	 * +idCarga+" AND ID_AREA="+idAreaAnterior+";";; <<<<<<< Updated upstream
	 * ======= >>>>>>> origin/master >>>>>>> Stashed changes PreparedStatement pS1
	 * = conn.prepareStatement(sql1); recursos.add(pS1); ResultSet rS1 =
	 * pS1.executeQuery(); rS1.next(); String vieneDeRegistroAnterior =
	 * rS1.getString("VIENE_DE"); int idImportadorRegistroAnterior =
	 * rS1.getInt("ID_IMPORTADOR"); // SACAR DEL AREA ANTERIOR java.sql.Date
	 * fechaCambioSql = new java.sql.Date(fechaCambio.getTime()); //String sql2 =
	 * "SET TRANSACTION ISOLATION LEVEL READ COMMITTED "; String sql2 =
	 * "UPDATE INGRESO_SALIDA_AREA SET FECHA_SALIDA ="+fechaCambioSql+
	 * " WHERE ID_CARGA="+idCarga+";";; PreparedStatement pS2 =
	 * conn.prepareStatement(sql2); recursos.add(pS2); pS2.executeQuery(); //
	 * INSERTAR EN EL AREA NUEVA //String sql3 =
	 * "SET TRANSACTION ISOLATION LEVEL READ COMMITTED;"; String sql3 =
	 * "INSERT INTO INGRESO_SALIDA_AREA VALUES ("; sql3 +=
	 * "ID="+"SEQ_CARGA_INGRESO.nextval,"; sql3 += "ID_CARGA ="+idCarga+", "; sql3
	 * += "ID_AREA ="+idAreaNueva+", "; sql3 += "VIENE_DE ="
	 * +vieneDeRegistroAnterior+", "; sql3 += "FECHA_INGRESO ="+fechaCambioSql+
	 * ", "; //sql3 += "FECHA_SALIDA ="+ <null> +", "; sql3 += "ID_IMPORTADOR ="
	 * +idImportadorRegistroAnterior+");"; PreparedStatement pS3 =
	 * conn.prepareStatement(sql3); recursos.add(pS3); pS3.executeQuery();
	 * conn.commit(); }
	 */

	public ArrayList<Integer> darCargasPorArea(int idArea) throws SQLException {
		ArrayList<Integer> cargas = new ArrayList<Integer>();
		// String sql = "SET TRANSACTION ISOLATION LEVEL READONLY;";
		String sql = "SELECT ID_CARGA FROM INGRESO_SALIDA_AREA WHERE ID_AREA="
				+ idArea + " AND FECHA_SALIDA IS NULL" + ";";
		PreparedStatement pS = conn.prepareStatement(sql);
		recursos.add(pS);
		ResultSet rS = pS.executeQuery();
		rS.next();
		while (rS.next()) {
			cargas.add(rS.getInt("ID_CARGA"));
		}
		return cargas;
	}

	public ArrayList<RegistroMovimientoCarga> consultaMovimientoCargas(
			int volumen, String tipoCarga) throws SQLException {
		ArrayList<RegistroMovimientoCarga> listaRegistros = new ArrayList<>();
		String sql = "SELECT CAR.ID, CAR.TIPO, CAR.FECHA_ORDEN, CAR.ORIGEN, CAR.DESTINO, ISA.ID_IMPORTADOR, CAR.ID_EXPORTADOR";
		sql += " FROM CARGA CAR INNER JOIN INGRESO_SALIDA_AREA ISA ON CAR.ID = ISA.ID_CARGA";
		sql += " WHERE CAR.TIPO = " + "'" + tipoCarga + "'";
		sql += " AND CAR.VOLUMEN > " + "'" + volumen + "'";

		PreparedStatement ps = conn.prepareStatement(sql);
		recursos.add(ps);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			int idCarga = rs.getInt("ID");

			String tipo = rs.getString("TIPO");
			java.util.Date fechaOrdenUtil = null;
			Timestamp timestampOrden = rs.getTimestamp("FECHA_ORDEN");
			if (timestampOrden != null) {
				fechaOrdenUtil = new java.util.Date(timestampOrden.getTime());
			}
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String fechaOrdenString = df.format(fechaOrdenUtil);

			String origen = rs.getString("ORIGEN");
			String destino = rs.getString("DESTINO");
			int idImportador = rs.getInt("ID_IMPORTADOR");
			int idExportador = rs.getInt("IS_EXPORTADOR");

			RegistroMovimientoCarga registroMovimientoCarga = new RegistroMovimientoCarga(
					idCarga, tipo, origen, destino, fechaOrdenString, idImportador,
					idExportador);
			
			listaRegistros.add(registroMovimientoCarga);

		}

		return listaRegistros;

	}
	//RFC10
	public ArrayList<RegistroArea> consultarAreasDeAlmacenamiento(int idArea1, int idArea2) throws SQLException {
		ArrayList <RegistroArea> registros = new ArrayList<RegistroArea>();
		String sql = "SELECT ISA.ID_CARGA, ISA.ID_AREA, ISA.VIENE_DE, ISA.FECHA_INGRESO, ISA.FECHA_SALIDA, C.ESTADO ";
		sql += "FROM INGRESO_SALIDA_AREA ISA JOIN CARGA C ON ISA.ID_CARGA=C.ID ";
		sql += "WHERE ISA.ID_AREA = "+idArea1 +" OR ISA.ID_AREA = "+ idArea2;
		System.out.println(sql);
	    PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		while (rs.next()) {
			int idCarga = rs.getInt("ID_CARGA");
			int idArea = rs.getInt("ID_AREA");
			String vieneDe = rs.getString("VIENE_DE");
			java.util.Date fechaIngresoUtil = null;
		    Timestamp timestampIngreso = rs.getTimestamp("FECHA_INGRESO");
		    if (timestampIngreso != null) {
		    	fechaIngresoUtil = new java.util.Date(timestampIngreso.getTime());
		    }
		    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		    String fechaIngresoString = df.format(fechaIngresoUtil);
		    Timestamp timestampSalida = rs.getTimestamp("FECHA_SALIDA");
		    if (timestampSalida != null) {
		    	fechaIngresoUtil = new java.util.Date(timestampSalida.getTime());
		    }
		    String fechaSalidaString = df.format(timestampSalida);
		    String estado = rs.getString("ESTADO");
		    registros.add(new RegistroArea(idCarga, idArea, vieneDe, fechaIngresoString, fechaSalidaString, estado));
		}
		return registros;
	}	
}
