package puppy.code;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;

public abstract class EntidadJuego {

    protected Texture textura;
    protected float x;
    protected float y;

    public EntidadJuego(Texture textura, float x, float y) {
        this.textura = textura;
        this.x = x;
        this.y = y;
    }

    public abstract void actualizar(float delta);
    public abstract void dibujar(SpriteBatch batch);
    public abstract void destruir();

    public float getX() { return x; }
    public float getY() { return y; }

    public void setPosicion(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Texture getTextura() {
        return textura;
    }
}
