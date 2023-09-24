package GestionModelo;

import com.braden.libraries.Encrypter;
import com.braden.libraries.NotificationsAndAlerts;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import modelo.Usuario;

/**
 * Braden_Proyect (DAW-CAE)
 *
 * @author Esteban Juan Antonio Olga Santi
 * @version 2.0
 */
/**
 * Clase que gestiona el modelo Usuario
 */
public class UsuarioDAO {

    /**
     * Atributo baseDeDatos, que representa la conexion bda que será utilizada
     * para los metodos oportunos de la clase.
     */
    private Connection baseDeDatos;

    /**
     * Atributo listaUsuarios, representa una coleccion HashMap con clave tipo
     * String como correo electronico y objeto Usuario como valor.
     */
    private Map<String, Usuario> listaUsuarios = new HashMap<>();

    /**
     * Atributo encriptacion, objeto de la clase Encrypter, se encarga de
     * encriptar las contraseñas con algoritmo SHA-256
     */
    private Encrypter encriptacion = new Encrypter();

    /**
     * Metodo que se encarga de extraer todos los usuarios que haya en ese
     * momento en la bda.
     *
     * @param base conexion bda.
     */
    public void extraerUsuariosBD(Connection base) {
        baseDeDatos = base;
        try ( PreparedStatement ps = baseDeDatos.prepareStatement("SELECT * FROM escala_braden.Usuarios")) {
            ResultSet resultados = ps.executeQuery();
            while (resultados.next()) {
                Usuario usuario = new Usuario(resultados.getInt("id"), resultados.getString("nombre"),
                        resultados.getString("apellido1"), resultados.getString("apellido2"),
                        resultados.getString("correo"), resultados.getString("contrasenya"));
                almacenarUsuario(usuario);
            }
        } catch (SQLException e) {
            NotificationsAndAlerts.generaNotification("SCALA BRADEN", e.getMessage(), "error");
        }
    }

    /**
     * Metodo para encontrar un usuario por su correo electronico en el HashMap.
     *
     * @param mailUsuario
     * @return Usuario, objeto al que le corresponde ese mail.
     */
    public Usuario localizarUsuario(String mailUsuario) {
        if (listaUsuarios.containsKey(mailUsuario)) {
            return listaUsuarios.get(mailUsuario);
        } else {
            return null;
        }
    }

    /**
     * Metodo para almacenar en la coleccion de lista usuarios a un usuario
     * pasado como parametro.
     *
     * @param usuario
     * @return Usuario, el objeto almacenado
     */
    public Usuario almacenarUsuario(Usuario usuario) {
        return listaUsuarios.put(usuario.getCorreo(), usuario);
    }

    /**
     * Metodo que comprueba si la coleccion esta vacia
     *
     * @return boolean
     */
    public boolean comprobarLista() {
        return !listaUsuarios.isEmpty();
    }

    /**
     * Metodo que inserta en la base de datos al usuario con los datos pasados
     * como parametro y con contrasenya encriptada.
     *
     * @param nombre
     * @param apellido1
     * @param apellido2
     * @param correo
     * @param contrasenya
     * @see Encrypter
     * @exception SQLException si ha habido algun error con la base de datos
     * @return Integer, si devuelve 1 ha salido bien si devuelve 0 no.
     */
    public Integer crearUsuarioNuevo(String nombre, String apellido1, String apellido2, String correo, String contrasenya) {
        int resultado = 0;
        String pass = encriptacion.encriptar(contrasenya);
        try ( PreparedStatement ps = baseDeDatos.prepareStatement("INSERT INTO Usuarios (id, nombre, apellido1, apellido2, correo, contrasenya) VALUES (?,?,?,?,?,?)")) {
            ps.setString(1, null);
            ps.setString(2, nombre);
            ps.setString(3, apellido1);
            ps.setString(4, apellido2);
            ps.setString(5, correo);
            ps.setString(6, pass);
            resultado = ps.executeUpdate();
        } catch (SQLException e) {
            resultado = e.getErrorCode();
        }
        return resultado;
    }

    /**
     * Metodo que conecta a la base de datos nada mas iniciar la ventana
     *
     * @param conn la conexion pasada como parametro de la ventana que llama a
     * este metodo.
     */
    public void conectarBDA(Connection conn) {
        baseDeDatos = conn;
    }

    /**
     * Metodo que comprueba si es la primera vez que el usuario inicia sesion en
     * la app
     *
     * @param usuario
     * @exception SQLException si ha habido algun error con la base de datos
     * @return boolean
     */
    public boolean primeraConexion(Usuario usuario) {
        boolean resultado = false;
        try ( PreparedStatement ps = baseDeDatos.prepareStatement("SELECT * FROM escala_braden.Usuarios WHERE id=?")) {
            ps.setInt(1, usuario.getId());
            ResultSet resultados = ps.executeQuery();
            while (resultados.next()) {
                resultado = resultados.getBoolean("primeravez");
                if (resultado) {
                    actualizarPrimeraConexion(usuario);
                }
            }
        } catch (SQLException e) {
            NotificationsAndAlerts.generaNotification("SCALA BRADEN", e.getMessage(), "error");
        }
        return resultado;
    }

    /**
     * Metodo que actualiza el campo de la primera conexion en la base de datos
     *
     * @param usuario
     * @exception SQLException si ha habido algun error con la base de datos
     */
    public void actualizarPrimeraConexion(Usuario usuario) {
        try ( PreparedStatement ps = baseDeDatos.prepareStatement("Update usuarios set primeravez=0 where id=?;")) {
            ps.setInt(1, usuario.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            NotificationsAndAlerts.generaNotification("SCALA BRADEN", e.getMessage(), "error");
        }
    }
}
