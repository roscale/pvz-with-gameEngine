import gameEngine.Collider;
import gameEngine.SpriteRenderer;
import processing.core.PVector;

/**
 * Created by roscale on 5/8/17.
 */
public abstract class ZombieFactory
{
	public static Zombie getZombie(ZombieType type)
	{
		Zombie zombie = null;

		zombie = new Zombie(type, 1, -0.5f);

		switch (type)
		{
			case REGULAR:
			case FLAG:
			case CONEHEAD:
			case BUCKETHEAD:
				zombie.setState(new WalkState(zombie)); break;
			case POLEVAULTING:
				zombie.getComponent(SpriteRenderer.class).setRelativePosition(new PVector(-130, -60)); // sweet spot
//				zombie.getComponent(SpriteRenderer.class).setRelativePosition(new PVector(-100, -60));
				// zombie.getComponent(Collider.class).setRelativePosition(new PVector(210, 70));
				zombie.setState(new RunState(zombie)); break;
			default:
				throw new RuntimeException("Type " + type.toString() + " doesn't exist");
		}

		return zombie;
	}
}

