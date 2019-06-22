package com.manager;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.duck.Duck;

public class GameManager extends ApplicationAdapter {
	
	public final static int AMMO = 6;
	public final static int PLAY_STATUS = 1;
	public final static int QUIT_STATUS = 2;
	
	double rechargeTime;
	int score;
	int dead;
	int ammo;
	int level;
	int deadDucks;
	int life;
	int status;
	boolean menu;
	GraphicManager graphic;
	SoundManager sound;
	RecordManager recordManager;
	ArrayList<Duck> ducks;
	
	@Override
	public void create () {
		graphic = new GraphicManager();
		sound = new SoundManager();
		recordManager = new RecordManager();
		this.reset();
	}

	@Override
	public void render () {
		if(menu)
			this.menuManager();
		else {
			recordManager.changeHighScore(score);
			graphic.clearDisplay();
			this.ducksManager();
			graphic.drawBack(score, recordManager.getHighUser(), recordManager.getPoints(Integer.toString(score)), ammo, level, life);
			if(life == 0) {
				recordManager.update(Integer.toString(score));
				this.reset();
			}
			this.inputControl();
		}
	}
	
	private void menuManager() {
		graphic.drawMenu(status);
		if(status == GameManager.PLAY_STATUS && (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.DOWN)))
			status = GameManager.QUIT_STATUS;
		else if(status == GameManager.QUIT_STATUS && (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.DOWN)))
			status = GameManager.PLAY_STATUS;
		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
			Gdx.app.exit();
		if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			if(status == GameManager.PLAY_STATUS) {
				sound.restartBackground();
				menu = false;
				createDucks();
			}
			else if(status == GameManager.QUIT_STATUS)
				Gdx.app.exit();
		}
	}
	
	private void createDucks() {
		++level;
		for(int i = 0; i < 3; ++i)
			ducks.add(new Duck(level*10));
		ammo = GameManager.AMMO;
	}
	
	private void inputControl() {
		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
			this.reset();
		else {
			rechargeTime += Gdx.graphics.getDeltaTime();
			if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && rechargeTime > 1.1d) {
				if(ammo > 0) {
					--ammo;
					float x = (Gdx.input.getX() + 32)*graphic.getFactorWidth();
					float y = (Gdx.graphics.getHeight() - 32 - Gdx.input.getY())*graphic.getFactorHeight();
					for(Duck d : ducks) {
						if(d.collide(x, y) && !d.isDead()) {
							++deadDucks;
							score += 10*level;
							d.setDead(true);
							sound.playDeath();
						}
					}
					sound.playShot();
					rechargeTime = 0.0d;
				}
				else {
					rechargeTime = 0.0d;
					sound.playFailedShot();
				}
			}
			else if(deadDucks == 3 && rechargeTime > 1.1d) {
				deadDucks = 0;
				sound.playReload();
			}
		}
	}
	
	private void reset() {
		sound.restartBackground();
		rechargeTime = 0.0d;
		score = 0;
		dead = 0;
		ammo = GameManager.AMMO;
		level = 0;
		deadDucks = 0;
		life = 5;
		status = GameManager.PLAY_STATUS;
		menu = true;
		ducks = new ArrayList<Duck>();
	}
	
	private void ducksManager() {
		for(int i = 0; i < ducks.size(); ++i) {
			graphic.drawDuck(ducks.get(i));
			ducks.get(i).delayQuack += Gdx.graphics.getDeltaTime();
			if(ducks.get(i).isQuacking() && ducks.get(i).delayQuack > 0.5d) {
				sound.playQuack();
				ducks.get(i).delayQuack = 0.0d;
			}
			if(ducks.get(i).isDead() && ducks.get(i).hit < 0.4) {
				ducks.get(i).hit += Gdx.graphics.getDeltaTime();
				graphic.drawDuckHitted(ducks.get(i));
			}
			else if(ducks.get(i).isDead()) {
				if(ducks.get(i).fall())
					ducks.remove(i--);
			}
			else if(ducks.get(i).isOut())
			{
				ducks.remove(i--);
				--life;
			}
			else
				ducks.get(i).move();
		}
		if(ducks.isEmpty())
			createDucks();
	}
	
	@Override
	public void dispose () {
		graphic.dispose();
		sound.dispose();
	}
}
