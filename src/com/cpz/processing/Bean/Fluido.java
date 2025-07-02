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

import com.cpz.processing.Interfaces.Identificable;
import org.jetbrains.annotations.NotNull;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

import static processing.core.PApplet.constrain;

/**
 * @author CPZ
 */
public class Fluido implements Identificable {

    private final List<Movil> lstMoviles;
    private final List<Fluido> fluidosOrigen;
    private PApplet sketch;
    private Object img;
    private int cantidadMovilesMax;
    private float velNoiseMin, velNoiseMax, dVelNoise, velNoise;
    private float diametroMin, diametroMax, diametro;
    private float desviacionMax;
    private float alfaMin, alfaMax;
    private float anchoVentana, altoVentana;
    private int periodo;
    private int dIndPosMin, dIndPosMax;
    private int colorRelleno;
    private List<PVector> posiciones, normales;
    private boolean llenar, vaciar, run;
    private String codigo;

    public Fluido() {
        lstMoviles = new ArrayList<>();
        fluidosOrigen = new ArrayList<>();
    }

    public void update() {
        if (!fluidosOrigen.isEmpty() && run) {
            boolean origenFinRecorridoPrimero = false;
            for (Fluido fluido : fluidosOrigen) {
                origenFinRecorridoPrimero = fluido.isFinRecorridoPrimero();
                if (origenFinRecorridoPrimero) break;
            }
            if (origenFinRecorridoPrimero && !isLleno() && !llenar) llenar = true;
            boolean origenVacio = true;
            for (Fluido fluido : fluidosOrigen) {
                origenVacio = origenVacio && fluido.isVacio();
            }
            if (origenVacio && !isVacio() && !vaciar) vaciar = true;
        }
        if (llenar && run) agregarMovil();
        if (vaciar) eliminarMovilesAlLlegar();
        lstMoviles.forEach(Movil::update);
    }

    public void draw() {
        lstMoviles.forEach(Movil::draw);
    }

    public void start() {
        if (!isLleno() && !llenar) {
            llenar = true;
            vaciar = false;
        }
    }

    public void stop() {
        if (!isVacio() && !vaciar) {
            vaciar = true;
            llenar = false;
        }
    }

    public void conmutarEstado() {
        if (!run) return;
        if (isRunning()) stop();
        else start();
    }

    public float getFactorLlenado() {
        return (float) lstMoviles.size() / cantidadMovilesMax;
    }

    private void agregarMovil() {
        Movil m = new Movil(sketch);
        m.setImg(img);
        m.setDiametro(sketch.random(diametroMin, diametroMax));
        m.setColorRelleno(colorRelleno);
        m.setDesviacionMax(desviacionMax);
        m.setPeriodo(periodo);
        m.setRangoAlfa(alfaMin, alfaMax);
        m.setVelocidadNoise(velNoise);
        m.setDeltaIndPos((int) sketch.random(dIndPosMin, dIndPosMax));
        m.setLstPos(posiciones);
        m.setLstNormal(normales);
        m.setDimensionesVentana(anchoVentana, altoVentana);
        m.setup();
        lstMoviles.add(m);
        if (lstMoviles.size() == cantidadMovilesMax) llenar = false;
    }

    public void eliminarMovilesAlLlegar() {
        if (lstMoviles.isEmpty()) {
            vaciar = false;
            return;
        }
        for (int i = lstMoviles.size() - 1; i >= 0; i--) {
            if (lstMoviles.get(i).isFinRecorrido()) {
                lstMoviles.remove(i);
            }
        }
    }

    public boolean isLleno() {
        return lstMoviles.size() == cantidadMovilesMax;
    }

    public boolean isVacio() {
        return lstMoviles.isEmpty();
    }

    public void actualizarVelocidadNoisePorDiferencial(@NotNull String modo) {
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

    public boolean isFinRecorridoPrimero() {
        for (Movil m : lstMoviles) {
            if (m.isFinRecorrido()) return true;
        }
        return false;
    }

    // <editor-fold defaultstate="collapsed" desc="*** setter & getter ***">
    public void setImg(Object img) {
        this.img = img;
        lstMoviles.forEach(m -> m.setImg(img));
    }

    public boolean isLlenar() {
        return llenar;
    }

    public boolean isVaciar() {
        return vaciar;
    }

    public void setSketch(PApplet sketch) {
        this.sketch = sketch;
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

    public void setColorRelleno(int colorRelleno) {
        this.colorRelleno = colorRelleno;
        lstMoviles.forEach(m -> m.setColorRelleno(colorRelleno));
    }

    public boolean isRunning() {
        return !lstMoviles.isEmpty();
    }

    @Override
    public String getCodigo() {
        return codigo;
    }

    @Override
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void agregarFluidoOrigen(Fluido fluidoOrigen) {
        fluidosOrigen.add(fluidoOrigen);
    }

    public void setRun(boolean run) {
        this.run = run;
        if (!run) vaciar = true;
    }

    public boolean isRun() {
        return run;
    }

    public List<Fluido> getFluidosOrigen() {
        return fluidosOrigen;
    }

    public void setDimensionesVentana(float anchoVentana, float altoVentana) {
        this.anchoVentana = anchoVentana;
        this.altoVentana = altoVentana;
        lstMoviles.forEach(m -> m.setDimensionesVentana(anchoVentana, altoVentana));
    }
// </editor-fold>
}