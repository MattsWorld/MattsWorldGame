package collectables;

import org.myname.flixeldemo.Player;
import org.myname.flixeldemo.R;
import org.myname.flixeldemo.parsing.Level;

public final class EnergyDrink extends PowerUp
{
	public static float TIME_GAINED = 100F;

	protected EnergyDrink(int X, int Y, int Width, int Height)
	{
		super(X, Y, Width, Height);
		super.loadGraphic(R.drawable.red_bull);
		//-- TODO Graphics
	}

	@Override
	protected void onCollect(Player p)
	{
		Level.timeRemaining += TIME_GAINED;
	}

	@Override
	protected void playSound()
	{
		// TODO Auto-generated method stub
	}
}