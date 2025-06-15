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
import processing.core.PVector;
import processing.event.MouseEvent;

import java.io.File;
import java.util.List;

import com.cpz.processing.Util.Tools;

/**
 * @author CPZ
 */
public class FluidoSimple extends PApplet {

    private int anchoPantalla, altoPantalla;

    private PVector p0, p1;
    private int colorStroke, colorOn, colorOff;
    private float alfaFondo, alfaFondoMin, alfaFondoMax;

    private Fluido fluido;

    // CONFIGURACIÓN PARA LA ANIMACIÓN *****************************************
    @Override
    public void settings() {
        size(1200, 600, P2D);
        smooth(8);
    }

    // CONFIGURACIÓN ADICIONAL DE LA VENTANA ***********************************
    @Override
    public void setup() {
        surface.setTitle("Fluido simple");
        frameRate(60);

        alfaFondoMin = 32;
        alfaFondoMax = 255;
        alfaFondo = alfaFondoMax;
        colorOn = color(255, 0, 102);
        colorOff = color(80, 87, 89);

        // CONTINUAR AQUÍ **********************************************************************************************
        // IMPLEMENTAR RECORRIDOS MÚLTIPLES
        // *************************************************************************************************************

        float distMin = 0f;
        float distMax = 2.236068f;
        int cantidadMovilesMin = 50;
        int cantidadMovilesMax = 200;
        // cálculo de trayectoria (solo si es que se quiere hacer en runtime)
        p0 = new PVector(random(1), random(1));
        p1 = new PVector(random(1), random(1));
        float dist = PVector.dist(new PVector(p0.x * width, p0.y * height), new PVector(p1.x * width, p1.y * height)) / height;
        int pasos = (int) map(dist, distMin, distMax, cantidadMovilesMin, cantidadMovilesMax);
        List<PVector> lstPos = Tools.calcularNuevaTrayectoria(p0, p1, pasos);
        PVector normal = Tools.calcularNormal(p0, p1);
        int cantidadMoviles = (int) map(dist, distMin, distMax, cantidadMovilesMin, cantidadMovilesMax);

        fluido = new Fluido(this);
        fluido.setImg(loadImage("data" + File.separator + "img" + File.separator + "circuloBlur.png"));
        fluido.setRangoDiametro(0.020f, 0.029f);
        fluido.setColorRelleno(color(255, 0, 102));
        fluido.setDesviacionMaxima(0.0125f);
        fluido.setPeriodo(15);
        fluido.setRangoAlfa(0.2f, 1f);
        fluido.setRangoVelocidadNoise(0.015f, 0.040f, 0.001f);
        fluido.setCantidadMovilesMax(cantidadMoviles);
        lstPos.stream().forEach(p -> fluido.addPos(p, normal));

    }

    // PROGRAMA PRINCIPAL ******************************************************
    @Override
    public void draw() {
        // update
        fluido.update();
        updateColorFondo();
        // draw
        dibujarFondo();
        fluido.draw();
    }

    public void updateColorFondo() {
        if (!fluido.isRunning()) {
            colorStroke = colorOff;
            return;
        }
        alfaFondo = (int) map(fluido.getCantidadMoviles(), 0, fluido.getCantidadMovilesMax(), alfaFondoMax, alfaFondoMin);
        float f = map(alfaFondo, alfaFondoMax, alfaFondoMin, 0, 1);
        colorOn = color(red(colorOn), green(colorOn), blue(colorOn), alfaFondo);
        colorOff = color(red(colorOff), green(colorOff), blue(colorOff), alfaFondo);
        colorStroke = lerpColor(colorOff, colorOn, f);
    }

    public void dibujarFondo() {
        background(32);
        strokeWeight(10);
        stroke(colorStroke);
        line(p0.x * width, p0.y * height, p1.x * width, p1.y * height);
    }

    // INTERRUPCIONES DE PROCESSING ********************************************
    @Override
    public void mouseReleased() {
        fluido.updateModo();
    }

    @Override
    public void mouseWheel(MouseEvent event) {
        fluido.updateVelocidad(event.getCount() < 0 ? "+" : "-");
    }

    @Override
    public void keyReleased() {
        switch (key) {
            case 'e' -> {
            }
            case 'r' -> {
            }
            case 't' -> {
            }
            case 'y' -> {
            }
            case 'i' -> {
            }
            case 'p' -> {
            }
            default -> {
            }
        }
    }

}
