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

import com.cpz.processing.Bean.Fluido;
import com.cpz.processing.UI.Barra;
import com.cpz.processing.UI.Deslizador;
import com.cpz.processing.UI.Switch;
import com.cpz.processing.Util.Constantes.*;
import com.cpz.processing.Util.Tools;
import processing.core.PApplet;
import processing.core.PVector;
import processing.event.MouseEvent;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.cpz.processing.Util.Constantes.BARRA_ESPACIADORA;

/**
 * @author CPZ
 */
public class DeslizadorTest extends PApplet {

    private Deslizador deslizador;

    // CONFIGURACIÓN PREVIA A SETUP ********************************************
    @Override
    public void settings() {
        size(1200, 600, P2D);
        smooth(8);
    }

    // CONFIGURACIÓN DE VARIABLES **********************************************
    @Override
    public void setup() {
        surface.setTitle("Fluido simple");
        frameRate(60);
        Object img = loadImage("data" + File.separator + "img" + File.separator + "circuloBlur.png");
        deslizador = new Deslizador();
        deslizador.setSketch(this);

        Barra bar = new Barra();
        bar.setSketch(this);
        bar.setDisplay(true);
        bar.setEditable(true);
        bar.setMedidas(100, 20);
        bar.setColorInicial(color(64, 164, 255));
        bar.setColorFinal(color(64, 255, 128));
        bar.setColorHover(color(255));
        bar.setColorOff(color(80, 87, 89));
        bar.setD(new BigDecimal("0.01"));
        bar.setOrientacion(Orientacion.HORIZONTAL);
        bar.setModo(BarraModo.INTERACTIVO);
        bar.setShape(loadShape("data" + File.separator + "img" + File.separator + "cuadrado.svg"));
        bar.setPos(100, 100);
        bar.update();

        Switch sw = new Switch();
        sw.setSketch(this);
        sw.setDisplay(true);
        sw.setEditable(true);
        sw.setShowHover(true);
        if (bar.getOrientacion() == Orientacion.VERTICAL) {
            sw.setAlto(bar.getAltoMax() * 0.05f);
            sw.setAncho(bar.getAnchoMax() * 1.5f);
        } else if (bar.getOrientacion() == Orientacion.HORIZONTAL) {
            sw.setAlto(bar.getAltoMax() * 1.5f);
            sw.setAncho(bar.getAnchoMax() * 0.05f);
        }
        sw.setColorHover(color(255));
        sw.setColorOff(color(120, 127, 129));
        sw.setColorOn(color(64, 255, 128));
        sw.setShape(loadShape("data" + File.separator + "img" + File.separator + "cuadrado.svg"));
        sw.setOn(false);

        deslizador.setBar(bar);
        deslizador.setSw(sw);
    }

    // PROGRAMA PRINCIPAL ******************************************************
    @Override
    public void draw() {
        background(32);
        deslizador.update();
        deslizador.draw();
    }

    // INTERRUPCIONES DE PROCESSING ********************************************
    @Override
    public void mouseReleased() {
    }

    @Override
    public void mouseDragged() {
        if (mouseButton == LEFT && deslizador.isHovering()) deslizador.mouseDragged();
    }

    @Override
    public void mouseWheel(MouseEvent event) {
        deslizador.mouseWheel(event.getCount());
    }

    @Override
    public void keyReleased() {
        switch (key) {

        }
        switch (keyCode) {
            case BARRA_ESPACIADORA -> {

            }
            /*
            case FLECHA_ARRIBA -> fluido2.actualizarVelocidadNoisePorDiferencial("+");
            case FLECHA_ABAJO -> fluido2.actualizarVelocidadNoisePorDiferencial("-");
            case FLECHA_DERECHA -> fluido2.setVelocidadNoise(1f);
            case FLECHA_IZQUIERDA -> fluido2.setVelocidadNoise(0);
             */
        }
    }

}
