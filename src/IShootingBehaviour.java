import gameEngine.*;
import processing.core.PVector;

/**
 * Created by roscale on 5/15/17.
 */
public interface IShootingBehaviour
{
	void execute();

	default boolean zombieForward(Plant plant)
	{
		Raycast.stepAmount = 40;

		PVector center = Helper.centerOf(plant.transform.getPosition(), plant.getComponent(SpriteRenderer.class).getSpriteSize());

		return !Raycast.cast(center, new PVector(1, 0), 18, new ZombieRaycastCondition()).isEmpty();
	}

	default void shoot(Plant plant, PeaType peaType)
	{
		Pea pea = PeaFactory.getPea(peaType);
		pea.transform.setPosition(plant.transform.getPosition());
	}
}

class ZombieRaycastCondition implements IRaycastCondition
{
	@Override
	public boolean eval(GameObject obj)
	{
		return Zombie.class.isAssignableFrom(obj.getClass());
	}
}

class NoShooting implements IShootingBehaviour
{
	@Override
	public void execute() {}
}

class NormalShooting implements IShootingBehaviour
{
	protected Plant plant;
	protected PeaType peaType;
	protected Counter counter = new Counter(90);

	public NormalShooting(Plant plant, PeaType peaType) { this.plant = plant; this.peaType = peaType; }

	@Override
	public void execute()
	{
		if (zombieForward(plant))
		{
			counter.increment();
			if (counter.last())
				shoot(plant, peaType);
		}
	}
}

class DoubleShooting extends NormalShooting
{
	public DoubleShooting(Plant plant, PeaType peaType) { super(plant, peaType); }

	@Override
	public void execute()
	{
		if (zombieForward(plant))
		{
			counter.increment();
			if (counter.last() || counter.value() == 20)
				shoot(plant, peaType);
		}
	}
}