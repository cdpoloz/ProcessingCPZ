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

package Input;

import Bean.PerlinVector;
import Interfaces.MouseInput;
import UI.Barra;
import UI.Switch;
import Util.Calc;
import Util.Constantes.BarraOrientacion;
import Util.Constantes.Esquina;
import processing.core.PApplet;

import java.math.BigDecimal;

public class Mouse implements MouseInput {

    @Override
    public boolean mouseWheel(int d, Object... o) {
        if (o != null
                && o.length == 1
                && o[0] instanceof Barra bar) {
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
        if (o != null
                && o.length == 1
                && o[0] instanceof Switch sw
                && mouseButton == PApplet.LEFT) {
            sw.conmutarEstado();
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseDragged(int mouseButton, Object... o) {
        if (o != null
                && o.length == 1
                && o[0] instanceof Barra bar
                && mouseButton == PApplet.LEFT) {
            mouseDraggedLeftBarra(bar);
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseMoved() {
        return false;
    }

    private void mouseDraggedLeftBarra(Barra bar) {
        BigDecimal posMin, posMax;
        BigDecimal valorMin = bar.getValorMin();
        BigDecimal valorMax = bar.getValorMax();
        BigDecimal valor;
        if (bar.getOrientacion() == BarraOrientacion.HORIZONTAL) {
            posMin = new BigDecimal(bar.getEsquina(Esquina.SUP_IZQ).x);
            posMax = new BigDecimal(bar.getEsquina(Esquina.SUP_DER).x);
            valor = Calc.map(new BigDecimal(bar.getSketch().mouseX), posMin, posMax, valorMin, valorMax);
        } else {
            posMax = new BigDecimal(bar.getEsquina(Esquina.SUP_IZQ).y);
            posMin = new BigDecimal(bar.getEsquina(Esquina.INF_IZQ).y);
            valor = Calc.map(new BigDecimal(bar.getSketch().mouseY), posMin, posMax, valorMin, valorMax);
        }
        bar.setValor(valor);
    }

    private void updatePerlinBar(Barra bar, PerlinVector perlin) {
        float vel = PApplet.map(bar.getValor().floatValue(), 0, 1, 0.002f, 0.008f);
        perlin.setVelocidad(vel);
    }

}
