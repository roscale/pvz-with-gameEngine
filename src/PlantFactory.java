/**
 * Created by roscale on 5/6/17.
 */
public abstract class PlantFactory
{
	public static Plant getPlant(PlantType type)
	{
		Plant plant = new Plant(type);

		switch (type)
		{
			case SUNFLOWER:
				plant.setPassiveBehaviour(new ThrowSun(plant)); break;
			case PEASHOOTER:
				plant.setShootingBehaviour(new NormalShooting(plant, PeaType.REGULAR)); break;
			case SNOWPEA:
				plant.setShootingBehaviour(new NormalShooting(plant, PeaType.ICE)); break;
			case WALLNUT:
				break;
			case CHERRYBOMB:
				plant.setPassiveBehaviour(new Explode(plant)); break;
		}

		return plant;
	}
}