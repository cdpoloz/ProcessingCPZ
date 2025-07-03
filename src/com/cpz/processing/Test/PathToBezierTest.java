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

package com.cpz.processing.Test;

import com.cpz.processing.Bean.Bezier;
import com.cpz.processing.UI.Label;
import processing.core.PApplet;

import java.util.List;

import static com.cpz.processing.Util.Constantes.*;

/**
 * @author CPZ
 */
public class PathToBezierTest extends PApplet {

    private Bezier bezier;
    private Label lbl;

    @Override
    public void settings() {
        size(1200, 675, P2D);
        smooth(8);

        lbl = new Label();
        lbl.setSketch(this);
        lbl.setDisplay(true);
        lbl.setAlineaX(AlineaX.IZQ);
        lbl.setAlineaY(AlineaY.SUP);
        lbl.setColorTexto(color(255));
        lbl.setPos(0.04f * width, 0.05f * height);
        lbl.setTamTexto(22);
    }

    @Override
    public void setup() {
        surface.setTitle("cpzProcessing - PathToBezier");
        frameRate(60);
        textFont(createFont("Consolas", 22));

        bezier = new Bezier();
        bezier.setSketch(this);
        bezier.setAnchoStroke(2);
        bezier.setColorStroke(MAGENTA);
    }

    @Override
    public void draw() {
        // update
        bezier.update();
        updateLabel();
        // draw
        dibujarFondo();
        bezier.draw();
        lbl.draw();
    }

    private void updateLabel() {
        String s = "";
        s += "Barra espaciadora → conmutar vista de guías\n";
        s += "Tecla '+'         → agregar curva adicional\n";
        s += "Tecla '-'         → quitar última curva adicional\n";
        s += "Backspace         → reiniciar curva\n";
        s += "\n";
        s += "Segmentos         → " + bezier.getCantidadCurvas() + "\n";
        s += "Longitud total    → " + String.format("%,.2f", bezier.getLongitudTotal()) + "\n";
        lbl.setTexto(s);
    }

    public void dibujarFondo() {
        background(32);
    }

    public void mouseDragged() {
        bezier.mouseDragged();
    }

    @Override
    public void keyReleased() {
        switch (keyCode) {
            case BARRA_ESPACIADORA -> bezier.setDibujarGuia(!bezier.isDibujarGuia());
            case TECLA_MAS -> bezier.agregarCurvaAdicional();
            case TECLA_MENOS -> bezier.eliminarCurvaAdicional();
            case BACKSPACE -> bezier.reset();
        }
    }

}
