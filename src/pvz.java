import gameEngine.World;
import gameEngine.components.Physics;
import gameEngine.managers.InputManager;
import processing.core.PApplet;
import processing.event.MouseEvent;

import java.util.Random;

/**
 * Created by roscale on 4/30/17.
 */
public class pvz extends PApplet
{
	public void settings() { size(1116, 600); }

	Grid grid;

	public void setup()
	{
		World.setPApplet(this);

//		World.p.sprite(World.p.loadImage(Globals.spritesPath + "Background/loading.png"), 0, 0, 1116, 600);

		Globals.loadData();

		grid = new Grid(250, 80, 9, 5, 81, 97);

		System.out.println();

		Shop.instance().createCard(PlantType.SUNFLOWER);
		Shop.instance().createCard(PlantType.PEASHOOTER);
		Shop.instance().createCard(PlantType.SNOWPEA);
		Shop.instance().createCard(PlantType.WALLNUT);
		Shop.instance().createCard(PlantType.CHERRYBOMB);

		ZombieFactory.getZombie(ZombieType.REGULAR).transform.setPosition(1000, 10);
		ZombieFactory.getZombie(ZombieType.FLAG).transform.setPosition(1000, 110);
		ZombieFactory.getZombie(ZombieType.POLEVAULTING).transform.setPosition(1000, 210);
		ZombieFactory.getZombie(ZombieType.FLAG).transform.setPosition(1000, 310);
		ZombieFactory.getZombie(ZombieType.REGULAR).transform.setPosition(1000, 410);

		// Falling sun
		new Thread(() -> {
			Random r = new Random();

			while (true)
			{
				Sun sun = (Sun) new Sun().transform.setPosition(r.nextInt(1116), -50).gameObject;
				sun.getComponent(Physics.class).velocity.set(0, 1f);

				try { Thread.sleep(10000); }
				catch (InterruptedException e) { e.printStackTrace(); }
			}
		}).start();
	}

	public void draw()
	{
		World.p.image(Globals.bg, 0, 0);

		World.update();
		World.show();
//		World.debug();
	}

	@Override public void mouseMoved(MouseEvent event) { InputManager.instance().mouseMoved(event); }
	@Override public void mousePressed(MouseEvent event) { InputManager.instance().mousePressed(event); }
	@Override public void mouseClicked(MouseEvent event) { InputManager.instance().mouseClicked(event); }
	@Override public void mouseDragged(MouseEvent event) { InputManager.instance().mouseDragged(event); }
	@Override public void mouseReleased(MouseEvent event) { InputManager.instance().mouseReleased(event); }
	@Override public void mouseWheel(MouseEvent event) { InputManager.instance().mouseWheel(event); }
}
