import gameEngine.*;
import processing.core.PVector;

/**
 * Created by roscale on 5/6/17.
 */
public class Grid extends GameObject
{
	public Cell[][] cells;
	public PVector size;

	public Grid(float x, float y, int w, int h, float cellW, float cellH)
	{
		transform.setPosition(x ,y);
		size = new PVector(w * cellW, h * cellH);

		cells = new Cell[w][h];

		for (int i = 0; i < w; i++)
			for (int j = 0; j < h; j++)
				cells[i][j] = new Cell(x + i*cellW, y + j*cellH, cellW, cellH);
	}
}
