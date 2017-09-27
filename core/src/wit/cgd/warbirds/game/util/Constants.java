/**
 * @file        Constants.java
 * @author      Aaron Mooney 20072163
 * @assignment  Warbirds
 * @brief       stores all constant data
 *
 * @notes       
 * 				
 */
package wit.cgd.warbirds.game.util;

public class Constants {

	// Game world dimensions
	public static final float	VIEWPORT_WIDTH		= 8.0f;
	public static final float	VIEWPORT_HEIGHT		= 8.0f;

	// GUI dimensions
	public static final float	VIEWPORT_GUI_WIDTH	= 480.0f;
	public static final float	VIEWPORT_GUI_HEIGHT	= 800.0f;

	// atlas for all game sprites
	public static final String	TEXTURE_ATLAS_GAME	= "images/game.atlas";

	// Persistent storage files
	public static final String	PREFERENCES			= "game.prefs";
	
	 // location of game specific skin and atlas
    public static final String  SKIN_UI                 = "images/ui.json";
    public static final String  TEXTURE_ATLAS_UI        = "images/ui.atlas";
    
 // location of libgdx default skin and atlas
    public static final String  SKIN_LIBGDX_UI          = "images/uiskin.json";
    public static final String  TEXTURE_ATLAS_LIBGDX_UI = "images/uiskin.atlas";

	// Speed Constants (most relative to SCROLL_SPEED)
	public static final float	SCROLL_SPEED		= 1.0f;

	public static final float	PLANE_H_SPEED		= 5.0f;
	public static final float	PLANE_MIN_V_SPEED	= -3 * SCROLL_SPEED;
	public static final float	PLANE_MAX_V_SPEED	= 4 * SCROLL_SPEED;
	public static final float	BULLET_SPEED		= 2.0f * PLANE_MAX_V_SPEED;
	
	//delay
	public static final float	PLAYER_SHOOT_DELAY	= 0.2f;

	public static final float	BULLET_DIE_DELAY	= 1.2f;
	public static final float	ENEMY_DIE_DELAY		= 0.2f;
	
	//health
	public static final float	PLAYER_HEALTH		= 100f;
	public static final float	GREEN_HEALTH		= 20f;
	public static final float	GOLD_HEALTH			= 30f;
	public static final float	WHITE_HEALTH		= 50f;
	
	//damage
	public static final float	DAMAGE		= 2f;
	public static final int BUTTON_PAD = 5;
	
	
	
}
