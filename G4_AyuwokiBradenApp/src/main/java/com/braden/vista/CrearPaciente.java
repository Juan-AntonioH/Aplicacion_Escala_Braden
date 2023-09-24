package com.braden.vista;

import GestionModelo.PacienteDAO;
import com.braden.libraries.NotificationsAndAlerts;
import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Braden_Proyect (DAW-CAE)
 *
 * @author Esteban Juan Antonio Olga Santi
 * @version 2.1
 */
/**
 * Clase que realiza la parte visual de la ventana crear paciente.
 */
public class CrearPaciente {

    /**
     * Atributo fechaNacimiento, representa la fecha de nacimiento del paciente
     * al crearlo, se utiliza el valor de un datepicker.
     */
    private LocalDate fechaNacimiento;

    /**
     * Atributo mensajeError, representa mensaje de error personalizado que
     * cambia en función de los errores que se encuentra al ejecutar un metodo.
     */
    private String mensajeError = "";

    /**
     * Atributo gestPaciente, representa un objeto de tipo PacienteDAO.
     */
    private PacienteDAO gestPaciente;

    /**
     * Atributo conn, representa la conexión de la base de datos.
     */
    private Connection conn;

    /**
     * Boton visual crear paciente.
     */
    @FXML
    private Button bCrearPaciente;

    /**
     * Campo de texto donde se introduce el nombre del paciente.
     */
    @FXML
    private TextField textFieldNomPaciente;

    /**
     * Calendario visual donde se selecciona la fecha de nacimiento.
     */
    @FXML
    private DatePicker dataPickerFechaNacimiento;

    /**
     * Metodo que recibe la conexion y el objeto PacienteDAO del menu principal.
     *
     * @param conn
     * @param base
     */
    public void moverBaseDeDatos(Connection conn, PacienteDAO base) {
        gestPaciente = base;
        this.conn = conn;
        dataPickerFechaNacimiento.setValue(LocalDate.now().minusYears(50));
        fechaNacimiento = dataPickerFechaNacimiento.getValue();

    }

    /**
     * Metodo que crea paciente al pulsar el boton bCrearPaciente
     *
     * @param event
     */
    @FXML
    private void crearPaciente(ActionEvent event) {
        String nombre = textFieldNomPaciente.getText();
        LocalDate fecha_na = fechaNacimiento;
        boolean comprobarDatosPaciente = comprobarDatosIntroducidos(nombre, fecha_na);
        if (comprobarDatosPaciente) {
            boolean respuesta = NotificationsAndAlerts.generaAlert("CONFIRMATION", "SCALA BRADEN", "¿Estas seguro de crear el paciente", "");
            if (respuesta) {
                int resultadoCrearPaciente = gestPaciente.crearPacienteNuevo(nombre, fechaNacimiento);
                if (resultadoCrearPaciente == 1) {
                    System.out.println("hola");
                    cerrarStage(event);
                    NotificationsAndAlerts.generaNotification("SCALA BRADEN", "FELICIDADES, el paciente fue creado exitosamente.", "information");
                } else {
                    NotificationsAndAlerts.generaNotification("SCALA BRADEN", "No se pudo guardar el paciente en la base de datos. Codigo de error: " + resultadoCrearPaciente, "error");
                }
            }
        } else {
            NotificationsAndAlerts.generaNotification("SCALA BRADEN", mensajeError, "warning");
        }
    }

    /**
     * Metodo que comprueba los datos que se han introducido en el campo de
     * texto y el date picker.
     *
     * @param nombre
     * @param fecha
     * @return boolean
     */
    private boolean comprobarDatosIntroducidos(String nombre, LocalDate fecha) {
        mensajeError = "";
        boolean comprobarDatos = true;
        final String DATOSPERSONALES = "[^0-9@]{2,}";
        if (!nombre.matches(DATOSPERSONALES)) {
            mensajeError += "Nombre: Debes introducir mínimo un caracter, no números\n";
            comprobarDatos = false;
        }

        if (!(fecha.isAfter(LocalDate.now().minusYears(120)) && fecha.isBefore(LocalDate.now()))) {
            mensajeError += "Fecha nacimiento: Has introducido una fecha poco creible.";
            comprobarDatos = false;
        }
        return comprobarDatos;
    }

    
    /**
     * Metodo que obtiene la fecha del datepicker.
     * @param event 
     */
    @FXML
    private void verfecha(ActionEvent event) {
        fechaNacimiento = dataPickerFechaNacimiento.getValue();
    }

    /**
     * Metodo que cierra el stage al pulsar la x o el boton atrás
     * @param event 
     */
    private void cerrarStage(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    /**
     * Metodo que cierra el stage al pulsar el boton atrás
     * @param event 
     */
    @FXML
    private void alPulsarVolver(ActionEvent event) throws IOException {
       cerrarStage(event);
    }
}
