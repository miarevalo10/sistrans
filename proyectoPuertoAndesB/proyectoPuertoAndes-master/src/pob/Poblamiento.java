package pob;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import dateUtilities.DateUtil;
import jdk.jfr.events.FileWriteEvent;
import vos.Area;
import vos.Buque;
import vos.Carga;
import vos.Importador;
import vos.Persona;

public class Poblamiento {
	// Delimiter used in CSV file
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";
	// CSV file header
	private static final String FILE_HEADER_EMPLOYEE = "emp_id,date_of_birth, salary, emp_name";
	private static final String FILE_HEADER_BUQUE = "ID, NOMBRE, CAPACIDAD_DISPONIBLE, REGISTRO_CAPITANIA, AGENTE_MARITIMO, TIPO_BUQUE, ESTADO, CAPACIDAD_MAXIMA";
	private static final String FILE_HEADER_IOBUQUE = "ID_REGISTRO, ID_BUQUE, FECHA_INGRESO, FECHA_SALIDA";
	private static final String FILE_HEADER_PERSONA = "ID, NOMBRE, TIPO_PERSONA";
	private static final String FILE_HEADER_IMPORTADOR = "ID, REGISTRO_ADUANA, TIPO_IMPORTADOR";
	private static final String FILE_HEADER_EXPORTADOR = "ID, RUT";
	private static final String FILE_HEADER_CARGA = "ID, VOLUMEN, TIPO, ID_IMPORTADOR, DESTINO, ID_EXPORTADOR,ORIGEN, FECHA_ORDEN";
	private static final String FILE_HEADER_AREA = "ID, TIPO, ESTADO, CAPACIDAD_DISPONIBLE, CAPACIDAD_MAXIMA";
	private static final String FILE_HEADER_IOAREA = "ID, ID_CARGA, ID_AREA, VIENE_DE, FECHA_INGRESO, FECHA_SALIDA, ID_IMPORTADOR";

