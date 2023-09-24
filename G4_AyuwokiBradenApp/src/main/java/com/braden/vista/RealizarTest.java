package com.braden.vista;

import GestionModelo.ItemsDAO;
import GestionModelo.RiesgoDAO;
import GestionModelo.TestDAO;
import com.braden.libraries.NotificationsAndAlerts;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Paciente;
import modelo.Usuario;

/**
 * Braden_Proyect (DAW-CAE)
 *
 * @author Esteban Juan Antonio Olga Santi
 * @version 2.0
 */
/**
 * Clase que representa que se encarga de realizar el test.
 */
public class RealizarTest {

    /**
     * Atributo gTest, representa un objeto de la clase TestDAO.
     */
    private TestDAO gTest;
    /**
     * Atributo que contiene el resultado de un Select de la bda.
     */
    private ResultSet result;
    /**
     * Atributo items, representa un objeto de la clase ItemsDAO.
     */
    private ItemsDAO items;
    /**
     * Atributo gesRiesgos, representa un objeto de la clase RiesgoDAO.
     */
    private RiesgoDAO gesRiesgo;
    /**
     * Opacidad máxima en la imagen seleccionada.
     */
    private final int OPACIDAD_SELECCIONADO = 1;
    /**
     * Opacidad media en la imagen seleccionada.
     */
    private final double OPACIDAD_NO_SELECCIONADO = 0.4;
    /**
     * Opacidad inicial de las imagenes.
     */
    private final double OPACIDAD_INICIAL = 0.7;
    /**
     * Atributo que contiene una imagen.
     */
    private Image img;
    private Image img2;
    private Image img3;
    private Image img4;
    private Image img5;
    /**
     * Atributo auxiliar para que la imagen se muestre en la última pregunta.
     */
    private String auxPregunta5 = "";
    /**
     * Atributo que obtiene un número según la imagen seleccionada.
     */
    private int eleccion;
    /**
     * Atributo auxiliar para indicar la posición actual de la pregunta.
     */
    private int contador;
    /**
     * Lista con la puntuación de cada pregunta.
     */
    private Integer[] listaPuntuacionesPorPregunta = {0, 0, 0, 0, 0, 0};
    /**
     * Suma total de los puntos obtenidos en cada pregunta.
     */
    private int sumaPuntos;
    /**
     * Contiene el nombre según tu riesgo ALTO | MODERADO | BAJO.
     */
    private String tipoRiesgo;
    /**
     * Atributo auxiliar que contiene la edad calculada por la fecha de
     * nacimiento.
     */
    private int edadPaciente;
    /**
     * Id riesgo según los puntos obtenidos.
     */
    private int riesgo;
    /**
     * Contiene la Id del paciente.
     */
    private int idPaciente;
    /**
     * Atributo gesImagenes,representa un objeto de la clase GestionImagenes que
     * se encarga del acceso a los métodos que gestiona las imagenes.
     */
    private GestionImagenes gesImagenes;
    /**
     * Atributo baseDeDatos, representa la conexión de la base de datos.
     */
    private Connection baseDeDatos;
    /**
     * Atributo usuario, representa un objeto de la clase Usuario.
     */
    private Usuario usuario;
    /**
     * Atributo usuraio, que representa un objeto tipo Usuario.
     */
    private Paciente paciente;
    /**
     * Elementos visuales ImagenView, aqui se representa las imagenes de cada
     * pregunta.
     */
    @FXML
    private ImageView img_pregunta1;
    @FXML
    private ImageView img_respuesta1;
    @FXML
    private ImageView img_respuesta2;
    @FXML
    private ImageView img_respuesta3;
    @FXML
    private ImageView img_respuesta4;
    /**
     * Etiqueta donde esta presente el nombre del paciente.
     */
    @FXML
    private Label labelPaciente;
    /**
     * Etiqueta donde esta presente la edad del paciente.
     */
    @FXML
    private Label labelEdadPaciente;
    /**
     * Etiqueta donde esta presente el nombre del usuario.
     */
    @FXML
    private Label labelUsuario;
    /**
     * Etiqueta donde esta presente el resultado de los puntos.
     */
    @FXML
    private Label labelResultadoSuma;
    /**
     * Etiqueta donde esta presente la puntuación de la pregunta.
     */
    @FXML
    private Label labelRespuesta1;
    @FXML
    private Label labelRespuesta6;
    @FXML
    private Label labelRespuesta5;
    @FXML
    private Label labelRespuesta4;
    @FXML
    private Label labelRespuesta3;
    @FXML
    private Label labelRespuesta2;
    /**
     * Etiqueta donde esta presente la puntuación de la pregunta.
     */
    @FXML
    private ImageView res_pregunta1;
    @FXML
    private ImageView res_pregunta2;
    @FXML
    private ImageView res_pregunta3;
    @FXML
    private ImageView res_pregunta4;
    @FXML
    private ImageView res_pregunta5;
    @FXML
    private ImageView res_pregunta6;
    /**
     * Botón visual para volver a la pregunta anterior.
     */
    @FXML
    private Button botonAtras;
    /**
     * Botón visual para volver al menú principal.
     */
    @FXML
    private Button botonMenu;
    /**
     * Botón visual para ir a la siguiente pregunta.
     */
    @FXML
    private Button botonSiguiente;
    /**
     * Botón visual para enviar el test después de finalizarlo.
     */
    @FXML
    private Button botonEnviarTest;
    /**
     * Panel que contiene las imágenes de las preguntas.
     */
    @FXML
    private GridPane gridPaneImagenes;
    /**
     * Area de texto que contiene la información de las preguntas y resultado
     * final.
     */
    @FXML
    private TextArea textAreaInfo;
    /**
     * Imagen auxiliar para la ultima pregunta.
     */
    @FXML
    private ImageView img_respuesta5;

