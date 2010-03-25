package org.myname.flixeldemo;

import java.util.ArrayList;
import java.util.Arrays;

import org.flixel.*;

//A non-killable enemy that doesn't move
public class Enemy extends FlxSprite
{	
	private static final float GRAVITY_ACCELERATION = 420;
	protected final int VELOCITY_AFTER_KILL = -150;
	private static final FlxSound yupyup = new FlxSound().loadEmbedded(R.raw.yup_yup);

	protected boolean killable = false;
	protected float speed;

	public Enemy(int startX, int startY, float horizontalSpeed, int SimpleGraphic)
	{
		super(startX, startY, SimpleGraphic, true);
		this.speed = horizontalSpeed;
		this.velocity.x = speed;
		this.acceleration.y = GRAVITY_ACCELERATION;
		
		//Get the number of frames for the animation
		int orininalImageWidth = (FlxResourceManager.getImage(SimpleGraphic)).width;
		int numberOfFrames = orininalImageWidth / this.width;
		Integer[] animationFrames = new Integer[numberOfFrames];
		for(int i = 0; i < numberOfFrames; i++)
		{
			animationFrames[i] = i;
		}
		
		super.addAnimation("moving", new ArrayList<Integer>(Arrays.asList(animationFrames)), 12);
	}
	
	public void update()
	{
		if(this.onScreen())
		{
			super.play("moving");
		}
		else
		{
			this.velocity.x = 0;
		}
		super.update();
	}

	public boolean collideX(FlxCore core)
	{
		boolean collidedX = super.collideX(core);

		//on a collision in the x direction, change directions
		if(collidedX)
		{
			yupyup.play();
			this.speed = -this.speed;
			this.velocity.x = this.speed;
		}

		return collidedX;
	}
	
	public boolean overlaps(FlxCore Core)
	{
		boolean overlaps = super.overlaps(Core);
		
		//if the player overlaps the enemy
		if(overlaps && Core.getClass() == Player.class)
		{
			//check if player overlapped from the top
			if(this.topCollision(Core) && this.killable)
			{
				this.kill();
				this.exists = false;
				((Player)Core).velocity.y = VELOCITY_AFTER_KILL;
			}
			else
			{
				Core.kill();
			}
		}
		return overlaps;
	}
	
	private boolean topCollision(FlxCore Core)
	{
		boolean collision = false;
		//Below code was taken from FlxCore.
		float ctp = Core.y - Core.last.y;
		float ttp = y - last.y;
		boolean tco = (Core.y < y + height) && (Core.y + Core.height > y);
		if(	( (ctp > 0) && (ttp <= 0) ) ||
			( (ctp >= 0) && ( ( ctp >  ttp) && tco ) ) ||
			( (ctp <= 0) && ( (-ctp < -ttp) && tco ) ) )
		{
			if(Core.y + Core.height > this.y)
			{
				collision = true;
			}
		}
		
		return collision;
	}
	
	public void kill()
	{
		super.kill();
	}
}