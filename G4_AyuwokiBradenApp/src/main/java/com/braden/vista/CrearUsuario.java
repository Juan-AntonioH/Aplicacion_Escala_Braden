package com.braden.vista;

import GestionModelo.UsuarioDAO;
import com.braden.baseDatos.BDA;
import com.braden.libraries.NotificationsAndAlerts;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * Braden_Proyect (DAW-CAE)
 *
 * @author Esteban Juan Antonio Olga Santi
 * @version 2.1
 */
/**
 * Clase que realiza la parte visual de la ventana crearUsuario
 */
public class CrearUsuario implements Initializable {

    /**
     * Atributo bda, representa un objeto de tipo BDA
     */
    private BDA bda;

    /**
     * Atributo mensajeError, representa mensaje de error personalizado que
     * cambia en función de los errores que se encuentra al ejecutar un metodo.
     */
    private String mensajeError;

    /**
     * Atributo baseDeDatos, representa la conexión de la base de datos.
     */
    private Connection baseDeDatos;

    /**
     * Atributo gestionUsuario, representa un objeto de la clase UsuarioDAO.
     */
    private UsuarioDAO gestionUsuario;

    /**
     * Boton visual para crear usuario
     */
    @FXML
    private Button botonCrearUsuario;

    /**
     * Campo de texto donde se introduce el nombre del usuario.
     */
    @FXML
    private TextField textFieldNombre;

    /**
     * Campo de texto donde se introduce el primer apellido del usuario.
     */
    @FXML
    private TextField textFieldApellido1;

    /**
     * Campo de texto donde se introduce el segundo apellido del usuario.
     */
    @FXML
    private TextField textFieldApellido2;

    /**
     * Campo de texto donde se introduce el correo del usuario.
     */
    @FXML
    private TextField textFieldCorreo;

    /**
     * Campo de texto donde se introduce la contraseña del usuario.
     */
    @FXML
    private TextField textFieldContrasenya;


    /**
     * Imagen ubicada en el botón volver atrás
     */
    @FXML
    private ImageView bVolver;

    
    /**
     * Redefinicion del metodo initialize, nada mas arrancar la ventana establece conexion con la bda.
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        establecerConexion();
    }

    /**
     * Metodo que crea un usuario que se activa al pulsar el boton crear usuario.
     * @param event
     * @throws IOException 
     */
    @FXML
    private void alPulsarCrearUsuario(MouseEvent event) throws IOException {
        String nombre = textFieldNombre.getText();
        String apellido1 = textFieldApellido1.getText();
        String apellido2 = textFieldApellido2.getText();
        String correo = textFieldCorreo.getText();
        String contrasenya = textFieldContrasenya.getText();
        boolean comprobarDatosUsuario = comprobarDatosIntroducidos(nombre, apellido1, correo, contrasenya);
        if (comprobarDatosUsuario) {
            boolean respuesta = NotificationsAndAlerts.generaAlert("CONFIRMATION", "SCALA BRADEN", "¿Estas seguro de crear el usuario", "");
            if (respuesta) {
                int resultadoCrearUsuario = gestionUsuario.crearUsuarioNuevo(nombre, apellido1, apellido2, correo, contrasenya);
                if (resultadoCrearUsuario == 1) {
                    NotificationsAndAlerts.generaNotification("SCALA BRADEN", "FELICIDADES, el usuario fue creado exitosamente.", "information");
                    App.setRoot("login");
                } else {
                    NotificationsAndAlerts.generaNotification("SCALA BRADEN", "No se pudo guardar el usuario en la base de datos. El error es: " + resultadoCrearUsuario, "information");
                }
            }
        } else {
            NotificationsAndAlerts.generaNotification("SCALA BRADEN", mensajeError, "warning");
        }

    }

    /**
     * Metodo que comprueba todos los campos introducidos en todos los campos de texto pasados como parametro.
     * @param nombre
     * @param apellido1
     * @param correo
     * @param contrasenya
     * @return boolean, true si no ha habido errores, false si si han habido.
     */
    private boolean comprobarDatosIntroducidos(String nombre, String apellido1, String correo, String contrasenya) {
        mensajeError = "";
        boolean comprobarDatos = true;
        final String CORREO = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9]+\\.[A-Za-z]{2,3}$";
        final String DATOSPERSONALES = "^[A-Za-z]{1,}";
        final String PASSWD = "^[A-Za-z0-9+_.-]{6,}";
        if (!nombre.matches(DATOSPERSONALES)) {
            mensajeError += "Nombre: Debes introducir mínimo un caracter, no números\n";
            comprobarDatos = false;
        }
        if (!apellido1.matches(DATOSPERSONALES)) {
            mensajeError += "Apellido1: Debes introducir mínimo un caracter, no números\n";
            comprobarDatos = false;
        }
        if (!correo.matches(CORREO)) {
            mensajeError += "Correo: Has introducido un correo no valido.\n";
            comprobarDatos = false;
        }
        if (!contrasenya.matches(PASSWD)) {
            mensajeError += "Contraseña: Has introducido una contraseña no valida, mínimo 6 carácteres.";
            comprobarDatos = false;
        }
        return comprobarDatos;
    }

    /**
     * Metodo que establece la conexion a la bda.
     */
    private void establecerConexion() {
        bda = new BDA();
        try {
            boolean conectarBDA = bda.conectar();
            if (conectarBDA) {
                baseDeDatos = bda.getConn();
                gestionUsuario = new UsuarioDAO();
                gestionUsuario.conectarBDA(baseDeDatos);
            } else {
                throw new SQLException();
            }
        } catch (SQLException ex) {
            NotificationsAndAlerts.generaNotification("Error conexion base de datos", ex.getMessage(), "error");
        }
    }

    
    /**
     * Metodo para volver a la ventana login al pulsar el boton volver atrás.
     * @param event
     * @throws IOException
     * @throws SQLException 
     */
    @FXML
    private void alPulsarVolver(MouseEvent event) throws IOException, SQLException {
        App.setRoot("login");
        bda.desconectar();
    }
}
