import gameEngine.components.Collider;
import gameEngine.components.Physics;
import gameEngine.components.SpriteRenderer;
import gameEngine.gameObjects.Particle;
import gameEngine.util.Counter;
import gameEngine.util.Sprite;
import processing.core.PVector;

import java.util.ArrayList;

public interface IZombieState
{
	default void handle() {}
	default void enter(Plant plant) {}
	default void stay(Plant plant) {}
	default void exit(Plant plant) {}
	default void die() {}
}

class RunState implements IZombieState
{
	private Zombie zombie;
	public RunState(Zombie zombie)
	{
		this.zombie = zombie;

		zombie.getComponent(SpriteRenderer.class).setSprite(Globals.zombiesData.get(zombie.type).runSprite);
		zombie.getComponent(Physics.class).velocity.set(zombie.speed-1, 0);
		zombie.getComponent(Collider.class).setOffset(new PVector(50, 70));
	}

	@Override
	public void enter(Plant plant)
	{
		zombie.setState(new JumpState(zombie));
	}

	@Override
	public void die()
	{
		// zombie.destroy();

		new DieAnimation(zombie,
				Globals.zombiesData.get(zombie.type).lostHeadRunSprite,
				Globals.zombiesData.get(zombie.type).headSprite,
				Globals.zombiesData.get(zombie.type).bodySprite);
	}
}

class JumpState implements IZombieState, Runnable
{
	private Zombie zombie;
	public JumpState(Zombie zombie)
	{
		this.zombie = zombie;

		new Thread(this).start();
	}

	@Override
	public void run()
	{
		zombie.getComponent(Collider.class).disable();

		zombie.getComponent(Physics.class).velocity.set(0, 0);
		zombie.getComponent(SpriteRenderer.class).setSprite(Globals.zombiesData.get(zombie.type).jump1Sprite);

		// Waiting
		while (!zombie.getComponent(SpriteRenderer.class).lastFrame());

//		zombie.transform.getPosition().add(-120f, 0);
		zombie.transform.getPosition().add(-155f, 0);
		zombie.getComponent(SpriteRenderer.class).setSprite(Globals.zombiesData.get(zombie.type).jump2Sprite);

		// Waiting
		while (!zombie.getComponent(SpriteRenderer.class).lastFrame());

		zombie.getComponent(Collider.class).setOffset(new PVector(90, 70));

		zombie.getComponent(Collider.class).enable();
		zombie.setState(new WalkState(zombie));
	}
}

class WalkState implements IZombieState
{
	private Zombie zombie;
	public WalkState(Zombie zombie)
	{
		this.zombie = zombie;

		zombie.getComponent(SpriteRenderer.class).setSprite(Globals.zombiesData.get(zombie.type).walkSprite);
		zombie.getComponent(Physics.class).velocity.set(zombie.speed, 0);
	}

	@Override
	public void stay(Plant plant)
	{
		AttackState attackState = new AttackState(zombie);
		attackState.enter(plant);
		zombie.setState(attackState);
	}

	@Override
	public void die()
	{
		// zombie.destroy();

		new DieAnimation(zombie,
				Globals.zombiesData.get(zombie.type).lostHeadWalkSprite,
				Globals.zombiesData.get(zombie.type).headSprite,
				Globals.zombiesData.get(zombie.type).bodySprite);
	}
}

class AttackState implements IZombieState
{
	private ArrayList<Plant> inCollision = new ArrayList<>();
	private Counter counter = new Counter(60);

	private Zombie zombie;
	public AttackState(Zombie zombie)
	{
		this.zombie = zombie;

		zombie.getComponent(SpriteRenderer.class).setSprite(Globals.zombiesData.get(zombie.type).attackSprite);
		zombie.getComponent(Physics.class).velocity.set(0, 0);
	}

	@Override
	public void enter(Plant plant)
	{
		inCollision.add(plant);
	}

	@Override
	public void stay(Plant plant)
	{
		counter.increment();

		if (counter.last())
			inCollision.get(0).hit(1);
	}

	@Override
	public void exit(Plant plant)
	{
		inCollision.remove(plant);

		if (inCollision.isEmpty())
			zombie.setState(new WalkState(zombie));
	}

	@Override
	public void die()
	{
		System.out.println("DIE MTFK");

		// zombie.destroy();

		new DieAnimation(zombie,
				Globals.zombiesData.get(zombie.type).lostHeadAttackSprite,
				Globals.zombiesData.get(zombie.type).headSprite,
				Globals.zombiesData.get(zombie.type).bodySprite);
	}
}

class DieAnimation implements Runnable
{
	private Zombie zombie;
	private Sprite lostHead;
	private Sprite head;
	private Sprite body;

	public DieAnimation(Zombie zombie, Sprite lostHead, Sprite head, Sprite body)
	{
		this.zombie = zombie;
		this.lostHead = lostHead;
		this.head = head;
		this.body = body;

		new Thread(this).start();
	}

	@Override
	public void run()
	{
		SpriteRenderer sr = zombie.getComponent(SpriteRenderer.class);

		zombie.getComponent(SpriteRenderer.class).setSprite(lostHead);

		Particle p = new Particle(head, Globals.commonFrameRate, false, 10000);
		p.transform.setPosition(PVector.add(sr.getPosition(), new PVector(50, 0)));
		p.transform.setZ(zombie.transform.getPosition().z);

		try
		{
			Thread.sleep(2000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		p = new Particle(body, Globals.commonFrameRate, false, 10000);
		p.transform.setPosition(sr.getPosition());
		p.transform.setZ(zombie.transform.getPosition().z);

		zombie.destroy();
	}
}