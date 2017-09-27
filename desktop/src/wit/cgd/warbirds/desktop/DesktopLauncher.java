/**
 * @file        DesktopLauncher.java
 * @author      Aaron Mooney 20072163
 * @assignment  Warbirds
 * @brief       Allows the game to be played on a desktop
 *
 * @notes       
 * 				
 */
package wit.cgd.warbirds.desktop;

import wit.cgd.warbirds.WarBirds;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;

public class DesktopLauncher {

	private static boolean	rebuildAtlas		= true;
	private static boolean	drawDebugOutline	= false;

	public static void main (String[] arg) {
		
		if (rebuildAtlas) {
			Settings settings = new Settings();
			settings.maxWidth = 1024;
			settings.maxHeight = 1024;
			settings.debug = drawDebugOutline;
			TexturePacker.process(settings, "assets-raw/images", "../android/assets/images", "game.atlas");
		}
		
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "War Birds";
		config.width = 480;
		config.height = 800;
		new LwjglApplication(new WarBirds(), config);
	}
}