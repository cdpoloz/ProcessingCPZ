package Input;

import Interfaces.TecladoInput;

import static Util.Constantes.BARRA_ESPACIADORA;

/**
 * @author CPZ
 */
public class Teclado implements TecladoInput {

    @Override
    public boolean keyReleased(char key, int keyCode, Object... o) {
        switch (key) {
            case 'q' -> {
            }
        }
        switch (keyCode) {
            case BARRA_ESPACIADORA -> {
            }
        }
        return false;
    }

    @Override
    public boolean keyPressed(char key, int keyCode, Object... o) {
        switch (key) {
            case 'q' -> {
            }
        }
        switch (keyCode) {
            case BARRA_ESPACIADORA -> {
            }
        }
        return false;
    }
}
