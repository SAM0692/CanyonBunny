package com.packtpub.libgdx.canyonbunny;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.packtpub.libgdx.canyonbunny.game.WorldController;
import com.packtpub.libgdx.canyonbunny.game.WorldRenderer;

public class CanyonBunnyMain implements ApplicationListener {

	private static final String TAG = CanyonBunnyMain.class.getName();

	private WorldController worldController;
	private WorldRenderer worldRenderer;

	private boolean pause;

	@Override
	public void create() {
		// Set the log level to debug
		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		// Initialize controller and renderer
		worldController = new WorldController();
		worldRenderer = new WorldRenderer(worldController);

		pause = false;
	}

	@Override
	public void resize(int width, int height) {
		// In the event of any screen resize
		worldRenderer.resize(width, height);
	}

	@Override
	public void render() {
		// Update the game world if the game is not paused
		if (!pause) {
			worldController.update(Gdx.graphics.getDeltaTime());
		}

		Gdx.gl.glClearColor(100/255.0f, 149/255.0f, 237/255.0f, 255/255.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		worldRenderer.render();
	}

	@Override
	public void pause() {
		pause = true;
	}

	@Override
	public void resume() {
		pause = false;
	}

	@Override
	public void dispose() {
		worldRenderer.dispose();
	}
}