    /**
     * Método encargado de establecer los datos iniciales de los botones,
     * imágenes, puntuaciones, etc.
     */
    private void cargarDatosIniciales() {
        botonEnviarTest.setDisable(true);
        gridPaneImagenes.setVisible(false);
        botonAtras.setDisable(true);
        gTest = new TestDAO();
        gTest.conectar(baseDeDatos);
        gesRiesgo = new RiesgoDAO();
        contador = 0;
        sumaPuntos = 0;
        labelResultadoSuma.setText("" + sumaPuntos);
        labelUsuario.setText(usuario.getNombre() + " " + usuario.getApellido1());
        idPaciente = paciente.getId();
        LocalDate ahora = LocalDate.now();
        edadPaciente = ahora.getYear() - paciente.getFecha().getYear();
        labelEdadPaciente.setText(edadPaciente + " años");
        labelPaciente.setText(paciente.getNombre());
    }

    /**
     * Método para obtener las imágenes de SELECT * FROM Items de la base de
     * datos.
     *
     * @throws SQLException
     */
    private void listaDeImagenesBaseDeDatos() throws SQLException {
        result = items.resultadoListaImagenes();
        cargarImagenPrincipal();
    }

    /**
     * Método encargado de mostrar las imágenes de la pregunta que te
     * encuentras.
     *
     * @throws SQLException
     */
    private void cargarImagenPrincipal() throws SQLException {
        img_pregunta1.setVisible(true);
        Image image = gesImagenes.obtenerImagenPrincipal(result);
        img_pregunta1.setImage(image);
        cargaImagenesSegunPregunta(contador);

    }

