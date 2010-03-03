package org.myname.flixeldemo;

import java.util.ArrayList;
import java.util.Arrays;

import org.flixel.*;

public class Enemy extends FlxSprite
{	
	protected static final float GRAVITY_ACCELERATION = 420;
	protected float speed;
	
	public Enemy(int startX, int startY, float horizontalSpeed, int SimpleGraphic)
	{
		super(startX, startY, SimpleGraphic, true);
		
		speed = horizontalSpeed;
		this.velocity.x = speed;
		this.acceleration.y = GRAVITY_ACCELERATION;
		addAnimation("idle", new ArrayList<Integer>(Arrays.asList(new Integer[] {0, 1})), 12);
	}
	
	public void update()
	{
		if(!this.onScreen())
		{
			this.velocity.x = 0;
		}
		else
		{
			play("idle");
			this.velocity.x = speed;
		}
		super.update();
	}

	public boolean collideX(FlxCore core)
	{
		boolean collidedX = super.collideX(core);

		if(collidedX)
		{
			this.speed = -this.speed;
			this.velocity.x = this.speed;
		}
		
		return collidedX;
	}
}
