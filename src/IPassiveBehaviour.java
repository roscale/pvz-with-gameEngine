import gameEngine.Counter;

/**
 * Created by roscale on 5/18/17.
 */
public interface IPassiveBehaviour
{
	void execute();
}

class NoBehaviour implements IPassiveBehaviour
{
	@Override
	public void execute() {}
}

class ThrowSun implements IPassiveBehaviour
{
	private Plant plant;
	private Counter counter;

	public ThrowSun(Plant plant)
	{
		this.plant = plant;
		this.counter = new Counter(1440);
	}

	@Override
	public void execute()
	{
		counter.increment();
		if (counter.last())
			new Sun().transform.setPosition(plant.transform.getPosition());
	}
}
