import gameEngine.Collider;
import gameEngine.SpriteRenderer;

import java.lang.reflect.InvocationTargetException;

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
		}

		return plant;
	}
}


//		try
//		{
//			if (Globals.plantsData.get(type).shootingBehaviourClass != null)
//				plant.setShootingBehaviour((IShootingBehaviour) Globals.plantsData.get(type).shootingBehaviourClass.getConstructor(Plant.class, PeaType.class).newInstance(plant, Globals.plantsData.get(type).peaType));
//		}
//		catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e)
//		{}
//
//		try
//		{
//			if (Globals.plantsData.get(type).passiveBehaviourClass != null)
//				plant.setPassiveBehaviour((IPassiveBehaviour) Globals.plantsData.get(type).passiveBehaviourClass.getConstructor(Plant.class).newInstance(plant));
//		}
//		catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e)
//		{}
