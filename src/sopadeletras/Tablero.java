/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sopadeletras;

/**
 *
 * @author Dell
 */
public class Tablero {
    private char[][] letras; // Creamos una matriz 4x4 para el tablero

    public Tablero(char[][] letras) {
        this.letras = letras;
    }

    public char[][] getLetras() {
        return letras;
    }

    
    public void mostrarTablero() {
      for (char[] fila : letras) {
          for (char letra : fila) {
             System.out.print(letra + " ");
            }
             System.out.println();
        }
      }
  }

