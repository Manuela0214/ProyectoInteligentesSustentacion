package matrizbotones;

import gestorArchivos.Archivo;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javax.swing.JFileChooser;

/**
 *
 * @author Manuela Arcila
 * @author Angie Camelo
 * 
 * Proyecto 1. Sistemas Inteligentes
 */
public class Main {

    public static void main(String[] args) {

        Archivo archivo = new Archivo();
        JFileChooser fc = new JFileChooser();
        int seleccion = fc.showOpenDialog(null);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File fichero = fc.getSelectedFile();
            String matriz[][] = archivo.leer(fichero.toString());

            System.out.println("Matriz leida:");
            for (int i = 0; i < matriz.length; i++) {
                for (int j = 0; j < matriz[i].length; j++) {
                    System.out.print(matriz[i][j] + " ");
                }
                System.out.println("");

            }
            Matriz laberinto = new Matriz(matriz);

            Scanner scanner = new Scanner(System.in);
            System.out.println("");
            System.out.print("Ingresa la fila del nodo de inicio: ");
            int xInicio = scanner.nextInt();

            System.out.print("Ingresa la columna del nodo de inicio: ");
            int yInicio = scanner.nextInt();

            System.out.print("Ingresa la fila del nodo objetivo: ");
            int xObjetivo = scanner.nextInt();

            System.out.print("Ingresa la columna del nodo objetivo: ");
            int yObjetivo = scanner.nextInt();

            scanner.nextLine();
            System.out.println("");
            System.out.println("Ingresa la letra del tipo de heuristica a utilizar:");
            System.out.println("1. Heurística de Manhattan (M)");
            System.out.println("2. Heurística Euclidiana (E)");
            String heuristica = scanner.nextLine();

            Nodo nodoInicial = new Nodo(xInicio, yInicio);
            Nodo nodoObjetivo = new Nodo(xObjetivo, yObjetivo);
            String tipoHeuristica = heuristica;

            laberinto.crearHashMap(nodoObjetivo, tipoHeuristica);
            System.out.println("");
            System.out.println("Listado de nodos:");
            laberinto.imprimirNodos();
            System.out.println("");

            System.out.println("Elige una opción:");
            System.out.println("Busquedas no informadas: ");
            System.out.println("1. Recorrido de anchura");
            System.out.println("2. Recorrido de profundidad");
            System.out.println("3. Recorrido costo uniforme");
            System.out.println("");
            System.out.println("Busquedas informadas: ");
            System.out.println("4. Recorrido Hill Climbing");
            System.out.println("5. Recorrido Beam Search");
            System.out.println("6. Recorrido A*");
            System.out.println("7. Nivel objetivo en recorrido anchura");
            int opcion = scanner.nextInt();
            BusquedaNOInformada BNI = new BusquedaNOInformada(laberinto.getNodos(nodoObjetivo, tipoHeuristica), matriz);
            BusquedaInformada BI = new BusquedaInformada(laberinto.getNodos(nodoObjetivo, tipoHeuristica), matriz);
            
            switch (opcion) {
                case 1:
                    System.out.println("");
                    List<Nodo> recorridoAnchura = BNI.recorridoEnAnchura(nodoInicial, nodoObjetivo);                    
                    System.out.println("Recorrido en anchura");
                    System.out.print("[");
                    for (Nodo nodoAnch : recorridoAnchura) {
                        System.out.print("(" + nodoAnch.getX() + "," + nodoAnch.getY() + "),");
                    }
                    System.out.print("]");
                    break;
                case 2:
                    System.out.println("");
                    System.out.println("Recorrido en profundidad:");
                    List<Nodo> recorridoProfundidad = BNI.recorridoEnProfundidad(nodoInicial,nodoObjetivo);
                    System.out.print("[");
                    for (Nodo nodoProf : recorridoProfundidad) {
                        System.out.print("(" + nodoProf.getX() + "," + nodoProf.getY() + "),");
                    }
                    System.out.print("]");
                    break;

                case 3:
                    System.out.println("");
                    System.out.println("Recorrido Costo Uniforme:");
                    List<Nodo> recorridoCostoU = BNI.recorridoCostoUniforme(nodoInicial, nodoObjetivo, tipoHeuristica);
                    System.out.println("[");
                    for (Nodo nodoCostoU : recorridoCostoU) {
                        System.out.print("(" + nodoCostoU.getX() + "," + nodoCostoU.getY() + "),");
                    }
                    System.out.println("]");
                    break;
                    
                case 4:
                    System.out.println("");
                    System.out.println("Recorrido Hill Climbing:");
                    List<Nodo> recorridoHill = BI.recorridoHillClimbing(nodoInicial, nodoObjetivo, tipoHeuristica);
                    System.out.println("[");
                    for (Nodo nodoHill : recorridoHill) {
                        System.out.print("(" + nodoHill.getX() + "," + nodoHill.getY() + "),");
                    }
                    System.out.println("]");
                    break;

                case 5:
                    System.out.println("");
                    List<Nodo> recorridoBeamS = BI.recorridoBeamSearch(nodoInicial, nodoObjetivo, tipoHeuristica);
                    System.out.println("Recorrido Beam Search:");
                    System.out.println("[");
                    for (Nodo nodoBeam : recorridoBeamS) {
                        System.out.print("(" + nodoBeam.getX() + "," + nodoBeam.getY() + "),");
                    }
                    System.out.println("]");
                    break;
                case 6:
                    System.out.println("");
                    List<Nodo> recorridoAE = BI.recorridoAEstrella(nodoInicial, nodoObjetivo,tipoHeuristica);
                    System.out.println("Recorrido A*");
                    System.out.println("[");
                    for (Nodo nodoAE : recorridoAE) {
                        System.out.print("(" + nodoAE.getX() + "," + nodoAE.getY() + "),");
                    }
                    System.out.println("]");
                    break;
                    
                case 7:
                    System.out.println("");
                    int nivel = BNI.calcularNivel(nodoInicial, nodoObjetivo);
                    System.out.println("El nivel del Objetivo es " + nivel);
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }

            scanner.close();
        }
    }

}
