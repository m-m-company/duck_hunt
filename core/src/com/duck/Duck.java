package com.duck;

import java.util.Random;

import com.background.Background;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Duck {
	
	final static int VELOCITY = 50;
	final static int RIGHT = 0;
	final static int LEFT = 1;
	final static int TOP_LEFT = 2;
	final static int TOP_RIGHT = 3;
	final static int LOWER_LEFT = 4;
	final static int LOWER_RIGHT = 5;
	
	private DuckGraphic g;
	private int direction;
	private float x;
	private float originY;
	private float y;
	private boolean isBorn;
	private boolean isDead;
	private double delayAnimation;
	private double delayChangeAnimation;
	
	private void selectDirection(Random r) {
		direction = r.nextInt(g.getMaximumDirections());
	}
	
	private void spawn() {
		isBorn = true;
		originY = Background.MINIMUM_Y;
		Random r = new Random();
		x = r.nextInt(Background.MAXIMUM_X);
		y = r.nextInt(Background.MAXIMUM_Y - 100);
		while(y < Background.MINIMUM_Y)
			y = r.nextInt(Background.MAXIMUM_Y - 100);
		direction = r.nextInt(4);
		g.setAnimations(direction);
	}
	
	public Duck() {
		delayAnimation = 0.0;
		delayChangeAnimation = 0.0;
		g = new DuckGraphic();
		isDead = false;
		spawn();
	}
	
	public Texture getFrame() {
		return g.getFrame();
	}
	
	public float getX() {
		return x;
	}

	public float getY() {
		if(isBorn)
			return originY;
		return y;
	}

	public void move() {
		if(!isBorn) {
			delayChangeAnimation += (double)Duck.VELOCITY/200;
			if(direction == Duck.RIGHT) {
				x += Gdx.graphics.getDeltaTime()*Duck.VELOCITY;
				if(x >= Background.MAXIMUM_X && delayChangeAnimation > 6) {
					selectDirection(new Random());
					g.setAnimations(direction);
					delayChangeAnimation = 0.0;
				}
			}
			if(direction == Duck.LEFT) {
				x -= Gdx.graphics.getDeltaTime()*Duck.VELOCITY;
				if(x <= Background.MINIMUM_X && delayChangeAnimation > 4) {
					selectDirection(new Random());
					g.setAnimations(direction);
					delayChangeAnimation = 0.0;
				}
			}
			if(direction == Duck.TOP_LEFT) {
				x -= Gdx.graphics.getDeltaTime()*Duck.VELOCITY;
				y += Gdx.graphics.getDeltaTime()*Duck.VELOCITY;
				if((x <= Background.MINIMUM_X || y >= Background.MAXIMUM_Y) && delayChangeAnimation > 4) {
					selectDirection(new Random());
					g.setAnimations(direction);
					delayChangeAnimation = 0.0;
				}
			}
			if(direction == Duck.TOP_RIGHT) {
				x += Gdx.graphics.getDeltaTime()*Duck.VELOCITY;
				y += Gdx.graphics.getDeltaTime()*Duck.VELOCITY;
				if((x >= Background.MAXIMUM_X || y >= Background.MAXIMUM_Y) && delayChangeAnimation > 4) {
					selectDirection(new Random());
					g.setAnimations(direction);
					delayChangeAnimation = 0.0;
				}
			}
			if(direction == Duck.LOWER_LEFT) {
				x -= Gdx.graphics.getDeltaTime()*Duck.VELOCITY;
				y -= Gdx.graphics.getDeltaTime()*Duck.VELOCITY;
				if((x <= Background.MINIMUM_X || y <= Background.MINIMUM_Y + 20) && delayChangeAnimation > 4) {
					selectDirection(new Random());
					g.setAnimations(direction);
					delayChangeAnimation = 0.0;
				}
			}
			if(direction == Duck.LOWER_RIGHT) {
				x += Gdx.graphics.getDeltaTime()*Duck.VELOCITY;
				y -= Gdx.graphics.getDeltaTime()*Duck.VELOCITY;
				if((x >= Background.MAXIMUM_X || y <= Background.MINIMUM_Y) && delayChangeAnimation > 4) {
					selectDirection(new Random());
					g.setAnimations(direction);
					delayChangeAnimation = 0.0;
				}
			}
		}
		else {
			if(originY < y) {
				originY += Gdx.graphics.getDeltaTime()*Duck.VELOCITY;
			}
			else
				isBorn = false;
		}
		delayAnimation += (double)Duck.VELOCITY/200;
		if(delayAnimation > 2) {
			g.switchAnimation();
			delayAnimation = 0.0f;
		}
	}
	
	public Texture getDeathAnimation() {
		isDead = true;
		return g.getHitted();
	}
	
	public boolean isDead() {
		return isDead;
	}
	
	public boolean fall() {//restituisce true se deve ancora cadere
		y -= Gdx.graphics.getDeltaTime()*Duck.VELOCITY;
		if(y > Background.MAXIMUM_Y)
			return false;
		return true;
	}
	
	public Texture fallAnimation() {
		return g.fallAnimation(direction);
	}
	
	public boolean collide(float x, float y) {
		return x >= this.x && x <= this.x+75 && y >= this.y && y <= this.y+75;
	}

}
