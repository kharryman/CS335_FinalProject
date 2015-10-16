package com.mm.sprites.enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mm.main.MegaManGame;
import com.mm.screens.PlayScreen;

public abstract class Enemy extends Sprite{
	protected World world;
	protected PlayScreen screen;
	public Body b2body;
	public float ppm = MegaManGame.PPM;
	public BodyDef bdef = new BodyDef();
	public int hitPoints;
	public int damageCaused;
	public Vector2 velocity;
	

	public Enemy(PlayScreen screen, float x, float y){			
		this.world = screen.getWorld();
		this.screen = screen;
		setPosition(x, y);		
		defineEnemy(x, y);
		velocity = new Vector2(0,0);
	}
	
	public void reverseVelocity(boolean x, boolean y){
		if (x){
			velocity.x = -velocity.x;		    
		}
		if (y){
			velocity.y = -velocity.y;
		}
	}
	
	protected abstract void defineEnemy(float x, float y);
	public abstract void update(float dt);	
	public abstract void onHit();
		
}
