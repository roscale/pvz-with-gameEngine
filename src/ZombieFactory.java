import gameEngine.components.SpriteRenderer;
import processing.core.PVector;

/**
 * Created by roscale on 5/8/17.
 */
public abstract class ZombieFactory
{
	public static Zombie getZombie(ZombieType type)
	{
		Zombie zombie = new Zombie(type, 1, -0.4f);

		switch (type)
		{
			case REGULAR:
			case FLAG:
			case CONEHEAD:
			case BUCKETHEAD:
				zombie.setState(new WalkState(zombie)); break;
			case POLEVAULTING:
				zombie.getComponent(SpriteRenderer.class).setOffset(new PVector(-130, -60)); // sweet spot
				zombie.setState(new RunState(zombie)); break;
			default:
				throw new RuntimeException("Type " + type.toString() + " doesn't exist");
		}

		return zombie;
	}
}

