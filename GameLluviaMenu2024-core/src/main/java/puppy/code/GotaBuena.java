package puppy.code;

import com.badlogic.gdx.graphics.Texture;

public class GotaBuena extends Gota {
    public GotaBuena(Texture textura, float x, float y) {
        super(textura, x, y);
    }

    @Override
    public void aplicarEfecto(Tarro tarro) {
        tarro.sumarPuntos(10);
    }
    @Override
    public void destruir() {	
    }

}
