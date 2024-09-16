import java.util.*;

public class Modelojuego {
    private final int NUMERO_DE_JUGADORES = 4;
    private final int FICHAS_POR_JUGADOR = 7;
    private LinkedList<FichaDomino> listaFichas;
    private Jugador[] jugadores;
    private TableroDomino tablero;

    public Modelojuego() {
        listaFichas = new LinkedList<FichaDomino>();
        inicializarTablero();
        inicializarJugadores();
    }

    public void inicializarTablero() {
        for (int i = 0; i < FICHAS_POR_JUGADOR; i++)
            for (int j = i; j < FICHAS_POR_JUGADOR; j++)
                listaFichas.add(new FichaDomino(i, j));
        tablero = new TableroDomino(listaFichas);
        tablero.mostrarFichas();
    }

    public void inicializarJugadores() {
        jugadores = new Jugador[NUMERO_DE_JUGADORES];
        for (int i = 0; i < jugadores.length; i++)
            jugadores[i] = new Jugador("Jugador" + (i + 1), new LinkedList<FichaDomino>());
    }

    public void mezclarFichas() {
        for (int i = 0; i < 1000; i++) {
            int rand1 = Rutinas.nextInt(0, 27);
            int rand2 = Rutinas.nextInt(0, 27);
            FichaDomino temp = listaFichas.get(rand1);
            listaFichas.set(rand1, listaFichas.get(rand2));
            listaFichas.set(rand2, temp);
        }
        tablero.mostrarFichas();
    }

    public void repartir() {
        for (int i = 0; i < jugadores.length; i++)
            for (int j = 0; j < FICHAS_POR_JUGADOR; j++)
                jugadores[i].getFichas().add(tablero.getListaFichas().removeLast());
    }

    public void mostrarFichasJugadores(){
        for(int i = 0; i < jugadores.length; i++){
            System.out.println(jugadores[i].getNombre());
            System.out.println(jugadores[i].getFichas().toString());
            System.out.println();
        }
    }

    public void partida() {
        mezclarFichas();
        repartir();
        mostrarFichasJugadores();
        int jugador = obtenerJugadorInicial();
        primerPieza(jugadores[jugador]);
        jugador++;
        if (jugador == 4)
            jugador = 0;
        while (!finPartida()) {
            turno(jugadores[jugador]);
            jugador++;
            if (jugador == 4)
                jugador = 0;
        }
    }

    private boolean finPartida(){
        for (int i = 0; i < jugadores.length; i++)
            if (jugadores[i].getFichas().isEmpty())
                return true;
        return false;
    }

    public void turno(Jugador j) {
        Scanner sc = new Scanner(System.in);
        System.out.println("----------------------------" + j.getNombre()
                + " fichas----------------------------------------------");
        j.verFichas();
        System.out.println("----------------------------" + j.getNombre()
                + " fichas----------------------------------------------");

        System.out.println(
                "inserte -1 para pasar, un numero entre el 0 y su cantidad de fichas para seleccionar una ficha");
        int aux = sc.nextInt();
        if (aux == -1)
            return;
        FichaDomino f = j.getFichas().get(aux);
        System.out.println("Inserte 1 para insertar a la derecha, 0 para insertar a la izquierda");
        int aux2 = sc.nextInt();
        if (aux2 == 1) {
            if (tablero.getListaFichas().getLast().getSeleccionado() == 0
                    || tablero.getListaFichas().getLast().getSeleccionado() == 1) {
                if (tablero.getListaFichas().getLast().getValor1() == f.getValor1())
                    f.setSeleccionado(-1);
                else if (tablero.getListaFichas().getLast().getValor1() == f.getValor2())
                    f.setSeleccionado(1);
                else {
                    turno(j);
                    return;
                }
                j.borrarFicha(aux);
                tablero.insertarDerecha(f);
                return;
            }
            if (tablero.getListaFichas().getLast().getSeleccionado() == -1) {
                if (tablero.getListaFichas().getLast().getValor2() == f.getValor1())
                    f.setSeleccionado(-1);
                else if (tablero.getListaFichas().getLast().getValor2() == f.getValor2())
                    f.setSeleccionado(1);
                else {
                    turno(j);
                    return;
                }
                j.borrarFicha(aux);
                tablero.insertarDerecha(f);
                return;
            }
        }
        if (aux2 == 0) {
            if (tablero.getListaFichas().getFirst().getSeleccionado() == 0
                    || tablero.getListaFichas().getFirst().getSeleccionado() == 1) {
                if (tablero.getListaFichas().getFirst().getValor1() == f.getValor1())
                    f.setSeleccionado(-1);
                else if (tablero.getListaFichas().getFirst().getValor1() == f.getValor2())
                    f.setSeleccionado(1);
                else {
                    turno(j);
                    return;
                }
                j.borrarFicha(aux);
                tablero.insertarIzquierda(f);
                return;
            }
            if (tablero.getListaFichas().getFirst().getSeleccionado() == -1) {
                if (tablero.getListaFichas().getFirst().getValor2() == f.getValor1())
                    f.setSeleccionado(-1);
                else if (tablero.getListaFichas().getFirst().getValor2() == f.getValor2())
                    f.setSeleccionado(1);
                else {
                    turno(j);
                    return;
                }
                j.borrarFicha(aux);
                tablero.insertarIzquierda(f);
                return;
            }
        }

    }

    public int obtenerJugadorInicial() {
        for (int i = 0; i < jugadores.length; i++)
            for (int j = 0; j < jugadores[i].getFichas().size(); j++)
                if (jugadores[i].getFichas().get(j).getValor1() == 6
                        && jugadores[i].getFichas().get(j).getValor2() == 6)
                    return i;
        return -1;
    }

    public void primerPieza(Jugador j) {
        Scanner sc = new Scanner(System.in);
        System.out.println("----------------------------" + j.getNombre()
                + " fichas----------------------------------------------");
        j.verFichas();
        System.out.println("----------------------------" + j.getNombre()
                + " fichas----------------------------------------------");

        System.out.println("Inserte la mula del 6");
        int aux = sc.nextInt();
        FichaDomino f = j.getFichas().get(aux);
        j.borrarFicha(aux);
        tablero.insertarDerecha(f);
    }

    public Jugador[] getJugadores() {
        return jugadores;
    }

    public TableroDomino getTablero() {
        return tablero;
    }

    
}
