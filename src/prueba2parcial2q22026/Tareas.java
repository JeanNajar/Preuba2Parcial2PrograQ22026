package prueba2parcial2q22026;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Tareas {
    
    private static final String VFILE = "tareas.txt";

    public static boolean inicializar() {
        try {
            File archivo = new File(VFILE);
            if (!archivo.exists()) {
                archivo.createNewFile();
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error al iniciar el archivo: " + e.getMessage());
            return false;
        }
    }

    private static List<String> leerTarea() {
        List<String> tarea = new ArrayList<>();
        File archivo = new File(VFILE);

        if (!archivo.exists() || archivo.length() == 0) {
            return tarea;
        }

        try {
            FileReader lector = new FileReader(archivo);
             String lineaActual = "";
            int c;

            while ((c = lector.read()) != -1) {
                char caracter = (char) c;

                if (caracter == '\n') {
                    String contenido = lineaActual.trim();
                    if (!contenido.isEmpty()) {
                        tarea.add(contenido);
                    }
                     lineaActual = "";
                     
                } else if (caracter != '\r') { 
                    
                   lineaActual = lineaActual + caracter;
                }
            }

            String ultima = lineaActual.trim();
            if (!ultima.isEmpty()) {
                tarea.add(ultima);
            }

            lector.close();

        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }

        return tarea;
    }

    
    private static void escribirTareas(List<String> tareas) {
        try {
            FileWriter escritor = new FileWriter(VFILE, false); 

            for (String tarea : tareas) {
                escritor.write(tarea + "\n");
            }

            escritor.close();

        } catch (IOException e) {
            System.out.println("Error al guardar las tareas: " + e.getMessage());
        }
    }

    public static void agregarTarea(Scanner scanner) {
        
        System.out.print("Ingresar la nueva tarea: ");
        String descripcion = scanner.nextLine().trim();

        if (descripcion.isEmpty()) {
            System.out.println("\nLa tarea no puede estar vacia.");
            return;
        }

        try {
            FileWriter escritor = new FileWriter(VFILE, true);
            escritor.write("PENDIENTE|" + descripcion + "\n");
            escritor.close();

            System.out.println("\n\u2713 Tarea agregada: " + descripcion);

        } catch (IOException e) {
            System.out.println("\nError al guardar la tarea: " + e.getMessage());
        }
    }

    public static void mostrarTareas() {
        List<String> tareas = leerTarea();

        System.out.println();
        System.out.println("LISTA DE TAREAS");
        System.out.println("");

        if (tareas.isEmpty()) {
            System.out.println("No hay tareas registradas");
            return;
        }

        for (int i = 0; i < tareas.size(); i++) {
            String[] partes = tareas.get(i).split("\\|", 2);

            if (partes.length < 2) continue;

            boolean completada = partes[0].equals("COMPLETADA");
            String descripcion = partes[1];
            String marcador    = completada ? "[\u2713]" : "[ ]";

            System.out.println((i + 1) + ". " + marcador + " " + descripcion);
        }
    }

    public static void completarTarea(Scanner scanner) {
        List<String> tareas = leerTarea();

        if (tareas.isEmpty()) {
            System.out.println("\nNo hay tareas para completar.");
            return;
        }

        System.out.print("Numero de tarea a completar: ");

        int numero;
        try {
            numero = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("\nIngresa un numero valido.");
            return;
        }

        if (numero < 1 || numero > tareas.size()) {
            System.out.println("\nNumero invalido. Debe estar entre 1 y " + tareas.size() + ".");
            return;
        }

        String[] partes = tareas.get(numero - 1).split("\\|", 2);

        if (partes.length < 2) {
            System.out.println("\nError al procesar la tarea.");
            return;
        }

        if (partes[0].equals("COMPLETADA")) {
            System.out.println("\nEsa tarea ya esta completada.");
            return;
        }

        String descripcion = partes[1];
        tareas.set(numero - 1, "COMPLETADA|" + descripcion);
        escribirTareas(tareas);

        System.out.println("\n\u2713 Tarea #" + numero + " completada: " + descripcion);
    }
}