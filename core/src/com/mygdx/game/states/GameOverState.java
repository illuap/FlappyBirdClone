package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.FlappyBirdClone;

/**
 * Created by paul on 31/05/16.
 */
public class GameOverState extends State{

    private Texture gameOver;
    private Texture bg;
    private Texture playBtn;
    private int score;
    private Label scoreLabel;
    private BitmapFont font;
    private Stage stage;
    private Skin skin;
    private static GlyphLayout glyphLayout = new GlyphLayout();

    protected GameOverState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, FlappyBirdClone.WIDTH/2, FlappyBirdClone.HEIGHT/2);
        gameOver = new Texture("gameover.png");
        bg = new Texture("bg.png");
        playBtn = new Texture("playbtn.png");
        score = 0;


        //----------------------------------------
        //-Generate the font used for the scoring
        //----------------------------------------
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("FlappyBirdy.TTF"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 18;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 2;
        //smoothing out the font
        parameter.minFilter = Texture.TextureFilter.Nearest;
        parameter.magFilter = Texture.TextureFilter.MipMapLinearNearest;
        font = generator.generateFont(parameter);
        font.setUseIntegerPositions(false); //smoother placement
        generator.dispose();



//        skin = new Skin(Gdx.files.internal("uiskin.json"));
//        stage = new Stage(new ScreenViewport());
//
//        Gdx.input.setInputProcessor(stage);
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm));
            dispose();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
//        stage.act(Gdx.graphics.getDeltaTime());
//        stage.draw();


        sb.setProjectionMatrix(cam.combined);
        sb.begin();

        sb.draw(bg,0,0);
        sb.draw(gameOver,(cam.position.x) - (gameOver.getWidth()/2),(cam.position.y) + 80);
        sb.draw(playBtn,(cam.position.x) - (playBtn.getWidth()/2),(cam.position.y) - (playBtn.getHeight()/2) - 100);

        glyphLayout.setText(font, "Score: "+score);
        font.draw(sb,glyphLayout,cam.position.x-(glyphLayout.width/2),cam.position.y+20);

        sb.end();
    }

    @Override
    public void dispose() {
        gameOver.dispose();
        bg.dispose();
        playBtn.dispose();
        System.out.println("Game Over State Disposed");
    }

    public void setScore(int x){
        score = x;
    }

}