    /**
     * Método encargado de ocultar o mostrar el panel y el textArea.
     */
    private void mostrarPanelConImagenes() {
        textAreaInfo.setVisible(false);
        gridPaneImagenes.setVisible(true);
        if (!"".equals(auxPregunta5)) {
            img_respuesta5.setVisible(true);
            transicionRespuestas(gridPaneImagenes);
            transicionRespuestas(img_respuesta5);
        } else {
            img_respuesta5.setVisible(false);
            transicionRespuestas(gridPaneImagenes);
        }
    }

//Control de preguntas y botones
    /**
     * Método encargado de establecer un número según la imagen seleccionada y
     * mostrar los puntos según selección, muestra las imágenes de las preguntas
     * si clickea en la imagen principal.
     *
     * @param event Acción del usuario sobre un elemento.
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    private void alSeleccionarImagen(MouseEvent event) throws SQLException, IOException {
        eleccion = 0;
        ImageView imagen = (ImageView) event.getSource();
        if (imagen == img_respuesta1) {
            eleccion = 1;
            opacidadImagenSeleccionada1();
        } else if (imagen == img_respuesta2) {
            eleccion = 2;
            opacidadImagenSeleccionada2();
        } else if (imagen == img_respuesta3) {
            eleccion = 3;
            opacidadImagenSeleccionada3();
        } else if (imagen == img_respuesta4) {
            eleccion = 4;
            opacidadImagenSeleccionada4();
        } else if (imagen == img_respuesta5) {
            eleccion = 3;
            opacidadImagenSeleccionada5();
        }
        if (imagen == img_pregunta1) {
            mostrarPanelConImagenes();
        } else {
            puntuacionItem(eleccion);
        }
    }

    /**
     * Método encargado de pasar a la siguiente pregunta y calcular los puntos.
     *
     * @param event Acción del usuario sobre el elemento.
     * @throws SQLException
     */
    @FXML
    private void preguntaSiguiente(MouseEvent event) throws SQLException {
        if (gridPaneImagenes.isVisible() == true && eleccion != 0) {
            listaPuntuacionesPorPregunta[contador] = eleccion;
            sumaPuntos += eleccion;
            contador++;
            labelResultadoSuma.setText("" + sumaPuntos);
            if (result.isLast()) {
                ultimaPreguntaRespondida();
            } else {
                siguientePreguntaNoUltima();
            }
            gridPaneImagenes.setVisible(false);
            textAreaInfo.setVisible(true);
        }
    }

    /**
     * Método encargado de volver a la pregunta anterior y calcular los puntos.
     *
     * @param event Acción del usuario sobre el elemento.
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    private void preguntaAnterior(MouseEvent event) throws SQLException, IOException {
        eleccion = 0;
        puntuacionItem(0);// pone en 0 los puntos de abajo de la pregunta en la que estabas
        if (result.isLast()) {// Si ya has clickado por ultima vez a siguiente resta los puntos guardados para evitar errore de puntos
            sumaPuntos -= listaPuntuacionesPorPregunta[contador];
            labelResultadoSuma.setText("" + sumaPuntos);
        }
        // Acciones para la pregunta anterior ya que no se guardo en la que estabas
        contador--;
        sumaPuntos -= listaPuntuacionesPorPregunta[contador];
        labelResultadoSuma.setText("" + sumaPuntos);
        puntuacionItem(0); // pone en 0 los puntos de la pregunta anterior
        botonEnviarTest.setDisable(true);
        botonSiguiente.setDisable(false);
        gridPaneImagenes.setVisible(false);
        result.previous();
        cargarImagenPrincipal();
        if (result.isFirst()) {
            botonAtras.setDisable(true);
        }
    }

    /**
     * Método establecer en las imágenes de abajo la puntuación de cada
     * pregunta.
     *
     * @param puntos Contiene los puntos según la imagen seleccionada.
     */
    private void puntuacionItem(int puntos) {
        switch (contador) {
            case 0:
                labelRespuesta1.setText("    " + puntos);
                break;
            case 1:
                labelRespuesta2.setText("    " + puntos);
                break;
            case 2:
                labelRespuesta3.setText("    " + puntos);
                break;
            case 3:
                labelRespuesta4.setText("    " + puntos);
                break;
            case 4:
                labelRespuesta5.setText("    " + puntos);
                break;
            case 5:
                labelRespuesta6.setText("    " + puntos);
                break;
        }
    }

