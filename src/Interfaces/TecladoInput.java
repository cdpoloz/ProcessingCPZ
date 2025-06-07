package Interfaces;

/**
 * @author CPZ
 */
public interface TecladoInput {

    boolean keyReleased(char key, int keyCode, Object... o);

    boolean keyPressed(char key, int keyCode, Object... o);
    
}
