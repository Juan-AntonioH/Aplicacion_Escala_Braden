package GestionModelo;

import com.braden.libraries.NotificationsAndAlerts;
import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.image.Image;

/**
 * Braden_Proyect (DAW-CAE)
 *
 * @author Esteban Juan Antonio Olga Santi
 * @version 2.0
 */
public class ItemsDAO {

    /**
     * Atributo conn, representa la conexión de la bda
     */
    Connection conn;

    /**
     *
     * @param conn
     */
    public ItemsDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Método que guarda en una Array las imágenes de la bda.
     *
     * @return Devuelve un Array Image.
     * @throws SQLException
     */
    public Image[] cargarImagenesRespuestas() throws SQLException {
        int posicion = 0;
        byte byteImage[] = null;
        Image[] respuestas = new Image[6];
        try ( PreparedStatement psd = conn.prepareStatement("SELECT * FROM Items", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ResultSet result = psd.executeQuery();
            while (result.next()) {
                Blob blob = result.getBlob(3);
                byteImage = blob.getBytes(1, (int) blob.length());
                Image img = new Image(new ByteArrayInputStream(byteImage));
                respuestas[posicion] = img;
                posicion++;
            }
        } catch (Exception e) {
            NotificationsAndAlerts.generaNotification("SCALA BRADEN", e.getMessage(), "error");
        }
        return respuestas;
    }

    /**
     * Método que recoge el resultado de un select a la tabla Items de la dba.
     *
     * @return Devuelve el resultado del "SELECT * FROM Items"
     */
    public ResultSet resultadoListaImagenes() {
        ResultSet result = null;
        try {
            PreparedStatement psd = conn.prepareStatement("SELECT * FROM Items",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            result = psd.executeQuery();
            result.first();
        } catch (SQLException e) {
            NotificationsAndAlerts.generaNotification("SCALA BRADEN", e.getMessage(), "error");
        }
        return result;
    }

}
