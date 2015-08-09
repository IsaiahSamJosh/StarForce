package com.StarForce;

import java.util.HashMap;
import java.util.Map;

import com.StarForce.Hero.State;
import com.StarForce.Hero;
import com.badlogic.gdx.math.Vector2;





public class WorldController {
	private Hero hero;
	private EnemyGrunt eg;
    enum Keys {
        LEFT, RIGHT, CLIMBING, FIRE, W, S, rBLOCK, lBLOCK
}
	static Map<Keys, Boolean> keys = new HashMap<WorldController.Keys, Boolean>();
	static {
	        keys.put(Keys.LEFT, false);
	        keys.put(Keys.W, false);
	        keys.put(Keys.S, false);
	        keys.put(Keys.RIGHT, false);
	};
	public WorldController(WorldRenderer renderer, StarForce game) { //was World world!
		this.hero=renderer.getHero();
		this.eg=renderer.getEnemyGrunt();
}
	public void leftPressed() {
        keys.get(keys.put(Keys.LEFT, true));
        //left arrow key
}
	public void rightPressed() {
        keys.get(keys.put(Keys.RIGHT, true));
}
	public void leftReleased() {
        keys.get(keys.put(Keys.LEFT, false));
}

public void rightReleased() {
        keys.get(keys.put(Keys.RIGHT, false));

}
public void update(float delta) {
	processInput(delta);
	updateHero(delta);
}
public void updateHero(float delta){
	hero.update(delta);
	eg.update(delta);
}
private void processInput(float delta) {
	if (keys.get(Keys.LEFT)) {
		hero.getBody().applyForceToCenter(new Vector2(-10, 0), true);
		hero.setFacingLeft(true);
        hero.setState(State.WALKING);
        if(hero.getPosition().x < eg.getPosition().x)
        	eg.getBody().applyForceToCenter(new Vector2(-5,0), true);
        else
        	eg.getBody().applyForceToCenter(new Vector2(5,0), true);
	}
	if (keys.get(Keys.RIGHT)) {
		hero.getBody().applyForceToCenter(new Vector2(10, 0), true);
		hero.setFacingLeft(false);
        hero.setState(State.WALKING);
        if(hero.getPosition().x < eg.getPosition().x)
        	eg.getBody().applyForceToCenter(new Vector2(-5,0), true);
        else
        	eg.getBody().applyForceToCenter(new Vector2(5,0), true);
	}
	if ((keys.get(Keys.LEFT) && keys.get(Keys.RIGHT)) ||
            (!keys.get(Keys.LEFT) && !(keys.get(Keys.RIGHT)))) {
		 hero.setState(State.IDLE);
		 if(hero.getPosition().x < eg.getPosition().x)
	        	eg.getBody().applyForceToCenter(new Vector2(-5,0), true);
	        else
	        	eg.getBody().applyForceToCenter(new Vector2(5,0), true);
	}
}
}
