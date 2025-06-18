/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sopadeletras;

/**
 *
 * @author Dell
 */
import java.awt.Point;
import java.util.*;
import java.util.HashSet;
import java.util.Set;

public class Tablero {
    private char[][] letras; // Matriz 4x4

    public Tablero(char[][] letras) {
        this.letras = letras;
    }

    public char[][] getLetras() {
        return letras;
    }

    // Mostrar el tablero como texto
    public String tableroComoTexto() {
        StringBuilder sb = new StringBuilder();
        for (char[] fila : letras) {
            for (char c : fila) {
                sb.append(c).append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    // --- DFS (para sí/no) ---
    public boolean buscarPalabraDFS(String palabra) {
        int n = letras.length, m = letras[0].length;
        boolean[][] visitado = new boolean[n][m];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                if (dfs(i, j, palabra, 0, visitado))
                    return true;
        return false;
    }

    // --- DFS (para camino/arbol) ---
    public String buscarCaminoDFS(String palabra) {
        int n = letras.length, m = letras[0].length;
        boolean[][] visitado = new boolean[n][m];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                if (buscarCaminoDFSRec(i, j, palabra, 0, visitado, "(" + i + "," + j + "):" + letras[i][j]))
                    return caminoEncontrado;
        return null;
    }
    private boolean dfs(int x, int y, String palabra, int idx, boolean[][] visitado) {
    int n = letras.length, m = letras[0].length;
    if (idx >= palabra.length()) return false;
    if (x < 0 || x >= n || y < 0 || y >= m) return false;
    if (visitado[x][y]) return false;
    if (letras[x][y] != palabra.charAt(idx)) return false;
    if (idx == palabra.length() - 1) return true;

    visitado[x][y] = true;
    int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
    int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};
    for (int d = 0; d < 8; d++) {
        if (dfs(x + dx[d], y + dy[d], palabra, idx + 1, visitado)) {
            visitado[x][y] = false; 
            return true;
        }
    }
    visitado[x][y] = false; 
    return false;
}

    private String caminoEncontrado = null;
    private boolean buscarCaminoDFSRec(int x, int y, String palabra, int idx, boolean[][] visitado, String camino) {
        int n = letras.length, m = letras[0].length;
        if (idx >= palabra.length()) return false;
        if (x < 0 || x >= n || y < 0 || y >= m) return false;
        if (visitado[x][y]) return false;
        if (letras[x][y] != palabra.charAt(idx)) return false;
        if (idx == palabra.length() - 1) {
            caminoEncontrado = camino;
            return true;
        }
        visitado[x][y] = true;
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};
        for (int d = 0; d < 8; d++)
            if (buscarCaminoDFSRec(x + dx[d], y + dy[d], palabra, idx + 1, visitado, camino + " -> (" + (x + dx[d]) + "," + (y + dy[d]) + "):" + ((x + dx[d]) >= 0 && (x + dx[d]) < n && (y + dy[d]) >= 0 && (y + dy[d]) < m ? letras[x + dx[d]][y + dy[d]] : ' ')))
                return true;
        visitado[x][y] = false;
        return false;
    }

    // --- BFS (para sí/no) ---
    public boolean buscarPalabraBFS(String palabra) {
        int N = letras.length, M = letras[0].length, len = palabra.length();
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (letras[i][j] == palabra.charAt(0)) {
                    Queue<BFSNode> queue = new LinkedList<>();
                    boolean[][] visitadoInicial = new boolean[N][M];
                    visitadoInicial[i][j] = true;
                    queue.add(new BFSNode(i, j, 1, visitadoInicial));
                    while (!queue.isEmpty()) {
                        BFSNode node = queue.poll();
                        if (node.idx == len) return true;
                        for (int d = 0; d < 8; d++) {
                            int nx = node.x + dx[d], ny = node.y + dy[d];
                            if (nx >= 0 && nx < N && ny >= 0 && ny < M
                                    && !node.visitados[nx][ny]
                                    && letras[nx][ny] == palabra.charAt(node.idx)) {
                                boolean[][] nuevoVisitados = copiarMatriz(node.visitados);
                                nuevoVisitados[nx][ny] = true;
                                queue.add(new BFSNode(nx, ny, node.idx + 1, nuevoVisitados));
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    // --- BFS (para camino/arbol) ---
    public String buscarCaminoBFS(String palabra) {
        int N = letras.length, M = letras[0].length, len = palabra.length();
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (letras[i][j] == palabra.charAt(0)) {
                    Queue<BFSNodeCamino> queue = new LinkedList<>();
                    boolean[][] visitadoInicial = new boolean[N][M];
                    visitadoInicial[i][j] = true;
                    queue.add(new BFSNodeCamino(i, j, 1, "(" + i + "," + j + "):" + letras[i][j], visitadoInicial));
                    while (!queue.isEmpty()) {
                        BFSNodeCamino node = queue.poll();
                        if (node.idx == len) return node.camino;
                        for (int d = 0; d < 8; d++) {
                            int nx = node.x + dx[d], ny = node.y + dy[d];
                            if (nx >= 0 && nx < N && ny >= 0 && ny < M
                                    && !node.visitados[nx][ny]
                                    && letras[nx][ny] == palabra.charAt(node.idx)) {
                                boolean[][] nuevoVisitados = copiarMatriz(node.visitados);
                                nuevoVisitados[nx][ny] = true;
                                queue.add(new BFSNodeCamino(nx, ny, node.idx + 1,
                                        node.camino + " -> (" + nx + "," + ny + "):" + letras[nx][ny],
                                        nuevoVisitados));
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    // --- Utilidades internas ---
    private boolean[][] copiarMatriz(boolean[][] matriz) {
        boolean[][] copia = new boolean[matriz.length][matriz[0].length];
        for (int i = 0; i < matriz.length; i++)
            System.arraycopy(matriz[i], 0, copia[i], 0, matriz[0].length);
        return copia;
    }

    private static class BFSNode {
        int x, y, idx;
        boolean[][] visitados;
        BFSNode(int x, int y, int idx, boolean[][] visitados) {
            this.x = x; this.y = y; this.idx = idx;
            this.visitados = visitados;
        }
    }

    private static class BFSNodeCamino {
        int x, y, idx;
        String camino;
        boolean[][] visitados;
        BFSNodeCamino(int x, int y, int idx, String camino, boolean[][] visitados) {
            this.x = x; this.y = y; this.idx = idx; this.camino = camino;
            this.visitados = visitados;
        }
    }
    
    public Set<Point> buscarPalabraPosiciones(String palabra) {
    int n = letras.length, m = letras[0].length;
    boolean[][] visitado = new boolean[n][m];
    Set<Point> resultado = new HashSet<>();
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            if (dfsPos(i, j, palabra, 0, visitado, resultado)) {
                return new HashSet<>(resultado); // copia para evitar efectos colaterales
            }
            resultado.clear();
        }
    }
    return new HashSet<>(); // Si no se encuentra, retorna vacío
}

private boolean dfsPos(int x, int y, String palabra, int idx, boolean[][] visitado, Set<Point> path) {
    int n = letras.length, m = letras[0].length;
    if (idx >= palabra.length()) return false;
    if (x < 0 || x >= n || y < 0 || y >= m) return false;
    if (visitado[x][y]) return false;
    if (letras[x][y] != palabra.charAt(idx)) return false;

    path.add(new Point(x, y));
    visitado[x][y] = true;
    if (idx == palabra.length() - 1) {
        visitado[x][y] = false;
        return true;
    }

    int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
    int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};
    for (int d = 0; d < 8; d++) {
        if (dfsPos(x + dx[d], y + dy[d], palabra, idx + 1, visitado, path)) {
            visitado[x][y] = false;
            return true;
        }
    }
    path.remove(new Point(x, y));
    visitado[x][y] = false;
    return false;
}
}