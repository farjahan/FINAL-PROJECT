package sjsu.cs175.final_project;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**TicTacToe View which is divided into nine cells.
 * 
 * @authors Swathi Kotturu and Farjahan Hossain
 * @class CS 175 section 1
 * @date Wednesday, December 10, 2014
 */
public class Game extends View {

	private Cell[][] singlesquare = null;
	int x = 3;
	int y = 3;
	private int l;
	private int a;
	private boolean whatdrawn = false;
	private int playerwin = 3;
	private Paint caneta;

	Handler handler = new Handler() {
		// @Override
		/* (non-Javadoc)
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				invalidate();
				break;
			case 1:
				Toast.makeText(getContext(), "You Win!", Toast.LENGTH_SHORT)
						.show();
				break;
			case 2:
				Toast.makeText(getContext(), "Computer Win!",
						Toast.LENGTH_SHORT).show();
				break;
			case 3:
				Toast.makeText(getContext(), "Loose!", Toast.LENGTH_SHORT)
						.show();
				break;
			default:
				break;
			}

			super.handleMessage(msg);
		}
	};

	/**Retrieves the game size.
	 * @return the game size
	 */
	public int getGameSize() {
		return x;
	}

	/**Creates game canvas split into 9 cells.
	 * @param context to draw onto
	 */
	public Game(Context context) {
		super(context);

		caneta = new Paint();

		this.caneta.setARGB(255, 0, 0, 0);
		this.caneta.setAntiAlias(true);
		this.caneta.setStyle(Style.STROKE);
		this.caneta.setStrokeWidth(5);

		l = this.getWidth();
		a = this.getHeight();

		singlesquare = new Cell[x][y];

		int xss = l / x;
		int yss = a / y;

		for (int z = 0; z < y; z++) {
			for (int i = 0; i < x; i++) {
				singlesquare[z][i] = new Empty(xss * i, z * yss);
			}
		}
	}

	/* (non-Javadoc)
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		for (int i = 0; i < singlesquare.length; i++) {
			for (int j = 0; j < singlesquare[0].length; j++) {
				singlesquare[i][j].draw(canvas, getResources(), j, i,
						(this.getWidth() + 3) / singlesquare.length,
						this.getHeight() / singlesquare[0].length);
			}
		}

		int xs = this.getWidth() / x;
		int ys = this.getHeight() / y;
		for (int i = 0; i <= x; i++) {
			canvas.drawLine(xs * i, 0, xs * i, this.getHeight(), caneta);
		}
		for (int i = 0; i <= y; i++) {
			canvas.drawLine(0, ys * i, this.getWidth(), ys * i, caneta);
		}

		super.onDraw(canvas);
	}

	/* (non-Javadoc)
	 * @see android.view.View#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x_aux = (int) (event.getX() / (this.getWidth() / x));
		int y_aux = (int) (event.getY() / (this.getHeight() / y));
		drawimage(x_aux, y_aux);
		return super.onTouchEvent(event);
	}

	/**Get's the current piece associated with the Player.
	 * @param player
	 * @return
	 */
	public String getPiece(int player) {
		switch (player) {
		case 1:
			return "x";
		case -1:
			return "o";
		}
		return null;
	}

	/**Draws an image at the current coordinates.
	 * @param x coordinate
	 * @param y coordinate
	 */
	public void drawimage(int x_aux, int y_aux) {
		Cell cel = null;
		if (whatdrawn) {
			cel = new Cross(singlesquare[x_aux][y_aux].x,
					singlesquare[x_aux][y_aux].y);
			whatdrawn = false;
		} else {
			cel = new Ball(singlesquare[x_aux][y_aux].x,
					singlesquare[x_aux][y_aux].y);
			whatdrawn = true;
		}

		singlesquare[y_aux][x_aux] = cel;

		handler.sendMessage(Message.obtain(handler, 0));
		Scores socket = new Scores(getContext().getSharedPreferences("MyPREFERENCES2",
				Context.MODE_PRIVATE), getContext().getSharedPreferences("MyPREFERENCES2",
				Context.MODE_PRIVATE).edit(), getContext());


		if (validate_game()) {

			if (whatdrawn) {
				System.out.println("You Win");
				handler.sendMessage(Message.obtain(handler, 1));
				//save scores
				socket.setCurrentScore(1);

			} else {
				System.out.println("Computer Win");
				handler.sendMessage(Message.obtain(handler, 2));
				socket.setCurrentScore(0);
			}
			
			Intent intentm = new Intent(getContext(), MainActivity.class);
			getContext().startActivity(intentm);
			resizegame(x);

		} else if (isFull()) {
			System.out.println("Loose");
			handler.sendMessage(Message.obtain(handler, 3));
			socket.setCurrentScore(0);
			Intent intentm = new Intent(getContext(), MainActivity.class);
			getContext().startActivity(intentm);
			resizegame(x);

		}
	}

	/**Determines if someone has won, tied, or still playing.
	 * @return true if someone won or game is over
	 */
	private boolean validate_game() {
		int contador = 0;
		Cell anterior = null;

		for (int i = 0; i < singlesquare.length; i++) {
			for (int j = 0; j < singlesquare[0].length; j++) {
				System.out.print(singlesquare[i][j]);
				if (!singlesquare[i][j].equals(anterior)
						|| singlesquare[i][j] instanceof Empty) {

					anterior = singlesquare[i][j];
					contador = 0;
				} else {
					contador++;
				}
				if (contador >= getPlayerwin() - 1) {
					return true;
				}

			}
			System.out.println("");
			anterior = null;
			contador = 0;
		}

		anterior = null;
		for (int j = 0; j < singlesquare[0].length; j++) {
			for (int i = 0; i < singlesquare.length; i++) {
				System.out.print(singlesquare[i][j]);
				if (!singlesquare[i][j].equals(anterior)
						|| singlesquare[i][j] instanceof Empty) {
					anterior = singlesquare[i][j];
					contador = 0;
				} else {
					contador++;
				}

				if (contador >= getPlayerwin() - 1) {
					return true;
				}

			}
			System.out.println("");
			anterior = null;
			contador = 0;
		}

		anterior = null;
		for (int j = singlesquare[0].length - 1; j >= 0; j--) {
			int yau = 0;
			for (int z = j; z < singlesquare[0].length; z++) {
				if (!singlesquare[yau][z].equals(anterior)
						|| singlesquare[yau][z] instanceof Empty) {
					anterior = singlesquare[yau][z];
					contador = 0;
				} else {
					contador++;
				}

				if (contador >= getPlayerwin() - 1) {
					return true;
				}
				yau++;
			}
			contador = 0;
			anterior = null;
		}

		anterior = null;
		for (int j = 0; j < singlesquare[0].length; j++) {
			int yau = 0;
			for (int z = j; z >= 0; z--) {
				if (!singlesquare[yau][z].equals(anterior)
						|| singlesquare[yau][z] instanceof Empty) {
					anterior = singlesquare[yau][z];
					contador = 0;
				} else {
					contador++;
				}

				if (contador >= getPlayerwin() - 1) {
					return true;
				}

				yau++;
			}
			contador = 0;
			anterior = null;
		}
		return false;
	}

	/**Determines whether the board is filled or not. 
	 * @return true if the board is full, false if the board is empty
	 */
	public boolean isFull() {
		for (int i = 0; i < singlesquare.length; i++) {
			for (int j = 0; j < singlesquare[0].length; j++) {
				if (singlesquare[i][j] instanceof Empty) {
					return false;
				}
			}
		}
		return true;
	}

	/**Resizes the cells in the game by a given width.
	 * @param width of cell to resize
	 */
	public void resizegame(int s) {
		x = s;
		y = s;

		singlesquare = new Cell[x][y];

		int xss = l / x;
		int yss = a / y;

		for (int z = 0; z < y; z++) {
			for (int i = 0; i < x; i++) {
				singlesquare[z][i] = new Empty(xss * i, z * yss);
			}
		}
		handler.sendMessage(Message.obtain(handler, 0));
	}

	/**Retrieves the player that won
	 * @return 1 for the first player, 2 for the second player
	 */
	public int getPlayerwin() {
		return playerwin;
	}

}