/**
 * @file        AbstractEnemy.java
 * @author      Aaron Mooney 20072163
 * @assignment  Warbirds
 * @brief       enemy superclass
 *
 * @notes       
 * 				
 */
package wit.cgd.warbirds.game.ai;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import wit.cgd.warbirds.game.Assets;
import wit.cgd.warbirds.game.objects.AbstractGameObject;
import wit.cgd.warbirds.game.objects.Bullet;
import wit.cgd.warbirds.game.objects.Level;
import wit.cgd.warbirds.game.util.Constants;

public class AbstractEnemy extends AbstractGameObject{
	
	public TextureRegion region;
	public int damage;
	public int score;
	protected Animation<TextureRegion> animation;
	protected Animation<TextureRegion> explosion;

	public AbstractEnemy(Level level) {
		super(level);
		timeShootDelay = 0;
		timeToDie = Constants.ENEMY_DIE_DELAY;
	}
	
	public void update(float deltaTime){
		super.update(deltaTime);
		position.x += velocity.x * deltaTime;
		position.y += velocity.y * deltaTime;
		timeShootDelay -= deltaTime;
		if (isInScreen() && !isDead()) state = State.ACTIVE;
		enemyShoot();
		if (isDead()){
			if (state == State.ACTIVE){
				state = State.DYING;
				animation = Assets.instance.player.animationExplosionBig;
				setAnimation(animation);
			}
		}
		
		
	}

	
	public void enemyShoot(){
		if (timeShootDelay>0) return;
		
		Bullet bullet = level.bulletPool2.obtain();
		bullet.reset();
		bullet.position.set(position);
		bullet.velocity.y = -Constants.BULLET_SPEED/2;
		bullet.velocity.rotate(rotation);
		bullet.rotation = rotation;
		level.enemyBullets.add(bullet);
		timeShootDelay = 1f;
		
	}
	
	public boolean withinRangeOfPlayer(){
		return(Vector2.dst(level.player.position.x, level.player.position.y, position.x, position.y) < 5);
	}
	


	@Override
	public void render(SpriteBatch batch) {
		region = animation.getKeyFrame(stateTime, true);
		
		batch.draw(region.getTexture(), position.x-origin.x, position.y-origin.y, origin.x, origin.y, 
		dimension.x, dimension.y, scale.x, scale.y, rotation, 
		region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight(), 
		false, false);
	}

}
