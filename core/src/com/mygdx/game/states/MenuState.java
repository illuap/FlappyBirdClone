package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.FlappyBirdClone;

/**
 * Created by paul on 31/05/16.
 */
public class MenuState extends com.mygdx.game.states.State {
    private Texture background;
    private Texture playBtn;


    public MenuState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, FlappyBirdClone.WIDTH / 2, FlappyBirdClone.HEIGHT / 2);
        background = new Texture("bg.png");
        playBtn = new Texture("playbtn.png");
    }


    @Override
    public void handleInput() {
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
        sb.begin();

        sb.draw(background,0,0, FlappyBirdClone.WIDTH, FlappyBirdClone.HEIGHT);
        sb.draw(playBtn,(FlappyBirdClone.WIDTH/2) - (playBtn.getWidth()/2),(FlappyBirdClone.HEIGHT/2) - (playBtn.getHeight()/2));

        sb.end();

    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
        System.out.println("Menu State Disposd");
    }
}
