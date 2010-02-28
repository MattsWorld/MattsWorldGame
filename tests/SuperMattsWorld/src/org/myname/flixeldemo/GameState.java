package org.myname.flixeldemo;

import java.util.ArrayList;

import org.flixel.*;

public class GameState extends FlxState 
{
	protected ArrayList<FlxBlock> levelBlocks = new ArrayList<FlxBlock>();
	protected ArrayList<FlxBlock> highBlocks = new ArrayList<FlxBlock>();
	protected Player player = null;
	
	public GameState()
	{
		FlxBlock ground = new FlxBlock(0, 640-16, 640, 16);
		FlxBlock higherGround = new FlxBlock(60, 640 - 60, 50, 16);

		ground.loadGraphic(R.drawable.tech_tiles);
		higherGround.loadGraphic(R.drawable.tech_tiles);

		highBlocks.add(this.add(higherGround));
		levelBlocks.add(this.add(ground));
		
		player = new Player();
		this.add(player);
		FlxG.follow(player, 2.5f);
		FlxG.followAdjust(0.5f, 0.0f);
		FlxG.followBounds(0, 0, 640, 640);
		
		FlxG.playMusic(R.raw.d3d);
	}
	
	public void update()
	{
		super.update();
		FlxG.collideArrayList(levelBlocks, player);
		FlxG.collideArrayListY(highBlocks, player);
	}
}
