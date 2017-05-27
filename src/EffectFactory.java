/**
 * Created by roscale on 9/05/17.
 */
public abstract class EffectFactory
{
    Effect effect;

    public static Effect getEffect(EffectType type)
    {
        switch (type)
        {
            case SLOWDOWN:
                return new SlowDown();
        }

        return null;
    }
}
