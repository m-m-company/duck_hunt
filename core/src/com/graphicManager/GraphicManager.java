package com.graphicManager;

import com.background.Background;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.duck.Duck;

public class GraphicManager {
	
	private SpriteBatch batch;
	private Pixmap pm;
	
	public GraphicManager() {
		batch = new SpriteBatch();
		pm = new Pixmap(Gdx.files.internal("mouse/cursor.png"));
		Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, 0, 0));
	}
	
	public void clearDisplay() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
	public void drawDeathAnimation(Duck duck) {
		batch.begin();
		batch.draw(duck.getDeathAnimation(), duck.getX(), duck.getY());
		batch.end();
	}
	
	public void drawDuck(Duck duck) {
		batch.begin();
		if(duck.isDead())
			batch.draw(duck.fallAnimation(), duck.getX(), duck.getY());
		else
			batch.draw(duck.getFrame(), duck.getX(), duck.getY());
		batch.end();
	}
	
	public void drawBack(Background background) {
		batch.begin();
		batch.draw(background.getBack(), 0, 0);
		batch.end();
	}
	
	public void dispose() {
		batch.dispose();
		pm.dispose();
	}

}
