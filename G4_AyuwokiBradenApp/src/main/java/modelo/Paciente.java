package modelo;

import java.time.LocalDate;

/**
 * Braden_Proyect (DAW-CAE)
 *
 * @author Esteban Juan Antonio Olga Santi
 * @version 1.0
 */
/**
 * Clase relacionada con la tabla paciente mysql.
 */
public class Paciente {

    /**
     * Atributo edad, representa la edad del paciente.
     */
    private int edad;

    /**
     * Atributo id, representa el identificador del paciente.
     */
    private int id;

    /**
     * Atributo nombre, representa el nombre del paciente.
     */
    private String nombre;

    /**
     * Atributo id, representa la fecha en la que fue creada el paciente.
     */
    private LocalDate fecha;

    /**
     * Constructor de la clase Paciente
     *
     * @param id
     * @param nombre
     * @param fecha
     */
    public Paciente(int id, String nombre, LocalDate fecha) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
    }

    //GETTERS AND SETTERS
    public int getEdad() {
        return edad;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    /**
     * Redefinición del método toString() para devolver un formato en especifico
     * utilizado posteriormente para representar en un ListView.
     *
     * @return String, con el formato ID - NOMBRE - EDAD.
     */
    @Override
    public String toString() {
        return "ID Paciente: " + id + "     Nombre: " + nombre + "    Edad: " + (LocalDate.now().getYear() - fecha.getYear()) + " años";
    }
}
