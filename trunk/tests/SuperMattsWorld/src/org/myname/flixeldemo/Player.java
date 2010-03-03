package org.myname.flixeldemo;

import java.util.ArrayList;
import java.util.Arrays;

import org.flixel.FlxG;
import org.flixel.FlxSprite;

import android.view.KeyEvent;

public class Player extends FlxSprite
{
	protected static final int PLAYER_START_X = 10;
	protected static final int PLAYER_START_Y = 640-30;

	protected static final int PLAYER_WIDTH_PX = 16;
	protected static final int PLAYER_HEIGHT_PX = 16;
	
	protected static final int PLAYER_RUN_SPEED = 80;
	protected static final float GRAVITY_ACCELERATION = 300; //-- 420
	protected static final float JUMP_ACCELERATION = 300; //-- 400
	
	public Player()
	{
		super(PLAYER_START_X, PLAYER_START_Y, R.drawable.spaceman, true, PLAYER_WIDTH_PX, PLAYER_HEIGHT_PX);
		//super.offset = new Point(16, 0);
		
		
		drag.x = PLAYER_RUN_SPEED * 8;
		acceleration.y = GRAVITY_ACCELERATION;
		maxVelocity.x = PLAYER_RUN_SPEED;
		maxVelocity.y = JUMP_ACCELERATION;
				
		addAnimation("idle", new ArrayList<Integer>(Arrays.asList(new Integer[] {0})));
		addAnimation("run",  new ArrayList<Integer>(Arrays.asList(new Integer[] {1, 2, 3, 0})), 12);
		addAnimation("jump",  new ArrayList<Integer>(Arrays.asList(new Integer[] {5})));
		//addAnimation("idle_up",  new ArrayList<Integer>(Arrays.asList(new Integer[] {5})));
		//addAnimation("run_up",  new ArrayList<Integer>(Arrays.asList(new Integer[] {6, 7, 8, 5})), 12);
		//addAnimation("jump_up",  new ArrayList<Integer>(Arrays.asList(new Integer[] {9})));
		//addAnimation("jump_down",  new ArrayList<Integer>(Arrays.asList(new Integer[] {10})));
	}
	
	public void update()
	{
		acceleration.x = 0;
		if(FlxG.keys.pressed(KeyEvent.KEYCODE_DPAD_LEFT) || FlxG.keys.justPressed(KeyEvent.KEYCODE_A))
		{
			setFacing(LEFT);
			acceleration.x = -drag.x;
		}
		else if(FlxG.keys.pressed(KeyEvent.KEYCODE_DPAD_RIGHT) || FlxG.keys.justPressed(KeyEvent.KEYCODE_D))
		{
			setFacing(RIGHT);
			acceleration.x = drag.x;
		}
		if((FlxG.keys.justPressed(KeyEvent.KEYCODE_DPAD_UP) || FlxG.keys.justPressed(KeyEvent.KEYCODE_L)) && velocity.y==0)
		{
			velocity.y = -JUMP_ACCELERATION;
		}
		
		if(velocity.y != 0)
		{
			play("jump");
		}
		else if(velocity.x == 0)
		{
			play("idle");
		}
		else
		{
			play("run");
		}
		
		super.update();
	}
	
	public void kill()
	{
		super.kill();
		FlxG.switchState(MenuState.class);
	}
}