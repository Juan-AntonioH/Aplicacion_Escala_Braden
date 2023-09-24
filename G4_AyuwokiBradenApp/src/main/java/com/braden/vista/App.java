package com.braden.vista;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.image.Image;

/**
 * Braden_Proyect (DAW-CAE)
 *
 * @author Esteban Juan Antonio Olga Santi
 * @version 1.0
 */
/**
 * Clase que carga la apliación FX
 */
public class App extends Application {

    /**
     * Atributo scene, representa la escena donde se muestran todos los
     * elementos visuales.
     */
    private static Scene scene;
    /**
     * Atributo fxmlLoader, representa el archivo fxml.
     */
    private static FXMLLoader fxmlLoader;

    /**
     * Atributo principal, representa el escenarios donde se encuentran las
     * escenas.
     */
    private static Stage principal;

    /**
     * Redefinición del metodo, se encarga de cargar la pagina principal de la
     * aplicación
     *
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("inicial"));
        stage.setScene(scene);
        stage.setTitle("Scala Braden");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("images/Logo_Basico.png")));
        stage.show();
        principal = stage;
    }

    /**
     * Metodo que establece un fxml pasado como parametro.
     * @param fxml
     * @throws IOException 
     */
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * Metodo que realiza la carga de una archivo fxml en un Objeto FXMLLoader.
     * @param fxml
     * @return
     * @throws IOException 
     */
    public static Parent loadFXML(String fxml) throws IOException {
        fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    //GETTER FXMLLOADER
    public static FXMLLoader getFxmlLoader() {
        return fxmlLoader;
    }

    /**
     * Metodo principal, main.
     * @param args 
     */
    public static void main(String[] args) {
        launch();
    }

    //GETTER STAGE
    public static Stage getPrincipal() {
        return App.principal;
    }

}
