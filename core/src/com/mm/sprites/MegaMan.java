package com.mm.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mm.main.MegaManGame;
import com.mm.screens.PlayScreen;

public class MegaMan extends Sprite{
	public enum State{FALLING, JUMPING, STANDING, RUNNING};
	public State currentState;
	public State previousState;
	public World world; //world mega man will live in
	public Body b2body;
	private float ppm = MegaManGame.PPM;
	private TextureRegion megamanStand;
	private Animation megamanRun;
	private Animation megamanJump;
	private float stateTimer;
	private boolean runningRight;
	
	public Animation anim;
	public float stateTime;
	
	//Box2D Collision Bits:
	public static final short GROUND_BIT = 1;
	public static final short MEGAMAN_BIT = 2;
	public static final short DESTROYED_BIT = 4;
	public static final short OBJECT_BIT = 8;
	public static final short ENEMY_BIT = 16;
	public static final short ENEMY_HEAD_BIT = 32;

	
	
	public MegaMan(PlayScreen screen, int startX, int startY){
		super(screen.getAtlas().findRegion("megamanspritesheet"));
		this.world = screen.getWorld();
		currentState = State.STANDING;
		previousState = State.STANDING;
		stateTimer = 0;
		runningRight = true;
		
		//running animation
		Array<TextureRegion> frames = new Array<TextureRegion>();
		for(int i = 1; i < 4; i++){
			frames.add(new TextureRegion(getTexture(), i * 30, 30, 23, 24 ));
		}
		megamanRun = new Animation(0.12f, frames);
		frames.clear();
		
	
		//standing animation
		megamanStand = new TextureRegion(getTexture(), 0, 0, 21, 24);
		
		setBounds(0, 4, 21/ppm, 24/ppm); //scale character
		defineMegaMan(startX, startY);
		
		
		
	}
	
	
	/**=======================================================
	 * @name: getFrame
	 * @param dt : Delta Time
	 * @return: Frame to be rendered to PlayScreen
	 * @desc: Gets the next frame of animation.
	 * =======================================================
	 */
	public TextureRegion getFrame(float dt) {
		currentState = getState();

		TextureRegion region;
		switch (currentState) {
		case RUNNING:
			region = megamanRun.getKeyFrame(stateTimer, true);
			break;
		case STANDING:
			default:
				region = megamanStand;
				break;
		}
		
		//mirror sprite image if player is facing right or left
		if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
			region.flip(true, false);
			runningRight = false;
		}
		else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
			region.flip(true, false);
			runningRight = true;
		}
		
		stateTimer = currentState == previousState ?  stateTimer + dt : 0;
		previousState = currentState;
		return region;
		
	}
	
	
	
	/**=======================================================
	 * @name: getState
	 * @param none
	 * @return none
	 * @desc: Determines which state of movement the player is
	 * in based on their current velocity in either the X or
	 * Y direction and returns a State object.
	 * =======================================================
	 */
	public State getState(){
		if(b2body.getLinearVelocity().x != 0){
			return State.RUNNING;
		}
		else{
			return State.STANDING;
		}
	}
	
	
	
	
	/**=======================================================
	 * Initialize the player sprite.
	 * @param startX: Player starting X-position
	 * @param startY: player starting Y-position
	 * =======================================================
	 */
	public void defineMegaMan(int startX, int startY){
		BodyDef bdef = new BodyDef();
		bdef.position.set((startX)/ppm, (startY)/ppm); //start position on map
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = world.createBody(bdef);
		
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape(); //mega mans hit box shape
		shape.setRadius(7/ppm);
		fdef.filter.maskBits = MegaMan.GROUND_BIT |
				MegaMan.ENEMY_BIT |
				MegaMan.OBJECT_BIT |
				MegaMan.ENEMY_HEAD_BIT;
		
		fdef.shape = shape;
		b2body.createFixture(fdef);
		
		if(this.b2body.getLinearVelocity().y > 2){
			this.b2body.setLinearVelocity(0, .01f);
		}
		
		fdef.isSensor = true;
		
		b2body.createFixture(fdef).setUserData("mm");
		
		
	}
	
	public void update(float dt){
		setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
		setRegion(getFrame(dt));
	}
	

}
