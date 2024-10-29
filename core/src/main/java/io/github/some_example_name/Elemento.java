package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Elemento {
	private Rectangle area; 
    private Texture textura;

    public Elemento(float x, float y, float ancho, float alto, Texture textura) {
        this.area = new Rectangle(x, y, ancho, alto);
        this.textura = textura;
    }
    public Texture getTextura() {
    	return textura;
    }
    public Rectangle getArea() {
        return area;
    }

    //Implementación común de movimiento para todos los elementos
    public void mover(float deltaTime) {
        area.y -= 300 * deltaTime;
    }
    
    public abstract void dibujar(SpriteBatch batch);
    
    public boolean fueraPantalla() {
    	return area.y + area.height < 0; //Verificar si salió de la pantalla
    }

	public boolean colision(Tarro tarro) {
		return area.overlaps(tarro.getArea()); //Verificar si chocó con el tarro
	}
}
