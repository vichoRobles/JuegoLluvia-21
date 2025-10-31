package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Tarro extends EntidadJuego implements Sonoro {
    private Rectangle bucket;
    private Sound sonidoHerido;
    private int vidas = 3;
    private int puntos = 0;
    private int velx = 400;
    private boolean herido = false;
    private int tiempoHeridoMax = 50;
    private int tiempoHerido;

    public Tarro(Texture tex, Sound ss) {
        super(tex, 800 / 2f - 64 / 2f, 20); // posición inicial
        this.sonidoHerido = ss;
    }

    public int getVidas() { return vidas; }
    public int getPuntos() { return puntos; }
    public Rectangle getArea() { return bucket; }
    public void sumarPuntos(int pp) { puntos += pp; }

    public void crear() {
        bucket = new Rectangle();
        bucket.x = x;
        bucket.y = y;
        bucket.width = 64;
        bucket.height = 64;
    }

    public void dañar() {
        vidas--;
        herido = true;
        tiempoHerido = tiempoHeridoMax;
        sonidoHerido.play();
    }

    @Override
    public void dibujar(SpriteBatch batch) {
        if (!herido)
            batch.draw(textura, bucket.x, bucket.y);
        else {
            batch.draw(textura, bucket.x, bucket.y + MathUtils.random(-5, 5));
            tiempoHerido--;
            if (tiempoHerido <= 0) herido = false;
        }
    }

    @Override
    public void actualizar(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= velx * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += velx * delta;
        if (bucket.x < 0) bucket.x = 0;
        if (bucket.x > 800 - 64) bucket.x = 800 - 64;
    }

    @Override
    public void destruir() {
        textura.dispose();
    }

    public boolean estaHerido() { return herido; }
    
    @Override
    public void pausarSonido() {
    }
    @Override
    public void continuarSonido() {
    }
}
