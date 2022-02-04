package practica1;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Contras {

    public static Random rand = new Random();

    // Cada uno de estos strings corresponde a la tabla de caracteres
    // validos para la generacion de contraseñas
    public static String tabla = "abcdefghijklmnopqrstuvwxyz0123456789";
    public static String tablaAlt = "ABCDEFGHIJKLMNOPQRSTUVWXYZ=!@#$%&/()";

    public static String contraDados() {
        String contra = "";
        int longitud = rand.nextInt(5) + 4;

        for (int i = 0; i < longitud; i++) {
            // Genera la tirada de los dos dados (D6) y de la moneda
            int dado1 = rand.nextInt(6) + 1;
            int dado2 = rand.nextInt(6) + 1;
            boolean moneda = rand.nextBoolean(); // Cruz = false, cara = true

            // Esta formula permite traducir la fila y columna a un numero que corresponde
            // con la posicion en el string anterior:
            int pos = (6 * (dado1 - 1)) + dado2;

            // Dependiendo de si la moneda es cara o cruz entrara a una tabla u otra
            // para generar el caracter
            Character caracter = moneda ? tabla.charAt(pos - 1) : tablaAlt.charAt(pos - 1);

            // Añade el caracter a la contraseña final
            contra += caracter;
        }
        return contra;
    }

    public static String contraAlgoritmo() {
        SecureRandom sRand = new SecureRandom();

        String contra = "";
        int longitud = rand.nextInt(5) + 4;

        for (int i = 0; i < longitud; i++) {
            // Genera la posicion dentro de la tabla de la que tiene que coger el caracter
            int caracter = sRand.nextInt(36);

            // Dependiendo de si la moneda es cara o cruz entrara a una tabla u otra
            // para generar el caracter
            int moneda = sRand.nextInt(2);
            contra += (moneda == 0) ? tabla.charAt(caracter) : tablaAlt.charAt(caracter);
        }
        return contra;
    }

    public static String contraIdea(String nombre, String apellido1, String dni, LocalDate fechaNacimiento) {
        int longitud = rand.nextInt(5) + 4;

        String contra = "";

        contra += nombre.toUpperCase().charAt(0); // Primera letra del nombre en mayus
        contra += apellido1.toLowerCase().charAt(apellido1.length() - 1); // Ultima letra del 1er apell. en minus
        contra += dni.substring(6, 8); // Las dos ultimas cifras del DNI

        // El resto del DNI equivaldra a la posicion en esta tabla, donde se guardan las letras
        String digitosDNI = "TRWAGMYFPDXBNJZSQVHLCKE";
        contra += digitosDNI.charAt((int) (Long.parseLong(dni) % 23));

        // Se formatea la fecha de nacimiento para que el año este al final y se cogen los dos
        // ultimos digitos
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("d/M/yyyy");
        String fechaString = formateador.format(fechaNacimiento);
        contra += fechaString.substring(fechaString.length() - 2, fechaString.length());

        // Se coge un signo al azar entre estos:
        String simbolos = "!@#$%&/()=";
        contra += simbolos.charAt(rand.nextInt(10));

        // Se hace un substring para que la contraseña solo llegue hasta la longitud deseada
        return contra.substring(0, longitud);
    }

    public static String seguridad(String contra) {
        // \u001B[XXm indica el color que tendra el texto. Es solo visual
        // Por defecto la contraseña sera poco segura, y en el resto de casos se reemplazaran
        String nivelSeguridad = "\u001B[31m" + "Poco segura" + "\u001B[0m";

        // Si la contraseña tiene mas de 6 caracteres sera como minimo segura
        if (contra.length() > 6) {
            nivelSeguridad = "\u001B[33m" + "Segura" + "\u001B[0m";

            // Ahora comprobamos si la contraseña es muy segura.
            Boolean tieneMayus = false;
            Boolean tieneNums = false;
            Boolean tieneSimbolos = false;

            // Se comprueba cada caracter de la cadena para ver de que tipo son
            for (int i = 0; i < contra.length(); i++) {
                char caracter = contra.charAt(i);

                if (!Character.isLetter(caracter)) {
                    // Si no es letra se comprueba si es digito
                    if (Character.isDigit(caracter))
                        tieneNums = true;
                    else
                        // Si el no es una letra ni un numero, es que es un simbolo
                        tieneSimbolos = true;

                    // Si es letra, se comprueba si es mayuscula.
                } else if (Character.isUpperCase(caracter)) {
                    tieneMayus = true;
                }

            }

            // Si se cumplen los tres requisitos, la contraseña sera muy segura
            if (tieneMayus && tieneNums && tieneSimbolos)
                nivelSeguridad = "\u001B[32m" + "Muy segura" + "\u001B[0m";
        }

        return nivelSeguridad;
    }

}
