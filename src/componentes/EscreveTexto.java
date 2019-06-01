/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package componentes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 *
 * @author Felipe Hiroshi
 */
public class EscreveTexto {
    double x;
    double y;

    public void clickDoMouse(TextField txtTexto, GraphicsContext areaDePintura, MouseEvent event){
        String texto;
        txtTexto.setVisible(true);
        if(txtTexto.getText().equals("")){
            x = event.getX();
            y = event.getY();
        }
        txtTexto.setTranslateX(x);
        txtTexto.setTranslateY(y);
        texto = txtTexto.getText();
        areaDePintura.fillText(texto, x, y+20);
    }
    
    public void soltarClickMouse(TextField txtTexto, GraphicsContext areaDePintura, MouseEvent event){
        if(!txtTexto.getText().equals(""))
            txtTexto.setVisible(false);
        txtTexto.clear();  
    }
    
}