//notes: this class uses a new camera just for the Hud class that stays locked in place instead of following an
//object around.

package com.mm.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mm.main.MegaManGame;

public class Hud {
	public Stage stage;
	private Viewport viewport;
	
	private Integer health;
	private float timeCount;
	private Integer score;
	
	Label healthLabel;
	Label scoreLabel;
	
	public Hud(SpriteBatch sb){
		health = 100;
		timeCount = 0;
		score = 0;
		
		viewport = new FitViewport(MegaManGame.V_WIDTH, MegaManGame.V_HEIGHT, new OrthographicCamera());
		stage = new Stage(viewport, sb);
		
		Table table = new Table(); //table is used for laying out items on the hud
		table.top();
		table.setFillParent(true); //table is now the size of the stage
		
		healthLabel = new Label(String.format("%s %03d%s","Health ", health, "%"), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		
		table.add(healthLabel).expandX().padTop(5).padRight(200);
		table.add(scoreLabel).expandX().padTop(5);
		
		stage.addActor(table);
	}
}
