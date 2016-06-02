package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.FlappyBirdClone;
import com.mygdx.game.sprites.Bird;
import com.mygdx.game.sprites.Tube;

/**
 * Created by paul on 31/05/16.
 */
public class PlayState extends State{
    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 4;
    private static final int GROUND_Y_OFFSET = -50;
    private static GlyphLayout glyphLayout = new GlyphLayout();

    private int score;
    private Bird bird;
    private Texture bg;
    private Texture topTube, bottomTube;
    private Texture ground;
    private Vector2 groundPos1, groundPos2;
    private BitmapFont font;
    private Sound lose;
    private Sound ding;
    private Tube tube;
    private Array<Tube> tubes;

    protected PlayState(GameStateManager gsm) {
        super(gsm);

        cam.setToOrtho(false, FlappyBirdClone.WIDTH/2, FlappyBirdClone.HEIGHT/2);

        bg = new Texture("bg.png");

        bird = new Bird(50,250);

        ground = new Texture("ground.png");
        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth/2,GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth/2)+(ground.getWidth()),GROUND_Y_OFFSET);

        topTube = new Texture("toptube.png");
        bottomTube = new Texture("bottomtube.png");

        //----------------------------------------
        //-Generate the font used for the scoring
        //----------------------------------------
        font = new BitmapFont(Gdx.files.internal("FlappyBirdy.fnt"));
        font.setUseIntegerPositions(false); //smoother placement

        //----------------------------------------
        //-Sound
        //----------------------------------------
        lose = Gdx.audio.newSound(Gdx.files.internal("fail.wav"));
        ding = Gdx.audio.newSound(Gdx.files.internal("ring.wav"));

        score = 0;
        tubes = new Array<Tube>();
        for(int i = 1 ; i < TUBE_COUNT; i++){
            tubes.add(new Tube(i* (TUBE_SPACING + Tube.TUBE_WIDTH),topTube.getWidth(),topTube.getHeight(),bottomTube.getWidth(),bottomTube.getHeight()));
        }
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            bird.jump();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        bird.update(dt);
        cam.position.x = bird.getPosition().x + 80;
        for(Tube tube: tubes){
            if(cam.position.x - (cam.viewportWidth/2) > tube.getPosBottomTube().x + Tube.TUBE_WIDTH){
                tube.reposition(tube.getPosTopTube().x + ((Tube.TUBE_WIDTH+TUBE_SPACING * TUBE_COUNT)));
            }
            if(tube.collides(bird.getBounds())){
                lose.play(0.5f);
                GameOverState tempGsm = new GameOverState(gsm);
                tempGsm.setScore(score);
                gsm.set(tempGsm);
            }
            if(tube.passes(bird.getBounds())){
                score++;
                ding.play(0.5f);
                System.out.println("Point! " + score);
            }
        }
        updateGround(); //continuous ground img loop


        if(bird.getPosition().y <= ground.getHeight()+ GROUND_Y_OFFSET){
            lose.play(0.5f);
            GameOverState tempGsm = new GameOverState(gsm);
            tempGsm.setScore(score);
            gsm.set(tempGsm);
        }
        cam.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();

        sb.draw(bg,cam.position.x - (cam.viewportWidth/2),0);
        sb.draw(bird.getTexture(), bird.getPosition().x,bird.getPosition().y);
        for(Tube tube: tubes){
            sb.draw(topTube,tube.getPosTopTube().x,tube.getPosTopTube().y);
            sb.draw(bottomTube,tube.getPosBottomTube().x,tube.getPosBottomTube().y);
        }
        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);
        glyphLayout.setText(font, ""+score);
        font.draw(sb, glyphLayout, cam.position.x-(glyphLayout.width/2), cam.position.y*2-20);
        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        bird.dispose();
        topTube.dispose();
        bottomTube.dispose();
        ground.dispose();
        font.dispose();
        System.out.println("Play State Disposd");
    }

    private void updateGround(){
        if(cam.position.x - (cam.viewportWidth/2)> groundPos1.x + ground.getWidth()){
            groundPos1.add(ground.getWidth()*2,0);
        }if(cam.position.x - (cam.viewportWidth/2)> groundPos2.x + ground.getWidth()){
            groundPos2.add(ground.getWidth()*2,0);
        }
    }


}
