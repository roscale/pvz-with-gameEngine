import gameEngine.*;

/**
 * Created by roscale on 4/30/17.
 */
public abstract class Living extends GameObject implements ICollider, IInput
{
	public float hp;

	public boolean dead = false;

	public Living()
	{
		addComponent(SpriteRenderer.class);
		addComponent(Collider.class);

		getComponent(SpriteRenderer.class).setFrameRate(Globals.commonFrameRate);
//		System.out.println(World.p.frameRate);

		// Debugging
//		addComponent(Input.class);
	}

	public boolean alive() { return hp > 0; }

	public void hit(float damage)
	{
		hp -= damage;
		if (!alive() && !dead)
		{
			dead = true;
			die();
		}
	}

	public void die() { destroy(); }

//	@Override
//	public void mouseDragged(MouseEvent event)
//	{
//		transform.getPosition().add(Helper.getRelativeMousePos(World.p));
//	}
}
