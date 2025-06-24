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

package com.cpz.processing.Ejemplos.Fluidos;

import com.cpz.processing.Bean.Fluido;
import com.cpz.processing.UI.Label;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.event.MouseEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.cpz.processing.Util.Tools;

import static com.cpz.processing.Util.Constantes.*;

/**
 * @author CPZ
 */
public class FluidoSimple extends PApplet {

    private PVector pResultado1, pResultado2, p11, p12, p21, p22;
    private Fluido fluidoResultado, fluido1, fluido2;
    private Label lbl;

    // CONFIGURACIÓN PREVIA A SETUP ********************************************
    @Override
    public void settings() {
        size(960, 496, P2D);
        //size(1920, 993, P2D);
        smooth(8);
    }

    // CONFIGURACIÓN DE VARIABLES **********************************************
    @Override
    public void setup() {
        surface.setTitle("Fluido simple");
        frameRate(60);
        // imagen
        PImage img = loadImage("data" + File.separator + "img" + File.separator + "circuloBlur.png");
        // cálculo de trayectoria (solo si es que se quiere hacer en runtime)
        float distMin = 0f;
        float distMax = 2.236068f;
        // cálculo para fluido 1
        p11 = new PVector(2500.580f / 3840f, 1536.118f / 1986f);
        p12 = new PVector(2000.580f / 3840f, 1643.613f / 1986f);
        PVector normal1 = Tools.calcularNormal(p11, p12);
        float dist1 = PVector.dist(p11, p12);
        int pasos1 = (int) map(dist1, distMin, distMax, 50, 800);
        List<PVector> posiciones1 = Tools.calcularPosicionesEnRecta(p11, p12, pasos1);
        List<PVector> normales1 = new ArrayList<>();
        for (int i = 0; i < posiciones1.size(); i++) {
            normales1.add(normal1);
        }
        fluido1 = new Fluido();
        fluido1.setSketch(this);
        fluido1.setImg(img);
        fluido1.setCantidadMovilesMax(100);
        fluido1.setRangDeltaIndPos(1, 3);
        fluido1.setColorOn(color(255, 0, 100));
        fluido1.setRangoAlfaMovil(0.01f, 0.80f);
        fluido1.setRangoVelocidadNoise(0.055f, 0.200f, 0.005f);
        fluido1.setPeriodo(15);
        fluido1.setDesviacionMax(0.005f);
        fluido1.setRangoDiametro(0.01f, 0.015f);
        fluido1.setPosiciones(posiciones1);
        fluido1.setNormales(normales1);
        System.out.println("fluido 1");
        for (int i = 0; i < posiciones1.size(); i++) {
            PVector p = posiciones1.get(i);
            PVector n = normales1.get(i);
            String s = String.format("%,6f", p.x) + "," + String.format("%,6f", p.y) + "," + String.format("%,6f", n.x) + "," + String.format("%,6f", n.y);
            System.out.println(s);
        }
        System.out.println();

        // cálculo para fluido 2
        p21 = new PVector(2500.580f / 3840f, 1000f / 1986f);
        p22 = new PVector(2000.580f / 3840f, 1643.613f / 1986f);
        PVector normal2 = Tools.calcularNormal(p21, p22);
        float dist2 = PVector.dist(p21, p22);
        int pasos2 = (int) map(dist2, distMin, distMax, 50, 800);
        List<PVector> posiciones2 = Tools.calcularPosicionesEnRecta(p21, p22, pasos2);
        List<PVector> normales2 = new ArrayList<>();
        for (int i = 0; i < posiciones2.size(); i++) {
            normales2.add(normal2);
        }
        fluido2 = new Fluido();
        fluido2.setSketch(this);
        fluido2.setImg(img);
        fluido2.setCantidadMovilesMax(100);
        fluido2.setRangDeltaIndPos(1, 3);
        fluido2.setColorOn(color(255, 0, 100));
        fluido2.setRangoAlfaMovil(0.01f, 0.80f);
        fluido2.setRangoVelocidadNoise(0.055f, 0.200f, 0.005f);
        fluido2.setPeriodo(15);
        fluido2.setDesviacionMax(0.005f);
        fluido2.setRangoDiametro(0.01f, 0.015f);
        fluido2.setPosiciones(posiciones2);
        fluido2.setNormales(normales2);
        System.out.println("fluido 2");
        for (int i = 0; i < posiciones2.size(); i++) {
            PVector p = posiciones2.get(i);
            PVector n = normales2.get(i);
            String s = String.format("%,6f", p.x) + "," + String.format("%,6f", p.y) + "," + String.format("%,6f", n.x) + "," + String.format("%,6f", n.y);
            System.out.println(s);
        }
        System.out.println();

        // cálculo para fluido resultado
        pResultado1 = new PVector(1805.824f / 3840f, 1754.332f / 1986f);
        pResultado2 = new PVector(1300.338f / 3840f, 1754.332f / 1986f);
        PVector normalResultado = Tools.calcularNormal(pResultado1, pResultado2);
        float distResultado = PVector.dist(pResultado1, pResultado2);
        int pasosresultado = (int) map(distResultado, distMin, distMax, 50, 800);
        List<PVector> posicionesResultado = Tools.calcularPosicionesEnRecta(pResultado1, pResultado2, pasosresultado);
        List<PVector> normalesResultado = new ArrayList<>();
        for (int i = 0; i < posicionesResultado.size(); i++) {
            normalesResultado.add(normalResultado);
        }
        fluidoResultado = new Fluido();
        fluidoResultado.setSketch(this);
        fluidoResultado.setImg(img);
        fluidoResultado.setCantidadMovilesMax(100);
        fluidoResultado.setRangDeltaIndPos(1, 3);
        fluidoResultado.setColorOn(color(255, 0, 100));
        fluidoResultado.setRangoAlfaMovil(0.01f, 0.80f);
        fluidoResultado.setRangoVelocidadNoise(0.055f, 0.200f, 0.005f);
        fluidoResultado.setPeriodo(15);
        fluidoResultado.setDesviacionMax(0.005f);
        fluidoResultado.setRangoDiametro(0.01f, 0.015f);
        fluidoResultado.setPosiciones(posicionesResultado);
        fluidoResultado.setNormales(normalesResultado);
        fluidoResultado.agregarFluidoOrigen(fluido1);
        fluidoResultado.agregarFluidoOrigen(fluido2);
        System.out.println("fluido resultado");
        for (int i = 0; i < posicionesResultado.size(); i++) {
            PVector p = posicionesResultado.get(i);
            PVector n = normalesResultado.get(i);
            String s = String.format("%,6f", p.x) + "," + String.format("%,6f", p.y) + "," + String.format("%,6f", n.x) + "," + String.format("%,6f", n.y);
            System.out.println(s);
        }
        System.out.println();
        // label temporal
        lbl = new Label();
        lbl.setSketch(this);
        lbl.setDisplay(true);
        lbl.setAlineaX(AlineaX.IZQ);
        lbl.setAlineaY(AlineaY.INF);
        lbl.setColorTexto(color(255));
        lbl.setPos(0.7f * width, 0.65f * height);
        lbl.setTamTexto(22);
    }

