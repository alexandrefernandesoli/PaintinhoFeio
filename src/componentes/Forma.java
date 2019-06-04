/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package componentes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

/**
 * @author Felipe Hiroshi
 */
abstract class Forma implements Ferramentas {
    double coordenadaX;
    double coordenadaY;
    double largura;
    double altura;

    public void clickDoMouse(GraphicsContext gc, MouseEvent event) {
        coordenadaX = event.getX();
        coordenadaY = event.getY();
    }

    public void arrastoDoMouse(GraphicsContext gc, MouseEvent event) {
        largura = Math.abs(event.getX() - coordenadaX);
        altura = Math.abs(event.getY() - coordenadaY);
    }

    void posicao(MouseEvent event) {
        if (event.getX() < coordenadaX)
            coordenadaX = event.getX();
        if (event.getY() < coordenadaY)
            coordenadaY = event.getY();
    }

    void finaliza() {
        altura = 0;
        largura = 0;
    }
}
