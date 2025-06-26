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
 * cpzProcessing - FluidoSimple
 *
 * Ejemplo de visualización de flujos de partículas a lo largo de trayectorias.
 *
 * Este sketch demuestra el uso de los componentes Fluido y Movil para representar
 * el movimiento de partículas simulando un flujo animado, dividido en múltiples
 * segmentos con trayectorias personalizadas y combinación de fuentes.
 *
 * Cada segmento puede actuar como fuente u origen de partículas, o como
 * colector de uno o más flujos anteriores. Se emplean normales para definir
 * la aleatoriedad del movimiento, ruido Perlin para suavizar las trayectorias
 * y controles de teclado para iniciar, detener o conmutar el estado de cada
 * uno de los flujos, así como para habilitar un segmento adicional.
 *
 * Las trayectorias están definidas en coordenadas relativas (0.0–1.0), lo
 * que permite una fácil adaptación a cualquier resolución.
 *
 * Los objetos de tipo Movil podrían usar archivos de imágenes personalizados en
 * vez de los archivos incluidos por defecto. Si se opta por usar objetos PShape
 * se recomienda el uso de archivos de tipo 'Plain SVG' aunque también se pueden
 * usar archivos SVG exportados de Inkscape, Adobe Illustrator u otros. La
 * apariencia visual de los fluidos completamente personalizable mediante el
 * reemplazo de estos archivos.
 *
 */

package com.cpz.processing.Ejemplos.Fluidos;

import com.cpz.processing.Bean.Fluido;
import com.cpz.processing.UI.Label;
import com.cpz.processing.Util.Tools;
import processing.core.PApplet;
import processing.core.PVector;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.cpz.processing.Util.Constantes.AlineaX;
import static com.cpz.processing.Util.Constantes.AlineaY;

/**
 * @author CPZ
 */
public class FluidoSimple extends PApplet {

    private PVector pIniOrigen1, pFinOrigen1;
    private PVector pIniOrigen2, pFinOrigen2;
    private PVector pIniSuma, pFinSuma;
    private PVector pIniExtra, pFinExtra;
    private Fluido fluidoOrigen1, fluidoOrigen2, fluidoSuma, fluidoExtra;
    private int colorFondo1, colorFondo2, colorFondoSuma, colorFondoExtra, colorRelleno, colorOff;
    private Label lbl;

