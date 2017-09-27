/**
 * @file        WorldController.java
 * @author      Aaron Mooney 20072163
 * @assignment  Warbirds
 * @brief       Controls the game logic
 *
 * @notes       
 * 				
 */
package wit.cgd.warbirds.game;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Rectangle;

import wit.cgd.warbirds.game.ai.AbstractEnemy;
import wit.cgd.warbirds.game.ai.GoldEnemy;
import wit.cgd.warbirds.game.objects.AbstractGameObject;
import wit.cgd.warbirds.game.objects.Bullet;
import wit.cgd.warbirds.game.objects.Level;
import wit.cgd.warbirds.game.util.CameraHelper;
import wit.cgd.warbirds.game.util.Constants;

public class WorldController extends InputAdapter {


	private Game game;
	public CameraHelper cameraHelper;
	public Level level;

	private Rectangle r1 = new Rectangle();
	private Rectangle r2 = new Rectangle();

	private float cullTime = 3f;
	public WorldController(Game game) {
		this.game = game;
		init();
	}

	private void init() {
		Gdx.input.setInputProcessor(this);
		level = new Level();
		cameraHelper = new CameraHelper();
		cameraHelper.setTarget(level);
	}

	public void update(float deltaTime) {
		handleDebugInput(deltaTime);
		handleGameInput(deltaTime);
		cameraHelper.update(deltaTime);
		level.update(deltaTime);
		cullTime -= deltaTime;
		cullObjects();
		testCollisions();
		
		if(level.player.score > 1000){
			level.loadLevel();
		}
		if (level.player.health < 0) level.init();
	}

	private void testCollisions() {
		
		//bullet player
		r1.set(level.player.position.x, level.player.position.y, level.player.dimension.x, level.player.dimension.y);

		for (Bullet bullet : level.enemyBullets) {
			r2.set(bullet.position.x, bullet.position.y, bullet.dimension.x, bullet.dimension.y);
			if (!r1.overlaps(r2))
				continue;
			enemyBulletPlayerCollision(bullet);
		}

		//enemy player
		for (AbstractEnemy enemy : level.enemies) {
			r1.set(level.player.position.x, level.player.position.y, level.player.dimension.x,
					level.player.dimension.y);
			r2.set(enemy.position.x, enemy.position.y, enemy.dimension.x, enemy.dimension.y);
			if (r1.overlaps(r2)) {
				enemyPlayerCollision(enemy);
				continue;
			}

			//bullet enemy
			for (Bullet bullet : level.bullets) {
				r1.set(bullet.position.x, bullet.position.y, bullet.dimension.x, bullet.dimension.y);
				if (!r2.overlaps(r1))
					continue;
				bulletEnemyCollision(enemy, bullet);
			}
		}
	}

	/**
	 * Remove object because they are out of screen bounds or because they have
	 * died
	 */
	public void cullObjects() {

		// cull enemy bullets
		for (int k = level.enemyBullets.size; --k >= 0;) { // traverse array
															// backwards !!!
			Bullet it = level.enemyBullets.get(k);
			if (it.state == Bullet.State.DEAD) {
				level.enemyBullets.removeIndex(k);
				level.bulletPool2.free(it);
			} else if (it.state == Bullet.State.ACTIVE && !isInScreen(it)) {
				it.state = Bullet.State.DYING;
				it.timeToDie = Constants.BULLET_DIE_DELAY;
			}
		}

		// cull bullets
		if (level.bullets.size > 10) {
			for (int k = level.bullets.size; --k >= 0;) { // traverse array
															// backwards !!!
				Bullet it = level.bullets.get(k);
				if (it.state == Bullet.State.DEAD) {
					level.bullets.removeIndex(k);
					level.bulletPool.free(it);
				} else if (it.state == Bullet.State.ACTIVE && !isInScreen(it)) {
					it.state = Bullet.State.DYING;
					it.timeToDie = Constants.BULLET_DIE_DELAY;
				}
			}
		}
	if (cullTime < 0) {
			for (int k = level.enemies.size; --k > 0;) {
				AbstractEnemy enemy = level.enemies.get(k);
				if (enemy.state == AbstractGameObject.State.DEAD) {
					if (enemy.health <= 0) {
						level.player.score += enemy.score;
					}
					level.enemies.removeIndex(k);
					level.enemyPool.free(enemy);
				} else if (enemy.state == AbstractGameObject.State.ACTIVE && !isInScreen(enemy)) {
					enemy.state = AbstractGameObject.State.DYING;
					enemy.timeToDie = Constants.ENEMY_DIE_DELAY;
				}
			}
			cullTime = 3f;
		}

	}

