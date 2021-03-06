package org.myname.flixeldemo;

import java.util.ArrayList;
import java.util.Arrays;

import org.flixel.FlxCore;
import org.flixel.FlxEmitter;
import org.flixel.FlxFadeListener;
import org.flixel.FlxG;
import org.flixel.FlxSound;
import org.flixel.FlxSprite;
import org.myname.flixeldemo.parsing.Level;

import android.view.KeyEvent;

public class Player extends FlxSprite
{
	protected static final int PLAYER_START_X = 10;
	private static final int PLAYER_START_Y = 640-230;

	private static final int PLAYER_WIDTH_PX = 32;
	private static final int PLAYER_HEIGHT_PX = 32;

	private static final int PLAYER_RUN_SPEED = 180;
	private static final float GRAVITY_ACCELERATION = 420; //-- 420
	private static final float JUMP_ACCELERATION = 400; //-- 400

	private static final FlxSound SOUND_DEATH = new FlxSound().loadEmbedded(R.raw.death1);
	private static final FlxSound JUMP = new FlxSound().loadEmbedded(R.raw.jumpsfx);

	/* Add to state! */
	public static final FlxEmitter chunkies = new FlxEmitter(0, 0, -1.5f)
	.setXVelocity(-150.0f, 150.0f)
	.setYVelocity(-200.0f, 0.0f)
	.setRotation(-720, 720)
	.setGravity(400f)
	.createSprites(R.drawable.cigarette);

	public Player()
	{
		super(PLAYER_START_X, PLAYER_START_Y, R.drawable.matt, true, PLAYER_WIDTH_PX, PLAYER_HEIGHT_PX);

		drag.x = PLAYER_RUN_SPEED * 8;
		acceleration.y = GRAVITY_ACCELERATION;
		maxVelocity.x = PLAYER_RUN_SPEED;
		maxVelocity.y = JUMP_ACCELERATION;

		addAnimation("idle", new ArrayList<Integer>(Arrays.asList(new Integer[] {0})));
		addAnimation("run",  new ArrayList<Integer>(Arrays.asList(new Integer[] {1, 2})), 16);
		addAnimation("jump",  new ArrayList<Integer>(Arrays.asList(new Integer[] {3})));
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
			JUMP.play();
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

		SOUND_DEATH.play();
		chunkies.x = this.x + (this.width>>1);
		chunkies.y = this.y + (this.height>>1);
		chunkies.restart();

		//-- clear level saves so I am not always dead!!!
		/* TODO
		 * if implementing "lives", only clear current level and reset, so
		 * Level.java will require a method to do this.
		 * and only switch to menu when lives == 0
		 */
		Level.levelSaves.clear();

		//-- Fade out for dramatic effect before
		//   going back to the title screen.
		FlxG.fade(0xffffffff, 2, new FlxFadeListener()
		{
			public void fadeComplete()
			{
				FlxG.switchState(MenuState.class);
			}
		}
		);
	}
	
	public boolean collide(FlxCore Core)
	{
		boolean hx = super.collideX(Core);
		boolean hy = super.collideY(Core);
		
		//Causes player to fall after hitting the bottom of a platform
		if(hy && Core.hitCeiling())
			velocity.y = JUMP_ACCELERATION;
		
		return hx || hy;
	}
}