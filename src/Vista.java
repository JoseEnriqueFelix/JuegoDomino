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
    private final int NUMERO_DE_JUGADORES = 4;
    private JPanel tablero;
    private JPanel[] jugadores;
    private JButton[] btnsPasar;
    private LinkedList<BtnFichaDomino>[] fichasJugadores;
    private JButton btnMezclar, btnRepartir, btnMostrar;
    private BtnFichaDomino[] btnsFichasTablero;
    private Border bordeOriginal;
    Border actual = BorderFactory.createLineBorder(Color.RED, 5);

    public Vista() {
        super("Dominó");
        jugadores = new JPanel[NUMERO_DE_JUGADORES];
        btnsPasar = new JButton[NUMERO_DE_JUGADORES];
        tablero = new JPanel();
        fichasJugadores = new LinkedList[NUMERO_DE_JUGADORES];
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

        for (int i = 0; i < jugadores.length; i++) {
            jugadores[i] = new JPanel();
            jugadores[i].setLayout(new GridBagLayout());
        }
        tablero.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;

        for (int i = 0; i < jugadores.length; i++) {
            btnsPasar[i] = new JButton("Pasar");
            jugadores[i].add(btnsPasar[i], gbc);
        }

        btnMostrar = new JButton("Ocultar");
        btnMezclar = new JButton("Mezclar");
        btnRepartir = new JButton("Repartir");

        
        bordeOriginal = tablero.getBorder();

        int w = this.getWidth();
        int h = this.getHeight();
        // posicion del panel jugador1
        int x = 0;
        int y = 0;
        int ancho = (int) (w * 0.25);
        int alto = (int) (h * 0.25);
        jugadores[0].setBounds(x, y, ancho, alto);
        // posicion del panel jugador2
        x = (int) (w * 0.75);
        jugadores[1].setBounds(x, y, ancho, alto);
        // posicion del jugador3
        y = (int) (h * 0.75);
        alto = (int) (h * 0.2); 
        jugadores[2].setBounds(x, y, ancho, alto);
        // posicion del jugador4
        x = 0;
        jugadores[3].setBounds(x, y, ancho, alto);
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
        // posicion mostrar
        x = (int) (w * 0.59);
        btnMostrar.setBounds(x, y, ancho, alto);

        for (int i = 0; i < jugadores.length; i++)
            add(jugadores[i]);
        add(tablero);
        add(btnMezclar);
        add(btnMostrar);
        add(btnRepartir);

        setVisible(true);
    }

    public void setNuevoTablero(LinkedList<FichaDomino> fichas) {
        tablero.removeAll();
        if (fichas.isEmpty()) 
            return;
        btnsFichasTablero = new BtnFichaDomino[fichas.size()];
        try {
            int c = 0;
            int f = 0;
            for (int i = 0; i < fichas.size(); i++) {
                btnsFichasTablero[i] = new BtnFichaDomino(fichas.get(i));
                if (btnsFichasTablero[i].getFichaAsociada().getEsVisible()) {
                    ImageIcon icon = Rutinas.AjustarImagen(
                            "imagenes/" + btnsFichasTablero[i].getClaveImagenAsociada() + ".png",
                            75, 75);
                    btnsFichasTablero[i].setIcon(icon);
                } else {
                    ImageIcon icon = Rutinas.AjustarImagen(
                            "imagenes/oculto.png",
                            80, 80);
                    btnsFichasTablero[i].setIcon(icon);
                }
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = c++;
                gbc.gridy = f;
                gbc.gridheight = 1;
                gbc.gridwidth = 1;
                gbc.weightx = 1.0;
                gbc.weighty = 1.0;

                gbc.fill = GridBagConstraints.BOTH;
                tablero.add(btnsFichasTablero[i], gbc);
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

    public void actualizarPanelesJugadores(Jugador[] jug) {
        for (int i = 0; i < jug.length; i++) {
            LinkedList<FichaDomino> aux = jug[i].getFichas();
            fichasJugadores[i] = new LinkedList<>();
            for (int j = 0; j < btnsFichasTablero.length; j++)
                if (aux.contains(btnsFichasTablero[j].getFichaAsociada()))
                    fichasJugadores[i].add(btnsFichasTablero[j]);
        }
        for (int i = 0; i < jug.length; i++) {
            int c = 0;
            int f = 0;
            GridBagConstraints gbc = new GridBagConstraints();
            for (int j = 0; j < fichasJugadores[i].size(); j++) {
                gbc.gridx = c++;
                gbc.gridy = f;
                gbc.gridheight = 1;
                gbc.gridwidth = 1;
                gbc.weightx = 1.0;
                gbc.weighty = 1.0;

                gbc.fill = GridBagConstraints.BOTH;
                jugadores[i].add(fichasJugadores[i].get(j), gbc);
                if (c == 4) {
                    c = 0;
                    f++;
                }
            }
        }
        revalidate();
        repaint();
    }

    public void setFocusPanelJugadorActual(int i){
        jugadores[i].setBorder(actual);
        for (int j = 0; j < fichasJugadores[i].size(); j++)
            fichasJugadores[i].get(j).setEnabled(true);
        for (int j = 0; j < jugadores.length; j++){
            if (j == i)
                continue;
            for (int k = 0; k < fichasJugadores[j].size(); k++)
                fichasJugadores[j].get(k).setEnabled(false);
            jugadores[j].setBorder(bordeOriginal);
        }
    }

    public JButton getBtnMezclar() {
        return btnMezclar;
    }

    public JButton getBtnRepartir() {
        return btnRepartir;
    }

    public JButton getBtnMostrar() {
        return btnMostrar;
    }

    public BtnFichaDomino[] getbtnsFichasTablero() {
        return btnsFichasTablero;
    }

    public LinkedList<BtnFichaDomino>[] getFichasJugadores() {
        return fichasJugadores;
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
