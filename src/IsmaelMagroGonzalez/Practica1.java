package practica1;

import java.time.LocalDate;
import java.util.Random;

public class Practica1 {

    public static Random rand = new Random();

    public static void main(String[] args) {

        // Peticion al usuario de todos los datos
        // Para el nombre y los apellidos se utilizara el mismo metodo, pero se
        // estableceran distintos minimos de caracteres
        System.out.print("Nombre: ");
        String nombre = EntradaDatos.nombre(3);
        System.out.print("Primer apellido: ");
        String apell1 = EntradaDatos.nombre(2);
        System.out.print("Segundo apellido: ");
        String apell2 = EntradaDatos.nombre(2);
        String dni = EntradaDatos.dni();
        LocalDate fechaNacimiento = EntradaDatos.fechaNacimiento();
        
               
        System.out.println("\n<-------------------------->");
        System.out.println("   Tu usuario es " + generarUsuario(nombre, apell1, apell2) + ".");
        System.out.println(  "<-------------------------->");
        
        
        // Generacion de las contraseñas e impresion en pantalla
        System.out.println("Contraseñas: ");
        
        String contraDados = Contras.contraDados();
        System.out.println(contraDados + " -> " + Contras.seguridad(contraDados));
        
        String contraAlgoritmo = Contras.contraAlgoritmo();
        System.out.println(contraAlgoritmo + " -> " + Contras.seguridad(contraAlgoritmo));
        
        String contraAIdea = Contras.contraIdea(nombre, apell1, dni, fechaNacimiento);
        System.out.println(contraAIdea + " -> " + Contras.seguridad(contraAIdea));
    }

    public static String generarUsuario(String nombre, String apell1, String apell2) {
        String usuario = apell2.substring(0, 2) + apell1.substring(0, 2) + nombre.substring(0, 3);
        return usuario.toLowerCase();
    }

}
