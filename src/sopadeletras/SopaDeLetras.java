/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sopadeletras;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;

/**
 *
 * @author Dell
 */

/**
 * Clase principal que inicia la aplicación de Sopa de Letras.
 */
public class SopaDeLetras {

    /**
     * @param args the command line arguments
     */
    //Despliega la ventana inicial
    public static void main(String[] args) {
    java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            new InterfazInicio().setVisible(true);
        }
    });

}
    
    private static String cargarArchivo() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos de texto (*.txt)", "txt"));
    int resultado = fileChooser.showOpenDialog(null);
    if (resultado == JFileChooser.APPROVE_OPTION) {
        File archivo = fileChooser.getSelectedFile();
        if (!archivo.getName().toLowerCase().endsWith(".txt")) {
            JOptionPane.showMessageDialog(null, "Solo se permiten archivos con extensión .txt", "Archivo no válido", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return archivo.getAbsolutePath();
    }
    return null;
}
    
    // Procesa un archivo de entrada para cargar el diccionario y el tablero.
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
             System.out.println(tablero.tableroComoTexto());
            System.out.println("Diccionario:");
            diccionario.mostrarDiccionario();

        } catch (IOException e) {
          e.printStackTrace();
        }
     }
   }
