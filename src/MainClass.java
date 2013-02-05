import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.geom.*;

public class MainClass extends JComponent {
	private static class Line {
			final int x1; 
			final int y1;
			final int x2;
			final int y2;   
			final Color color;

			public Line(int x1, int y1, int x2, int y2, Color color) {
					this.x1 = x1;
					this.y1 = y1;
					this.x2 = x2;
					this.y2 = y2;
					this.color = color;
			}               
	}

	private static class Circle {
		public float x, y, radius;
		public Circle(float x, float y, float radius) {
			this.x = x;
			this.y = y;
			this.radius = radius;
		}
	}

	private final ArrayList<Circle> circles = new ArrayList<Circle>();;
	private final LinkedList<Line> lines = new LinkedList<Line>();

	public void addLine(int x1, int x2, int x3, int x4) {
		addLine(x1, x2, x3, x4, Color.black);
		repaint();
	}

	public void addLine(Line line) {
		lines.add(line);
		repaint();
	}

	public void addLine(int x1, int x2, int x3, int x4, Color color) {
		lines.add(new Line(x1,x2,x3,x4, color));        
		repaint();
	}

	public void addCircle(Circle circle) {
		circles.add(circle);
		repaint();
	}

	public void clearLines() {
		lines.clear();
		repaint();
	}

	public void removeLastLine() {
		lines.removeLast();
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			for (Line line : lines) {
				g2.setColor(line.color);
				g2.setStroke(new BasicStroke(3));
				g2.draw(new Line2D.Float(line.x1, line.y1, line.x2, line.y2));
				g2.setStroke(new BasicStroke(1));
			}
			for (Circle c : circles) {
				g.fillOval((int) c.x, (int) c.y, (int) c.radius, (int) c.radius);
			}
	}

	public static void main(String[] args) throws InterruptedException{
		System.out.println("This is a test!");
		
		JFrame testFrame = new JFrame();
		testFrame.setTitle("intelliLine");
		
		final MainClass comp = new MainClass();

		comp.setPreferredSize(new Dimension(600, 400));
		testFrame.getContentPane().add(comp, BorderLayout.CENTER);
		testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		testFrame.pack();
		testFrame.setVisible(true);

		// adding bias line
		Line biasLine = new Line(0,0,600,400, new Color(0, 0, 0));
		comp.addLine(biasLine);

		// adding initial perceptron's guess line
		Perceptron ptron = new Perceptron(3);
		float[] initial = ptron.weights;
		Line currentGuess = new Line(0, (int) (-200 * initial[0] / initial[1]) + 200, 600, (int) (200 * initial[0] / initial[1]) + 200, new Color(255,0,0));
		comp.addLine(currentGuess);

		// train the perceptron step by step using 4000 sample points
		Trainer[] tr = new Trainer[4000];
		Random rand = new Random();

		for (int i = 0; i < tr.length; i++) {
			float x = (float) rand.nextInt(600) - 300;
			float y = (float) rand.nextInt(400) - 200;
			System.out.println("Adding point (" + x + ", " + y + ")...");
			int answer = (x + y > 0) ? 1 : ((x + y == 0) ? 0 : -1);
			Circle cp = new Circle(x + 300, y + 200, 5);
			comp.addCircle(cp);
			tr[i] = new Trainer(x, y, answer);
			ptron.train(tr[i].inputs, tr[i].answer);
			// adjust the guess on the screen
			float[] newpoints = ptron.weights;
			currentGuess = new Line(0, (int) (-200 * initial[0] / initial[1]) + 200, 600, (int) (200 * newpoints[0] / newpoints[1]) + 200, new Color(255,0,0));
			comp.removeLastLine();
			comp.addLine(currentGuess);
			Thread.sleep(10);
		}
	}
}