package com.graphicManager;

import com.background.Background;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.duck.Duck;
import com.game.Game;

public class GraphicManager {
	
	private final static int START_X_AMMO = 30;
	private final static int Y_AMMO = 5;
	private final static int Y_SCORE = 40;
	private final static int Y_LEVEL_STRING = GraphicManager.Y_SCORE;
	
	private SpriteBatch batch;
	private ShapeRenderer sh;
	private Pixmap pm;
	private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private BitmapFont font;
    private Texture bullet;
    private int xScore;
    private int xLevelString;
    private int width;
    private int height;
	
	public GraphicManager() {
		//Gdx.graphics.setWindowedMode(800, 600);
		batch = new SpriteBatch();
		sh = new ShapeRenderer();
		pm = new Pixmap(Gdx.files.internal("mouse/cursor.png"));
		Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, 0, 0));
		generator = new FreeTypeFontGenerator(Gdx.files.internal("background/font.ttf"));
		parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		font = new BitmapFont();
		parameter.size = 40;
        parameter.color = Color.BLACK;
        bullet = new Texture(Gdx.files.internal("background/ammo.png"));
        xScore = GraphicManager.START_X_AMMO + Game.AMMO*bullet.getWidth() + 100;
        xLevelString = xScore + 150;
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
	}
	
	public float getFactorWidth() {
		return (float)width/Gdx.graphics.getWidth();
	}
	
	public float getFactorHeight() {
		return (float)height/Gdx.graphics.getHeight();
	}
	
	public void clearDisplay() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
	public void drawDuckHitted(Duck duck) {
		batch.begin();
		batch.draw(duck.getHittedAnimation(), duck.getX(), duck.getY());
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
	
	public void drawBack(Background background, int score, int ammo, int level) {
		batch.begin();
		batch.draw(background.getBack(), 0, 0);
		this.drawAmmo(ammo);
		font = generator.generateFont(parameter);
		this.drawScore(score);
		this.drawLevel(level);
		batch.end();
		font.dispose();
		this.drawAmmoBorder();
	}
	
	private void drawAmmo(int ammo) {
		for(int x = GraphicManager.START_X_AMMO; x < GraphicManager.START_X_AMMO + ammo*bullet.getWidth(); x += bullet.getWidth())
			batch.draw(bullet, x, GraphicManager.Y_AMMO);
	}
	
	private void drawScore(int score) {
		font.draw(batch, Integer.toString(score), this.xScore, GraphicManager.Y_SCORE);
	}
	
	private void drawAmmoBorder() {
		sh.begin(ShapeType.Line);
		sh.setColor(Color.BLACK);
		sh.rect(GraphicManager.START_X_AMMO, GraphicManager.Y_AMMO, bullet.getWidth()*Game.AMMO, bullet.getHeight());
		sh.end();
	}
	
	private void drawLevel(int level) {
		font.draw(batch, "LEVEL " + level, this.xLevelString, GraphicManager.Y_LEVEL_STRING);
	}
	
	public void dispose() {
		batch.dispose();
		sh.dispose();
		pm.dispose();
	}

}
