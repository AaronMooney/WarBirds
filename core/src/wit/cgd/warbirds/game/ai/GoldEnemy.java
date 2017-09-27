/**
 * @file        GoldEnemy.java
 * @author      Aaron Mooney 20072163
 * @assignment  Warbirds
 * @brief       gold plane enemy class
 *
 * @notes       moves like the green plane but dives into the player when in range
 * 				
 */
package wit.cgd.warbirds.game.ai;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

import wit.cgd.warbirds.game.Assets;
import wit.cgd.warbirds.game.objects.Level;
import wit.cgd.warbirds.game.util.Constants;

public class GoldEnemy extends AbstractEnemy {
	private double distanceX;
	private double distanceY;
	private double magnitude;

	public GoldEnemy(Level level) {
		super(level);
		init();
	}
	
	public void init(){
		health = Constants.GOLD_HEALTH;
		dimension.set(1,1);
		origin.set(dimension.x/2,dimension.y/2);
		animation = Assets.instance.goldEnemy.animationNormal;
		setAnimation(animation);
		velocity.x = .5f;
		velocity.y = -0.5f;
		score = 50;
		
	}
	
	public void update(float deltaTime){
		super.update(deltaTime);
		
		if(!withinRangeOfPlayer()){
			if (position.x < -3){
				velocity.x = 1.0f;
				rotation = MathUtils.atan2(velocity.x, velocity.y) * MathUtils.radiansToDegrees -90f;
			}else if (position.x > 3){
				velocity.x = - velocity.x;
				rotation = MathUtils.atan2(velocity.x, velocity.y) * MathUtils.radiansToDegrees +90f;
			}
		}
		else{
			distanceX = level.player.position.x - position.x;
			distanceY = level.player.position.y - position.y;
			magnitude = Math.sqrt(distanceX*distanceY + distanceY * distanceX);
			distanceX = distanceX/magnitude;
			distanceY = distanceY/magnitude;
			
			velocity.x += distanceX * 0.1f;
			velocity.y += distanceY * 0.1f;
		}
	}
	
public void render (SpriteBatch batch) {
		
		region = animation.getKeyFrame(stateTime, true);

		batch.draw(region.getTexture(), position.x-origin.x, position.y-origin.y, origin.x, origin.y, 
			dimension.x, dimension.y, scale.x, scale.y, rotation, 
			region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight(), 
			false, false);
	}

}
