package org.myname.flixeldemo.parsing;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.flixel.FlxBlock;
import org.myname.flixeldemo.BouncingEnemy;
import org.myname.flixeldemo.Enemy;
import org.myname.flixeldemo.GameView;
import org.myname.flixeldemo.JumpBlock;
import org.myname.flixeldemo.MovingBlock;
import org.myname.flixeldemo.Player;
import org.myname.flixeldemo.R;

import collectables.PowerUp;

import flash.geom.Point;

/**
 * Parses Level data from text files. These files are used to create <code>Level</code>
 * objects to run the game.
 * 
 * 3/11/2010 - Tony Greer Implemented jump blocks and added lvl_jump_test
 *  
 * @author Matt's World Design Team
 */
public final class LevelParser
{
	enum State{LEVEL, MIDDLE_GROUND, STATIONARY_BLOCK, MOVING_BLOCK, HURT_BLOCK, DEATH_BLOCK, ENEMY, LABEL, JUMP, POWER_UP, KENEMY, MUSIC, TIME, TEXT, NONE}

	/** Map for taking text resource names and converting them into the integer address value. */
	public static final Map<String, Integer> KEY_RESOURCE_ADDR;
	
	static
	{		
		/*
		 * TODO - Add all resources that will be referenced as a memory
		 * location in the Droid.
		 */
		HashMap<String, Integer> temp = new HashMap<String, Integer>();

		/* TEXTURES */
		temp.put("fire", R.drawable.fire);
		temp.put("enemy", R.drawable.enemy);
		temp.put("kenemy", R.drawable.enemy);
		temp.put("tech_tiles", R.drawable.tech_tiles);
		temp.put("matt", R.drawable.matt);
		temp.put("spike", R.drawable.spike);
		temp.put("water", R.drawable.water);
		temp.put("sand", R.drawable.sand);
		temp.put("rock", R.drawable.rock);
		temp.put("boulder", R.drawable.boulder);

		/* LEVELS */
		temp.put("lvl_test", R.raw.lvl_test);
		temp.put("lvl_test2", R.raw.lvl_test2);
		temp.put("lvl_test3", R.raw.lvl_test3);
		temp.put("lvl_jump_test", R.raw.lvl_jump_test);

		/* MUSIC */
		//temp.put("d3d", R.raw.d3d);
		temp.put("death1", R.raw.death1);
		temp.put("death2", R.raw.death2);
		temp.put("music1", R.raw.level1_music);
		//temp.put("", value)

		KEY_RESOURCE_ADDR = Collections.unmodifiableMap(temp);
	}

