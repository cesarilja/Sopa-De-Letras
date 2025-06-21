/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sopadeletras;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import javax.swing.SwingConstants;
/**
 *
 * @author Dell
 */

/**
 * Renderer personalizado para resaltar celdas del JTable
 * según las posiciones encontradas de una palabra.
 */
public class ResaltadoRenderer extends DefaultTableCellRenderer {
    private Set<Point> posicionesResaltadas;

    public ResaltadoRenderer(Set<Point> posicionesResaltadas) {
        this.posicionesResaltadas = posicionesResaltadas;
    }

    @Override
    public Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        // CENTRAR TEXTO
        setHorizontalAlignment(SwingConstants.CENTER);

        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (posicionesResaltadas != null && posicionesResaltadas.contains(new Point(row, column))) {
            c.setBackground(Color.YELLOW);
            c.setForeground(Color.BLACK);
        } else {
            c.setBackground(Color.WHITE);
            c.setForeground(Color.BLACK);
        }
        return c;
    }
}
    

