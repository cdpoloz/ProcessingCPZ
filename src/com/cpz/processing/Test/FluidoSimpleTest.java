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
import com.cpz.processing.Util.Tools;
import processing.core.PApplet;
import processing.core.PVector;
import processing.event.MouseEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.cpz.processing.Util.Constantes.*;

/**
 * @author CPZ
 */
public class FluidoSimpleTest extends PApplet {

    private PVector p0, p1, p2;
    private Fluido fluido;

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
        float distMin = 0f;
        float distMax = 2.236068f;

        p0 = new PVector(3354.964f / 3840f, 1722.936f / 1986f);
        p1 = new PVector(3354.964f / 3840f, 1668.545f / 1986f);
        //p2 = new PVector(3479.966f / 3840f, 1834.331f / 1986f);
        PVector normal01 = Tools.calcularNormal(p0, p1);
        //PVector normal12 = Tools.calcularNormal(p1, p2);
        float dist01 = PVector.dist(p0, p1);
        //float dist12 = PVector.dist(p1, p2);
        float distTotal = dist01;// + dist12;
        int pasosTotal = (int) map(distTotal, distMin, distMax, 50, 800);
        int pasos01 = (int) (dist01 * pasosTotal / distTotal);
        //int pasos12 = (int) (dist12 * pasosTotal / distTotal);
        List<PVector> posiciones01 = Tools.calcularPosicionesEnRecta(p0, p1, pasos01);
        //List<PVector> posiciones12 = Tools.calcularPosicionesEnRecta(p1, p2, pasos12);
        List<PVector> normales01 = new ArrayList<>();
        List<PVector> normales12 = new ArrayList<>();
        for (int i = 0; i < posiciones01.size(); i++) {
            normales01.add(normal01);
        }
        /*
        for (int i = 0; i < posiciones12.size(); i++) {
            normales12.add(normal12);
        }
        */
        List<PVector> posiciones = new ArrayList<>();
        posiciones.addAll(posiciones01);
        //posiciones.addAll(posiciones12);
        List<PVector> normales = new ArrayList<>();
        normales.addAll(normales01);
        normales.addAll(normales12);
        fluido = new Fluido();
        fluido.setSketch(this);
        fluido.setCodigo("f1");
        fluido.setImg(img);
        fluido.setCantidadMovilesMax(100);
        fluido.setRangDeltaIndPos(1, 3);
        fluido.setColorRelleno(color(255, 0, 100));
        fluido.setRangoAlfaMovil(0.01f, 0.80f);
        fluido.setRangoVelocidadNoise(0.055f, 0.200f, 0.005f);
        fluido.setPeriodo(15);
        fluido.setDesviacionMax(0.005f);
        fluido.setRangoDiametro(0.01f, 0.015f);
        fluido.setPosiciones(posiciones);
        fluido.setNormales(normales);
        fluido.setRun(true);
        for (int i = 0; i < posiciones.size(); i++) {
            PVector p = posiciones.get(i);
            PVector n = normales.get(i);
            String s = String.format("%,6f", p.x) + "," + String.format("%,6f", p.y) + "," + String.format("%,6f", n.x) + "," + String.format("%,6f", n.y);
            System.out.println(s);
        }
        System.out.println();
        System.out.println(posiciones.size());

    }

    // PROGRAMA PRINCIPAL ******************************************************
    @Override
    public void draw() {
        // update
        fluido.update();
        // color fondo fluido 1
        float alfaFondoMin = 0.1f;
        float alfaFondoMax = 1f;
        float alfaFondo1 = map(fluido.getFactorLlenado(), 0, 1, alfaFondoMax, alfaFondoMin);
        int colorOn = color(255, 0, 120, alfaFondo1 * 255);
        int colorOff = color(80, 87, 89, alfaFondo1 * 255);
        int colorFondo = lerpColor(colorOff, colorOn, fluido.getFactorLlenado());
        // draw
        background(32);
        strokeWeight(10);
        stroke(colorFondo);
        line(p0.x * width, p0.y * height, p1.x * width, p1.y * height);
        //line(p1.x * width, p1.y * height, p2.x * width, p2.y * height);
        fluido.draw();
    }

    // INTERRUPCIONES DE PROCESSING ********************************************
    @Override
    public void mouseReleased() {
        if (mouseButton == RIGHT) fluido.conmutarEstado();
    }

    @Override
    public void mouseWheel(MouseEvent event) {

    }

    @Override
    public void keyReleased() {
        switch (key) {
            case 'e' -> fluido.start();
            case 'r' -> fluido.stop();
        }
        switch (keyCode) {
            case BARRA_ESPACIADORA -> {
                fluido.conmutarEstado();
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
