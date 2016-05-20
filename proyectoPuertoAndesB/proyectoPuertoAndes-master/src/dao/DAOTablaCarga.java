package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dateUtilities.DateParser;
import vos.Carga;
import vos.ConsultaMovimientosCarga;
import vos.RespuestaConsultaArea;
import vos.RespuestaConsultaMovimientos;

public class DAOTablaCarga {
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
	public DAOTablaCarga() {
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

	/*public Carga getCargaPorId(int idCarga) throws NumberFormatException, Exception 
	{
		DAOTablaIngresoSalidaArea daoIngresoSalidaArea = new DAOTablaIngresoSalidaArea();
		String SQL="SELECT * FROM CARGA WHERE ID="+idCarga+";";
		PreparedStatement pS = conn.prepareStatement(SQL);
		recursos.add(pS);
		ResultSet rS = pS.executeQuery(); // AQUI ESTA MAL, no se hizo el .next
		String rId = rS.getString(1);
		String rVolumen = rS.getString(2);
		Carga c = null;
		String[] registro = daoIngresoSalidaArea.darRegistroCarga(idCarga);
		if(rId!=null&&rVolumen!=null&&registro[5]!=null&&registro[7]!=null&&registro[4]!=null){
			String rFechaIngreso = registro[5];
			Date rFecha = (Date) DateParser.fromStringToDate(rFechaIngreso);
			c = new Carga(Integer.parseInt(rId), Integer.parseInt(rVolumen), rFecha, Integer.parseInt(registro[7]), registro[4]);
		}
		return c;
	}*/

	public String getTipoCarga(Integer idArea) throws Exception {
		//String sql = "SET TRANSACTION ISOLATION LEVEL READONLY;";
		String sql  = "SELECT TIPO FROM CARGA WHERE ID=" + idArea;
		System.out.println(sql);
		PreparedStatement pS = conn.prepareStatement(sql);
		recursos.add(pS);
		ResultSet rs = pS.executeQuery();
		rs.next();
		String tipo = rs.getString("TIPO");
		return tipo;
	}

	public int getImportadorDeCarga(int idActual) throws Exception{
		int idImportadorBuscado = -1;
		String sql = "SELECT ID_IMPORTADOR FROM CARGA WHERE ID=" + idActual;
		PreparedStatement pS = conn.prepareStatement(sql);
		recursos.add(pS);
		ResultSet rs = pS.executeQuery();
		rs.next();
		idImportadorBuscado = rs.getInt("ID_IMPORTADOR");
		return idImportadorBuscado;
	}

	// RFC5
	public ArrayList<RespuestaConsultaMovimientos> consultarMovimientosCarga(ConsultaMovimientosCarga consulta) throws SQLException {
		ArrayList<RespuestaConsultaMovimientos> respuestas = new ArrayList<RespuestaConsultaMovimientos>();
		//String sql ="SET TRANSACTION ISOLATION LEVEL READONLY;";
		String sql = "SELECT * FROM CARGA JOIN INGRESO_SALIDA_AREA ON CARGA.ID=INGRESO_SALIDA_AREA.ID_CARGA WHERE ";
		int filtros=0;
		if(consulta.getOrigen()!=null&&consulta.getOrigen()!=""){
			sql+="CARGA.ORIGEN="+consulta.getOrigen()+" ";
			filtros++;
		}
		if(consulta.getDestino()!=null&&consulta.getDestino()!=""){
			if(filtros>0){
				sql+="AND ";
			}
			sql+="CARGA.DESTINO="+consulta.getDestino()+" ";
		}
		if(consulta.getIdCarga()>0){
			if(filtros>0){
				sql+="AND ";
			}
			sql+="CARGA.ID="+consulta.getIdCarga()+" ";
		}
		if(consulta.getTipoCarga()!=null&&consulta.getTipoCarga()!=""){
			if(filtros>0){
				sql+="AND ";
			}
			sql+="CARGA.TIPO="+consulta.getTipoCarga()+" ";
		}
		if(consulta.getFechaOrden()!=null){
			if(filtros>0){
				sql+="AND ";
			}
			sql+="(INGRESO_SALIDA_AREA.FECHA_SALIDA="+consulta.getFechaMovimiento()+" OR ";
			sql+="INGRESO_SALIDA_AREA.FECHA_INGRESO="+consulta.getFechaMovimiento()+") ";
		}
		if(consulta.getIdExportador()>0){
			if(filtros>0){
				sql+="AND ";
			}
			sql+="CARGA.ID_EXPORTADOR="+consulta.getIdExportador();
		}
		else if(consulta.getIdImportador()>0){
			if(filtros>0){
				sql+="AND ";
			}
			sql+="CARGA.ID_IMPORTADOR="+consulta.getIdImportador();
		}
		sql+=";";
		PreparedStatement pS = conn.prepareStatement(sql);
		recursos.add(pS);
		ResultSet rs = pS.executeQuery();
		while (rs.next()){
			int idRegistro = rs.getInt("INGRESO_SALIDA_AREA.ID");
			int idCarga = rs.getInt("INGRESO_SALIDA_AREA.ID_CARGA");
			Date fechaMovimiento = null;
			Date fechaIngreso = rs.getDate("FECHA_INGRESO");
			Date fechaSalida = rs.getDate("FECHA_SALIDA");
			if(fechaSalida!=null){
				fechaMovimiento=fechaSalida;
			}
			else if(fechaIngreso!=null&&fechaSalida==null){
				fechaMovimiento=fechaIngreso;
			}
			RespuestaConsultaMovimientos rcm  = new RespuestaConsultaMovimientos(idRegistro, idCarga, fechaMovimiento);
			respuestas.add(rcm);
		}		
		return respuestas;
	}
	
	

}
