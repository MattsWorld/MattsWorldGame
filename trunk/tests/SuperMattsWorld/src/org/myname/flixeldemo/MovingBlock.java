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
	
	public MovingBlock(int maxVerticalMovement, int verticalSpeed, int maxHorizontalMovement, 
			int horizontalSpeed, int xPos, int yPos, int width, int height)
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
	}
	
	public void update()
	{
		super.update();
		
		this.y += verticalMovementDirection * verticalMovementSpeed;
		this.x += horizontalMovementDirection * horizontalMovementSpeed;
			   
		if (this.x >= this.startX + this.maximumHorizontalMovement)
		{
		    this.x = this.startX + this.maximumHorizontalMovement;
		    horizontalMovementDirection = -1;
		}
		else if (this.x <= this.startX)
		{
		    horizontalMovementDirection = 1;
		}
		
		if (this.y >= this.startY + this.maximumVerticalMovement)
		{
		    this.y = this.startY + this.maximumVerticalMovement;
		    verticalMovementDirection = -1;
		}
		else if (this.y <= this.startY)
		{
		    verticalMovementDirection = 1;
		}
	}
}

