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

    private PVector p0, p1, p2;
    private int colorFondo;
    private Fluido fluido;


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
        // cálculo de trayectoria (solo si es que se quiere hacer en runtime)
        float distMin = 0f;
        float distMax = 2.236068f;
        p0 = new PVector(random(1), random(1));
        p1 = new PVector(random(1), random(1));
        p2 = new PVector(random(1), random(1));
        PVector normal01 = Tools.calcularNormal(p0, p1);
        PVector normal12 = Tools.calcularNormal(p1, p2);
        float dist01 = PVector.dist(p0, p1);
        float dist12 = PVector.dist(p1, p2);
        float distTotal = dist01 + dist12;
        int pasosTotal = (int) map(distTotal, distMin, distMax, 50, 800);
        int pasos01 = (int) (dist01 * pasosTotal / distTotal);
        int pasos12 = pasosTotal - pasos01;
        List<PVector> posiciones = new ArrayList<>();
        List<PVector> normales = new ArrayList<>();
        for (int i = 0; i < pasos01; i++) {
            PVector pos = PVector.lerp(p0, p1, (float) i / pasos01);
            posiciones.add(new PVector(pos.x, pos.y));
            normales.add(new PVector(normal01.x, normal01.y));
        }
        for (int i = 0; i < pasos12; i++) {
            PVector pos = PVector.lerp(p1, p2, (float) i / pasos12);
            posiciones.add(new PVector(pos.x, pos.y));
            normales.add(new PVector(normal12.x, normal12.y));
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < posiciones.size(); i++) {
            PVector p = posiciones.get(i);
            PVector n = normales.get(i);
            String s = String.format("%,6f", p.x) + "," + String.format("%,6f", p.y) + "," + String.format("%,6f", n.x) + "," + String.format("%,6f", n.y);
            sb.append(s);
            if (i < posiciones.size() - 1) {
                sb.append(";");
            }
        }
        System.out.println(sb);
        // imagen
        PImage img = loadImage("data" + File.separator + "img" + File.separator + "circuloBlur.png");
        // fluido
        fluido = new Fluido(this);
        fluido.setImg(img);
        fluido.setCantidadMovilesMax(250);
        fluido.setRangDeltaIndPos(1, 3);
        fluido.setRangoColores(color(80, 87, 89), color(255, 0, 120));
        fluido.setRangoAlfaMovil(0.01f, 0.80f);
        fluido.setRangoAlfaFondo(0.06f, 1f);
        fluido.setRangoVelocidadNoise(0.055f, 0.200f, 0.005f);
        fluido.setPeriodo(15);
        fluido.setDesviacionMax(0.01f);
        fluido.setRangoDiametro(0.01f, 0.025f);
        fluido.setPosiciones(posiciones);
        fluido.setNormales(normales);
    }

    // PROGRAMA PRINCIPAL ******************************************************
    @Override
    public void draw() {
        // update
        fluido.update();
        colorFondo = fluido.getColorFondo();
        // draw
        dibujarFondo();
        fluido.draw();
    }

    public void dibujarFondo() {
        background(32);
        strokeWeight(10);
        stroke(colorFondo);
        line(p0.x * width, p0.y * height, p1.x * width, p1.y * height);
        line(p1.x * width, p1.y * height, p2.x * width, p2.y * height);
    }

    // INTERRUPCIONES DE PROCESSING ********************************************
    @Override
    public void mouseReleased() {
        fluido.conmutarEstadoFluido();
    }

    @Override
    public void mouseWheel(MouseEvent event) {
        fluido.actualizarVelocidadNoisePorDiferencial(event.getCount() < 0 ? "+" : "-");
    }

    @Override
    public void keyReleased() {
        switch (keyCode) {
            case BARRA_ESPACIADORA -> fluido.conmutarEstadoFluido();
            case FLECHA_ARRIBA -> fluido.actualizarVelocidadNoisePorDiferencial("+");
            case FLECHA_ABAJO -> fluido.actualizarVelocidadNoisePorDiferencial("-");
            case FLECHA_DERECHA -> fluido.setVelocidadNoise(1f);
            case FLECHA_IZQUIERDA -> fluido.setVelocidadNoise(0);
        }
    }

}
