package Util;

// <editor-fold defaultstate="collapsed" desc="*** import ***">
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
// </editor-fold>

/**
 * @author CPZ
 */
public class Constantes {

    public static enum BarraModo {
        INTERACTIVO, INDICADOR
    };
    public static enum BarraOrientacion {
        HORIZONTAL, VERTICAL
    };
    public static enum LabelAlineaX {
        IZQ, CENTRO, DER
    };
    public static enum LabelAlineaY {
        SUP, CENTRO, INF
    };
    public static enum Esquina {
        SUP_IZQ, SUP_DER, INF_IZQ, INF_DER
    };
    public static enum ColorComponente {
        RED, GREEN, BLUE, ALPHA
    };
    public static final MathContext MATH_CONTEXT = new MathContext(15, RoundingMode.HALF_UP);
    public static final int BARRA_ESPACIADORA = 32;
    public static final int BACKSPACE = 8;
    public static final int FLECHA_IZQUIERDA = 37;
    public static final int FLECHA_ARRIBA = 38;
    public static final int FLECHA_DERECHA = 39;
    public static final int FLECHA_ABAJO = 40;
    public static final int TECLA_MAS = 139;
    public static final int TECLA_MENOS = 140;
    public static final BigDecimal CIEN = new BigDecimal(100);
    public static final int COLOR_TEST = Tools.construirColor(255, 0, 120, 255);
    
}
