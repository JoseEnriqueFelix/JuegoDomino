public class Main {
    public static void main(String[] args) {
        Vista vista = new Vista();
        Modelojuego juego = new Modelojuego();
        Controlador controlador = new Controlador(juego, vista);
        controlador.comenzarJuego();
        controlador.setEscuchadoresBotonesJuego();
    }
}
