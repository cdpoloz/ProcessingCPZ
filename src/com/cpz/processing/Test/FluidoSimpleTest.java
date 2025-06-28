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

    private PVector p0, p1, p2, p3, p4, p5, p6, p7;
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

        p0 = new PVector(1848.208f / 3840f, 601.541f / 1986f);
        p1 = new PVector(1848.208f / 3840f, 212.145f / 1986f);
        p2 = new PVector(2619.306f / 3840f, 212.145f / 1986f);
        p3 = new PVector(2619.306f / 3840f, 404.645f / 1986f);
        //p4 = new PVector(1977.103f / 3840f, 884.123f / 1986f);
        //p5 = new PVector(1977.103f / 3840f, 842.303f / 1986f);
        //p6 = new PVector(1848.399f / 3840f, 842.303f / 1986f);
        //p7 = new PVector(1848.399f / 3840f, 782.936f / 1986f);
        PVector normal01 = Tools.calcularNormal(p0, p1);
        PVector normal12 = Tools.calcularNormal(p1, p2);
        PVector normal23 = Tools.calcularNormal(p2, p3);
        //PVector normal34 = Tools.calcularNormal(p3, p4);
        //PVector normal45 = Tools.calcularNormal(p4, p5);
        //PVector normal56 = Tools.calcularNormal(p5, p6);
        //PVector normal67 = Tools.calcularNormal(p6, p7);
        float dist01 = PVector.dist(p0, p1);
        float dist12 = PVector.dist(p1, p2);
        float dist23 = PVector.dist(p2, p3);
        //float dist34 = PVector.dist(p3, p4);
        //float dist45 = PVector.dist(p4, p5);
        //float dist56 = PVector.dist(p5, p6);
        //float dist67 = PVector.dist(p6, p7);
        float distTotal = dist01 + dist12 + dist23;// + dist45 + dist56 + dist67;
        int pasosTotal = (int) map(distTotal, distMin, distMax, 50, 800);
        int pasos01 = (int) (dist01 * pasosTotal / distTotal);
        int pasos12 = (int) (dist12 * pasosTotal / distTotal);
        int pasos23 = (int) (dist23 * pasosTotal / distTotal);
        //int pasos34 = (int) (dist34 * pasosTotal / distTotal);
        //int pasos45 = (int) (dist45 * pasosTotal / distTotal);
        //int pasos56 = (int) (dist56 * pasosTotal / distTotal);
        //int pasos67 = (int) (dist67 * pasosTotal / distTotal);
        List<PVector> posiciones01 = Tools.calcularPosicionesEnRecta(p0, p1, pasos01);
        List<PVector> posiciones12 = Tools.calcularPosicionesEnRecta(p1, p2, pasos12);
        List<PVector> posiciones23 = Tools.calcularPosicionesEnRecta(p2, p3, pasos23);
       // List<PVector> posiciones34 = Tools.calcularPosicionesEnRecta(p3, p4, pasos34);
        //List<PVector> posiciones45 = Tools.calcularPosicionesEnRecta(p4, p5, pasos45);
        //List<PVector> posiciones56 = Tools.calcularPosicionesEnRecta(p5, p6, pasos56);
        //List<PVector> posiciones67 = Tools.calcularPosicionesEnRecta(p6, p7, pasos67);
        List<PVector> normales01 = new ArrayList<>();
        List<PVector> normales12 = new ArrayList<>();
        List<PVector> normales23 = new ArrayList<>();
        //List<PVector> normales34 = new ArrayList<>();
        //List<PVector> normales45 = new ArrayList<>();
        //List<PVector> normales56 = new ArrayList<>();
        //List<PVector> normales67 = new ArrayList<>();
        for (int i = 0; i < posiciones01.size(); i++) {
            normales01.add(normal01);
        }

        for (int i = 0; i < posiciones12.size(); i++) {
            normales12.add(normal12);
        }

        for (int i = 0; i < posiciones23.size(); i++) {
            normales23.add(normal23);
        }
/*
        for (int i = 0; i < posiciones34.size(); i++) {
            normales34.add(normal34);
        }
        */
        /*
        for (int i = 0; i < posiciones45.size(); i++) {
            normales45.add(normal45);
        }
        for (int i = 0; i < posiciones56.size(); i++) {
            normales56.add(normal56);
        }
        for (int i = 0; i < posiciones67.size(); i++) {
            normales67.add(normal67);
        }
        */
        List<PVector> posiciones = new ArrayList<>();
        posiciones.addAll(posiciones01);
        posiciones.addAll(posiciones12);
        posiciones.addAll(posiciones23);
        //posiciones.addAll(posiciones34);
        //posiciones.addAll(posiciones45);
        //posiciones.addAll(posiciones56);
        //posiciones.addAll(posiciones67);
        List<PVector> normales = new ArrayList<>();
        normales.addAll(normales01);
        normales.addAll(normales12);
        normales.addAll(normales23);
        //normales.addAll(normales34);
        //normales.addAll(normales45);
        //normales.addAll(normales56);
        //normales.addAll(normales67);
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
        line(p1.x * width, p1.y * height, p2.x * width, p2.y * height);
        line(p2.x * width, p2.y * height, p3.x * width, p3.y * height);
        //line(p3.x * width, p3.y * height, p4.x * width, p4.y * height);
        //line(p4.x * width, p4.y * height, p5.x * width, p5.y * height);
        //line(p5.x * width, p5.y * height, p6.x * width, p6.y * height);
        //line(p6.x * width, p6.y * height, p7.x * width, p7.y * height);
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
