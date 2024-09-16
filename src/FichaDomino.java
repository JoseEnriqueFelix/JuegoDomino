public class FichaDomino {
    private int valor1, valor2;
    private int seleccionado; // 1 valor1, -1 valor2, 0 ninguno
    private boolean esVisible;
     
    public FichaDomino(int valor1, int valor2){
        this.valor1 = valor1;
        this.valor2 = valor2;
        seleccionado = 0;
        esVisible = true;
    }

    public boolean getEsVisible(){
        return esVisible;
    }

    public void setVisible(boolean b){
        esVisible = b;
    }

    public int getValor1() {
        return valor1;
    }

    public int getValor2() {
        return valor2;
    }

    public int getSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(int seleccionado) {
        this.seleccionado = seleccionado;
    }

    public String toString(){
        String direccion;
        if (seleccionado == 1)
            direccion = "valor1";
        else if (seleccionado == -1)
            direccion = "valor2";
        else 
            direccion = "ninguno";
        return "{valor1 = " + valor1 + ", valor2 = " + valor2 + ", seleccionado = " + direccion + "}"; 
    }
}
