package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Rayo extends Elemento implements ElementoEfecto{

    public Rayo(float x, float y, float ancho, float alto) {
        super(x, y, ancho, alto, new Texture(Gdx.files.internal("rayo.png")));
        
    }

    @Override
    public void mover(float deltaTime) {
    	Rectangle area = getArea();
        area.y -= 500 * deltaTime;
    }
    
	@Override
    public void aplicarEfecto(Tarro tarro) {
        tarro.otorgarInmunidad(6);  //Otorga inmunidad por 6 segundos
    }
}
