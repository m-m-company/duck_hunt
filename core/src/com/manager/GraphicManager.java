package com.manager;

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

public class GraphicManager {
	
	private final static int START_X_AMMO = 15;
	private final static int Y_AMMO = 5;
	private final static int Y_SCORE = 50;
	private final static int Y_HIGH_SCORE = GraphicManager.Y_SCORE - 25;
	private final static int Y_LEVEL_STRING = GraphicManager.Y_SCORE;
	private final static int Y_LIFE_STRING = GraphicManager.Y_HIGH_SCORE;
	public final static int MINIMUM_Y = 90;
	public final static int MAXIMUM_Y = 470;
	public final static int MINIMUM_X = 0;
	public final static int MAXIMUM_X = 560;
	
	private SpriteBatch batch;
	private ShapeRenderer sh;
	private Pixmap pm;
	private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private BitmapFont font;
    private Texture background;
    private Texture bullet;
    private Texture playMenu;
    private Texture quitMenu;
    private int xScore;
    private int xLevelString;
    private int xLifeString;
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
		parameter.size = 22;
        parameter.color = Color.BLACK;
        background = new Texture("background/back.png");
        bullet = new Texture(Gdx.files.internal("background/ammo.png"));
        playMenu = new Texture(Gdx.files.internal("menu/playMenu.jpg"));
        quitMenu = new Texture(Gdx.files.internal("menu/quitMenu.jpg"));
        xScore = GraphicManager.START_X_AMMO + GameManager.AMMO*bullet.getWidth() + 80;
        xLevelString = xScore + 250;
        xLifeString = xLevelString;
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
	
	public void drawBack(int score, String highUser, String highScore, int ammo, int level, int life) {
		batch.begin();
		batch.draw(background, 0, 0);
		this.drawAmmo(ammo);
		font = generator.generateFont(parameter);
		this.drawScore(score);
		this.drawHighScore(highUser, highScore);
		this.drawLevel(level);
		this.drawLostDucks(life);
		batch.end();
		font.dispose();
		this.drawAmmoBorder();
	}
	
	public void drawMenu(int status) {
		batch.begin();
		if(status == GameManager.PLAY_STATUS)
			batch.draw(playMenu, 0, 0, width, height);
		else if(status == GameManager.QUIT_STATUS)
			batch.draw(quitMenu, 0, 0, width, height);
		batch.end();	
	}
	
	private void drawAmmo(int ammo) {
		for(int x = GraphicManager.START_X_AMMO; x < GraphicManager.START_X_AMMO + ammo*bullet.getWidth(); x += bullet.getWidth())
			batch.draw(bullet, x, GraphicManager.Y_AMMO);
	}
	
	private void drawScore(int score) {
		font.draw(batch, "YOU " + Integer.toString(score), this.xScore + 25, GraphicManager.Y_SCORE);
	}
	
	private void drawHighScore(String user, String highScore) {
		font.draw(batch, user + " " + highScore, xScore, GraphicManager.Y_HIGH_SCORE);
	}
	
	private void drawAmmoBorder() {
		sh.begin(ShapeType.Line);
		sh.setColor(Color.BLACK);
		sh.rect(GraphicManager.START_X_AMMO, GraphicManager.Y_AMMO, bullet.getWidth()*GameManager.AMMO, bullet.getHeight());
		sh.end();
	}
	
	private void drawLevel(int level) {
		font.draw(batch, "LEVEL " + level, this.xLevelString - 10, GraphicManager.Y_LEVEL_STRING);
	}
	
	private void drawLostDucks(int life) {
		font.draw(batch, "LIFE " + life, this.xLifeString, GraphicManager.Y_LIFE_STRING);
	}
	
	public void dispose() {
		batch.dispose();
		sh.dispose();
		pm.dispose();
		generator.dispose();
		background.dispose();
		bullet.dispose();
		playMenu.dispose();
		quitMenu.dispose();
	}

}
