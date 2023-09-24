package modelo;

/**
 * Braden_Proyect (DAW-CAE)
 *
 * @author Esteban Juan Antonio Olga Santi
 * @version 1.0
 */
/**
 * Clase relacionada con la tabla usuario mysql.
 */
public class Usuario {

    /**
     * Atributo id, representa el identificador del usuario.
     */
    private int id;

    /**
     * Atributo nombre, representa el nombre del usuario.
     */
    private String nombre;

    /**
     * Atributo apellido1, representa el primer apellido del usuario.
     */
    private String apellido1;

    /**
     * Atributo apellido2, representa el segundo apellido del usuario.
     */
    private String apellido2;

    /**
     * Atributo correo, representa el correo que tiene asociado el contraseña.
     */
    private String correo;

    /**
     * Atributo contrasenya, representa la contrasenya que tiene el usuario de
     * forma encriptada algoritmo SHA-256.
     */
    private String contrasenya;

    /**
     *
     * Constructor de la clase Usuario.
     *
     * @param id
     * @param nombre
     * @param apellido1
     * @param apellido2
     * @param correo
     * @param contrasenya
     *
     */
    public Usuario(int id, String nombre, String apellido1, String apellido2, String correo, String contrasenya) {
        this.id = id;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.correo = correo;
        this.contrasenya = contrasenya;
    }

    //GETTERS AND SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    /**
     * Redefinición del método toString() para devolver un formato en especifico
     * utilizado posteriormente para introducir en una colección.
     *
     * @return String con formato ID - NOMBRE - CORREO
     */
    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", nombre=" + nombre + ", correo=" + correo + '}';
    }

}
