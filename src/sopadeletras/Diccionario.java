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

/**
 * Clase que representa un diccionario de palabras.
 * Evita palabras duplicadas.
 */

public class Diccionario {
   
    private HashSet<String> palabras; // Usamos un HashSet para poder evitar duplicados

    
    public Diccionario() {
        palabras = new HashSet<>();
    }
    
    /**
     * Agrega una palabra al diccionario (en mayúsculas).
     * @param palabra Palabra a agregar.
     * @return true si la palabra fue agregada, false si ya existía.
     */

    public boolean agregarPalabra(String palabra) {
    return palabras.add(palabra.toUpperCase());
    }
    
    //Verifica si una palabra esta en el diccionario
    public boolean contienePalabra(String palabra) {
        return palabras.contains(palabra);
    }
    
    //Muestra todas las palabras del diccionario por consola.
    public void mostrarDiccionario() {
        for (String palabra : palabras) {
            System.out.println(palabra);
        }
    }
    public Set<String> getPalabras() {
    return palabras;
}
}

