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
	protected ArrayList<FlxBlock> deathBlocks = new ArrayList<FlxBlock>();

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
			public void Collide(FlxCore object1, FlxCore object2)
			{
				//-- TODO Enemy specific code here... May want to kill the enemy or hurt the player
				//   based on a series of instanceof statements. 
				player.kill();
			}
		}		
		);
	}
}