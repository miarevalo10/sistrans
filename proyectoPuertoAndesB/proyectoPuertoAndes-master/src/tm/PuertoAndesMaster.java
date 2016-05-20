package tm;

import java.awt.List;
/* importaciones de Java*/
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/*Importaciones de los paquetes dao y vos */
import dao.*;
import vos.*;

public class PuertoAndesMaster {

	/**
	 * Atributo estatico que simboliza un estado en el que un puerto no tiene un
	 * area disponible dada una capacidad necesaria para la descarga
	 */
	private static final String NO_DISPONIBLE = "NO_DISPONIBLE";

	/**
	 * Atributo estatico que simboliza un estado en el que un puerto tiene un area
	 * disponible dada una capacidad necesaria para la descarga
	 */
	private static final String DISPONIBLE = "DISPONIBLE";

	/* Atributos para la conexion */
	/**
	 * Atributo estático que contiene el path relativo del archivo que tiene los
	 * datos de la conexión con Oracle
	 */
	private static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";

	/**
	 * Atributo estático que contiene el path absoluto del archivo que tiene los
	 * datos de la conexión
	 */
	private String connectionDataPath;

	/**
	 * Atributo que guarda el usuario que se va a usar para conectarse a la base
	 * de datos.
	 */
	private String user;

	/**
	 * Atributo que guarda la clave que se va a usar para conectarse a la base de
	 * datos.
	 */
	private String password;

	/**
	 * Atributo que guarda el URL que se va a usar para conectarse a la base de
	 * datos.
	 */
	private String url;

	/**
	 * Atributo que guarda el driver que se va a usar para conectarse a la base de
	 * datos.
	 */
	private String driver;

	/**
	 * Conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Método constructor de la clase PuertoAndesMaster, esta clase modela y
	 * contiene cada una de las transacciones y la logica de negocios que estas
	 * conllevan. <b>post: </b> Se crea el objeto PuertooAndesMaster, se
	 * inicializa el path absoluto de el archivo de conexión y se inicializa los
	 * atributos que se usan par la conexión a la base de datos.
	 * 
	 * @param contextPathP
	 *          - path absoluto en el servidor del contexto del deploy actual
	 */
	public PuertoAndesMaster(String contextPathP) {
		connectionDataPath = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
		initConnectionData();
	}

