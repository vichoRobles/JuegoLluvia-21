package puppy.code;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public abstract class Gota extends EntidadJuego {
    protected Rectangle area;

    public Gota(Texture textura, float x, float y) {
        super(textura, x, y);
        area = new Rectangle(x, y, 64, 64);
    }

    @Override
    public void actualizar(float delta) {
        y -= getVelocidadCaida() * delta;
        area.setPosition(x, y);
    }

    @Override
    public void dibujar(SpriteBatch batch) {
        batch.draw(textura, x, y);
    }

    public Rectangle getArea() {
        return area;
    }

    // 💡 Cada subclase define su efecto al tocar el tarro
    public abstract void aplicarEfecto(Tarro tarro);

    // 💡 Cada subclase puede tener su propia velocidad de caída
    public float getVelocidadCaida() {
        return 300;
    }
}
