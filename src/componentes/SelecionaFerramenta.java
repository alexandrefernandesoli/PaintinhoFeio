package componentes;

import javafx.SeguraElementos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

public class SelecionaFerramenta{
    private DesenhaCaneta caneta = new DesenhaCaneta();
    private DesenhaRetangulo retangulo = new DesenhaRetangulo();
    private DesenhaCirculo circulo = new DesenhaCirculo();
    private EscreveTexto texto = new EscreveTexto();
    private DesenhaReta reta = new DesenhaReta();
    private PreenchimentoBalde balde = new PreenchimentoBalde();


    public void clickDoMouse(GraphicsContext areaDePintura, SeguraElementos elementos, MouseEvent evento) {
        areaDePintura.setFill(elementos.getCor());
        areaDePintura.setStroke(elementos.getCor());
        areaDePintura.setLineWidth(elementos.getTamanho());

        if (elementos.getFerramenta().getUserData() == "caneta") {
            caneta.clickDoMouse(areaDePintura, evento);
        } else if (elementos.getFerramenta().getUserData() == "retangulo") {
            retangulo.clickDoMouse(areaDePintura, evento);
        } else if (elementos.getFerramenta().getUserData() == "circulo") {
            circulo.clickDoMouse(areaDePintura, evento);
        } else if (elementos.getFerramenta().getUserData() == "texto") {
            texto.clickDoMouse(elementos.getTexto(), areaDePintura, evento);
        } else if (elementos.getFerramenta().getUserData() == "reta") {
            reta.clickDoMouse(areaDePintura, evento);
        } else if (elementos.getFerramenta().getUserData() == "balde") {
            balde.clickDoMouse(areaDePintura, evento);
        }
    }

    public void arrastoDoMouse(GraphicsContext areaDePintura, SeguraElementos elementos, MouseEvent evento) {
        if (elementos.getFerramenta().getUserData() == "caneta") {
            caneta.arrastoDoMouse(areaDePintura, evento);
        }
        if (elementos.getFerramenta().getUserData() == "borracha") {
            double size = elementos.getTamanho();
            areaDePintura.clearRect(evento.getX() - size / 2, evento.getY() - size / 2, size, size);
        } else if (elementos.getFerramenta().getUserData() == "retangulo") {
            retangulo.arrastoDoMouse(areaDePintura, evento);
        } else if (elementos.getFerramenta().getUserData() == "circulo") {
            circulo.arrastoDoMouse(areaDePintura, evento);
        } else if (elementos.getFerramenta().getUserData() == "reta") {
            reta.arrastoDoMouse(areaDePintura, evento);
        } else if (elementos.getFerramenta().getUserData() == "balde") {
            balde.arrastoDoMouse(areaDePintura, evento);
        }
    }

    public void soltarClickMouse(GraphicsContext areaDePintura, SeguraElementos elementos, MouseEvent evento) {
        if (elementos.getFerramenta().getUserData() == "caneta") {
            caneta.soltarClickMouse(areaDePintura, evento);
        } else if (elementos.getFerramenta().getUserData() == "retangulo") {
            retangulo.soltarClickMouse(areaDePintura, evento);
        } else if (elementos.getFerramenta().getUserData() == "circulo") {
            circulo.soltarClickMouse(areaDePintura, evento);
        } else if (elementos.getFerramenta().getUserData() == "texto") {
            texto.soltarClickMouse(elementos.getTexto(), areaDePintura, evento);
        } else if (elementos.getFerramenta().getUserData() == "reta") {
            reta.soltarClickMouse(areaDePintura, evento);
        } else if (elementos.getFerramenta().getUserData() == "balde") {
            balde.soltarClickMouse(areaDePintura, evento);
        }
    }
}
