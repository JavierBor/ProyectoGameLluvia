package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class GotaBuena extends Elemento{
	public GotaBuena(float x, float y, float ancho, float alto) {
		super(x, y, ancho, alto, new Texture(Gdx.files.internal("drop.png")));
	}
	
	public void dibujar(SpriteBatch batch){
		Rectangle area = getArea();
		batch.draw(getTextura(), area.x, area.y);
	}
}