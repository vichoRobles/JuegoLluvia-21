package puppy.code;

import com.badlogic.gdx.Gdx;
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

        // Cargar recursos del juego
        Sound hurtSound = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
        tarro = new Tarro(new Texture(Gdx.files.internal("bucket.png")), hurtSound);
        tarro.crear();

        Texture gota = new Texture(Gdx.files.internal("drop.png"));
        Texture gotaMala = new Texture(Gdx.files.internal("dropBad.png"));
        Sound dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        Music rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        lluvia = new Lluvia(gota, gotaMala, dropSound, rainMusic);
        lluvia.crear();

        // Cámara
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    @Override
    public void render(float delta) {
        // Limpiar pantalla
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        // Dibujar texto
        font.draw(batch, "Gotas totales: " + tarro.getPuntos(), 5, 475);
        font.draw(batch, "Vidas : " + tarro.getVidas(), 670, 475);
        font.draw(batch, "HighScore : " + game.getHigherScore(), camera.viewportWidth / 2 - 50, 475);

        // Lógica principal del juego
        if (!tarro.estaHerido()) {
            tarro.actualizar(delta); // reemplaza actualizarMovimiento()
            lluvia.actualizar(delta); // reemplaza actualizarMovimiento(tarro)

            // Aquí puedes mantener la lógica de colisión con el tarro
            if (!lluvia.actualizarMovimiento(tarro)) { // <- lo definiremos abajo
                if (game.getHigherScore() < tarro.getPuntos())
                    game.setHigherScore(tarro.getPuntos());

                game.setScreen(new GameOverScreen(game));
                dispose();
            }
        }

        tarro.dibujar(batch);
        lluvia.dibujar(batch); // reemplaza actualizarDibujoLluvia(batch)

        batch.end();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void show() {
    	lluvia.continuarSonido();
    }

    @Override
    public void hide() {}

    @Override
    public void pause() {
        if (tarro instanceof Sonoro) ((Sonoro) tarro).pausarSonido();
        if (lluvia instanceof Sonoro) ((Sonoro) lluvia).pausarSonido();
        game.setScreen(new PausaScreen(game, this));
    }
    
    @Override
    public void resume() {
        if (tarro instanceof Sonoro) ((Sonoro) tarro).continuarSonido();
        if (lluvia instanceof Sonoro) ((Sonoro) lluvia).continuarSonido();
    }

    @Override
    public void dispose() {
        tarro.destruir();
        lluvia.destruir();
    }
}
