package org.myname.flixeldemo.parsing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.flixel.FlxBlock;
import org.flixel.FlxCollideListener;
import org.flixel.FlxCore;
import org.flixel.FlxG;
import org.flixel.FlxState;
import org.myname.flixeldemo.Enemy;
import org.myname.flixeldemo.Player;
import org.myname.flixeldemo.R;

import flash.geom.Point;

public class Level extends FlxState
{
	protected int defaultTexture = R.drawable.tech_tiles;
	protected int background;
	protected String name;
	protected int width, height;
	
	
	public static int nextLevel = R.raw.lvl_test;
	public static int currentLevel = R.raw.lvl_test;
	public static String levelEntryLabel = "start";

	
	
	public static HashMap<Integer, Level> levelSaves= new HashMap<Integer, Level>();
	
	

	protected Player player;
//	protected final ArrayList<Enemy> enemies = new ArrayList<Enemy>();
//	protected final HashMap<String, Point> labels = new HashMap<String, Point>();
//	//-- TODO needs a container Object for inner variables.
//	protected final ArrayList<Object> jump = new ArrayList<Object>();
//	protected final ArrayList<FlxBlock> movingBlocks = new ArrayList<FlxBlock>();
//	protected final ArrayList<FlxBlock> stationaryBlocks = new ArrayList<FlxBlock>();
//	protected final ArrayList<FlxBlock> hurtBlocks = new ArrayList<FlxBlock>();
//	protected final ArrayList<FlxBlock> deathBlocks = new ArrayList<FlxBlock>();

	protected ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	protected HashMap<String, Point> labels = new HashMap<String, Point>();
	//-- TODO needs a container Object for inner variables.
	protected ArrayList<Object> jump = new ArrayList<Object>();
	protected ArrayList<FlxBlock> movingBlocks = new ArrayList<FlxBlock>();
	protected ArrayList<FlxBlock> stationaryBlocks = new ArrayList<FlxBlock>();
	protected ArrayList<FlxBlock> hurtBlocks = new ArrayList<FlxBlock>();
	protected ArrayList<FlxBlock> deathBlocks = new ArrayList<FlxBlock>();

	public Level()
	{
		if (!Level.levelSaves.containsKey(nextLevel)){
			new LevelParser(this);
	
			for(Iterator<FlxBlock> it = movingBlocks.iterator(); it.hasNext();)
				super.add(it.next());
	
			for(Iterator<FlxBlock> it = stationaryBlocks.iterator(); it.hasNext();)
				super.add(it.next());
	
			for(Iterator<FlxBlock> it = hurtBlocks.iterator(); it.hasNext();)
				super.add(it.next());
	
			for(Iterator<FlxBlock> it = deathBlocks.iterator(); it.hasNext();)
				super.add(it.next());
	
			super.add(player);
	
			for(Iterator<Enemy> it = enemies.iterator(); it.hasNext();)
				super.add(it.next());
	
			FlxG.follow(player, 2.5f);
			FlxG.followAdjust(0.5f, 0.0f);
			FlxG.followBounds(0, 0, this.width + 100, this.height + 100);
		}else{
			
			new LevelParser(this);
			for(Iterator<FlxBlock> it = movingBlocks.iterator(); it.hasNext();)
				super.add(it.next());
	
			for(Iterator<FlxBlock> it = stationaryBlocks.iterator(); it.hasNext();)
				super.add(it.next());
	
			for(Iterator<FlxBlock> it = hurtBlocks.iterator(); it.hasNext();)
				super.add(it.next());
	
			for(Iterator<FlxBlock> it = deathBlocks.iterator(); it.hasNext();)
				super.add(it.next());
			
	
			super.add(player);
	
			for(Iterator<Enemy> it = levelSaves.get(nextLevel).enemies.iterator(); it.hasNext();)
				super.add(it.next());
	
			FlxG.follow(player, 2.5f);
			FlxG.followAdjust(0.5f, 0.0f);
			FlxG.followBounds(0, 0, this.width + 100, this.height + 100);
			
			player.x = 400;
			player.y = 100;
			
		
		}
		currentLevel = LevelParser.KEY_RESOURCE_ADDR.get(this.name);
		levelSaves.put(currentLevel, this);
	}
	
	
	public static void switchLevel(String nextName, String nextLabel)
	{
		nextLevel = LevelParser.KEY_RESOURCE_ADDR.get(nextName);
		if (FlxG.state instanceof Level)
			levelSaves.put(LevelParser.KEY_RESOURCE_ADDR.get(((Level)FlxG.state).name), ((Level)FlxG.state));
		FlxG.switchState(Level.class);
		
	}
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
				//player.kill();
				Level.switchLevel("lvl_test3","");
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
				//player.kill();
				//object1.dead = true;
				//object1.visible = false;
				((Enemy)object1).exists = false;
				Level.switchLevel("lvl_test","");
			}

			
		}
		
		
		);
	}
}