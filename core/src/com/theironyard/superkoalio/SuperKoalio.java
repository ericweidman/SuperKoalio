package com.theironyard.superkoalio;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SuperKoalio extends ApplicationAdapter {
    SpriteBatch batch;
    TextureRegion stand;

    static final int WIDTH = 18;
    static final int HEIGHT = 26;
    static final int DRAW_WIDTH = WIDTH*3;
    static final int DRAW_HEIGHT = HEIGHT*3;
    static final float MAX_VELOCITY = 500;
    static final float MAX_JUMP_VELOCITY = 2000;
    static final int GRAVITY = -50;

    float x, y, xv, yv;
    boolean canJump;



    @Override
    public void create () {
        batch = new SpriteBatch();
        Texture sheet = new Texture("koalio.png");
        TextureRegion[][] tiles = TextureRegion.split(sheet, WIDTH, HEIGHT);
        stand = tiles[0][0];

    }

    @Override
    public void render () {
        move();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(stand, x, y, DRAW_WIDTH, DRAW_HEIGHT);
        batch.end();
    }

    float decelerate(float velocity) {
        float deceleration = 0.9f;
        velocity *= deceleration;
        if (Math.abs(velocity) < 1) {
            velocity = 0;
        }

        return velocity;
    }


    void move() {
        if (Gdx.input.isKeyPressed(Input.Keys.W) && canJump) {
            yv = MAX_JUMP_VELOCITY;
            canJump = false;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            yv = MAX_VELOCITY * -1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            xv = MAX_VELOCITY;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            xv = MAX_VELOCITY * -1;
        }

        yv += GRAVITY;

        y += yv * Gdx.graphics.getDeltaTime();
        x += xv * Gdx.graphics.getDeltaTime();

        if (y <0) {
            y = 0;
            canJump = true;
        }


        yv = decelerate(yv);
        xv = decelerate(xv);

    }
}
