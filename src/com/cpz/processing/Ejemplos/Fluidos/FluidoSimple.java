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

    private PVector p0, p1, p2, p3, p4, p5, p6;
    private int colorFondo1, colorFondo2;
    private Fluido fluido1, fluido2;

    // CONFIGURACIÓN PREVIA A SETUP ********************************************
    @Override
    public void settings() {
        size(1920, 993, P2D);
        smooth(8);
    }

    // CONFIGURACIÓN DE VARIABLES **********************************************
    @Override
    public void setup() {
        surface.setTitle("Fluido simple");
        frameRate(60);
        // fondo
        colorFondo1 = color(80, 87, 89);
        colorFondo2 = color(80, 87, 89);
        // imagen
        PImage img = loadImage("data" + File.separator + "img" + File.separator + "circuloBlur.png");
        // cálculo de trayectoria (solo si es que se quiere hacer en runtime)
        float distMin = 0f;
        float distMax = 2.236068f;

        p0 = new PVector(1284.407f / 3840f, 1062.799f / 1986f);
        p1 = new PVector(1480.585f / 3840f, 1062.799f / 1986f);
        p2 = new PVector(1480.586f / 3840f, 902.122f / 1986f);

        p3 = new PVector(1500.585f / 3840f, 886.117f / 1986f);
        p4 = new PVector(1500.586f / 3840f, 272.144f / 1986f);
        p5 = new PVector(2085.805f / 3840f, 272.144f / 1986f);
        p6 = new PVector(2085.805f / 3840f, 404.6817f / 1986f);

        PVector normal01 = Tools.calcularNormal(p0, p1);
        PVector normal12 = Tools.calcularNormal(p1, p2);
        PVector normal23 = Tools.calcularNormal(p2, p3);
        PVector normal34 = Tools.calcularNormal(p3, p4);
        PVector normal45 = Tools.calcularNormal(p4, p5);
        PVector normal56 = Tools.calcularNormal(p5, p6);
        float dist01 = PVector.dist(p0, p1);
        float dist12 = PVector.dist(p1, p2);
        float dist23 = PVector.dist(p2, p3);
        float dist34 = PVector.dist(p3, p4);
        float dist45 = PVector.dist(p4, p5);
        float dist56 = PVector.dist(p5, p6);
        float distTotal = dist01 + dist12 + dist23 + dist34 + dist45 + dist56;
        int pasosTotal = (int) map(distTotal, distMin, distMax, 50, 800);
        int pasos01 = (int) (dist01 * pasosTotal / distTotal);
        int pasos12 = (int) (dist12 * pasosTotal / distTotal);
        int pasos23 = (int) (dist23 * pasosTotal / distTotal);
        int pasos34 = (int) (dist34 * pasosTotal / distTotal);
        int pasos45 = (int) (dist45 * pasosTotal / distTotal);
        int pasos56 = pasosTotal - pasos01 - pasos12 - pasos23 - pasos34 - pasos45;
        List<PVector> posiciones = new ArrayList<>();
        List<PVector> normales = new ArrayList<>();
        List<PVector> posiciones01 = Tools.calcularPosicionesEnRecta(p0, p1, pasos01);
        List<PVector> posiciones12 = Tools.calcularPosicionesEnRecta(p1, p2, pasos12);
        List<PVector> posiciones23 = Tools.calcularPosicionesEnRecta(p2, p3, pasos23);
        List<PVector> posiciones34 = Tools.calcularPosicionesEnRecta(p3, p4, pasos34);
        List<PVector> posiciones45 = Tools.calcularPosicionesEnRecta(p4, p5, pasos45);
        List<PVector> posiciones56 = Tools.calcularPosicionesEnRecta(p5, p6, pasos56);
        for (PVector p : posiciones01) {
            posiciones.add(p);
            normales.add(normal01);
        }
        for (PVector p : posiciones12) {
            posiciones.add(p);
            normales.add(normal12);
        }
        for (PVector p : posiciones23) {
            posiciones.add(p);
            normales.add(normal23);
        }

        // fluido 1
        fluido1 = new Fluido(this);
        fluido1.setImg(img);
        fluido1.setCantidadMovilesMax(150);
        fluido1.setRangDeltaIndPos(1, 3);
        fluido1.setColorOn(color(255, 0, 120));
        fluido1.setRangoAlfaMovil(0.01f, 0.80f);
        fluido1.setRangoVelocidadNoise(0.055f, 0.200f, 0.005f);
        fluido1.setPeriodo(15);
        fluido1.setDesviacionMax(0.005f);
        fluido1.setRangoDiametro(0.01f, 0.015f);
        fluido1.setPosiciones(posiciones);
        fluido1.setNormales(normales);
        for (int i = 0; i < posiciones.size(); i++) {
            PVector p = posiciones.get(i);
            PVector n = normales.get(i);
            String s = String.format("%,6f", p.x) + "," + String.format("%,6f", p.y) + "," + String.format("%,6f", n.x) + "," + String.format("%,6f", n.y);
            System.out.println(s);
        }
        System.out.println();
        posiciones = new ArrayList<>();
        normales = new ArrayList<>();
        for (PVector p : posiciones34) {
            posiciones.add(p);
            normales.add(normal34);
        }
        for (PVector p : posiciones45) {
            posiciones.add(p);
            normales.add(normal45);
        }
        for (PVector p : posiciones56) {
            posiciones.add(p);
            normales.add(normal56);
        }
        // fluido 1
        fluido2 = new Fluido(this);
        fluido2.setImg(img);
        fluido2.setCantidadMovilesMax(150);
        fluido2.setRangDeltaIndPos(1, 3);
        fluido2.setColorOn(color(255, 0, 120));
        fluido2.setRangoAlfaMovil(0.01f, 0.80f);
        fluido2.setRangoVelocidadNoise(0.055f, 0.200f, 0.005f);
        fluido2.setPeriodo(15);
        fluido2.setDesviacionMax(0.005f);
        fluido2.setRangoDiametro(0.01f, 0.015f);
        fluido2.setPosiciones(posiciones);
        fluido2.setNormales(normales);
        for (int i = 0; i < posiciones.size(); i++) {
            PVector p = posiciones.get(i);
            PVector n = normales.get(i);
            String s = String.format("%,6f", p.x) + "," + String.format("%,6f", p.y) + "," + String.format("%,6f", n.x) + "," + String.format("%,6f", n.y);
            System.out.println(s);
        }
    }

    // PROGRAMA PRINCIPAL ******************************************************
    @Override
    public void draw() {
        // update
        fluido1.update();
        fluido2.update();

        float alfaFondoMin = 0.1f;
        float alfaFondoMax = 1f;
        float f1 = fluido1.getFactorLlenado();
        float alfaFondo1 = map(f1, 0, 1, alfaFondoMax, alfaFondoMin);
        int colorOn1 = color(255, 0, 120, alfaFondo1 * 255);
        int colorOff1 = color(80, 87, 89, alfaFondo1 * 255);
        colorFondo1 = lerpColor(colorOff1, colorOn1, f1);

        float f2 = fluido1.getFactorLlenado();
        float alfaFondo = map(f2, 0, 1, alfaFondoMax, alfaFondoMin);
        int colorOn2 = color(255, 0, 120, alfaFondo * 255);
        int colorOff2 = color(80, 87, 89, alfaFondo * 255);
        colorFondo2 = lerpColor(colorOff2, colorOn2, f2);

        // draw
        dibujarFondo();
        fluido1.draw();
        fluido2.draw();
    }

    public void dibujarFondo() {
        background(32);
        strokeWeight(10);
        stroke(colorFondo1);
        line(p0.x * width, p0.y * height, p1.x * width, p1.y * height);
        line(p1.x * width, p1.y * height, p2.x * width, p2.y * height);
        line(p2.x * width, p2.y * height, p3.x * width, p3.y * height);
        stroke(colorFondo2);
        line(p3.x * width, p3.y * height, p4.x * width, p4.y * height);
        line(p4.x * width, p4.y * height, p5.x * width, p5.y * height);
        line(p5.x * width, p5.y * height, p6.x * width, p6.y * height);
    }

    // INTERRUPCIONES DE PROCESSING ********************************************
    @Override
    public void mouseReleased() {
        fluido1.conmutarEstadoFluido();
        fluido2.conmutarEstadoFluido();
    }

    @Override
    public void mouseWheel(MouseEvent event) {
        fluido1.actualizarVelocidadNoisePorDiferencial(event.getCount() < 0 ? "+" : "-");
        fluido2.actualizarVelocidadNoisePorDiferencial(event.getCount() < 0 ? "+" : "-");
    }

    @Override
    public void keyReleased() {
        switch (keyCode) {
            case BARRA_ESPACIADORA -> {
                fluido1.conmutarEstadoFluido();
                fluido2.conmutarEstadoFluido();
            }
            case FLECHA_ARRIBA -> fluido1.actualizarVelocidadNoisePorDiferencial("+");
            case FLECHA_ABAJO -> fluido1.actualizarVelocidadNoisePorDiferencial("-");
            case FLECHA_DERECHA -> fluido1.setVelocidadNoise(1f);
            case FLECHA_IZQUIERDA -> fluido1.setVelocidadNoise(0);
        }
    }

}
