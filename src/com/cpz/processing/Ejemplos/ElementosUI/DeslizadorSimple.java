/*
 * Copyright 2025 Carlos Polo Zamora - CPZ - CePeZeta
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * cpzProcessing - DeslizadorSimple
 *
 * Ejemplo de un control deslizante personalizado con elementos interactivos.
 *
 * Este sketch muestra el uso de la clase Deslizador, que combina una barra de valor
 * continuo y un manejador tipo switch en un solo objeto gráfico. Ambos componentes
 * pueden personalizarse de forma independiente en forma, color y comportamiento.
 *
 * El deslizador puede controlarse usando la rueda del mouse o arrastrando con el botón
 * izquierdo, permitiendo una interacción fluida y precisa. La apariencia visual del control
 * puede adaptarse fácilmente mediante archivos SVG personalizados.
 *
 */


package com.cpz.processing.Ejemplos.ElementosUI;

import com.cpz.processing.UI.Deslizador;
import com.cpz.processing.UI.Label;
import com.cpz.processing.Util.Constantes.*;
import org.jetbrains.annotations.NotNull;
import processing.core.PApplet;
import processing.event.MouseEvent;

import java.io.File;

import static com.cpz.processing.Util.Constantes.CIEN;

/**
 * @author CPZ
 */
public class DeslizadorSimple extends PApplet {

    private Deslizador deslizador;
    private Label lblValor, lblInfo;

    @Override
    public void settings() {
        size(1200, 600, P2D);
        smooth(8);
    }

    @Override
    public void setup() {
        surface.setTitle("Deslizador simple");
        frameRate(60);
        deslizador = new Deslizador();
        deslizador.setSketch(this);
        deslizador.setPos(100, 100);
        deslizador.setMedidasBarra(400, 40);
        deslizador.setMedidasHandle(60, 60);
        deslizador.setColoresBarra(color(80, 87, 89), color(128, 0, 51), color(255, 0, 102));
        deslizador.setColoresHandle(color(255, 204, 0), color(255));
        deslizador.setOrientacion(Orientacion.HORIZONTAL);
        deslizador.setModo(BarraModo.INTERACTIVO);
        deslizador.setShapeFondo(loadShape("data" + File.separator + "img" + File.separator + "cuadrado.svg"));
        deslizador.setShapeHandle(loadShape("data" + File.separator + "img" + File.separator + "sun.svg"));

        lblValor = new Label();
        lblValor.setSketch(this);
        lblValor.setPos(300, 160);
        lblValor.setAlineaX(AlineaX.CENTRO);
        lblValor.setAlineaY(AlineaY.SUP);
        lblValor.setDisplay(true);
        lblValor.setColorTexto(color(255));
        lblValor.setTamTexto(22);
        lblValor.setTexto("Título");

        lblInfo = new Label();
        lblInfo.setSketch(this);
        lblInfo.setPos(width * 0.45f, height * 0.15f);
        lblInfo.setAlineaX(AlineaX.IZQ);
        lblInfo.setAlineaY(AlineaY.SUP);
        lblInfo.setDisplay(true);
        lblInfo.setColorTexto(color(255));
        lblInfo.setTamTexto(22);
        StringBuilder sb = new StringBuilder();
        sb.append("La clase Deslizador combina el uso de una Barra y un Switch\n");
        sb.append("en un solo objeto. Cada uno de estos elementos se puede\n");
        sb.append("personalizar dependiendo de lo que se necesite al momento.\n\n");
        sb.append("Tener en cuenta la orientación de la barra para poder ajustar\n");
        sb.append("las dimensiones acorde a ésta.\n\n");
        sb.append("Este deslizador se puede controlar ya sea usando la rueda del\n");
        sb.append("mouse o haciendo clic y arrastrando el mouse gracias a los métodos\n");
        sb.append("mouseWheel() y mouseDragged().");
        lblInfo.setTexto(sb);
    }

    @Override
    public void draw() {
        background(32);
        deslizador.update();
        lblValor.setTexto(String.format("%.0f%%", deslizador.getValor().multiply(CIEN)));

        deslizador.draw();
        lblValor.draw();
        lblInfo.draw();
    }

    @Override
    public void mouseDragged() {
        if (mouseButton == LEFT && deslizador.isHovering()) deslizador.mouseDragged();
    }

    @Override
    public void mouseWheel(@NotNull MouseEvent event) {
        deslizador.mouseWheel(event.getCount());
    }

}
