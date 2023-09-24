package com.braden.vista;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.image.Image;

/**
 * Braden_Proyect (DAW-CAE)
 *
 * @author Esteban Juan Antonio Olga Santi
 * @version 2.0
 */
/**
 * Clase que gestiona las imágenes del test.
 */
public class GestionImagenes {

    /**
     * Método que recibe un resultado y devuelve la imagen según su posición.
     *
     * @param result Posición especifica del SELECT * FROM Items
     * @return Devuelve la imagen especifica según la posición del resultado
     * @throws SQLException
     */
    public Image obtenerImagenPrincipal(ResultSet result) throws SQLException {
        byte byteImage[] = null;
        Blob blob = result.getBlob(3);
        byteImage = blob.getBytes(1, (int) blob.length());
        Image img = new Image(new ByteArrayInputStream(byteImage));
        return img;
    }

    /**
     * Método que recibe un número y devuelve la ruta de las imágenes a mostrar.
     *
     * @param posicion Indica la posición del array.
     * @return Devuelve una lista de imágenes.
     */
    public String[] rutaImagenes(int posicion) {
        String[] rutas = new String[6];
        switch (posicion) {
            case 0:
                rutas[0] = "images/sens_rp1.png";
                rutas[1] = "images/sens_rp2.png";
                rutas[2] = "images/sens_rp3.png";
                rutas[3] = "images/sens_rp4.png";
                rutas[4] = "";
                rutas[5] = "PERCEPCIÓN SENSORIAL.\nCapacidad para reaccionar ante una molestia relacionada con la presión.\n"
                        + "Por ejemplo: si el individuo recibe un golpe, de inmediato sentirá un intenso dolor que durará unos minutos.";
                break;
            case 1:
                rutas[0] = "images/exp_rp1.png";
                rutas[1] = "images/exp_rp2.png";
                rutas[2] = "images/exp_rp3.png";
                rutas[3] = "images/exp_rp4.png";
                rutas[4] = "";
                rutas[5] = "EXPOSICIÓN A LA HUMEDAD.\nNivel de exposición de la piel a la humedad.\n"
                        + "Ejemplo: sudor, orina, etc.";
                break;
            case 2:
                rutas[0] = "images/act_rp1.png";
                rutas[1] = "images/act_rp2.png";
                rutas[2] = "images/act_rp3.png";
                rutas[3] = "images/act_rp4.png";
                rutas[4] = "";
                rutas[5] = "ACTIVIDAD.\nNivel de actividad física. La movilidad que puede realizar el paciente.";
                break;
            case 3:
                rutas[0] = "images/mov_rp1.png";
                rutas[1] = "images/mov_rp2.png";
                rutas[2] = "images/mov_rp3.png";
                rutas[3] = "images/mov_rp4.png";
                rutas[4] = "";
                rutas[5] = "MOVILIDAD.\nCapacidad para cambiar y controlar la posición del cuerpo.\n"
                        + "Movimientos o cambios que puede realizar, del cuerpo o extremidades.";
                break;
            case 4:
                rutas[0] = "images/nutri_rp1.png";
                rutas[1] = "images/nutri_rp2.png";
                rutas[2] = "images/nutri_rp3.png";
                rutas[3] = "images/nutri_rp4.png";
                rutas[4] = "";
                rutas[5] = "NUTRICIÓN.\nPatrón usual de ingesta de alimentos.\n"
                        + "Cantidad de veces que come al día y que alimentos ingiere.";
                break;
            case 5:
                rutas[0] = "images/fr_rp1.png";
                rutas[1] = "images/fr_rp2.png";
                rutas[2] = "";
                rutas[3] = "";
                rutas[4] = "images/fr_rp3.png";
                rutas[5] = "ROCE Y PELIGRO DE LESIONES.\nPosibles lesiones de rozamientos o fracturas al moverse.";
                break;
        }
        return rutas;
    }

}
