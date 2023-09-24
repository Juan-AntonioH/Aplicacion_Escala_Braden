package com.braden.libraries;

import java.util.Optional;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * Braden_Proyect (DAW-CAE)
 *
 * @author Esteban Juan Antonio Olga Santi
 * @version 2.1
 */
/**
 * Clase que generara todas las Notificaciones y Alertas que se representen
 * durante la ejeución de todo el programa.
 */
public class NotificationsAndAlerts {

    /**
     * Atributo estatico alerta, representa un objeto de tipo Alert.
     */
    private static Alert alerta;

    /**
     * Metodo estatico que genera una notificación estableciendo el formato de
     * mensaje segun los parametros pasados.
     *
     * @param titulo
     * @param texto
     * @param tipo
     */
    public static void generaNotification(String titulo, String texto, String tipo) {
        Notifications notificacion = Notifications.create()
                .title(titulo)
                .position(Pos.CENTER)
                .text(texto)
                .hideAfter(Duration.seconds(5));
        if (tipo.equalsIgnoreCase("error")) {
            notificacion.showError();
        } else if (tipo.equalsIgnoreCase("warning")) {
            notificacion.showWarning();
        } else if (tipo.equalsIgnoreCase("information")) {
            notificacion.showInformation();
        }

    }

    /**
     * Metodo que genera un Alert con el formato segun los parametros pasados.
     *
     * @param tipo
     * @param titulo
     * @param cabecera
     * @param texto
     * @return boolean, true si es de tipo CONFIRMATION, false si es otro tipo
     */
    public static boolean generaAlert(String tipo, String titulo, String cabecera, String texto) {
        boolean resultado = false;
        alerta = new Alert(Alert.AlertType.valueOf(tipo));
        alerta.setTitle(titulo);
        alerta.setHeaderText(cabecera);
        alerta.setContentText(texto);
        if (alerta.getAlertType() == Alert.AlertType.CONFIRMATION) {
            resultado = confirmacion(alerta);
        } else {
            // Si es un mensaje de confirmación, redirige a otro método para gestionar los botones.
            alerta.showAndWait();
        }
        return resultado;
    }

    /**
     * Metodo que sera llamado exclusivamente cuando la generacion del alert sea
     * de tipo CONFIRMATION
     *
     * @param mensaje
     * @return boolean, true si ha seleccionado ACEPTAR,false si no.
     */
    private static boolean confirmacion(Alert mensaje) {
        boolean opcion;
        ButtonType botonAceptar = new ButtonType("ACEPTAR");
        ButtonType botonCancelar = new ButtonType("CANCELAR", ButtonBar.ButtonData.CANCEL_CLOSE);
        mensaje.getButtonTypes().setAll(botonAceptar, botonCancelar);
        Optional<ButtonType> resultado = mensaje.showAndWait();
        String eleccion = resultado.get().getText();
        if (eleccion.equals("ACEPTAR")) {
            opcion = true;
        } else {
            opcion = false;
        }
        return opcion;
    }
}
