import gameEngine.GameObject;
import gameEngine.IInput;
import gameEngine.Input;
import gameEngine.Layers;
import processing.core.PVector;

/**
 * Created by roscale on 5/6/17.
 */
public class Cell extends GameObject implements IInput
{
	public Plant plant = null;

	public Cell(float x, float y, float w, float h)
	{
		transform.setPosition(x, y).setZ(Layers.get("Default"));
		addComponent(Input.class).setSize(new PVector(w, h));
	}

	boolean isEmpty() { return plant == null; }

	void setPlant(PlantType type)
	{
		plant = PlantFactory.getPlant(type);
		plant.setCell(this);
		plant.transform.setPosition(this.transform.getPosition());
	}

	void clear() { plant = null; }

	@Override
	public void debug()
	{
		super.debug();
		System.out.println("Contains plant: " + plant);
	}
}
