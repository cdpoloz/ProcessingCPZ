package Interfaces;

/**
 * @author CPZ
 */
public interface ElementoUI {

    void setCodigo(String codigo);

    String getCodigo();

    void update();

    void updateHover();

    void draw();
    
    void setDisplay(boolean display);

    boolean isHovering();

    boolean isFlancoSubida();

    boolean isFlancoBajada();
    
    boolean isCambioValor();
    
    void setShowHover(boolean showHover);
    
    void setEditable(boolean editable);
    
    boolean isEditable();

}
