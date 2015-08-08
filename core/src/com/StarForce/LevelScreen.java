package com.StarForce;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class LevelScreen implements Screen {
	Stage stage;
	Skin skin;
	BitmapFont font;
	Pixmap pixmap;
	TextureAtlas atlas;
	StarForce game;
	public LevelScreen(StarForce game){
		this.game = game;
	}
		public void render(float delta) {
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			stage.act(Gdx.graphics.getDeltaTime());
	        stage.draw();

	        //stage.setDebugAll(true); // This is optional, but enables debug lines for tables.
			
		}

		@Override
		public void resize(int width, int height) {
			stage.getViewport().update(width, height, true);
		}

		@Override
		public void show() {
			stage = new Stage();
			Gdx.input.setInputProcessor(stage);
		    font = new BitmapFont(Gdx.files.internal("gamefonts.fnt"));
			Table table = new Table();
			table.setFillParent(true);
			stage.addActor(table);
	       atlas = new TextureAtlas(Gdx.files.internal("SpriteSheet.txt"));
			// Add widgets to the table here.
	       TextureRegion upr = atlas.findRegion("defaultbutton");
	        // A skin can be loaded via JSON or defined programmatically, either is fine. Using a skin is optional but strongly
	        // recommended solely for the convenience of getting a texture, region, etc as a drawable, tinted drawable, etc.
	        skin = new Skin();

	        // Generate a 1x1 white texture and store it in the skin named "white".
	        pixmap = new Pixmap(1, 1, Format.RGBA8888);
	        pixmap.setColor(Color.WHITE);
	        pixmap.fill();
	        skin.add("white", new Texture(pixmap));

	        // Store the default libgdx font under the name "default".
	        skin.add("default", new BitmapFont()); //default is the name of the skin

	        // Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
	        TextButtonStyle textButtonStyle = new TextButtonStyle();
	        textButtonStyle.up = new TextureRegionDrawable(upr);
	        textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
	        textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
	        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
	        textButtonStyle.font = font;
	        skin.add("default", textButtonStyle);
	        // Create a button with the "default" TextButtonStyle. A 3rd parameter can be used to specify a name other than "default".
	        final TextButton level1 = new TextButton("Level 1", skin);
	        final TextButton level2 = new TextButton("Level 2", skin);
	        final TextButton level3 = new TextButton("Level 3", skin);
	        final TextButton level4 = new TextButton("Level 4", skin);
	        table.add(level1).width(50).height(30);
	        table.row();
	        table.add(level2).width(50).height(30);
	        table.row();
	        table.add(level3).width(50).height(30);
	        table.row();
	        table.add(level4).width(50).height(30);
	        level1.addListener(new ChangeListener() {
	        	public void changed (ChangeEvent event, Actor actor) {
	                       game.setScreen(new PlayScreen(game));
	        	
	        	}
	        });
        level3.addListener(new ChangeListener(){
        	public void changed (ChangeEvent event, Actor actor) {
        		   game.setScreen(new PlayScreen(game));
        	}
        });
	       level4.addListener(new ChangeListener(){
	        	public void changed (ChangeEvent event, Actor actor) {
	        		   game.setScreen(new PlayScreen(game));
	        	}
	        });
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
			stage.dispose();
			skin.dispose();
			font.dispose();
			pixmap.dispose();
			atlas.dispose();
			Gdx.input.setInputProcessor(null);
		}

	}
