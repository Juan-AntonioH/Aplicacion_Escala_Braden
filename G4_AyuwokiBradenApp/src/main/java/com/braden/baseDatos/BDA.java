package com.braden.baseDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Braden_Proyect (DAW-CAE)
 *
 * @author Esteban Juan Antonio Olga Santi
 * @version 1.0
 */
/**
 * Clase que representa la conexión de la base de datos, establecimiento y
 * cierre de conexión.
 */
public class BDA {

    /**
     * Atributo conn, representa la conexion que se utilizara en todas las
     * clases que la necesiten.
     */
    private Connection conn;

    /**
     * Atributo final URL, representa la url para establecer conexión en mysql.
     */
    private final String URL = "jdbc:mysql://localhost:3306/escala_braden";

    /**
     * Atributo final USUARIO, representa el usuario para poder entrar a la bda.
     */
    private final String USUARIO = "root";

    /**
     * Atributo final PASSWD, representa la contraseña del usuario para poder
     * entrar a la bda.
     */
    private final String PASSWD = "daw1";

    /**
     * Metodo que conecta a la base de datos.
     *
     * @return boolean
     * @throws SQLException
     */
    public boolean conectar() throws SQLException {
        conn = DriverManager.getConnection(URL, USUARIO, PASSWD);
        return conn != null;
    }

    /**
     * Metodo que desconecta de la base de datos
     *
     * @return boolean
     * @throws SQLException
     */
    public boolean desconectar() throws SQLException {
        try {
            conn.close();
            conn = null;
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    //GETTER DE LA CONEXION
    public Connection getConn() {
        return conn;
    }
}
