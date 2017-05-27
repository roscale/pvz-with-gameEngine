import gameEngine.*;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PVector;
import processing.event.MouseEvent;

import java.util.ArrayList;

/**
 * Created by roscale on 5/6/17.
 */
public class Shop extends GameObject implements IDrawable
{
	private static Shop instance;
	public static Shop instance()
	{
		if (instance == null)
			instance = new Shop();
		return instance;
	}

	//////////////////////////////////////////////////////////////

	private ArrayList<Card> cards = new ArrayList<>();
	private int balance = 999;

	private float cardMargin = 0;
	private PVector balancePos = new PVector(150, 10);

	private Shovel shovel = new Shovel(300, 10);

	public Shop()
	{
		addComponent(CustomDraw.class);
		transform.setZ(Layers.get("UI"));

		transform.setPosition(0, 0);
	}

	public int getBalance() { return balance; }

	public void createItem(PlantType type)
	{
		cards.add(new Card(type));
		reposition();
	}

	public boolean buyItem(Card card)
	{
		if (card.getCost() <= balance)
		{
			balance -= card.getCost();
			return true;
		}
		return false;
	}

	public void invest(int value) { balance += value; }

	private void reposition()
	{
		for (int i = 0; i < cards.size(); i++)
		{
			cards.get(i).transform.setPosition(PVector.add(this.transform.getPosition(), new PVector(0, i * (60 + cardMargin))));
			cards.get(i).setOrigin(cards.get(i).transform.getPosition());
		}
	}

	@Override
	public void draw()
	{
		World.p.image(Globals.sunBg, balancePos.x, balancePos.y);

		World.p.fill(0);
		World.p.textAlign(PConstants.CENTER, PConstants.CENTER);
		World.p.text(balance, balancePos.x+75, balancePos.y+15);
	}

	/**
	 * Created by roscale on 5/6/17.
	 */
	private static class Card extends GameObject implements IDrawable, IInput
	{
		private PImage image;

		private PVector origin = new PVector();
		private PlantType plantType;

		private int cost;

		private boolean available = false;

		private Card(PlantType plantType)
		{
			transform.setZ(Layers.get("UI"));

			this.plantType = plantType;
			this.cost = Globals.plantsData.get(plantType).cost;
			this.image = Globals.plantsData.get(plantType).cardImage;

			addComponent(CustomDraw.class);
			addComponent(Input.class).setSize(new PVector(image.width, image.height));
		}

		public void setOrigin(PVector origin) { this.origin.set(origin); }
		public void moveToOrigin() { transform.setPosition(origin); transform.setZ(transform.getPosition().z - 0.1f); }

		public int getCost() { return cost; }

		@Override
		public void draw()
		{
			World.p.image(image, transform.getPosition().x, transform.getPosition().y);

			if (Shop.instance().getBalance() < cost) // Not enough money
			{
				World.p.fill(255, 0, 0);
				available = false;
			}
			else
			{
				World.p.fill(0);
				available = true;
			}

			World.p.textAlign(PConstants.LEFT, PConstants.TOP);
			World.p.textSize(18);
			World.p.text(cost, transform.getPosition().x+65, transform.getPosition().y+40);
		}

		@Override
		public void mouseDragged(MouseEvent event)
		{
			if (available)
			{
				transform.getPosition().add(Helper.getRelativeMousePos(World.p));
				transform.setZ(Layers.get("UI") + 0.1f);
			}
		}

		public void mouseReleased(MouseEvent event)
		{
			ArrayList<Input> inputs = InputManager.instance().getInputsAt(new PVector(event.getX(), event.getY()));
			for (int i = 0; i < inputs.size(); i++)
				if (i != inputs.size()-1 && inputs.get(i) == this.getComponent(Input.class))
					// Get the object under this one
					if (inputs.get(i + 1).gameObject.getClass() == Cell.class)
					{
						Cell cell = (Cell) inputs.get(i + 1).gameObject;
						if (cell.isEmpty() && instance().buyItem(this))
							cell.setPlant(plantType);
					}
			moveToOrigin();
		}
	}

	/**
	 * Created by roscale on 5/18/17.
	 */
	public static class Shovel extends GameObject implements IInput, IDrawable
	{
		private PVector origin = new PVector();

		public Shovel(float x, float y)
		{
			transform.setPosition(x, y);
			transform.setZ(Layers.get("UI"));
			setOrigin(new PVector(x, y));

			addComponent(CustomDraw.class);
			addComponent(Input.class).setSize(new PVector(Globals.shovelBg.width, Globals.shovelBg.height));
		}

		public void setOrigin(PVector origin) { this.origin.set(origin); }
		public void moveToOrigin() { transform.setPosition(origin); }

		@Override
		public void mouseDragged(MouseEvent event) { transform.getPosition().add(Helper.getRelativeMousePos(World.p)); }

		public void mouseReleased(MouseEvent event)
		{
			ArrayList<Input> inputs = InputManager.instance().getInputsAt(new PVector(event.getX(), event.getY()));
			for (int i = 0; i < inputs.size(); i++)
				if (i != inputs.size()-1 && inputs.get(i) == this.getComponent(Input.class))
				{
					System.out.println(inputs.get(i + 1).gameObject.getClass());

					// Get the object under this one
					if (inputs.get(i + 1).gameObject.getClass() == Cell.class)
					{
						Cell cell = (Cell) inputs.get(i + 1).gameObject;
						if (!cell.isEmpty())
						{
							instance().invest(Globals.plantsData.get(cell.plant.type).cost / 2);
							cell.plant.destroy();
						}
					}
				}
			moveToOrigin();
		}

		@Override
		public void draw()
		{
			World.p.image(Globals.shovelBg, transform.getPosition().x, transform.getPosition().y);
		}
	}
}