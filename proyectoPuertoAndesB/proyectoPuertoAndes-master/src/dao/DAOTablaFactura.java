package dao;

/*Importaciones de java*/
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;

import javax.naming.ldap.Rdn;

/*Importaciones de vos*/
import vos.Buque;
import vos.BuqueGeneral;
import vos.RegistroFacturaExportador;

public class DAOTablaFactura {

	
	// costos de los equipos
	public static final int COSTO_GRUA = 100;
	public static final int COSTO_PORTICO = 200;
	public static final int COSTO_ELEVADOR = 300;
	public static final int COSTO_MONTACARGAS = 400;
	
	public static final int COSTO_DIAS_AREA = 400;

	public static final int COSTO_COBERTIZO = 400;
	public static final int COSTO_SILO = 300;
	public static final int COSTO_PATIO = 400;
	public static final int COSTO_BODEGA = 400;
	
	
	
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
    public DAOTablaFactura() {
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
     * Método que agrega el exportador que entra como parámetro a la base de datos.
     * @param registroFactura - el importador a agregar. importador !=  null
     * <b> post: </b> se ha agregado el exportador a la base de datos en la transaction actual. pendiente que el importador master
     * haga commit para que el importador baje  a la base de datos.
     * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el importador a la base de datos
     * @throws Exception - Cualquier error que no corresponda a la base de datos
     */
    public void addRegistroFacturaExportador (RegistroFacturaExportador registroFacturaExportador) throws SQLException, Exception{

    	// esta transaccion requiere de obtener informacion de otras tablas para calcular el costo total y registrarlo
    	int costoTotal = 0;
    	
    	//primero obtenemos lo que vamos a cobrar por equipo de la carga
    	
    	int costoEquipos = 0;
    	int idCarga = registroFacturaExportador.getIdCargaFacturaFactura();
    	
    	String sqlCarga = "SELECT * FROM CARGA_USA_EQUIPO WHERE ID_CARGA = ";
    	sqlCarga += idCarga;
    	
    	System.out.println(sqlCarga);
    	
    	PreparedStatement prepStmt = conn.prepareStatement(sqlCarga);
      recursos.add(prepStmt);
      ResultSet rs = prepStmt.executeQuery();
      
      while (rs.next()){
      	//sacamos el idEquipo
      	
      	int idEquipo  = rs.getInt("ID_EQUIPO");
      	System.out.println("id equipo es " + idEquipo);
      	
      	// HACEMOS LA CONSULTA DEL TIPO
      	String consultaTipo = "SELECT TIPO FROM EQUIPO WHERE ID = " + idEquipo;
      	
      	System.out.println(consultaTipo);
      	prepStmt = conn.prepareStatement(consultaTipo);
        recursos.add(prepStmt);
        ResultSet rs2 = prepStmt.executeQuery();
        rs2.next();
      	
      	String tipoEquipo = rs2.getString("TIPO");
      	
      	
      	if (tipoEquipo.equals("GRUA")){
      		costoEquipos += COSTO_GRUA;
      	}
      	else if (tipoEquipo.equals("portico")){
      		costoEquipos += COSTO_PORTICO;
      	}
      	else if (tipoEquipo.equals("elevador")){
      		costoEquipos += COSTO_ELEVADOR;
      	}
      	else if (tipoEquipo.equals("montacargas")){
      		costoEquipos += COSTO_MONTACARGAS;
      	}
      	else {
      		throw new Exception ("Costo no válido");
      	}
      }
    	 
      System.out.println("Pasa costos por equipo");
      /* costos por area */
      int costosArea = 0;
      
      // necesitamos encontrar el id del registro del ultimo ingreso de una carga en un area
      String sqlUltimoRegistroId = "SELECT MAX(ID) FROM INGRESO_SALIDA_AREA ";
      sqlUltimoRegistroId += "WHERE ID_CARGA = " + idCarga;
      
      prepStmt = conn.prepareStatement(sqlUltimoRegistroId);
      recursos.add(prepStmt);
      rs = prepStmt.executeQuery();
      rs.next();
      int idUltimoRegistro = rs.getInt("MAX(ID)");
      
      // ahora obtenemos el registro, para calcular la difenrencia entre las fechas.
      
      String sqlRegistroParaCobrarArea = "SELECT * FROM INGRESO_SALIDA_AREA WHERE ID =";
      sqlRegistroParaCobrarArea += idUltimoRegistro;
      
      prepStmt = conn.prepareStatement(sqlRegistroParaCobrarArea);
      recursos.add(sqlRegistroParaCobrarArea);
      rs = prepStmt.executeQuery();
      
      System.out.println("Pasa agarrar el registro de ingreso salida area");
      
      // fechas de ingreso y salida
      Date fechaIngreso = null;
    	Date fechaSalida = null;
    	int idArea = -1;
    	rs.next();
    	fechaIngreso = rs.getDate("FECHA_INGRESO");
      fechaSalida = rs.getDate("FECHA_SALIDA");
      idArea = rs.getInt("ID_AREA");
      
            
      int diferenciaDiasFecha = 4;
      
      /*if (fechaIngreso != null && fechaSalida != null){
      	
      }*/
      costosArea = diferenciaDiasFecha * COSTO_DIAS_AREA;
      
      // costo por el USO area de almacenamiento
      
      int costoEspaciosArea = 0;
      String sqlTipoArea = "SELECT TIPO FROM AREA WHERE ID = ";
      sqlTipoArea += idArea;
      
      prepStmt = conn.prepareStatement(sqlTipoArea);
      recursos.add(sqlTipoArea);
      rs = prepStmt.executeQuery();
      String tipo = null;
      
      rs.next();
      	tipo = rs.getString("TIPO");
      
      
      if (tipo.equals("cobertizo")){
      	costoEspaciosArea += COSTO_COBERTIZO;
      }
      else if (tipo.equals("bodega")){
      	costoEspaciosArea += COSTO_BODEGA;
      }
      else if (tipo.equals("silo")){
      	costoEspaciosArea += COSTO_SILO;
      }
      else if (tipo.equals("patio")){
      	costoEspaciosArea += COSTO_PATIO;
      }
      
      System.out.println("pasa costos por uso de area");
      
      // FINALMENTE, el volumen de la carga
      int costosVolumen = 0;
      String sqlVolumen = "SELECT VOLUMEN FROM CARGA WHERE ID =";
      sqlVolumen += registroFacturaExportador.getIdCargaFacturaFactura();
      
      System.out.println(sqlVolumen);
      prepStmt = conn.prepareStatement(sqlVolumen);
  		recursos.add(prepStmt);
  		ResultSet rsVolume = prepStmt.executeQuery();
      rsVolume.next();
      int volumen  = rsVolume.getInt("VOLUMEN");
      
      costosVolumen = 50 * volumen;
      
      
      // facturamos el total y lo agregamos a la base de datos
      java.sql.Date sqlFechaFactura = new java.sql.Date(registroFacturaExportador.getFechaFactura().getTime());
      
      costoTotal = costosVolumen + costoEspaciosArea + costosArea + costoEquipos;
      
      String sqlInsert= "INSERT INTO FACTURA VALUES (";
      sqlInsert += "seq_factura.nextval" + ",";
      sqlInsert += registroFacturaExportador.getIdBuqueGeneralFactura()  + ",";
      sqlInsert += registroFacturaExportador.getIdExportador() + ",";
      sqlInsert += "TO_DATE('"+  sqlFechaFactura  + "', 'YYYY-MM-DD'),";
      sqlInsert += costoTotal + ")";

  		prepStmt = conn.prepareStatement(sqlInsert);
  		recursos.add(prepStmt);
  		prepStmt.executeQuery();
      		

    }

		public int darTotalFacturado(int idExportador, java.util.Date fechaInicio,
				java.util.Date fechaFin) throws SQLException {
			int totalFacturado = 0;
			
      java.sql.Date sqlFechaInicio = new java.sql.Date(fechaInicio.getTime());
      java.sql.Date sqlFechaFin = new java.sql.Date(fechaFin.getTime());

			
			
			String sql = "SELECT TOTAL FROM FACTURA WHERE";
			sql += " ID_EXPORTADOR = " + idExportador;
			sql += " AND FECHA >= " + "TO_DATE('"+  sqlFechaInicio  + "', 'YYYY-MM-DD')";
			sql += " AND FECHA <= " + "TO_DATE('"+  sqlFechaFin  + "', 'YYYY-MM-DD')";
			
			PreparedStatement prepStmt = conn.prepareStatement(sql);
  		recursos.add(prepStmt);
  		ResultSet rs = prepStmt.executeQuery();
  		
  		while(rs.next()){
  			int totalFacturaActual = rs.getInt("TOTAL");
  			totalFacturado += totalFacturaActual;
  		}
			
			
			
			return totalFacturado;
		}

	
}
