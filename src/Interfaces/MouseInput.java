package Interfaces;

/**
 * @author CPZ
 */
public interface MouseInput {

    boolean mouseWheel(int d, Object... o);

    boolean mouseClicked(int mouseButton, Object... o);
    
    boolean mouseReleased(int mouseButton, Object... o);
    
    boolean mouseDragged(int mouseButton, Object... o);
    
    boolean mouseMoved();

}
