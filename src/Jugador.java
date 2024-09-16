import java.util.*;

public class Jugador {
    private LinkedList<FichaDomino> fichas;
    private String nombre;
    private int puntos;

    public Jugador(String nombre, LinkedList<FichaDomino> fichas) {
        this.nombre = nombre;
        this.fichas = fichas;
        puntos = 0;
    }

    public void pasar() {
    }

    public void borrarFicha(int indice) {
        if (indice >= 0 && indice < fichas.size()) {
            fichas.remove(indice);
        } else {
            System.out.println("Índice fuera de rango");
        }
    }
    
    public void verFichas() {
        for (FichaDomino f : fichas)
            System.out.println(f.toString());
    }

    public LinkedList<FichaDomino> getFichas() {
        return fichas;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    // public void insertarDerecha() {
    // }

    // public void insertarIzquierda() {
    // }

}
