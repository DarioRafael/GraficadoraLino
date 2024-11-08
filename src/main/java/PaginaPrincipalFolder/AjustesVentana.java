package PaginaPrincipalFolder;

import java.awt.Dimension;

public class AjustesVentana {
    private Dimension windowSize;

    public AjustesVentana() {
        this.windowSize = new Dimension(800, 500);
    }

    public Dimension getWindowSize() {
        return windowSize;
    }

    public void setWindowSize(Dimension windowSize) {
        this.windowSize = windowSize;
    }
}