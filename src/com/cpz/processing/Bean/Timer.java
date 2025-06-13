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

public class Timer {

    private PApplet sketch;
    private int id;
    private long tiempoInicio, tiempoActual, tiempoOn;
    private int periodo;
    private float dutyCycle;
    private boolean running;
    private String codigo, descripcion;

    public void iniciar() {
        tiempoInicio = sketch.millis();
        running = true;
    }

    public void iniciar(int periodo) {
        this.periodo = periodo;
        tiempoInicio = sketch.millis();
        running = true;
    }

    public void iniciar(int periodo, float dutyCycle) {
        this.periodo = periodo;
        this.dutyCycle = dutyCycle;
        tiempoInicio = sketch.millis();
        running = true;
    }

    public void update() {
        tiempoActual = sketch.millis();
    }

    public boolean periodoPulso() {
        tiempoActual = sketch.millis();
        if (tiempoActual - tiempoInicio > periodo) {
            tiempoInicio = tiempoActual;
            return true;
        } else {
            return false;
        }
    }

    public boolean periodoFin() {
        return tiempoActual - tiempoInicio > periodo;
    }

    public boolean periodoOnOff() {
        if (tiempoActual - tiempoInicio > 2L * periodo) {
            tiempoInicio = tiempoActual;
        }
        return tiempoActual - tiempoInicio < periodo;
    }

    public boolean periodoDC() {
        tiempoOn = (int) (periodo * this.dutyCycle);
        if (tiempoActual - tiempoInicio > periodo) {
            tiempoInicio = tiempoActual;
        }
        return tiempoActual - tiempoInicio < tiempoOn;
    }

    public boolean periodoDC(float dutyCycle) {
        this.dutyCycle = PApplet.constrain(dutyCycle, 0, 1);
        tiempoOn = (int) (periodo * this.dutyCycle);
        if (tiempoActual - tiempoInicio > periodo) {
            tiempoInicio = tiempoActual;
        }
        return tiempoActual - tiempoInicio < tiempoOn;
    }

    public int getTiempoTranscurrido() {
        return (int) (tiempoActual - tiempoInicio);
    }

    public void stop() {
        dutyCycle = 0;
        tiempoInicio = 0;
        tiempoActual = 0;
        tiempoOn = 0;
        running = false;
    }

    // <editor-fold defaultstate="collapsed" desc="*** setter & getter ***">
    public boolean isRunning() {
        return running;
    }

    public void setDutyCycle(float dutyCycle) {
        this.dutyCycle = dutyCycle;
    }

    public void setSketch(PApplet sketch) {
        this.sketch = sketch;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public int getPeriodo() {
        return periodo;
    }
    // </editor-fold>

}
