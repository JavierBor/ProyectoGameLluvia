package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Nieve extends Elemento implements ElementoEfecto {

    private static final float FACTOR_REDUCCION_VELOCIDAD = 0.3f; // Reduce la velocidad en un 50%
    private static final float DURACION_REDUCCION = 5; // Duración de la ralentización en segundos

    public Nieve(float x, float y, float ancho, float alto) {
        super(x, y, ancho, alto, new Texture(Gdx.files.internal("nieve.png")));
        
    }
    
    @Override
    public void dibujar(SpriteBatch batch){
		Rectangle area = getArea();
		batch.draw(getTextura(), area.x, area.y);
	}

    @Override
    public void mover(float deltaTime) {
        Rectangle area = getArea();
        area.y -= 550 * deltaTime; 
    }

    @Override
    public void aplicarEfecto(Tarro tarro) {
        tarro.reducirVelocidad(FACTOR_REDUCCION_VELOCIDAD, DURACION_REDUCCION); // Aplica el efecto de ralentización
    }
    
}
