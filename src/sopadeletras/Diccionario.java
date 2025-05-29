package sopadeletras;
 import java.util.HashSet;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Dell
 */
public class Diccionario {
   
    private HashSet<String> palabras; // Usamos un HashSet para poder evitar duplicados

    public Diccionario() {
        palabras = new HashSet<>();
    }

    public void agregarPalabra(String palabra) {
        palabras.add(palabra);
    }

    public boolean contienePalabra(String palabra) {
        return palabras.contains(palabra);
    }

    public void mostrarDiccionario() {
        for (String palabra : palabras) {
            System.out.println(palabra);
        }
    }
}

