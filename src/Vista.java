import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.border.Border;
import java.util.LinkedList;

public class Vista extends JFrame {

    private JPanel jugador1, jugador2, jugador3, jugador4, tablero;
    private JButton btnPasarJ1, btnPasarJ2, btnPasarJ3, btnPasarJ4;
    private JButton btnMezclar, btnRepartir, btnMostrar;
    private BtnFichaDomino[] botonesFichas;

    public Vista() {
        super("Dominó");
        hazInterfaz();
        // this.addComponentListener(this);
    }

    public static void main(String[] args) {
        new Vista();
    }

    private void hazInterfaz() {
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);

        jugador1 = new JPanel();
        jugador2 = new JPanel();
        jugador3 = new JPanel();
        jugador4 = new JPanel();
        tablero = new JPanel();
        tablero.setLayout(new GridBagLayout());

        btnPasarJ1 = new JButton("Pasar");
        jugador1.add(btnPasarJ1);
        btnPasarJ2 = new JButton("Pasar");
        jugador2.add(btnPasarJ2);
        btnPasarJ3 = new JButton("Pasar");
        jugador3.add(btnPasarJ3);
        btnPasarJ4 = new JButton("Pasar");
        jugador4.add(btnPasarJ4);
        btnMostrar = new JButton("Ocultar");
        btnMezclar = new JButton("Mezclar");
        btnRepartir = new JButton("Repartir");

        // Crear un borde rojo
        Border bordeRojo = BorderFactory.createLineBorder(Color.red);

        // Asignar el borde rojo a cada panel
        jugador1.setBorder(bordeRojo);
        jugador2.setBorder(bordeRojo);
        jugador3.setBorder(bordeRojo);
        jugador4.setBorder(bordeRojo);
        tablero.setBorder(bordeRojo);

        int w = this.getWidth();
        int h = this.getHeight();
        // posicion del panel jugador1
        int x = 0;
        int y = 0;
        int ancho = (int) (w * 0.25);
        int alto = (int) (h * 0.25);
        jugador1.setBounds(x, y, ancho, alto);
        // posicion del panel jugador2
        x = (int) (w * 0.75);
        jugador2.setBounds(x, y, ancho, alto);
        // posicion del jugador3
        y = (int) (h * 0.75);
        jugador3.setBounds(x, y, ancho, alto);
        // posicion del jugador4
        x = 0;
        jugador4.setBounds(x, y, ancho, alto);
        // posicion del tablero
        x = (int) (w * 0.25);
        y = (int) (h * 0.25);
        ancho = (int) (w * 0.5);
        alto = (int) (h * 0.5);
        tablero.setBounds(x, y, ancho, alto);
        // posicion mezclar
        x = (int) (w * 0.25);
        y = (int) (h * 0.75);
        ancho = (int) (w * 0.16);
        alto = (int) (h * 0.1);
        btnMezclar.setBounds(x, y, ancho, alto);
        // posicion repartir
        x = (int) (w * 0.42);
        btnRepartir.setBounds(x, y, ancho, alto);
        //posicion mostrar
        x = (int) (w * 0.59);
        btnMostrar.setBounds(x, y, ancho, alto);

        add(jugador1);
        add(jugador2);
        add(jugador3);
        add(jugador4);
        add(tablero);
        add(btnMezclar);
        add(btnMostrar);
        add(btnRepartir);

        setVisible(true);
    }

    public void setNuevoTablero(LinkedList<FichaDomino> fichas) {
        botonesFichas = new BtnFichaDomino[fichas.size()];
        tablero.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        try {
            int c = 0;
            int f = 0;
            for (int i = 0; i < fichas.size(); i++) {
                botonesFichas[i] = new BtnFichaDomino(fichas.get(i));
                ImageIcon icon = Rutinas.AjustarImagen("imagenes/" + botonesFichas[i].getClaveImagenAsociada() + ".png",
                        80, 80);
                botonesFichas[i].setIcon(icon);

                gbc.gridx = c++;
                gbc.gridy = f;
                gbc.gridheight = 1;
                gbc.gridwidth = 1;
                gbc.weightx = 1.0;
                gbc.weighty = 1.0;

                gbc.fill = GridBagConstraints.BOTH;
                tablero.add(botonesFichas[i], gbc);
                if (c == 7) {
                    c = 0;
                    f++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        revalidate();
        repaint();
    }

    private BufferedImage rotateImage(BufferedImage image, double angle) {
        double rads = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
        int w = image.getWidth();
        int h = image.getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);

        BufferedImage rotated = new BufferedImage(newWidth, newHeight, image.getType());
        Graphics2D g2d = rotated.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((newWidth - w) / 2, (newHeight - h) / 2);

        int x = w / 2;
        int y = h / 2;

        at.rotate(rads, x, y);
        g2d.setTransform(at);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return rotated;
    }

    // @Override
    // public void componentResized(ComponentEvent e) {
    // // Obtener el tamaño actual de los botones y ajustar la imagen al nuevo
    // tamaño
    // for (JButton boton : botones) {
    // int width = boton.getWidth();
    // int height = boton.getHeight();

    // ImageIcon icon = (ImageIcon) boton.getIcon();
    // if (icon != null) {
    // // Ajustar la imagen al tamaño actual del botón
    // icon = Rutinas.AjustarImagen(icon, width, height);
    // boton.setIcon(icon);
    // }
    // }

    // validate(); // Actualiza el layout después del redimensionamiento
    // }

    // @Override
    // public void componentMoved(ComponentEvent e) {
    // // No es necesario implementar este método
    // }

    // @Override
    // public void componentShown(ComponentEvent e) {
    // // No es necesario implementar este método
    // }

    // @Override
    // public void componentHidden(ComponentEvent e) {
    // // No es necesario implementar este método
    // }
}
