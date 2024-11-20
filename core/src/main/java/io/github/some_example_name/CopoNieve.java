package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class CopoNieve extends Elemento implements ElementoEfecto {

    private static final float FACTOR_REDUCCION_VELOCIDAD = 0.3f; //Reduce la velocidad en un 50%
    private static final float DURACION_REDUCCION = 5; //Duración de la ralentización en segundos
    private Sound breakingSound;
    private Sound freezingSound;
    private boolean moverDerecha;

    public CopoNieve(float x, float y, float ancho, float alto) {
        super(x, y, ancho, alto, new Texture(Gdx.files.internal("copoNieve.png")));
        breakingSound = Gdx.audio.newSound(Gdx.files.internal("breakingSound.mp3"));   
        freezingSound = Gdx.audio.newSound(Gdx.files.internal("freezingSound.mp3"));   
        moverDerecha = (x == 0);
    }
    
    @Override
    public void dibujar(SpriteBatch batch){
		Rectangle area = getArea();
		batch.draw(getTextura(), area.x, area.y);
	}

    @Override
    public void mover(float deltaTime) {
        Rectangle area = getArea();
        area.y -= MathUtils.random(300, 500)  * deltaTime; 
        
        if (moverDerecha) {
            area.x += MathUtils.random(300, 600) * deltaTime; //Movimiento hacia la derecha
        } else {
            area.x -= MathUtils.random(300, 600)  * deltaTime; //Movimiento hacia la izquierda
        }
    }

    @Override
    public void aplicarEfecto(Tarro tarro) {
    	if (!tarro.esInmune()) {
    		tarro.reducirVelocidad(FACTOR_REDUCCION_VELOCIDAD, DURACION_REDUCCION); //Aplica el efecto de ralentización
    		freezingSound.stop();
    		freezingSound.setVolume(freezingSound.play(), 0.5f);
    	}
    	else {
    		tarro.sumarPuntos(25);
    		breakingSound.setVolume(breakingSound.play(), 0.6f);
    	}
    }
    public boolean getMoverDerecha() {
        return moverDerecha;
    }

    public void setMoverDerecha(boolean moverDerecha) {
        this.moverDerecha = moverDerecha;
    }

    public Sound getBreakingSound() {
        return breakingSound;
    }

    public void setBreakingSound(Sound breakingSound) {
        this.breakingSound = breakingSound;
    }

    public Sound getFreezingSound() {
        return freezingSound;
    }

    public void setFreezingSound(Sound freezingSound) {
        this.freezingSound = freezingSound;
    }

}