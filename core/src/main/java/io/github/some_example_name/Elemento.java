package io.github.some_example_name;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Elemento {
	public void aplicarEfecto(Tarro tarro);
	public void mover(float deltaTime);
	public boolean colision(Tarro tarro);
	public boolean fueraPantalla();
	public void dibujar(SpriteBatch batch);
}
