package org.myname.flixeldemo;

//--A killable enemy that moves sideways. If the player is within MAXIMUM_DISTANCE then it will stop
//	moving and fire its missile left or right depending on the position of the player
//--A ShootingEnemy contains a reference to the player. This is so that when the player is on screen, the 
//	enemy will shoot toward the player
public class ShootingEnemy extends Enemy
{
	protected Projectile missile;
	protected Player player;
	protected int missleWidth;
	protected final int MAX_MISSILE_VELOCITY = 100;
	protected final int MISSILE_VERTICAL_VELOCITY = 50;
	protected final int MAXIMUM_DISTANCE = 200;
	protected final int VELOCITY_AFTER_KILL = -150;
	
	public ShootingEnemy(int x, int y, float horizontalSpeed, Integer EnemyGraphic, Projectile ammo, Player player)
	{
		super(x, y, horizontalSpeed, EnemyGraphic);
		
		this.missile = ammo;
		this.missleWidth = this.missile.width;
		this.player = player;
		this.killable = true;
	}
	
	public ShootingEnemy(int x, int y, float horizontalSpeed, Integer EnemyGraphic, Projectile ammo)
	{
		super(x, y, horizontalSpeed, EnemyGraphic);
		
		this.missile = ammo;
		this.missleWidth = this.missile.width;
		this.player = null;
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
					if(!this.missile.exists)
					{
						if(this.player.x < this.x)
						{
							//player is to the left, so shoot left
							this.missile.shoot((int)this.x - missleWidth, (int)this.y, -MAX_MISSILE_VELOCITY, -MISSILE_VERTICAL_VELOCITY);
						}
						else
						{
							//player is to the right, so shoot right
							this.missile.shoot((int)this.x + this.width + missleWidth, (int)this.y, MAX_MISSILE_VELOCITY, -MISSILE_VERTICAL_VELOCITY);
						}
					}
					//don't move while shooting
					this.velocity.x = 0;
				}
				else
				{
					//player is not within range, so move horizontally
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
