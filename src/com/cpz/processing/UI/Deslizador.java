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

package com.cpz.processing.UI;

import com.cpz.processing.Interfaces.Identificable;
import com.cpz.processing.Util.Calc;
import com.cpz.processing.Util.Constantes.BarraModo;
import com.cpz.processing.Util.Constantes.Esquina;
import com.cpz.processing.Util.Constantes.Orientacion;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

import java.math.BigDecimal;

public class Deslizador implements Identificable {

    private PApplet sketch;
    private String codigo;
    private final Barra bar;
    private final Switch sw;

    public Deslizador() {
        bar = new Barra();
        bar.setDisplay(true);
        bar.setEditable(true);
        bar.setD(new BigDecimal("0.01"));
        sw = new Switch();
        sw.setDisplay(true);
        sw.setEditable(true);
        sw.setShowHover(true);
    }

    public void update() {
        bar.update();
        float x, y;
        if (bar.getOrientacion() == Orientacion.VERTICAL) {
            x = bar.getEsquina(Esquina.INF_IZQ).x + bar.getAnchoMax() * 0.5f - sw.getAncho() * 0.5f;
            y = bar.getEsquina(Esquina.INF_IZQ).y - PApplet.map(bar.getValor().floatValue(), 0, 1, 0, bar.getAltoMax()) - sw.getAlto() * 0.5f;
        } else {
            x = bar.getEsquina(Esquina.INF_IZQ).x + PApplet.map(bar.getValor().floatValue(), 0, 1, 0, bar.getAnchoMax()) - sw.getAncho() * 0.5f;
            y = bar.getEsquina(Esquina.INF_IZQ).y - bar.getAltoMax() * 0.5f - sw.getAlto() * 0.5f;
        }
        sw.setPos(x, y);
        sw.update();
        sw.setOn(bar.isHovering() && sketch.mousePressed);
    }

    public void draw() {
        bar.draw();
        sw.draw();
    }

    public boolean isHovering() {
        return bar.isHovering();
    }

    public void mouseWheel(int d) {
        bar.setValorPorMouse(d);
    }

    public void mouseDragged() {
        BigDecimal posMin, posMax;
        BigDecimal valorMin = bar.getValorMin();
        BigDecimal valorMax = bar.getValorMax();
        BigDecimal valor;
        if (bar.getOrientacion() == Orientacion.HORIZONTAL) {
            posMin = new BigDecimal(bar.getEsquina(Esquina.SUP_IZQ).x);
            posMax = new BigDecimal(bar.getEsquina(Esquina.SUP_DER).x);
            valor = Calc.map(new BigDecimal(sketch.mouseX), posMin, posMax, valorMin, valorMax);
        } else {
            posMax = new BigDecimal(bar.getEsquina(Esquina.SUP_IZQ).y);
            posMin = new BigDecimal(bar.getEsquina(Esquina.INF_IZQ).y);
            valor = Calc.map(new BigDecimal(sketch.mouseY), posMin, posMax, valorMin, valorMax);
        }
        bar.setValor(valor);
    }

    // <editor-fold defaultstate="collapsed" desc="*** setter & getter ***">
    @Override
    public String getCodigo() {
        return codigo;
    }

    @Override
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setSketch(PApplet sketch) {
        this.sketch = sketch;
        bar.setSketch(sketch);
        sw.setSketch(sketch);
    }

    public void setMedidasBarra(float ancho, float alto) {
        bar.setMedidas(ancho, alto);
    }

    public void setMedidasHandle(float ancho, float alto) {
        sw.setAncho(ancho);
        sw.setAlto(alto);
    }

    public void setColoresBarra(int cOff, int cIni, int cFin) {
        bar.setColorOff(cOff);
        bar.setColorInicial(cIni);
        bar.setColorFinal(cFin);
    }

    public void setColoresHandle(int cRelleno, int cHover) {
        sw.setColorOn(cRelleno);
        sw.setColorOff(cRelleno);
        sw.setColorHover(cHover);
    }

    public void setOrientacion(Orientacion orientacion) {
        bar.setOrientacion(orientacion);
    }

    public void setModo(BarraModo modo) {
        bar.setModo(modo);
    }

    public void setPos(float x, float y) {
        bar.setPos(x, y);
    }

    public void setPos(PVector pos) {
        bar.setPos(pos);
    }

    public void setShapeFondo(PShape shape) {
        bar.setShape(shape);
    }

    public void setShapeHandle(PShape shape) {
        sw.setShape(shape);
    }

    public BigDecimal getValor() {
        return bar.getValor();
    }
// </editor-fold>
}
