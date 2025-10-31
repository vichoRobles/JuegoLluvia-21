package puppy.code;

import com.badlogic.gdx.graphics.Texture;

public class GotaMala extends Gota {
    public GotaMala(Texture textura, float x, float y) {
        super(textura, x, y);
    }

    @Override
    public void aplicarEfecto(Tarro tarro) {
        tarro.dañar();
    }
    @Override
    public void destruir() {	
    }
}
