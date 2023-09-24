package com.braden.libraries;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;

/**
 * Braden_Proyect (DAW-CAE)
 *
 * @author Esteban Juan Antonio Olga Santi
 * @version 2.1
 */
/**
 * Clase que se encarga de encriptar las contraseñas
 */
public class Encrypter {

    /**
     * Atributo final ALGORITMO, representa el algoritmo que se utlizará para
     * codificar las contraseñas
     */
    private final String ALGORITMO = "SHA-256";

    /**
     * Metodo que encripta la contraseña pasada como parametro
     *
     * @param pass
     * @return String, la contraseña encriptada
     */
    public String encriptar(String pass) {
        String encrypt;
        ConfigurablePasswordEncryptor codi = new ConfigurablePasswordEncryptor();
        codi.setAlgorithm(ALGORITMO);
        codi.setPlainDigest(true);
        encrypt = codi.encryptPassword(pass);
        return encrypt;
    }

}
