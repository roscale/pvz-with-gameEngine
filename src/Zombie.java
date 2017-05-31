import gameEngine.GameObject;
import gameEngine.Layers;
import gameEngine.components.Collider;
import gameEngine.components.Physics;
import processing.core.PVector;

import java.util.ArrayList;

/**
 * Created by roscale on 5/1/17.
 */
public class Zombie extends Living
{
	public ZombieType type;
	public float attackDamage;
	public float speed;

	public IZombieState state;

	private ArrayList<Effect> appliedEffects = new ArrayList<>();

	public Zombie(ZombieType type, float attackDamage, float speed)
	{
		transform.setZ(Layers.get("Zombie"));

		addComponent(Physics.class);

		getComponent(Collider.class).setOffset(new PVector(90, 70));
		getComponent(Collider.class).setSize(new PVector(40, 70));

		this.type = type;
		this.hp = Globals.zombiesData.get(type).hp;
		this.attackDamage = attackDamage;
		this.speed = speed;
	}

	public void setState(IZombieState state) { this.state = state; }

	public void applyEffect(Effect effect)
	{
		int iEffect;
		if ((iEffect = appliedEffects.indexOf(effect)) != -1)
			appliedEffects.get(iEffect).extend();
		else
		{
			appliedEffects.add(effect);
			effect.setTarget(this);
			effect.start();
		}
	}

	public void removeEffect(Effect effect) { appliedEffects.remove(effect); }

	@Override
	public void update()
	{
		if (transform.getPosition().x < 0)
			destroy();

		state.handle();
	}

	@Override
	public void die()
	{
		state.die();
	}

	@Override
	public void onCollisionEnterWith(GameObject other)
	{
		if (alive() && other instanceof Plant)
			state.enter((Plant) other);
	}

	@Override
	public void onCollisionStayWith(GameObject other)
	{
		if (alive() && other instanceof Plant)
			state.stay((Plant) other);
	}

	@Override
	public void onCollisionExitWith(GameObject other)
	{
		if (alive() && other instanceof Plant)
			state.exit((Plant) other);
	}

	@Override
	public void debug() // TODO Implement custom debug
	{
		System.out.println("HP: " + hp);
		super.debug();
	}
}
