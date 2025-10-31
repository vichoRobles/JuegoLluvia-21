package puppy.code;

import com.badlogic.gdx.graphics.Texture;

public class GotaCurativa extends Gota {

    public GotaCurativa(Texture textura, float x, float y) {
        super(textura, x, y);
    }

    @Override
    public void aplicarEfecto(Tarro tarro) {

        if (tarro.getVidas() < 3) {
            try {
                java.lang.reflect.Field vidasField = Tarro.class.getDeclaredField("vidas");
                vidasField.setAccessible(true);
                int vidasActuales = tarro.getVidas();
                vidasField.setInt(tarro, vidasActuales + 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    @Override
    public void destruir() {	
    }
}
