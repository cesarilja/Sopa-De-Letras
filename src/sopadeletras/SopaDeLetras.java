/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sopadeletras;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;

/**
 *
 * @author Dell
 */
public class SopaDeLetras {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String archivo = cargarArchivo();
        if (archivo != null) {
            procesarArchivo(archivo);
        }
    }

    private static String cargarArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        int resultado = fileChooser.showOpenDialog(null);
        if (resultado == JFileChooser.APPROVE_OPTION) {
          return fileChooser.getSelectedFile().getAbsolutePath();
        }
        return null;
    }

    private static void procesarArchivo(String archivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            Diccionario diccionario = new Diccionario();
            char[][] letras = new char[4][4];
            int fila = 0;

            while ((linea = br.readLine()) != null) {
                if (linea.startsWith("dic")) {
                while (!(linea = br.readLine()).equals("/dic")) {
                 diccionario.agregarPalabra(linea.trim());
                    }
                }else if (linea.startsWith("tab")) {
                  linea = br.readLine();
                  String[] partes = linea.split(",");
                  for (int i = 0; i < partes.length; i++) {
                   letras[fila][i] = partes[i].charAt(0);
                    }
                    fila++;
                    }
                }

            Tablero tablero = new Tablero(letras);
             tablero.mostrarTablero();
            System.out.println("Diccionario:");
            diccionario.mostrarDiccionario();

        } catch (IOException e) {
          e.printStackTrace();
        }
     }
   }
