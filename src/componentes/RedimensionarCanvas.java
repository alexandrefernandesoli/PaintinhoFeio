package componentes;

import javafx.scene.canvas.Canvas;

/**
 * @author Felipe Hiroshi
 */
public class RedimensionarCanvas extends Canvas {

    @Override
    public boolean isResizable() {
        return true;
    }

    @Override
    public double prefWidth(double height) {
        return getWidth();
    }

    @Override
    public double prefHeight(double width) {
        return getHeight();
    }
}
