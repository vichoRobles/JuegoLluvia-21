package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
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

    // Control del escudo
    private boolean escudoActivo = false;
    private float duracionEscudo = 0f; // en segundos
    private final float duracionMaxEscudo = 5f; // duración total del escudo (5 segundos)

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
        if (escudoActivo) return; // No recibe daño si tiene escudo

        vidas--;
        herido = true;
        tiempoHerido = tiempoHeridoMax;
        sonidoHerido.play();
    }

    // Activar el escudo
    public void activarEscudo() {
        escudoActivo = true;
        duracionEscudo = duracionMaxEscudo;
    }

    @Override
    public void dibujar(SpriteBatch batch) {
        // Si el escudo está activo, dibuja el tarro con un leve parpadeo azul
        if (escudoActivo) {
            float alpha = 0.5f + 0.5f * MathUtils.sinDeg((duracionEscudo * 360) % 360);
            batch.setColor(0.3f, 0.6f, 1f, alpha); // azul translúcido
        }

        if (!herido)
            batch.draw(textura, bucket.x, bucket.y);
        else {
            batch.draw(textura, bucket.x, bucket.y + MathUtils.random(-5, 5));
            tiempoHerido--;
            if (tiempoHerido <= 0) herido = false;
        }

        batch.setColor(Color.WHITE); // Restaurar color normal
    }

    @Override
    public void actualizar(float delta) {
        // Movimiento
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= velx * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += velx * delta;
        if (bucket.x < 0) bucket.x = 0;
        if (bucket.x > 800 - 64) bucket.x = 800 - 64;

        // Actualizar duración del escudo
        if (escudoActivo) {
            duracionEscudo -= delta;
            if (duracionEscudo <= 0) {
                escudoActivo = false;
            }
        }
    }

    public boolean tieneEscudo() { return escudoActivo; }

    @Override
    public void destruir() {
        textura.dispose();
    }

    public boolean estaHerido() { return herido; }

    @Override
    public void pausarSonido() {}
    @Override
    public void continuarSonido() {}
}