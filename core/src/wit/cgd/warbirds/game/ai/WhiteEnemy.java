/**
 * @file        WhiteEnemy.java
 * @author      Aaron Mooney 20072163
 * @assignment  Warbirds
 * @brief      	White plane enemy class
 *
 * @notes       moves in a circle
 * 				
 */
package wit.cgd.warbirds.game.ai;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import wit.cgd.warbirds.game.Assets;
import wit.cgd.warbirds.game.objects.Level;
import wit.cgd.warbirds.game.util.Constants;

public class WhiteEnemy extends AbstractEnemy {
	
	private float angle;
	private float radius;

	public WhiteEnemy(Level level) {
		super(level);
		init();
		
	}
	
	public void init(){
		health = Constants.WHITE_HEALTH;
		dimension.set(1,1);
		origin.set(dimension.x/2,dimension.y/2);
		animation = Assets.instance.whiteEnemy.animationNormal;
		setAnimation(animation);
		angle = 0.03f;
		radius = 2;
		velocity.x = 2.5f;
		velocity.y = -2.5f;
		score = 100;
	}
	
	public void update(float deltaTime){
		super.update(deltaTime);
		
		angle += 0.03f;
		velocity.x = (float) (origin.x + (radius*Math.cos(angle)));
		velocity.y = (float) (origin.y + (radius*Math.sin(angle)));
	}

	
public void render (SpriteBatch batch) {
		
		region = animation.getKeyFrame(stateTime, true);

		batch.draw(region.getTexture(), position.x-origin.x, position.y-origin.y, origin.x, origin.y, 
			dimension.x, dimension.y, scale.x, scale.y, rotation, 
			region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight(), 
			false, false);
	}
}
