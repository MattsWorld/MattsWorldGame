package org.myname.flixeldemo;

import org.flixel.FlxGame;
import org.flixel.FlxGameView;

import android.content.Context;
import android.util.AttributeSet;

public class GameView extends FlxGameView 
{
	public GameView(Context context, AttributeSet attrs)
	{
		super(
			new FlxGame(320, 455, MenuState.class, context, R.class), 
			context, 
			attrs
		);
	}
}
