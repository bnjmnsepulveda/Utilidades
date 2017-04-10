/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formatos;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Roberto
 */
public class TextFromat {

    private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static String limpiarCaratereasEspeciales(String texto) {
        return texto.replaceAll("[^A-Z,a-z,0-9]", "");
    }

    public static boolean validarEmail(String email) {
        Pattern pattern = Pattern.compile(PATTERN_EMAIL);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
