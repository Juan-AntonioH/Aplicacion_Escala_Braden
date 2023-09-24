package modelo;

/**
 * Braden_Proyect (DAW-CAE)
 *
 * @author Esteban Juan Antonio Olga Santi
 * @version 1.0
 */
/**
 *
 * Clase relacionada con la tabla log mysql
 *
 */
public class Log {

    /**
     * Atributo puntosTotales representa los puntos totales que obtuvo un
     * paciente en un test.
     */
    private int puntosTotales;

    /**
     * Atributo idPaciente representa el identificador que tiene asociado el
     * paciente para realizar los tests.
     */
    private int idPaciente;

    /**
     * Atributo numTests representa el numero de veces que el paciente ha sacado
     * x puntosTotales. Se actualiza mediante trigger en sql
     */
    private int numTests;

    //GETTERS AND SETTERS
    public int getPuntosTotales() {
        return puntosTotales;
    }

    public void setPuntosTotales(int puntosTotales) {
        this.puntosTotales = puntosTotales;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public int getNumTests() {
        return numTests;
    }

    public void setNumTests(int numTests) {
        this.numTests = numTests;
    }

}
