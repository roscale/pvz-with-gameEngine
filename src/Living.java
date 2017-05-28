import gameEngine.*;

/**
 * Created by roscale on 4/30/17.
 */
public abstract class Living extends GameObject implements ICollider
{
	public float hp;
	public boolean dead = false;

	public Living()
	{
		addComponent(Collider.class);
		addComponent(SpriteRenderer.class).setFrameRate(Globals.commonFrameRate);
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
}
