import gameEngine.Layers;
import gameEngine.Sprite;
import gameEngine.World;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.data.JSONArray;
import processing.data.JSONObject;
import processing.event.MouseEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static processing.core.PApplet.loadJSONObject;

class PlantData
{
	public float hp;
	public int cost;

	public PImage cardImage;
	public Sprite sprite;
	public ArrayList<Sprite> damagedSprites = new ArrayList<>();
}

class ZombieData
{
	public float hp;
	public Sprite runSprite;
	public Sprite jump1Sprite;
	public Sprite jump2Sprite;
	public Sprite walkSprite;
	public Sprite attackSprite;

	public Sprite bodySprite;
	public Sprite headSprite;
	public Sprite lostHeadRunSprite;
	public Sprite lostHeadAttackSprite;
	public Sprite lostHeadWalkSprite;
}

class PeaData
{
	public float hp;
	public float damage;
	public EffectType effectType;
	public Sprite sprite;
}

public class Globals {
	//
	// Helper functions
	//

	public static PVector getMousePos(MouseEvent event)
	{ return new PVector(event.getX(), event.getY()); }

	public static PVector getMousePos(PApplet p)
	{ return new PVector(p.mouseX, p.mouseY); }

	public static PVector getRelativeMousePos(PApplet p)
	{ return new PVector(p.mouseX - p.pmouseX, p.mouseY - p.pmouseY); }

	/*
	/	REFACTORED
	*/

	public static String spritesPath = "resources/";
	public static float commonFrameRate = 15f;

	public static PImage bg;
	public static PImage sunBg;
	public static PImage shovelBg;
	public static Sprite sunSprite;

	private static Sprite getSpriteOrNullFrom(String path, String ext) { return (path != null) ? new Sprite(path, ext) : null; }

	public static Map<PlantType, PlantData> plantsData = new HashMap<>();
	public static Map<ZombieType, ZombieData> zombiesData = new HashMap<>();
	public static Map<PeaType, PeaData> peasData = new HashMap<>();

	public static void loadData()
	{
		Layers.add("Plant", 1f);
		Layers.add("Zombie", 2f);
		Layers.add("Sun", 9f);

		//
		// Load main resources
		//

		bg = World.p.loadImage(spritesPath + "Background/bg.jpg");
		sunBg = World.p.loadImage(spritesPath + "Background/Menu/SunBack.png");
		shovelBg = World.p.loadImage(spritesPath + "Background/Menu/Shovel.png");
		sunSprite = new Sprite(spritesPath + "Sun/", "png");

		//
		// Load plants json
		//

		JSONObject plantsJson = loadJSONObject(new File("resources/plantsData.json"));

		for (Object obj : plantsJson.keys())
		{
			JSONObject plantObj = plantsJson.getJSONObject((String) obj);

			PlantData data = new PlantData();
			data.hp = plantObj.getFloat("hp");
			data.cost = plantObj.getInt("cost");
			data.cardImage = World.p.loadImage(plantObj.getString("cardImage"));
			data.sprite = new Sprite(plantObj.getString("sprite"), "png");

			JSONArray damagedSpritesArray = plantObj.getJSONArray("damagedSprites");
			if (damagedSpritesArray != null)
				for (String damagedSpritePath : damagedSpritesArray.getStringArray())
					data.damagedSprites.add(new Sprite(damagedSpritePath, "png"));

			plantsData.put(PlantType.valueOf((String) obj), data);
		}

		//
		// Load zombies json
		//

		JSONObject zombiesJson = loadJSONObject(new File("resources/zombiesData.json"));

		for (Object obj : zombiesJson.keys())
		{
			JSONObject zombieObj = zombiesJson.getJSONObject((String) obj);

			ZombieData data = new ZombieData();
			data.hp = zombieObj.getFloat("hp");
			data.runSprite = getSpriteOrNullFrom(zombieObj.getString("runSprite"), "png");
			data.jump1Sprite = getSpriteOrNullFrom(zombieObj.getString("jump1Sprite"), "png");
			data.jump2Sprite = getSpriteOrNullFrom(zombieObj.getString("jump2Sprite"), "png");
			data.walkSprite = getSpriteOrNullFrom(zombieObj.getString("walkSprite"), "png");
			data.attackSprite = getSpriteOrNullFrom(zombieObj.getString("attackSprite"), "png");
			data.bodySprite = getSpriteOrNullFrom(zombieObj.getString("bodySprite"), "png");
			data.headSprite = getSpriteOrNullFrom(zombieObj.getString("headSprite"), "png");
			data.lostHeadRunSprite = getSpriteOrNullFrom(zombieObj.getString("lostHeadRunSprite"), "png");
			data.lostHeadAttackSprite = getSpriteOrNullFrom(zombieObj.getString("lostHeadAttackSprite"), "png");
			data.lostHeadWalkSprite = getSpriteOrNullFrom(zombieObj.getString("lostHeadWalkSprite"), "png");

			zombiesData.put(ZombieType.valueOf((String) obj), data);
		}

		//
		// Load peas json
		//

		JSONObject peasJson = loadJSONObject(new File("resources/peasData.json"));

		for (Object obj : peasJson.keys())
		{
			JSONObject peaObj = peasJson.getJSONObject((String) obj);

			PeaData data = new PeaData();
			data.hp = peaObj.getFloat("hp");
			data.damage = peaObj.getFloat("damage");

			try { data.effectType = EffectType.valueOf(peaObj.getString("effect")); }
			catch (NullPointerException ignore) { data.effectType = null; }

			data.sprite = getSpriteOrNullFrom(peaObj.getString("sprite"), "png");

			peasData.put(PeaType.valueOf((String) obj), data);
		}
	}
}