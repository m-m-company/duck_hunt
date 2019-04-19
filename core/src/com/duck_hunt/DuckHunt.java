package com.duck_hunt;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DuckHunt extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Sound sound;
	Pixmap pm;
	Duck d;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("background/back.png");
		sound = Gdx.audio.newSound(Gdx.files.internal("sound/shot.wav"));
		pm = new Pixmap(Gdx.files.internal("mouse/cursor.png"));
		d = new Duck();
		Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, 0, 0));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
			System.exit(0);
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			sound.play();
			batch.begin();
			batch.draw(d.getDeathAnimation(), d.getX(), d.getY());
			batch.end();
		}
		if(d.isDead())
			d.fall();
		else
			d.move();
		batch.begin();
		if(d.isDead())
			batch.draw(d.getFallAnimation(), d.getX(), d.getY());
		else
			batch.draw(d.getFrame(), d.getX(), d.getY());
		batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		sound.dispose();
		pm.dispose();
	}
}