    // CONFIGURACIÓN PREVIA A SETUP ********************************************
    @Override
    public void settings() {
        size(1200, 675, P2D);
        smooth(8);

        // *** COLORES
        colorRelleno = color(255, 0, 100);
        colorOff = color(80, 87, 89);

        // *** TRAYECTORIA - se calcula la trayectoria entre los puntos de inicio y fin de cada segmento
        float distMin = 0f;
        float distMax = 1.777778f; // se asume como distancia máxima la diagonal para una ventana de formato 16:9
        // la cantidad de pasos en las que se divide cada segmento dependiendo de su longitud
        int pasosMin = 50;
        int pasosMax = 900;

        // cálculo para fluido origen 1
        pIniOrigen1 = new PVector(0.05f, 0.05f);
        pFinOrigen1 = new PVector(0.35f, 0.23f);
        PVector normal1 = Tools.calcularNormal(pIniOrigen1, pFinOrigen1);
        float dist1 = PVector.dist(pIniOrigen1, pFinOrigen1);
        int pasos1 = (int) map(dist1, distMin, distMax, pasosMin, pasosMax);
        List<PVector> posiciones1 = Tools.calcularPosicionesEnRecta(pIniOrigen1, pFinOrigen1, pasos1);
        List<PVector> normales1 = new ArrayList<>();
        for (int i = 0; i < posiciones1.size(); i++) {
            normales1.add(normal1);
        }
        fluidoOrigen1 = new Fluido();
        fluidoOrigen1.setSketch(this);
        fluidoOrigen1.setCodigo("fOrigen1");
        fluidoOrigen1.setCantidadMovilesMax(100);
        fluidoOrigen1.setRangDeltaIndPos(1, 3);
        fluidoOrigen1.setColorRelleno(colorRelleno);
        fluidoOrigen1.setRangoAlfaMovil(0.01f, 0.80f);
        fluidoOrigen1.setRangoVelocidadNoise(0.055f, 0.200f, 0.005f);
        fluidoOrigen1.setPeriodo(15);
        fluidoOrigen1.setDesviacionMax(0.005f);
        fluidoOrigen1.setRangoDiametro(0.01f, 0.015f);
        fluidoOrigen1.setPosiciones(posiciones1);
        fluidoOrigen1.setNormales(normales1);
        fluidoOrigen1.setRun(true);

        // cálculo para fluido origen 2
        pIniOrigen2 = new PVector(0.62f, 0.17f);
        pFinOrigen2 = new PVector(0.35f, 0.23f);
        PVector normal2 = Tools.calcularNormal(pIniOrigen2, pFinOrigen2);
        float dist2 = PVector.dist(pIniOrigen2, pFinOrigen2);
        int pasos2 = (int) map(dist2, distMin, distMax, pasosMin, pasosMax);
        List<PVector> posiciones2 = Tools.calcularPosicionesEnRecta(pIniOrigen2, pFinOrigen2, pasos2);
        List<PVector> normales2 = new ArrayList<>();
        for (int i = 0; i < posiciones2.size(); i++) {
            normales2.add(normal2);
        }
        fluidoOrigen2 = new Fluido();
        fluidoOrigen2.setSketch(this);
        fluidoOrigen2.setCodigo("fOrigen2");
        fluidoOrigen2.setCantidadMovilesMax(100);
        fluidoOrigen2.setRangDeltaIndPos(1, 3);
        fluidoOrigen2.setColorRelleno(colorRelleno);
        fluidoOrigen2.setRangoAlfaMovil(0.01f, 0.80f);
        fluidoOrigen2.setRangoVelocidadNoise(0.055f, 0.200f, 0.005f);
        fluidoOrigen2.setPeriodo(15);
        fluidoOrigen2.setDesviacionMax(0.005f);
        fluidoOrigen2.setRangoDiametro(0.01f, 0.015f);
        fluidoOrigen2.setPosiciones(posiciones2);
        fluidoOrigen2.setNormales(normales2);
        fluidoOrigen2.setRun(true);

        // cálculo para fluido suma
        pIniSuma = new PVector(0.35f, 0.23f);
        pFinSuma = new PVector(0.65f, 0.71f);
        PVector normalSuma = Tools.calcularNormal(pIniSuma, pFinSuma);
        float distSuma = PVector.dist(pIniSuma, pFinSuma);
        int pasosSuma = (int) map(distSuma, distMin, distMax, pasosMin, pasosMax);
        List<PVector> posicionesSuma = Tools.calcularPosicionesEnRecta(pIniSuma, pFinSuma, pasosSuma);
        List<PVector> normalesSuma = new ArrayList<>();
        for (int i = 0; i < posicionesSuma.size(); i++) {
            normalesSuma.add(normalSuma);
        }
        fluidoSuma = new Fluido();
        fluidoSuma.setSketch(this);
        fluidoSuma.setCodigo("fSuma");
        fluidoSuma.setCantidadMovilesMax(200);
        fluidoSuma.setRangDeltaIndPos(1, 3);
        fluidoSuma.setColorRelleno(colorRelleno);
        fluidoSuma.setRangoAlfaMovil(0.01f, 0.80f);
        fluidoSuma.setRangoVelocidadNoise(0.055f, 0.200f, 0.005f);
        fluidoSuma.setPeriodo(15);
        fluidoSuma.setDesviacionMax(0.005f);
        fluidoSuma.setRangoDiametro(0.01f, 0.015f);
        fluidoSuma.setPosiciones(posicionesSuma);
        fluidoSuma.setNormales(normalesSuma);
        fluidoSuma.setRun(true);
        fluidoSuma.agregarFluidoOrigen(fluidoOrigen1);
        fluidoSuma.agregarFluidoOrigen(fluidoOrigen2);

        // cálculo para fluido extra
        pIniExtra = new PVector(0.65f, 0.71f);
        pFinExtra = new PVector(0.95f, 0.95f);
        PVector normalExtra = Tools.calcularNormal(pIniExtra, pFinExtra);
        float distExtra = PVector.dist(pIniExtra, pFinExtra);
        int pasosExtra = (int) map(distExtra, distMin, distMax, pasosMin, pasosMax);
        List<PVector> posicionesExtra = Tools.calcularPosicionesEnRecta(pIniExtra, pFinExtra, pasosExtra);
        List<PVector> normalesExtra = new ArrayList<>();
        for (int i = 0; i < posicionesExtra.size(); i++) {
            normalesExtra.add(normalExtra);
        }
        fluidoExtra = new Fluido();
        fluidoExtra.setSketch(this);
        fluidoExtra.setCodigo("fExtra");
        fluidoExtra.setCantidadMovilesMax(200);
        fluidoExtra.setRangDeltaIndPos(1, 3);
        fluidoExtra.setColorRelleno(colorRelleno);
        fluidoExtra.setRangoAlfaMovil(0.01f, 0.80f);
        fluidoExtra.setRangoVelocidadNoise(0.055f, 0.200f, 0.005f);
        fluidoExtra.setPeriodo(15);
        fluidoExtra.setDesviacionMax(0.005f);
        fluidoExtra.setRangoDiametro(0.01f, 0.015f);
        fluidoExtra.setPosiciones(posicionesExtra);
        fluidoExtra.setNormales(normalesExtra);
        fluidoExtra.agregarFluidoOrigen(fluidoSuma);
        fluidoExtra.setRun(false);

        lbl = new Label();
        lbl.setSketch(this);
        lbl.setDisplay(true);
        lbl.setAlineaX(AlineaX.IZQ);
        lbl.setAlineaY(AlineaY.SUP);
        lbl.setColorTexto(color(255));
        lbl.setPos(0.04f * width, 0.25f * height);
        lbl.setTamTexto(22);
    }

