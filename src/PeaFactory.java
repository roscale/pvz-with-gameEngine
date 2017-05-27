/**
 * Created by roscale on 5/8/17.
 */
public abstract class PeaFactory
{
	public static Pea getPea(PeaType type)
	{
		Pea pea = new Pea(type);
		return pea;
	}
}
