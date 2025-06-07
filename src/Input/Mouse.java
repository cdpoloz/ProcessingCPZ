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

import static Util.Constantes.MATH_CONTEXT;

/**
 * @author CPZ
 */
public class Mouse implements MouseInput {

    @Override
    public boolean mouseWheel(int d, Object... o) {
        if (o != null
                && o.length == 2
                && o[0] instanceof Barra bar
                && o[1] instanceof PerlinVector perlin) {
            bar.setValorPorMouse(d);
            updatePerlinBar(bar, perlin);
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
                && o.length == 3
                && o[0] instanceof Switch sw
                && o[1] instanceof Barra bar
                && o[2] instanceof PerlinVector perlin
                && mouseButton == PApplet.LEFT) {
            sw.conmutarEstado();
            bar.setValor(sw.isOn() ? bar.getValorMax().multiply(new BigDecimal("0.5")) : BigDecimal.ZERO);
            updatePerlinBar(bar, perlin);
            return true;
        }
        if (o != null
                && o.length == 2
                && o[0] instanceof Barra bar
                && o[1] instanceof PerlinVector perlin
                && mouseButton == PApplet.RIGHT) {
            bar.setValor(bar.getValor().compareTo(BigDecimal.ZERO) > 0 ? BigDecimal.ZERO : bar.getValorMax());
            updatePerlinBar(bar, perlin);
            return true;
        }
        if (o != null
                && o.length == 2
                && o[0] instanceof Barra bar
                && o[1] instanceof PerlinVector perlin
                && mouseButton == PApplet.CENTER) {
            float tercio = bar.getValorMax().divide(new BigDecimal(3), MATH_CONTEXT).floatValue();
            float valor = bar.getValor().floatValue();
            if (valor == 1) {
                valor = 0;
                bar.setValor(new BigDecimal(valor));
                updatePerlinBar(bar, perlin);
                return true;
            }
            if (valor < tercio) {
                valor = tercio;
            } else if (valor >= tercio && valor < tercio * 2) {
                valor = tercio * 2;
            } else if (valor >= tercio * 2) {
                valor = 1;
            }
            bar.setValor(new BigDecimal(valor));
            updatePerlinBar(bar, perlin);
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseDragged(int mouseButton, Object... o) {
        if (o != null
                && o.length == 2
                && o[0] instanceof Barra bar
                && o[1] instanceof PerlinVector perlin
                && mouseButton == PApplet.LEFT) {
            mouseDraggedLeftBarra(bar);
            updatePerlinBar(bar, perlin);
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
