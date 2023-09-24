/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.braden.vista;

import com.braden.libraries.NotificationsAndAlerts;
import java.io.IOException;
import java.net.URISyntaxException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Braden_Proyect (DAW-CAE)
 *
 * @author Esteban Juan Antonio Olga Santi
 * @version 1.0
 */
/**
 * Clase que representa al ventana visual informacion.
 */
public class Informacion {

    /**
     * Metodo que vuelve al menu al cerrar la ventana.
     * @param event
     * @throws IOException 
     */
    @FXML
    private void volvermenu(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    /**
     * Metodo que abre un pagina externa al clickear un link.
     * @param event 
     */
    @FXML
    private void linkAbrirNavegador(MouseEvent event) {
        if (java.awt.Desktop.isDesktopSupported()) {
            java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
            if (desktop.isSupported(java.awt.Desktop.Action.BROWSE)) {
                try {
                    java.net.URI uri = new java.net.URI("https://revistamedica.com/como/escala-de-braden/");
                    desktop.browse(uri);
                } catch (IOException | URISyntaxException e) {
                    NotificationsAndAlerts.generaNotification("Escala Braden", e.getMessage(), "error");
                }
            }
        }
    }

}
