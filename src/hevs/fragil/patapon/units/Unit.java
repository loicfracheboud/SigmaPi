package hevs.fragil.patapon.units;
import com.badlogic.gdx.math.Vector2;

import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import hevs.fragil.patapon.drawables.SpriteSheet;
import hevs.fragil.patapon.mechanics.Param;
import hevs.fragil.patapon.physics.BodyPolygon;

public abstract class Unit implements DrawableObject{
	int level = 1;
	int species = 1;
	protected int life;
	protected int attack;
	protected int defense;
	protected int distance;
	protected int range;
	protected int cooldown;
	protected long lastAttack = 0;
	BodyPolygon hitBox;
	static SpriteSheet legs;
	static int nUnits;
	SpriteSheet body, eye;
	int walkIndex;
	
	Unit(int lvl, int species, int attack, int defense, int life, int distance, int range, int cooldown){
		this.species = species;
		this.level = lvl;
		this.life = Param.LIFE_BASE + level * 5;
		this.attack = attack;
		this.defense = defense;
		this.distance = distance;
		this.life = life;
		this.range = range;
		this.cooldown = cooldown;
		nUnits++;
	}	
	public void setPosition(int newPos){
		if(hitBox != null)
			hitBox.setPosition(newPos);
		else
			hitBox = new BodyPolygon(new Vector2(newPos, Param.FLOOR_DEPTH));
	}
	protected int getPosition(){
		return (int)hitBox.getBodyWorldCenter().x;
	}
	protected void setLife(int d){
		this.life = d;
	}
	public String toString(){
		return ", Level : "+ level + ", Life : " + life;
	}
	public abstract void attack();
	public abstract void draw(GdxGraphics g, float time);
	public void drawLegs(float stateTime){
		walkIndex = legs.drawKeyFrames(stateTime, getPosition()-32);
	}
	public void drawBody(float stateTime){
		body.drawWalkAnimation(walkIndex, (5*(species-1))+(level-1), getPosition()-32, 40);
	}
	public void drawEye() {
		//TODO get unit state to change the eye expression
		eye.drawWalkAnimation(walkIndex, 1, getPosition()-32, 52);
	}
	//only to load files in the onInit method
	public static void setLegsSprite(String url, int cols, int rows){
		legs = new SpriteSheet(url, cols , rows, 0.2f);
	}
	public void setBodySprite(String url, int cols, int rows) {
		body = new SpriteSheet(url, cols , rows, 0.2f);		
	}
	public void setEyeSprite(String url, int cols, int rows) {
		eye = new SpriteSheet(url, cols , rows, 0.2f);		
	}
	
}
