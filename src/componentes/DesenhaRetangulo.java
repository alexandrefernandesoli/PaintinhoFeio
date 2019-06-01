/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package componentes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

/**
 * @author Felipe Hiroshi
 */
public class DesenhaRetangulo extends Forma implements Ferramentas {


     @Override
    public void clickDoMouse(GraphicsContext gc, MouseEvent event) {
        coordenadaX = event.getX();
        coordenadaY = event.getY();
    }

    @Override
    public void arrastoDoMouse(GraphicsContext gc, MouseEvent event) {
        largura = Math.abs(event.getX() - coordenadaX);
        altura = Math.abs(event.getY() - coordenadaY);
    }

    @Override
    public void soltarClickMouse(GraphicsContext gc, MouseEvent event) {
        if (event.getX() < coordenadaX)
            coordenadaX = event.getX();
        if (event.getY() < coordenadaY)
            coordenadaY = event.getY();

        gc.fillRect(coordenadaX, coordenadaY, largura, altura);
        altura = 0;
        largura = 0;
    }

}
