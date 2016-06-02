package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by paul on 31/05/16.
 */
public class Tube {
    public static final int TUBE_WIDTH = 52;

    private static final int FLUCTUATION = 130;
    private static final int TUBE_GAP = 200;
    private static final int LOWEST_OPENING = 120;
    private Vector2 posTopTube, posBottomTube;
    private Rectangle boundsTop, boundsBottom;
    private Rectangle scoreArea;
    private Random rand;

    private int topTubeWidth,topTubeHeight,bottomTubeWidth,bottomTubeHeight;


    public Tube(float x,int ttW,int ttH, int btW, int btH){

        rand = new Random();
        topTubeWidth = ttW;
        topTubeHeight = ttH;
        bottomTubeWidth = btW;
        bottomTubeHeight = btH;



        posTopTube = new Vector2(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        posBottomTube = new Vector2(x,posTopTube.y - TUBE_GAP - bottomTubeHeight);

        scoreArea = new Rectangle(x+40,getPosTopTube().y-TUBE_GAP, 5, TUBE_GAP);

        boundsTop = new Rectangle(posTopTube.x, posTopTube.y,topTubeWidth,topTubeHeight);
        boundsBottom = new Rectangle(posBottomTube.x, posBottomTube.y,topTubeWidth,bottomTubeHeight);



    }

    public Vector2 getPosTopTube() {
        return posTopTube;
    }

    public Vector2 getPosBottomTube() {
        return posBottomTube;
    }

    public void reposition(float x){
        posTopTube.set(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        posBottomTube.set(x,posTopTube.y - TUBE_GAP - bottomTubeHeight);

        scoreArea.set(posTopTube.x+50,posTopTube.y-TUBE_GAP, 5, TUBE_GAP);

        boundsTop.set(posTopTube.x, posTopTube.y,topTubeWidth,topTubeHeight);
        boundsBottom.set(posBottomTube.x, posBottomTube.y,bottomTubeWidth,bottomTubeHeight);
    }

    public boolean collides(Rectangle player){
        return player.overlaps(boundsTop)||player.overlaps(boundsBottom);
    }

    public boolean passes(Rectangle player){
        if(player.overlaps(scoreArea)){
            scoreArea.set(0,0,0,0);
            return true;
        }else{
            return false;
        }
    }

    public void dispose(){
    }
}
