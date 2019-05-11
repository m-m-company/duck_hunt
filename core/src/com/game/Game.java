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
	
	final static int STAGE_1 = 100;
	final static int STAGE_2 = 300;
	final static int STAGE_3 = 600;
	final static int STAGE_4 = 1000;
	public final static int AMMO = 6;
	
	double rechargeTime;
	int score;
	int dead;
	int ammo;
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
			else
				ducks.get(i).move();
		}
		if(ducks.isEmpty())
			createDucks();
		graphic.drawBack(background, score, ammo);
	}
	
	public void createDucks() {
		if(score >= 0 && score <= Game.STAGE_1) {
			ducks.add(new Duck());
		}
		else if(score > Game.STAGE_1 && score <= Game.STAGE_2) {
			ducks.add(new Duck());
			ducks.add(new Duck());
		}
		else if(score > Game.STAGE_2 && score <= Game.STAGE_3) {
			ducks.add(new Duck());
			ducks.add(new Duck());
			ducks.add(new Duck());
		}
		else if(score > Game.STAGE_3 && score <= Game.STAGE_4) {
			ducks.add(new Duck());
			ducks.add(new Duck());
			ducks.add(new Duck());
			ducks.add(new Duck());
		}
	}
	
	public void inputControl() {
		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
			Gdx.app.exit();
		rechargeTime += Gdx.graphics.getDeltaTime();
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && rechargeTime > 1.1d) {//do I like this sound shot?
			if(ammo > 0) {
				--ammo;
				float x = (Gdx.input.getX() + 32)*graphic.getFactorWidth();
				float y = (Gdx.graphics.getHeight() - 32 - Gdx.input.getY())*graphic.getFactorHeight();
				for(Duck d : ducks) {
					//System.out.println("paperella X = " + d.getX() + " Y = " + d.getY());
					//System.out.println("mouse X = " + x + " Y = " + y);
					if(d.collide(x, y)) {
						score += 20;
						d.setDead(true);
						sound.playDeath();
					}
				}
				sound.playShot();
				rechargeTime = 0.0d;
			}
			else if(rechargeTime > 1.1d) {
				rechargeTime = 0.0d;
				sound.playFailedShot();
			}
		}
	}
	
	@Override
	public void dispose () {
		graphic.dispose();
		sound.dispose();
		background.dispose();
	}
}
