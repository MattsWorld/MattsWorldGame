package org.myname.flixeldemo;

import org.flixel.FlxGame;
import org.flixel.FlxGameView;
import org.myname.flixeldemo.parsing.LevelParser;

import android.content.Context;
import android.util.AttributeSet;

public class GameView extends FlxGameView 
{
	//-- TODO PARSER HERE.
	public GameView(Context context, AttributeSet attrs)
	{
		super(
			new FlxGame(320, 455, MenuState.class, context, R.class), 
			context, 
			attrs
		);

		LevelParser lp = new LevelParser(R.raw.lvl_test, super.getResources());
	}
}
