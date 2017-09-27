/**
 * @file        GreenEnemy.java
 * @author      Aaron Mooney 20072163
 * @assignment  Warbirds
 * @brief       Green plane enemy class
 *
 * @notes       moves left and right only
 * 				
 */
package wit.cgd.warbirds.game.ai;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

import wit.cgd.warbirds.game.Assets;
import wit.cgd.warbirds.game.objects.Level;
import wit.cgd.warbirds.game.util.Constants;

public class GreenEnemy extends AbstractEnemy{

	public GreenEnemy(Level level) {
		super(level);
		init();
		
	}

	public void init() {
		// TODO Auto-generated method stub
		health = Constants.GREEN_HEALTH;
		dimension.set(1, 1);
		animation = Assets.instance.greenEnemy.animationNormal;
		setAnimation(animation);
		velocity.x = .5f;
		velocity.y = -.5f;
		// Center image on game object
		origin.set(dimension.x / 2, dimension.y / 2);
		state = State.ACTIVE;
		score = 5;
		
	}
	
	public void update(float deltaTime){
		super.update(deltaTime);
		//timeShootDelay -= deltaTime;
		if (position.x < -3){
			velocity.x = 1.0f;
			rotation = MathUtils.atan2(velocity.x, velocity.y) * MathUtils.radiansToDegrees -90f;
		}else if (position.x > 3){
			velocity.x = - velocity.x;
			rotation = MathUtils.atan2(velocity.x, velocity.y) * MathUtils.radiansToDegrees +90f;
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
