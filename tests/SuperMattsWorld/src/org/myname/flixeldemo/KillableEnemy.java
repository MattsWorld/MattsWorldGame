package org.myname.flixeldemo;

//--A killable enemy that moves sideways
public class KillableEnemy extends Enemy
{
	public KillableEnemy(int startX, int startY, float horizontalSpeed, int SimpleGraphic)
	{
		super(startX, startY, horizontalSpeed, SimpleGraphic);
		this.killable = true;
	}
	
	public void update()
	{
		if(this.onScreen())
		{
			this.velocity.x = speed;
		}

		super.update();
	}
}
