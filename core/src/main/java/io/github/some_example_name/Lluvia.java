package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Lluvia {
	private Array<Rectangle> rainDropsPos;
	private Array<Integer> rainDropsType;
	private Array<Elemento> elementosPos;
    private long lastDropTime;
    private Texture gotaBuena;
    private Texture gotaMala;
    private Texture textRayo;
    private Sound dropSound;
    private Music rainMusic;
	   
	public Lluvia(Texture gotaBuena, Texture gotaMala, Sound ss, Music mm, Texture textRayo) {
		rainMusic = mm;
		dropSound = ss;
		this.gotaBuena = gotaBuena;
		this.gotaMala = gotaMala;
		this.textRayo = textRayo;
	}
	
	public void crear() {
		rainDropsPos = new Array<Rectangle>();
		rainDropsType = new Array<Integer>();
		elementosPos = new Array<Elemento>();
		crearGotaDeLluvia();
	      // start the playback of the background music immediately
	      rainMusic.setLooping(true);
	      rainMusic.play();
	}
	
	private void crearGotaDeLluvia() {
		Rectangle raindrop = new Rectangle();
		raindrop.x = MathUtils.random(0, 800-64);
		raindrop.y = 480;
		raindrop.width = 64;
		raindrop.height = 64;
		rainDropsPos.add(raindrop);
		
		//Ver el tipo de gota (buena o mala)
	    if (MathUtils.random(1, 10) < 5) {
	        rainDropsType.add(1);  //1 para gota dañina
	    } else {
	        rainDropsType.add(2);  //2 para gota buena
	    }
	    
	    lastDropTime = TimeUtils.nanoTime();
	}
	
	private void crearElemento() {
		int probabilidad = MathUtils.random(1, 100);
		if (probabilidad <= 5) {
			Rayo rayo = new Rayo(MathUtils.random(0, 800-64), 480, 64, 64);
			elementosPos.add(rayo);
		}
		else {
			crearGotaDeLluvia();
		}
	}
	
   public boolean actualizarMovimiento(Tarro tarro) {
	   //Generar elementos de la lluvia
	   if(TimeUtils.nanoTime() - lastDropTime > 100000000) crearElemento();
	  
	   
	   // revisar si las gotas cayeron al suelo o chocaron con el tarro
	   for (int i=0; i < rainDropsPos.size; i++ ) {
		  Rectangle raindrop = rainDropsPos.get(i);
	      raindrop.y -= 300 * Gdx.graphics.getDeltaTime();
	      //cae al suelo y se elimina
	      if(raindrop.y + 64 < 0) {
	    	  rainDropsPos.removeIndex(i); 
	    	  rainDropsType.removeIndex(i);
	      }
	      if(raindrop.overlaps(tarro.getArea())) {
	    	  //la gota choca con el tarro
	    	  if(rainDropsType.get(i)==1) { // gota dañina
	    	      tarro.dañar();
	    	      if (tarro.getVidas()<=0)
	    		     return false; // si se queda sin vidas retorna falso /game over
	    	      rainDropsPos.removeIndex(i);
	              rainDropsType.removeIndex(i);
	      	  } 
	    	  else if (rainDropsType.get(i)==2) { // gota a recolectar
	    	      tarro.sumarPuntos(10);
	              dropSound.play();
	              rainDropsPos.removeIndex(i);
	              rainDropsType.removeIndex(i);
	      	  }       		
	       }
	   } 
	   
	   for (int i = 0; i < elementosPos.size; i++) {
		    Elemento elemento = elementosPos.get(i);
		    elemento.mover(Gdx.graphics.getDeltaTime());

		    if (elemento.fueraPantalla()) {
		        elementosPos.removeIndex(i);
		        continue;
		    }

		    if (elemento.colision(tarro)) {
		        if (elemento instanceof Rayo) {
		            tarro.otorgarInmunidad(5);  //Aplica inmunidad
		        } 
		        /*else if (elemento instanceof Arcoiris) {
		            tarro.sumarVida();  //Añade una vida
		        }*/
		        elementosPos.removeIndex(i);
		    }
	   }
	   return true; 
   }
   
   public void actualizarDibujoElementos(SpriteBatch batch) {    
	    for (Elemento elemento : elementosPos) {
	        elemento.dibujar(batch);  // Asegúrate de tener un método de dibujo en la clase Elemento
	    }
	}
   
   public void actualizarDibujoLluvia(SpriteBatch batch) {    
	  for (int i=0; i < rainDropsPos.size; i++ ) {
		  Rectangle raindrop = rainDropsPos.get(i);
		  if(rainDropsType.get(i)==1) // gota dañina
	         batch.draw(gotaMala, raindrop.x, raindrop.y); 
		  else if (rainDropsType.get(i)==2)
			 batch.draw(gotaBuena, raindrop.x, raindrop.y); 
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
