import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.event.*;

public class Controlador implements ActionListener {

    private Vista vista;
    private Modelojuego modelo;

    public Controlador(Modelojuego modelo, Vista vista) {
        this.modelo = modelo;
        this.vista = vista;
    }

    public LinkedList<FichaDomino> getListaFichasTablero() {
        return modelo.getTablero().getListaFichas();
    }

    public void comenzarJuego() {
        vista.setNuevoTablero(getListaFichasTablero());
    }

    public void setEscuchadoresBotonesJuego() {
        vista.getBtnMezclar().addActionListener(this);
        vista.getBtnMostrar().addActionListener(this);
        vista.getBtnRepartir().addActionListener(this);
    }

    public void setEscuchadoresPasar() {
        JButton[] aux = vista.getBtnsPasar();
        for (int i = 0; i < aux.length; i++) {
            aux[i].addActionListener(this);
        }
    }

    private void setEscuchadoresPiezasJugadores() {
        LinkedList<BtnFichaDomino>[] lista = vista.getFichasJugadores();
        for (int i = 0; i < lista.length; i++)
            for (int j = 0; j < lista[i].size(); j++)
                lista[i].get(j).addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ((JButton) e.getSource() == vista.getBtnMezclar()) {
            modelo.mezclarFichas();
            vista.setNuevoTablero(getListaFichasTablero());
            return;
        }
        if ((JButton) e.getSource() == vista.getBtnMostrar()) {
            LinkedList<FichaDomino> fichas;
            if (vista.getBtnMostrar().getText().equals("Ocultar")) {
                fichas = getListaFichasTablero();
                for (FichaDomino f : fichas)
                    f.setVisible(false);
                vista.getBtnMostrar().setText("Mostrar");
            } else {
                fichas = getListaFichasTablero();
                for (FichaDomino f : fichas)
                    f.setVisible(true);
                vista.getBtnMostrar().setText("Ocultar");
            }
            vista.setNuevoTablero(getListaFichasTablero());
            return;
        }
        if ((JButton) e.getSource() == vista.getBtnRepartir()) {
            modelo.repartir();
            vista.repartirFichas(modelo.getJugadores());
            vista.setNuevoTablero(getListaFichasTablero());
            setEscuchadoresPiezasJugadores();
            setEscuchadoresPasar();
            int i = modelo.obtenerJugadorInicial();
            vista.setFocusPanelJugadorActual(i);
            vista.getBtnMezclar().setEnabled(false);
            vista.getBtnRepartir().setEnabled(false);
            vista.getBtnMostrar().setEnabled(false);
            return;
        }
        JButton[] auxPasar = vista.getBtnsPasar();
        for (int i = 0; i < auxPasar.length; i++) {
            if ((JButton) e.getSource() == auxPasar[i]) {
                modelo.actualizarIndiceJugadorActual();
                vista.setFocusPanelJugadorActual(modelo.getIndiceJugadorActual());
                return;
            }
        }
        LinkedList<BtnFichaDomino>[] lista = vista.getFichasJugadores();
        BtnFichaDomino aux = (BtnFichaDomino) e.getSource();
        for (int i = 0; i < lista.length; i++)
            for (int j = 0; j < lista[i].size(); j++)
                if (aux == lista[i].get(j)) {
                    if (modelo.esPrimeraPieza()) {
                        if (modelo.primerPieza(modelo.getJugadores()[i], aux.getFichaAsociada())) {
                            modelo.actualizarIndiceJugadorActual();
                            vista.setFocusPanelJugadorActual(modelo.getIndiceJugadorActual());
                            vista.actualizarPanelesJugadores(aux.getFichaAsociada());
                            vista.setNuevoTablero(getListaFichasTablero());
                            setEscuchadoresPiezasJugadores();
                            setEscuchadoresPasar();
                            modelo.setEsPrimeraPieza(false);
                        }
                        return;
                    }
                    if (modelo.turno(modelo.getJugadores()[i], aux.getFichaAsociada())) {
                        modelo.actualizarIndiceJugadorActual();
                        vista.setFocusPanelJugadorActual(modelo.getIndiceJugadorActual());
                        vista.actualizarPanelesJugadores(aux.getFichaAsociada());
                        vista.setNuevoTablero(getListaFichasTablero());
                        
                        setEscuchadoresPasar();
                        setEscuchadoresPiezasJugadores();
                        
                        if (modelo.evaluarFinalPartida()) {
                            JOptionPane.showMessageDialog(vista,
                                    "¡La partida ha terminado\nHa ganado el jugador " + modelo.getGanador(),
                                    "Fin del Juego",
                                    JOptionPane.INFORMATION_MESSAGE);
                            vista.dispose();
                            System.exit(0);
                        }
                    }
                    return;
                }

    }
}
