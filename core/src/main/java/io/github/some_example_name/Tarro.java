package io.github.some_example_name;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Tarro {
	   private Rectangle bucket;
	   private Texture bucketImage;
	   private Texture bucketGold;
	   private Sound sonidoHerido;
	   private Sound powerSound;
	   private Sound vidaSound;
	   private int vidas = 3;
	   private int puntos = 0;
	   private int velx = 500;
	   private boolean herido = false;
	   private int tiempoHeridoMax=50;
	   private int tiempoHerido;
	   private boolean inmunidad;
	   private float tiempoInmunidad;
	   
	   
	   public Tarro(Texture tex, Sound ss, Texture gold, Sound power, Sound vidaMas) {
		   bucketImage = tex;
		   sonidoHerido = ss;
		   bucketGold = gold;
		   powerSound = power;
		   vidaSound = vidaMas;
	   }
	   
		public int getVidas() {
			return vidas;
		}
	
		public int getPuntos() {
			return puntos;
		}
		public Rectangle getArea() {
			return bucket;
		}
		public void sumarPuntos(int pp) {
			puntos+=pp;
		}
		
	   public void crear() {
		      bucket = new Rectangle();
		      bucket.x = 800 / 2 - 64 / 2;
		      bucket.y = 20;
		      bucket.width = 64;
		      bucket.height = 64;
	   }
	   
	   public void dañar() {
		  if (inmunidad == true) return;
		  vidas--;
		  herido = true;
		  tiempoHerido=tiempoHeridoMax;
		  sonidoHerido.play();
	   }
	   
	   public void aumentarVida() {
		   puntos+=100;
		   vidaSound.play();
		   vidas++;
	   }
	   
	   public void otorgarInmunidad(float segundos) {	 
		   puntos+=25;
	       tiempoInmunidad = segundos;
	       powerSound.stop(); //Evitamos que se acumulen
	       powerSound.play();
	       inmunidad = true;
	   }
	   
	   public void dibujar(SpriteBatch batch) {
		   //Manejo de inmunidad
	       if (inmunidad) {
	           tiempoInmunidad -= Gdx.graphics.getDeltaTime(); 
	           if (tiempoInmunidad <= 0) {
	               inmunidad = false;
	               powerSound.stop();
	           }
	           batch.draw(bucketGold, bucket.x, bucket.y);
	       }
	       else {
	    	   batch.setColor(1, 1, 1, 1); //Color normal (blanco)
		 
		       if (!herido)  
		    	   batch.draw(bucketImage, bucket.x, bucket.y);
		       else {
		    	   batch.draw(bucketImage, bucket.x, bucket.y+ MathUtils.random(-5,5));
		    	   tiempoHerido--;
		    	   if (tiempoHerido<=0) herido = false;
		       }
	       }
	   } 
	   
	   
	   public void actualizarMovimiento() { 
		   //Movimiento desde teclado (Arriba, abajo, izq, der)
		   if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= velx * Gdx.graphics.getDeltaTime();
		   if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += velx * Gdx.graphics.getDeltaTime();
		   if(Gdx.input.isKeyPressed(Input.Keys.UP)) bucket.y += velx * Gdx.graphics.getDeltaTime();
		   if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) bucket.y -= velx * Gdx.graphics.getDeltaTime();
		   
		   //Para evitar que no salga por arriba o abajo
		   if(bucket.y < 0) bucket.y = 0;
		   if(bucket.y > 480 - 64) bucket.y = 480 - 64;
		   
		   //Al salir por un lado, aparece por el otro
		   if(bucket.x < -64) bucket.x = 800; 
		   if(bucket.x > 800) bucket.x = -64; 
	   }
	    	   
	   public boolean esInmune() {
		   return inmunidad;
	   }

	   public void destruir() {
		   bucketImage.dispose();
	   }
	
	   public boolean estaHerido() {
		   return herido;
	   }
}
