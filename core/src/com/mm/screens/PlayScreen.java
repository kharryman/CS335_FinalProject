package com.mm.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mm.main.MegaManGame;
import com.mm.scenes.Hud;
import com.mm.sprites.MegaMan;

/**
 * 
 * Controls:
 *   - F1 to F4 will load maps. 
 *   - WASD controls for player movement.
 *   - SPACE to jump.
 *
 */

public class PlayScreen implements Screen {
	private MegaManGame game;
	private TextureAtlas atlas;
	private OrthographicCamera gamecam;
	private Viewport gamePort;
	private Hud hud;
	private float ppm = MegaManGame.PPM;
	private MegaMan player;
	
	//tiled map variables
	private TmxMapLoader mapLoader;
	private TiledMap map;
	private MapProperties mapProperties;
	private OrthogonalTiledMapRenderer renderer;
	private final int  SCALE_ADJUST = 0;
	
	//Box2d variables
	private World world;
	private Box2DDebugRenderer b2dr; //draws hit-boxes
	

	
	
	public PlayScreen(MegaManGame game, String mapName, int mapType){
		this.game = game;
		gamecam = new OrthographicCamera();
		gamePort = new FitViewport(MegaManGame.V_WIDTH / ppm, MegaManGame.V_HEIGHT / ppm, gamecam);
		hud = new Hud(game.batch);
		
		atlas = new TextureAtlas("megaman.pack");
		
		
		mapLoader = new TmxMapLoader();
		TmxMapLoader.Parameters params = new TmxMapLoader.Parameters();
		params.textureMagFilter = TextureFilter.Nearest;
		params.textureMinFilter = TextureFilter.Nearest;

		map = mapLoader.load(mapName, params); 
		mapProperties = map.getProperties();
		renderer = new OrthogonalTiledMapRenderer(map, 1/(ppm+SCALE_ADJUST));
		gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0); //set starting position for camera
		
		world = new World(new Vector2(0, -10), true);
		b2dr = new Box2DDebugRenderer();
		
		
		BodyDef bdef = new BodyDef();
		PolygonShape shape = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
		Body body;
		
		//create and add ground objects
		for(MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set((rect.getX() + rect.getWidth() / 2)/(ppm + SCALE_ADJUST), (rect.getY() + rect.getHeight() / 2)/(ppm + SCALE_ADJUST));
			body = world.createBody(bdef);
			
			shape.setAsBox((rect.getWidth() / 2)/(ppm + SCALE_ADJUST), (rect.getHeight() / 2)/(ppm + SCALE_ADJUST));
			fdef.shape = shape;
			body.createFixture(fdef);
		}
		

	}
	
	
	
	public TextureAtlas getAtlas(){
		return atlas;
	}
	
	
	
	
	public void initPlayer(int startX, int startY){
		player = new MegaMan(this, startX, startY);
	}
	
	
	
	//update game world
	public void update(float dt){
		handleInput(dt);
		
		world.step(1/60f, 6, 2);
		
		player.update(dt);
		
		int mapLeft = 0;
		int mapRight = 0 + mapProperties.get("width", Integer.class);
		int mapBottom = 0;
		int mapTop = 0 + mapProperties.get("height", Integer.class);
		float cameraHalfWidth = gamecam.viewportWidth * .5f;
		float cameraHalfHeight = gamecam.viewportHeight * .5f;
		
		// attacth camera to player
		gamecam.position.y = player.b2body.getPosition().y;
		gamecam.position.x = player.b2body.getPosition().x;


		float cameraLeft = gamecam.position.x - cameraHalfWidth;
		float cameraRight = gamecam.position.x + cameraHalfWidth;
		float cameraBottom = gamecam.position.y - cameraHalfHeight;
		float cameraTop = gamecam.position.y + cameraHalfHeight;
		
		
		// Horizontal axis
		if(mapProperties.get("width", Integer.class) < gamecam.viewportWidth){
		    gamecam.position.x = mapRight / 2;
		}
		else if(cameraLeft <= mapLeft){
			gamecam.position.x = mapLeft + cameraHalfWidth;
		}
		else if(cameraRight >= mapRight){
			gamecam.position.x = mapRight - cameraHalfWidth;
		}

		// Vertical axis
		if(mapProperties.get("height", Integer.class) < gamecam.viewportHeight){
			gamecam.position.y = mapTop / 2;
		}
		else if(cameraBottom <= mapBottom){
			gamecam.position.y = mapBottom + cameraHalfHeight;
		}
		else if(cameraTop >= mapTop){
			gamecam.position.y = mapTop - cameraHalfHeight;
		}
		
		
		
		//debug stuff
		System.out.println(String.format("Player: x=%f     y=%f     gamecam width = %f     gamecam height = %f" +
				"     mapwidth = %d     camright = %f     mapRight = %d", player.b2body.getPosition().x, player.b2body.getPosition().y,
				gamecam.viewportWidth, gamecam.viewportHeight, mapProperties.get("width", Integer.class), cameraRight, mapRight
				));
		
		
		
		//update camera when it moves
		gamecam.update();
		renderer.setView(gamecam);
	}
	
	
	
	public void handleInput(float dt){
		//jump
		if(Gdx.input.isKeyJustPressed(Keys.SPACE)){
			//prevent multi-jumping
			if(player.b2body.getLinearVelocity().y == 0){ 
				player.b2body.setLinearVelocity(new Vector2(player.b2body.getLinearVelocity().x, 0));
				player.b2body.applyLinearImpulse(new Vector2(0, 3.0f), player.b2body.getWorldCenter(), true);
			}
		}
		
		//move player left
		if(Gdx.input.isKeyPressed(Keys.A) && player.b2body.getLinearVelocity().x >= -1){
			player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
		}
		
		//move player right
		if(Gdx.input.isKeyPressed(Keys.D) && player.b2body.getLinearVelocity().x <= 1){
			player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
		}
		
		//exit game
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)){
			Gdx.app.exit();
		}
		
		
		//screen change controller (debugging)
		if(Gdx.input.isKeyJustPressed(Keys.F1)){
			game.changeScreen(0);
		}
		if(Gdx.input.isKeyJustPressed(Keys.F2)){
			game.changeScreen(1);
		}
		if(Gdx.input.isKeyJustPressed(Keys.F3)){
			game.changeScreen(2);
		}
		if(Gdx.input.isKeyJustPressed(Keys.F4)){
			game.changeScreen(3);
		}
		
		
	}
	
	public World getWorld(){
		return this.world;
	}



	
	@Override
	public void render(float delta) {
		update(delta);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//render box2d objects
		renderer.render();
		
		//hit boxes
		b2dr.render(world, gamecam.combined); 
		
		//render megaman with texture
		game.batch.setProjectionMatrix(gamecam.combined);
		game.batch.begin();
		player.draw(game.batch);
		game.batch.end();
		
		game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
		hud.stage.draw();
		
	}
	
	@Override
	public void resize(int width, int height) {
		gamePort.update(width, height);
		
	}
	
	@Override
	public void show() {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose() {}

}
