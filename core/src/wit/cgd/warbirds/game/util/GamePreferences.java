/**
 * @file        GamePreferences.java
 * @author      Aaron Mooney 20072163
 * @assignment  Warbirds
 * @brief       stores game preferences
 *
 * @notes       
 * 				
 */
package wit.cgd.warbirds.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.MathUtils;

import wit.cgd.warbirds.game.util.Constants;
import wit.cgd.warbirds.game.util.GamePreferences;

public class GamePreferences {

	public boolean 	 music;
	public float 	 musicVolume;
	public boolean 	 sound;
	public float 	 soundVolume;

    public static final String          TAG         = GamePreferences.class.getName();

    public static final GamePreferences instance    = new GamePreferences();
    private Preferences                 prefs;
	
    

    private GamePreferences() {
        prefs = Gdx.app.getPreferences(Constants.PREFERENCES);
    }

    public void load() {
    	sound = prefs.getBoolean("sound");
    	soundVolume = prefs.getFloat("soundVolume",
    			MathUtils.clamp(soundVolume, 0, 1));
    	music = prefs.getBoolean("music");
    	musicVolume = prefs.getFloat("musicVolume",
    			MathUtils.clamp(musicVolume, 0, 1));
    }

    public void save() {
        prefs.flush();
    }

}
