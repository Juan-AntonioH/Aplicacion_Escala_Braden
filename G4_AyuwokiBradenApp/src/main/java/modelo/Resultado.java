package modelo;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Braden_Proyect (DAW-CAE)
 *
 * @author Esteban Juan Antonio Olga Santi
 * @version 1.0
 */
/**
 * Clase relacionada con la tabla resultados mysql.
 */
public class Resultado {

    /**
     * Atributo fecha, representa fecha y hora de la realización del test
     */
    private LocalDateTime fecha;

    /**
     * Atributo idPaciente, representa la id del paciente al cual se le ha
     * realizado el test.
     */
    private int idPaciente;

    /**
     * Atributos puntos_itemX, representa el valor (maximo 4) de cada una de las
     * preguntas del test.
     */
    private int puntos_item1, puntos_item2, puntos_item3, puntos_item4, puntos_item5, puntos_item6;

    /**
     * Atributo sumaPuntos, representa la suma total de los 6 items presentes en
     * el test.
     */
    private int sumaPuntos;

    /**
     * Atributo riesgo, representa el riesgo obtenido a partir de la suma de
     * puntos. Solo puede ser (BAJO|MEDIO|ALTO).
     */
    private String riesgo;

    /**
     * Constructor de la clase Resultado
     *
     * @param fecha
     * @param paciente
     * @param puntosPreguntas
     * @param sumaPuntos
     * @param riesgo BAJO|MEDIO|ALTO
     */
    public Resultado(Timestamp fecha, int paciente, int[] puntosPreguntas, int sumaPuntos, String riesgo) {
        this.fecha = fecha.toLocalDateTime();
        this.idPaciente = paciente;
        puntos_item1 = puntosPreguntas[0];
        puntos_item2 = puntosPreguntas[1];
        puntos_item3 = puntosPreguntas[2];
        puntos_item4 = puntosPreguntas[3];
        puntos_item5 = puntosPreguntas[4];
        puntos_item6 = puntosPreguntas[5];
        this.sumaPuntos = sumaPuntos;
        this.riesgo = riesgo;
    }

    //GETTERS AND SETTERS
    public String getFecha() {
        return DateTimeFormatter.ofPattern("EEEE, dd 'de' MMMM 'del' yyyy h:mm").format(fecha);

    }

    public LocalDateTime getFechaSinFormatear() {
        return fecha;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public int getPuntos_item1() {
        return puntos_item1;
    }

    public int getPuntos_item2() {
        return puntos_item2;
    }

    public int getPuntos_item3() {
        return puntos_item3;
    }

    public int getPuntos_item4() {
        return puntos_item4;
    }

    public int getPuntos_item5() {
        return puntos_item5;
    }

    public int getPuntos_item6() {
        return puntos_item6;
    }

    public int getSumaPuntos() {
        return sumaPuntos;
    }

    public String getRiesgo() {
        return riesgo;
    }

    /**
     * Redifinición del metodo toString() para representar posteriormente en un
     * TableView y exportación de ficheros con una alineación adecuada.
     *
     * @return String con todos los items de un test.
     */
    @Override
    public String toString() {
        return String.format("%-40s%-15d%-20s%-15d%-15d%-15d%-16d%-15d%-15d", getFecha(), sumaPuntos, riesgo, puntos_item1, puntos_item2, puntos_item3, puntos_item4, puntos_item5, puntos_item6);
    }
}
