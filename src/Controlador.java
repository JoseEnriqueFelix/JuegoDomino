import java.util.LinkedList;

public class Controlador {

    private Vista vista;
    private Modelojuego modelo;

    public Controlador(Modelojuego modelo, Vista vista){
        this.modelo = modelo;
        this.vista = vista;
    }

    public LinkedList<FichaDomino> getListaFichasTablero(){
        return modelo.getTablero().getListaFichas();
    } 

    public void comenzarJuego(){
        vista.setNuevoTablero(getListaFichasTablero());
    }
}
