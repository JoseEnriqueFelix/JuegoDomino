import java.util.LinkedList;

import javax.swing.JButton;

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

    private void setEscuchadoresPiezasJugadores() {
        LinkedList<BtnFichaDomino>[] lista = vista.getFichasJugadores();
        for (int i = 0; i < lista.length; i++)
            for (int j = 0; j < lista[i].size(); j++)
                lista[i].get(j).addActionListener(this);
    }

    private void actualizarPanelesJugadores() {
        vista.actualizarPanelesJugadores(modelo.getJugadores());
    }

    private void actualizarPanelTablero() {
        vista.setNuevoTablero(getListaFichasTablero());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ((JButton) e.getSource() == vista.getBtnMezclar()) {
            modelo.mezclarFichas();
            actualizarPanelTablero();
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
            actualizarPanelTablero();
            return;
        }
        if ((JButton) e.getSource() == vista.getBtnRepartir()) {
            modelo.repartir();
            actualizarPanelesJugadores();
            actualizarPanelTablero();
            setEscuchadoresPiezasJugadores();
            int i = modelo.obtenerJugadorInicial();
            vista.setFocusPanelJugadorActual(i);
            vista.getBtnMezclar().setEnabled(false);
            vista.getBtnMostrar().setEnabled(false);
            vista.getBtnRepartir().setEnabled(false);
            return;
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
                            AlgoAuxAcomodo.setMatrizPrimero(aux);
                            actualizarPanelesJugadores();
                            vista.actualizarTablero();
                        }
                    }
                }
    }
}
