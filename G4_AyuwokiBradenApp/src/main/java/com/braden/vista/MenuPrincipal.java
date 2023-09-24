package com.braden.vista;

import com.braden.libraries.NotificationsAndAlerts;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Paciente;
import modelo.Usuario;

/**
 * Braden_Proyect (DAW-CAE)
 *
 * @author Esteban Juan Antonio Olga Santi
 * @version 1.0
 */
/**
 * Clase que representa la parte visual menu principal.
 */
public class MenuPrincipal {

    /**
     * Atributo final indica la opacidad cuando un elemento visual esta deshabilitado.
     */
    private final double OPACIDAD_DESACTIVADO = 0.4;
    
    /**
     * Atributo baseDeDatos, representa la conexión de la base de datos.
     */
    private Connection baseDeDatos;
    
    /**
     * Atributo usuario, representa un objeto Usuario.
     */
    private Usuario usuario;
    
    /**
     * Atributo paciente, representa un objeto Paciente.
     */
    private Paciente paciente;
    
    /**
     * Boton visual ver informacion.
     */
    @FXML
    private Button bInfo;
    
    /**
     * Boton visual salir del menu principal.
     */
    @FXML
    private Button bSalir;
    
    /**
     * Imagen visual que indica realizar test.
     */
    @FXML
    private ImageView realizarTest;
    
    /**
     * Imagen visual que indica ver historial.
     */
    @FXML
    private ImageView verHistorial;
    
    /**
     * Etiqueta donde esta presente el nombre del usuario.
     */
    @FXML
    private Label labelNombreUsuario;
    
    /**
     * Etiqueta donde esta presente el nombre del paciente.
     */
    @FXML
    private Label labelNombrePaciente;
    
    /**
     * Texto Realizar Test.
     */
    @FXML
    private Text txt_test;
    
    /**
     * Texto Ver Historial.
     */
    @FXML
    private Text txt_historial;
    
    /**
     * Representan toda la información disponible a la izquierda en el bocadillo.
     */
    @FXML
    private Text textInfoNoPaciente;
    @FXML
    private Text textInfoNoPaciente1;
    @FXML
    private Text textInfoNoPaciente11;

    
    /**
     * Metodo que recibe los datos del login pasados como parametro.
     * @param base
     * @param user 
     */
    public void moverBaseDeDatosMenuPrincipal(Connection base, Usuario user) {
        baseDeDatos = base;
        usuario = user;
        labelNombreUsuario.setText(usuario.getNombre());
        labelNombrePaciente.setText("Paciente no seleccionado");
        realizarTest.setDisable(true);
        realizarTest.setOpacity(OPACIDAD_DESACTIVADO);
        verHistorial.setDisable(true);
        verHistorial.setOpacity(OPACIDAD_DESACTIVADO);
        txt_test.setOpacity(OPACIDAD_DESACTIVADO);
        txt_historial.setOpacity(OPACIDAD_DESACTIVADO);
    }

    
    /**
     * Metodo que abre una ventana con la informacion acerca de las ulceras al clickear el boton ver info.
     * @param event
     * @throws IOException 
     */
    @FXML
    private void verInfo(MouseEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("info.fxml"));
        Parent root = (Parent) loader.load();
        Stage stage=new Stage();
        Scene nuevo= new Scene(root);
        stage.setScene(nuevo);
        stage.setTitle("Información");
        stage.initOwner(App.getPrincipal());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }

    /**
     * Metodo que sale del menu principal para regresar al login.
     * @param event
     * @throws IOException 
     * @see Login
     */
    @FXML
    private void volverALogin(MouseEvent event) throws IOException {
        App.setRoot("login");
    }

    /**
     * Metodo que manda a la ventana listar paciente con la base de datos y el usuario.
     * @param event
     * @throws IOException 
     */
    @FXML
    private void alPulsarImagenPaciente(MouseEvent event) throws IOException {
        App.setRoot("listarPaciente");
        ListarPaciente crearPaciente = App.getFxmlLoader().getController();
        crearPaciente.moverBaseDeDatosPaciente(baseDeDatos, usuario);
    }

    /**
     * Metodo que manda a la ventana hacer test con la base de datos , el usuario y el paciente.
     * @param event
     * @throws IOException 
     */
    @FXML
    private void alPulsarTest(MouseEvent event) throws IOException, SQLException {
        NotificationsAndAlerts.generaNotification("SCALA BRADEN", "Los resultados del test no estan comprobados "
                + "cientificamente.", "information");
        App.setRoot("hacerTest");
        RealizarTest realizarTest = App.getFxmlLoader().getController();
        realizarTest.moverDatosRealizarTest(baseDeDatos, usuario, paciente);
    }

    /**
     * Metodo que manda a la ventanahistorial con la base de datos , el usuario y el paciente.
     * @param event
     * @throws IOException 
     */
    @FXML
    private void alPulsarHistorial(MouseEvent event) throws IOException {
        App.setRoot("historial");
        Historial historial = App.getFxmlLoader().getController();
        historial.recibirDatos(baseDeDatos, usuario, paciente);
    }

    /**
     * Metodo que recibe los datos de vuelta al salir de las ventanas Listar Pacientes, Hacer Test o Ver Historial.
     * @param base
     * @param user
     * @param pacient 
     */
    public void moverDatosMenuPrincipal(Connection base, Usuario user, Paciente pacient) {
        baseDeDatos = base;
        usuario = user;
        paciente = pacient;
        labelNombreUsuario.setText(usuario.getNombre());
        labelNombrePaciente.setText(paciente.getNombre());
    }

}
