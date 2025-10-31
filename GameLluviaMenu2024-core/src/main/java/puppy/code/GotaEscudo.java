package puppy.code;

import com.badlogic.gdx.graphics.Texture;

public class GotaEscudo extends Gota {
    public GotaEscudo(Texture textura, float x, float y) {
        super(textura, x, y);
    }

    @Override
    public void aplicarEfecto(Tarro tarro) {
        tarro.activarEscudo();
    }
    @Override
    public void destruir() {	
    }
}
