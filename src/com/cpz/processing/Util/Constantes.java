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

package com.cpz.processing.Util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Constantes {

    public enum BarraModo {
        INTERACTIVO, INDICADOR
    };
    public enum Orientacion {
        HORIZONTAL, VERTICAL
    };
    public enum AlineaX {
        IZQ, CENTRO, DER
    };
    public enum AlineaY {
        SUP, CENTRO, INF
    };
    public enum Esquina {
        SUP_IZQ, SUP_DER, INF_IZQ, INF_DER
    };
    public enum ColorComponente {
        RED, GREEN, BLUE, ALPHA
    };
    public enum FluidoEstado {
        LLENAR, LLENO, VACIAR, VACIO
    }
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
    public static final BigDecimal MIL = new BigDecimal(1000);
    public static final int COLOR_TEST = Tools.construirColor(255, 0, 120, 255);
    
}
