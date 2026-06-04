
package prueba2parcial2q22026;

import java.util.Scanner;

public class GestorTareas {

    public static void main(String[] args) {
        
           try {
        System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
         } catch (Exception e) { }
        
        Scanner scanner = new Scanner(System.in);
        
        if (!Tareas.inicializar()) {
            return;
        }
        
        int opcion = 0;
        do {
            Menu();
            System.out.print("Selecciona una opcion: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                opcion = 0;
            }

            switch (opcion) {
                case 1:
                    Tareas.agregarTarea(scanner);
                    break;
                case 2:
                    Tareas.mostrarTareas();
                    break;
                case 3:
                    Tareas.completarTarea(scanner);
                    break;
                case 4:
                    System.out.println("\nSe salio Correctamente");
                    break;
                default:
                    System.out.println("\nla opcion no valida. Intenta de nuevo.");
            }

            System.out.println();

        } while (opcion != 4);

        scanner.close();
    }


    private static void Menu() {
        System.out.println("GESTOR DE TAREAS");
        
        System.out.println("");
        System.out.println("1. Agregar tarea");
        System.out.println("2. Mostrar tareas");
        System.out.println("3. Completar tarea");
        System.out.println("4. Salir");
    }
}