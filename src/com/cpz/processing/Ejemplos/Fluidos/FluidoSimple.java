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

    private PVector p0, p1, p2, p3, p4;
    private Fluido fluido2, fluido1;
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
        // cálculo de posiciones fluido
        p0 = new PVector(1805.824f / 3840f, 1754.332f / 1986f);
        p1 = new PVector(1300.338f / 3840f, 1754.332f / 1986f);

        p2 = new PVector(2500.580f / 3840f, 1536.118f / 1986f);
        p3 = new PVector(2000.580f / 3840f, 1643.613f / 1986f);

        PVector normal01 = Tools.calcularNormal(p0, p1);
        PVector normal23 = Tools.calcularNormal(p2, p3);
        float dist01 = PVector.dist(p0, p1);
        float dist23 = PVector.dist(p2, p3);

        int pasos01 = (int) map(dist01, distMin, distMax, 50, 800);
        int pasos23 = (int) map(dist23, distMin, distMax, 50, 800);

        List<PVector> posiciones = new ArrayList<>();
        List<PVector> normales = new ArrayList<>();
        List<PVector> posiciones01 = Tools.calcularPosicionesEnRecta(p0, p1, pasos01);
        /*
        List<PVector> posiciones12 = Tools.calcularPosicionesEnRecta(p1, p2, pasos12);

        List<PVector> posiciones34 = Tools.calcularPosicionesEnRecta(p3, p4, pasos34);
         */
        for (int i = 0; i < posiciones01.size(); i++) {
            normales.add(normal01);
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
        fluido2.setPosiciones(posiciones01);
        fluido2.setNormales(normales);
        for (int i = 0; i < posiciones01.size(); i++) {
            PVector p = posiciones01.get(i);
            PVector n = normales.get(i);
            String s = String.format("%,6f", p.x) + "," + String.format("%,6f", p.y) + "," + String.format("%,6f", n.x) + "," + String.format("%,6f", n.y);
            System.out.println(s);
        }
        System.out.println();

        posiciones = new ArrayList<>();
        normales = new ArrayList<>();

        List<PVector> posiciones23 = Tools.calcularPosicionesEnRecta(p2, p3, pasos23);

        for (int i = 0; i < posiciones23.size(); i++) {
            normales.add(normal23);
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
        fluido1.setPosiciones(posiciones23);
        fluido1.setNormales(normales);
        for (int i = 0; i < posiciones23.size(); i++) {
            PVector p = posiciones23.get(i);
            PVector n = normales.get(i);
            String s = String.format("%,6f", p.x) + "," + String.format("%,6f", p.y) + "," + String.format("%,6f", n.x) + "," + String.format("%,6f", n.y);
            System.out.println(s);
        }
        System.out.println();

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
        if (fluido1.isLlenar()) fluido1.agregarMovil();
        if (fluido1.isVaciar()) fluido1.eliminarMovilesAlLlegar();
        fluido1.update();

        if (fluido1.isFinRecorridoPrimero() && !fluido2.isLleno() && !fluido2.isLlenar()) fluido2.setLlenar(true);
        if (fluido1.isVacio() && !fluido2.isVacio() && !fluido2.isVaciar()) fluido2.setVaciar(true);
        if (fluido2.isLlenar()) fluido2.agregarMovil();
        if (fluido2.isVaciar()) fluido2.eliminarMovilesAlLlegar();
        fluido2.update();



        float alfaFondoMin = 0.1f;
        float alfaFondoMax = 1f;

        float alfaFondo1 = map(fluido1.getFactorLlenado(), 0, 1, alfaFondoMax, alfaFondoMin);
        int colorOn = color(255, 0, 120, alfaFondo1 * 255);
        int colorOff = color(80, 87, 89, alfaFondo1 * 255);
        int colorFondo1 = lerpColor(colorOff, colorOn, fluido1.getFactorLlenado());

        float alfaFondo2 = map(fluido2.getFactorLlenado(), 0, 1, alfaFondoMax, alfaFondoMin);
        colorOn = color(255, 0, 120, alfaFondo2 * 255);
        colorOff = color(80, 87, 89, alfaFondo2 * 255);
        int colorFondo2 = lerpColor(colorOff, colorOn, fluido2.getFactorLlenado());

        // draw
        dibujarFondo(colorFondo1, colorFondo2);
        fluido1.draw();
        fluido2.draw();
        String s = "llenar: " + (fluido1.isLlenar() ? "1" : "0") + "\nvaciar: " + (fluido1.isVaciar() ? "1" : "0") + "\n\n";
        s += "llenar: " + (fluido2.isLlenar() ? "1" : "0") + "\nvaciar: " + (fluido2.isVaciar() ? "1" : "0") + "\n";
        lbl.setTexto(s);
        lbl.draw();
    }

    public void dibujarFondo(int colorFondo1, int colorFondo2) {
        background(32);
        strokeWeight(10);
        stroke(colorFondo2);

        line(p0.x * width, p0.y * height, p1.x * width, p1.y * height);
        stroke(colorFondo1);
        line(p2.x * width, p2.y * height, p3.x * width, p3.y * height);

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
            case 'e' -> {
                if (!fluido1.isLleno() && !fluido1.isLlenar()) fluido1.setLlenar(true);
            }
            case 'r' -> {
                if (!fluido1.isVacio() && !fluido1.isVaciar()) fluido1.setVaciar(true);
            }
        }
        switch (keyCode) {
            /*
            case BARRA_ESPACIADORA -> {
                FluidoEstado estado1 = fluidoOrigen1.getEstado();
                if (estado1 == FluidoEstado.VACIO || estado1 == FluidoEstado.VACIAR) {
                    fluidoOrigen1.setEstado(FluidoEstado.LLENAR);
                } else if (estado1 == FluidoEstado.LLENAR || estado1 == FluidoEstado.LLENO) {
                    fluidoOrigen1.setEstado(FluidoEstado.VACIAR);
                }
            }
            */
            case FLECHA_ARRIBA -> fluido2.actualizarVelocidadNoisePorDiferencial("+");
            case FLECHA_ABAJO -> fluido2.actualizarVelocidadNoisePorDiferencial("-");
            case FLECHA_DERECHA -> fluido2.setVelocidadNoise(1f);
            case FLECHA_IZQUIERDA -> fluido2.setVelocidadNoise(0);
        }
    }

}
