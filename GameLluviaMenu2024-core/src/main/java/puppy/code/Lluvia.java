package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Lluvia extends EntidadJuego implements Sonoro {

    private Array<Gota> gotas;
    private long lastDropTime;
    private Texture gotaBuena;
    private Texture gotaMala;
    private Texture gotaCurativa;
    private Texture gotaEscudo;
    private Sound dropSound;
    private Music rainMusic;

    public Lluvia(Texture gotaBuena, Texture gotaMala, Texture gotaCurativa, Texture gotaEscudo, Sound dropSound, Music rainMusic) {
        super(gotaBuena, 0, 480);
        this.gotaBuena = gotaBuena;
        this.gotaMala = gotaMala;
        this.gotaCurativa = gotaCurativa;
        this.gotaEscudo = gotaEscudo; 
        this.dropSound = dropSound;
        this.rainMusic = rainMusic;
    }

    public void crear() {
        gotas = new Array<>();
        crearGotaDeLluvia();
        rainMusic.setLooping(true);
        rainMusic.play();
    }

    private void crearGotaDeLluvia() {
        float x = MathUtils.random(0, 800 - 64);
        float y = 480;

        int tipo = MathUtils.random(1, 100);

        if (tipo <= 45)
            gotas.add(new GotaMala(gotaMala, x, y));          // 45%
        else if (tipo <= 90)
            gotas.add(new GotaBuena(gotaBuena, x, y));        // 45%
        else if (tipo <= 97)
            gotas.add(new GotaCurativa(gotaCurativa, x, y));  // 7%
        else
            gotas.add(new GotaEscudo(gotaEscudo, x, y));      // 3%

        lastDropTime = TimeUtils.nanoTime();
    }

    public boolean actualizarMovimiento(Tarro tarro) {
        if (TimeUtils.nanoTime() - lastDropTime > 100000000)
            crearGotaDeLluvia();

        for (int i = 0; i < gotas.size; i++) {
            Gota gota = gotas.get(i);
            gota.actualizar(Gdx.graphics.getDeltaTime());

            if (gota.getY() + 64 < 0) {
                gotas.removeIndex(i);
                continue;
            }

            if (gota.getArea().overlaps(tarro.getArea())) {
                gota.aplicarEfecto(tarro);

                // Sonido solo para gotas buenas o beneficiosas
                if (gota instanceof GotaBuena || gota instanceof GotaCurativa || gota instanceof GotaEscudo) {
                    dropSound.play();
                }

                gotas.removeIndex(i);
            }
        }
        return tarro.getVidas() > 0;
    }

    @Override
    public void actualizar(float delta) {}

    @Override
    public void dibujar(SpriteBatch batch) {
        for (Gota gota : gotas) {
            gota.dibujar(batch);
        }
    }

    @Override
    public void destruir() {
        dropSound.dispose();
        rainMusic.dispose();
    }

    @Override
    public void pausarSonido() {
        rainMusic.pause();
    }

    @Override
    public void continuarSonido() {
        rainMusic.play();
    }
}

