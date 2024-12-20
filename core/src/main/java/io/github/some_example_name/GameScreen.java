package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {
	final GameLluviaMenu game;
    private OrthographicCamera camera;
	private SpriteBatch batch;	   
	private BitmapFont font;
	private Tarro tarro;
	private Lluvia lluvia;

	public GameScreen(final GameLluviaMenu game) {
		
		this.game = game;
        this.batch = game.getBatch();
        this.font = game.getFont();
		// load the images for the droplet and the bucket, 64x64 pixels each 	     
		Sound hurtSound = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
		Sound powerSound = Gdx.audio.newSound(Gdx.files.internal("powerSound.mp3")); 
		Sound vidaSound = Gdx.audio.newSound(Gdx.files.internal("vidaSound.mp3")); 
		//tarro = new Tarro(new Texture(Gdx.files.internal("bucket.png")),hurtSound, new Texture(Gdx.files.internal("bucketGold.png")), powerSound, vidaSound);
		tarro = new Tarro(new Texture(Gdx.files.internal("bucket.png")),
                hurtSound, 
                new Texture(Gdx.files.internal("bucketGold.png")),
                new Texture(Gdx.files.internal("bucketFroze.png")),
                powerSound, 
                vidaSound);
 

         
        Sound dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav")); 
	    Music rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        lluvia = new Lluvia(dropSound, rainMusic);
	      
	    // camera
	    camera = new OrthographicCamera();
	    camera.setToOrtho(false, 800, 480);
	    batch = new SpriteBatch();
	    // creacion del tarro
	    tarro.crear();
	     
	    // creacion de la lluvia
	    lluvia.crear();
	}

	@Override
	
	public void render(float delta) {
		//limpia la pantalla con color azul obscuro.
		ScreenUtils.clear(0, 0, 0.2f, 1);
		//actualizar matrices de la cámara
		camera.update();
		//actualizar 
		batch.setProjectionMatrix(camera.combined);
		
		//Detectar si se presiona la tecla de pausa	'P'
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
        	this.pause();
        }
          
		batch.begin();
		//Textos mostrados en pantalla
		font.draw(batch, "Puntaje Actual: " + ScoreManager.getInstance().getPuntos(), 5, 475);
		font.draw(batch, "Vidas: " + tarro.getVidas(), 5, 445);
		font.draw(batch, "HighScore: " + game.getHigherScore(), 550, 475);
		
		if (!tarro.estaHerido()) {
			// movimiento del tarro desde teclado
	        tarro.actualizarMovimiento();        
			// caida de la lluvia 
	       if (!lluvia.actualizarMovimiento(tarro)) {
	    	  //actualizar HigherScore
	    	  if (game.getHigherScore() < ScoreManager.getInstance().getPuntos())
	    		  game.setHigherScore(ScoreManager.getInstance().getPuntos());  
	    	  //ir a la ventana de finde juego y destruir la actual
	    	  game.setScreen(new GameOverScreen(game));
	    	  ScoreManager.getInstance().resetPuntos();
	    	  dispose();
	       }
		}
		
		tarro.dibujar(batch);
		lluvia.actualizarDibujoElementos(batch);
		batch.end();
	} 
	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
	  lluvia.continuar();
	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {
		lluvia.pausar();
		game.setScreen(new PausaScreen(game, this)); 
	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
      tarro.destruir();
      lluvia.destruir();

	}

}