    /**
     * Método que muestra como resultado los datos del usuario, paciente, puntos
     * de cada pregunta, suma total y el riesgo.
     */
    private void ultimaPreguntaRespondida() {
        RiesgoDAO nombreRiesgo = new RiesgoDAO();
        botonSiguiente.setDisable(true);
        botonEnviarTest.setDisable(false);
        contador--;
        img_pregunta1.setVisible(false);
        gridPaneImagenes.setVisible(false);
        img_respuesta5.setVisible(false);
        textAreaInfo.setVisible(true);
        riesgo = gesRiesgo.calcularRiesgo(sumaPuntos, edadPaciente);
        tipoRiesgo = nombreRiesgo.riesgoSegunID(riesgo);
        String mensajeResultado = "Usuario: " + labelUsuario.getText() + "\nPaciente: " + labelPaciente.getText() + "\nEdad: " + labelEdadPaciente.getText()
                + "\nPERCEPCIÓN SENSORIAL: " + labelRespuesta1.getText() + "\nEXPOSICIÓN A LA HUMEDAD: " + labelRespuesta2.getText()
                + "\nACTIVIDAD: " + labelRespuesta3.getText() + "\nMOVILIDAD: " + labelRespuesta4.getText()
                + "\nNUTRICIÓN: " + labelRespuesta5.getText() + "\nFRICCIÓN Y CIZALLAMIENTO: " + labelRespuesta6.getText()
                + "\nResultado:      " + labelResultadoSuma.getText() + "\nRIESGO: " + tipoRiesgo;
        textAreaInfo.setText(mensajeResultado);
    }

    /**
     * Método encargado de mostrar la información del textArea, cambiar la
     * imagen principal y habilitar o deshabilitar boton atrás o siguiente.
     *
     * @throws SQLException
     */
    private void siguientePreguntaNoUltima() throws SQLException {
        botonSiguiente.setDisable(false);
        result.next();
        cargarImagenPrincipal();
        botonAtras.setDisable(false);
        eleccion = 0;
    }

    /**
     * Método encargado de preguntar al usuario si quiere enviar los resultados
     * y añadirlos en la base de datos si acepta.
     *
     * @param event Acción del usuario sobre el elemento.
     * @throws IOException
     */
    @FXML
    private void alPulsarEnviarTest(MouseEvent event) throws IOException {
        boolean respuestaVentana = NotificationsAndAlerts.generaAlert("CONFIRMATION", "SCALA BRADEN", "¿DESEAS FINALIZAR EL TEST?", "");
        if (respuestaVentana) {
            guardarResultadoEnBDA();
        }
    }

//Imagenes
    /**
     * Método encargado de indicar que imágenes se muestra según pregunta.
     *
     * @param contador Número que indica que imágenes mostrar.
     */
    private void cargaImagenesSegunPregunta(int contador) {
        String[] imagenes = gesImagenes.rutaImagenes(contador);
        img_respuesta4.setVisible(true);
        img_respuesta5.setVisible(false);
        textAreaInfo.setText(imagenes[5]);
        imagenespreguntas(imagenes);
        opacidadImagenes();
    }

    /**
     * Método encargado de mostrar las imágenes seleccionadas pasadas por
     * parámetro.
     *
     * @param imagenes Contiene la ruta de las imágenes.
     */
    private void imagenespreguntas(String[] imagenes) {
        img = new Image(getClass().getResourceAsStream(imagenes[0]));
        img2 = new Image(getClass().getResourceAsStream(imagenes[1]));
        img3 = new Image(getClass().getResourceAsStream(imagenes[2]));
        img4 = new Image(getClass().getResourceAsStream(imagenes[3]));
        img5 = new Image(getClass().getResourceAsStream(imagenes[4]));
        auxPregunta5 = imagenes[4];
        img_respuesta1.setImage(img);
        img_respuesta2.setImage(img2);
        img_respuesta3.setImage(img3);
        img_respuesta4.setImage(img4);
        img_respuesta5.setImage(img5);
    }

