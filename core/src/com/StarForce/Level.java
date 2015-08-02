package com.StarForce;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;



public class Level {
	float w = Gdx.graphics.getWidth();
    float h = Gdx.graphics.getHeight();
	Hero hero;
	public Hero getHero() {
        return hero;
}
	public Level(){
		createLevel();
	}
	private void createLevel(){
	//hero = new Hero(new Vector2(2,2));
	}
	public void update(float delta){
		
	}
}