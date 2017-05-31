import gameEngine.components.Physics;
import gameEngine.components.SpriteRenderer;
import processing.core.PVector;

/**
 * Created by roscale on 9/05/17.
 */

public abstract class Effect extends Thread
{
    public EffectType type;
    protected Zombie target;
    protected long duration;

    private boolean extend = false;
    private long extendTime;

    public void setTarget(Zombie target) { this.target = target; }

    public void hold()
    {
        long remainingTime = duration;

        while (remainingTime > 0)
        {
            try { Thread.sleep(remainingTime); }
            catch (InterruptedException e) { e.printStackTrace(); }

            long endSleepTime = System.currentTimeMillis();

            if (extend)
            {
                remainingTime = duration - (endSleepTime - extendTime);
                extend = false;
            }
            else
                 remainingTime -= duration;
        }
    }

    public void extend() {
        extend = true;
        extendTime = System.currentTimeMillis();
    }

    @Override
    public boolean equals(Object o)
    {
        return o instanceof Effect && this.type == ((Effect) o).type;
    }
}

class SlowDown extends Effect
{
    public SlowDown()
    {
        type = EffectType.SLOWDOWN;
        duration = 5000;
    }

    @Override
    public void run()
    {
        PVector velocity = target.getComponent(Physics.class).velocity;
        SpriteRenderer sr = target.getComponent(SpriteRenderer.class);

        target.speed /= 2;
        velocity.div(2f);
        sr.setFrameRate(sr.getFrameRate()/2);

        hold();

        target.speed *= 2;
        velocity.mult(2f);
        sr.setFrameRate(sr.getFrameRate()*2);
        target.removeEffect(this);
    }
}