    /**
     * Método encargado de obtener las imágenes de todas las preguntas.
     *
     * @throws SQLException
     */
    private void cargarImagenesRespuestas() throws SQLException {
        Image[] respuestas = items.cargarImagenesRespuestas();
        colocarImagenesRespuestas(respuestas);
    }

    /**
     * Método que muestra las imagénes de las 6 preguntas pasadas por parámetro.
     *
     * @param lista Lista con las 6 imagénes de las preguntas.
     */
    private void colocarImagenesRespuestas(Image[] lista) {
        res_pregunta1.setImage(lista[0]);
        res_pregunta2.setImage(lista[1]);
        res_pregunta3.setImage(lista[2]);
        res_pregunta4.setImage(lista[3]);
        res_pregunta5.setImage(lista[4]);
        res_pregunta6.setImage(lista[5]);
    }

    /**
     * Método que aplica una transición que muestra las imágenes del panel
     * lentamente.
     *
     * @param node
     */
    private void transicionRespuestas(Node node) {
        FadeTransition fade = new FadeTransition(javafx.util.Duration.seconds(3), node);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.setCycleCount(1);
        fade.play();
    }

    /**
     * Método que establece la opacidad inicial de las imágenes.
     */
    private void opacidadImagenes() {
        img_respuesta1.setOpacity(OPACIDAD_INICIAL);
        img_respuesta2.setOpacity(OPACIDAD_INICIAL);
        img_respuesta3.setOpacity(OPACIDAD_INICIAL);
        img_respuesta4.setOpacity(OPACIDAD_INICIAL);
        img_respuesta5.setOpacity(OPACIDAD_INICIAL);
    }

    /**
     * Método que establece la opacidad total al seleccionar la primera imagen y
     * cambiar la opacidad de las otras 3 imágenes.
     */
    private void opacidadImagenSeleccionada1() {
        img_respuesta1.setOpacity(OPACIDAD_SELECCIONADO);
        img_respuesta2.setOpacity(OPACIDAD_NO_SELECCIONADO);
        img_respuesta3.setOpacity(OPACIDAD_NO_SELECCIONADO);
        img_respuesta4.setOpacity(OPACIDAD_NO_SELECCIONADO);
        img_respuesta5.setOpacity(OPACIDAD_NO_SELECCIONADO);
    }

    /**
     * Método que establece la opacidad total al seleccionar la segunda imagen y
     * cambiar la opacidad de las otras 3 imágenes.
     */
    private void opacidadImagenSeleccionada2() {
        img_respuesta1.setOpacity(OPACIDAD_NO_SELECCIONADO);
        img_respuesta2.setOpacity(OPACIDAD_SELECCIONADO);
        img_respuesta3.setOpacity(OPACIDAD_NO_SELECCIONADO);
        img_respuesta4.setOpacity(OPACIDAD_NO_SELECCIONADO);
        img_respuesta5.setOpacity(OPACIDAD_NO_SELECCIONADO);
    }

    /**
     * Método que establece la opacidad total al seleccionar la tercera imagen y
     * cambiar la opacidad de las otras 3 imágenes.
     */
    private void opacidadImagenSeleccionada3() {
        img_respuesta1.setOpacity(OPACIDAD_NO_SELECCIONADO);
        img_respuesta2.setOpacity(OPACIDAD_NO_SELECCIONADO);
        img_respuesta3.setOpacity(OPACIDAD_SELECCIONADO);
        img_respuesta4.setOpacity(OPACIDAD_NO_SELECCIONADO);
        img_respuesta5.setOpacity(OPACIDAD_NO_SELECCIONADO);
    }

    /**
     * Método que establece la opacidad total al seleccionar la cuarta imagen y
     * cambiar la opacidad de las otras 3 imágenes.
     */
    private void opacidadImagenSeleccionada4() {
        img_respuesta1.setOpacity(OPACIDAD_NO_SELECCIONADO);
        img_respuesta2.setOpacity(OPACIDAD_NO_SELECCIONADO);
        img_respuesta3.setOpacity(OPACIDAD_NO_SELECCIONADO);
        img_respuesta4.setOpacity(OPACIDAD_SELECCIONADO);
        img_respuesta5.setOpacity(OPACIDAD_NO_SELECCIONADO);
    }

