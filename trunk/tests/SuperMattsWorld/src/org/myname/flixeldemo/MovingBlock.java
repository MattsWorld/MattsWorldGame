package org.myname.flixeldemo;

import org.flixel.*;

public class MovingBlock extends FlxBlock
{
	protected int verticalMovementDirection;
	protected int horizontalMovementDirection;
	protected int maximumVerticalMovement;
	protected int maximumHorizontalMovement;
	protected int verticalMovementSpeed;
	protected int horizontalMovementSpeed;
	protected int startX;
	protected int startY;
	protected boolean oneway;
	
	public MovingBlock(int maxVerticalMovement, int verticalSpeed, int maxHorizontalMovement, 
			int horizontalSpeed, int xPos, int yPos, int width, int height, boolean oneway)
	{
		super(xPos, yPos, width, height);
		this.verticalMovementDirection = 1;
		this.horizontalMovementDirection = 1;
		this.maximumVerticalMovement = maxVerticalMovement;
		this.maximumHorizontalMovement = maxHorizontalMovement;
		this.verticalMovementSpeed = verticalSpeed;
		this.horizontalMovementSpeed = horizontalSpeed;
		this.startX = xPos;
		this.startY = yPos;	
		this.oneway = oneway;
	}
	
	public void update()
	{
		super.update();
		
		this.y += verticalMovementDirection * verticalMovementSpeed;
		this.x += horizontalMovementDirection * horizontalMovementSpeed;
		
		if(!oneway) //both directions (diagonals work as well)
		{
			if(maximumHorizontalMovement > 0) //left (start) to right (end); works
			{
				if (this.x >= this.startX + this.maximumHorizontalMovement) //right of end position
				{
				    this.x = this.startX + this.maximumHorizontalMovement;
				    horizontalMovementDirection = -1;
				}
				else if (this.x <= this.startX) // left of start position
				{
				    horizontalMovementDirection = 1;
				}
			}
			else if (maximumHorizontalMovement < 0) //right (start) to left (end); works
			{
				if(this.x >= this.startX) //right of start position
				{
					horizontalMovementDirection = -1;
				}
				else if(this.x <= this.startX + this.maximumHorizontalMovement) //left of end position
				{
					this.x = this.startX + this.maximumHorizontalMovement;
					horizontalMovementDirection = 1;
				}
			}
			
			if(maximumVerticalMovement > 0) //up (start) to down (end); works
			{
				if (this.y >= this.startY + this.maximumVerticalMovement) //above end location
				{
				    this.y = this.startY + this.maximumVerticalMovement;
				    verticalMovementDirection = -1;
				}
				else if (this.y <= this.startY) //below start position
				{
				    verticalMovementDirection = 1;
				}
			}
			else if(maximumVerticalMovement < 0) //down (start) to up (end); works
			{
				if(this.y >= this.startY) //above start position
				{
					verticalMovementDirection = -1;
				}
				else if(this.y <= this.startY + this.maximumVerticalMovement) //below end position
				{
					this.y = this.startY + this.maximumVerticalMovement;
					verticalMovementDirection = 1;
				}
			}
		}
		else //single directions only (diagonals work as well)
		{
			if(maximumHorizontalMovement < 0) //left only; works
			{
				if(this.x >= this.startX) //right of start position
				{
					horizontalMovementDirection = -1;
				}
				else if(this.x <= this.startX + this.maximumHorizontalMovement) //left of end position
				{
					this.x = this.startX - this.maximumHorizontalMovement;
					horizontalMovementDirection = -1;
				}
			}
			else if(maximumHorizontalMovement > 0) //right only; works
			{
				if (this.x >= this.startX + this.maximumHorizontalMovement) //right of end position
				{
				    this.x = this.startX - this.maximumHorizontalMovement;
				    horizontalMovementDirection = 1;
				}
				else if (this.x <= this.startX) // left of start position
				{
				    horizontalMovementDirection = 1;
				}
			}
			
			if(maximumVerticalMovement < 0) //up only; works
			{
				if(this.y >= this.startY) //above start position
				{
					verticalMovementDirection = -1;
				}
				else if(this.y <= this.startY + this.maximumVerticalMovement) //below end position
				{
					this.y = this.startY - this.maximumVerticalMovement;
					verticalMovementDirection = -1;
				}
			}
			else if(maximumVerticalMovement > 0) //down only; works
			{
				if (this.y >= this.startY + this.maximumVerticalMovement) //above end location
				{
				    this.y = this.startY - this.maximumVerticalMovement;
				    verticalMovementDirection = 1;
				}
				else if (this.y <= this.startY) //below start position
				{
				    verticalMovementDirection = 1;
				}
			}
		}
	}
}

