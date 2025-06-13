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

package com.cpz.processing.Ejemplos.SimpleUI.Input;

import com.cpz.processing.Interfaces.Input.InputMouse;
import com.cpz.processing.UI.Barra;
import com.cpz.processing.UI.Switch;
import com.cpz.processing.Util.Calc;
import com.cpz.processing.Util.Constantes;
import processing.core.PApplet;

import java.math.BigDecimal;

public class Mouse implements InputMouse {

    @Override
    public boolean mouseWheel(int d, Object... o) {
        if (o != null
                && o.length == 1
                && o[0] instanceof Barra bar
                && bar.isHovering()) {
            bar.setValorPorMouse(d);
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseClicked(int mouseButton, Object... o) {
        return false;
    }

    @Override
    public boolean mouseReleased(int mouseButton, Object... o) {
        if (mouseButton == PApplet.LEFT
                && o != null
                && o.length == 1
                && o[0] instanceof Switch sw
                && sw.isHovering()) {
            sw.conmutarEstado();
            return true;
        }
        if (mouseButton == PApplet.RIGHT
                && o != null
                && o.length == 1
                && o[0] instanceof Barra bar) {
            BigDecimal valor = bar.getValor();
            if (valor.compareTo(BigDecimal.ZERO) > 0) {
                bar.setValor(BigDecimal.ZERO);
                return true;
            }
            bar.setValor(BigDecimal.ONE);
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseDragged(int mouseButton, Object... o) {
        if (o != null
                && o.length == 1
                && o[0] instanceof Barra bar
                && bar.isHovering()) {
            mouseDraggedLeftBarra(bar);
            return true;
        }
        return false;
    }

    private void mouseDraggedLeftBarra(Barra bar) {
        BigDecimal posMin, posMax;
        BigDecimal valorMin = bar.getValorMin();
        BigDecimal valorMax = bar.getValorMax();
        BigDecimal valor;
        if (bar.getOrientacion() == Constantes.Orientacion.HORIZONTAL) {
            posMin = new BigDecimal(bar.getEsquina(Constantes.Esquina.SUP_IZQ).x);
            posMax = new BigDecimal(bar.getEsquina(Constantes.Esquina.SUP_DER).x);
            valor = Calc.map(new BigDecimal(bar.getSketch().mouseX), posMin, posMax, valorMin, valorMax);
        } else {
            posMax = new BigDecimal(bar.getEsquina(Constantes.Esquina.SUP_IZQ).y);
            posMin = new BigDecimal(bar.getEsquina(Constantes.Esquina.INF_IZQ).y);
            valor = Calc.map(new BigDecimal(bar.getSketch().mouseY), posMin, posMax, valorMin, valorMax);
        }
        bar.setValor(valor);
    }

    @Override
    public boolean mouseMoved() {
        return false;
    }
}
