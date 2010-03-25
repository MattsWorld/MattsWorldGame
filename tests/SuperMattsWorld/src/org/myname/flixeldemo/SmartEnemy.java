package org.myname.flixeldemo;

//--A killable enemy that moves sideways. If the player is within MAXIMUM_DISTANCE then it will move
//	horizontally toward the player
//--A SmartEnemy contains a reference to the player. This is so that when the player is on screen, the 
//	enemy will move toward the player
public class SmartEnemy extends Enemy
{
	protected Player player;
	protected final int VELOCITY_AFTER_KILL = -150;
	protected final int MAXIMUM_DISTANCE = 200;
	
	public SmartEnemy(int x, int y, float horizontalSpeed, Integer Graphic)
	{
		super(x, y, horizontalSpeed, Graphic);
		this.player = null;
		this.killable = true;
	}
	
	public SmartEnemy(int x, int y, float horizontalSpeed, Integer Graphic, Player player)
	{
		super(x, y, horizontalSpeed, Graphic);
		this.player = player;
		this.killable = true;
	}
	
	//Add a reference to the player if not done so in the constructor
	public void setPlayer(Player player)
	{
		this.player = player;
	}
	
	public void update()
	{
		if(this.onScreen())
		{
			if(this.player != null)
			{
				boolean playerInRange = Math.abs(this.player.x - this.x) <= MAXIMUM_DISTANCE //player within horizontal distance
										&& this.player.y < this.y + this.height //player not below this
										&& this.player.y + this.player.height >= this.y - MAXIMUM_DISTANCE; //player not too far up
				if(playerInRange)
				{
					if(this.player.x < this.x)
					{
						//player to the left, so move left
						this.velocity.x = -Math.abs(speed);
					}
					else if(this.player.x > this.x)
					{
						//player to the right, so move right
						this.velocity.x = Math.abs(speed);
					}
					else
					{
						//player directly above, so don't move
						this.velocity.x = 0;
					}
				}
				else
				{
					this.velocity.x = speed;
				}
			}
			else
			{
				this.velocity.x = speed;
			}		
		}
		super.update();
	}
}
