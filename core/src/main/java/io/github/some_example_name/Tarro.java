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
	   private Texture bucketSnow; 
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
	   private boolean congelado;
	   private float tiempoInmunidad;
	   private float tiempoRalentizacion; 
	   
	   public Tarro(Texture tex, Sound ss, Texture gold, Texture snow, Sound power, Sound vidaMas) {
		   bucketImage = tex;
		   sonidoHerido = ss;
		   bucketGold = gold;
		   bucketSnow = snow; 
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
		  tiempoHerido = tiempoHeridoMax;
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
	   
	   public void reducirVelocidad(float factor, float duracion) {
		    congelado = true;
		    velx *= factor;
		    this.tiempoRalentizacion = Math.max(this.tiempoRalentizacion, duracion);
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
	    	   if (!herido) {
	    		   if (congelado) batch.draw(bucketSnow, bucket.x, bucket.y);
	    		   else batch.draw(bucketImage, bucket.x, bucket.y);
	    	   }
		       else {	
		    	   if (congelado) batch.draw(bucketSnow, bucket.x, bucket.y+ MathUtils.random(-5,5));
		    	   else batch.draw(bucketImage, bucket.x, bucket.y+ MathUtils.random(-5,5));
		    	   tiempoHerido--;
		    	   if (tiempoHerido<=0) herido = false;
		       }
	       }     
	   } 
	   
	   public void actualizarMovimiento() {
	        //Controlamos la duración de la ralentización
	        if (tiempoRalentizacion > 0) {
	            tiempoRalentizacion -= Gdx.graphics.getDeltaTime();
	            if (tiempoRalentizacion <= 0 || inmunidad) {
	            	congelado = false;
	                velx = 500; //Restauramos la velocidad
	            }
	        }
	        
	        //Movimiento desde teclado usando la velocidad actual
	        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= velx * Gdx.graphics.getDeltaTime();
	        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += velx * Gdx.graphics.getDeltaTime();
	        if (Gdx.input.isKeyPressed(Input.Keys.UP)) bucket.y += velx * Gdx.graphics.getDeltaTime();
	        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) bucket.y -= velx * Gdx.graphics.getDeltaTime();

	        //Restricciones de movimiento (mantener dentro de la pantalla)
	        if (bucket.y < 0) bucket.y = 0;
	        if (bucket.y > 480 - 64) bucket.y = 480 - 64;

	        //Al salir por los lados
	        if (bucket.x < -64) bucket.x = 800;
	        if (bucket.x > 800) bucket.x = -64;
	   }
	      
	   public boolean estaCongelado() {
		   return congelado;
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
	// Getters

	   public Rectangle getBucket() {
	       return bucket;
	   }

	   public Texture getBucketImage() {
	       return bucketImage;
	   }

	   public Texture getBucketGold() {
	       return bucketGold;
	   }

	   public Texture getBucketSnow() {
	       return bucketSnow;
	   }

	   public Sound getSonidoHerido() {
	       return sonidoHerido;
	   }

	   public Sound getPowerSound() {
	       return powerSound;
	   }

	   public Sound getVidaSound() {
	       return vidaSound;
	   }

	   public int getVelx() {
	       return velx;
	   }

	   public boolean isHerido() {
	       return herido;
	   }

	   public int getTiempoHeridoMax() {
	       return tiempoHeridoMax;
	   }

	   public int getTiempoHerido() {
	       return tiempoHerido;
	   }

	   public boolean isInmunidad() {
	       return inmunidad;
	   }

	   public boolean isCongelado() {
	       return congelado;
	   }

	   public float getTiempoInmunidad() {
	       return tiempoInmunidad;
	   }

	   public float getTiempoRalentizacion() {
	       return tiempoRalentizacion;
	   }

	   // Setters
	   public void setVidas(int vidas) {
	       this.vidas = vidas;
	   }

	   public void setPuntos(int puntos) {
	       this.puntos = puntos;
	   }

	   public void setBucket(Rectangle bucket) {
	       this.bucket = bucket;
	   }

	   public void setBucketImage(Texture bucketImage) {
	       this.bucketImage = bucketImage;
	   }

	   public void setBucketGold(Texture bucketGold) {
	       this.bucketGold = bucketGold;
	   }

	   public void setBucketSnow(Texture bucketSnow) {
	       this.bucketSnow = bucketSnow;
	   }

	   public void setSonidoHerido(Sound sonidoHerido) {
	       this.sonidoHerido = sonidoHerido;
	   }

	   public void setPowerSound(Sound powerSound) {
	       this.powerSound = powerSound;
	   }

	   public void setVidaSound(Sound vidaSound) {
	       this.vidaSound = vidaSound;
	   }

	   public void setVelx(int velx) {
	       this.velx = velx;
	   }

	   public void setHerido(boolean herido) {
	       this.herido = herido;
	   }

	   public void setTiempoHeridoMax(int tiempoHeridoMax) {
	       this.tiempoHeridoMax = tiempoHeridoMax;
	   }

	   public void setTiempoHerido(int tiempoHerido) {
	       this.tiempoHerido = tiempoHerido;
	   }

	   public void setInmunidad(boolean inmunidad) {
	       this.inmunidad = inmunidad;
	   }

	   public void setCongelado(boolean congelado) {
	       this.congelado = congelado;
	   }

	   public void setTiempoInmunidad(float tiempoInmunidad) {
	       this.tiempoInmunidad = tiempoInmunidad;
	   }

	   public void setTiempoRalentizacion(float tiempoRalentizacion) {
	       this.tiempoRalentizacion = tiempoRalentizacion;
	   }

}
