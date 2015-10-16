package com.mm.sprites.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.mm.main.MegaManGame;
import com.mm.screens.PlayScreen;
import com.mm.sprites.MegaMan;

public class Met extends Enemy {
	public float stateTime ,moveTime;
	public Animation anim;
	
	Texture metSheet;
	private TextureRegion[] frames;
	TextureRegion currentFrame;
	private boolean setToDestroy;
	private boolean destroyed;
	//velocity animation:
	private Vector2[] moves;
	private int moveIndex;
	

	

	public Met(PlayScreen screen, float x, float y) {
		super(screen, x, y);
		setToDestroy=false;
		destroyed=false;
		velocity = new Vector2(0,0);
		moves = new Vector2[4];
		moves[0] = new Vector2(-0.5f,0);
		moves[1] = new Vector2(-0,0.5f);
		moves[2] = new Vector2(0,-0.5f);
		moves[3] = new Vector2(0.5f,0);
		animate();
		moveTime=0;
		moveIndex=0;
	}

	public void animate() {
		int FRAME_COLS = 3;
		int FRAME_ROWS = 1;
		metSheet = new Texture(Gdx.files.internal("metWalk.png"));
		TextureRegion[][] tmp = TextureRegion.split(metSheet,
				metSheet.getWidth() / FRAME_COLS, metSheet.getHeight()
						/ FRAME_ROWS);
		frames = new TextureRegion[3];
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				frames[j] = new TextureRegion(tmp[i][j]);
			}
		}
		anim = new Animation(0.15f, frames);
		stateTime = 0f;
		setBounds(getX(), getY(), 16 / MegaManGame.PPM, 16 / MegaManGame.PPM);
	}
	
		
	
	public void update(float dt){
		stateTime += dt;
		moveTime += dt;
		
		if (setToDestroy && !destroyed){
			world.destroyBody(b2body);
			setRegion(new TextureRegion());
			stateTime = 0;
			moveTime = 0;
		}
		else if(!destroyed){
			if (moveTime>0.15f){
				moveIndex++;
				if (moveIndex>moves.length - 1){
					moveIndex = 0;
				}				
				velocity = moves[moveIndex];
				moveTime=0;
			}
	    b2body.setLinearVelocity(velocity);
		setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y / 2);
		setRegion(anim.getKeyFrame(stateTime, true));
		}
	}

	@Override
	public void defineEnemy(float startX, float startY) {
		BodyDef bdef = new BodyDef();
		bdef.position.set(getX(), getY()); // start position on map
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = world.createBody(bdef);

		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape(); // mega mans hit box shape
		shape.setRadius(7 / ppm);

		fdef.filter.categoryBits = MegaMan.ENEMY_BIT;
		fdef.filter.maskBits = MegaMan.GROUND_BIT | MegaMan.ENEMY_BIT
				| MegaMan.OBJECT_BIT;

		fdef.shape = shape;
		b2body.createFixture(fdef).setUserData(this);
		
		//Create Head here to allow for jumping on top:
		PolygonShape head = new PolygonShape();
		Vector2[] vertice = new Vector2[4];
		vertice[0] = new Vector2(-5 , 8).scl(1 / MegaManGame.PPM);
		vertice[1] = new Vector2(5 , 8).scl(1 / MegaManGame.PPM);
		vertice[2] = new Vector2(-3 , 3).scl(1 / MegaManGame.PPM);
		vertice[3] = new Vector2(3 , 3).scl(1 / MegaManGame.PPM);
		head.set(vertice);
		
		fdef.shape = head;
		//IS BOX2D variable: sets how much Mega Man rebounds when you jump on Met's head:
		fdef.restitution = 0.5f;
		fdef.filter.categoryBits = MegaMan.ENEMY_HEAD_BIT;
		b2body.createFixture(fdef).setUserData(this);
		

		if (this.b2body.getLinearVelocity().y > 2) {
			this.b2body.setLinearVelocity(0, .01f);
		}
	}
	
	public void draw(Batch batch){
		if (!destroyed || stateTime<1){
			super.draw(batch);
		}
	}

	@Override
	public void onHit() {
		setToDestroy=true;
		
	}

	
	
	
}

