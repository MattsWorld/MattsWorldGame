//package org.myname.flixeldemo;
//
//import org.flixel.FlxBlock;
//import org.flixel.FlxG;
//import org.myname.flixeldemo.DeathBlock.BlockType;
//
//public class GameState extends AbstractLevel 
//{
////	protected ArrayList<FlxBlock> levelBlocks = new ArrayList<FlxBlock>();
////	protected ArrayList<FlxBlock> highBlocks = new ArrayList<FlxBlock>();
////	protected ArrayList<MovingBlock> movingBlocks = new ArrayList<MovingBlock>();
////	protected ArrayList<Enemy> enemies	= new ArrayList<Enemy>();
////	protected Player player = null;
//
//	public GameState()
//	{		
//		FlxBlock ground = new FlxBlock(0, 640-16, 825, 16);
//		FlxBlock higherGround = new FlxBlock(60, 640 - 60, 50, 16);
//		FlxBlock evenHigherGround = new FlxBlock( 75, 640 - 100, 50, 16);
//		FlxBlock leftWall = new FlxBlock(340, 640-32, 16, 16);
//		FlxBlock rightWall = new FlxBlock(540, 640-32, 16, 16);
//		FlxBlock righterWall = new FlxBlock(684, 640-32, 16, 16);
//		FlxBlock platformAboveDeath = new FlxBlock(625, 640 - 125, 159, 16);
//		DeathBlock spike = new DeathBlock(650, 640-141, 80, BlockType.spike);
//		DeathBlock water = new DeathBlock(556, 640-32, 128, BlockType.water);
//		DeathBlock flames = new DeathBlock(700, 640-32, 112, BlockType.fire);
//		MovingBlock platform = new MovingBlock(50, 1, 0, 0, 150, 640-64, 90, 48, false);
//		MovingBlock squisher = new MovingBlock(-98, 2, 0, 0, 275, 640-98, 75, 32, false);
//		MovingBlock tester = new MovingBlock(98, 2, 0, 0, 275, 640-98, 75, 32, false); //test changes
//
//		ground.loadGraphic(R.drawable.tech_tiles);
//		higherGround.loadGraphic(R.drawable.tech_tiles);
//		evenHigherGround.loadGraphic(R.drawable.tech_tiles);
//		leftWall.loadGraphic(R.drawable.tech_tiles);
//		rightWall.loadGraphic(R.drawable.tech_tiles);
//		righterWall.loadGraphic(R.drawable.tech_tiles);
//		platformAboveDeath.loadGraphic(R.drawable.tech_tiles);
//		platform.loadGraphic(R.drawable.tech_tiles);
//		squisher.loadGraphic(R.drawable.tech_tiles);
//		tester.loadGraphic(R.drawable.tech_tiles);
//
//		super.deathBlocks.add(this.add(spike));
//		super.deathBlocks.add(this.add(water));
//		super.deathBlocks.add(this.add(flames));
//		super.movingBlocks.add(this.add(tester));
//		super.movingBlocks.add(this.add(platform));
//		super.movingBlocks.add(this.add(squisher));
//		super.stationaryBlocks.add(this.add(higherGround));
//		super.stationaryBlocks.add(this.add(evenHigherGround));
//		super.stationaryBlocks.add(this.add(leftWall));
//		super.stationaryBlocks.add(this.add(rightWall));
//		super.stationaryBlocks.add(this.add(ground));
//		super.stationaryBlocks.add(this.add(righterWall));
//		super.stationaryBlocks.add(this.add(platformAboveDeath));
//
//		super.player = new Player();
//		this.add(player);
//		FlxG.follow(player, 2.5f);
//		FlxG.followAdjust(0.5f, 0.0f);
//		FlxG.followBounds(0, 0, 800, 640);
//
//		float enemySpeed = 50;
//		super.enemies.add(this.add(new Enemy(400, 640 - 50, enemySpeed, R.drawable.enemy)));
//
//		//FlxG.playMusic(R.raw.d3d);
//	}
//
////	public void update()
////	{
////		super.update();
////		FlxG.collideArrayList(levelBlocks, player);
////		FlxG.collideArrayList(highBlocks, player);
////		FlxG.collideArrayList(movingBlocks, player);
////		FlxG.overlapArrayList(movingBlocks, player, new FlxCollideListener()
////		{
////			public void Collide(FlxCore object1, FlxCore object2)
////			{
////				player.kill();
////			}
////		}
////		);
////		FlxG.overlapArrayList(levelBlocks, player, new FlxCollideListener()
////		{
////			public void Collide(FlxCore object1, FlxCore object2)
////			{
////				player.kill();
////			}
////		}
////		);
////		FlxG.collideArrayLists(levelBlocks, enemies);
////		FlxG.collideArrayLists(enemies, highBlocks);
////		FlxG.overlapArrayList(enemies, player, new FlxCollideListener()
////		{
////			public void Collide(FlxCore object1, FlxCore object2)
////			{
////				player.kill();
////			}
////		}		
////		);
////
////	}
//}