    // CONFIGURACIÓN DE VARIABLES **********************************************
    @Override
    public void setup() {
        surface.setTitle("cpzProcessing - Fluido simple");
        frameRate(60);
        textFont(createFont("Consolas", 22));

        // *** IMAGEN - aquí puedes elegir si quieres usar un objeto PImage o PShape
        Object img = loadImage("data" + File.separator + "img" + File.separator + "circuloBlur.png");
        //Object img = loadShape("data" + File.separator + "img" + File.separator + "shape.svg");

        fluidoOrigen1.setImg(img);
        fluidoOrigen2.setImg(img);
        fluidoSuma.setImg(img);
        fluidoExtra.setImg(img);
    }

    // PROGRAMA PRINCIPAL ******************************************************
    @Override
    public void draw() {
        // update
        fluidoOrigen1.update();
        fluidoOrigen2.update();
        fluidoSuma.update();
        fluidoExtra.update();
        updateColoresFondo();
        updateLabel();
        // draw
        dibujarFondo();
        fluidoOrigen1.draw();
        fluidoOrigen2.draw();
        fluidoSuma.draw();
        fluidoExtra.draw();
        lbl.draw();
    }

    private void updateColoresFondo() {
        colorFondo1 = calcularColorFondo(fluidoOrigen1);
        colorFondo2 = calcularColorFondo(fluidoOrigen2);
        colorFondoSuma = calcularColorFondo(fluidoSuma);
        colorFondoExtra = calcularColorFondo(fluidoExtra);
    }

    private int calcularColorFondo(Fluido fluido) {
        if (fluido == null) {
            return 0;
        }
        float alfaFondoMin = 0.15f;
        float alfaFondoMax = 1f;
        float alfaFondo = map(fluido.getFactorLlenado(), 0, 1, alfaFondoMax, alfaFondoMin);
        colorRelleno = color(red(colorRelleno), green(colorRelleno), blue(colorRelleno), alfaFondo * 255);
        colorOff = color(red(colorOff), green(colorOff), blue(colorOff), alfaFondo * 255);
        return lerpColor(colorOff, colorRelleno, fluido.getFactorLlenado());
    }

    private void updateLabel() {
        String s = "";
        s += "Factores de llenado:\n\n";
        s += "fOrigen1: " + String.format("%.2f", fluidoOrigen1.getFactorLlenado()) + " (" + (fluidoOrigen1.isRun() ? "on" : "off") + ")\n";
        s += "fOrigen2: " + String.format("%.2f", fluidoOrigen2.getFactorLlenado()) + " (" + (fluidoOrigen2.isRun() ? "on" : "off") + ")\n";
        s += "fSuma   : " + String.format("%.2f", fluidoSuma.getFactorLlenado()) + " (" + (fluidoSuma.isRun() ? "on" : "off") + ")\n";
        s += "fExtra  : " + String.format("%.2f", fluidoExtra.getFactorLlenado()) + " (" + (fluidoExtra.isRun() ? "on" : "off") + ")\n\n";
        s += "Controles de teclado:\n\n";
        s += "'w' → conmutar estado de ambos fluidos origen\n";
        s += "'e' → iniciar fluido origen 1\n";
        s += "'r' → iniciar fluido origen 2\n";
        s += "'d' → detener fluido origen 1\n";
        s += "'f' → detener fluido origen 2\n";
        s += "'c' → habilitar/deshabilitar segmento extra\n";
        lbl.setTexto(s);
    }

    public void dibujarFondo() {
        background(32);
        strokeWeight(10);
        stroke(colorFondoExtra);
        line(pIniExtra.x * width, pIniExtra.y * height, pFinExtra.x * width, pFinExtra.y * height);
        stroke(colorFondoSuma);
        line(pIniSuma.x * width, pIniSuma.y * height, pFinSuma.x * width, pFinSuma.y * height);
        stroke(colorFondo2);
        line(pIniOrigen2.x * width, pIniOrigen2.y * height, pFinOrigen2.x * width, pFinOrigen2.y * height);
        stroke(colorFondo1);
        line(pIniOrigen1.x * width, pIniOrigen1.y * height, pFinOrigen1.x * width, pFinOrigen1.y * height);
    }

    // INTERRUPCIONES DE PROCESSING ********************************************
    @Override
    public void keyReleased() {
        switch (key) {
            case 'w' -> {
                fluidoOrigen1.conmutarEstado();
                fluidoOrigen2.conmutarEstado();
            }
            case 'e' -> fluidoOrigen1.start();
            case 'r' -> fluidoOrigen2.start();
            case 'd' -> fluidoOrigen1.stop();
            case 'f' -> fluidoOrigen2.stop();
            case 'c' -> fluidoExtra.setRun(!fluidoExtra.isRun());
        }
    }

}
