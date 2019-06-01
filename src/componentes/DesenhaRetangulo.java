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
public class DesenhaRetangulo extends Forma implements Ferramentas {


     @Override
    public void clickDoMouse(GraphicsContext gc, MouseEvent event) {
        coordenadas(event);
    }

    @Override
    public void arrastoDoMouse(GraphicsContext gc, MouseEvent event) {
        tamanho(event);
    }

    @Override
    public void soltarClickMouse(GraphicsContext gc, MouseEvent event) {
        posicao(event);
        gc.fillRect(coordenadaX, coordenadaY, largura, altura);
        finaliza();
    }

}
