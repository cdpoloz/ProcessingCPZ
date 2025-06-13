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
 * cpzProcessing - SimpleUI
 *
 * Ejemplo de una interfaz gráfica con elementos interactivos simples.
 *
 * Este sketch muestra el uso de etiquetas, barras y switches personalizados,
 * temporizadores, ruido Perlin para generar movimiento fluido para el punto
 * animado y control de eventos de entrada usando el teclado y ratón.
 *
 * Tanto los objetos de tipo Barra o Switch podrían usar archivos de imágenes
 * personalizados en vez de los archivos incluidos por defecto. Se recomienda
 * el uso de archivos de tipo 'Plain SVG' aunque también se pueden usar archivos
 * SVG exportados de Inkscape, Adobe Illustrator u otros. La apariencia visual
 * de los controles es completamente personalizable mediante el reemplazo de estos
 * archivos.
 *
 * Se incluye el paquete 'Input' en el que se encuentran las implementaciones
 * de las interfaces InputMouse e InputTeclado. Cada ejemplo tiene su propio
 * paquete 'Input' porque las implementaciones de los eventos de teclado y
 * ratón pueden variar dependiendo del sketch de ejemplo.
 */

package com.cpz.processing.Ejemplos.SimpleUI;

import com.cpz.processing.Bean.PerlinVector;
import com.cpz.processing.Bean.Timer;
import com.cpz.processing.Ejemplos.SimpleUI.Input.Mouse;
import com.cpz.processing.Ejemplos.SimpleUI.Input.Teclado;
import com.cpz.processing.UI.Barra;
import com.cpz.processing.UI.Label;
import com.cpz.processing.UI.Switch;
import processing.core.PApplet;
import processing.core.PVector;
import processing.event.MouseEvent;

import java.io.File;
import java.math.BigDecimal;

import static com.cpz.processing.Util.Constantes.*;

public class SimpleUI extends PApplet {

    private Barra bar;
    private Label lblTitulo1, lblTitulo2, lblBar, lblSw, lblSwInd;
    private Switch sw, swInd;
    private Timer timerSwInd;
    private PerlinVector perlin;
    private Mouse mouse;
    private Teclado teclado;

    @Override
    public void settings() {
        size(1200, 600, P2D);
        smooth(8);
    }

