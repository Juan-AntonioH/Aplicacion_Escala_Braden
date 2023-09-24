/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.braden.vista;

import GestionModelo.TestDAO;
import com.braden.ficheros.FilesDAO;
import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import modelo.Paciente;
import modelo.Resultado;
import modelo.Usuario;

/**
 * Braden_Proyect (DAW-CAE)
 *
 * @author Esteban Juan Antonio Olga Santi
 * @version 1.0
 */
/**
 * Clase que representa la parte visual de la ventana historial.
 */
public class Historial {

    /**
     * Atributo baseDeDatos, representa la conexión de la base de datos.
     */
    private Connection baseDeDatos;

    /**
     * Atributo usuario representa un objeto de tipo Ususario.
     */
    private Usuario usuario;

    /**
     * Atributo paciente, representa un objeto de tipo Paciente.
     */
    private Paciente paciente;

    /**
     * Atributo gestTest, representa un objeto de tipo TestDAO.
     */
    TestDAO gesTest;

    /**
     * Atributo fechaFiltro, donde se almacena la fecha seleccionada en el
     * elemento visual datepicker.
     */
    LocalDate fechaFiltro;

    /**
     * Atributo gestionFicheros, representa un objeto de tipo FilesDAO.
     */
    FilesDAO gestionFicheros;

    /**
     * Elemento visual TableView, se representa el historial de resultados.
     */
    @FXML
    private TableView<Resultado> historial;
    private ObservableList<Resultado> listaResultados = FXCollections.observableArrayList();
    TableColumn fecha = new TableColumn("Fecha");
    TableColumn pregunta1 = new TableColumn("Percepcion");
    TableColumn pregunta2 = new TableColumn("Exposición");
    TableColumn pregunta3 = new TableColumn("Actividad");
    TableColumn pregunta4 = new TableColumn("Movilidad");
    TableColumn pregunta5 = new TableColumn("Nutrición");
    TableColumn pregunta6 = new TableColumn("Fricción");
    TableColumn sumaPuntos = new TableColumn("Total");
    TableColumn riesgo = new TableColumn("Riesgo");

    /**
     * Elemento visual datepicker.
     */
    @FXML
    private DatePicker fechaFin;

    /**
     * Boton visual exportar resultados.
     */
    @FXML
    private Button btn_exportar;

    /**
     * Etiqueta de texto, donde se representa el paciente al que se le esta
     * aplicando el historial.
     */
    @FXML
    private Label labelNombrePaciente;

    /**
     * Boton visual volver atrás.
     */
    @FXML
    private Button bVolver1;

    /**
     * Imagen contenida dentro del botón volver atrás.
     */
    @FXML
    private ImageView bVolver;

    /**
     * Metodo que recibe los datos necesarios pasados como parametro para
     * representar el historial.
     *
     * @param conn
     * @param user
     * @param pacient
     */
    public void recibirDatos(Connection conn, Usuario user, Paciente pacient) {
        baseDeDatos = conn;
        usuario = user;
        paciente = pacient;
        gesTest = new TestDAO();
        gesTest.conectar(conn);
        gesTest.extraerResultadosDB(conn, paciente.getId());
        labelNombrePaciente.setText(paciente.getNombre());
        historial.getColumns().addAll(fecha, pregunta1, pregunta2, pregunta3, pregunta4, pregunta5, pregunta6, sumaPuntos, riesgo);
        añadirResultados();
        mostrarResultados();
        fechaFiltro=LocalDate.now();
        fechaFin.setValue(LocalDate.now());
        gestionFicheros = new FilesDAO();
    }

    /**
     * Metodo que vuelve al menu principal al clickear el boton atrás.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void volverAMenu(ActionEvent event) throws IOException {
        App.setRoot("menuPrincipal");
        MenuPrincipal menuPrincipal = App.getFxmlLoader().getController();
        menuPrincipal.moverDatosMenuPrincipal(baseDeDatos, usuario, paciente);
    }

    /**
     * Metodo que carga todos las columnas del bda junto a los del TableView.
     */
    private void mostrarResultados() {
        fecha.setCellValueFactory(new PropertyValueFactory<Resultado, String>("fecha"));
        pregunta1.setCellValueFactory(new PropertyValueFactory<Resultado, String>("puntos_item1"));
        pregunta2.setCellValueFactory(new PropertyValueFactory<Resultado, String>("puntos_item2"));
        pregunta3.setCellValueFactory(new PropertyValueFactory<Resultado, String>("puntos_item3"));
        pregunta4.setCellValueFactory(new PropertyValueFactory<Resultado, String>("puntos_item4"));
        pregunta5.setCellValueFactory(new PropertyValueFactory<Resultado, String>("puntos_item5"));
        pregunta6.setCellValueFactory(new PropertyValueFactory<Resultado, String>("puntos_item6"));
        sumaPuntos.setCellValueFactory(new PropertyValueFactory<Resultado, String>("sumaPuntos"));
        riesgo.setCellValueFactory(new PropertyValueFactory<Resultado, String>("riesgo"));
        historial.setItems(listaResultados);
    }

    /**
     * Metodo que carga los datos en las columnas de la TableView previamente
     * cargadas.
     */
    public void añadirResultados() {
        listaResultados.clear();
        listaResultados.addAll(gesTest.mostrarHistorial());
    }

    /**
     * Metodo que cambia los resultados de la TableView al escoger otra fecha en
     * el datepicker.
     *
     * @param event
     */
    @FXML
    private void filtrarResultdosPorFecha(ActionEvent event) {
        listaResultados.clear();
        fechaFiltro = fechaFin.getValue();
        for (Resultado resultado : gesTest.mostrarHistorial()) {
            LocalDateTime fechaResultado = resultado.getFechaSinFormatear();
            if (fechaResultado.isBefore(fechaFiltro.atStartOfDay().plusDays(1))) {
                listaResultados.add(resultado);
            }
        }
        mostrarResultados();
    }

    /**
     * Metodo que exporta resultados que haya en la TableView y los guarda en un
     * fichero.
     *
     * @param event
     * @see FilesDAO
     */
    @FXML
    private void exportarResultados(ActionEvent event) {
        gestionFicheros.exportarResultadosExcel(paciente, listaResultados, fechaFiltro,historial);
    }

}
