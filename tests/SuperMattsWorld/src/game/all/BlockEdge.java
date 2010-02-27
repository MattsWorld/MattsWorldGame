package game.all;

import game.abstraction.Collidable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents an edge of the smallest logical display unit in the game. The different
 * edge properties are based on the fact that the main character interacts with other
 * objects and has no reflection or property other than the edge belongs to the player.
 * 
 * @author Tony Greer
 * @since 2/25/2010
 */
public final class BlockEdge implements Collidable
{
	/**
	 * The edge property determines how to handle collision detection in the game based
	 * on what types of edges are interacting.
	 */
	public static enum EdgeProperty {KILL_PLAYER, HURT_PLAYER, KILL_BLOCK, HURT_BLOCK, NONE, NONE_GROUND, MATT, MATT_SUPER}

	public static final BlockEdge BLOCK_EDGE = new BlockEdge(EdgeProperty.NONE);
	public static final BlockEdge BLOCK_EDGE_MATT = new BlockEdge(EdgeProperty.MATT);
	public static final BlockEdge BLOCK_EDGE_LEVEL_EDGE = new BlockEdge(EdgeProperty.NONE_GROUND);
	public static final BlockEdge BLOCK_EDGE_HURT_PLAYER = new BlockEdge(EdgeProperty.HURT_PLAYER);
	public static final BlockEdge BLOCK_EDGE_KILL_PLAYER = new BlockEdge(EdgeProperty.KILL_PLAYER);
	public static final BlockEdge BLOCK_EDGE_HURT_THIS = new BlockEdge(EdgeProperty.HURT_BLOCK);
	public static final BlockEdge BLOCK_EDGE_KILL_THIS = new BlockEdge(EdgeProperty.KILL_BLOCK);

	private static final Map<Integer, CollisionResult> mapCollisionResults;
	
	static
	{
		HashMap<Integer, CollisionResult> temp = new HashMap<Integer, CollisionResult>();
		/*
		 * TODO create a static cached map of results for super quick
		 * access.
		 */
		temp.put(new Integer(BLOCK_EDGE.hashCode() + BLOCK_EDGE_HURT_PLAYER.hashCode()), CollisionResult.NOTHING);
		temp.put(new Integer(BLOCK_EDGE.hashCode() + BLOCK_EDGE_HURT_THIS.hashCode()), CollisionResult.STOP);
		temp.put(new Integer(BLOCK_EDGE.hashCode() + BLOCK_EDGE_KILL_PLAYER.hashCode()), CollisionResult.STOP);
		temp.put(new Integer(BLOCK_EDGE.hashCode() + BLOCK_EDGE_HURT_THIS.hashCode()), CollisionResult.STOP);
		temp.put(new Integer(BLOCK_EDGE.hashCode() + BLOCK_EDGE_HURT_THIS.hashCode()), CollisionResult.STOP);
		temp.put(new Integer(BLOCK_EDGE.hashCode() + BLOCK_EDGE_HURT_THIS.hashCode()), CollisionResult.STOP);
		temp.put(new Integer(BLOCK_EDGE.hashCode() + BLOCK_EDGE_HURT_THIS.hashCode()), CollisionResult.STOP);
		temp.put(new Integer(BLOCK_EDGE.hashCode() + BLOCK_EDGE_HURT_THIS.hashCode()), CollisionResult.STOP);
		temp.put(new Integer(BLOCK_EDGE.hashCode() + BLOCK_EDGE_HURT_THIS.hashCode()), CollisionResult.STOP);
		temp.put(new Integer(BLOCK_EDGE.hashCode() + BLOCK_EDGE_HURT_THIS.hashCode()), CollisionResult.STOP);
		temp.put(new Integer(BLOCK_EDGE.hashCode() + BLOCK_EDGE_HURT_THIS.hashCode()), CollisionResult.STOP);
		temp.put(new Integer(BLOCK_EDGE.hashCode() + BLOCK_EDGE_HURT_THIS.hashCode()), CollisionResult.STOP);
		
		mapCollisionResults = Collections.unmodifiableMap(temp);
	}

	/* One variable for property */
	private EdgeProperty property;

	/* Singleton class */
	private BlockEdge(EdgeProperty property)
	{
		this.property = property;
	}

	/*
	 * (non-Javadoc)
	 * @see game.abstraction.Collidable#collide(game.abstraction.Collidable)
	 */
	@Override
	public CollisionResult collide(Collidable c)
	{
		return null;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return this.property.hashCode();
	}

	public EdgeProperty getProperty()
	{
		return property;
	}
}