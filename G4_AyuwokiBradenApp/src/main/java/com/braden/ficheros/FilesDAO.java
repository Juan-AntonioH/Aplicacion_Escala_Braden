package com.braden.ficheros;

import GestionModelo.RiesgoDAO;
import com.braden.libraries.NotificationsAndAlerts;
import com.braden.vista.App;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.stage.DirectoryChooser;
import modelo.Paciente;
import modelo.Resultado;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Braden_Proyect (DAW-CAE)
 *
 * @author Esteban Juan Antonio Olga Santi
 * @version 2.0
 */
/**
 * Clase que gestiona ficheros.
 */
public class FilesDAO {

    /**
     * Metodo que exporta los resultados que haya en un TableView en cierto
     * momento sobre un paciente.
     *
     * @param paciente
     * @param listaResultados
     * @param fechaFiltro
     * @return String, mensaje de que ha pasado con la operaciÃ³n
     */
    public String exportarResultados(Paciente paciente, List<Resultado> listaResultados, LocalDate fechaFiltro) {
        String mensaje = "";
        Path directorio = seleccionarCarpeta();
        Path directorioArchivo = Paths.get(directorio + "\\" + paciente.getNombre() + "_" + LocalDate.now() + ".txt");
        if (Files.exists(directorioArchivo)) {
            boolean respuesta = NotificationsAndAlerts.generaAlert("CONFIRMATION", "SCALA BRADEN", "EL ARCHIVO YA EXISTE, DESEA REMPLAZARLO?", "");
            if (respuesta) {
                mensaje = generaFicchero(directorioArchivo, listaResultados, paciente.getNombre(), fechaFiltro);
            }
        } else {
            mensaje = generaFicchero(directorioArchivo, listaResultados, paciente.getNombre(), fechaFiltro);
        }
        return mensaje;
    }

