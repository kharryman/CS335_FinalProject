package com.mm.sprites.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Met extends Enemy {
	Texture metSheet;
	TextureRegion[] metFrames;
	TextureRegion currentFrame;
	public Animation anim;
	
	public float stateTime;

	public Met(World world, int startX, int startY) {
		super(world, startX, startY);
		// TODO Auto-generated constructor stub
		applySprite();
	}

	public void applySprite() {
		int FRAME_COLS = 3;
		int FRAME_ROWS = 1;
		metSheet = new Texture(Gdx.files.internal("metWalk.png"));
		TextureRegion[][] tmp = TextureRegion.split(metSheet,metSheet.getWidth()/FRAME_COLS, metSheet.getHeight()/FRAME_ROWS);
		metFrames = new TextureRegion[3];
		int index = 0;
		for(int i = 0; i < FRAME_ROWS; i++) {
			for(int j = 0; j < FRAME_COLS; j++) {
				metFrames[j] = tmp[i][j];
			}
		}
		
		anim = new Animation(0.15f, metFrames);
		stateTime = 0f;
		//don't use another sprite batch as they are very resource intensive.
		//just reuse the same sprite sheet from the main class
		
		
	}
	@Override
	public void defineEnemy(int startX, int startY) {
		// 	public void defineEnemy(int startX, int startY) {
		bdef.position.set((startX)/ppm, (startY)/ppm);
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = world.createBody(bdef);
		
		FixtureDef fdef = new FixtureDef();
		
		CircleShape shape = new CircleShape();
		shape.setRadius(5/ppm);
		
		
		fdef.shape = shape;
		b2body.createFixture(fdef);
		
		if(this.b2body.getLinearVelocity().y > 2) {
			this.b2body.setLinearVelocity(0, .01f);
		}
	}
	
}