	public static void writeCsvFileEmployee(String fileName) {
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(fileName);
			fileWriter.append(FILE_HEADER_EMPLOYEE.toString());
			fileWriter.append(NEW_LINE_SEPARATOR);
			int numeroRegistros = 1000000;
			for (int i = 0; i < numeroRegistros; i++) {
				fileWriter.append(String.valueOf(1));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append("2016-04-05");
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(243231));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf("nombre" + i));
				fileWriter.append(NEW_LINE_SEPARATOR);

			}

		} catch (Exception e) {
			System.out.println("Error en la creacion el CSV");
			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}

		}

	}

	private static void writeCvsFileBuque(String fileNameBuque) {
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(fileNameBuque);
			fileWriter.append(FILE_HEADER_BUQUE.toString());
			fileWriter.append(NEW_LINE_SEPARATOR);
			int numeroRegistros = 2000;
			for (int i = 1000; i < numeroRegistros; i++) {
				// metemos el id (embarrada la SEQ)
				fileWriter.append(String.valueOf(i));
				fileWriter.append(COMMA_DELIMITER);
				// nombre
				fileWriter.append("BUQ" + i);
				fileWriter.append(COMMA_DELIMITER);
				// capacidad disponible
				int capacidadDisponible = getRandomCapacidad();
				fileWriter.append(String.valueOf(capacidadDisponible));
				fileWriter.append(COMMA_DELIMITER);
				// egistro capitania
				fileWriter.append(String.valueOf("RC" + i));
				fileWriter.append(COMMA_DELIMITER);
				// gente maritimo
				fileWriter.append(String.valueOf("AG" + i));
				fileWriter.append(COMMA_DELIMITER);
				// tipoBuque
				String tipo = getRandomTipoBuque();
				fileWriter.append(tipo);
				fileWriter.append(COMMA_DELIMITER);
				// estado
				String estado = Buque.NORMAL;
				fileWriter.append(estado);
				fileWriter.append(COMMA_DELIMITER);
				// capacidad_maxima
				int capacidadMaxima = 500 - capacidadDisponible;
				fileWriter.append(String.valueOf(capacidadMaxima));

				fileWriter.append(NEW_LINE_SEPARATOR);

			}

		} catch (Exception e) {
			System.out.println("Error en la creacion el CSV");
			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}

		}

	}

	private static void writeCvsFileIOBuque(String fileNameIOBuque) {
		ArrayList<Integer> idsBuques = new ArrayList<>();
		for (int i = 1000; i < 2000; i++) {
			idsBuques.add(i);
		}

		FileWriter fileWriter = null;
		try {

			fileWriter = new FileWriter(fileNameIOBuque);
			fileWriter.append(FILE_HEADER_IOBUQUE.toString());
			fileWriter.append(NEW_LINE_SEPARATOR);
			int numeroRegistros = 1000000;

			String fechaIngresoString = "2017-01-01";
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date fechaInicio = formatter.parse(fechaIngresoString);
			System.out.println("la fecha inicial string es " + fechaIngresoString
					+ " y en Date es " + fechaInicio);

			String fechaSalidaString = "2017-01-03";
			formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date fechaFin = formatter.parse(fechaSalidaString);
			System.out.println("la fecha final string es " + fechaSalidaString
					+ " y en Date es " + fechaFin);

			int contadorMil = 0;

			for (int i = 1000; i < numeroRegistros; i++) {

				// en caso de que ya hayan pasado 500 registros
				if (contadorMil % 500 == 0) {

					fechaInicio = DateUtil.addDays(fechaInicio, 2);
					fechaFin = DateUtil.addDays(fechaFin, 2);

					// dejamos limpia la estructura y volvemos a agregar todos los buques
					// de nuevo
					idsBuques = new ArrayList<>();
					for (int j = 1000; j < 2000; j++) {
						idsBuques.add(j);
					}
				}

				// metemos el id (embarrada la SEQ)
				fileWriter.append(String.valueOf(i));
				fileWriter.append(COMMA_DELIMITER);

				// ID_BUQUE
				int indexAUtilizar = randInt(0, idsBuques.size() - 1); // aqui escoge de
																																// los barcos
																																// disponibles
				int idBuque = idsBuques.get(indexAUtilizar);
				idsBuques.remove(indexAUtilizar);

				fileWriter.append("" + idBuque);
				fileWriter.append(COMMA_DELIMITER);

				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				// fecha Ingreso
				String fechaInicioStringAppend = df.format(fechaInicio);
				fileWriter.append(String.valueOf(fechaInicioStringAppend));
				fileWriter.append(COMMA_DELIMITER);
				// fecha Salida
				String fechaFinStringAppend = df.format(fechaFin);
				fileWriter.append(String.valueOf(fechaFinStringAppend));
				fileWriter.append(NEW_LINE_SEPARATOR);

				contadorMil += 1;

			}

		} catch (Exception e) {
			System.out.println("Error en la creacion el CSV");
			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}

		}

	}

	private static void writeCvsFilePersonaImportadorExportador(
			String fileNamePersona, String fileNameImportador,
			String fileNameExportador) {

		FileWriter fileWriterPersona = null;
		FileWriter fileWriterImportador = null;
		FileWriter fileWriterExportador = null;
		try {

			fileWriterPersona = new FileWriter(fileNamePersona);
			fileWriterImportador = new FileWriter(fileNameImportador);
			fileWriterExportador = new FileWriter(fileNameExportador);

			fileWriterPersona.append(FILE_HEADER_PERSONA.toString());
			fileWriterPersona.append(NEW_LINE_SEPARATOR);

			fileWriterExportador.append(FILE_HEADER_EXPORTADOR.toString());
			fileWriterExportador.append(NEW_LINE_SEPARATOR);

			fileWriterImportador.append(FILE_HEADER_IMPORTADOR.toString());
			fileWriterImportador.append(NEW_LINE_SEPARATOR);

			int numeroRegistros = 1500;

			for (int i = 1000; i < numeroRegistros; i++) {

				// metemos el id en persona (embarrada la SEQ)
				fileWriterPersona.append(String.valueOf(i));
				fileWriterPersona.append(COMMA_DELIMITER);

				// NOMBRE
				fileWriterPersona.append("PERSONA" + i);
				fileWriterPersona.append(COMMA_DELIMITER);

				// tipo de la persona
				String tipoPersona = getRandomTipoPersona();
				fileWriterPersona.append(tipoPersona);
				fileWriterPersona.append(NEW_LINE_SEPARATOR);

				if (i < 1250) {
					// metemos el id en importador (embarrada la SEQ)
					fileWriterImportador.append(String.valueOf(i));
					fileWriterImportador.append(COMMA_DELIMITER);
					// registro aduana
					fileWriterImportador.append(String.valueOf(10000 + i));
					fileWriterImportador.append(COMMA_DELIMITER);
					// tipo Importador
					String tipoImportador = getRandomTipoImportador();
					fileWriterImportador.append(tipoImportador);
					fileWriterImportador.append(NEW_LINE_SEPARATOR);

				} else {
					// metemos el id en exportador (embarrada la SEQ)
					fileWriterExportador.append(String.valueOf(i));
					fileWriterExportador.append(COMMA_DELIMITER);

					// rut
					fileWriterExportador.append(String.valueOf(i + 10000));
					fileWriterExportador.append(NEW_LINE_SEPARATOR);

				}

			}

		} catch (Exception e) {
			System.out.println("Error en la creacion el CSV");
			e.printStackTrace();
		} finally {
			try {
				fileWriterPersona.flush();
				fileWriterExportador.flush();
				fileWriterImportador.flush();

				fileWriterPersona.close();
				fileWriterExportador.close();
				fileWriterImportador.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}

		}

	}

	private static void writeCvsFileArea(String fileNameArea) {
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(fileNameArea);
			fileWriter.append(FILE_HEADER_AREA.toString());
			fileWriter.append(NEW_LINE_SEPARATOR);
			int numeroRegistros = 6000;
			for (int i = 1000; i < numeroRegistros; i++) {
				// ID
				fileWriter.append(String.valueOf(i));
				fileWriter.append(COMMA_DELIMITER);
				// tipo
				String tipoArea = getRandomTipoArea();
				fileWriter.append(tipoArea);
				fileWriter.append(COMMA_DELIMITER);
				// estado
				String estado = "LIBERADA";
				fileWriter.append(estado);
				fileWriter.append(COMMA_DELIMITER);
				// Capacidad disponible
				int capacidadDisponible = getRandomCapacidadArea();
				fileWriter.append(String.valueOf(capacidadDisponible));
				fileWriter.append(COMMA_DELIMITER);

				// capacidad máxima
				int capacidadMaxima = 20000;
				fileWriter.append(String.valueOf(capacidadMaxima));
				fileWriter.append(NEW_LINE_SEPARATOR);

			}

		} catch (Exception e) {
			System.out.println("Error en la creacion el CSV");
			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}

		}
	}

	private static void writeCvsFileCargaIOArea(String fileNameCarga,
			String fileNameIOArea) {

		FileWriter fileWriterCarga = null;
		FileWriter fileWriterIOArea = null;

		try {

			fileWriterCarga = new FileWriter(fileNameCarga);
			fileWriterIOArea = new FileWriter(fileNameIOArea);

			fileWriterCarga.append(FILE_HEADER_CARGA.toString());
			fileWriterIOArea.append(FILE_HEADER_IOAREA.toString());
			fileWriterCarga.append(NEW_LINE_SEPARATOR);
			fileWriterIOArea.append(NEW_LINE_SEPARATOR);

			int numeroRegistros = 2000000;

			String fechaIngresoString = "2017-01-01";
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date fechaInicio = formatter.parse(fechaIngresoString);
			System.out.println("la fecha inicial string es " + fechaIngresoString
					+ " y en Date es " + fechaInicio);

			String fechaSalidaString = "2017-01-03";
			formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date fechaFin = formatter.parse(fechaSalidaString);
			System.out.println("la fecha final string es " + fechaSalidaString
					+ " y en Date es " + fechaFin);
			int contadorCincoMil = 0;
			for (int i = 1000; i < numeroRegistros; i++) {
				
				
				if (contadorCincoMil % 5000 == 0) {

					fechaInicio = DateUtil.addDays(fechaInicio, 2);
					fechaFin = DateUtil.addDays(fechaFin, 2);
				}

				// CARGA

				// metemos el id en CARGA (embarrada la SEQ)
				fileWriterCarga.append(String.valueOf(i));
				fileWriterCarga.append(COMMA_DELIMITER);
				// volumen
				int volumen = getRandomVolumen();
				fileWriterCarga.append(String.valueOf(volumen));
				fileWriterCarga.append(COMMA_DELIMITER);
				// tipo
				String tipoCarga = getRandomTipoCarga();
				fileWriterCarga.append(String.valueOf(tipoCarga));
				fileWriterCarga.append(COMMA_DELIMITER);
				// id importador
				int idImportador = getRandomImportador();
				fileWriterCarga.append(String.valueOf(idImportador));
				fileWriterCarga.append(COMMA_DELIMITER);
				// destino
				String destino = "PUERTOANDES";
				fileWriterCarga.append(String.valueOf(destino));
				fileWriterCarga.append(COMMA_DELIMITER);
				// id exportador
				int idExportador = getRandomExportador();
				fileWriterCarga.append(String.valueOf(idExportador));
				fileWriterCarga.append(COMMA_DELIMITER);
				// origen
				String origen = getRandomOrigen();
				fileWriterCarga.append(String.valueOf(origen));
				fileWriterCarga.append(COMMA_DELIMITER);

				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				// fecha orden
				String fechaInicioStringAppend = df.format(fechaInicio);
				fileWriterCarga.append(String.valueOf(fechaInicioStringAppend));
				fileWriterCarga.append(NEW_LINE_SEPARATOR);

				// IOAREA

				// metemos el id en IO AREA (embarrada la SEQ)
				fileWriterIOArea.append(String.valueOf(i));
				fileWriterIOArea.append(COMMA_DELIMITER);
				// idCarga
				fileWriterIOArea.append(String.valueOf(i));
				fileWriterIOArea.append(COMMA_DELIMITER);
				// idArea
				int idArea = getRandomArea();
				fileWriterIOArea.append(String.valueOf(idArea));
				fileWriterIOArea.append(COMMA_DELIMITER);
				// viene de
				String vieneDe = "BARCO";
				fileWriterIOArea.append(String.valueOf(vieneDe));
				fileWriterIOArea.append(COMMA_DELIMITER);
				// fecha orden
				fileWriterIOArea.append(String.valueOf(fechaInicioStringAppend));
				fileWriterIOArea.append(COMMA_DELIMITER);
				// fecha Salida
				String fechaFinStringAppend = df.format(fechaFin);
				fileWriterIOArea.append(String.valueOf(fechaFinStringAppend));
				fileWriterIOArea.append(COMMA_DELIMITER);
				// idImoportador
				fileWriterIOArea.append(String.valueOf(idImportador));

				fileWriterIOArea.append(NEW_LINE_SEPARATOR);
				
				contadorCincoMil += 1;

			}

		} catch (Exception e) {
			System.out.println("Error en la creacion el CSV");
			e.printStackTrace();
		} finally {
			try {
				fileWriterCarga.flush();
				fileWriterCarga.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}

		}

	}

	private static String getRandomOrigen() {
		int min = 1000;
		int max = 5999;
		Random rand = new Random(Double.doubleToLongBits(Math.random()));

		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;
		String str = "ORIGEN";
		return str + randomNum;
	}

	private static int getRandomArea() {
		int min = 1250;
		int max = 1499;
		Random rand = new Random(Double.doubleToLongBits(Math.random()));

		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	private static int getRandomExportador() {
		int min = 1250;
		int max = 1499;
		Random rand = new Random(Double.doubleToLongBits(Math.random()));

		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	private static int getRandomImportador() {
		int min = 1000;
		int max = 1249;
		Random rand = new Random(Double.doubleToLongBits(Math.random()));

		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	private static String getRandomTipoCarga() {
		Random r = new Random();
		String[] tipos = { Carga.CONTENEDOR, Carga.GRANEL,
				Carga.VEHICULAR };
		return tipos[r.nextInt(tipos.length)];
	}

	private static String getRandomTipoImportador() {
		Random r = new Random();
		String[] tipos = { Importador.NO_HABITUAL, Importador.HABITUAL };
		return tipos[r.nextInt(tipos.length)];
	}

	private static String getRandomTipoPersona() {
		Random r = new Random();
		String[] tipos = { Persona.JURIDICA, Persona.NATURAL };
		return tipos[r.nextInt(tipos.length)];
	}

	private static String getRandomTipoArea() {
		Random r = new Random();
		String[] tipos = { Area.BODEGA, Area.COBERTIZO, Area.SILO, Area.PATIO };
		return tipos[r.nextInt(tipos.length)];
	}

	private static String getRandomTipoBuque() {
		Random r = new Random();
		String[] tipos = { Buque.PORTACONTENEDOR, Buque.MULTIPROPOSITO,
				Buque.RORO };
		return tipos[r.nextInt(tipos.length)];
	}

	private static int getRandomCapacidad() {
		// TODO Auto-generated method stub
		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int min = 1;
		int max = 500;
		Random rand = new Random();

		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	private static int getRandomVolumen() {
		// TODO Auto-generated method stub
		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int min = 50;
		int max = 500;
		Random rand = new Random(Double.doubleToLongBits(Math.random()));

		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	private static int getRandomCapacidadArea() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int min = 10000;
		int max = 20000;
		Random rand = new Random();

		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	public static int randInt(int min, int max) {

		Random rand = new Random();

		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	public static void main(String[] args) {
		String fileName = System.getProperty("user.home") + "/employee.csv";
		// Poblamiento.writeCsvFileEmployee(fileName);

		String fileNameBuque = System.getProperty("user.home") + "/buques.csv";
		// Poblamiento.writeCvsFileBuque(fileNameBuque);

		String fileNameIOBuque = System.getProperty("user.home") + "/iobuque.csv";
		// Poblamiento.writeCvsFileIOBuque(fileNameIOBuque);

		String fileNamePersona = System.getProperty("user.home") + "/persona.csv";
		String fileNameImportador = System.getProperty("user.home")
				+ "/importador.csv";
		String fileNameExportador = System.getProperty("user.home")
				+ "/exportador.csv";

		// Poblamiento.writeCvsFilePersonaImportadorExportador(fileNamePersona,
		// fileNameImportador, fileNameExportador);

		String fileNameArea = System.getProperty("user.home") + "/area.csv";
		//Poblamiento.writeCvsFileArea(fileNameArea);
		
		String fileNameCarga = System.getProperty("user.home") + "/carga.csv";
		String fileNameIOArea = System.getProperty("user.home") + "/ioarea.csv";
		Poblamiento.writeCvsFileCargaIOArea(fileNameCarga, fileNameIOArea);

	}

}