    /**
     * Metodo que permite seleccionar una carpeta al usuario que serÃ¡ usada
     * como referencia para generar el archivo.
     *
     * @return Path ruta de la carpeta que ha seleccionado el usuario
     */
    public Path seleccionarCarpeta() {
        try {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            File selectedDirectory = directoryChooser.showDialog(App.getPrincipal());
            Path directorio = selectedDirectory.toPath();
            return directorio;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Metodo que genera el fichero con los resultados con la ruta especificada
     * extraida del metodo seleccionarCarpeta.
     *
     * @param ruta
     * @param listaResultados
     * @param paciente
     * @param fechaFiltro
     * @return String, resultado de la generaciÃ³n de fichero
     */
    public String generaFicchero(Path ruta, List<Resultado> listaResultados, String paciente, LocalDate fechaFiltro) {
        String mensaje = "";
        try (BufferedWriter out = Files.newBufferedWriter(ruta, Charset.defaultCharset(), StandardOpenOption.CREATE)) {
            int mediaPuntos = 0;
            int contador = 0;
            out.flush();
            out.write("HISTORIAL DEL PACIENTE: " + paciente + "\n\n");
            out.write("FECHA DE FILTRO: " + fechaFiltro + "\n\n\n");
            out.write(String.format("%20s%-20s%-15s%-15S%-15s%-15s%-15s%-15s%-15s\n\n", "FECHA", "", "TOTAL", "RIESGO", "PERCEPCIÃ“N", "EXPOSICIÃ“N", "ACTIVIDAD", "MOVILIDAD", "NUTRICIÃ“N", "FRICCION"));
            for (Resultado resultado : listaResultados) {
                contador++;
                mediaPuntos += resultado.getSumaPuntos();
                out.write(String.valueOf(resultado) + "\n");
            }
            out.write("\n\nLa media de resultados del paciente " + paciente + " es de " + mediaPuntos / contador + " puntos");
        } catch (IOException e) {
            mensaje = e.getMessage();
        }
        return mensaje;
    }

    /**
     * Metodo que exporta los resultados que haya en un TableView en cierto
     * momento sobre un paciente.
     *
     * @param paciente
     * @param listaResultados
     * @param fechaFiltro
     * @param historial
     */
    public void exportarResultadosExcel(Paciente paciente, ObservableList<Resultado> listaResultados, LocalDate fechaFiltro, TableView<Resultado> historial) {
        Path directorio = seleccionarCarpeta();
        Path directorioArchivo = Paths.get(directorio + "\\" + paciente.getNombre() + "_" + LocalDate.now() + ".xls");
        if (Files.exists(directorioArchivo)) {
            boolean respuesta = NotificationsAndAlerts.generaAlert("CONFIRMATION", "SCALA BRADEN", "EL ARCHIVO YA EXISTE, DESEA REMPLAZARLO?", "");
            if (respuesta) {
                generaFicheroExcel(paciente, listaResultados, fechaFiltro, historial, directorioArchivo);
            }
        } else {
            generaFicheroExcel(paciente, listaResultados, fechaFiltro, historial, directorioArchivo);

        }

    }

    /**
     * Metodo que genera un Excel con los resultados con la ruta especificada
     * extraida del metodo seleccionarCarpeta.
     *
     * @param paciente
     * @param listaResultados
     * @param fechaFiltro
     * @param historial
     * @param directorioArchivo
     */
    private void generaFicheroExcel(Paciente paciente, ObservableList<Resultado> listaResultados, LocalDate fechaFiltro, TableView<Resultado> historial, Path directorioArchivo) {

        //Crear libro de trabajo en blanco
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        HSSFSheet hssfSheet = hssfWorkbook.createSheet("Resultados");
        HSSFRow firstRow = hssfSheet.createRow(0);
        hssfSheet.setColumnWidth(0, 9000);
        ///set titles of columns
        int posicion = 0;
        for (; posicion < historial.getColumns().size(); posicion++) {
            firstRow.createCell(posicion).setCellValue(historial.getColumns().get(posicion).getText());
        }

        int sumaTotal = 0;
        for (Resultado listaResultado : listaResultados) {
            sumaTotal += listaResultado.getSumaPuntos();
        }
        sumaTotal /= listaResultados.size();

        for (int row = 0; row < historial.getItems().size(); row++) {
            HSSFRow hssfRow = hssfSheet.createRow(row + 1);
            for (int col = 0; col < historial.getColumns().size(); col++) {
                Object celValue = historial.getColumns().get(col).getCellObservableValue(row).getValue();
                try {
                    if (celValue != null && Double.parseDouble(celValue.toString()) != 0.0) {
                        hssfRow.createCell(col).setCellValue(Double.parseDouble(celValue.toString()));
                    }
                } catch (NumberFormatException e) {
                    hssfRow.createCell(col).setCellValue(celValue.toString());
                }
            }
        }
        RiesgoDAO riesgo = new RiesgoDAO();
        int edad = LocalDate.now().getYear() - paciente.getFecha().getYear();
        int idRiesgo = riesgo.calcularRiesgo(sumaTotal, edad);
        HSSFRow filtro = hssfSheet.createRow(listaResultados.size() + 2);
        HSSFRow nombre = hssfSheet.createRow(listaResultados.size() + 3);
        HSSFRow media = hssfSheet.createRow(listaResultados.size() + 4);
        filtro.createCell(0).setCellValue("Fecha filtro: " + fechaFiltro);
        media.createCell(0).setCellValue("Media Puntos: " + sumaTotal + " Riesgo: " + riesgo.riesgoSegunID(idRiesgo));
        nombre.createCell(0).setCellValue("Paciente: " + paciente.getNombre());
        //Guarda el archivo excel y lo cierra.
        try {
            FileOutputStream out = new FileOutputStream(directorioArchivo.toFile());
            hssfWorkbook.write(out);
            out.close();
            NotificationsAndAlerts.generaNotification("ESCALA DE BRADEN", "Se creo el archivo con exito", "information");
        } catch (IOException e) {
            NotificationsAndAlerts.generaNotification("ESCALA DE BRADEN", e.getMessage(), "error");
        }

    }
}
