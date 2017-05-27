import gameEngine.*;
import processing.event.MouseEvent;

/**
 * Created by roscale on 5/27/17.
 */
public class Sun extends GameObject implements IInput
{
	public Sun()
	{
		transform.setZ(Layers.get("Sun"));

		addComponent(SpriteRenderer.class).setSprite(Globals.sunSprite);
		addComponent(Input.class).setSize(getComponent(SpriteRenderer.class).getSpriteSize());
	}

	@Override
	public void mouseClicked(MouseEvent event)
	{
		Shop.instance().invest(100);
		destroy();
	}
}
