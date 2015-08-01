package com.StarForce;

import com.badlogic.gdx.Game;

public class StarForce extends Game {

	
	@Override
	public void create () {
  setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
        super.render();
	}
	
	public void dispose(){
		super.dispose();
		
		getScreen().dispose();
	}
	
	public void resize(int width, int height){
		super.resize(width,height);
	}
	
	public void pause(){
		super.pause();
	}
	
	public void resume(){
		super.resume();
	}
}