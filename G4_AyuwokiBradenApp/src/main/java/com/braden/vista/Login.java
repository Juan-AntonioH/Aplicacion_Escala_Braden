package com.braden.vista;

import com.braden.libraries.Encrypter;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import modelo.Usuario;

/**
 * Braden_Proyect (DAW-CAE)
 *
 * @author Esteban Juan Antonio Olga Santi
 * @version 1.0
 */
/**
 * Clase que representa la ventana visual Login.
 */
public class Login{

    /**
     * Atributo baseDeDatos, representa la conexión de la base de datos.
     */
    private Connection baseDeDatos;
    
    /**
     * Atributo gestionUsuario, representa un objeto de la clase UsuarioDAO.
     */
    private UsuarioDAO gestionUsuario;
    
    /**
     * Atributo usuario, representa un objeto de la clase Usuario.
     */
    private Usuario usuario;
    
    /**
     * Atributo encriptacion, representa un objeto de la clase Encrypter.
     */
    private Encrypter encriptacion = new Encrypter();
    
    /**
     * Representa una imagen validar.png.
     */
    private Image validar;
    
    /**
     * Representa una imagen error.png.
     */
    private Image error;
    
    /**
     * Boton visual para acceder al menuPrincipal.
     */
    @FXML
    private Button botonAcceder;
    
    /**
     * Campo de texto, donde se introduce el usuario.
     */
    @FXML
    private TextField textFieldUsuario;
    
    /**
     * Campo de texto, donde se introduce la contraseña del usuario.
     */
    @FXML
    private PasswordField textFieldContrasenya;
    
    /**
     * Boton visual crear usuario.
     */
    @FXML
    private Button botonCrearUsuario;
    
    /**
     * Elementos visuales Imagen, aqui se representa las imagenes validar.png y error.png.
     */
    @FXML
    private ImageView imagenVerificarUsuario;
    @FXML
    private ImageView imagenVerificarPassw;

    /**
     * Redifinición del metodo initialize
     * @param url
     * @param rb 
     */
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//        imagenesComprobaciones();
//    }

    /**
     * Metodo que carga las imagenes en la aplicación.
     */
    private void imagenesComprobaciones() {
        validar = new Image(getClass().getResourceAsStream("..\\..\\..\\Imagenes\\cheque.png"));
        error = new Image(getClass().getResourceAsStream("..\\..\\..\\Imagenes\\wrong.png"));
    }

    /**
     * Metodo que establece conexion con base de datos y manda al menu principal.
     * @param event
     * @throws IOException 
     */
    @FXML
    private void alPulsarAcceder(MouseEvent event) throws IOException {
        if (baseDeDatos == null) {
            establecerConexion();
            comprobarUsuarioLista();
        } else {
            comprobarUsuarioLista();
        }
    }

    /**
     * Metodo que redirige a una ventana crear usuario.
     * @param event
     * @throws IOException 
     * @see CrearUsuario
     */
    @FXML
    private void alPulsarCrearUsuario(MouseEvent event) throws IOException {
        App.setRoot("crearUsuario");
    }

    
    /**
     * Metodo que comprueba que el usuario existe.
     * @return boolean
     */
    private boolean autentificacionUsuario() {
        boolean comprobarUser;
        boolean comprobarPasswd = false;
        usuario = gestionUsuario.localizarUsuario(textFieldUsuario.getText());
        comprobarUser = comprobarUsuario(usuario);
        if (comprobarUser) {
            comprobarPasswd = comprobarContrasenya();
        }
        return comprobarPasswd;
    }
    
    /**
     * Metodo que establece imagenes segun si el usuario existe o no.
     * @return boolean
     */
    private boolean comprobarUsuario(Usuario usuario) {
        boolean verificacion = true;
        if (usuario != null) {
            imagenVerificarUsuario.setImage(validar);
        } else {
            imagenVerificarUsuario.setImage(error);
            verificacion = false;
        }
        return verificacion;
    }

    /**
     * Metodo que comprueba que la contraseña es correcta y pertenece a ese usuario.
     * @return boolean
     */
    private boolean comprobarContrasenya() {
        String pass = encriptacion.encriptar(textFieldContrasenya.getText());
        boolean comprobacionPasswd = true;
        if (usuario.getContrasenya().equalsIgnoreCase(pass)) {
            imagenVerificarPassw.setImage(validar);
        } else {
            imagenVerificarPassw.setImage(error);
            textFieldContrasenya.clear();
            comprobacionPasswd = false;
        }
        return comprobacionPasswd;
    }

    /**
     * Metodo que establece conexion con la base de datos.
     */
    private void establecerConexion() {
        BDA bda = new BDA();
        try {
            boolean conectarBDA = bda.conectar();
            if (conectarBDA) {
                baseDeDatos = bda.getConn();
                gestionUsuario = new UsuarioDAO();
                gestionUsuario.extraerUsuariosBD(baseDeDatos);
            } else {
                throw new SQLException();
            }
        } catch (SQLException ex) {
            NotificationsAndAlerts.generaNotification("ERROR BASE DE DATOS", ex.getMessage(), "error");
        }
    }

    /**
     * Metodo que manda al menu principal si la comprobación de credenciales a sido exitosa.
     * @throws IOException 
     */
    private void comprobarUsuarioLista() throws IOException {
        boolean comprobarUsuario = autentificacionUsuario();
        if (comprobarUsuario) {
            App.setRoot("menuPrincipal");
            if (gestionUsuario.primeraConexion(usuario)) {
                NotificationsAndAlerts.generaNotification("Escala de Braden", "Primera vez conectado", "information");
            }
            MenuPrincipal menuPrincipal = App.getFxmlLoader().getController();
            menuPrincipal.moverBaseDeDatosMenuPrincipal(baseDeDatos, usuario);
        }
    }
}
