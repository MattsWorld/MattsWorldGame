package org.myname.flixeldemo.parsing;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.myname.flixeldemo.R;

import android.content.res.Resources;

public class LevelParser
{
	enum State{LEVEL, MIDDLE_GROUND, STATIONARY_BLOCK, MOVING_BLOCK, HURT_BLOCK, DEATH_BLOCK, ENEMY, LABEL, JUMP, POWER_UP, NONE}

	/** Map for taking text resource names and converting them into the integer address value. */
	public static final Map<String, Integer> KEY_RESOURCE_ADDR;
	
	static
	{
		HashMap<String, Integer> temp = new HashMap<String, Integer>();

		temp.put("fire", R.drawable.fire);
		temp.put("enemy", R.drawable.enemy);
		temp.put("tech_tiles", R.drawable.tech_tiles);
		temp.put("spaceman", R.drawable.spaceman);
		temp.put("spike", R.drawable.spike);

		/*
		 * TODO - Add all resources that will be referenced as a memory
		 * location in the Droid.
		 */	

		KEY_RESOURCE_ADDR = Collections.unmodifiableMap(temp);		
	}

	public LevelParser(int resource, Resources res)
	{
		try
		{
			InputStreamReader isr = new InputStreamReader(res.openRawResource(resource), Charset.forName("UTF-8"));
			BufferedReader br = new BufferedReader(isr);

			String str;
			State state = State.NONE;

			while((str = br.readLine()) != null)
			{
				if(str.startsWith("#"))
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
				}

				/*
				 * TODO Handle all of the different attributes
				 * of the the "tag-like" components in the text file.
				 */
				if (state.equals(State.LEVEL))
				{
					
				}
			}
		}catch(Throwable ioe)
		{

		}
	}
}