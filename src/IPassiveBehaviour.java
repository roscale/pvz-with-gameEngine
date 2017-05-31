import gameEngine.Layers;
import gameEngine.components.SpriteRenderer;
import gameEngine.gameObjects.Particle;
import gameEngine.util.Counter;

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

class Explode implements IPassiveBehaviour
{
	private Plant plant;

	public Explode(Plant plant)
	{
		this.plant = plant;
		plant.getComponent(SpriteRenderer.class).setSprite(Globals.plantsData.get(plant.type).sprite);

		new Thread(() -> {
			while (!plant.getComponent(SpriteRenderer.class).lastFrame());

			new Particle(Globals.plantsData.get(plant.type).explodeSprite, Globals.commonFrameRate, false, 500).transform.setPosition(plant.transform.getPosition()).setZ(Layers.get("Sun"));

			plant.destroy();
		}).start();
	}

	@Override
	public void execute()
	{

	}
}