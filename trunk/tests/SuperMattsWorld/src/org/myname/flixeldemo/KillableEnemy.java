package org.myname.flixeldemo;

import org.flixel.FlxCore;

public class KillableEnemy extends Enemy
{
	protected final int VELOCITY_AFTER_KILL = -150;
	public KillableEnemy(int startX, int startY, float horizontalSpeed, int SimpleGraphic)
	{
		super(startX, startY, horizontalSpeed, SimpleGraphic);
	}
	
	public boolean overlaps(FlxCore Core)
	{
		boolean overlaps = super.overlaps(Core);
		//if the player overlaps the enemy
		if(overlaps && Core.getClass() == Player.class)
		{
			//check for overlap from the top
			if(this.collideY(Core))
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
}
