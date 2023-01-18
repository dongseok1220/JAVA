import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class BallThreadAssig_20191619 extends Frame implements ActionListener  {
	private Canvas canvas;
	private static ArrayList<Ball> ball = new ArrayList<Ball>();


	public static class WindowDestroyer extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	}

	public BallThreadAssig_20191619()  {
		canvas = new Canvas();
		add("Center", canvas);
		Panel p = new Panel();
		Button s = new Button("Start");
		Button c = new Button("Close");
		p.add(s);
		p.add(c);
		s.addActionListener(this);
		c.addActionListener(this);
		add("South", p);
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getActionCommand() == "Start") {
			ball.add(new Ball(canvas, 200, 150, 16, 5));
			ball.get(0).start();
			ball.add(new Ball(canvas, 180, 130, 16, 2));
			ball.get(1).start();
			ball.add(new Ball(canvas, 220, 130, 16, 3));
			ball.get(2).start();
			ball.add(new Ball(canvas, 180, 170, 16, 1));
			ball.get(3).start();
			ball.add(new Ball(canvas, 220, 170, 16, 4));
			ball.get(4).start();
		} else if (evt.getActionCommand() == "Close")
			System.exit(0);
	}

	class Ball extends Thread{
		private Canvas box;
		private int SIZE = 16;
		private int x = 5;
		private int y = 5;
		private int dx = 2;
		private int dy = 2;
		private boolean isdraw = true;
		

		Image offScreen;
		Graphics bufferGraphics;
		
		public void paint(Graphics g) {
			offScreen = createImage(400, 300);
			bufferGraphics = offScreen.getGraphics();
			update(g);
		}
		public void update(Graphics g) {
			bufferGraphics.clearRect(0, 0, 400, 300);
			g.drawImage(offScreen, 0, 0, (ImageObserver) this);
			//repaint();
		}
		
		public Ball(Canvas c, int x, int y, int size, int i) {
			this.box = c;
			this.isdraw = true;
			this.x = x;
			this.y = y;
			this.SIZE = size;
			if (size == 1) {
				isdraw = false;
				ball.remove(ball.indexOf(this));
				return;
			}

			switch (i) {
			case 1:
				dx = -dx; // 왼쪽 위
				break;
			case 2:
				dx = -dx; // 왼쪽 아래
				dy = -dy;
				break;
			case 3:
				dy = -dy;
				break;
			case 4:
				break;
			case 5:
				dx = 0;
				break;
			default:
			}
		}

		public void draw() {
			if (isdraw) {
				Graphics g = box.getGraphics();
				g.fillOval(x, y, SIZE, SIZE);
				g.dispose();
			}
		}

		public void move() {
			Graphics g = box.getGraphics();
			g.setXORMode(this.box.getBackground());
			g.fillOval(x, y, SIZE, SIZE);
			x += dx;
			y += dy;
			Dimension d = box.getSize();
			if (x < 0) {
				x = 0;
				dx = -dx;
			}
			if (x + SIZE >= d.width) {
				x = d.width - SIZE;
				dx = -dx;
			}
			if (y < 0) {
				y = 0;
				dy = -dy;
			}
			if (y + SIZE >= d.height) {
				y = d.height - SIZE;
				dy = -dy;
			}
			g.fillOval(x, y, SIZE, SIZE);
			for (int i = 0; i < ball.size(); i++) {
				Ball targetBall = ball.get(i);
				if (this.isCollide(targetBall) && i != ball.indexOf(this)) {
					this.isdraw = false;
					targetBall.isdraw = false;
					this.splitTwoBall(this, targetBall);
				}
			}
			g.dispose();
		}

		public boolean isCollide(Ball check) {
			if (check == null) return false;
			int distanceX = this.x - check.x;
			int distanceY = this.y - check.y;
			double distance = Math.sqrt((distanceX * distanceX) + (distanceY * distanceY));

			// 반지름의 합이 중점간의 거리보다 크거나 같으면 둘이 충돌한 것으로 간주한다.
			if (distance < (this.SIZE + check.SIZE) / 2) {
				return true;
			}
			return false;
		}

		public void splitTwoBall(Ball ballOne, Ball ballTwo) {	
			Graphics g = box.getGraphics();
			g.clearRect(0, 0, 400, 300);
			split(ballOne, ballTwo);
			g.dispose();
		}

		public void split(Ball check,Ball check2) {
			int SIZE1 = check.SIZE;
			int SIZE2 = check2.SIZE;
			int indexOfBall = ball.indexOf(check);
			ball.remove(indexOfBall);
			indexOfBall = ball.indexOf(check2);
			ball.remove(indexOfBall);
			
			if (SIZE1 >= 4) {
				Ball ballOne = new Ball(canvas, check.x-check.SIZE, check.y, check.SIZE / 2, 1);
				Ball ballTwo = new Ball(canvas, check.x-check.SIZE, check.y, check.SIZE / 2, 2);
				ballOne.start();
				ballTwo.start();
				ball.add(ballOne);
				ball.add(ballTwo);
			}
			if (SIZE2 >= 4) {
				Ball ballOne1 = new Ball(canvas, check2.x+check2.SIZE, check2.y, check2.SIZE / 2, 3);
				Ball ballTwo2 = new Ball(canvas, check2.x+check2.SIZE, check2.y, check2.SIZE / 2, 4);
				ballOne1.start();
				ballTwo2.start();
				ball.add(ballOne1);
				ball.add(ballTwo2);
			}
			else {
				Graphics g = box.getGraphics();
				g.clearRect(0, 0, 400, 300);
				g.dispose();
			}
		}

		public void run() {
			if (this.isdraw) {
				this.draw();
				for (int i =0;;i++) {
					try {
						move();
						repaint();
						Thread.sleep(125);
					} catch (InterruptedException e) {
					}
				}
			}
		}

	}

	public static void main(String[] args) {
		Frame f = new BallThreadAssig_20191619();
		f.setSize(400, 300);
		WindowDestroyer listener = new WindowDestroyer();
		f.addWindowListener(listener);
		f.setVisible(true);
	}

}
