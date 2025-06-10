package sopadeletras;
 import java.util.HashSet;
import java.util.Set;

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

    public boolean agregarPalabra(String palabra) {
    return palabras.add(palabra.toLowerCase());
    }

    public boolean contienePalabra(String palabra) {
        return palabras.contains(palabra);
    }

    public void mostrarDiccionario() {
        for (String palabra : palabras) {
            System.out.println(palabra);
        }
    }
    public Set<String> getPalabras() {
    return palabras;
}
}

