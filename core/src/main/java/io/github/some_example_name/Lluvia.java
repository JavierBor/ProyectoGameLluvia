package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Lluvia {
	private Array<Elemento> elementosPos;
    private long lastDropTime;
    private Sound dropSound;
    private Music rainMusic;
	   
	public Lluvia(Sound ss, Music mm) {
		rainMusic = mm;
		dropSound = ss;
	}
	
	public void crear() {
		//Creamos el arreglo de elementos 
		elementosPos = new Array<Elemento>();
        
		//Música de fondo
        rainMusic.setLooping(true);
        rainMusic.setVolume(0.25f);
        rainMusic.play();
	}
	
	private void crearGotaDeLluvia() { 	
		//Ver el tipo de gota (buena o mala)
	    if (MathUtils.random(1, 10) < 4) {
	    	Elemento gotaMala = new GotaMala(MathUtils.random(0, 800-64), 480, 64, 64);
	        elementosPos.add(gotaMala);
	    } else {
	    	Elemento gotaBuena = new GotaBuena(MathUtils.random(0, 800-64), 480, 64, 64);
	        elementosPos.add(gotaBuena);
	    }
	    
	    lastDropTime = TimeUtils.nanoTime();
	}
	
	private void crearElemento() {
		double probabilidad = MathUtils.random(0f, 100f);
		if (probabilidad <= 1) { //Probabilidad del 1%
			Elemento rayo = new Rayo(MathUtils.random(0, 800-64), 480, 64, 64);
			elementosPos.add(rayo);
		}
		else if (probabilidad > 1 && probabilidad <= 1.5) { //Probabilidad del 0,5%
			Elemento sol = new Sol(MathUtils.random(0, 800-64), 480, 64, 64);
			elementosPos.add(sol);
		}
		else if (probabilidad <= 5) { //Probabilidad del 1%
			Elemento nieve = new Nieve(MathUtils.random(0, 800-64), 480, 64, 64);
			elementosPos.add(nieve);
		}
		else { //Resto de probabilidades (Gotas normales)
			crearGotaDeLluvia();
		}
	}
	
   public boolean actualizarMovimiento(Tarro tarro) {
	   //Generar elementos de la lluvia
	   if(TimeUtils.nanoTime() - lastDropTime > 100000000) crearElemento();
	   
	   for (int i = 0; i < elementosPos.size; i++) {
		    Elemento elemento = elementosPos.get(i);
		    elemento.mover(Gdx.graphics.getDeltaTime());

		    if (elemento.fueraPantalla()) {
		        elementosPos.removeIndex(i);
		        continue;
		    }

		    if (elemento.colision(tarro)) { //Aplicamos el efecto del elemento       
	            if (elemento instanceof ElementoEfecto) {
	            	ElementoEfecto elementoEfecto = (ElementoEfecto) elemento;
	            	elementoEfecto.aplicarEfecto(tarro); //Se aplicará el efecto automaticamente     
	            }
		    	if (elemento instanceof Sol) {
		    		tarro.aumentarVida();
		    	}
		    	if (elemento instanceof GotaBuena) {
		    		tarro.sumarPuntos(10);
		            dropSound.setVolume(dropSound.play(), 0.5f);
		    	}
		    	if (elemento instanceof GotaMala) {		    		
		    		if (tarro.esInmune()) { //En caso de estar con efecto Rayo
		    			tarro.sumarPuntos(5);
		    	    }
		    		else
		    		{
		    		    
		    			tarro.dañar(); // Dañar el tarro
		    			if (tarro.getVidas() <= 0) return false; // GAMEOVER
		    		    }
		    	
		    	}
		        elementosPos.removeIndex(i);
		    }
	   }
	   return true; 
   }
   
   public void actualizarDibujoElementos(SpriteBatch batch) {    
	    for (Elemento elemento : elementosPos) {
	        elemento.dibujar(batch);
	    }
	}
     
   public void destruir() {
      dropSound.dispose();
      rainMusic.dispose();
   }
   
   public void pausar() {
	  rainMusic.stop();
   }
   
   public void continuar() {
	  rainMusic.play();
   }
   
   
}