	public LevelParser(Level level)
	{
		String str = null;
		InputStreamReader isr = null;
		BufferedReader br = null;

		try
		{
			isr = new InputStreamReader(GameView.res.openRawResource(Level.nextLevel), Charset.forName("UTF-8"));
			br = new BufferedReader(isr);
			
			State state = State.NONE;

			while((str = br.readLine()) != null)
			{
				/*
				 * Comments!
				 */
				if(str.trim().startsWith("#") || "".equals(str.trim()))
					continue;

				/*
				 * Determine if there was a change of state
				 * for this particular line of text.
				 */
				if(str.equalsIgnoreCase("[level]"))
				{
					state = State.LEVEL;
					continue;
				}else if(str.equalsIgnoreCase("[middle_ground]"))
				{				
					state = State.MIDDLE_GROUND;
					continue;
				}else if(str.equalsIgnoreCase("[stationary_block]"))
				{
					state = State.STATIONARY_BLOCK;
					continue;
				}else if(str.equalsIgnoreCase("[moving_block]"))
				{
				
					state = State.MOVING_BLOCK;					
					continue;
				}else if(str.equalsIgnoreCase("[hurt_block]"))
				{
					state = State.HURT_BLOCK;
					continue;
				}else if(str.equalsIgnoreCase("[death_block]"))
				{
					state = State.DEATH_BLOCK;
					continue;
				}else if(str.equalsIgnoreCase("[enemy]"))
				{
					state = State.ENEMY;
					continue;
				}else if(str.equalsIgnoreCase("[kenemy]"))
				{
					state= State.KENEMY;
					continue;
				}else if(str.equalsIgnoreCase("[label]"))
				{
					state = State.LABEL;
					continue;	
				}else if(str.equalsIgnoreCase("[jump]"))
				{
					state = State.JUMP;
					continue;
				}else if(str.equalsIgnoreCase("[power_up]"))
				{
					state = State.POWER_UP;
					continue;	
				}else if(str.equalsIgnoreCase("[music]"))
				{
					state =State.MUSIC;
					continue;
				}else if(str.equalsIgnoreCase("[text]"))
				{
					state =State.TEXT;
					continue;
				}else if(str.equalsIgnoreCase("[time]"))
				{
					state =State.TIME;
					continue;
				}

				/*
				 * TODO Handle all of the different attributes
				 * of the the "tag-like" components in the text file.
				 */
				String[] strParts = str.split("\\s+");
				
				String name, lvl_name, text;
				int xInit, yInit, width, height,
				maxXMovement, maxYMovement, horizSpeed, verticSpeed, texture;
				switch(state)
				{
					case LEVEL:
						level.name = strParts[0];
						level.width = Integer.parseInt(strParts[1]);
						level.height = Integer.parseInt(strParts[2]);
						level.defaultTexture = KEY_RESOURCE_ADDR.get(strParts[3]);

						if(strParts.length == 5)
							level.background = KEY_RESOURCE_ADDR.get(strParts[4]);
						
					break;

					case MIDDLE_GROUND:
						
					break;

					case STATIONARY_BLOCK:
						 xInit = Integer.parseInt(strParts[0]);
						 yInit = Integer.parseInt(strParts[1]);
						 width = Integer.parseInt(strParts[2]);
						 height = Integer.parseInt(strParts[3]);
						 texture = level.defaultTexture;

						if(strParts.length == 5)
							texture = KEY_RESOURCE_ADDR.get(strParts[4]);
						
						level.stationaryBlocks.add(new FlxBlock(xInit, yInit, width, height).loadGraphic(texture));
					break;

					case MOVING_BLOCK:
						/*
						 * Question about oneway... should the thing 
						 * just travel right off the screen without even seeing
						 * it??? WTF
						 */
						 xInit = Integer.parseInt(strParts[0]);
						 yInit = Integer.parseInt(strParts[1]);
						 width = Integer.parseInt(strParts[2]);
						 height = Integer.parseInt(strParts[3]);
						 maxXMovement = Integer.parseInt(strParts[4]);
						 maxYMovement = Integer.parseInt(strParts[5]);
						 horizSpeed = Integer.parseInt(strParts[6]);
						 verticSpeed = Integer.parseInt(strParts[7]);
						 texture = level.defaultTexture;

						if(strParts.length == 9)
								texture = KEY_RESOURCE_ADDR.get(strParts[8]);
						
						level.movingBlocks.add(new MovingBlock(maxYMovement, verticSpeed, maxXMovement, horizSpeed,
								xInit, yInit, width, height, false).loadGraphic(texture));
					break;

					case HURT_BLOCK:
						 xInit = Integer.parseInt(strParts[0]);
						 yInit = Integer.parseInt(strParts[1]);
						 width = Integer.parseInt(strParts[2]);
						 height = Integer.parseInt(strParts[3]);
						 //-- TODO strParts[4] is going to be pain level
						 texture = level.defaultTexture;

						if(strParts.length == 6)
							texture = KEY_RESOURCE_ADDR.get(strParts[5]);
						
						level.hurtBlocks.add(new FlxBlock(xInit, yInit, width, height).loadGraphic(texture));
						
					break;

					case DEATH_BLOCK:
						 xInit = Integer.parseInt(strParts[0]);
						 yInit = Integer.parseInt(strParts[1]);
						 width = Integer.parseInt(strParts[2]);
						 height = Integer.parseInt(strParts[3]);
						 texture = level.defaultTexture;

						if(strParts.length == 5)
							texture = KEY_RESOURCE_ADDR.get(strParts[4]);
						
						level.deathBlocks.add(new FlxBlock(xInit, yInit, width, height).loadGraphic(texture));
					break;

					case ENEMY:
						 xInit = Integer.parseInt(strParts[0]);
						 yInit = Integer.parseInt(strParts[1]);
						 horizSpeed = Integer.parseInt(strParts[2]);
						 texture = KEY_RESOURCE_ADDR.get(strParts[3]);
						 
						 /*
						  * 		----Temporary change for consistency----
						  * 
						  * Due to the redesign of the enemy classes, a BouncingEnemy
						  * must be used here to have the same effect as the old version of Enemy.
						  * The vertical speed is set to 0 so the enemy will only move horizontally.
						  * If we used the current Enemy class, the enemy would not move horizontally at all.
						  */
						 level.enemies.add(new BouncingEnemy(xInit, yInit, horizSpeed, 0, texture));
						
					break;

					case LABEL:
						name = strParts[0];
						xInit = Integer.parseInt(strParts[1]);
						yInit = Integer.parseInt(strParts[2]);
						
						level.labels.put(name, new Point(xInit, yInit));

					break;

					case JUMP:
						xInit = Integer.parseInt(strParts[0]);
						yInit = Integer.parseInt(strParts[1]);
						width = Integer.parseInt(strParts[2]);
						height = Integer.parseInt(strParts[3]);
						lvl_name = strParts[4];
						name = strParts[5];
						
						//-- TODO Account for multiple visits
						level.jump.add(new JumpBlock(xInit, yInit, width, height, lvl_name, name, true).loadGraphic(R.drawable.fire));

					break;

					case POWER_UP:
						xInit = Integer.parseInt(strParts[0]);
						yInit = Integer.parseInt(strParts[1]);
						name = strParts[2];

						level.powerUps.add(PowerUp.getInstance(xInit, yInit, name));
					break;
					
					case MUSIC:
						level.music = KEY_RESOURCE_ADDR.get(strParts[0]);
					
					break;

					case TIME:
						Level.timeRemaining = Float.parseFloat(strParts[0]);
					
					break;				
					
					case TEXT:
						xInit = Integer.parseInt(strParts[0]);
						yInit = Integer.parseInt(strParts[1]);
						text = "";
						for (int i = 2; i < strParts.length; i++)
							text += strParts[i] + " ";
						/*
						 * TODO add to textBox for Storyboards
						 */
					
					break;					


					default:
						throw new ParseException("F!", -1);
				}
			}

			Player player = new Player();
			Point p = level.labels.get("start");
			player.x = p.x;
			player.y = p.y;
			level.player = player;
			
		}catch(Throwable ioe)
		{
			System.err.println("Problem parsing level: " + str);
			ioe.printStackTrace();
		}finally
		{
			try{if(br != null) br.close();}catch(Exception sq){}
			try{if(isr != null) isr.close();}catch(Exception sq){}
		}
	}
}