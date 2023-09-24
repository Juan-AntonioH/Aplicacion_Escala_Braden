package GestionModelo;

/**
 * Braden_Proyect (DAW-CAE)
 *
 * @author Esteban Juan Antonio Olga Santi
 * @version 1.0
 */
/**
 * Clase que gestiona el modelo Riesgo
 */
public class RiesgoDAO {

    /**
     * Riesgo según edad.
     */
    private final int ALTO = 12;
    private final int MODERADO = 14;
    private final int BAJOJOVEN = 16;
    private final int EDADBAJO = 75;

    /**
     * Metodo que calcula el riesgo segun suma puntos totales y la edad de la
     * paciente.
     *
     * @param resultado
     * @param edad
     * @return Integer representando el riesgo 1|2|3
     *
     */
    public int calcularRiesgo(int resultado, int edad) {
        int riesgo = 4;
        if (resultado < ALTO) {
            riesgo = 3;
        } else if (resultado <= MODERADO) {
            riesgo = 2;
        } else if (resultado <= BAJOJOVEN && edad < EDADBAJO) {
            riesgo = 1;
        }
        return riesgo;
    }

    /**
     * Metodo que transforma el calculo del riesgo realizado en el metodo
     * anterior a String
     *
     * @param idRiesgo
     * @return String BAJO|MEDIO|ALTO
     */
    public String riesgoSegunID(int idRiesgo) {
        String riesgo = "";
        switch (idRiesgo) {
            case 1:
                riesgo = "BAJO";
                break;
            case 2:
                riesgo = "MODERADO";
                break;
            case 3:
                riesgo = "ALTO";
                break;
            case 4:
                riesgo = "SIN_RIESGO";
                break;
        }
        return riesgo;
    }

}
