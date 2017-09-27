/**
 * @file        LevelDecoration.java
 * @author      Aaron Mooney 20072163
 * @assignment  Warbirds
 * @brief       Class to handle the islands and water
 *
 * @notes       
 * 				
 */
package wit.cgd.warbirds.game.objects;

import wit.cgd.warbirds.game.Assets;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import wit.cgd.warbirds.game.util.Constants;

public class LevelDecoration extends AbstractGameObject {

	private TextureRegion water;
	private TextureRegion islandBig;
	private TextureRegion islandSmall;
	private TextureRegion islandTiny;

	public Array<Island> islands;

	private class Island extends AbstractGameObject {
		private TextureRegion region;

		public Island(Level level, TextureRegion region) {
			super(level);
			this.region = region;
		}

		public void render(SpriteBatch batch) {
			batch.draw(region.getTexture(), position.x + origin.x, position.y + origin.y, origin.x, origin.y,
					dimension.x, dimension.y, scale.x, scale.y, rotation, region.getRegionX(), region.getRegionY(),
					region.getRegionWidth(), region.getRegionHeight(), false, false);
		}
	}

	public LevelDecoration(Level level) {
		super(level);
		init();
	}

	private void init() {
		dimension.set(1, 1);

		islandBig = Assets.instance.levelDecoration.islandBig;
		islandSmall = Assets.instance.levelDecoration.islandSmall;
		islandTiny = Assets.instance.levelDecoration.islandTiny;
		water = Assets.instance.levelDecoration.water;

		islands = new Array<Island>();
	}

	@Override
	public void render(SpriteBatch batch) {
		TextureRegion region = water;

		// water
		for (int k = (int) level.start; k < level.end; ++k)
			for (int c = -((int) Constants.VIEWPORT_WIDTH / 2); c < (int) (Constants.VIEWPORT_WIDTH / 2); ++c)
				batch.draw(region.getTexture(), origin.x + position.x + c, origin.y + position.y + k, origin.x,
						origin.y, 1.1f, 1.1f, 1, 1, rotation, region.getRegionX(), region.getRegionY(),
						region.getRegionWidth(), region.getRegionHeight(), false, false);

		for (Island island : islands) {
			region = island.region;
			if (island.position.y < level.start || island.position.y > level.end)
				continue;
			batch.draw(region.getTexture(), island.position.x - island.origin.x, island.position.y - island.origin.y,
					island.origin.x, island.origin.y, 1.1f, 1.1f, 1, 1, island.rotation, region.getRegionX(),
					region.getRegionY(), region.getRegionWidth(), region.getRegionHeight(), false, false);
		}


		// islands

	}

	public void add() {
			Island island = null;
			int name = level.randomGenerator.nextInt(5);
			if (name <= 1) {
				island = new Island(level, islandBig);
			} else if (name>1 && name < 4) {
				island = new Island(level, islandSmall);
			} else if (name == 4) {
				island = new Island(level, islandTiny);
			}
			island.origin.x = island.dimension.x / 2;
			island.origin.y = island.dimension.y / 2;
			
			island.position.set((level.randomGenerator.nextInt(((int)Constants.VIEWPORT_WIDTH*2) + 1) -Constants.VIEWPORT_WIDTH)/2,
					level.end + 3);
			island.rotation = rotation;
			islands.add(island);
		}
}
