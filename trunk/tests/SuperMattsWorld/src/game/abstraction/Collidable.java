package game.abstraction;

/**
 * Represents an object that can encounter collisions with other objects of
 * the same type. Provides a concrete way to determine the outcome for each object.
 * 
 * @author Tony Greer
 * @since 2/25/2010
 */
public interface Collidable
{
	/**
	 * The result of a collision between two <code>Collidable</code> objects.
	 * In a game situation, this can either result in death, nothing, or stop.
	 */
	public static enum CollisionResult {DEATH, NOTHING, STOP}

	/**
	 * Determines the outcome of the collision for <tt>this</tt>
	 * instance with another object that can encounter a collision.
	 * @param c
	 */
	public abstract CollisionResult collide(Collidable c);
}