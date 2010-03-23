package collectables;

import org.flixel.FlxSound;
import org.myname.flixeldemo.Player;
import org.myname.flixeldemo.R;
import org.myname.flixeldemo.parsing.Level;

public class Cigarette extends PowerUp
{
	public static float TIME_GAINED = 15F;

	private static final FlxSound sound = new FlxSound().loadEmbedded(R.raw.cigarettesfx);

	protected Cigarette(int X, int Y, int Width, int Height)
	{
		super(X, Y, Width, Height);
		this.loadGraphic(R.drawable.cigarette);
	}

	@Override
	protected void onCollect(Player p)
	{
		Level.timeRemaining += TIME_GAINED;
	}

	@Override
	protected void playSound()
	{
		sound.play();
	}
}