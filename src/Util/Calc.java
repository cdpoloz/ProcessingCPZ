package Util;

import static Util.Constantes.MATH_CONTEXT;
import java.math.BigDecimal;

/**
 * @author CPZ
 */
public class Calc {

    public static BigDecimal map(
            BigDecimal numero,
            BigDecimal min,
            BigDecimal max,
            BigDecimal nuevoMin,
            BigDecimal nuevoMax) {
        BigDecimal numerador = numero.subtract(min).multiply(nuevoMax.subtract(nuevoMin));
        BigDecimal denominador = max.subtract(min);
        if (denominador.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal resultado = numerador.divide(denominador, MATH_CONTEXT).add(nuevoMin);
        return resultado;
    }
}
