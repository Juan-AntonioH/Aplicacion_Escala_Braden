package GestionModelo;

import com.braden.libraries.NotificationsAndAlerts;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import modelo.Resultado;

/**
 * Braden_Proyect (DAW-CAE)
 *
 * @author Esteban Juan Antonio Olga Santi
 * @version 1.0
 */
/**
 * Clase que gestiona el modelo Test
 */
public class TestDAO {

    /**
     * Atributo gesRiesgo, representa un objeto de la clase gesRiesgo necesaria
     * para calcular el riesgo y representarlo en String.
     */
    private RiesgoDAO gesRiesgo = new RiesgoDAO();

    /**
     * Atributo baseDeDatos, representa la conexión a la base de datos para
     * realizar las operaciones necesarias.
     */
    private Connection baseDeDatos;

    /**
     * Atributo listarresultados, representa una coleccion ArrayList donde se
     * almacenan todos los resultados de todos los pacientes.
     */
    private List<Resultado> listaresultados = new ArrayList<>();

    /**
     * Metodo para establecer la conexion a la bda nada mas cargar la ventana
     *
     * @param base pasando la conexion de la ventana que llama a este metodo
     */
    public void conectar(Connection base) {
        baseDeDatos = base;
    }

    /**
     * Metodo que inserta un nuevo resultado en la base de datos con los
     * parametros pasados.
     *
     * @param idPaciente
     * @param puntos
     * @param id_riesgo
     * @return Integer , si es 1 se ha realizado correctamente si es 0 no.
     */
    public Integer insertResultados(int idPaciente, Integer[] puntos, int id_riesgo) {
        int resultado = 0;
        try ( PreparedStatement ps = baseDeDatos.prepareStatement("INSERT INTO Resultados VALUES (?,?,?,?,?,?,?,?,?)")) {
            ps.setTimestamp(1, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
            ps.setInt(2, idPaciente);
            for (int posicion = 0; posicion < puntos.length; posicion++) {
                ps.setInt(posicion + 3, puntos[posicion]);
            }
            ps.setInt(9, id_riesgo);
            resultado = ps.executeUpdate();
        } catch (SQLException e) {
            resultado = 0;
            NotificationsAndAlerts.generaNotification("SCALA BRADEN", e.getMessage(), "error");
        }
        return resultado;
    }

    //GETTER DEL ARRAYLIST
    public List<Resultado> mostrarHistorial() {
        return listaresultados;
    }

    /**
     * Metodo que extrae todos los resultado de la base de datos para
     * representarla en un TableView
     *
     * @param base
     * @param paciente
     * @see com.braden.vista.Historial
     */
    public void extraerResultadosDB(Connection base, int paciente) {
        int[] puntosPreguntas = new int[6];
        try ( PreparedStatement ps = baseDeDatos.prepareStatement("SELECT * FROM escala_braden.Resultados WHERE paciente=?")) {
            ps.setInt(1, paciente);
            ResultSet resultados = ps.executeQuery();
            while (resultados.next()) {
                int sumaPuntos = 0;
                for (int posicion = 0; posicion < puntosPreguntas.length; posicion++) {
                    puntosPreguntas[posicion] = resultados.getInt(posicion + 3);
                    sumaPuntos += puntosPreguntas[posicion];
                }
                crearObjetoResultado(resultados.getTimestamp(1), resultados.getInt(2), puntosPreguntas, sumaPuntos, resultados.getInt(9));
            }
        } catch (SQLException e) {
            NotificationsAndAlerts.generaNotification("SCALA BRADEN", e.getMessage(), "error");
        }
    }

    /**
     * Metodo que almacena el resultado pasado como parametro en la lista de
     * resultados.
     *
     * @param resultado
     */
    public void almacenarResultado(Resultado resultado) {
        listaresultados.add(resultado);
    }

    /**
     * Metodo que compureba si la lista de resultados esta vacia.
     *
     * @return boolean
     */
    public boolean comprobarLista() {
        return !listaresultados.isEmpty();
    }

    /**
     * Metodo que crea un objeto Resultado con los parametros pasados y llama al
     * metodo almacenarResultado para introducirlo en la coleccion
     *
     * @param timestamp
     * @param paciente
     * @param puntosPreguntas
     * @param sumaPuntos
     * @param idRiesgo
     */
    private void crearObjetoResultado(Timestamp timestamp, int paciente, int[] puntosPreguntas, int sumaPuntos, int idRiesgo) {
        String riesgo = gesRiesgo.riesgoSegunID(idRiesgo);
        Resultado resultado = new Resultado(timestamp, paciente, puntosPreguntas, sumaPuntos, riesgo);
        almacenarResultado(resultado);
    }

}