    // PROGRAMA PRINCIPAL ******************************************************
    @Override
    public void draw() {
        // update
        fluido1.update();
        fluido2.update();
        fluidoResultado.update();
        // color fondo fluido 1
        float alfaFondoMin = 0.1f;
        float alfaFondoMax = 1f;
        float alfaFondo1 = map(fluido1.getFactorLlenado(), 0, 1, alfaFondoMax, alfaFondoMin);
        int colorOn = color(255, 0, 120, alfaFondo1 * 255);
        int colorOff = color(80, 87, 89, alfaFondo1 * 255);
        int colorFondo1 = lerpColor(colorOff, colorOn, fluido1.getFactorLlenado());
        // color fondo fluido 2
        float alfaFondo2 = map(fluido2.getFactorLlenado(), 0, 1, alfaFondoMax, alfaFondoMin);
        colorOn = color(255, 0, 120, alfaFondo2 * 255);
        colorOff = color(80, 87, 89, alfaFondo2 * 255);
        int colorFondo2 = lerpColor(colorOff, colorOn, fluido2.getFactorLlenado());
        // color fondo fluido resultado
        float alfaFondoResultado = map(fluidoResultado.getFactorLlenado(), 0, 1, alfaFondoMax, alfaFondoMin);
        colorOn = color(255, 0, 120, alfaFondoResultado * 255);
        colorOff = color(80, 87, 89, alfaFondoResultado * 255);
        int colorFondoResultado = lerpColor(colorOff, colorOn, fluidoResultado.getFactorLlenado());
        // update - label
        String s = "1.llenar: " + (fluido1.isLlenar() ? "1" : "0") + "\n1.vaciar: " + (fluido1.isVaciar() ? "1" : "0") + "\n\n";
        s += "2.llenar: " + (fluido2.isLlenar() ? "1" : "0") + "\n2.vaciar: " + (fluido2.isVaciar() ? "1" : "0") + "\n\n";
        s += "r.llenar: " + (fluidoResultado.isLlenar() ? "1" : "0") + "\nr.vaciar: " + (fluidoResultado.isVaciar() ? "1" : "0") + "\n\n";
        s += "running: "  + (fluidoResultado.isRunning() ? "1" : "0");
        lbl.setTexto(s);
        // draw
        dibujarFondo(colorFondo1, colorFondo2, colorFondoResultado);
        fluido1.draw();
        fluido2.draw();
        fluidoResultado.draw();
        lbl.draw();
    }

    public void dibujarFondo(int colorFondo1, int colorFondo2, int colorFondoResultado) {
        background(32);
        strokeWeight(10);
        stroke(colorFondoResultado);
        line(pResultado1.x * width, pResultado1.y * height, pResultado2.x * width, pResultado2.y * height);
        stroke(colorFondo1);
        line(p11.x * width, p11.y * height, p12.x * width, p12.y * height);
        stroke(colorFondo2);
        line(p21.x * width, p21.y * height, p22.x * width, p22.y * height);

    }

    // INTERRUPCIONES DE PROCESSING ********************************************
    @Override
    public void mouseReleased() {

    }

    @Override
    public void mouseWheel(MouseEvent event) {

    }

    @Override
    public void keyReleased() {
        switch (key) {
            case 'e' -> fluido1.start();
            case 'r' -> fluido1.stop();
            case 'd' -> fluido2.start();
            case 'f' -> fluido2.stop();
        }
        switch (keyCode) {
            case BARRA_ESPACIADORA -> {
                fluido1.conmutarEstado();
                fluido2.conmutarEstado();
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
