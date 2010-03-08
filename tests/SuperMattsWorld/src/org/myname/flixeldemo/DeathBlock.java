package org.myname.flixeldemo;

import java.util.ArrayList;
import java.util.Arrays;

import org.flixel.FlxBlock;
import org.flixel.FlxCore;
import org.flixel.FlxG;

import flash.display.BitmapData;

public class DeathBlock extends FlxBlock
{	
	public enum BlockType
	{
		water,
		fire,
		spike
	}
	
	protected BlockType type;
	protected ArrayList<AnimatedBlock> blocks = new ArrayList<AnimatedBlock>();
	
	/*
	 * Uses an ArrayList of AnimatedBlocks because there is no simple way to animate  a line of blocks
	 * The ArrayList contains either a single entry if the block is stationary, or has width/n entries
	 * where each entry is n pixels wide. When setting the width of the DeathBlock its best for the 
	 * width to be evenly divisible by the pixel width of the image(or one section of the image if
	 * is animated). Height cannot be specified because will cause issues if the height specified is
	 * different than the height of the graphic
	 */
	public DeathBlock(int x, int y, int width, BlockType blockType)
	{
		super(x, y, width, width);
		this.type = blockType;
		
		switch(this.type)
		{
			case water:
				this.createAnimatedDeathBlock(x, y, width, R.drawable.water, new Integer[] {0, 1});
				break;
			case fire:
				this.createAnimatedDeathBlock(x, y, width, R.drawable.flame, new Integer[] {0, 1, 2});
				break;
			case spike:
				this.createNonAnimatedDeathBlock(x, y, width, R.drawable.spike);
				break;
		}
	}
	
	private void createAnimatedDeathBlock(int x, int y, int width, Integer TileGraphic, Integer[] imageFrames)
	{
		BitmapData pixels = FlxG.addBitmap(TileGraphic, false);
		int widthOfBlock = pixels.width / imageFrames.length;
		int numberOfBlocks = width / widthOfBlock;
		
		ArrayList<Integer> Frames = new ArrayList<Integer>(Arrays.asList(imageFrames));
		for(int i = 0; i < numberOfBlocks; i++)
		{
			blocks.add(new AnimatedBlock(x + i*widthOfBlock, y, widthOfBlock, pixels.height));
			blocks.get(i).loadGraphic(TileGraphic, true, widthOfBlock, pixels.height);
			blocks.get(i).addAnimation("idle", Frames, 2);
		}
	}
	
	private void createNonAnimatedDeathBlock(int x, int y, int width, Integer TileGraphic)
	{		
		BitmapData pixels = FlxG.addBitmap(TileGraphic, false);
		
		blocks.add(new AnimatedBlock(x, y, width, pixels.height));
		blocks.get(0).loadGraphic(TileGraphic);
	}
	
	public void update()
	{
		for(AnimatedBlock ab : blocks)
		{
			if(ab.isAnimated && ab.onScreen())
			{
				ab.play("idle");
			}
			ab.update();
		}
	}
	
	public void render()
	{
		for(AnimatedBlock ab : blocks)
		{
			ab.render();
		}
	}
	
	public boolean collide(FlxCore Core)
	{
		boolean collision = false;
		for(AnimatedBlock ab : blocks)
		{
			if(ab.collide(Core))
			{
				collision = true;
			}
		}
		return collision;
	}
	
	public boolean overlaps(FlxCore Core)
	{
		boolean overlap = false;
		for(AnimatedBlock ab : blocks)
		{
			if(ab.overlaps(Core))
			{
				overlap = true;
			}
		}
		return overlap;
	}
}
