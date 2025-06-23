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

import com.cpz.processing.Util.Constantes.FluidoEstado;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

import static com.cpz.processing.Util.Constantes.FluidoEstado.*;
import static processing.core.PApplet.constrain;

/**
 * @author CPZ
 */
public class Fluido {

    private final PApplet sketch;
    private PImage img;
    private int cantidadMovilesMax;
    private final List<Movil> lstMoviles;
    private FluidoEstado modo;
    private float velNoiseMin, velNoiseMax, dVelNoise, velNoise;
    private float diametroMin, diametroMax, diametro;
    private float desviacionMax;
    private float alfaMin, alfaMax;
    private int periodo;
    private int dIndPosMin, dIndPosMax;
    private int colorOn;
    private List<PVector> posiciones, normales;
    private boolean running;

    public Fluido(PApplet sketch) {
        this.sketch = sketch;
        lstMoviles = new ArrayList<>();
        modo = VACIO;
    }

    public void update() {
        updateCantidadMoviles();
        lstMoviles.forEach(Movil::update);
    }

    public void draw() {
        lstMoviles.forEach(Movil::draw);
    }

    private void updateCantidadMoviles() {
        if (modo == LLENAR && lstMoviles.size() < cantidadMovilesMax) llenarLista();
        else if (modo == LLENAR && lstMoviles.size() == cantidadMovilesMax) modo = LLENO;
        else if (modo == VACIAR) {
            if (!lstMoviles.isEmpty()) vaciarLista();
            else modo = VACIO;
        }
        running = !lstMoviles.isEmpty();
    }

    public float getFactorLlenado() {
        return (float) lstMoviles.size() / cantidadMovilesMax;
    }

    private void llenarLista() {
        Movil m = new Movil(sketch);
        m.setImg(img);
        m.setDiametro(sketch.random(diametroMin, diametroMax));
        m.setColorRelleno(colorOn);
        m.setDesviacionMax(desviacionMax);
        m.setPeriodo(periodo);
        m.setRangoAlfa(alfaMin, alfaMax);
        m.setVelocidadNoise(velNoise);
        m.setDeltaIndPos((int) sketch.random(dIndPosMin, dIndPosMax));
        m.setLstPos(posiciones);
        m.setLstNormal(normales);
        m.setup();
        lstMoviles.add(m);
    }

    private void vaciarLista() {
        for (int i = lstMoviles.size() - 1; i >= 0; i--) {
            if (!lstMoviles.get(i).isFinRecorrido()) {
                continue;
            }
            lstMoviles.remove(i);
        }
    }

    public void actualizarVelocidadNoisePorDiferencial(String modo) {
        float d = modo.equals("+") ? dVelNoise : -dVelNoise;
        velNoise += d;
        velNoise = constrain(velNoise, velNoiseMin, velNoiseMax);
        lstMoviles.forEach(m -> m.setVelocidadNoise(velNoise));
    }

    public void setVelocidadNoise(float f) {
        velNoise = PApplet.map(f, 0, 1, velNoiseMin, velNoiseMax);
        velNoise = constrain(velNoise, velNoiseMin, velNoiseMax);
        lstMoviles.forEach(m -> m.setVelocidadNoise(velNoise));
    }

    public void conmutarEstadoFluido() {
        if (modo == VACIO || modo == VACIAR) modo = LLENAR;
        else if (modo == LLENO || modo == LLENAR) modo = VACIAR;
    }

    // <editor-fold defaultstate="collapsed" desc="*** setter & getter ***">
    public void setImg(PImage img) {
        this.img = img;
    }

    public void setCantidadMovilesMax(int cantidadMovilesMax) {
        this.cantidadMovilesMax = cantidadMovilesMax;
    }

    public void setDesviacionMax(float desviacionMax) {
        this.desviacionMax = desviacionMax;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public void setNormales(List<PVector> normales) {
        this.normales = normales;
    }

    public void setPosiciones(List<PVector> posiciones) {
        this.posiciones = posiciones;
    }

    public void setRangoVelocidadNoise(float velNoiseMin, float velNoiseMax, float dVelNoise) {
        this.velNoiseMin = velNoiseMin;
        this.velNoiseMax = velNoiseMax;
        this.dVelNoise = dVelNoise;
        this.velNoise = velNoiseMin;
    }

    public void setRangoDiametro(float diametroMin, float diametroMax) {
        this.diametroMin = diametroMin;
        this.diametroMax = diametroMax;
    }

    public void setRangoAlfaMovil(float alfaMin, float alfaMax) {
        this.alfaMin = alfaMin;
        this.alfaMax = alfaMax;
    }

    public void setRangDeltaIndPos(int dIndPosMin, int dIndPosMax) {
        this.dIndPosMin = dIndPosMin;
        this.dIndPosMax = dIndPosMax;
    }

    public void setColorOn(int colorOn) {
        this.colorOn = colorOn;
        lstMoviles.forEach(m -> m.setColorRelleno(colorOn));
    }

    public boolean isRunning() {
        return running;
    }
// </editor-fold>
}