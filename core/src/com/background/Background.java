package com.background;

import com.badlogic.gdx.graphics.Texture;

public class Background {
	
	public final static int MINIMUM_Y = 90;
	public final static int MAXIMUM_Y = 470;
	public final static int MINIMUM_X = 0;
	public final static int MAXIMUM_X = 560;
	
	private Texture back;
	
	public Background() {
		back = new Texture("background/back.png");
	}

	public Texture getBack() {
		return back;
	}
	
	public void dispose() {
		back.dispose();
	}
}
