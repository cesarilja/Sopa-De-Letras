/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sopadeletras;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Dell
 */
public class FondoSeleccionarModoPanel extends JPanel {
    private Image imagen;

    public FondoSeleccionarModoPanel() {
        // Cambia el nombre si tu archivo es JPG, PNG, etc.
        imagen = new ImageIcon(getClass().getResource("/resources/ImagenSeleccionarModo.png")).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagen != null) {
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
    

