package com.packtpub.libgdx.canyonbunny.game;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.MathUtils;
import com.packtpub.libgdx.canyonbunny.util.CameraHelper;
import com.packtpub.libgdx.canyonbunny.game.objects.Rock;
import com.packtpub.libgdx.canyonbunny.util.Constants;

public class WorldController extends InputAdapter {
    private static final String TAG = WorldController.class.getName();

    public CameraHelper cameraHelper;
    
    public Level level;
    public int score;
    public int lives;


    public WorldController() {
        init();
    }

    private void init() {
        Gdx.input.setInputProcessor(this);
        cameraHelper = new CameraHelper();
        lives = Constants.LIVES_START;
        initLevel();
    }
    
   private void initLevel() {
       score = 0;
       level = new Level(Constants.LEVEL_01);
   }

    public void update(float deltaTime) {
        handleDebugInput(deltaTime);
        cameraHelper.update(deltaTime);
    }

    private void handleDebugInput(float deltaTime) {
        if(Gdx.app.getType() != Application.ApplicationType.Desktop){
            return;
        }

        // Camera controls
        float camMovSpeed = 5 * deltaTime;
        float camMovSpeedAccelerationFactor = 5;
        if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)){
            camMovSpeed *= camMovSpeedAccelerationFactor;
        }if(Gdx.input.isKeyPressed(Keys.LEFT)){
            moveCamera(-camMovSpeed, 0);
        }if(Gdx.input.isKeyPressed(Keys.RIGHT)){
            moveCamera(camMovSpeed, 0);
        }if(Gdx.input.isKeyPressed(Keys.UP)){
            moveCamera(0, camMovSpeed);
        }if(Gdx.input.isKeyPressed(Keys.DOWN)){
            moveCamera(0, -camMovSpeed);
        }else if(Gdx.input.isKeyPressed(Keys.BACKSPACE)){
            cameraHelper.setPosition(0, 0);
        }

        // Camera controls(zoom)
        float camZoomSpeed = 1 * deltaTime;
        float camZoomSpeedAccelerationFactor = 5;
        if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
            camZoomSpeed *= camZoomSpeedAccelerationFactor;
        }if(Gdx.input.isKeyPressed(Keys.COMMA)){
            cameraHelper.addZoom(camZoomSpeed);
        }if(Gdx.input.isKeyPressed(Keys.PERIOD)){
            cameraHelper.addZoom(-camMovSpeed);
        }else if(Gdx.input.isKeyPressed(Keys.SLASH)){
            cameraHelper.setZoom(1);
        }
    }

    private void moveCamera(float x, float y) {
        x += cameraHelper.getPosition().x;
        y += cameraHelper.getPosition().y;
        cameraHelper.setPosition(x, y);
    }

    @Override
    public boolean keyUp(int keycode) {
        // Reset game world
        if (keycode == Keys.R) {
            init();
            Gdx.app.debug(TAG, "Game world reseted");
        }
        return false;
    }
}
