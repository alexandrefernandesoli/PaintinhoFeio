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
public class DesenhaRetangulo implements Ferramentas {
    Rectangle rect = new Rectangle();

    @Override
    public void clickDoMouse(GraphicsContext gc, MouseEvent event) {
        rect.setTranslateX(event.getX());
        rect.setTranslateY(event.getY());
        rect.setX(event.getX());
        rect.setY(event.getY());
    }

    @Override
    public void arrastoDoMouse(GraphicsContext gc, MouseEvent event) {
        rect.setWidth(Math.abs(event.getX() - rect.getTranslateX()));
        rect.setHeight(Math.abs(event.getY() - rect.getTranslateY()));
    }

    @Override
    public void soltarClickMouse(GraphicsContext gc, MouseEvent event) {
        if (event.getX() - rect.getTranslateX() < 0 || event.getY() - rect.getTranslateY() < 0) {
            rect.setX(event.getX());
            rect.setY(event.getY());
        }
        gc.fillRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }

}
