package org.myname.flixeldemo;

import java.util.ArrayList;
import java.util.Arrays;

import org.flixel.*;

public class Enemy extends FlxSprite
{	
	private static final float GRAVITY_ACCELERATION = 420;
	private static final FlxSound yupyup = new FlxSound().loadEmbedded(R.raw.yup_yup);

	protected float speed;

	public Enemy(int startX, int startY, float horizontalSpeed, int SimpleGraphic)
	{
		super(startX, startY, SimpleGraphic, true);
		this.speed = horizontalSpeed;
		this.velocity.x = speed;
		this.acceleration.y = GRAVITY_ACCELERATION;
		super.addAnimation("idle", new ArrayList<Integer>(Arrays.asList(new Integer[] {0, 1})), 12);
	}
	
	public void update()
	{
		//-- Don't move the character whilest
		//   off the screen.
		if(!this.onScreen())
		{
			this.velocity.x = 0;
		}
		else
		{
			super.play("idle");
			this.velocity.x = speed;
		}

		super.update();
	}

	public boolean collideX(FlxCore core)
	{
		boolean collidedX = super.collideX(core);

		if(collidedX)
		{
			yupyup.play();
			this.speed = -this.speed;
			this.velocity.x = this.speed;
		}

		return collidedX;
	}
}