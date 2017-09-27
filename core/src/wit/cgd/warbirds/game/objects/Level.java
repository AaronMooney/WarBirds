/**
 * @file        Level.java
 * @author      Aaron Mooney 20072163
 * @assignment  Warbirds
 * @brief       Level class where objects are spawned
 *
 * @notes       
 * 				
 */
package wit.cgd.warbirds.game.objects;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Pool;

import wit.cgd.warbirds.game.ai.AbstractEnemy;
import wit.cgd.warbirds.game.ai.GoldEnemy;
import wit.cgd.warbirds.game.ai.GreenEnemy;
import wit.cgd.warbirds.game.ai.WhiteEnemy;
import wit.cgd.warbirds.game.util.Constants;

public class Level extends AbstractGameObject {

	public static final String TAG = Level.class.getName();

	public Player player = null;
	public LevelDecoration levelDecoration;
	public float start;
	public float end;
	public final float ISLAND_DELAY = 1f;
	public float island_timer;
	public float spawn_timer;

	public final Array<Bullet> bullets = new Array<Bullet>();
	public final Array<Bullet> enemyBullets = new Array<Bullet>();
	public final Pool<AbstractEnemy> enemyPool = new Pool<AbstractEnemy>(){
		@Override
		protected AbstractEnemy newObject() {
			return new AbstractEnemy(level);
		}
	};

	public final Pool<Bullet> bulletPool = new Pool<Bullet>() {
		@Override
		protected Bullet newObject() {
			return new Bullet(level);
		}
	};
	
	public final Pool<Bullet> bulletPool2 = new Pool<Bullet>() {
		@Override
		protected Bullet newObject() {
			return new Bullet(level);
		}
	};
	

	public Random randomGenerator;

	public Array<AbstractEnemy> enemies;

	private float green_spawn_timer;
	private float white_spawn_timer;
	private float gold_spawn_timer;

	/**
	 * Simple class to store generic object in level.
	 */
	public static class LevelObject {
		String name;
		int x;
		int y;
		float rotation;
		int state;
	}

	/**
	 * Collection of all objects in level
	 */
	public static class LevelMap {
		ArrayList<LevelObject> islands;
		ArrayList<LevelObject> enemies;
		String name;
		float length;
		long seed;
	}

	public Level() {
		super(null);
		init();

	}

	public void init() {
		randomGenerator = new Random();
		

		// player
		player = new Player(this);
		player.position.set(0, 0);
		enemies = new Array<AbstractEnemy>();

		levelDecoration = new LevelDecoration(this);

		// read and parse level map (form a json file)
		String map ="";
		if (player.score < 1000){
		map = Gdx.files.internal("levels/level-01.json").readString();
		} else {
			map = Gdx.files.internal("levels/level-02.json").readString();
		}

		Json json = new Json();
		json.setElementType(LevelMap.class, "enemies", LevelObject.class);
		LevelMap data = new LevelMap();
		data = json.fromJson(LevelMap.class, map);
		randomGenerator.setSeed(data.seed);

		position.set(0, 0);
		velocity.y = Constants.SCROLL_SPEED;
		state = State.ACTIVE;

	}

	public void update(float deltaTime) {

		super.update(deltaTime);
		
		island_timer -= deltaTime;
		green_spawn_timer -= deltaTime;
		gold_spawn_timer -= deltaTime;
		white_spawn_timer -= deltaTime;
		
		if(island_timer < 0){
			if (ThreadLocalRandom.current().nextFloat()< 0.03) return;
			levelDecoration.add();
			island_timer = ISLAND_DELAY;
		}
		
		if (green_spawn_timer < 0){
			if (ThreadLocalRandom.current().nextFloat()< 0.03) return;
			addGreenEnemy();
			green_spawn_timer = 1.5f;
		}
		
		if (gold_spawn_timer < 0){
			if (ThreadLocalRandom.current().nextFloat()< 0.03) return;
			addGoldEnemy();
			gold_spawn_timer = 1f;
		}
		
		if (white_spawn_timer < 0){
			if (ThreadLocalRandom.current().nextFloat()< 0.03) return;
			addWhiteEnemy();
			white_spawn_timer = 2.5f;
		}

		// limits for rendering
		start = position.y - scale.y * Constants.VIEWPORT_HEIGHT;
		end = position.y + scale.y * Constants.VIEWPORT_HEIGHT;

		player.update(deltaTime);

		for (Bullet bullet : bullets)
			bullet.update(deltaTime);
		for (Bullet bullet : enemyBullets)
			bullet.update(deltaTime);
		for (AbstractEnemy enemy : enemies)
			enemy.update(deltaTime);

	}
	
public void addGreenEnemy(){
	AbstractEnemy enemy = new GreenEnemy(this);
	enemy.origin.x = enemy.dimension.x / 2;
	enemy.origin.y = enemy.dimension.y / 2;
	
	enemy.position.set((randomGenerator.nextInt(((int)Constants.VIEWPORT_WIDTH*2)-1) -Constants.VIEWPORT_WIDTH)-0.5f,
			end +3);
	enemies.add(enemy);
	}

public void addGoldEnemy(){
	AbstractEnemy enemy = new GoldEnemy(this);
	enemy.origin.x = enemy.dimension.x / 2;
	enemy.origin.y = enemy.dimension.y / 2;
	
	enemy.position.set((randomGenerator.nextInt(((int)Constants.VIEWPORT_WIDTH*2)-1) -Constants.VIEWPORT_WIDTH)-0.5f,
			end +3);
	enemies.add(enemy);
	}

public void addWhiteEnemy(){
	AbstractEnemy enemy = new WhiteEnemy(this);
	enemy.origin.x = enemy.dimension.x / 2;
	enemy.origin.y = enemy.dimension.y / 2;
	
	enemy.position.set((randomGenerator.nextInt(((int)Constants.VIEWPORT_WIDTH*2)-1) -Constants.VIEWPORT_WIDTH)-0.5f,
			end +3);
	enemies.add(enemy);
}

public void loadLevel(){
	init();
}

	public void render(SpriteBatch batch) {

		levelDecoration.render(batch);
		player.render(batch);
		for (Bullet bullet : bullets)
			bullet.render(batch);
		for (Bullet bullet : enemyBullets)
			bullet.render(batch);
		for (AbstractEnemy enemy : enemies)
			enemy.render(batch);

		System.out.println("Bullets " + bullets.size);
		System.out.println("Enemy Bullets " + enemyBullets.size);
	}

}
