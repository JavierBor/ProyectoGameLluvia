package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Sol implements Elemento{
	private Rectangle area; 
    private Texture textura;

    public Sol(float x, float y, float ancho, float alto) {
        this.area = new Rectangle(x, y, ancho, alto);
        this.textura = new Texture(Gdx.files.internal("sol.png"));
    }
    
    public Rectangle getArea() {
        return area; //Obtener su area
    }
    
    @Override
	public void mover(float deltaTime) {
		area.y -= 300 * deltaTime;		
	}
    
	@Override
	public void aplicarEfecto(Tarro tarro) {
		tarro.aumentarVida();		
	}

	@Override
    public boolean fueraPantalla() {
    	return area.y + area.height < 0; //Verificar si salió de la pantalla
    }

	@Override
	public boolean colision(Tarro tarro) {
		return area.overlaps(tarro.getArea()); //Verificar si chocó con el tarro
	}

	@Override
	public void dibujar(SpriteBatch batch) {
		batch.draw(textura, area.x, area.y); //Actualizar su sprite
	}
}
