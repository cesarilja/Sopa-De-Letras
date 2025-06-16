/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sopadeletras;
import java.util.*;
/**
 *
 * @author Dell
 */
public class BuscadorPalabras {
    private final char[][] tablero;
    private final int filas;
    private final int columnas;
    private final Diccionario diccionario;

    public BuscadorPalabras(char[][] tablero, Diccionario diccionario) {
        this.tablero = tablero;
        this.filas = tablero.length;
        this.columnas = tablero[0].length;
        this.diccionario = diccionario;
    }

    // Verifica si una palabra está en el tablero (DFS)
    public boolean buscarPalabraDFS(String palabra) {
        palabra = palabra.toUpperCase();
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (tablero[i][j] == palabra.charAt(0)) {
                    boolean[][] visitado = new boolean[filas][columnas];
                    if (dfs(i, j, palabra, 0, visitado)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // DFS recursivo para buscar palabra
    private boolean dfs(int x, int y, String palabra, int pos, boolean[][] visitado) {
        if (pos == palabra.length()) return true;
        if (x < 0 || x >= filas || y < 0 || y >= columnas) return false;
        if (visitado[x][y]) return false;
        if (tablero[x][y] != palabra.charAt(pos)) return false;

        visitado[x][y] = true;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx != 0 || dy != 0) {
                    if (dfs(x + dx, y + dy, palabra, pos + 1, visitado)) {
                        visitado[x][y] = false; // backtrack
                        return true;
                    }
                }
            }
        }
        visitado[x][y] = false; // backtrack
        return false;
    }

    // Busca todas las palabras del diccionario usando DFS
    public List<String> buscarTodasPalabrasDFS() {
        List<String> encontradas = new ArrayList<>();
        for (String palabra : diccionario.getPalabras()) {
            if (buscarPalabraDFS(palabra)) {
                encontradas.add(palabra);
            }
        }
        return encontradas;
    }

    // BFS para encontrar todas las palabras (retorna la lista y el "árbol" en texto)
    public Map<String, String> buscarTodasPalabrasBFS() {
        Map<String, String> resultados = new LinkedHashMap<>();
        for (String palabra : diccionario.getPalabras()) {
            String arbol = bfsArbol(palabra.toUpperCase());
            if (arbol != null) {
                resultados.put(palabra, arbol);
            }
        }
        return resultados;
    }

    // Busca una palabra y retorna su "árbol" de búsqueda (texto)
    public String buscarPalabraBFSArbol(String palabra) {
        return bfsArbol(palabra.toUpperCase());
    }

    // Implementación BFS para hallar el "árbol de búsqueda" de una palabra
    private String bfsArbol(String palabra) {
        Queue<Nodo> queue = new LinkedList<>();
        boolean[][] visitado = new boolean[filas][columnas];

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (tablero[i][j] == palabra.charAt(0)) {
                    queue.offer(new Nodo(i, j, 1, "" + tablero[i][j]));
                }
            }
        }

        while (!queue.isEmpty()) {
            Nodo nodo = queue.poll();
            if (nodo.longitud == palabra.length()) {
                return nodo.camino;
            }
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    int nx = nodo.x + dx;
                    int ny = nodo.y + dy;
                    if (dx == 0 && dy == 0) continue;
                    if (nx >= 0 && nx < filas && ny >= 0 && ny < columnas) {
                        if (tablero[nx][ny] == palabra.charAt(nodo.longitud)) {
                            queue.offer(new Nodo(nx, ny, nodo.longitud + 1, nodo.camino + "->" + tablero[nx][ny]));
                        }
                    }
                }
            }
        }
        return null;
    }

    // Clase Nodo para BFS
    private static class Nodo {
        int x, y, longitud;
        String camino;
        Nodo(int x, int y, int longitud, String camino) {
            this.x = x;
            this.y = y;
            this.longitud = longitud;
            this.camino = camino;
        }
    }
}
    

