/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package sopadeletras;

import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JFileChooser;       
import sopadeletras.Diccionario;
import sopadeletras.Tablero;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import sopadeletras.ResaltadoRenderer;

/**
 *
 * @author Dell
 */
public class InterfazSopaDeLetras extends javax.swing.JFrame {
    private Diccionario diccionario;
    private Tablero tablero;
    private File ultimoArchivoCargado = null;
    private static final String ARCHIVO_ULTIMA_PARTIDA = "ultima_partida.txt";
    


    public InterfazSopaDeLetras() {
        initComponents();
        this.diccionario = new Diccionario();
        this.tablero = null;
        ajustarTablaTablero();
        cargarUltimaPartidaGuardada(); // Intenta leer la ruta guardada al iniciar
        
        tablaTablero.setTableHeader(null);
        jScrollPane2.setColumnHeaderView(null);
        
    }
    private void guardarUltimaPartida(File archivo) {
        try (PrintWriter out = new PrintWriter(ARCHIVO_ULTIMA_PARTIDA)) {
            out.println(archivo.getAbsolutePath());
        } catch (IOException e) {}
    }
    
    private void cargarUltimaPartidaGuardada() {
        File archivoUltimaPartida = new File(ARCHIVO_ULTIMA_PARTIDA);
        if (archivoUltimaPartida.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(archivoUltimaPartida))) {
                String path = br.readLine();
                if (path != null && !path.isEmpty()) {
                    File archivo = new File(path);
                    if (archivo.exists()) {
                        ultimoArchivoCargado = archivo;
                    }
                }
            } catch (IOException e) {}
        }
    }
    
    private void ajustarTablaTablero() {
    tablaTablero.setTableHeader(null);
    tablaTablero.setFont(new java.awt.Font("Monospaced", java.awt.Font.BOLD, 32));
    tablaTablero.setRowHeight(50);

    javax.swing.table.DefaultTableCellRenderer centerRenderer = new javax.swing.table.DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    for (int i = 0; i < tablaTablero.getColumnCount(); i++) {
        tablaTablero.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }
}
    private void reiniciarPrograma() {
    // Limpiar tablero visual
    int filas = 4, columnas = 4;
    javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) tablaTablero.getModel();
    modelo.setRowCount(filas);
    modelo.setColumnCount(columnas);
    for (int i = 0; i < filas; i++) {
        for (int j = 0; j < columnas; j++) {
            modelo.setValueAt("", i, j);
        }
    }

    // Limpiar área de resultado
    AreaResultado.setText("");

    // Limpiar campo de texto de palabra
    TextoPalabra.setText("");

    // Limpiar diccionario y tablero en memoria
    diccionario = new Diccionario();
    tablero = null;
}
    
    private boolean cargarArchivoDesdeRuta(File archivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            boolean leyendoDiccionario = false;
            boolean leyendoTablero = false;
            diccionario = new Diccionario();
            char[][] letrasTablero = new char[4][4];

            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.equalsIgnoreCase("dic")) {
                    leyendoDiccionario = true;
                    continue;
                }
                if (linea.equalsIgnoreCase("/dic")) {
                    leyendoDiccionario = false;
                    continue;
                }
                if (linea.equalsIgnoreCase("tab")) {
                    leyendoTablero = true;
                    continue;
                }
                if (linea.equalsIgnoreCase("/tab")) {
                    leyendoTablero = false;
                    continue;
                }

                if (leyendoDiccionario && !linea.isEmpty()) {
                   diccionario.agregarPalabra(linea.trim().toUpperCase());
                }
                if (leyendoTablero && !linea.isEmpty()) {
                    // Se espera una línea con 16 letras separadas por coma
                    String[] letras = linea.split(",");
                    if (letras.length != 16) {
                        JOptionPane.showMessageDialog(this, "Error: El tablero debe tener 16 letras.", "Error", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                    int idx = 0;
                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            letrasTablero[i][j] = letras[idx++].trim().charAt(0);
                        }
                    }
                }
            }
            diccionario.mostrarDiccionario();

            tablero = new Tablero(letrasTablero);
            actualizarTableroVisual(letrasTablero);

            AreaResultado.setText("Archivo cargado correctamente. Diccionario y tablero listos.\n");
            return true;

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al leer el archivo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error en el formato del archivo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    
    private void actualizarTableroVisual(char[][] letras) {
    for (int i = 0; i < letras.length; i++)
        for (int j = 0; j < letras[i].length; j++)
            tablaTablero.setValueAt(String.valueOf(letras[i][j]), i, j);
}
    
    private Set<Point> posicionesResaltadas = new HashSet<>();
    private void buscarYResaltarPalabra() {
    String palabra = TextoPalabra.getText().trim().toUpperCase();
    Set<Point> posiciones = tablero.buscarPalabraPosiciones(palabra);

    if (!posiciones.isEmpty()) {
        posicionesResaltadas = posiciones;
        AreaResultado.append("¡Palabra encontrada!\n");
    } else {
        posicionesResaltadas.clear();
        AreaResultado.append("Palabra NO encontrada.\n");
    }

    ResaltadoRenderer renderer = new ResaltadoRenderer(posicionesResaltadas);
    for (int i = 0; i < tablaTablero.getColumnCount(); i++) {
        tablaTablero.getColumnModel().getColumn(i).setCellRenderer(renderer);
    }
    tablaTablero.repaint();
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        AreaResultado = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        TextoPalabra = new javax.swing.JTextField();
        CargarArchivo = new javax.swing.JButton();
        BuscarPalabra = new javax.swing.JButton();
        BuscarTodasDFS = new javax.swing.JButton();
        BuscarTodasBFS = new javax.swing.JButton();
        GuardarDiccionario = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaTablero = new javax.swing.JTable();
        cargarUltimaPartida = new javax.swing.JButton();
        Reiniciar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 70, -1, -1));

        jLabel1.setText("TABLERO DE LETRAS");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 30, -1, -1));

        jLabel2.setText("RESULTADO");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 440, -1, -1));

        AreaResultado.setColumns(20);
        AreaResultado.setRows(5);
        jScrollPane2.setViewportView(AreaResultado);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 390, 410, 110));

        jLabel3.setText("INGRESE UNA PALABRA");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 300, -1, -1));
        getContentPane().add(TextoPalabra, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 320, 180, 30));

        CargarArchivo.setText("CARGAR ARCHIVO");
        CargarArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CargarArchivoActionPerformed(evt);
            }
        });
        getContentPane().add(CargarArchivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 80, -1, -1));

        BuscarPalabra.setText("BUSCAR PALABRA");
        BuscarPalabra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarPalabraActionPerformed(evt);
            }
        });
        getContentPane().add(BuscarPalabra, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 120, -1, -1));

        BuscarTodasDFS.setText("BUSCAR TODAS  (DFS)");
        BuscarTodasDFS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarTodasDFSActionPerformed(evt);
            }
        });
        getContentPane().add(BuscarTodasDFS, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 160, -1, -1));

        BuscarTodasBFS.setText("BUSCAR TODAS (BFS)");
        BuscarTodasBFS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarTodasBFSActionPerformed(evt);
            }
        });
        getContentPane().add(BuscarTodasBFS, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 200, -1, -1));

        GuardarDiccionario.setText("GUARDAR EN DICCIONARIO");
        GuardarDiccionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GuardarDiccionarioActionPerformed(evt);
            }
        });
        getContentPane().add(GuardarDiccionario, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 240, -1, -1));

        tablaTablero.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "", "", "", ""
            }
        ));
        jScrollPane3.setViewportView(tablaTablero);

        getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 60, 300, 210));

        cargarUltimaPartida.setText("CARGAR ULTIMO ARCHIVO ");
        cargarUltimaPartida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cargarUltimaPartidaActionPerformed(evt);
            }
        });
        getContentPane().add(cargarUltimaPartida, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 280, -1, -1));

        Reiniciar.setText("REINICIAR");
        Reiniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReiniciarActionPerformed(evt);
            }
        });
        getContentPane().add(Reiniciar, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 440, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BuscarPalabraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarPalabraActionPerformed
        String palabra = TextoPalabra.getText().trim().toUpperCase();
    if (palabra.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, ingrese una palabra para buscar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        return;
    }
    if (tablero == null) {
        JOptionPane.showMessageDialog(this, "Primero cargue un tablero.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        return;
    }
    // === Validación con el Diccionario ===
    if (diccionario == null || !diccionario.contienePalabra(palabra)) {
        AreaResultado.append("La palabra '" + palabra + "' NO está en el diccionario.\n");
        return;
    }
    // === Búsqueda en el tablero ===
    long inicio = System.currentTimeMillis();
    boolean encontrada = tablero.buscarPalabraDFS(palabra);
    long fin = System.currentTimeMillis();
    if (encontrada) {
    String arbol = tablero.buscarCaminoBFS(palabra);;
    AreaResultado.append("Palabra '" + palabra + "' encontrada en el tablero.\n");
    AreaResultado.append("Árbol de búsqueda (camino): " + arbol + "\n");
    AreaResultado.append("Tiempo: " + (fin - inicio) + " ms\n");
} else {
    AreaResultado.append("La palabra '" + palabra + "' NO se encuentra en el tablero. Tiempo: " + (fin - inicio) + " ms\n");
}
     buscarYResaltarPalabra();
     
    
    }//GEN-LAST:event_BuscarPalabraActionPerformed
    
    private void BuscarTodasDFSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarTodasDFSActionPerformed
                                               
    if (tablero == null || diccionario == null) {
        AreaResultado.append("Primero cargue un tablero y un diccionario.\n");
        return;
    }
    long inicio = System.currentTimeMillis();
    int encontradas = 0;
    for (String palabra : diccionario.getPalabras()) {
        boolean encontrada = tablero.buscarPalabraDFS(palabra);
        if (encontrada) {
            AreaResultado.append("Palabra '" + palabra + "' encontrada (DFS).\n");
            encontradas++;
        } else {
            AreaResultado.append("Palabra '" + palabra + "' NO encontrada (DFS).\n");
        }
    }
    long fin = System.currentTimeMillis();
    AreaResultado.append("Total encontradas con DFS: " + encontradas + ". Tiempo: " + (fin - inicio) + " ms\n");

    }//GEN-LAST:event_BuscarTodasDFSActionPerformed

    private void GuardarDiccionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GuardarDiccionarioActionPerformed
    String palabra = TextoPalabra.getText().trim();
    if (palabra.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, ingrese una palabra para guardar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        return;
    }

    if (diccionario == null){
        diccionario = new Diccionario();
    }
     boolean guardada = diccionario.agregarPalabra(palabra);
    if (guardada) {
        AreaResultado.append("Palabra agregada al diccionario: " + palabra + "\n");
    } else {
        AreaResultado.append("La palabra ya existe en el diccionario: " + palabra + "\n");
    }
    TextoPalabra.setText("");
    }//GEN-LAST:event_GuardarDiccionarioActionPerformed

    private void BuscarTodasBFSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarTodasBFSActionPerformed
                                              
    if (tablero == null || diccionario == null) {
        AreaResultado.append("Primero cargue un tablero y un diccionario.\n");
        return;
    }
    long inicio = System.currentTimeMillis();
    int encontradas = 0;
    for (String palabra : diccionario.getPalabras()) {
        boolean encontrada = tablero.buscarPalabraBFS(palabra);
        if (encontrada) {
            AreaResultado.append("Palabra '" + palabra + "' encontrada (BFS).\n");
            encontradas++;
        } else {
            AreaResultado.append("Palabra '" + palabra + "' NO encontrada (BFS).\n");
        }
    }
    long fin = System.currentTimeMillis();
    AreaResultado.append("Total encontradas con BFS: " + encontradas + ". Tiempo: " + (fin - inicio) + " ms\n");

    }//GEN-LAST:event_BuscarTodasBFSActionPerformed

    private void CargarArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CargarArchivoActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            if (cargarArchivoDesdeRuta(archivo)) {
                ultimoArchivoCargado = archivo;
                guardarUltimaPartida(archivo);
            }
        }
        
    
    }//GEN-LAST:event_CargarArchivoActionPerformed

    private void cargarUltimaPartidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cargarUltimaPartidaActionPerformed
        if (ultimoArchivoCargado != null && ultimoArchivoCargado.exists()) {
            cargarArchivoDesdeRuta(ultimoArchivoCargado);
        } else {
            JOptionPane.showMessageDialog(this, "No hay partida guardada para cargar.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
        }
        ResaltadoRenderer renderer = new ResaltadoRenderer(posicionesResaltadas);
            for (int i = 0; i < tablaTablero.getColumnCount(); i++) {
            tablaTablero.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
        tablaTablero.repaint();
        
    
    }//GEN-LAST:event_cargarUltimaPartidaActionPerformed

    private void ReiniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReiniciarActionPerformed
        reiniciarPrograma();
    }//GEN-LAST:event_ReiniciarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InterfazSopaDeLetras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InterfazSopaDeLetras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InterfazSopaDeLetras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InterfazSopaDeLetras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InterfazSopaDeLetras().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea AreaResultado;
    private javax.swing.JButton BuscarPalabra;
    private javax.swing.JButton BuscarTodasBFS;
    private javax.swing.JButton BuscarTodasDFS;
    private javax.swing.JButton CargarArchivo;
    private javax.swing.JButton GuardarDiccionario;
    private javax.swing.JButton Reiniciar;
    private javax.swing.JTextField TextoPalabra;
    private javax.swing.JButton cargarUltimaPartida;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tablaTablero;
    // End of variables declaration//GEN-END:variables
}