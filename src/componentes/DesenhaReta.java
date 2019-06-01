/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package componentes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Felipe Hiroshi
 */
public class DesenhaReta implements Ferramentas{
    double x1;
    double y1;
    double x2;
    double y2;
    
    @Override
    public void clickDoMouse(GraphicsContext gc, MouseEvent event) {
        x1 = event.getX();
        y1 = event.getY();
    }

    @Override
    public void arrastoDoMouse(GraphicsContext gc, MouseEvent event) {
      
    }

    @Override
    public void soltarClickMouse(GraphicsContext gc, MouseEvent event) {
        x2 = event.getX();
        y2 = event.getY();
        gc.strokeLine(x1, y1, x2, y2);
    }
    
}
