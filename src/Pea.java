import gameEngine.*;
import processing.core.PVector;

/**
 * Created by roscale on 5/1/17.
 */
public class Pea extends Living
{
	private PeaType type;

	public float damage;
	public float speed;
	public EffectType effectType = null;

	public Pea(PeaType type)
	{
		transform.setZ(2);

		this.type = type;
		this.hp = Globals.peasData.get(type).hp;
		this.damage = Globals.peasData.get(type).damage;
		this.speed = 5;

		this.effectType = Globals.peasData.get(type).effectType;

		getComponent(SpriteRenderer.class).setSprite(Globals.peasData.get(type).sprite);
		getComponent(Collider.class).setSize(getComponent(SpriteRenderer.class).getSpriteSize());

		addComponent(Physics.class).velocity.set(new PVector(speed, 0));
	}

	@Override
	public void onCollisionEnterWith(GameObject other)
	{
		if (Zombie.class.isAssignableFrom(other.getClass()))
		{
			Zombie zombie = (Zombie) other;
			zombie.hit(damage);
			if (effectType != null) zombie.applyEffect(EffectFactory.getEffect(effectType));

			hit(1);
		}
	}
}
