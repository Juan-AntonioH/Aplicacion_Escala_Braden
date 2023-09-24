package com.braden.vista;

import com.braden.libraries.NotificationsAndAlerts;
import java.io.IOException;
import java.net.URISyntaxException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelo.Paciente;

/**
 * Braden_Proyect (DAW-CAE)
 *
 * @author Esteban Juan Antonio Olga Santi
 * @version 2.0
 */
/**
 * Clase que representa la parte visual de la ventana recomendaciones.
 *
 * @author esteb
 */
public class Recomendaciones {

    /**
     * Atributo final puntuacionBaja, representa el numero a tomar en
     * consideración para indicar las recomendaciones.
     */
    private final int puntuacionBaja = 2;

    /**
     * Area de texto visual con la informacion de las recomendaciones.
     */
    @FXML
    private TextArea textAreaRecomendacion;

    /**
     * Etiqueta visual donde se indica el nombre del paciente.
     */
    @FXML
    private Label labelNombrePaciente;

    /**
     * Metodo que vuelve al menu principal al clickar el boton volver al menu.
     *
     * @param event
     */
    @FXML
    private void volverMenu(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    /**
     * Metodo que rediriga a una pagina externa al pulsar click en la url.
     *
     * @param event
     */
    @FXML
    private void alPulsarURL(MouseEvent event) {
        if (java.awt.Desktop.isDesktopSupported()) {
            java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
            if (desktop.isSupported(java.awt.Desktop.Action.BROWSE)) {
                try {
                    java.net.URI uri = new java.net.URI("https://medlineplus.gov/spanish/pressuresores.html");
                    desktop.browse(uri);
                } catch (IOException | URISyntaxException e) {
                    NotificationsAndAlerts.generaNotification("Escala Braden", e.getMessage(), "error");
                }
            }
        }
    }

    /**
     * Metodo que recoge los resultados del test realizado recientemente pasados
     * como parametro.
     *
     * @param paciente
     * @param puntos
     * @param riesgo
     */
    public void recogerDatosResultados(Paciente paciente, Integer[] puntos, String riesgo) {
        labelNombrePaciente.setText(paciente.getNombre());
        textAreaRecomendacion.setText("Riesgo de ulcera: " + riesgo + "\nRecomendaciones\n");
        recomendacionesSegunPuntos(puntos);
    }

    /**
     * Metodo que va calculando aquellos items en los que la puntuacion sea
     * menor que 2, para indicar mas tarde en el text area.
     *
     * @param puntos
     */
    private void recomendacionesSegunPuntos(Integer[] puntos) {
        boolean estaSano = true;
        for (int pregunta = 0; pregunta < puntos.length; pregunta++) {
            if (puntos[pregunta] <= puntuacionBaja) {
                recomendacionSegunPregunta(pregunta);
                estaSano = false;
            }
        }
        if (estaSano) {
            textAreaRecomendacion.appendText("¡Muy bien campeón, estás cojonudo!");
        }
    }

    /**
     * Metodo que aplica en el text area las recomendaciones segun parametro
     * pasado.
     *
     * @param pregunta
     */
    private void recomendacionSegunPregunta(int pregunta) {
        switch (pregunta) {
            case 0:
                textAreaRecomendacion.appendText("PERCEPCIÓN SENSORIAL: Evita acciones peligrosas.\n");
                break;
            case 1:
                textAreaRecomendacion.appendText("EXPOSICIÓN A LA HUMEDAD: Evita las humedades\n");
                break;
            case 2:
                textAreaRecomendacion.appendText("ACTIVIDAD: Realizar más actividades\n");
                break;
            case 3:
                textAreaRecomendacion.appendText("MOVILIDAD: Moverse más, paseos, etc.\n");
                break;
            case 4:
                textAreaRecomendacion.appendText("NUTRICIÓN: Deberías de alimentarte mejor.\n");
                break;
            case 5:
                textAreaRecomendacion.appendText("FRICCIÓN Y CIZALLAMIENTO: Evitar movimientos bruscos.");
                break;
        }
    }
}
