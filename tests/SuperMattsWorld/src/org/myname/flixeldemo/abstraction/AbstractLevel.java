package org.myname.flixeldemo.abstraction;

import java.util.ArrayList;
import java.util.HashMap;

import org.flixel.FlxBlock;
import org.flixel.FlxCollideListener;
import org.flixel.FlxCore;
import org.flixel.FlxG;
import org.flixel.FlxSprite;
import org.flixel.FlxState;

import flash.geom.Point;

public class AbstractLevel extends FlxState
{
	protected String name;
	protected FlxSprite player;
	protected ArrayList<FlxSprite> enemies = new ArrayList<FlxSprite>();
	protected HashMap<String, Point> labels = new HashMap<String, Point>();
	//-- TODO needs a container Object for inner variables.
	protected ArrayList<Object> jump = new ArrayList<Object>();
	protected ArrayList<FlxBlock> movingBlocks = new ArrayList<FlxBlock>();
	protected ArrayList<FlxBlock> stationaryBlocks = new ArrayList<FlxBlock>();
	protected ArrayList<FlxBlock> hurtBlocks = new ArrayList<FlxBlock>();
	protected ArrayList<FlxCore> deathBlocks = new ArrayList<FlxCore>();

	@Override
	public void update()
	{
		super.update();

		/*
		 * PLAYER COLLISIONS
		 */
		FlxG.collideArrayList(stationaryBlocks, player);
		FlxG.collideArrayList(movingBlocks, player);
		FlxG.collideArrayList(hurtBlocks, player);
		//FlxG.collideArrayList(deathBlocks, player);
		FlxG.overlapArrayList(movingBlocks, player, new FlxCollideListener()
		{
			public void Collide(FlxCore object1, FlxCore object2)
			{
				//-- squashed by a moving block!
				player.kill();
			}
		}
		);
		FlxG.overlapArrayList(stationaryBlocks, player, new FlxCollideListener()
		{
			public void Collide(FlxCore object1, FlxCore object2)
			{
				//-- squashed by a stationary block???
				//   maybe this also needs to be here for the moving
				//   blocks! Ask Grant!
				player.kill();
			}
		}
		);
		FlxG.overlapArrayList(hurtBlocks, player, new FlxCollideListener()
		{
			public void Collide(FlxCore object1, FlxCore object2)
			{
				player.hurt(1F);
			}
		}
		);
		FlxG.overlapArrayList(deathBlocks, player, new FlxCollideListener()
		{
			public void Collide(FlxCore object1, FlxCore object2)
			{
				player.kill();
			}
		}
		);

		/*
		 * ENEMY COLLISIONS
		 */
		FlxG.collideArrayLists(enemies, stationaryBlocks);
		FlxG.collideArrayLists(enemies, movingBlocks);
		FlxG.overlapArrayList(enemies, player, new FlxCollideListener()
		{
			//Need a collide listener otherwise overlapArrayList will kill the enemy and the player
			public void Collide(FlxCore obj1, FlxCore obj2){}
		});
	}
}