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
     // Para mostrar en área de texto de la interfaz
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
  

// Busca la palabra en el tablero usando DFS. Retorna true si la encuentra, false si no.
    public boolean buscarPalabraDFS(String palabra) {
        int n = letras.length;
        int m = letras[0].length;
        boolean[][] visitado = new boolean[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (dfs(i, j, palabra, 0, visitado)) {
                    return true;
                }
            }
        }
        return false;
    }
    // Busca la palabra en el tablero usando el metodo BFS.
    public boolean buscarPalabraBFS(String palabra) {
    int N = letras.length;
    int M = letras[0].length;
    int len = palabra.length();

    // Direcciones posibles (8)
    int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
    int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            if (letras[i][j] == palabra.charAt(0)) {
                // La cola ahora lleva: x, y, idx, visitados
                java.util.Queue<BFSNode> queue = new java.util.LinkedList<>();
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
    return false; // No encontrada
}

// Clase auxiliar
class BFSNode {
    int x, y, idx;
    boolean[][] visitados;
    BFSNode(int x, int y, int idx, boolean[][] visitados) {
        this.x = x; this.y = y; this.idx = idx;
        this.visitados = visitados;
    }
}

private boolean[][] copiarMatriz(boolean[][] matriz) {
    boolean[][] copia = new boolean[matriz.length][matriz[0].length];
    for (int i = 0; i < matriz.length; i++)
        System.arraycopy(matriz[i], 0, copia[i], 0, matriz[0].length);
    return copia;
}

    

    // DFS recursivo
    private boolean dfs(int x, int y, String palabra, int idx, boolean[][] visitado) {
        if (idx == palabra.length())
            return true; // Palabra encontrada completa

        int n = letras.length;
        int m = letras[0].length;

        // Fuera de límites, ya visitado, o letra no coincide
        if (x < 0 || x >= n || y < 0 || y >= m || visitado[x][y] || letras[x][y] != palabra.charAt(idx))
            return false;

        visitado[x][y] = true;

        // Probar todas las direcciones (8)
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int d = 0; d < 8; d++) {
            if (dfs(x + dx[d], y + dy[d], palabra, idx + 1, visitado))
                return true;
        }

        visitado[x][y] = false; // Backtracking
        return false;
    }
}
