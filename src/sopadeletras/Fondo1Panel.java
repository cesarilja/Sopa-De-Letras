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
public class Fondo1Panel extends JPanel{
     private Image imagen;

    public Fondo1Panel() {
        imagen = new ImageIcon(getClass().getResource("/resources/ImagenInicio.png")).getImage();
    }

    @Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Image imagen = new ImageIcon(getClass().getResource("/resources/ImagenInicio.png")).getImage();
    // Dibuja la imagen escalada al tamaño actual del panel
    g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
}
}

    

