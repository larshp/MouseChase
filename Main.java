import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import java.sql.Date;

import javax.imageio.*;

public class Main {

	/**
	 * @param args
	 * @throws AWTException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws AWTException,
			InterruptedException {

		Robot robot = new Robot();

		Rectangle captureSize = new Rectangle(Toolkit.getDefaultToolkit()
				.getScreenSize());

		File f = null;

		f = new File("C:\\Users\\xxx\\Desktop\\eclipse\\g1.png");
		BufferedImage bubble1 = null;
		try {
			bubble1 = ImageIO.read(f);
		} catch (IOException e) {
			System.out.println("could not open file");
		}
		int bheight1 = bubble1.getHeight();
		int bwidth1 = bubble1.getWidth();
		int[][] bubble1rgb = new int[bwidth1][bheight1];
		for (int x = 0; x < bwidth1; x++) {
			for (int y = 0; y < bheight1; y++) {
				bubble1rgb[x][y] = bubble1.getRGB(x, y);
			}
		}

		int total = 0;
		while (true) {

			BufferedImage screen = robot.createScreenCapture(captureSize);

			int screenheight = screen.getHeight();
			int screenwidth = screen.getWidth();

			int[][] screenrgb = new int[screenwidth][screenheight];

			for (int x = 0; x < screenwidth; x++) {
				for (int y = 0; y < screenheight; y++) {
					screenrgb[x][y] = screen.getRGB(x, y);
				}
			}

			boolean exit = false;
			for (int x = 0; x < screenwidth - bwidth1 && exit == false; x++) {
				for (int y = 0; y < screenheight - bheight1 && exit == false; y++) {

					boolean found = true;
					for (int bx = 0; bx < bwidth1 && found == true; bx++) {
						for (int by = 0; by < bheight1 && found == true; by++) {
							if (bubble1rgb[bx][by] != screenrgb[x + bx][y + by]) {
								found = false;
							}
						}
					}
					if (found == true) {
						total = total + 1;
						System.out.println("found at " + x + " " + y
								+ ", total " + total);
						int cx = MouseInfo.getPointerInfo().getLocation().x;
						int cy = MouseInfo.getPointerInfo().getLocation().y;
						move(robot, x, y);
						Thread.sleep(30);
						move(robot, cx, cy);
						Thread.sleep(150);
						exit = true;
					}

				}
			}

			Thread.sleep(100);
			// System.out.println("done");
		}
	}

	public static void move(Robot r, int x, int y) throws InterruptedException {
		int cx = MouseInfo.getPointerInfo().getLocation().x;
		int cy = MouseInfo.getPointerInfo().getLocation().y;

		while (cx != x || cy != y) {
			if (x > cx) {
				r.mouseMove(cx + 1, cy);
			} else if (x < cx) {
				r.mouseMove(cx - 1, cy);
			}

			cx = MouseInfo.getPointerInfo().getLocation().x;
			// System.out.println("x: " + cx + " " + x);

			if (y > cy) {
				r.mouseMove(cx, cy + 1);
			} else if (y < cy) {
				r.mouseMove(cx, cy - 1);
			}

			cy = MouseInfo.getPointerInfo().getLocation().y;
			// System.out.println("y: " + cy + " " + y);

			Thread.sleep(1);
		}
	}

}
