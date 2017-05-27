import gameEngine.*;
import processing.event.MouseEvent;

import java.util.ArrayList;

/**
 * Created by roscale on 5/1/17.
 */
public class Plant extends Living
{
	public PlantType type;
	private Cell cell;

	private IPassiveBehaviour passiveBehaviour;
	private IShootingBehaviour shootingBehaviour;

	public Plant(PlantType type)
	{
		transform.setZ(Layers.get("Plant"));

		this.type = type;
		this.hp = Globals.plantsData.get(type).hp;

		getComponent(SpriteRenderer.class).setSprite(Globals.plantsData.get(type).sprite);
		getComponent(Collider.class).setSize(getComponent(SpriteRenderer.class).getSpriteSize());

		passiveBehaviour = new NoBehaviour();
		shootingBehaviour = new NoShooting();
	}

	public void setCell(Cell cell) { this.cell = cell; }

	public void setShootingBehaviour(IShootingBehaviour shootingBehaviour) { this.shootingBehaviour = shootingBehaviour; }
	public void setPassiveBehaviour(IPassiveBehaviour passiveBehaviour) { this.passiveBehaviour = passiveBehaviour; }

	@Override
	public void update()
	{
		passiveBehaviour.execute();
		shootingBehaviour.execute();
	}

	@Override
	public void hit(float damage)
	{
		super.hit(damage);

		// Set damage sprites
		ArrayList<Sprite> damagedSprites = Globals.plantsData.get(type).damagedSprites; // Shortcut reference
		float maxHp = Globals.plantsData.get(type).hp;

		for (int i = damagedSprites.size()-1; i >= 0; i--)
		{
			float segment = maxHp / (damagedSprites.size() + 1);
			float hpThreshold = maxHp - segment * (i + 1);

			if (hp <= hpThreshold)
			{
				getComponent(SpriteRenderer.class).setSprite(damagedSprites.get(i));
				System.out.println("HP: " + hp);
				break;
			}
		}
	}

	@Override
	public void onDestroy() { if (cell != null) cell.clear(); }

	@Override
	public void mouseClicked(MouseEvent event)
	{
//		Pea pea = PeaFactory.getPea(peaType);
//		pea.transform.setPosition(transform.getPosition());
		destroy();
	}
}
