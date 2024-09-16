import java.util.*;

public class TableroDomino {
    private LinkedList<FichaDomino> listaFichas = new LinkedList<>();

    public TableroDomino(LinkedList<FichaDomino> lista) {
        this.listaFichas = lista;
    }

    public void mostrarFichas() {
        System.out.println("----------------TABLERO-------------------------");
        for (FichaDomino f : listaFichas)
            System.out.println(f.toString());
        System.out.println("----------------TABLERO-------------------------");

    }

    public LinkedList<FichaDomino> getListaFichas() {
        return listaFichas;
    }

    public void insertarDerecha(FichaDomino ficha) {
        listaFichas.addLast(ficha);
        mostrarFichas();
    }

    public void insertarIzquierda(FichaDomino ficha) {
        listaFichas.addFirst(ficha);
        mostrarFichas();
    }

}
