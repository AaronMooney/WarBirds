/**
 * @file        WarBirds.java
 * @author      Aaron Mooney 20072163
 * @assignment  Warbirds
 * @brief     	Main Class
 *
 * @notes       
 * 				
 */
package wit.cgd.warbirds;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;

import wit.cgd.warbirds.game.Assets;
import wit.cgd.warbirds.game.screens.GameScreen;
import wit.cgd.warbirds.game.util.GamePreferences;

public class WarBirds extends Game {

	@Override
	public void create() {

		// Set Libgdx log level
		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		// Load assets
		Assets.instance.init(new AssetManager());

		// Load preferences for audio settings 
		GamePreferences.instance.load();
		
		// TODO start playing music
		// AudioManager.instance.play(Assets.instance.music.music);

		// TODO Start game at menu screen
		setScreen(new GameScreen(this));

	}

}