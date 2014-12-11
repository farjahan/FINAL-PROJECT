package sjsu.cs175.final_project;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Point;

/**Creates a Cell. 
 * 
 * @authors Swathi Kotturu and Farjahan Hossain
 * @class CS 175 section 1
 * @date Wednesday, December 10, 2014
 */
public abstract class Cell extends Point {

	/**Creates a cell at point described by x and y
	 * @param x coordinate
	 * @param y coordinate
	 */
	public Cell(int x, int y) {
		super(x, y);
	}

	/**A Draw method to draw a cell on a given canvas, with given resources
	 * @param canvas to draw on
	 * @param resources
	 * @param x coordinate
	 * @param y coordinate
	 * @param width of cell
	 * @param height of cell
	 */
	abstract public void draw(Canvas g, Resources res, int x, int y, int w,
			int h);
}