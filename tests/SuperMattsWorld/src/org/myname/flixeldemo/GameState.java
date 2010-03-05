package org.myname.flixeldemo;

import java.util.ArrayList;

import org.flixel.FlxBlock;
import org.flixel.FlxCollideListener;
import org.flixel.FlxCore;
import org.flixel.FlxG;
import org.flixel.FlxState;

public class GameState extends FlxState 
{
	protected ArrayList<FlxBlock> levelBlocks = new ArrayList<FlxBlock>();
	protected ArrayList<FlxBlock> highBlocks = new ArrayList<FlxBlock>();
	protected ArrayList<MovingBlock> movingBlocks = new ArrayList<MovingBlock>();
	protected ArrayList<Enemy> enemies	= new ArrayList<Enemy>();
	protected Player player = null;

	public GameState()
	{		
		FlxBlock ground = new FlxBlock(0, 640-16, 640, 16);
		FlxBlock higherGround = new FlxBlock(60, 640 - 60, 50, 16);
		FlxBlock evenHigherGround = new FlxBlock( 75, 640 - 100, 50, 16);
		FlxBlock leftWall = new FlxBlock(340, 640-32, 16, 16);
		FlxBlock rightWall = new FlxBlock(540, 640-32, 16, 16);
		MovingBlock platform = new MovingBlock(50, 1, 0, 0, 150, 640-64, 90, 48, false);
		MovingBlock squisher = new MovingBlock(-98, 2, 0, 0, 275, 640-98, 75, 32, false);
		MovingBlock tester = new MovingBlock(98, 2, 0, 0, 275, 640-98, 75, 32, false); //test changes

		ground.loadGraphic(R.drawable.tech_tiles);
		higherGround.loadGraphic(R.drawable.tech_tiles);
		evenHigherGround.loadGraphic(R.drawable.tech_tiles);
		leftWall.loadGraphic(R.drawable.tech_tiles);
		rightWall.loadGraphic(R.drawable.tech_tiles);
		platform.loadGraphic(R.drawable.tech_tiles);
		squisher.loadGraphic(R.drawable.tech_tiles);
		tester.loadGraphic(R.drawable.tech_tiles);

		movingBlocks.add(this.add(tester));
		movingBlocks.add(this.add(platform));
		movingBlocks.add(this.add(squisher));
		highBlocks.add(this.add(higherGround));
		highBlocks.add(this.add(evenHigherGround));
		highBlocks.add(this.add(leftWall));
		highBlocks.add(this.add(rightWall));
		levelBlocks.add(this.add(ground));

		player = new Player();
		this.add(player);
		FlxG.follow(player, 2.5f);
		FlxG.followAdjust(0.5f, 0.0f);
		FlxG.followBounds(0, 0, 640, 640);

		float enemySpeed = 50;
		enemies.add(this.add(new Enemy(400, 640 - 50, enemySpeed, R.drawable.enemy)));

		//FlxG.playMusic(R.raw.d3d);
	}

	public void update()
	{
		super.update();
		FlxG.collideArrayList(levelBlocks, player);
		FlxG.collideArrayList(highBlocks, player);
		FlxG.collideArrayList(movingBlocks, player);
		FlxG.overlapArrayList(movingBlocks, player, new FlxCollideListener()
		{
			public void Collide(FlxCore object1, FlxCore object2)
			{
				player.kill();
			}
		}
		);
		FlxG.overlapArrayList(levelBlocks, player, new FlxCollideListener()
		{
			public void Collide(FlxCore object1, FlxCore object2)
			{
				player.kill();
			}
		}
		);
		FlxG.collideArrayLists(levelBlocks, enemies);
		FlxG.collideArrayLists(enemies, highBlocks);
		FlxG.overlapArrayList(enemies, player, new FlxCollideListener()
		{
			public void Collide(FlxCore object1, FlxCore object2)
			{
				player.kill();
			}
		}		
		);

	}
}
