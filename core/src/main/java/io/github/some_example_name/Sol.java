package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Sol extends Elemento{
    private float direccion = 1; // 1 para derecha, -1 para izquierda
    private float limiteIzquierda; //Límite izquierdo
    private float limiteDerecha; //Límite derecho
    private float contadorCambio;
    private float tiempoCambio;
	
    public Sol(float x, float y, float ancho, float alto) {
        super(x, y, ancho, alto, new Texture(Gdx.files.internal("sol.png")));
        limiteIzquierda = 0;
        limiteDerecha = 800 - 50;
        contadorCambio = 0;
        tiempoCambio = MathUtils.random(1, 3);
    }
    
    @Override
    public void dibujar(SpriteBatch batch){
		Rectangle area = getArea();
		batch.draw(getTextura(), area.x, area.y);
	}
    
    @Override
    public void mover(float deltaTime) {
    	
    	//Mover el sol en eje x con distintas velocidades
        getArea().x += MathUtils.random(100, 450) * direccion * deltaTime;
        //Mover el del en eje y con velocidad fija
        getArea().y -= 350 * deltaTime;

        //Aumentar el contador de tiempo
        contadorCambio += deltaTime;

        //Cambiar dirección después de cierto tiempo
        if (contadorCambio >= tiempoCambio) {
            direccion *= -1; //Cambiar la dirección
            contadorCambio = 0; 
            tiempoCambio = MathUtils.random(1, 3);
        }

        //Cambiar dirección si alcanza los límites laterales
        if (getArea().x <= limiteIzquierda) {
            direccion = 1;
        } else if (getArea().x >= limiteDerecha) {
            direccion = -1;
        }
    }
}
