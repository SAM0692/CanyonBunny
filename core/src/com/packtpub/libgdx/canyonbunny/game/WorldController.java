package com.packtpub.libgdx.canyonbunny.game;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.packtpub.libgdx.canyonbunny.util.CameraHelper;

public class WorldController extends InputAdapter {
    private static final String TAG = WorldController.class.getName();

    public Sprite[] testSprites;
    public int selectedSprite;

    public CameraHelper cameraHelper;

    public WorldController() {
        init();
    }

    private void init() {
        Gdx.input.setInputProcessor(this);
        cameraHelper = new CameraHelper();
        initTestObjects();
    }

    private void initTestObjects() {
        testSprites = new Sprite[5];

        int width = 32;
        int height = 32;

        Pixmap pixmap = createProceduralPixmap(width, height);

        Texture texture = new Texture(pixmap);

        for (int i = 0; i < testSprites.length; i++) {
            Sprite spr = new Sprite(texture);

            spr.setSize(1, 1);
            spr.setOrigin(spr.getWidth()/2, spr.getHeight()/2);

            float randomX= MathUtils.random(-2.0f, 2.0f);
            float randomY= MathUtils.random(-2.0f, 2.0f);

            spr.setPosition(randomX, randomY);

            testSprites[i] = spr;
        }

        selectedSprite = 0;
    }

    private Pixmap createProceduralPixmap(int width, int height) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);


        pixmap.setColor(1, 0, 0, 0.5f);
        pixmap.fill();

        pixmap.setColor(1, 1, 0, 1);
        pixmap.drawLine(0, 0, width, height);
        pixmap.drawLine(width, 0, 0, height);

        pixmap.setColor(0, 1, 1, 1);
        pixmap.drawRectangle(0, 0, width, height);

        return pixmap;
    }

    public void update(float deltaTime) {
        handleDebugInput(deltaTime);
        updateTestObjects(deltaTime);
        cameraHelper.update(deltaTime);
    }

    private void handleDebugInput(float deltaTime) {
        if(Gdx.app.getType() != Application.ApplicationType.Desktop){
            return;
        }

        // Test sprites controls
        float sprMovSpeed = 5 * deltaTime;
        if(Gdx.input.isKeyPressed(Keys.A)){
            moveSelectedSprite(-sprMovSpeed, 0);
        }if (Gdx.input.isKeyPressed(Keys.D)) {
            moveSelectedSprite(sprMovSpeed, 0);
        }if (Gdx.input.isKeyPressed(Keys.W)) {
            moveSelectedSprite(0, sprMovSpeed);
        }if (Gdx.input.isKeyPressed(Keys.S)) {
            moveSelectedSprite(0, -sprMovSpeed);
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

    private void moveSelectedSprite(float x, float y){
        testSprites[selectedSprite].translate(x, y);
    }
    private void moveCamera(float x, float y) {
        x += cameraHelper.getPosition().x;
        y += cameraHelper.getPosition().y;
        cameraHelper.setPosition(x, y);
    }

    private void updateTestObjects(float deltaTime) {
        float rotation = testSprites[selectedSprite].getRotation();

        rotation += 90 * deltaTime;
        rotation %= 360;

        testSprites[selectedSprite].setRotation(rotation);
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Keys.R) {
            init();
            Gdx.app.debug(TAG, "Game world reseted");
        }else if (keycode == Keys.SPACE) {
            selectedSprite = (selectedSprite + 1) % testSprites.length;
            if(cameraHelper.hasTarget()) {
                cameraHelper.setTarget(testSprites[selectedSprite]);
            }
            Gdx.app.debug(TAG, "Sprite #" + selectedSprite + " selected");
        }else if(keycode == Keys.ENTER) {
            cameraHelper.setTarget(cameraHelper.hasTarget() ? null : testSprites[selectedSprite]);
            Gdx.app.debug(TAG, "Camera follow enabled: " + cameraHelper.hasTarget());
        }
        return false;
    }
}
