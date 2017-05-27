import gameEngine.InputManager;
import gameEngine.World;
import processing.core.PApplet;
import processing.event.MouseEvent;

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

		Shop.instance().createItem(PlantType.SUNFLOWER);
		Shop.instance().createItem(PlantType.SNOWPEA);
		Shop.instance().createItem(PlantType.PEASHOOTER);
		Shop.instance().createItem(PlantType.WALLNUT);
		Shop.instance().createItem(PlantType.PEASHOOTER);
		Shop.instance().createItem(PlantType.PEASHOOTER);

		ZombieFactory.getZombie(ZombieType.REGULAR).transform.setPosition(1000, 10);
		ZombieFactory.getZombie(ZombieType.FLAG).transform.setPosition(1000, 110);
		ZombieFactory.getZombie(ZombieType.POLEVAULTING).transform.setPosition(1000, 210);
		ZombieFactory.getZombie(ZombieType.FLAG).transform.setPosition(1000, 310);
		ZombieFactory.getZombie(ZombieType.REGULAR).transform.setPosition(800, 410);
		ZombieFactory.getZombie(ZombieType.REGULAR).transform.setPosition(800, 410);
		ZombieFactory.getZombie(ZombieType.REGULAR).transform.setPosition(800, 410);
		ZombieFactory.getZombie(ZombieType.REGULAR).transform.setPosition(800, 410);
		ZombieFactory.getZombie(ZombieType.REGULAR).transform.setPosition(800, 410);
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
