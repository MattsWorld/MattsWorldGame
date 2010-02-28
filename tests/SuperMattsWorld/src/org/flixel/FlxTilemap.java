/*package org.flixel;

import java.util.ArrayList;

import flash.display.Bitmap;
import flash.display.BitmapData;
import flash.geom.Matrix;
import flash.geom.Point;
import flash.geom.Rectangle;

	//@desc		This is a traditional tilemap display and collision class
	public class FlxTilemap extends FlxCore
	{
		//[Embed(source="data/autotiles.png")] static public Class ImgAuto;
		//[Embed(source="data/autotiles_alt.png")] static public Class ImgAutoAlt;
		
		static public final int OFF = 0;
		static public final int AUTO = 1;
		static public final int ALT = 2;
		
		//@desc		What tile index will you start colliding with (default: 1)
		public int collideIndex;
		//@desc		The first index of your tile sheet (default: 0) If you want to change it, do so before calling loadMap()
		public int startingIndex;
		//@desc		What tile index will you start drawing with (default: 1)  NOTE: should always be >= startingIndex. If you want to change it, do so before calling loadMap()
		public int drawIndex;
		//@desc		Set this flag to use one of the 16-tile binary auto-tile algorithms (OFF, AUTO, or ALT)
		public int auto;
		
		//@desc		Read-only variables, do not recommend changing them after the map is loaded!
		public int widthInTiles;
		public int heightInTiles;
		public int totalTiles;
		
		protected BitmapData _pixels;
		protected ArrayList _data;
		protected ArrayList _rects;
		protected int _tileWidth;
		protected int _tileHeight;
		protected Point _p;
		protected FlxCore _block;
		protected ArrayList _callbacks;
		protected int _screenRows;
		protected int _screenCols;
		
		//@desc		The tilemap constructor just initializes some basic variables
		public FlxTilemap()
		{
			super();
			auto = OFF;
			collideIndex = 1;
			startingIndex = 0;
			drawIndex = 1;
			widthInTiles = 0;
			heightInTiles = 0;
			totalTiles = 0;
			_data = new ArrayList();
			_p = new Point();
			_tileWidth = 0;
			_tileHeight = 0;
			_rects = null;
			_pixels = null;
			_block = new FlxCore();
			_block.width = _block.height = 0;
			_block.fixed = true;			
			_callbacks = new ArrayList();
		}
		
		//@desc		Load the tilemap with string data and a tile graphic
		//@param	MapData			A string of comma and line-return delineated indices indicating what order the tiles should go in
		//@param	TileGraphic		All the tiles you want to use, arranged in a strip corresponding to the numbers in MapData
		//@param	TileWidth		The width of your tiles (e.g. 8) - defaults to height of the tile graphic if unspecified
		//@param	TileHeight		The height of your tiles (e.g. 8) - defaults to width if unspecified
		//@return	A pointer this instance of FlxTilemap, for chaining as usual :)
		public FlxTilemap loadMap(String MapData, Class TileGraphic, int TileWidth=0, int TileHeight=0)
		{
			//Figure out the map dimensions based on the data string
			int c;
			ArrayList cols;
			ArrayList rows = MapData.split("\n");
			heightInTiles = rows.length;
			for(int r = 0; r < heightInTiles; r++)
			{
				cols = rows.get(r).split(",");
				if(cols.length <= 1)
				{
					heightInTiles--;
					continue;
				}
				if(widthInTiles == 0)
					widthInTiles = cols.length;
				for(c = 0; c < widthInTiles; c++)
					_data.push((int)(cols.get(c)));
			}
			
			//Pre-process the map data if it's auto-tiled
			int i;
			totalTiles = widthInTiles*heightInTiles;
			if(auto > OFF)
			{
				collideIndex = startingIndex = drawIndex = 1;
				for(i = 0; i < totalTiles; i++)
					autoTile(i);
			}
			//Figure out the size of the tiles
			_pixels = FlxG.addBitmap(TileGraphic);
			_tileWidth = TileWidth;
			if(_tileWidth == 0)
				_tileWidth = _pixels.height;
			_tileHeight = TileHeight;
			if(_tileHeight == 0)
				_tileHeight = _tileWidth;
			_block.width = _tileWidth;
			_block.height = _tileHeight;
			
			//Then go through and create the actual map
			width = widthInTiles*_tileWidth;
			height = heightInTiles*_tileHeight;
			_rects = new ArrayList(totalTiles);
			for(i = 0; i < totalTiles; i++)
				updateTile(i);
			//Pre-set some helper variables for later
			_screenRows = Math.ceil(FlxG.height/_tileHeight)+1;
			if(_screenRows > heightInTiles)
				_screenRows = heightInTiles;
			_screenCols = Math.ceil(FlxG.width/_tileWidth)+1;
			if(_screenCols > widthInTiles)
				_screenCols = widthInTiles;
			
			return this;
		}
		
		//@desc		Draws the tilemap
		override public void render()
		{
			super.render();
			getScreenXY(_p);
			int tx = (int) Math.floor(-_p.x/_tileWidth);
			int ty = (int) Math.floor(-_p.y/_tileHeight);
			if(tx < 0) tx = 0;
			if(tx > widthInTiles-_screenCols) tx = widthInTiles-_screenCols;
			if(ty < 0) ty = 0;
			if(ty > heightInTiles-_screenRows) ty = heightInTiles-_screenRows;
			int ri = ty*widthInTiles+tx;
			_p.x += tx*_tileWidth;
			_p.y += ty*_tileHeight;
			int opx = (int) _p.x;
			int c;
			int cri;
			for(int r = 0; r < _screenRows; r++)
			{
				cri = ri;
				for(c = 0; c < _screenCols; c++)
				{
					if(_rects.get(cri) != null)
						FlxG.buffer.copyPixels(_pixels,(Rectangle) _rects.get(cri),_p,null,null,true);
					cri++;
					_p.x += _tileWidth;
				}
				ri += widthInTiles;
				_p.x = opx;
				_p.y += _tileHeight;
			}
		}
		
		//@desc		Checks for overlaps between the provided object and any tiles above the collision index
		//@param	Core		The FlxCore you want to check against
		override public boolean overlaps(FlxCore Core)
		{
			int c;
			int d;
			int i;
			int dd;
			ArrayList blocks = new ArrayList();
			
			//First make a list of all the blocks we'll use for collision
			int ix = (int) Math.floor((Core.x - x)/_tileWidth);
			int iy = (int) Math.floor((Core.y - y)/_tileHeight);
			int iw = (int) (Math.ceil(Core.width/_tileWidth)+1);
			int ih = (int) (Math.ceil(Core.height/_tileHeight)+1);
			for(int r = 0; r < ih; r++)
			{
				if((r < 0) || (r >= heightInTiles)) break;
				d = (iy+r)*widthInTiles+ix;
				for(c = 0; c < iw; c++)
				{
					if((c < 0) || (c >= widthInTiles)) break;
					dd = _data[d+c];
					if(dd >= collideIndex)
						blocks.push({x x+(ix+c)*_tileWidth,y y+(iy+r)*_tileHeight,dd data});
				}
			}
			
			//Then check for overlaps
			int bl = blocks.size();
			boolean hx = false;
			for(i = 0; i < bl; i++)
			{
				_block.last.x = _block.x = blocks.get(i).x;
				_block.last.y = _block.y = blocks.get(i).y;
				if(_block.overlaps(Core))
					return true;
			}
			return false;
		}
		
		//@desc		Collides a FlxCore object against the tilemap
		//@param	Core		The FlxCore you want to collide against
		override public boolean collide(FlxCore Core)
		{
			int c;
			int d;
			int i;
			int dd;
			ArrayList blocks = new ArrayList();
			
			//First make a list of all the blocks we'll use for collision
			int ix = (int) Math.floor((Core.x - x)/_tileWidth);
			int iy = (int) Math.floor((Core.y - y)/_tileHeight);
			int iw = (int) (Math.ceil(Core.width/_tileWidth)+1);
			int ih = (int) (Math.ceil(Core.height/_tileHeight)+1);
			for(int r = 0; r < ih; r++)
			{
				if((r < 0) || (r >= heightInTiles)) break;
				d = (iy+r)*widthInTiles+ix;
				for(c = 0; c < iw; c++)
				{
					if((c < 0) || (c >= widthInTiles)) break;
					dd = _data[d+c];
					if(dd >= collideIndex)
						blocks.push({x x+(ix+c)*_tileWidth,y y+(iy+r)*_tileHeight,dd data});
				}
			}
			
			//Then do all the X collisions
			int bl = blocks.size();
			boolean hx = false;
			for(i = 0; i < bl; i++)
			{
				_block.last.x = _block.x = blocks.get(i).x;
				_block.last.y = _block.y = blocks.get(i).y;
				if(_block.collideX(Core))
				{
					d = blocks.get(i).data;
					if(_callbacks.get(d) != null)
						_callbacks.get(d)(Core,_block.x/_tileWidth,_block.y/_tileHeight,d);
					hx = true;
				}
			}
			
			//Then do all the Y collisions
			boolean hy = false;
			for(i = 0; i < bl; i++)
			{
				_block.last.x = _block.x = blocks.get(i).x;
				_block.last.y = _block.y = blocks.get(i).y;
				if(_block.collideY(Core))
				{
					d = blocks.get(i).data;
					if(_callbacks.get(d) != null)
						_callbacks.get(d)(Core,_block.x/_tileWidth,_block.y/_tileHeight,d);
					hy = true;
				}
			}
			
			return hx || hy;
		}
		
		//@desc		Check the value of a particular tile
		//@param	X		The X coordinate of the tile (in tiles, not pixels)
		//@param	Y		The Y coordinate of the tile (in tiles, not pixels)
		public int getTile(int X,int Y)
		{
			return getTileByIndex(Y * widthInTiles + X);
		}
		
		//@desc		Get the value of a tile in the tilemap by index
		//@param	Index	The slot in the data array (Y * widthInTiles + X) where this tile is stored
		//@return	A int containing the value of the tile at this spot in the array
		public int getTileByIndex(int Index)
		{
			return _data.get(Index);
		}
		
		//@desc		Change the data and graphic of a tile in the tilemap
		//@param	X		The X coordinate of the tile (in tiles, not pixels)
		//@param	Y		The Y coordinate of the tile (in tiles, not pixels)
		//@param	Tile	The new integer data you wish to inject
		public void setTile(int X,int Y,int Tile)
		{
			setTileByIndex(Y * widthInTiles + X,Tile);
		}
		
		//@desc		Change the data and graphic of a tile in the tilemap
		//@param	Index	The slot in the data array (Y * widthInTiles + X) where this tile is stored
		//@param	Tile	The new integer data you wish to inject
		public void setTileByIndex(int Index,int Tile)
		{
			_data.get(Index) = Tile;
			
			if(auto.equals(OFF))
			{
				updateTile(Index);
				return;
			}
			//If this map is autotiled and it changes, locally update the arrangement
			int i;
			int r = int(Index/widthInTiles) - 1;
			int rl = r+3;
			int c = Index%widthInTiles - 1;
			int cl = c+3;
			for(r = r; r < rl; r++)
			{
				for(c = cl - 3; c < cl; c++)
				{
					if((r >= 0) && (r < heightInTiles) && (c >= 0) && (c < widthInTiles))
					{
						i = r*widthInTiles+c;
						autoTile(i);
						updateTile(i);
					}
				}
			}
		}
		
		//@desc		Bind a function Callback(FlxCore Core,int X,int Y,int Tile) to a range of tiles
		//@param	Tile		The tile to trigger the callback
		//@param	Callback	The function to trigger - parameters are (FlxCore Core,int X,int Y,int Tile)
		//@param	Range		If you want this callback to work for a bunch of different tiles, input the range here (default = 1)
		public void setCallback(int Tile,Function Callback,int Range=1)
		{
			if(Range <= 0) return;
			for(int i = Tile; i < Tile+Range; i++)
				_callbacks.get(i) = Callback;
		}
		
		//@desc		Call this function to lock the automatic camera to the map's edges
		public void follow()
		{
			FlxG.followBounds(x,y,width,height);
		}
		
		//@desc		Converts a one-dimensional array of tile data to a comma-separated string
		//@param	Data		An array full of integer tile references
		//@param	Width		The number of tiles in each row
		//@return	A comma-separated string containing the level data in a FlxTilemap-constructor-friendly format
		static public String arrayToCSV(ArrayList Data,int Width)
		{
			int r;
			int c;
			String csv;
			int Height = Data.length / Width;
			for(r = 0; r < Height; r++)
			{
				for(c = 0; c < Width; c++)
				{
					if(c == 0)
					{
						if(r == 0)
							csv += Data[0];
						else
							csv += "\n"+Data[r*Width];
					}
					else
						csv += ", "+Data[r*Width+c];
				}
			}
			return csv;
		}
		
		static public String pngToCSV(Class PNGFile,boolean Invert=false,int Scale=1)
		{
			//Import and scale image if necessary
			Bitmap layout;
			if(Scale <= 1)
				layout = new PNGFile;
			else
			{
				Bitmap tmp = new PNGFile;
				layout = new Bitmap(new BitmapData(tmp.width*Scale,tmp.height*Scale));
				Matrix mtx = new Matrix();
				mtx.scale(Scale,Scale);
				layout.bitmapData.draw(tmp,mtx);
			}
			BitmapData bd = layout.bitmapData;
			
			//Walk image and export pixel values
			int r;
			int c;
			int p;
			String csv;
			int w = layout.width;
			int h = layout.height;
			for(r = 0; r < h; r++)
			{
				for(c = 0; c < w; c++)
				{
					//Decide if this pixel/tile is solid (1) or not (0)
					p = bd.getPixel(c,r);
					if((Invert && (p > 0)) || (!Invert && (p == 0)))
						p = 1;
					else
						p = 0;
					
					//Write the result to the string
					if(c == 0)
					{
						if(r == 0)
							csv += p;
						else
							csv += "\n"+p;
					}
					else
						csv += ", "+p;
				}
			}
			return csv;
		}
		
		//@desc		An internal function used by the binary auto-tilers
		//@param	Index		The index of the tile you want to analyze
		protected void autoTile(int Index)
		{
			if(_data.get(Index) == 0) return;
			_data.get(Index) = 0;
			if((Index-widthInTiles < 0) || (_data[Index-widthInTiles] > 0)) 		//UP
				_data.get(Index) += 1;
			if((Index%widthInTiles >= widthInTiles-1) || (_data[Index+1] > 0)) 		//RIGHT
				_data.get(Index) += 2;
			if((Index+widthInTiles >= totalTiles) || (_data[Index+widthInTiles] > 0)) //DOWN
				_data.get(Index) += 4;
			if((Index%widthInTiles <= 0) || (_data[Index-1] > 0)) 					//LEFT
				_data.get(Index) += 8;
			if((auto.equals(ALT)) && (_data.get(Index) == 15))	//The alternate algo checks for interior corners
			{
				if((Index%widthInTiles > 0) && (Index+widthInTiles < totalTiles) && (_data[Index+widthInTiles-1] <= 0))
					_data.get(Index) = 1;		//BOTTOM LEFT OPEN
				if((Index%widthInTiles > 0) && (Index-widthInTiles >= 0) && (_data[Index-widthInTiles-1] <= 0))
					_data.get(Index) = 2;		//TOP LEFT OPEN
				if((Index%widthInTiles < widthInTiles) && (Index-widthInTiles >= 0) && (_data[Index-widthInTiles+1] <= 0))
					_data.get(Index) = 4;		//TOP RIGHT OPEN
				if((Index%widthInTiles < widthInTiles) &&(Index+widthInTiles < totalTiles) && (_data[Index+widthInTiles+1] <= 0))
					_data.get(Index) = 8; 		//BOTTOM RIGHT OPEN
			}
			_data.get(Index) += 1;
		}
		
		//@desc		Internal function used in setTileByIndex() and the constructor to update the map
		//@param	Index		The index of the tile you want to update
		protected void updateTile(int Index)
		{
			if(_data.get(Index) < drawIndex)
			{
				_rects.get(Index) = null;
				return;
			}
			int rx = (_data.get(Index)-startingIndex)*_tileWidth;
			int ry = 0;
			if(rx >= _pixels.width)
			{
				ry = int(rx/_pixels.width)*_tileHeight;
				rx %= _pixels.width;
			}
			_rects.get(Index) = (new Rectangle(rx,ry,_tileWidth,_tileHeight));
		}
	}*/
