package practica1;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class EntradaDatos {

    static Scanner sc = new Scanner(System.in);

    // Metodo que recoge el nombre y apellidos del usuario, con un minimo de caracteres especificados
    public static String nombre(int minCaracteres) {
        String nombre;

        boolean nombreCorrecto;
        do {
            // Se eliminan los guiones en caso de que algún apellido los tenga, que estos no den error
            nombre = normalizar(sc.nextLine()).replaceAll("-", "");

            nombreCorrecto = nombre.length() >= minCaracteres;

            if (nombreCorrecto) {
                // Se comprueba que el nombre solo contenga caracteres alfabeticos (para evitar simbolos o numeros)
                for (int i = 0; i < nombre.length(); i++) {
                    if (!Character.isAlphabetic(nombre.charAt(i)))
                        nombreCorrecto = false;
                }
            }

            if (!nombreCorrecto)
                System.out.print("------> Introduce un nombre valido de al menos " + minCaracteres + " caracteres: ");

        } while (!nombreCorrecto);

        return nombre;
    }

    public static String dni() {
        long dni = 0;
        String dniString;

        boolean dniValido;
        do {
            dniValido = true;
            System.out.print("DNI (sin letra): ");
            dniString = normalizar(sc.nextLine());

            try {
                // Solo se utilizaran los 8 primeros digitos introducidos. Si alguno de ellos
                // fuese una letra o simbolo, el DNI no es valido y vuelve a preguntar
                if (dniString.length() != 8) {
                    dniString = dniString.substring(0, 8);
                    System.out.println(" -> Se utilizaran unicamente los 8 "
                            + "primeros digitos introducidos (" + dniString + ")");
                }

                dni = Long.parseLong(dniString);
                // El DNI debe ser mayor que 0
                dniValido = dni > 0;
            } catch (Exception e) {
                dniValido = false;
            }

            if (!dniValido)
                System.out.println("------> Introduce un DNI valido.");
        } while (!dniValido); // Si da error el DNI valdra 0, y volvera a preguntar

        return dniString;
    }

    // Método que elimina todos los espacios y tabulaciones 
    public static String normalizar(String st) {
        return st.replaceAll(" ", "").replaceAll("\t", "");
    }

    public static LocalDate fechaNacimiento() {
        LocalDate ahora = LocalDate.now();
        LocalDate fechaMinima = LocalDate.ofYearDay(1901, 1);
        LocalDate dia = ahora;

        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("d/M/yyyy");

        do {
            System.out.print("Fecha de nacimiento (dd/mm/aaaa): ");
            String nacimientoString = sc.nextLine();

            try {
                dia = LocalDate.parse(nacimientoString, formateador);

                // En caso de que la fecha introducida sea la de hoy o posterior a hoy
                // no permite utilizarla y vuelve a preguntar
                if (dia.isAfter(ahora.minusDays(1)))
                    System.out.println("------> Introduce una fecha anterior a la de hoy.");
                if (dia.isBefore(fechaMinima))
                    System.out.println("------> Introduce una fecha superior al 1900.");
            } catch (Exception e) {
                System.out.println("------> Introduce una fecha valida.");
            }

        } while (dia.isAfter(ahora.minusDays(1)) || dia.isBefore(fechaMinima));

        return dia;
    }
}