	// Collision detection methods
	public void bulletEnemyCollision(AbstractEnemy enemy, Bullet bullet) {
		System.out.println("collision bullet enemy");
		enemy.health -= Constants.DAMAGE;
		bullet.state = AbstractGameObject.State.DEAD;
	}

	public void enemyBulletPlayerCollision(Bullet bullet) {
		System.out.println("collision bullet player");
		level.player.health -= Constants.DAMAGE;
		bullet.state = AbstractGameObject.State.DEAD;
	}

	public void enemyPlayerCollision(AbstractEnemy enemy) {
		System.out.println("collision enemy player");
		if (enemy instanceof GoldEnemy) level.player.health -= 4f;
		else level.player.health -= 0.01f;
	}

	public boolean isInScreen(AbstractGameObject obj) {
		return ((obj.position.x >= -Constants.VIEWPORT_WIDTH / 2 && obj.position.x <= Constants.VIEWPORT_WIDTH / 2)
				&& (obj.position.y >= level.start && obj.position.y <= level.end));
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.ESCAPE || keycode == Keys.BACK) {
			Gdx.app.exit();
		}
		return false;
	}

	private void handleGameInput(float deltaTime) {

		if (Gdx.input.isKeyPressed(Keys.A)) {
			level.player.velocity.x = -Constants.PLANE_H_SPEED;
		} else if (Gdx.input.isKeyPressed(Keys.D)) {
			level.player.velocity.x = Constants.PLANE_H_SPEED;
		} else {
			level.player.velocity.x = 0;
		}
		if (Gdx.input.isKeyPressed(Keys.W)) {
			level.player.velocity.y = Constants.PLANE_MAX_V_SPEED;
		} else if (Gdx.input.isKeyPressed(Keys.S)) {
			level.player.velocity.y = Constants.PLANE_MIN_V_SPEED;
		} else {
			level.player.velocity.y = Constants.SCROLL_SPEED;
		}
		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			level.player.shoot();
			level.player.shoot();
		}
	}

	private void handleDebugInput(float deltaTime) {
		if (Gdx.app.getType() != ApplicationType.Desktop)
			return;

		if (Gdx.input.isKeyPressed(Keys.ENTER)) {
			cameraHelper.setTarget(!cameraHelper.hasTarget() ? level : null);
		}

		if (!cameraHelper.hasTarget()) {
			// Camera Controls (move)
			float camMoveSpeed = 5 * deltaTime;
			float camMoveSpeedAccelerationFactor = 5;
			if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT))
				camMoveSpeed *= camMoveSpeedAccelerationFactor;
			if (Gdx.input.isKeyPressed(Keys.LEFT))
				moveCamera(-camMoveSpeed, 0);
			if (Gdx.input.isKeyPressed(Keys.RIGHT))
				moveCamera(camMoveSpeed, 0);
			if (Gdx.input.isKeyPressed(Keys.UP))
				moveCamera(0, camMoveSpeed);
			if (Gdx.input.isKeyPressed(Keys.DOWN))
				moveCamera(0, -camMoveSpeed);
			if (Gdx.input.isKeyPressed(Keys.BACKSPACE))
				cameraHelper.reset();
		}

		// Camera Controls (zoom)
		float camZoomSpeed = 1 * deltaTime;
		float camZoomSpeedAccelerationFactor = 5;
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT))
			camZoomSpeed *= camZoomSpeedAccelerationFactor;
		if (Gdx.input.isKeyPressed(Keys.COMMA))
			cameraHelper.addZoom(camZoomSpeed);
		if (Gdx.input.isKeyPressed(Keys.PERIOD))
			cameraHelper.addZoom(-camZoomSpeed);
		if (Gdx.input.isKeyPressed(Keys.SLASH))
			cameraHelper.setZoom(1);
	}

	private void moveCamera(float x, float y) {
		x += cameraHelper.getPosition().x;
		y += cameraHelper.getPosition().y;
		cameraHelper.setPosition(x, y);
	}

}
