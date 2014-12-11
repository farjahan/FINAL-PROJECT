package sjsu.cs175.final_project;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**Creates an Empty Cell.
 * 
 * @authors Swathi Kotturu and Farjahan Hossain
 * @class CS 175 section 1
 * @date Wednesday, December 10, 2014
 */
public class Empty extends Cell {

	/**Draws an Empty Cell in the specified coordinates.
	 * @param x coordinate
	 * @param y coordinate
	 */
	public Empty(int x, int y) {
		super(x, y);
	}

	/* (non-Javadoc)
	 * @see sjsu.cs175.final_project.Cell#draw(android.graphics.Canvas, android.content.res.Resources, int, int, int, int)
	 */
	public void draw(Canvas g, Resources res, int x, int y, int w, int h) {
		Bitmap im = BitmapFactory.decodeResource(res, R.drawable.vazio);
		g.drawBitmap(im, null,
				new Rect(x * w, y * h, (x * w) + w, (y * h) + h), new Paint());
	}

	/* (non-Javadoc)
	 * @see android.graphics.Point#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Empty) {
			return true;
		} else {
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see android.graphics.Point#toString()
	 */
	@Override
	public String toString() {
		return " ";
	}
}