    /**
     * Método auxiliar que establece la opacidad total al seleccionar la tercera
     * imagen y cambiar la opcaidad de las otras 2 imágenes
     */
    private void opacidadImagenSeleccionada5() {
        img_respuesta1.setOpacity(OPACIDAD_NO_SELECCIONADO);
        img_respuesta2.setOpacity(OPACIDAD_NO_SELECCIONADO);
        img_respuesta3.setOpacity(OPACIDAD_NO_SELECCIONADO);
        img_respuesta4.setOpacity(OPACIDAD_NO_SELECCIONADO);
        img_respuesta5.setOpacity(OPACIDAD_SELECCIONADO);
    }

    /**
     * Método encargado de recibir los objetos del menú principal y con ellos
     * cargar todos los datos necesarios, imágenes, modificación labels, etc.
     *
     * @param base Contiene la conexión a la base de datos.
     * @param user Usuario actual
     * @param pacient Paciente seleccionado
     * @throws SQLException
     */
    void moverDatosRealizarTest(Connection base, Usuario user, Paciente pacient) throws SQLException {
        baseDeDatos = base;
        usuario = user;
        paciente = pacient;
        items = new ItemsDAO(base);
        gesImagenes = new GestionImagenes();
        cargarDatosIniciales();
        try {
            cargarImagenesRespuestas();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        listaDeImagenesBaseDeDatos();
    }

    /**
     * Método encargado de guardar el resultado en la base de datos y comprobar
     * si tuvo exito, luego abre la ventana Resultados con información de
     * interés.
     *
     * @throws IOException
     */
    private void guardarResultadoEnBDA() throws IOException {
        int resultado = gTest.insertResultados(idPaciente, listaPuntuacionesPorPregunta, riesgo);
        if (resultado == 1) {
            NotificationsAndAlerts.generaAlert("INFORMATION", "SCALA BRADEN", "Test finalizado con exito", "");
            App.setRoot("menuPrincipal");
            MenuPrincipal menuPrinci = App.getFxmlLoader().getController();
            menuPrinci.moverDatosMenuPrincipal(baseDeDatos, usuario, paciente);
            ventanaRecomendaciones();
        } else {
            NotificationsAndAlerts.generaAlert("ERROR", "SCALA BRADEN", "No se ha podido agregar el test correctamente", "");
        }
    }

    /**
     * Método que pregunta al usuario si esta seguro de volver al menú
     * principal, si acepta lo redirige a dicho menú.
     *
     * @param event Acción del usuario sobre el elemento
     * @throws IOException
     */
    @FXML
    private void menuPrincipal(MouseEvent event) throws IOException {
        boolean respuestaVentana = NotificationsAndAlerts.generaAlert("CONFIRMATION", "SCALA BRADEN", "¿ESTAS SEGURO DE VOLVER AL MENÚ SIN TERMINAR EL TEST?", "");
        if (respuestaVentana) {
            App.setRoot("menuPrincipal");
            MenuPrincipal menuPrincipal = App.getFxmlLoader().getController();
            menuPrincipal.moverDatosMenuPrincipal(baseDeDatos, usuario, paciente);
        }
    }

    /**
     * Método encargado de abrir una ventana aparte con las recomendaciones
     * según el resultado, no deja acceder a la ventana original hasta cerrar
     * esta.
     *
     * @throws IOException
     */
    private void ventanaRecomendaciones() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("recomendaciones.fxml"));
        Parent root = (Parent) loader.load();
        Recomendaciones recomendacion = loader.getController();
        Stage stage = new Stage();
        Scene nuevo = new Scene(root);
        stage.setScene(nuevo);
        stage.setTitle("Recomendación Resultados");
        stage.initOwner(App.getPrincipal());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
        recomendacion.recogerDatosResultados(paciente, listaPuntuacionesPorPregunta, tipoRiesgo);
    }
}
