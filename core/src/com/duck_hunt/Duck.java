package com.duck_hunt;

import java.util.Random;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Queue;

public class Duck {
	
	private final static int VELOCITY = 50;
	private final static int RIGHT = 0;
	private final static int LEFT = 1;
	private final static int TOP_LEFT = 2;
	private final static int TOP_RIGHT = 3;
	private final static int LOWER_LEFT = 4;
	private final static int LOWER_RIGHT = 5;
	
	private String directions[] = {"right", "left", "topLeft", "topRight", "lowerLeft", "lowerRight"};
	private Queue<Texture> animations;
	private Texture frame;
	private float delayAnimation;
	private int direction;
	private float x;
	private float originY;
	private float y;
	private boolean isBorn;
	private boolean isDead;
	
	private void selectDirection(Random r) {
		direction = r.nextInt(directions.length);
	}
	
	private void loadAnimations() {
		animations = new Queue<Texture>();
		for(int i = 0; i < 3; ++i)
			animations.addLast(new Texture("duck/" + directions[direction] + i + ".png"));
		frame = animations.first();
	}
	
	private void spawn() {
		isBorn = true;
		originY = Background.MINIMUM_Y;
		Random r = new Random();
		x = r.nextInt(Background.MAXIMUM_X);
		y = r.nextInt(Background.MAXIMUM_Y - 100);
		while(y < Background.MINIMUM_Y)
			y = r.nextInt(Background.MAXIMUM_Y - 100);
		selectDirection(r);
		loadAnimations();
	}
	
	public Duck() {
		delayAnimation = 0.0f;
		isDead = false;
		spawn();
	}
	
	public Texture getFrame() {
		return frame;
	}
	
	private void switchAnimation() {
		frame = animations.removeFirst();
		animations.addLast(frame);
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
			switch(direction) {
				case Duck.RIGHT:
					if(x >= Background.MAXIMUM_X) {
						selectDirection(new Random());
						loadAnimations();
					}
					x += Gdx.graphics.getDeltaTime()*Duck.VELOCITY;
					break;
				case Duck.LEFT:
					if(x <= Background.MINIMUM_X) {
						selectDirection(new Random());
						loadAnimations();
					}
					x -= Gdx.graphics.getDeltaTime()*Duck.VELOCITY;
					break;
				case Duck.TOP_LEFT:
					if(x <= Background.MINIMUM_X || x >= Background.MAXIMUM_Y) {
						selectDirection(new Random());
						loadAnimations();
					}
					x -= Gdx.graphics.getDeltaTime()*Duck.VELOCITY;
					y += Gdx.graphics.getDeltaTime()*Duck.VELOCITY;
					break;
				case Duck.TOP_RIGHT:
					if(x >= Background.MAXIMUM_X || x >= Background.MAXIMUM_Y) {
						selectDirection(new Random());
						loadAnimations();
					}
					x += Gdx.graphics.getDeltaTime()*Duck.VELOCITY;
					y += Gdx.graphics.getDeltaTime()*Duck.VELOCITY;
					break;
				case Duck.LOWER_LEFT:
					if(x <= Background.MINIMUM_X || y <= Background.MINIMUM_Y + 20) {
						selectDirection(new Random());
						loadAnimations();
					}
					x -= Gdx.graphics.getDeltaTime()*Duck.VELOCITY;
					y -= Gdx.graphics.getDeltaTime()*Duck.VELOCITY;
					break;
				case Duck.LOWER_RIGHT:
					if(x >= Background.MAXIMUM_X || y <= Background.MINIMUM_Y) {
						selectDirection(new Random());
						loadAnimations();
					}
					x += Gdx.graphics.getDeltaTime()*Duck.VELOCITY;
					y -= Gdx.graphics.getDeltaTime()*Duck.VELOCITY;
					break;
				default:
					System.out.println("ERROR");
					break;
			}
		}
		else {
			if(originY < y) {
				originY += Gdx.graphics.getDeltaTime()*Duck.VELOCITY;
			}
			else
				isBorn = false;
		}
		delayAnimation += (float)Duck.VELOCITY/200;
		if(delayAnimation > 2) {
			switchAnimation();
			delayAnimation = 0.0f;
		}
	}
	
	public Texture getDeathAnimation() {
		isDead = true;
		return new Texture("duck/hitted.png");
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
	
	public Texture getFallAnimation() {
		if(direction == Duck.LEFT || direction == Duck.TOP_LEFT || direction == Duck.LOWER_LEFT)
			return new Texture("duck/fallLeft.png");
		else
			return new Texture("duck/fallRight.png");
	}

}
