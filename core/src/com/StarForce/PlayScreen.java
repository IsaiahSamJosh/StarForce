package com.StarForce;


import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public class PlayScreen implements Screen, InputProcessor {
	private int width, height;
    private WorldRenderer renderer;
    private WorldController controller;
    StarForce game;
	public PlayScreen(StarForce game){
    	this.game = game;
    }
	@Override
	public void render(float delta) {
	Gdx.gl.glClearColor(0, 0, 0, 1); //sets the color (black), last parameter is the opacity
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);//clears the screen with the created color

	controller.update(delta);
	renderer.update(delta);
    renderer.render();

	}

	@Override
	public void resize(int width, int height) {
		this.width = width;
        this.height = height;

	}

	@Override
	public void show() {
		renderer = new WorldRenderer(game); //first param was world
        controller = new WorldController(renderer,game);
        Gdx.input.setInputProcessor(this);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.LEFT){ //lets you go left using the left arrow key
            controller.leftPressed();
		}
        if (keycode == Keys.RIGHT){ //lets you go right using the right arrow key
            controller.rightPressed();
        }
        if(keycode ==Keys.A){
        	controller.leftPressed();
        }
        if(keycode == Keys.D){
        	controller.rightPressed();
        }
        if (keycode==Keys.Z) {
        	MyInput.setKey(MyInput.BUTTON1, true);
        }
        if (keycode==Keys.X) {
        	MyInput.setKey(MyInput.BUTTON2, true);
        }
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.LEFT){
            controller.leftReleased();
		}
		if (keycode == Keys.RIGHT){
            controller.rightReleased();
		}
		if (keycode == Keys.A){
			controller.leftReleased();
		}
		if(keycode == Keys.D) {
			controller.rightReleased();
		}
		 if (keycode==Keys.Z) {
	        	MyInput.setKey(MyInput.BUTTON1, false);
	        }
	        if (keycode==Keys.X) {
	        	MyInput.setKey(MyInput.BUTTON2, false);
	        }
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		if (!Gdx.app.getType().equals(ApplicationType.Android)) // if the application is not an android device, don't do anything
            return false;
    if (x < width / 2 - (width/6) && y > height / 2) {
            controller.leftPressed();
    }
    if (x > width / 2 + (width/6) && y > height / 2) {
            controller.rightPressed();
    }
    if(x > width/3 && x < width/2 + (width/6)){
    }
		return true;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		if (!Gdx.app.getType().equals(ApplicationType.Android)) // if the application is not an android device, don't do anything
            return false;
    if (x < width/ 2 - (width/6) && y > height / 2) {
            controller.leftReleased();
    }
    if (x > width / 2 + (width/6) && y > height / 2) {
            controller.rightReleased();
    }
    return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
