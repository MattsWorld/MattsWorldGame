package game.all;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * 
 * @author Tony
 */
public class GameView extends View
{
	public int x = 0;
	public int y = 0;

	public GameView(Context context)
	{
		super(context);
		super.setKeepScreenOn(true);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		Paint p = new Paint();
		
		int width = canvas.getWidth(); 
		int height = canvas.getHeight();			

		p.setColor(Color.BLUE);
		canvas.drawRect(0, 0, width, height, p);

		p.setColor(Color.GREEN);
		canvas.drawRect(0, height/2, width, height, p);

		p.setColor(Color.RED);
		canvas.drawRect(0, (int)(height*3.0/4), width, height, p);

		p.setColor(Color.BLACK);
		canvas.drawRect(40 + x, (int)(height*3.0/4)-40 + y, 80 + x, (int)(height*3.0/4) + y, p);

		canvas.drawText("Matt!", 90, (int)(height*3.0/4)-40, p);
		canvas.drawText("Sky!", 50, 20, p);
		canvas.drawText("Middle Background!", 120, (height/2) + 15, p);
		canvas.drawText("Beach!", 50, (int)(height*3.0/4) + 15, p);
	}
}