	/*
	 * Método que inicializa los atributos que se usan para la conexion a la base
	 * de datos. <b>post: </b> Se han inicializado los atributos que se usan par
	 * la conexión a la base de datos.
	 */
	private void initConnectionData() {
		try {
			File arch = new File(this.connectionDataPath);
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream(arch);
			prop.load(in);
			in.close();
			this.url = prop.getProperty("url");
			this.user = prop.getProperty("usuario");
			this.password = prop.getProperty("clave");
			this.driver = prop.getProperty("driver");
			Class.forName(driver);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * Método que retorna la conexión a la base de datos
	 * 
	 * @return Connection - la conexión a la base de datos
	 * @throws SQLException
	 *           - Cualquier error que se genere durante la conexión a la base de
	 *           datos de Oracle
	 */
	private Connection darConexion() throws SQLException {
		System.out.println("Connecting to: " + url + " With user: " + user);
		return DriverManager.getConnection(url, user, password);
	}

	////////////////////////////////////////
	/////// Transacciones////////////////////
	////////////////////////////////////////

	/**
	 * Método que modela la transacción que agrega un solo importador a la base de
	 * datos. <b> post: </b> se ha agregado el importador que entra como parámetro
	 * 
	 * @param importador
	 *          - el importador a agregar. importador != null
	 * @throws Exception
	 *           - cualquier error que se genera agregando el importador
	 */
	public void addImportador(Importador importador) throws Exception {
		DAOTablaImportador daoImportador = new DAOTablaImportador();

		// en la trasaccion puede haber errores, así que manejamos la cosa con un
		// try y excepciones
		try {
			this.conn = darConexion();
			daoImportador.setConn(conn);
			daoImportador.addImportador(importador);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoImportador.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println(
						"SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

	}

	/**
	 * Método que modela la transacción que agrega un solo exportador a la base de
	 * datos. <b> post: </b> se ha agregado el exportador que entra como parámetro
	 * 
	 * @param exportador
	 *          - el exportador a agregar. exportador != null
	 * @throws Exception
	 *           - cualquier error que se genera agregando el exportador
	 */
	public void addExportador(Exportador exportador) throws Exception {
		DAOTablaExportador daoExportador = new DAOTablaExportador();

		// en la trasaccion puede haber errores, así que manejamos la cosa con un
		// try y excepciones
		try {
			this.conn = darConexion();
			daoExportador.setConn(conn);
			daoExportador.addExportador(exportador);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoExportador.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println(
						"SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

	}

	/**
	 * Método que agrega una bodega a la base de datos. <b> post: </b> se ha
	 * agregado la bodega que entra como parámetro
	 * 
	 * @param bodega
	 *          - objeto tipo bodega que se desea agregar. bodega != null
	 * @throws Exception
	 *           - cualquier error que se genera agregando el objeto
	 */
	public void addBodega(Bodega bodega) throws Exception {
		DAOTablaArea daoArea = new DAOTablaArea();
		DAOTablaBodega daoBodega = new DAOTablaBodega();
		try {
			this.conn = darConexion();
			daoArea.setConn(conn);
			daoArea.addArea(bodega);
			daoBodega.setConn(conn);
			daoBodega.addBodega(bodega);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoArea.cerrarRecursos();
				daoBodega.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println(
						"SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Método que agrega un patio a la base de datos. <b> post: </b> se ha
	 * agregado el patio que entra como parámetro
	 * 
	 * @param patio
	 *          - objeto tipo patio que se desea agregar. patio != null
	 * @throws Exception
	 *           - cualquier error que se genera agregando el obejto
	 *//*
		 * public void addPatio(Patio patio) throws Exception { DAOTablaArea daoArea
		 * = new DAOTablaArea(); DAOTablaPatio daoPatio = new DAOTablaPatio(); try{
		 * this.conn = darConexion(); daoArea.setConn(conn); daoArea.addArea(patio);
		 * daoPatio.setConn(conn); daoPatio.addPatio(patio); conn.commit(); } catch
		 * (SQLException e) { System.err.println("SQLException:" + e.getMessage());
		 * e.printStackTrace(); throw e; } catch (Exception e) {
		 * System.err.println("GeneralException:" + e.getMessage());
		 * e.printStackTrace(); throw e; } finally { try { daoArea.cerrarRecursos();
		 * daoPatio.cerrarRecursos(); if(this.conn!=null) this.conn.close(); } catch
		 * (SQLException exception) { System.err.println(
		 * "SQLException closing resources:" + exception.getMessage());
		 * exception.printStackTrace(); throw exception; } } }
		 */
	/**
	 * Método que agrega un cobertizo a la base de datos. <b> post: </b> se ha
	 * agregado el cobertizo que entra como parámetro
	 * 
	 * @param cobertizo
	 *          - objeto tipo cobertizo que se desea agregar. cobertizo != null
	 * @throws Exception
	 *           - cualquier error que se genera agregando el objeto
	 */
	public void addCobertizo(Cobertizo cobertizo) throws Exception {
		DAOTablaArea daoArea = new DAOTablaArea();
		DAOTablaCobertizo daoCobertizo = new DAOTablaCobertizo();
		Set name = new HashSet<>();
		try {
			this.conn = darConexion();
			daoArea.setConn(conn);
			daoArea.addArea(cobertizo);
			daoCobertizo.setConn(conn);
			daoCobertizo.addCobertizo(cobertizo);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoArea.cerrarRecursos();
				daoCobertizo.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println(
						"SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Método que agrega un silo a la base de datos. <b> post: </b> se ha agregado
	 * el silo que entra como parámetro
	 * 
	 * @param silo
	 *          - objeto tipo silo que se desea agregar. silo != null
	 * @throws Exception
	 *           - cualquier error que se genera agregando el objeto
	 */
	public void addSilo(Silo silo) throws Exception {
		DAOTablaArea daoArea = new DAOTablaArea();
		DAOTablaSilo daoSilo = new DAOTablaSilo();
		try {
			this.conn = darConexion();
			daoArea.setConn(conn);
			daoArea.addArea(silo);
			daoSilo.setConn(conn);
			daoSilo.addSilo(silo);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoArea.cerrarRecursos();
				daoSilo.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println(
						"SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Método que modela la transacción que agrega un solo buque a la base de
	 * datos. <b> post: </b> se ha agregado el buque que entra como parámetro
	 * 
	 * @param buqueGeneral
	 *          - el buque a agregar. buqueGeneral != null
	 * @throws Exception
	 *           - cualquier error que se genera agregando el buque
	 */

	public void addBuque(BuqueGeneral buqueGeneral) throws Exception {
		DAOTablaBuque daoBuque = new DAOTablaBuque();

		try {
			this.conn = darConexion();
			daoBuque.setConn(conn);
			daoBuque.addBuque(buqueGeneral);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoBuque.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println(
						"SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

	}

	/**
	 * Método que modela la transacción que registra el arribo de un buque en la
	 * base de datos. <b> post: </b> se ha registrado el arribo de un buque que
	 * entra como parámetro
	 * 
	 * @param registroArribo
	 *          - el arribo del buque a registrar . registroArribo != null
	 * @throws Exception
	 *           - cualquier error que se genera registrando el ingreso del buque
	 */

	public void addRegistroArriboBuque(RegistroArriboOSalida registroArribo)
			throws Exception {
		DAOTablaIngresoSalidaBuque daoTablaISBuque = new DAOTablaIngresoSalidaBuque();
		try {
			this.conn = darConexion();
			daoTablaISBuque.setConn(conn);
			daoTablaISBuque.addRegistroArriboBuque(registroArribo);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoTablaISBuque.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println(
						"SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

	}

	/**
	 * Método que modela la transacción que registra la salida de un buque en la
	 * base de datos. <b> post: </b> se ha registrado la salida de un buque que
	 * entra como parámetro
	 * 
	 * @param registroSalida
	 *          - el arribo del buque a registrar . registroSalida != null
	 * @throws Exception
	 *           - cualquier error que se genera registrando la salida del buque
	 */

	public void addRegistroSalidaBuque(RegistroArriboOSalida registroSalida)
			throws Exception {
		DAOTablaIngresoSalidaBuque daoTablaISBuque = new DAOTablaIngresoSalidaBuque();
		try {
			this.conn = darConexion();
			daoTablaISBuque.setConn(conn);
			daoTablaISBuque.addRegistroSalidaBuque(registroSalida);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoTablaISBuque.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println(
						"SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

	}

	/**
	 * Método que modela la transacción que registra la factura generada a un
	 * exportador en la base de datos. <b> post: </b> se ha registrado la factura
	 * generada a un exportador
	 * 
	 * @param registroFactura
	 *          - objeto contenedor que contiene una fechaFactura que es la en la
	 *          que se genera la factura. fechaFactura != null y un buqueFactura -
	 *          buque contratado por el exportador. buqueFactura != null
	 * @throws Exception
	 *           - cualquier error que se genera registrando la factura generada a
	 *           un exportador
	 */

	public void addRegistroFactura(
			RegistroFacturaExportador registroFacturaExportador) throws Exception {
		DAOTablaFactura daoTablaFactura = new DAOTablaFactura();

		try {
			this.conn = darConexion();
			daoTablaFactura.setConn(conn);
			daoTablaFactura.addRegistroFacturaExportador(registroFacturaExportador);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoTablaFactura.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println(
						"SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

	}

	/**
	 * Método que modela la transacción que agrega un solo video a la base de
	 * datos. <b> post: </b> se ha agregado el video que entra como parámetro
	 * 
	 * @param video
	 *          - el video a agregar. video != null
	 * @throws Exception
	 *           - cualquier error que se genera agregando el video
	 */
	public void addVideo(Video video) throws Exception {
		DAOTablaVideos daoVideos = new DAOTablaVideos();
		try {
			////// Transacción
			this.conn = darConexion();
			daoVideos.setConn(conn);
			daoVideos.addVideo(video);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoVideos.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println(
						"SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void registrarEntregaCargaImportador(
			RegistroEntregaCargaImportador registroEntregaCargaImportador)
			throws Exception {
		// TODO Auto-generated method stub
		DAOTablaIngresoSalidaArea daoIngresoSalidaArea = new DAOTablaIngresoSalidaArea();
		try {
			////// Transacción
			this.conn = darConexion();
			daoIngresoSalidaArea.setConn(conn);
			daoIngresoSalidaArea
					.registrarEntregaCargaImportador(registroEntregaCargaImportador);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoIngresoSalidaArea.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println(
						"SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

	}

	public void registrarLlegadaCargaArea(
			RegistroLlegadaCargaArea registroLlegada) throws Exception {
		DAOTablaIngresoSalidaArea daoIngresoSalidaArea = new DAOTablaIngresoSalidaArea();
		try {
			////// Transacción
			this.conn = darConexion();
			daoIngresoSalidaArea.setConn(conn);
			daoIngresoSalidaArea.registrarLlegadaCargaArea(registroLlegada);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoIngresoSalidaArea.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println(
						"SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

	}

	public void addRegistroDescargaBuque(RegistroDescargaBuque resDescarga)
			throws Exception {

		DAOTablaIngresoSalidaArea daoIngresoSalidaArea = new DAOTablaIngresoSalidaArea();
		DAOTablaReservaArea daoReservarArea = new DAOTablaReservaArea();
		DAOTablaBuque daoTablaBuque = new DAOTablaBuque();
		DAOTablaCarga daoTablaCarga = new DAOTablaCarga();
		DAOTablaCargaReservaBuque daoTablaCargaReservaBuque = new DAOTablaCargaReservaBuque();
		DAOTablaArea daoTablaArea = new DAOTablaArea();
		try {
			////// Transacción
			this.conn = darConexion();
			conn.setAutoCommit(false);
			Savepoint savepointInicio = conn.setSavepoint("savepointInicio");

			// 1. dado el id del buque y la fecha identifico cuales cargas hay que
			// bajar del barco
			daoTablaCargaReservaBuque.setConn(conn);
			int idBuque = resDescarga.getIdBuque();
			System.out.println("id del buque para descargar" + idBuque);
			Date fechaDescarga = resDescarga.getFechaDescarga();
			ArrayList<Integer> listaIdsCargas = daoTablaCargaReservaBuque
					.getIdsCargasReservadasFechaBuque(idBuque, fechaDescarga);

			// se supone que estas cargas todas tienen el mismo tipo. Donde reviso
			// esto? Aqui o en el momento de agregarlas
			daoTablaCarga.setConn(conn);
			int numeroCargas = listaIdsCargas.size();
			System.out.println("numero de cargas para descargar: " + numeroCargas);
			int idParaObtenerTipo = listaIdsCargas.get(0);
			System.out.println("id para obtener tipo " + idParaObtenerTipo);
			String tipoCarga = daoTablaCarga.getTipoCarga(idParaObtenerTipo); // TODo
																																				// DONE
			System.out
					.println("tipo de la carga(s) que quiero descargar " + tipoCarga);

			daoTablaArea.setConn(conn);
			// 2. Encontramos un area liberada que cumpla las condiciones de tipo y
			// capacidad
			Integer idAreaDisponible = -1;

			// esto esta lanzando exception, luego idAreaDisponible es -1
			try {
				idAreaDisponible = daoTablaArea
						.darIdAreaDisponiblePorCapacidadyTipo(numeroCargas, tipoCarga);
				System.out.println(
						"el idAreaDisponible dentro del try catch es " + idAreaDisponible);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}

			// 3 Reservar el area y cambiar el estado del buque
			daoTablaBuque.setConn(conn);
			daoTablaArea.reservarArea(idAreaDisponible, fechaDescarga);

			String estado = Buque.PROCESO_DE_CARGA;
			daoTablaBuque.cambiarEstadoBuque(idBuque, estado);

			// 4. Se hace el descargue
			daoIngresoSalidaArea.setConn(conn);
			int idCargaActual = -1;
			int idImportadorActual = -1;

			Savepoint savepointAntesCargas = conn
					.setSavepoint("savepointAntesCargas");
			Savepoint savepointCargas;

			for (int i = 0; i < numeroCargas; i++) {

				savepointCargas = conn.setSavepoint("savepointCargas");
				idCargaActual = listaIdsCargas.get(i);
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				String fechaDescargaString = df.format(fechaDescarga);
				System.out.println(
						"fecha en string dentro del for de 4. es: " + fechaDescargaString);
				idImportadorActual = daoTablaCarga.getImportadorDeCarga(idCargaActual);

				RegistroLlegadaCargaArea registroLlegada = new RegistroLlegadaCargaArea(
						idCargaActual, idAreaDisponible, "BARCO", fechaDescargaString, null,
						idImportadorActual);

				daoIngresoSalidaArea.registrarLlegadaCargaArea(registroLlegada);

				// daoIngresoSalidaArea.ejecutarSavePointIngresoCargaArea();
			}
			// cambiamos de nuevo el estado, es por si se traba en el proceso de
			// carga, entonces va a quedar en ese estado
			estado = Buque.NORMAL;
			daoTablaBuque.cambiarEstadoBuque(idBuque, estado);

			// finalmente, aumento la capacidad del buque y reduzco la capacidad del
			// area
			daoTablaBuque.aumentarCapacidad(numeroCargas, idBuque);
			daoTablaArea.reducirCapacidad(numeroCargas, idAreaDisponible);
			conn.commit();

		} catch (SQLException e) {
			// this.conn.rollback(savepointInicio);
			this.conn.rollback();
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoIngresoSalidaArea.cerrarRecursos();
				daoReservarArea.cerrarRecursos();
				daoTablaBuque.cerrarRecursos();
				daoTablaCarga.cerrarRecursos();
				daoTablaCargaReservaBuque.cerrarRecursos();
				daoTablaArea.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println(
						"SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	// RF6
	public void addRegistroCargaBuque(RegistroCargaBuque registroCargaBuque)
			throws Exception {
		DAOTablaIngresoSalidaArea daoIngresoSalidaArea = new DAOTablaIngresoSalidaArea();
		DAOTablaCargaReservaBuque daoCargaReservaBuque = new DAOTablaCargaReservaBuque();
		try {
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoIngresoSalidaArea.setConn(conn);
			daoCargaReservaBuque.setConn(conn);
			daoIngresoSalidaArea.registrarSalidaCargaArea(registroCargaBuque);
			System.out.println("REGISTRA SALIDA CARGA DE AREA");
			daoCargaReservaBuque.registrarCargaBuque(registroCargaBuque);
			System.out.println("REGISTRA  CARGA EN BUQUE");
		} catch (Exception e) {
			conn.rollback();
			throw e;
		} finally {
			try {
				daoIngresoSalidaArea.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println(
						"SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
			try {
				daoCargaReservaBuque.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println(
						"SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	// RF10
	public ArrayList<ItemAreaCargaDestino> darItemsAreaCargaDestino(
			String destino) throws Exception {
		DAOTablaDestinosBuques daoDestinosBuques = new DAOTablaDestinosBuques();
		ArrayList<ItemAreaCargaDestino> items = new ArrayList<ItemAreaCargaDestino>();
		try {
			this.conn = darConexion();
			daoDestinosBuques.setConn(conn);
			items = daoDestinosBuques.darItemsAreaCargaDestino(destino);
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				daoDestinosBuques.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println(
						"SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return items;
	}

	public void cargarBuque(int idBuque) throws Exception {
		DAOTablaBuque daoTablaBuque = new DAOTablaBuque();
		DAOTablaCarga daoTablaCarga = new DAOTablaCarga();
		DAOTablaDestinosBuques daoTablaDestinosBuques = new DAOTablaDestinosBuques();
		DAOTablaArea daoTablaArea = new DAOTablaArea();
		ArrayList<ItemAreaCargaDestino> items = new ArrayList<ItemAreaCargaDestino>();
		try {
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoTablaBuque.setConn(conn);
			daoTablaCarga.setConn(conn);
			daoTablaDestinosBuques.setConn(conn);
			daoTablaArea.setConn(conn);
			ArrayList<String> destinos = daoTablaDestinosBuques
					.darDestinosBuque(idBuque);
			Buque buque = daoTablaBuque.selectBuque(idBuque);
			int cargasCargadas = 0;
			for (int i = 0; i < destinos.size(); i++) {
				String destino = destinos.get(i);
				items.addAll(darItemsAreaCargaDestino(destino));
				for (int j = 0; j < items.size(); j++) {
					int idCarga = items.get(j).getIdCarga();
					Date fechaSalida = new java.util.Date();
					RegistroCargaBuque registro = new RegistroCargaBuque(idCarga, idBuque,
							fechaSalida, Carga.CAMION);
					String tipoCarga = daoTablaCarga
							.getTipoCarga(items.get(j).getIdCarga());
					boolean sePuede = false;
					String tipoBuque = buque.getTipoBuque();
					if (tipoBuque.equals(Buque.MULTIPROPOSITO)) {
						sePuede = true;
					} else if (tipoBuque.equals(Buque.PORTACONTENEDOR)
							&& tipoCarga.equals(Carga.CONTENEDOR)) {
						sePuede = true;
					} else if (tipoBuque.equals(Buque.RORO)
							&& tipoCarga.equals(Carga.VEHICULAR)) {
						sePuede = true;
					}
					if (sePuede) {
						Savepoint savePoint = conn.setSavepoint("SAVE_POINT");
						try {
							addRegistroCargaBuque(registro);
							cargasCargadas++;
						} catch (Exception e) {
							conn.rollback(savePoint);
						}
					}
				}
			}
			daoTablaBuque.actualizarCapacidad(cargasCargadas, idBuque);
			// ACTIUALIZAR CAPACIDAD AREA
			// daoTablaArea.reducirCapacidad(cargasCargadas, idArea);
		} catch (Exception e) {
			conn.rollback();
			throw e;
		} finally {
			try {
				daoTablaBuque.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println(
						"SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
			try {
				daoTablaCarga.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println(
						"SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
			try {
				daoTablaDestinosBuques.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println(
						"SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
			try {
				daoTablaArea.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println(
						"SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

	}

	// RF13
	public ArrayList<Integer> cerrarAreaAlmacenamiento(int idArea)
			throws Exception {
		ArrayList<Integer> cargasSinMover = new ArrayList<Integer>();
		DAOTablaArea daoTablaArea = new DAOTablaArea();
		DAOTablaIngresoSalidaArea daoTablaIngresoSalidaArea = new DAOTablaIngresoSalidaArea();
		try {
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoTablaArea.setConn(conn);
			daoTablaIngresoSalidaArea.setConn(conn);
			daoTablaArea.cambiarEstado(Area.DESHABILITADA, idArea);
			int capacidadMaximaArea = daoTablaArea.getCapacidadMaxima(idArea);
			int capacidadDisponibleArea = daoTablaArea.getCapacidadDisponible(idArea);
			int numeroCargas = capacidadMaximaArea - capacidadDisponibleArea;
			String tipoArea = daoTablaArea.getTipo(idArea);
			int idAreaDisponible = daoTablaArea
					.darIdAreaDisponiblePorCapacidadyTipo(numeroCargas, tipoArea);
			ArrayList<Integer> cargasParaMover = daoTablaIngresoSalidaArea
					.darCargasPorArea(idArea);
			for (int i = 0; i < cargasParaMover.size(); i++) {
				int capacidadDisponible = daoTablaArea
						.getCapacidadDisponible(idAreaDisponible);
				if (capacidadDisponible >= cargasParaMover.size()) {
					int idCarga = cargasParaMover.get(i);
					Date fechaCambio = new Date();
					cambiarCargaDeArea(idArea, idAreaDisponible, idCarga, fechaCambio);
					cargasParaMover.remove(idCarga);
				} else {
					idAreaDisponible = daoTablaArea.darIdAreaDisponiblePorCapacidadyTipo(
							cargasParaMover.size(), tipoArea);
					if (idAreaDisponible == 0) {
						for (int j = 0; j < cargasParaMover.size(); j++) {
							cargasSinMover.add(cargasParaMover.get(j));
						}
					} else {
						capacidadDisponible = daoTablaArea
								.getCapacidadDisponible(idAreaDisponible);
						int idCarga = cargasParaMover.get(i);
						Date fechaCambio = new Date();
						Savepoint savePoint = conn.setSavepoint("SAVE_POINT");
						try {
							cambiarCargaDeArea(idArea, idAreaDisponible, idCarga,
									fechaCambio);
							cargasParaMover.remove(idCarga);

						} catch (Exception e) {
							conn.rollback(savePoint);
						}
					}
				}
			}
		} catch (Exception e) {
			conn.rollback();
			throw e;
		} finally {
			try {
				daoTablaIngresoSalidaArea.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println(
						"SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
			try {
				daoTablaArea.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println(
						"SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return cargasSinMover;
	}

	public void cambiarCargaDeArea(int idAreaAnterior, int idAreaNueva,
			int idCarga, Date fechaCambio) throws Exception {
		DAOTablaIngresoSalidaArea daoTablaIngresoSalidaArea = new DAOTablaIngresoSalidaArea();
		DAOTablaArea daoTablaArea = new DAOTablaArea();
		try {
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoTablaIngresoSalidaArea.setConn(conn);
			daoTablaArea.setConn(conn);
			// Crear cambiarCargaDeArea
			// daoTablaIngresoSalidaArea.cambiarCargaDeArea(idAreaAnterior,
			// idAreaNueva, idCarga, fechaCambio);
			daoTablaArea.reducirCapacidad(1, idAreaAnterior);
			daoTablaArea.aumentarCapacidad(1, idAreaNueva);
		} catch (Exception e) {
			conn.rollback();
			throw e;
		} finally {
			try {
				daoTablaIngresoSalidaArea.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println(
						"SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
			try {
				daoTablaArea.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println(
						"SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

	}
	// <<<<<<< Updated upstream

	public void deshabilitarBuque(
			RegistroDeshabilitacionBuque registroDeshabilitacionBuque)
			throws Exception {

		int idBuque = registroDeshabilitacionBuque.getIdBuque();
		Date fechaDeshabilitacion = registroDeshabilitacionBuque
				.getFechaDeshabilitacion();
		String fechaDeshabilitacionString = registroDeshabilitacionBuque
				.getFechaDeshabilitacionString();
		String motivo = registroDeshabilitacionBuque.getMotivo();

		// daos
		DAOTablaBuque daoBuque = new DAOTablaBuque();
		DAOTablaCarga daoCarga = new DAOTablaCarga();
		DAOTablaIngresoSalidaBuque daoIOBuque = new DAOTablaIngresoSalidaBuque();
		try {
			this.conn = darConexion();
			daoBuque.setConn(conn);
			daoCarga.setConn(conn);
			daoIOBuque.setConn(conn);
			conn.setAutoCommit(false);

			// 1. Verificaciones que mantienen la consistencia de la base de datos

			// 1.1 Verificacion que el buque esta en el puerto
			if (daoIOBuque.estaBuqueEnPuerto(idBuque) == false)
				throw new Exception(
						"El buque no esta en el puerto, pues la fecha de salida de la BD no es null");

			System.out.println("Pasa verificacion esta");

			// 1.2 Verificacion que la fecha de deshabilitacion
			Date fechaIngreso = daoIOBuque.darultimaFechaIngreso(idBuque);

			System.out.println("Pasa verificacion fecha");

			int comparacion = fechaIngreso.compareTo(fechaDeshabilitacion);
			if (comparacion > 0)
				throw new Exception(
						"La fecha de deshabilitacion es antes de la fecha de ingreso. Imposible hacer transaccion");

			// 2. revisar el tipo de deshablitacion dependiendo el caso y tomar accion

			if (motivo.equals(Buque.AVERIA) || motivo.equals(Buque.MANTENIMIENTO)) {
				// es necesario descargar el buque, pero no a otros buques, eso no tiene
				// logica. Que pasa si al dueno del buque
				// no se le da gana recibir las cargas? Toca en las areas de las que SI
				// somos duenos.

				// hacemos uso de RF11
				RegistroDescargaBuque registroDescarga = new RegistroDescargaBuque(
						idBuque, fechaDeshabilitacionString);
				addRegistroDescargaBuque(registroDescarga);
				System.out.println("Pasa descarga");

			} else if (motivo.equals(Buque.PROBLEMA_LEGAL)) {
				// no hay movimiento de carga
			} else {
				throw new Exception("El motivo que venia en el JSON no es valido");
			}

			// 3. Cambio el estado del buque a deshabilitado. Ojo, el motivo puede
			// estar mal desde el JSON.
			// en ese caso, este metodo lanza exception
			daoBuque.cambiarEstadoBuque(idBuque, motivo);

			System.out.println("Pasa cambiar estado buque");

			conn.commit();

		} catch (SQLException e) {
			// conn.rollback();
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoBuque.cerrarRecursos();
				daoCarga.cerrarRecursos();
				daoIOBuque.cerrarRecursos();

				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println(
						"SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

	}

	public ArrayList<RespuestaConsultaArea> consultarAreasAlmacenamiento(
			ConsultaAreasAlmacenamientoGerente consulta) throws Exception {
		DAOTablaArea daoArea = new DAOTablaArea();
		ArrayList<RespuestaConsultaArea> respuestas = new ArrayList();
		try {
			this.conn = darConexion();
			daoArea.setConn(conn);
			respuestas = daoArea.consultarAreasAlmacenamiento(consulta);
			conn.commit();
		} catch (SQLException e) {
			conn.rollback();
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoArea.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println(
						"SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return respuestas;

	}

	public ArrayList<RespuestaConsultaMovimientos> consultarMovimientosCarga(
			ConsultaMovimientosCarga consulta) throws SQLException {
		DAOTablaCarga daoCarga = new DAOTablaCarga();
		ArrayList<RespuestaConsultaMovimientos> respuestas = new ArrayList<RespuestaConsultaMovimientos>();
		try {
			this.conn = darConexion();
			daoCarga.setConn(conn);
			respuestas = daoCarga.consultarMovimientosCarga(consulta);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCarga.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println(
						"SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return respuestas;
	}

	// RFC7
	public ArrayList<RegistroArriboOSalida> consultarRegistrosIOSalida(
			String fechaInicio, String fechaFin, String nombreBuque,
			String tipoDeBuque) throws Exception {
		DAOTablaIngresoSalidaBuque daoIOBuque = new DAOTablaIngresoSalidaBuque();
		ArrayList<RegistroArriboOSalida> listaRegistros = new ArrayList<>();

		try {
			this.conn = darConexion();
			daoIOBuque.setConn(conn);
			// transaccion
			listaRegistros = daoIOBuque.consultaIngresoSalidaBuques(fechaInicio,
					fechaFin, nombreBuque, tipoDeBuque);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoIOBuque.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println(
						"SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

		return listaRegistros;
	}

	// RFC8
	public ArrayList<RegistroArriboOSalida> consultarRegistrosIOSalida_v2(
			String fechaInicio, String fechaFin, String nombreBuque, String tipoBuque)
			throws Exception {
		DAOTablaIngresoSalidaBuque daoIOBuque = new DAOTablaIngresoSalidaBuque();
		ArrayList<RegistroArriboOSalida> listaRegistros = new ArrayList<>();
		try {
			this.conn = darConexion();
			daoIOBuque.setConn(conn);
			listaRegistros = daoIOBuque.consultaIngresoSalidaBuques_v2(fechaInicio,
					fechaFin, nombreBuque, tipoBuque);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoIOBuque.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println(
						"SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return listaRegistros;
	}

	// RFC9
	public ArrayList<RegistroMovimientoCarga> consultarMovimientosCarga(
			int volumen, String tipoCarga) throws Exception {
		DAOTablaIngresoSalidaArea daoIOArea = new DAOTablaIngresoSalidaArea();
		ArrayList<RegistroMovimientoCarga> listaRegistros = new ArrayList<>();

		try {
			this.conn = darConexion();
			daoIOArea.setConn(conn);
			// transaccion
			listaRegistros = daoIOArea.consultaMovimientoCargas(volumen, tipoCarga);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoIOArea.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println(
						"SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

		return listaRegistros;

	}

	// RFC10
	public ArrayList<RegistroArea> consultarAreasDeAlmacenamiento(int idArea1,
			int idArea2) throws SQLException {
		DAOTablaIngresoSalidaArea daoISA = new DAOTablaIngresoSalidaArea();
		ArrayList<RegistroArea> registros = new ArrayList<>();
		try {
			this.conn = darConexion();
			daoISA.setConn(conn);
			registros = daoISA.consultarAreasDeAlmacenamiento(idArea1, idArea2);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoISA.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println(
						"SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

		return registros;
	}

	/**
	 * 
	 * @param capacidad
	 * @param tipoArea
	 * @return String con el reporte de la existencia del area
	 * @throws Exception
	 */
	public String existeAreaDisponibleConCapacidad(int capacidad, String tipoArea)
			throws Exception {
		DAOTablaArea daoArea = new DAOTablaArea();
		String respuesta = NO_DISPONIBLE;
		try {
			this.conn = darConexion();
			daoArea.setConn(conn);
			boolean existeArea = daoArea.ExisteAreaDisponibleConCapacidad(capacidad,
					tipoArea);
			if (existeArea) {
				respuesta = DISPONIBLE;
			}
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoArea.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println(
						"SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

		return respuesta;

	}

	/**
	 * 
	 * @param capacidad
	 * @param tipoArea
	 * @return el id del area disponible con tipo indicado y capacidad suficiente
	 * @throws Exception
	 */
	public int darIdAreaDisponibleConCapacidad(int capacidad, String tipoArea)
			throws Exception {
		DAOTablaArea daoArea = new DAOTablaArea();
		int idArea = -1;
		try {
			this.conn = darConexion();
			daoArea.setConn(conn);
			idArea = daoArea.darIdAreaDisponibleConCapacidad(capacidad, tipoArea);

			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoArea.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println(
						"SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

		return idArea;

	}

	/**
	 * 
	 * @param idExportador
	 * @param fechaInicio
	 * @param fechaFin
	 * @return el total facturado en las fechas que entran por parametro. -1 si
	 *         hay algun problema
	 * @throws SQLException
	 */
	public int calcularTotalFacturado(int idExportador, Date fechaInicio,
			Date fechaFin) throws SQLException {
		int totalFacturado = -1;
		DAOTablaFactura daoFactura = new DAOTablaFactura();
		try {
			this.conn = darConexion();
			daoFactura.setConn(conn);
			totalFacturado = daoFactura.darTotalFacturado(idExportador, fechaInicio,
					fechaFin);

			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoFactura.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println(
						"SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return totalFacturado;

	}

}