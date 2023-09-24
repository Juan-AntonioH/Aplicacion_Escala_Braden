package GestionModelo;

import com.braden.libraries.NotificationsAndAlerts;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import modelo.Paciente;

/**
 * Braden_Proyect (DAW-CAE)
 *
 * @author Esteban Juan Antonio Olga Santi
 * @version 2.0
 */
public class PacienteDAO {

    /**
     * Atributo baseDeDatos, representa la conexión de la bda
     */
    private Connection baseDeDatos;

    /**
     * Atributo listaPacientes, representa una coleccion ArrayList con la lista
     * de pacientes que haya en un momento dado en la base de datos.
     */
    private List<Paciente> listaPacientes = new ArrayList<>();

    /**
     * Atributo idPaciente, representa el identificador del ultimo paciente
     * creado, que sera necesario para realizar los metodos de la clase.
     */
    private int idPaciente = 0;

    /**
     * Metodo que extrae todos los pacientes que haya en el momento en MySQL.
     *
     * @param base conexión de base de datos.
     *
     */
    public void extraerPacientesBD(Connection base) {
        baseDeDatos = base;
        try ( PreparedStatement ps = baseDeDatos.prepareStatement("SELECT * FROM escala_braden.Pacientes", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ResultSet resultados = ps.executeQuery();
            while (resultados.next()) {
                Date tmp = resultados.getDate(3);
                crearObjetoPaciente(resultados.getInt("id"), resultados.getString("nombre"),
                        Instant.ofEpochMilli(tmp.getTime()).atZone(ZoneId.systemDefault()).toLocalDate());

            }
            resultados.last();
            this.idPaciente = resultados.getInt("id");
        } catch (SQLException e) {
            NotificationsAndAlerts.generaNotification("SCALA BRADEN", e.getMessage(), "error");
        }

    }

    /**
     * Metodo que almacena el paciente pasado como parametro en la colección
     * donde se encuentran todos los pacientes.
     *
     * @param paciente
     */
    public void almacenarPaciente(Paciente paciente) {
        listaPacientes.add(paciente);
    }

    //GETTER ARRAYLIST
    public List<Paciente> listaPacientes() {
        return listaPacientes;
    }

    /**
     * Metodo que comprueba si la lista de los pacientes esta vacia.
     *
     * @return
     */
    public boolean comprobarLista() {
        return !listaPacientes().isEmpty();
    }

    /**
     * Metodo inserta el paciente en la base de datos con los datos pasados como
     * parametro.
     *
     * @param nombre
     * @param fecha_nacimiento
     * @return Integer, representa 1 si el proceso ha funcionado o 0 si no
     *
     */
    public Integer crearPacienteNuevo(String nombre, LocalDate fecha_nacimiento) {
        int rows = 0;
        try ( PreparedStatement ps = baseDeDatos.prepareStatement("INSERT INTO Pacientes (nombre, fecha_nac) VALUES (?,?)")) {
            ps.setString(1, nombre);
            ps.setDate(2, java.sql.Date.valueOf(fecha_nacimiento));
            rows = ps.executeUpdate();
            crearObjetoPaciente(++idPaciente, nombre, fecha_nacimiento);
        } catch (SQLException e) {
            idPaciente = e.getErrorCode();
        }
        return rows;
    }

    /**
     * Metodo que crea el objeto Paciente y lo almacena en la lista de
     * pacientes.
     *
     * @param id
     * @param nombre
     * @param fecha_na
     */
    private void crearObjetoPaciente(int id, String nombre, LocalDate fecha_na) {
        Paciente paciente = new Paciente(id, nombre, fecha_na);
        almacenarPaciente(paciente);
    }
}
