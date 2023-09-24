package com.braden.vista;

import GestionModelo.PacienteDAO;
import com.braden.libraries.NotificationsAndAlerts;
import java.io.IOException;
import java.sql.Connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Paciente;
import modelo.Usuario;

public class ListarPaciente {

    /**
     * Atributo usuraio, que representa un objeto tipo Usuario.
     */
    private Usuario usuario;

    /**
     * Atributo paciente, que representa un objeto tipo Paciente.
     */
    private Paciente paciente;

    /**
     * Atributo baseDeDatos, que representa la conexion a la bda.
     */
    private Connection baseDeDatos;

    /**
     * Atributo gestPaciente, que representa un objeto tipo PacienteDAO.
     */
    private PacienteDAO gestPaciente;

    /**
     * List View visual donde se representa todos los pacientes actuales que hay
     * en la bda.
     */
    @FXML
    private ListView<Paciente> listViewPacientes;
    private ObservableList<Paciente> listaPacientes;

    /**
     * Metodo que recoge los datos pasados como parametro de otras ventanas en
     * sus atributos.
     *
     * @param base
     * @param user
     */
    public void moverBaseDeDatosPaciente(Connection base, Usuario user) {
        baseDeDatos = base;
        usuario = user;
        gestPaciente = new PacienteDAO();
        gestPaciente.extraerPacientesBD(base);
        listarPacientes();
    }

    /**
     * Metodo que regresa al menu principal al pulsar el boton volver atras.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void alPulsarVolver(MouseEvent event) throws IOException {
        if (paciente == null) {
            volverMenuSinPaciente();
        } else {
            volverMenuConPaciente();
        }

    }

    /**
     * Metodo que almacena en el atributo paciente, el paciente seleccionado en
     * el ListView.
     *
     * @param event
     */
    @FXML
    private void seleccionarPaciente(MouseEvent event) {
        try {
            int posicionPaciente = listViewPacientes.getSelectionModel().getSelectedIndex();
            if (posicionPaciente >= 0) {// Evita error si clickeas en la ventana sin seleccionar ningun paciente la primera vez. El error no sucede si clickeas despues de seleccionar a alguien.
                paciente = listaPacientes.get(posicionPaciente);
            }
        } catch (Exception e) { // Por si ocurre algún otro tipo de error.
            NotificationsAndAlerts.generaNotification("SCALA BRADEN", e.getMessage(), "error");
        }
    }

    /**
     * Metodo que lista todos los pacientes y los representa en la ListView.
     */
    private void listarPacientes() {
        listaPacientes = FXCollections.observableArrayList(gestPaciente.listaPacientes());
        listViewPacientes.setItems(listaPacientes);
    }

    /**
     * Metodo que regresa al menu principal pasando paciente como parametro en
     * la ventana.
     *
     * @throws IOException
     */
    private void volverMenuConPaciente() throws IOException {
        App.setRoot("menuPrincipal");
        MenuPrincipal menuPrincipal = App.getFxmlLoader().getController();
        menuPrincipal.moverDatosMenuPrincipal(baseDeDatos, usuario, paciente);
    }

    /**
     * Metodo que regresa al menu principal sin pasar paciente.
     *
     * @throws IOException
     */
    private void volverMenuSinPaciente() throws IOException {
        App.setRoot("menuPrincipal");
        MenuPrincipal menuPrincipal = App.getFxmlLoader().getController();
        menuPrincipal.moverBaseDeDatosMenuPrincipal(baseDeDatos, usuario);
    }

    /**
     * Metodo que redirige a una ventana nueva en donde se puede crear el
     * paciente.
     *
     * @param event
     * @throws IOException
     * @see CrearPaciente
     */
    @FXML
    private void crearPaciente(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("creaPaciente.fxml"));
        Parent root = (Parent) loader.load();
        CrearPaciente crearPaciente = loader.getController();
        crearPaciente.moverBaseDeDatos(baseDeDatos, gestPaciente);
        Stage stage = new Stage();
        Scene nuevo = new Scene(root);
        stage.setScene(nuevo);
        stage.setTitle("Nuevo paciente");
        stage.initOwner(App.getPrincipal());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
        stage.setOnHidden(eh -> listarPacientes());
    }
}
