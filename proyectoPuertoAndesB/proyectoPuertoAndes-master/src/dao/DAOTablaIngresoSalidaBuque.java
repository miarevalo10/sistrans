package dao;

/*Importaciones de java*/
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import dateUtilities.DateParser;

/*Importaciones de vos*/

import vos.BuqueGeneral;
import vos.Buque;
import vos.RegistroArriboOSalida;
import vos.Video;


/**
 * DAO que conecta con la tabla de ingreso y salida de buques
 */
public class DAOTablaIngresoSalidaBuque {

    /**
     * Arraylits de recursos que se usan para la ejecución de sentencias SQL
     */
    private ArrayList<Object> recursos;

    /**
     * Atributo que genera la conexión a la base de datos
     */
    private Connection conn;

    /**
     * Método constructor que crea DAOExportador
     * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
     */
    public DAOTablaIngresoSalidaBuque() {
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
     * Método que agrega el registro del Arribo de un buque  que entra como parámetro a la base de datos.
     * @param registroArribo - el registro del buque  a agregar. importador !=  null
     * <b> post: </b> se ha agregado el registroArribo a la base de datos en la transaction actual. pendiente que el registroArribo master
     * haga commit para que el registroArribo baje  a la base de datos.
     * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el registroArribo a la base de datos
     * @throws Exception - Cualquier error que no corresponda a la base de datos
     */
    public void addRegistroArriboBuque (RegistroArriboOSalida registroArribo) throws SQLException, Exception{

    	 	// falta revisar si ya habia entrado
        Date fechaArribo = registroArribo.getFechaArribo();
        String secuenciaID  = "seq_buque_ingreso.nextval";
        java.sql.Date sqlFechaArribo = new java.sql.Date(fechaArribo.getTime());

       
        String sql = "INSERT INTO INGRESO_SALIDA_BUQUES (ID_REGISTRO, ID_BUQUE,FECHA_INGRESO) VALUES (";
        sql += secuenciaID + ",";
        sql += registroArribo.getIdBuque() + ",";
        sql += "TO_DATE('" +sqlFechaArribo + "', 'YYYY-MM-DD') " + ")";
        
        System.out.println(sql);
        PreparedStatement prepStmt = conn.prepareStatement(sql);
        recursos.add(prepStmt);
        prepStmt.executeQuery();
        
        // si no sirve, mirar http://stackoverflow.com/questions/1081234/java-date-insert-into-database, respuesta de oscarRyz

    }

    /**
     * Método que agrega el registro de la salida de un buque  que entra como parámetro a la base de datos.
     * @param registroSalida - el registro del buque  a agregar. importador !=  null
     * <b> post: </b> se ha agregado el registroArribo a la base de datos en la transaction actual. pendiente que el registroArribo master
     * haga commit para que el registroArribo baje  a la base de datos.
     * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el registroArribo a la base de datos
     * @throws Exception - Cualquier error que no corresponda a la base de datos
     */

    public void addRegistroSalidaBuque (RegistroArriboOSalida registroSalida) throws SQLException, Exception {

      
    	// falta revisar si ya habia entrado
      Date fechaSalida = registroSalida.getFechaSalida();
      String secuenciaID  = "seq_buque_ingreso.nextval";
      java.sql.Date sqlFechaSalida = new java.sql.Date(fechaSalida.getTime());
      
      System.out.println(sqlFechaSalida);

      
    	
    	
    	
    	
    		// necesitamos encontrar el id del registro del ultimo ingreso de este buque, para eso, SQL

        String sqlUltimoRegistroId = "SELECT MAX(ID_REGISTRO) FROM INGRESO_SALIDA_BUQUES ";
        sqlUltimoRegistroId += "WHERE ID_BUQUE = " + registroSalida.getIdBuque();

        System.out.println(sqlUltimoRegistroId);

        PreparedStatement prepStmt = conn.prepareStatement(sqlUltimoRegistroId);
        recursos.add(prepStmt);
        ResultSet rs = prepStmt.executeQuery();
        
        

        int idUltimoRegistro = -1;
        rs.next();
        idUltimoRegistro = rs.getInt("MAX(ID_REGISTRO)");
      	System.out.println("previis id es " + idUltimoRegistro);


        
        // miramos si ya arribo en el puerto
        String revisionArribo = "SELECT * FROM INGRESO_SALIDA_BUQUES ";
        revisionArribo +="WHERE ID_REGISTRO = " + idUltimoRegistro;
        
        System.out.println(revisionArribo);

        prepStmt = conn.prepareStatement(revisionArribo);
        recursos.add(prepStmt);
        rs = prepStmt.executeQuery();
        boolean siArribo = false;
        
        System.out.println("");
        while(rs.next()){
        	System.out.println("date ingreso es" + rs.getDate("FECHA_INGRESO") + "date salida es" + rs.getDate("FECHA_SALIDA") );
        	siArribo = rs.getDate("FECHA_INGRESO") != null && rs.getDate("FECHA_SALIDA") == null;
        }
        
        System.out.println("Pasa fechas");
        System.out.println("Si arribo es" + siArribo);
        
        if(siArribo){
       // el id se lo da la secuencia, para borrar solo se necesita el nombre y la fecha de ingreso
          String sqlUpdate = "UPDATE INGRESO_SALIDA_BUQUES ";
          sqlUpdate += "SET FECHA_SALIDA =" + "TO_DATE('" +sqlFechaSalida + "', 'YYYY-MM-DD') ";
          sqlUpdate += " WHERE ID_REGISTRO =" + idUltimoRegistro;

          System.out.println(sqlUpdate);

          prepStmt = conn.prepareStatement(sqlUpdate);
          recursos.add(prepStmt);
          prepStmt.executeQuery();
          System.out.println("guardo");
        }
        else
        {
        	System.out.println("exception!!");
        	throw new Exception ("El buque ya salio, revisar parametros de fecha");
        }
        
        
        
        
    }
    
    
    // este metodo esta por alguna razón repetido... puede causar errores.
  /*  
    public void registrarIngresoBuque(Carga c, Buque b, java.sql.Date f) throws SQLException {
		String SQL="UPDATE INGRESO_SALIDA_BUQUE SET FECHA_SALIDA WHERE ID_BUQUE="+b.getId()+";";
		PreparedStatement pS=conn.prepareStatement(SQL);
		recursos.add(pS);
		pS.executeQuery();
	}*/
    
	public ArrayList<Buque> consultaActividad(int porCapacidad, int capacidadMenor, int capacidadMayor, int porTipoBuque,
                                              int porPortacontenedor, int porMultiproposito, int porRoro, int porRangoFecha, String fechaMenor,
                                              String fechaMayor, int porAgrupamiento, int porOrdenamiento) throws SQLException {
		ArrayList<Buque> buques = new ArrayList<Buque>();
		String SQL = "SELECT * ";
		if(porOrdenamiento==1){
			SQL+=",COUNT(INGRESO_SALIDA_BUQUE.ID_BUQUE) AS NUMERO_BUQUES";
		}
		SQL+="FROM INGRESO_SALIDA_BUQUE JOIN BUQUE ON INGRESO_SALIDA_BUQUE.ID_BUQUE=BUQUE.ID WHERE ";
		if(porCapacidad==1){
			SQL+="BUQUE.CAPACIDAD BETWEEN "+capacidadMenor+" AND "+capacidadMayor;
		}
		if(porPortacontenedor==1){
			if(porCapacidad==1){
				SQL+=" AND ";
			}
			SQL+="BUQUE.TIPO_BUQUE=\""+Buque.PORTACONTENEDOR;
		}
		else if(porMultiproposito==1){
			if(porCapacidad==1){
				SQL+=" AND ";
			}
			SQL+="BUQUE.TIPO_BUQUE=\""+Buque.MULTIPROPOSITO;
		}
		else if(porRoro==1){
			if(porCapacidad==1){
				SQL+=" AND ";
			}
			SQL+="BUQUE.TIPO_BUQUE=\""+Buque.RORO;
		}
		if(porRangoFecha==1){
			if(porCapacidad==1||porTipoBuque==1){
				SQL+=" AND ";
			}
			SQL+="INGRESO_SALIDA_BUQUES.FECHA_INGRESO BETWEEN #"+fechaMenor+"# AND #"+fechaMayor+"#";
		}
		if(porAgrupamiento==1){
			SQL+="GROUP BY INGRESO_SALIDA_BUQUE.ID_BUQUE ";
		}
		if(porOrdenamiento==1){
			SQL+="ORDER BY INGRESO_SALIDA_BUQUE.ID_BUQUE DESC";
		}
		System.out.println("SQL stmt:" + SQL);
		PreparedStatement pS = conn.prepareStatement(SQL);
		recursos.add(pS);
		ResultSet rs = pS.executeQuery();
		while (rs.next()) {
			int id = Integer.parseInt(rs.getString(1));
			String nombre = rs.getString(2);
			int capacidad = Integer.parseInt(rs.getString(3));
			String registroCapitania = rs.getString(4);
			String agenteMaritimo = rs.getString(5);
			String tipoBuque = rs.getString(6);
			buques.add(new Buque(id, nombre, capacidad, registroCapitania, agenteMaritimo, tipoBuque));
		}
		return buques;
	}

	public boolean estaBuqueEnPuerto (int idBuque) throws Exception { 	
		boolean esta = false;
		// necesitamos encontrar el id del registro del ultimo ingreso de este buque, para eso, SQL
    String sqlUltimoRegistroId = "SELECT MAX(ID_REGISTRO) FROM INGRESO_SALIDA_BUQUES ";
    sqlUltimoRegistroId += "WHERE ID_BUQUE = " + idBuque;

    System.out.println(sqlUltimoRegistroId);
    PreparedStatement prepStmt = conn.prepareStatement(sqlUltimoRegistroId);
    recursos.add(prepStmt);
    ResultSet rs = prepStmt.executeQuery();
    int idUltimoRegistro = -1;
    rs.next();
    idUltimoRegistro = rs.getInt("MAX(ID_REGISTRO)");
  	System.out.println("maximo id es " + idUltimoRegistro);
  	
  	// Revisamos si ya ingreso, obtenemos la fecha de salida para saber si aun esta en el puerto
		String sql = "SELECT FECHA_SALIDA FROM INGRESO_SALIDA_BUQUES WHERE ID_REGISTRO = " + idUltimoRegistro;
		System.out.println(sql);
		prepStmt = conn.prepareStatement(sql);
    recursos.add(prepStmt);
    rs = prepStmt.executeQuery();
    rs.next();
    java.sql.Date fechaSalida = rs.getDate("FECHA_SALIDA");
    esta = fechaSalida == null;
      
		return esta;
		
	}
	
	
	
	public Date darultimaFechaIngreso(int idBuque) throws Exception{
		Date ultimaFechaIngreso = null;
		
		
	// necesitamos encontrar el id del registro del ultimo ingreso de este buque, para eso, SQL

    String sqlUltimoRegistroId = "SELECT MAX(ID_REGISTRO) FROM INGRESO_SALIDA_BUQUES ";
    sqlUltimoRegistroId += "WHERE ID_BUQUE = " + idBuque;

    System.out.println(sqlUltimoRegistroId);
    PreparedStatement prepStmt = conn.prepareStatement(sqlUltimoRegistroId);
    recursos.add(prepStmt);
    ResultSet rs = prepStmt.executeQuery();
    int idUltimoRegistro = -1;
    rs.next();
    idUltimoRegistro = rs.getInt("MAX(ID_REGISTRO)");
  	System.out.println("maximo id es " + idUltimoRegistro);
  	
  	// Revisamos si ya ingreso, obtenemos la fecha para saber si ya ingreso
		String sql = "SELECT FECHA_INGRESO FROM INGRESO_SALIDA_BUQUES WHERE ID_REGISTRO = " + idUltimoRegistro;
		System.out.println(sql);
		prepStmt = conn.prepareStatement(sql);
    recursos.add(prepStmt);
    rs = prepStmt.executeQuery();
    rs.next();
    java.util.Date fechaIngresoUtil = null;
    Timestamp timestamp = rs.getTimestamp("FECHA_INGRESO");
    if (timestamp != null) {
    	fechaIngresoUtil = new java.util.Date(timestamp.getTime());
    }
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    String fechaIngresoString = df.format(fechaIngresoUtil);
    
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    ultimaFechaIngreso = formatter.parse(fechaIngresoString);
  	return ultimaFechaIngreso;
		
		
	}
	
	//RFC7
	public ArrayList<RegistroArriboOSalida> consultaIngresoSalidaBuques(
			String fechaInicio, String fechaFin, String nombreBuque,
			String tipoDeBuque) throws SQLException, Exception {
		
		boolean busquedaJoinNombre = false;
		boolean busquedaJoinTipo = false;
		ArrayList <RegistroArriboOSalida> listaRegistrosObetidos = new ArrayList<>();
		String sql = "SELECT ISB.ID_BUQUE AS ID_BUQUE, B.NOMBRE AS NOMBRE,  ISB.FECHA_INGRESO AS FECHA_INGRESO, ISB.FECHA_SALIDA AS FECHA_SALIDA";
		
		if(!nombreBuque.equals("")){
			sql += ", B.NOMBRE AS NOMBRE_BUQUE";
			busquedaJoinNombre = true;
		}
		else if(!tipoDeBuque.equals("") && Buque.tipoValidoDeBuque(tipoDeBuque)){
    	sql += ", B.TIPO_BUQUE AS TIPO_BUQUE";
    	busquedaJoinTipo  = true;
    }
		
    if(busquedaJoinNombre || busquedaJoinTipo){
    	sql += " FROM INGRESO_SALIDA_BUQUES ISB INNER JOIN BUQUE B ON ISB.ID_BUQUE = B.ID";
    }
    Date fechaInicioUtil = DateParser.fromStringToDate(fechaInicio);
    java.sql.Date fechaInicioSql = new java.sql.Date(fechaInicioUtil.getTime());
     
    sql += " WHERE ISB.FECHA_INGRESO >= " + "TO_DATE('" +fechaInicioSql + "', 'YYYY-MM-DD')";
    sql += " AND ";
    Date fechaFinUtil = DateParser.fromStringToDate(fechaFin);
    java.sql.Date fechaFinSql = new java.sql.Date(fechaFinUtil.getTime());
    sql += "ISB.FECHA_SALIDA <= " + "TO_DATE('" +fechaFinSql + "', 'YYYY-MM-DD')";
    
    if(busquedaJoinNombre){
    	sql += " AND B.NOMBRE = " + nombreBuque;
    }
    else if(busquedaJoinTipo){
    	sql += " AND B.TIPO_BUQUE = " + "'" + tipoDeBuque + "'";
    }
    System.out.println(sql);
    PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		while (rs.next()) {
			String nombre = rs.getString("NOMBRE");
			int idBuque = Integer.parseInt(rs.getString("ID_BUQUE"));
			
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
      listaRegistrosObetidos.add(new RegistroArriboOSalida(idBuque, fechaIngresoString, fechaSalidaString, nombre));
		}

		return listaRegistrosObetidos;
	}

	//RFC8
	public ArrayList<RegistroArriboOSalida> consultaIngresoSalidaBuques_v2(String fechaInicio, 
			String fechaFin, String nombreBuque, String tipoBuque) throws ParseException, SQLException {
		boolean busquedaNombre = false;
		boolean busquedaTipo = false;
		ArrayList <RegistroArriboOSalida> registros = new ArrayList<>();
		String sql = "SELECT ISB.ID_BUQUE AS ID_BUQUE, B.NOMBRE AS NOMBRE,  ISB.FECHA_INGRESO AS FECHA_INGRESO, ISB.FECHA_SALIDA AS FECHA_SALIDA";
		if(!nombreBuque.equals("")){
			sql += ", B.NOMBRE AS NOMBRE_BUQUE";
			busquedaNombre = true;
		}
		else if(!tipoBuque.equals("") && Buque.tipoValidoDeBuque(tipoBuque)){
	    	sql += ", B.TIPO_BUQUE AS TIPO_BUQUE";
	    	busquedaTipo  = true;
	    }
	    if(busquedaNombre || busquedaTipo){
	    	sql += " FROM INGRESO_SALIDA_BUQUES ISB INNER JOIN BUQUE B ON ISB.ID_BUQUE = B.ID";
	    }
	    Date fechaInicioUtil = DateParser.fromStringToDate(fechaInicio);
	    java.sql.Date fechaInicioSql = new java.sql.Date(fechaInicioUtil.getTime());
	    sql += " WHERE ISB.FECHA_INGRESO >= " + "TO_DATE('" +fechaInicioSql + "', 'YYYY-MM-DD')";
	    sql += " AND ";
	    Date fechaFinUtil = DateParser.fromStringToDate(fechaFin);
	    java.sql.Date fechaFinSql = new java.sql.Date(fechaFinUtil.getTime());
	    sql += "ISB.FECHA_SALIDA <= " + "TO_DATE('" +fechaFinSql + "', 'YYYY-MM-DD')";
	    if(busquedaNombre){
	    	sql += " AND B.NOMBRE <> " + nombreBuque;
	    }
	    else if(busquedaTipo){
	    	sql += " AND B.TIPO_BUQUE <> " + tipoBuque;
	    }
	    System.out.println(sql);
	    PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		while (rs.next()) {
			String nombre = rs.getString("NOMBRE");
			int idBuque = Integer.parseInt(rs.getString("ID_BUQUE"));	
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
	        registros.add(new RegistroArriboOSalida(idBuque, fechaIngresoString, fechaSalidaString, nombre));
		}
		return registros;
	}
}
