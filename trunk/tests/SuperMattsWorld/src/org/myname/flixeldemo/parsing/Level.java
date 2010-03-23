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
import org.myname.flixeldemo.Hud;
import org.myname.flixeldemo.Player;
import org.myname.flixeldemo.R;

import flash.geom.Point;

public class Level extends FlxState
{
	public static final String DEFAULT_START_LABEL = "start";

	public static final HashMap<Integer, Level> levelSaves = new HashMap<Integer, Level>();

	/** The next level to be loaded upon SwitchState to this class.*/
	protected static int nextLevel = R.raw.lvl_test;
	/** The current level to already loaded in this class. */
	protected static int currentLevel = R.raw.lvl_jump_test;
	/** The label to start the player at in this class. start if not set. */
	protected static String startLabel;
	/** Only set by LevelParser and incremented with power-ups.*/
	public static float timeRemaining = 60;

	protected int defaultTexture = R.drawable.tech_tiles;
	protected int background;
	protected String name;
	protected int width, height;
	protected Player player;
	protected int music = 0;
	protected ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	protected HashMap<String, Point> labels = new HashMap<String, Point>();
	//-- TODO needs a container Object for inner variables.
	protected ArrayList<FlxBlock> jump = new ArrayList<FlxBlock>();
	protected ArrayList<FlxBlock> movingBlocks = new ArrayList<FlxBlock>();
	protected ArrayList<FlxBlock> stationaryBlocks = new ArrayList<FlxBlock>();
	protected ArrayList<FlxBlock> hurtBlocks = new ArrayList<FlxBlock>();
	protected ArrayList<FlxBlock> deathBlocks = new ArrayList<FlxBlock>();
	protected ArrayList<FlxBlock> powerUps = new ArrayList<FlxBlock>();
	protected Hud headsUp = new Hud();
	
	public Level()
	{
		if (Level.levelSaves.containsKey(nextLevel))
			//-- Copy the contents of the saved level DOOD!
			this.copyContents(Level.levelSaves.get(nextLevel));
		else
			new LevelParser(this);

		
		this.addComponentsToLayer();
		this.setCameraFollow();

		/*
		 * SET START POINT - Check to see if we provided a new start point for the player. This is
		 * set in the Level.switchLevel function. If the specified point is not
		 * found, then start them at the beginning of the level.s
		 */
		Point startPoint = labels.containsKey(startLabel) ? labels.get(startLabel) : labels.get(DEFAULT_START_LABEL);
		this.player.x = startPoint.x;
		this.player.y = startPoint.y;

		
		/*
		 * TODO play music
		 */
		if (music != 0 && false)
			FlxG.playMusic(music);

	
		//-- Save?
		currentLevel = LevelParser.KEY_RESOURCE_ADDR.get(this.name);
		levelSaves.put(currentLevel, this);
	}

	/**
	 * 
	 * Sets the nextLevel and startLabel static members.
	 * 
	 * 
	 * PRE: nextLevelName is the name of the next level that is being switched to.
	 * 				This is required, but can be the same name as the current level.
	 * 		nextLevelLabel is the labeled point to with the Player will jump to in the next level.
	 * 				string can be empty, it will default to the start label of the level
	 * 				when level is opened.
	 * 
	 * POST: nextLevel addresses by nextLavelName is loaded as current FlxG.state,
	 * 			 and the player is located at the nextLevelLabel. If the current "level" is a Level,
	 * 				the current state is stored. 
	 * 
	 * @param nextLevelName
	 * @param nextLevelLabel
	 */
	public static void switchLevel(String nextLevelName, String nextLevelLabel)
	{
		nextLevel = LevelParser.KEY_RESOURCE_ADDR.get(nextLevelName);

		// only save Levels, not others such as MenuState
		if (FlxG.state instanceof Level)
			levelSaves.put(LevelParser.KEY_RESOURCE_ADDR.get(((Level)FlxG.state).name), ((Level)FlxG.state));

		//-- need to know where to start when we swap the levels!
		//   if this is not found we will default to "start"
		Level.startLabel = nextLevelLabel;

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
				player.kill();
				//Level.switchLevel("lvl_test3","");
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

		/*
		 * JUMP BLOCKS - have to trigger the overridden overlaps function.
		 */
		FlxG.overlapArrayList(jump, player);

		/*
		 * POWER UPS - just overlap these... on overlap they
		 * will set exists to false and viola! Gone!
		 */
		FlxG.overlapArrayList(powerUps, player);

		/*
		 * LEVEL TIMER - TODO use methods.
		 */
		if(timeRemaining > 0)
		{
			timeRemaining -= FlxG.elapsed;
	
			// Update HUD
			headsUp.updateHud("Time: " + (int)timeRemaining + "     Level: " 
					+ this.name + "     x: " + (int)player.x + " y: " + (int)player.y);
			if(timeRemaining <= 0)
			{
				player.kill();
			}
		}
	}

	/**
	 * Adds Level compenents to the layer in the superclass FlxState.
	 */
	private void addComponentsToLayer()
	{
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

		for(Iterator<FlxBlock> it = jump.iterator(); it.hasNext();)
			super.add(it.next());

		for(Iterator<FlxBlock> it = powerUps.iterator(); it.hasNext();)
			super.add(it.next());

		super.add(headsUp.getBackground());
		super.add(headsUp.getHudText());
	}

	/**
	 * PRE: Player is in the FlxState layer, height/width set.
	 * POST: Camera focuses and follows based on level width + height
	 */
	private void setCameraFollow()
	{
		FlxG.follow(this.player, 2.5f);
		FlxG.followAdjust(0.5f, 0.0f);
		FlxG.followBounds(0, 0, this.width + 100, this.height + 100);
	}

	/**
	 * Copies all mutable objects from the provided Level to this instance
	 * @param level
	 */
	private final void copyContents(Level level)
	{
		this.player = level.player;
		this.enemies = level.enemies;
		this.labels = level.labels;
		this.jump = level.jump;
		this.movingBlocks = level.movingBlocks;
		this.stationaryBlocks = level.stationaryBlocks;
		this.hurtBlocks = level.hurtBlocks;
		this.deathBlocks = level.deathBlocks;
		this.name = level.name;
		this.background = level.background;
		this.width = level.width;
		this.height = level.height;
		this.defaultTexture = level.defaultTexture;
		this.music = level.music;
		this.powerUps = level.powerUps;
		this.headsUp = level.headsUp;
	}
}