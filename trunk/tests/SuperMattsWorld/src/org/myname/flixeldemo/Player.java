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
import org.myname.flixeldemo.parsing.LevelParser;

import android.view.KeyEvent;

public class Player extends FlxSprite
{
	protected static final int PLAYER_START_X = 10;
	private static final int PLAYER_START_Y = 640-230;

	private static final int PLAYER_WIDTH_PX = 16;
	private static final int PLAYER_HEIGHT_PX = 16;

	private static final int PLAYER_RUN_SPEED = 180;
	private static final float GRAVITY_ACCELERATION = 300; //-- 420
	private static final float JUMP_ACCELERATION = 300; //-- 400

	private static final FlxSound SOUND_DEATH = new FlxSound().loadEmbedded(R.raw.evil_laugh);
	private static final FlxSound JUMP = new FlxSound().loadEmbedded(R.raw.jumpsfx);

	protected final FlxEmitter chunkies;

	public Player()
	{
		super(PLAYER_START_X, PLAYER_START_Y, R.drawable.spaceman, true, PLAYER_WIDTH_PX, PLAYER_HEIGHT_PX);

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

		this.chunkies = new FlxEmitter(0, 0, -1.5f)
		.setXVelocity(-150.0f, 150.0f)
		.setYVelocity(-200.0f, 0.0f)
		.setRotation(-720, 720)
		.setGravity(400f)
		.createSprites(R.drawable.fire);

		FlxG.state.add(chunkies);
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
		this.chunkies.x = this.x + (this.width>>1);
		this.chunkies.y = this.y + (this.height>>1);
		this.chunkies.restart();

		//-- Fade out for dramatic effect before
		//   going back to the title screen.
		FlxG.fade(0xffffffff, 2, new FlxFadeListener()
		{
			public void fadeComplete()
			{
				FlxG.switchState(MenuState.class);
				//Level.level_choice = R.raw.lvl_test3;
				//FlxG.switchState(Level.class);
				//Level.switchLevel("lvl_test3","");
			}
		}, true
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