    @Override
    public void setup() {
        surface.setTitle("ProcessingCPZ - Ejemplos");
        frameRate(60);

        mouse = new Mouse();
        teclado = new Teclado();
        lblTitulo1 = new Label();
        lblTitulo1.setSketch(this);
        lblTitulo1.setPos(50, 50);
        lblTitulo1.setDisplay(true);
        lblTitulo1.setAlineaX(AlineaX.IZQ);
        lblTitulo1.setAlineaY(AlineaY.SUP);
        lblTitulo1.setColorTexto(color(255));
        lblTitulo1.setTamTexto(18);
        lblTitulo1.setTexto("Esta es una etiqueta estática");

        lblTitulo2 = new Label();
        lblTitulo2.setSketch(this);
        lblTitulo2.setPos(width * 0.5f + 50, 50);
        lblTitulo2.setDisplay(true);
        lblTitulo2.setAlineaX(AlineaX.IZQ);
        lblTitulo2.setAlineaY(AlineaY.SUP);
        lblTitulo2.setColorTexto(color(255));
        lblTitulo2.setTamTexto(18);
        lblTitulo2.setTexto("La barra de la izquierda controla la velocidad del punto");

        bar = new Barra();
        bar.setSketch(this);
        bar.setDisplay(true);
        bar.setEditable(true);
        bar.setShowHover(true);
        bar.setMedidas(20, 250);
        bar.setColorInicial(color(64, 164, 255));
        bar.setColorFinal(color(64, 255, 128));
        bar.setColorHover(color(255));
        bar.setColorOff(color(80, 87, 89));
        bar.setD(new BigDecimal("0.01"));
        bar.setOrientacion(Orientacion.VERTICAL);
        bar.setModo(BarraModo.INTERACTIVO);
        bar.setShape(loadShape("data" + File.separator + "img" + File.separator + "cuadrado.svg"));
        bar.setPos(100, 100);

        lblBar = new Label();
        lblBar.setSketch(this);
        lblBar.setPos(bar.getCentro().add(new PVector(0, bar.getAltoMax() * 0.55f)));
        lblBar.setDisplay(true);
        lblBar.setAlineaX(AlineaX.CENTRO);
        lblBar.setAlineaY(AlineaY.SUP);
        lblBar.setColorTexto(color(255));
        lblBar.setTamTexto(18);

        sw = new Switch();
        sw.setSketch(this);
        sw.setDisplay(true);
        sw.setEditable(true);
        sw.setShowHover(true);
        sw.setAlto(30);
        sw.setAncho(30);
        sw.setColorHover(color(255));
        sw.setColorOff(color(80, 87, 89));
        sw.setColorOn(color(64, 255, 128));
        sw.setPos(250, bar.getCentro().y - sw.getAlto() * 0.5f);
        sw.setShape(loadShape("data" + File.separator + "img" + File.separator + "circulo.svg"));
        sw.setOn(false);

        lblSw = new Label();
        lblSw.setSketch(this);
        lblSw.setPos(sw.getCentro().x, sw.getCentro().y + sw.getAlto() * 0.8f);
        lblSw.setDisplay(true);
        lblSw.setAlineaX(AlineaX.CENTRO);
        lblSw.setAlineaY(AlineaY.SUP);
        lblSw.setColorTexto(color(255));
        lblSw.setTamTexto(18);

        swInd = new Switch();
        swInd.setSketch(this);
        swInd.setDisplay(true);
        swInd.setEditable(false);
        swInd.setShowHover(false);
        swInd.setAlto(30);
        swInd.setAncho(30);
        swInd.setColorHover(color(255));
        swInd.setColorOff(color(80, 87, 89));
        swInd.setColorOn(color(64, 255, 128));
        swInd.setPos(450, sw.getCentro().y - swInd.getAncho() * 0.5f);
        swInd.setShape(loadShape("data" + File.separator + "img" + File.separator + "circulo.svg"));
        swInd.setOn(false);

        timerSwInd = new Timer();
        timerSwInd.setSketch(this);
        timerSwInd.setPeriodo(500);
        timerSwInd.iniciar();

        lblSwInd = new Label();
        lblSwInd.setSketch(this);
        lblSwInd.setPos(swInd.getCentro().x, swInd.getCentro().y + swInd.getAlto() * 0.8f);
        lblSwInd.setDisplay(true);
        lblSwInd.setAlineaX(AlineaX.CENTRO);
        lblSwInd.setAlineaY(AlineaY.SUP);
        lblSwInd.setColorTexto(color(255));
        lblSwInd.setTamTexto(18);
        lblSwInd.setTexto("Este indicador se\nactiva cuando el\ntimer llegó al\nperiodo indicado\n" + timerSwInd.getPeriodo() + " ms");

        perlin = new PerlinVector();
        perlin.setSketch(this);
        perlin.setVelocidad(0.0035f, 0.0035f);
    }

    @Override
    public void draw() {
        // update
        bar.update();
        sw.update();
        swInd.update();
        timerSwInd.update();
        perlin.update();
        swInd.setOn(timerSwInd.periodoOnOff());
        BigDecimal valorBar = bar.getValor();
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%.0f%%", valorBar.multiply(CIEN, MATH_CONTEXT))).append("\n\n");
        sb.append("Con esta barra\n").append("puedes usar\n").append("mouseWheel(),\nmouseDragged()\no\nmouseReleased()\n(botón derecho)");
        lblBar.setTexto(sb);
        lblSw.setTexto("Este es un\nswitch simple\nque puedes\nconmutar con\nmouseReleased()\no la barra\nespaciadora\n\n" + (sw.isOn() ? "On" : "Off"));
        float velocidadPunto = map(valorBar.floatValue(), bar.getValorMin().floatValue(), bar.getValorMax().floatValue(), 0.0035f, 0.02f);
        perlin.setVelocidad(velocidadPunto);
        float x = map(perlin.get().x, 0, 1, width * 0.5f, width);
        float y = map(perlin.get().y, 0, 1, 0, height);
        // draw
        background(32);
        bar.draw();
        sw.draw();
        swInd.draw();
        lblBar.draw();
        lblTitulo1.draw();
        lblTitulo2.draw();
        lblSw.draw();
        lblSwInd.draw();
        strokeWeight(1);
        stroke(255);
        line(width * 0.5f, 0, width * 0.5f, height);
        fill(color(64, 255, 128));
        noStroke();
        ellipse(x, y, 10, 10);
    }

    @Override
    public void mouseReleased() {
        mouse.mouseReleased(mouseButton, sw);
        mouse.mouseReleased(mouseButton, bar);
    }

    @Override
    public void mouseWheel(MouseEvent event) {
        mouse.mouseWheel(event.getCount(), bar);
    }

    @Override
    public void mouseDragged() {
        mouse.mouseDragged(mouseButton, bar);
    }

    @Override
    public void keyReleased() {
        teclado.keyReleased(key, keyCode, sw);
    }

}
