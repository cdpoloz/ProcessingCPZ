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

package com.cpz.processing.Bean;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

import static processing.core.PApplet.constrain;

/**
 * @author CPZ
 */
public class Fluido {

    private final PApplet sketch;
    private final List<Movil> lstMovil;
    private final Timer timer;
    private String modo;
    private PImage img;
    private int colorRelleno, periodo, cantidadMovilesMax;
    private float alfaMin, alfaMax;
    private float velocidadNoise, velocidadNoiseMin, velocidadNoiseMax, dVelocidadNoise;
    private float desviacionMax, diametroMin, diametroMax;
    private final List<PVector> lstPos, lstNormal;

    public Fluido(PApplet sketch) {
        this.sketch = sketch;
        lstMovil = new ArrayList<>();
        lstPos = new ArrayList<>();
        lstNormal = new ArrayList<>();
        timer = new Timer();
        timer.setSketch(sketch);
        modo = "listaVacia";
    }

    public void update() {
        updateContenidoLista();
        lstMovil.forEach(Movil::update);
    }

    private void updateContenidoLista() {
        if (!timer.isRunning()) {
            return;
        }
        if (modo.equals("llenarLista")) {
            llenarLista();
        } else if (modo.equals("vaciarLista")) {
            vaciarLista();
        }
    }

    private void llenarLista() {
        if (!timer.periodoPulso()) {
            return;
        }
        if (lstMovil.size() == cantidadMovilesMax) {
            timer.stop();
            modo = "listaLlena";
            return;
        }
        PVector normal = lstNormal.get(lstMovil.size());
        Movil movil = new Movil(sketch);
        movil.setColorRelleno(colorRelleno);
        movil.setDesviacionMax(desviacionMax);
        movil.setImg(img);
        movil.setLstPos(lstPos);
        movil.setNormal(normal);
        movil.setPeriodo(periodo);
        movil.setRangoAlfa(alfaMin, alfaMax);
        movil.setVelocidadNoise(velocidadNoise);
        movil.setDiametro(sketch.random(diametroMin, diametroMax));
        movil.setup();
        lstMovil.add(movil);
    }

    private void vaciarLista() {
        if (!timer.periodoPulso()) {
            return;
        }
        if (lstMovil.isEmpty()) {
            timer.stop();
            modo = "listaVacia";
            return;
        }
        for (int i = lstMovil.size() - 1; i >= 0; i--) {
            if (!lstMovil.get(i).isFinRecorrido()) {
                continue;
            }
            lstMovil.remove(i);
        }
    }

    public void draw() {
        lstMovil.forEach(Movil::draw);
    }

    public void updateVelocidad(String modoCambioVelocidad) {
        if (modoCambioVelocidad.equals("+")) {
            velocidadNoise += dVelocidadNoise;
        } else {
            velocidadNoise -= dVelocidadNoise;
        }
        velocidadNoise = constrain(velocidadNoise, velocidadNoiseMin, velocidadNoiseMax);
        lstMovil.forEach(m -> m.setVelocidadNoise(velocidadNoise));
    }

    public int getCantidadMoviles() {
        return lstMovil.size();
    }

    public void setRangoDiametro(float diametroMin, float diametroMax) {
        this.diametroMin = diametroMin;
        this.diametroMax = diametroMax;
    }

    public void setColorRelleno(int colorRelleno) {
        this.colorRelleno = colorRelleno;
    }

    public void setDesviacionMaxima(float desviacionMax) {
        this.desviacionMax = desviacionMax;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public void setRangoAlfa(float alfaMin, float alfaMax) {
        this.alfaMin = alfaMin;
        this.alfaMax = alfaMax;
    }

    public void setRangoVelocidadNoise(float velocidadNoiseMin, float velocidadNoiseMax, float dVelocidadNoise) {
        this.velocidadNoiseMin = velocidadNoiseMin;
        this.velocidadNoiseMax = velocidadNoiseMax;
        this.dVelocidadNoise = dVelocidadNoise;
        velocidadNoise = velocidadNoiseMin;
    }

    public void setCantidadMovilesMax(int cantidadMovilesMax) {
        this.cantidadMovilesMax = cantidadMovilesMax;
    }

    public int getCantidadMovilesMax() {
        return cantidadMovilesMax;
    }

    public void addPos(PVector pos, PVector normal) {
        lstPos.add(pos);
        lstNormal.add(normal);
    }

    public void updateModo() {
        switch(modo) {
            case "listaLlena" -> {
                modo = "vaciarLista";
                timer.iniciar(10);
            }
            case "listaVacia" -> {
                modo = "llenarLista";
                timer.iniciar(10);
            }
            case "vaciarLista" ->
                    modo = "llenarLista";
            case "llenarLista" ->
                    modo = "vaciarLista";
        }
    }

    public void setImg(PImage img) {
        this.img = img;
    }

    public boolean isRunning() {
        return !lstMovil.isEmpty();
    }
}