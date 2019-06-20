package com.game;

import java.util.ArrayList;

import com.background.Background;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.duck.Duck;
import com.graphicManager.GraphicManager;
import com.sound.SoundManager;

public class Game extends ApplicationAdapter {
	
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
	Background background;
	ArrayList<Duck> ducks;
	
	@Override
	public void create () {
		graphic = new GraphicManager();
		sound = new SoundManager();
		background = new Background();
		this.reset();
	}

	@Override
	public void render () {
		if(menu)
			this.menuManager();
		else {
			graphic.clearDisplay();
			this.ducksManager();
			graphic.drawBack(background, score, ammo, level, life);
			if(life == 0) 
				this.reset();
			this.inputControl();
		}
	}
	
	private void menuManager() {
		graphic.drawMenu(status);
		if(status == Game.PLAY_STATUS && (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.DOWN)))
			status = Game.QUIT_STATUS;
		else if(status == Game.QUIT_STATUS && (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.DOWN)))
			status = Game.PLAY_STATUS;
		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
			Gdx.app.exit();
		if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			if(status == Game.PLAY_STATUS) {
				menu = false;
				createDucks();
			}
			else if(status == Game.QUIT_STATUS)
				Gdx.app.exit();
		}
	}
	
	private void createDucks() {
		++level;
		for(int i = 0; i < 3; ++i)
			ducks.add(new Duck(level*10));
		ammo = Game.AMMO;
	}
	
	private void inputControl() {
		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
			this.reset();
		else {
			rechargeTime += Gdx.graphics.getDeltaTime();
			if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && rechargeTime > 1.1d) {//do I like this sound shot?
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
		rechargeTime = 0.0d;
		score = 0;
		dead = 0;
		ammo = Game.AMMO;
		level = 0;
		deadDucks = 0;
		life = 5;
		status = Game.PLAY_STATUS;
		menu = true;
		ducks = new ArrayList<Duck>();
		System.out.println("si");
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
		background.dispose();
	}
}
