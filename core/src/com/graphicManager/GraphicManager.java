package com.graphicManager;

import com.background.Background;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.duck.Duck;

public class GraphicManager {
	
	private SpriteBatch batch;
	private Pixmap pm;
	private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private BitmapFont font;
	
	public GraphicManager() {
		batch = new SpriteBatch();
		pm = new Pixmap(Gdx.files.internal("mouse/cursor.png"));
		Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, 0, 0));
		generator = new FreeTypeFontGenerator(Gdx.files.internal("background/font.ttf"));
		parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		font = new BitmapFont();
		parameter.size = 40;
        parameter.color = Color.BLACK;
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
	
	public void drawBack(Background background, int score) {
		batch.begin();
		batch.draw(background.getBack(), 0, 0);
		font = generator.generateFont(parameter);
		font.draw(batch, Integer.toString(score), Background.MAXIMUM_X/2, Background.MAXIMUM_Y);
		batch.end();
		font.dispose();
	}
	
	public void dispose() {
		batch.dispose();
		pm.dispose();
	}

}
