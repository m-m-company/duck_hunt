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
	
	double rechargeTime;
	int score;
	int dead;
	int ammo;
	int level;
	int deadDucks;
	GraphicManager graphic;
	SoundManager sound;
	Background background;
	ArrayList<Duck> ducks;
	
	@Override
	public void create () {
		rechargeTime = 0.0d;
		score = 0;
		dead = 0;
		ammo = Game.AMMO;
		level = 0;
		graphic = new GraphicManager();
		sound = new SoundManager();
		background = new Background();
		ducks = new ArrayList<Duck>();
		createDucks();
	}

	@Override
	public void render () {
		graphic.clearDisplay();
		this.inputControl();
		this.ducksManager();
		graphic.drawBack(background, score, ammo, level);
	}
	
	public void createDucks() {
		++level;
		for(int i = 0; i < 3; ++i)
			ducks.add(new Duck(level*10));
		ammo = Game.AMMO;
	}
	
	private void inputControl() {
		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
			Gdx.app.exit();
		rechargeTime += Gdx.graphics.getDeltaTime();
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && rechargeTime > 1.1d) {//do I like this sound shot?
			if(ammo > 0) {
				--ammo;
				float x = (Gdx.input.getX() + 32)*graphic.getFactorWidth();
				float y = (Gdx.graphics.getHeight() - 32 - Gdx.input.getY())*graphic.getFactorHeight();
				for(Duck d : ducks) {
					//System.out.println("duck X = " + d.getX() + " Y = " + d.getY());
					//System.out.println("mouse X = " + x + " Y = " + y);
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
				ducks.remove(i--);
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
