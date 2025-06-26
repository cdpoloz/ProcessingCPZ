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
import com.cpz.processing.UI.Label;
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

    private PVector pIniOrigen1, pFinOrigen1;
    private PVector pIniOrigen2, pFinOrigen2;
    private PVector pIniSuma, pFinSuma;
    private PVector pIniExtension, pFinExtension;
    private Fluido fluidoOrigen1, fluidoOrigen2, fluidoSuma, fluidoExtension;
    private Label lbl;

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
        // *** IMAGEN - aquí puedes elegir si quieres usar un objeto PImage o PShape
        Object img = loadImage("data" + File.separator + "img" + File.separator + "circuloBlur.png");
        //Object img = loadShape("data" + File.separator + "img" + File.separator + "shape.svg");
        // *** TRAYECTORIA - se calcula la trayectoria entre los puntos de inicio y fin de cada segmento
        float distMin = 0f;
        //float distMax = 2.236068f;
        float distMax = 1.777778f;
        // *** TRAYECTORIA - cálculo para fluido de origen 1
        pIniOrigen1 = new PVector(0.05f, 0.05f);
        pFinOrigen1 = new PVector(0.35f, 0.23f);
        PVector normal1 = Tools.calcularNormal(pIniOrigen1, pFinOrigen1);
        float dist1 = PVector.dist(pIniOrigen1, pFinOrigen1);
        int pasos1 = (int) map(dist1, distMin, distMax, 50, 800);
        List<PVector> posiciones1 = Tools.calcularPosicionesEnRecta(pIniOrigen1, pFinOrigen1, pasos1);
        List<PVector> normales1 = new ArrayList<>();
        for (int i = 0; i < posiciones1.size(); i++) {
            normales1.add(normal1);
        }
        fluidoOrigen1 = new Fluido();
        fluidoOrigen1.setSketch(this);
        fluidoOrigen1.setCodigo("f1");
        fluidoOrigen1.setImg(img);
        fluidoOrigen1.setCantidadMovilesMax(100);
        fluidoOrigen1.setRangDeltaIndPos(1, 3);
        fluidoOrigen1.setColorRelleno(color(255, 0, 100));
        fluidoOrigen1.setRangoAlfaMovil(0.01f, 0.80f);
        fluidoOrigen1.setRangoVelocidadNoise(0.055f, 0.200f, 0.005f);
        fluidoOrigen1.setPeriodo(15);
        fluidoOrigen1.setDesviacionMax(0.005f);
        fluidoOrigen1.setRangoDiametro(0.01f, 0.015f);
        fluidoOrigen1.setPosiciones(posiciones1);
        fluidoOrigen1.setNormales(normales1);
        fluidoOrigen1.setRun(true);
        System.out.println("fluido 1");
        for (int i = 0; i < posiciones1.size(); i++) {
            PVector p = posiciones1.get(i);
            PVector n = normales1.get(i);
            String s = String.format("%,6f", p.x) + "," + String.format("%,6f", p.y) + "," + String.format("%,6f", n.x) + "," + String.format("%,6f", n.y);
            System.out.println(s);
        }
        System.out.println();

        // cálculo para fluido 2
        pIniOrigen2 = new PVector(2500.580f / 3840f, 1000f / 1986f);
        pFinOrigen2 = new PVector(0.35f, 0.23f);
        PVector normal2 = Tools.calcularNormal(pIniOrigen2, pFinOrigen2);
        float dist2 = PVector.dist(pIniOrigen2, pFinOrigen2);
        int pasos2 = (int) map(dist2, distMin, distMax, 50, 800);
        List<PVector> posiciones2 = Tools.calcularPosicionesEnRecta(pIniOrigen2, pFinOrigen2, pasos2);
        List<PVector> normales2 = new ArrayList<>();
        for (int i = 0; i < posiciones2.size(); i++) {
            normales2.add(normal2);
        }
        fluidoOrigen2 = new Fluido();
        fluidoOrigen2.setSketch(this);
        fluidoOrigen2.setCodigo("f2");
        fluidoOrigen2.setImg(img);
        fluidoOrigen2.setCantidadMovilesMax(100);
        fluidoOrigen2.setRangDeltaIndPos(1, 3);
        fluidoOrigen2.setColorRelleno(color(255, 0, 100));
        fluidoOrigen2.setRangoAlfaMovil(0.01f, 0.80f);
        fluidoOrigen2.setRangoVelocidadNoise(0.055f, 0.200f, 0.005f);
        fluidoOrigen2.setPeriodo(15);
        fluidoOrigen2.setDesviacionMax(0.005f);
        fluidoOrigen2.setRangoDiametro(0.01f, 0.015f);
        fluidoOrigen2.setPosiciones(posiciones2);
        fluidoOrigen2.setNormales(normales2);
        fluidoOrigen2.setRun(true);
        System.out.println("fluido 2");
        for (int i = 0; i < posiciones2.size(); i++) {
            PVector p = posiciones2.get(i);
            PVector n = normales2.get(i);
            String s = String.format("%,6f", p.x) + "," + String.format("%,6f", p.y) + "," + String.format("%,6f", n.x) + "," + String.format("%,6f", n.y);
            System.out.println(s);
        }
        System.out.println();

        // cálculo para fluido resultado
        pIniSuma = new PVector(1805.824f / 3840f, 1754.332f / 1986f);
        pFinSuma = new PVector(1300.338f / 3840f, 1754.332f / 1986f);
        PVector normalResultado = Tools.calcularNormal(pIniSuma, pFinSuma);
        float distResultado = PVector.dist(pIniSuma, pFinSuma);
        int pasosResultado = (int) map(distResultado, distMin, distMax, 50, 800);
        List<PVector> posicionesResultado = Tools.calcularPosicionesEnRecta(pIniSuma, pFinSuma, pasosResultado);
        List<PVector> normalesResultado = new ArrayList<>();
        for (int i = 0; i < posicionesResultado.size(); i++) {
            normalesResultado.add(normalResultado);
        }
        fluidoSuma = new Fluido();
        fluidoSuma.setSketch(this);
        fluidoSuma.setCodigo("fR");
        fluidoSuma.setImg(img);
        fluidoSuma.setCantidadMovilesMax(100);
        fluidoSuma.setRangDeltaIndPos(1, 3);
        fluidoSuma.setColorRelleno(color(255, 0, 100));
        fluidoSuma.setRangoAlfaMovil(0.01f, 0.80f);
        fluidoSuma.setRangoVelocidadNoise(0.055f, 0.200f, 0.005f);
        fluidoSuma.setPeriodo(15);
        fluidoSuma.setDesviacionMax(0.005f);
        fluidoSuma.setRangoDiametro(0.01f, 0.015f);
        fluidoSuma.setPosiciones(posicionesResultado);
        fluidoSuma.setNormales(normalesResultado);
        fluidoSuma.setRun(true);
        fluidoSuma.agregarFluidoOrigen(fluidoOrigen1);
        fluidoSuma.agregarFluidoOrigen(fluidoOrigen2);
        System.out.println("fluido resultado");
        for (int i = 0; i < posicionesResultado.size(); i++) {
            PVector p = posicionesResultado.get(i);
            PVector n = normalesResultado.get(i);
            String s = String.format("%,6f", p.x) + "," + String.format("%,6f", p.y) + "," + String.format("%,6f", n.x) + "," + String.format("%,6f", n.y);
            System.out.println(s);
        }
        System.out.println();

        // cálculo para fluido extra
        pIniExtension = new PVector(1000f / 3840f, 1200f / 1986f);
        pFinExtension = new PVector(500f / 3840f, 500f / 1986f);
        PVector normalExtra = Tools.calcularNormal(pIniExtension, pFinExtension);
        float distExtra = PVector.dist(pIniExtension, pFinExtension);
        int pasosExtra = (int) map(distExtra, distMin, distMax, 50, 800);
        List<PVector> posicionesExtra = Tools.calcularPosicionesEnRecta(pIniExtension, pFinExtension, pasosExtra);
        List<PVector> normalesExtra = new ArrayList<>();
        for (int i = 0; i < posicionesExtra.size(); i++) {
            normalesExtra.add(normalExtra);
        }
        fluidoExtension = new Fluido();
        fluidoExtension.setSketch(this);
        fluidoExtension.setCodigo("fE");
        fluidoExtension.setImg(img);
        fluidoExtension.setCantidadMovilesMax(100);
        fluidoExtension.setRangDeltaIndPos(1, 3);
        fluidoExtension.setColorRelleno(color(255, 0, 100));
        fluidoExtension.setRangoAlfaMovil(0.01f, 0.80f);
        fluidoExtension.setRangoVelocidadNoise(0.055f, 0.200f, 0.005f);
        fluidoExtension.setPeriodo(15);
        fluidoExtension.setDesviacionMax(0.005f);
        fluidoExtension.setRangoDiametro(0.01f, 0.015f);
        fluidoExtension.setPosiciones(posicionesExtra);
        fluidoExtension.setNormales(normalesExtra);
        fluidoExtension.agregarFluidoOrigen(fluidoSuma);
        fluidoExtension.setRun(false);
        System.out.println("fluido extra");
        for (int i = 0; i < posicionesExtra.size(); i++) {
            PVector p = posicionesExtra.get(i);
            PVector n = normalesExtra.get(i);
            String s = String.format("%,6f", p.x) + "," + String.format("%,6f", p.y) + "," + String.format("%,6f", n.x) + "," + String.format("%,6f", n.y);
            System.out.println(s);
        }
        System.out.println();

        lbl = new Label();
        lbl.setSketch(this);
        lbl.setDisplay(true);
        lbl.setAlineaX(AlineaX.IZQ);
        lbl.setAlineaY(AlineaY.SUP);
        lbl.setColorTexto(color(255));
        lbl.setPos(0.8f * width, 0.25f * height);
        lbl.setTamTexto(22);
    }

    // PROGRAMA PRINCIPAL ******************************************************
    @Override
    public void draw() {
        // update
        fluidoOrigen1.update();
        fluidoOrigen2.update();
        fluidoSuma.update();
        fluidoExtension.update();
        // color fondo fluido 1
        float alfaFondoMin = 0.1f;
        float alfaFondoMax = 1f;
        float alfaFondo1 = map(fluidoOrigen1.getFactorLlenado(), 0, 1, alfaFondoMax, alfaFondoMin);
        int colorOn = color(255, 0, 120, alfaFondo1 * 255);
        int colorOff = color(80, 87, 89, alfaFondo1 * 255);
        int colorFondo1 = lerpColor(colorOff, colorOn, fluidoOrigen1.getFactorLlenado());
        // color fondo fluido 2
        float alfaFondo2 = map(fluidoOrigen2.getFactorLlenado(), 0, 1, alfaFondoMax, alfaFondoMin);
        colorOn = color(255, 0, 120, alfaFondo2 * 255);
        colorOff = color(80, 87, 89, alfaFondo2 * 255);
        int colorFondo2 = lerpColor(colorOff, colorOn, fluidoOrigen2.getFactorLlenado());
        // color fondo fluido resultado
        float alfaFondoResultado = map(fluidoSuma.getFactorLlenado(), 0, 1, alfaFondoMax, alfaFondoMin);
        colorOn = color(255, 0, 120, alfaFondoResultado * 255);
        colorOff = color(80, 87, 89, alfaFondoResultado * 255);
        int colorFondoResultado = lerpColor(colorOff, colorOn, fluidoSuma.getFactorLlenado());
        // color fondo fluido extra
        float alfaFondoExtra = map(fluidoExtension.getFactorLlenado(), 0, 1, alfaFondoMax, alfaFondoMin);
        colorOn = color(255, 0, 120, alfaFondoExtra * 255);
        colorOff = color(80, 87, 89, alfaFondoExtra * 255);
        int colorFondoExtra = lerpColor(colorOff, colorOn, fluidoExtension.getFactorLlenado());
        // update - label
        String s = "1.llenar: " + (fluidoOrigen1.isLlenar() ? "1" : "0") + "\n1.vaciar: " + (fluidoOrigen1.isVaciar() ? "1" : "0") + "\n\n";
        s += "2.llenar: " + (fluidoOrigen2.isLlenar() ? "1" : "0") + "\n2.vaciar: " + (fluidoOrigen2.isVaciar() ? "1" : "0") + "\n\n";
        s += "r.llenar: " + (fluidoSuma.isLlenar() ? "1" : "0") + "\nr.vaciar: " + (fluidoSuma.isVaciar() ? "1" : "0") + "\n\n";
        s += "e.llenar: " + (fluidoExtension.isLlenar() ? "1" : "0") + "\ne.vaciar: " + (fluidoExtension.isVaciar() ? "1" : "0") + "\n\n";
        lbl.setTexto(s);
        // draw
        dibujarFondo(colorFondo1, colorFondo2, colorFondoResultado, colorFondoExtra);
        fluidoOrigen1.draw();
        fluidoOrigen2.draw();
        fluidoSuma.draw();
        fluidoExtension.draw();
        lbl.draw();
    }

    public void dibujarFondo(int colorFondo1, int colorFondo2, int colorFondoResultado, int colorFondoExtra) {
        background(32);
        strokeWeight(10);
        stroke(colorFondo1);
        line(pIniOrigen1.x * width, pIniOrigen1.y * height, pFinOrigen1.x * width, pFinOrigen1.y * height);
        stroke(colorFondo2);
        line(pIniOrigen2.x * width, pIniOrigen2.y * height, pFinOrigen2.x * width, pFinOrigen2.y * height);
        stroke(colorFondoResultado);
        line(pIniSuma.x * width, pIniSuma.y * height, pFinSuma.x * width, pFinSuma.y * height);
        stroke(colorFondoExtra);
        line(pIniExtension.x * width, pIniExtension.y * height, pFinExtension.x * width, pFinExtension.y * height);
    }

    // INTERRUPCIONES DE PROCESSING ********************************************
    @Override
    public void mouseReleased() {
        if (mouseButton == RIGHT) fluidoOrigen1.conmutarEstado();
        if (mouseButton == LEFT) fluidoOrigen2.conmutarEstado();
    }

    @Override
    public void mouseWheel(MouseEvent event) {

    }

    @Override
    public void keyReleased() {
        switch (key) {
            case 'e' -> fluidoOrigen1.start();
            case 'r' -> fluidoOrigen1.stop();
            case 'd' -> fluidoOrigen2.start();
            case 'f' -> fluidoOrigen2.stop();
            case 'c' -> fluidoExtension.setRun(!fluidoExtension.isRun());
            case 'n' -> {
                Object img = loadImage("data" + File.separator + "img" + File.separator + "circuloBlur.png");
                fluidoOrigen1.setImg(img);
                fluidoOrigen2.setImg(img);
                fluidoSuma.setImg(img);
                fluidoExtension.setImg(img);
            }
            case 'm' -> {
                Object img = loadShape("data" + File.separator + "img" + File.separator + "shape.svg");
                fluidoOrigen1.setImg(img);
                fluidoOrigen2.setImg(img);
                fluidoSuma.setImg(img);
                fluidoExtension.setImg(img);
            }
        }
        switch (keyCode) {
            case BARRA_ESPACIADORA -> {
                fluidoOrigen1.conmutarEstado();
                fluidoOrigen2.conmutarEstado();
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
