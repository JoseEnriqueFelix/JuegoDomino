public class AlgoAuxAcomodo {
    private static BtnFichaDomino[][] matrizDeAcomodo = {
            { null, null, null, null, null, null, null },
            { null, null, null, null, null, null, null },
            { null, null, null, null, null, null, null },
            { null, null, null, null, null, null, null },
            { null, null, null, null, null, null, null },
            { null, null, null, null, null, null, null },
            { null, null, null, null, null, null, null },
            { null, null, null, null, null, null, null },
            { null, null, null, null, null, null, null }
    };

    private static int posDerechaX;
    private static int posDerechaY;
    private static int posIzquierdaY;
    private static int posIzquierdaX;

    public static int getPosDerechaX() {
        return posDerechaX;
    }

    public static int getPosDerechaY() {
        return posDerechaY;
    }

    public static int getPosIzquierdaY() {
        return posIzquierdaY;
    }

    public static int getPosIzquierdaX() {
        return posIzquierdaX;
    }

    public static BtnFichaDomino[][] getMatriz() {
        return matrizDeAcomodo;
    }

    public static void setMatrizPrimero(BtnFichaDomino f) {
        matrizDeAcomodo[4][3] = f;
        posDerechaX = 4;
        posDerechaY = 4;
        posIzquierdaY = 4;
        posIzquierdaX = 2;
    }

    public static void setMatrizDerecha(BtnFichaDomino f) {
        matrizDeAcomodo[posDerechaY][posDerechaX] = f;
        posDerechaX++;
        if (posDerechaX > 6) {
            posDerechaX = 6;
            posDerechaY--;
        }
    }

    public static void setMatrizIzquierda(BtnFichaDomino f) {
        matrizDeAcomodo[posIzquierdaY][posIzquierdaX] = f;
        posIzquierdaX--;
        if (posIzquierdaX < 0) {
            posIzquierdaX = 0;
            posIzquierdaY++;
        }
    }

    public static void imprimirMatriz() {
        for (int i = 0; i < matrizDeAcomodo.length; i++) {
            for (int j = 0; j < matrizDeAcomodo[i].length; j++) {
                System.out.print(matrizDeAcomodo[i][j] + " ");
            }
            System.out.println();
        }
    }
}
