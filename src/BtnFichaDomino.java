import javax.swing.*;

public class BtnFichaDomino extends JButton {
    private FichaDomino fichaAsociada;
    private String claveImagenAsociada;
    private boolean esMula;

    public BtnFichaDomino(FichaDomino fichaAsociada) {
        this.fichaAsociada = fichaAsociada;
        claveImagenAsociada = "" + fichaAsociada.getValor1() + fichaAsociada.getValor2();
        if (fichaAsociada.getValor1() == fichaAsociada.getValor2())
            esMula = true;
        else
            esMula = false;
    }

    public FichaDomino getFichaAsociada() {
        return fichaAsociada;
    }

    public String getClaveImagenAsociada() {
        return claveImagenAsociada;
    }

    public boolean getEsMula() {
        return esMula;
    }
}
