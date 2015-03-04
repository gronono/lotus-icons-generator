import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.imageio.ImageIO;


public class LotusIconsGenerator {


	public static void main(String[] args) throws IOException {

		List<String> logos = new ArrayList<String>();

		//		System.out.println("var logos = [");
		BufferedImage image = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("base.png"));
		logos.add(encode(image));

		for (int i = 1; i < 100; i++) {
			Graphics2D g = image.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

			g.setColor(Color.BLACK);
			Shape outterCircle = new Ellipse2D.Double(10, 10, 21, 21);
			g.draw(outterCircle);
			g.setColor(Color.WHITE);
			Shape innerCircle = new Ellipse2D.Double(outterCircle.getBounds2D().getX() + 1, outterCircle.getBounds2D().getY() + 1, outterCircle.getBounds2D().getWidth() - 1, outterCircle.getBounds2D().getHeight() - 1);
			g.fill(innerCircle);

			g.setFont(new Font("Tahoma", Font.BOLD, 18));
			g.setColor(Color.BLACK);
			String s = Integer.toString(i);
			float stringLen = (float) g.getFontMetrics().getStringBounds(s, g).getWidth();
			float start = 20/2 - stringLen/2f;
			g.drawString(s, start + 11, 28);

			g.dispose();

			logos.add(encode(image));
		}

		System.out.println(logos.stream().map(s -> "'" + s + "'").reduce((s1, s2) -> s1 + ",\n" + s2).get());
	}

	private static String encode(BufferedImage image) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(image, "png", out);
		//		ImageIO.write(image, "png", new File("out.png"));
		return Base64.getEncoder().encodeToString(out.toByteArray());
	}
}
