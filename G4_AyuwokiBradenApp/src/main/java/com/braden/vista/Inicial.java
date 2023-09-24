package com.braden.vista;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Braden_Proyect (DAW-CAE)
 *
 * @author Esteban Juan Antonio Olga Santi
 * @version 1.0
 */
/**
 * Clase que representa la parte visual de la ventana inicial
 */
public class Inicial {

    /**
     * Metodo que accede a la ventana del login al clickear el boton Acceder.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void entrarLogin(ActionEvent event) throws IOException {
        App.setRoot("login");
    }

    /**
     * Metodo que muestra la informacion sobre ulceras al pulsar el botón ver
     * info.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void verInfo(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("info.fxml"));
        Parent root = (Parent) loader.load();
        Informacion info = loader.getController();
        Stage stage = new Stage();
        Scene nuevo = new Scene(root);
        stage.setScene(nuevo);
        stage.setTitle("Información");
        stage.initOwner(App.getPrincipal());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }
}
