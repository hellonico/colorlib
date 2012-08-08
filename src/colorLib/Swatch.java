package colorLib;

import processing.core.PApplet;

/**
 * Creates a swatch object with the overloaded or a random color. This swatch
 * object holds all information on a color. All color transformations are done
 * on this
 * 
 * @author Andreas K&ouml;berle
 * @author Jan Vantomme
 * 
 */

public class Swatch
{

	private PApplet p;
	private int c;

	/**
	 * @param i_p PApplet, normally you will use 'this'.
	 */
	public Swatch(final PApplet i_p)
	{
		p = i_p;
		c = p.g.color(p.random(p.g.colorModeX), p.random(p.g.colorModeY), p
				.random(p.g.colorModeZ));
	}

	/**
	 * @param color color, color
	 */
	public Swatch(final PApplet i_p, final int color)
	{
		p = i_p;
		c = color;
	}

	/**
	 * Gets the black value for the color of this swatch. Based on the generic
	 * RGB to CMYK formula: black = minimum(1 - red, 1 - green, 1 - blue)
	 * 
	 * @return color, the black value of the given color, a number between 0 and 1.
	 */
	public float black()
	{
		return PApplet.min(1 - ((c >> 16) & 0xFF) / 255.0f,
				1 - ((c >> 8) & 0xFF) / 255.0f, 1 - ((c) & 0xFF) / 255.0f);
	}

	/**
	 * Gets the cyan value for a given RGB color. Based on the generic RGB to
	 * CMYK formula: cyan = (1 - red - black) / (1 - black)
	 * 
	 * @return int, the cyan value of the given color, a number between 0 and 1.
	 */
	public float cyan()
	{
		return (1 - ((c >> 16) & 0xFF) / 255.0f - black()) / (1 - black());
	}

	/**
	 * Gets the magenta value for a given RGB color. Based on the generic RGB to
	 * CMYK formula: magenta = (1 - green - black) / (1 - black)
	 * 
	 * @return int, the magenta value of the given color, a number between 0 and 1.
	 */
	public float magenta()
	{
		return (1 - ((c >> 8) & 0xFF) / 255.0f - black()) / (1 - black());
	}

	/**
	 * Gets the yellow value for a given RGB color. Based on the generic RGB to
	 * CMYK formula: yellow = (1 - blue - black) / (1 - black)
	 * 
	 * @return int, the yellow value of the given color, a number between 0 and 1.
	 */
	public float yellow()
	{
		return (1 - ((c) & 0xFF) / 255.0f - black()) / (1 - black());
	}

	/**
	 * Darkens the color by a given number. The deafult is 10.
	 */
	public void darken()
	{
		darken(10);
	}

	/**
	 * @param i_step
	 *            float, number to decrease the brightness of the swatch
	 */
	public void darken(float i_step)
	{
		lighten(-i_step);
	}

	/**
	 * Lightens the color by a given number. The deafult is 10.
	 */
	public void lighten()
	{
		lighten(10);
	}

	/**
	 * @param i_step
	 *            float, number to increase the brightness of the swatch
	 */
	public void lighten(final float i_step)
	{
		c = setHSBColor(p.hue(c), p.saturation(c), p.brightness(c) + i_step, p
				.alpha(c));
	}

	/**
	 * Desaturates the color by a given number. The deafult is 10.
	 */
	public void desaturate()
	{
		saturate(-10);
	}

	/**
	 * @param i_step
	 *            i_step float, number to increase the saturation of the swatch
	 */
	public void desaturate(final int i_step)
	{
		saturate(-i_step);
	}

	/**
	 * Saturates the color by a given number. The deafult is 10.
	 */
	public void saturate() 
	{
		saturate(10);
	}

	/**
	 * @param i_step
	 *            float, number to decrease the saturation of the swatch
	 */
	public void saturate(final int i_step)
	{
		c = setHSBColor(p.hue(c), p.saturation(c) + i_step, p.brightness(c));
	}

	/**
	 * Sets a new color based on HSB values.
	 * 
	 * @param h
	 *            hue value for the color that will be set by the function
	 * @param s
	 *            saturation value for the color that will be set by the
	 *            function
	 * @param b
	 *            brightness value for the color that will be set by the
	 *            function
	 * @param a
	 *            alpha value for the color that will be set by the function
	 * @return a new color
	 */
	private int setHSBColor(final float h, final float s, final float b, final float a)
	{
		int color;
		int colorMode = p.g.colorMode;
		p.colorMode(PApplet.HSB);
		color = p.color(h, s, b, a);
		p.colorMode(colorMode);
		return color;
	}

	/**
	 * Sets a new color based on HSB values.
	 * 
	 * @param h
	 *            hue value for the color that will be set by the function
	 * @param s
	 *            saturation value for the color that will be set by the
	 *            function
	 * @param b
	 *            brightness value for the color that will be set by the
	 *            function
	 * @return a new color
	 */
	private int setHSBColor(final float h, final float s, final float b)
	{
		return setHSBColor(h, s, b, 255);
	}

	/**
	 * Returns the color of the swatch.
	 * 
	 * @return color, color
	 */
	public int getColor()
	{
		return c;
	}

	/**
	 * Returns the transparent color of the swatch
	 * 
	 * @param alpha, Alpha value for the transparent color. Should be an integer between 0 and 100.
	 * @return color, color
	 */
	public int getTransparentColor(int alpha)
	{
		int r = (c >> 16) & 0xff;
		int g = (c >> 8) & 0xff;
		int b = c & 0xff;
		int a = alpha;
		
		int colorMode = p.g.colorMode;
		p.colorMode(PApplet.RGB, 255, 255, 255, 100);
		int transparentColor = p.color(r, g, b, a);
		p.colorMode(colorMode);
		
		return transparentColor;
	}
	
	
	/**
	 * Sets the color of the swatch
	 * 
	 * @param i_c
	 *            color, color
	 */
	public void setColor(final int i_c)
	{
		c = i_c;
	}

	/**
	 * Rotates a color around the RGB (Red-Green-Blue) color wheel.
	 * 
	 * @param i_angle
	 *            float, value between 0 and 255
	 */
	public void rotateRGB(final float i_angle)
	{
		c = setHSBColor((p.hue(c) + i_angle) % 256, p.saturation(c), p
				.brightness(c));
	}

	/**
	 * Rotates a color around the RYB (Red-Yellow-Blue) color wheel. More
	 * information about the RYB color wheel can be found at <a
	 * href="http://en.wikipedia.org/wiki/RYB_color_model">Wikipedia</a>.
	 * 
	 * @param i_angle
	 *            float, angle between 0 and 360 to rotate the color
	 */
	public void rotateRYB(float i_angle)
	{
		float hue = ((p.hue(c)) / 256f) * 360;
		i_angle %= 360;
		float angle = 0;
		int[][] wheel = { { 0, 0 }, { 15, 8 }, { 30, 17 }, { 45, 26 },
				{ 60, 34 }, { 75, 41 }, { 90, 48 }, { 105, 54 }, { 120, 60 },
				{ 135, 81 }, { 150, 103 }, { 165, 123 }, { 180, 138 },
				{ 195, 155 }, { 210, 171 }, { 225, 187 }, { 240, 204 },
				{ 255, 219 }, { 270, 234 }, { 285, 251 }, { 300, 267 },
				{ 315, 282 }, { 330, 298 }, { 345, 329 }, { 360, 0 } };

		for (int i = 0; i < wheel.length - 1; i++) {
			int x0 = wheel[i][0];
			int y0 = wheel[i][1];
			int x1 = wheel[i + 1][0];
			int y1 = wheel[i + 1][1];
			if (y1 < y0) {
				y1 += 360;
			}
			if (y0 <= hue && hue <= y1) {
				angle = 1f * x0 + (x1 - x0) * (hue - y0) / (y1 - y0);
				break;
			}
		}
		System.out.println("hue: " + hue + "angle: " + angle);
		angle = (angle + i_angle) % 360;

		for (int i = 0; i < wheel.length - 1; i++) {
			int x0 = wheel[i][0];
			int y0 = wheel[i][1];
			int x1 = wheel[i + 1][0];
			int y1 = wheel[i + 1][1];
			if (y1 < y0)
				y1 += 360;
			if (x0 <= angle && angle <= x1) {
				hue = 1f * y0 + (y1 - y0) * (angle - x0) / (x1 - x0);
				break;
			}
		}

		hue %= 360;
		c = setHSBColor(hue * 256 / 360, p.saturation(c), p.brightness(c), p
				.alpha(c));
	}
	
	/**
	 * Returns the nearest hue to the color as an one
	 * of the following strings: "red", "orange", "yellow", "lime",
	 * "green","teal", "cyan", "azure", "blue", "indigo", "purple", "pink"
	 * 
	 * @return String: the nearest hue of the color
	 */
	public String getNearestHue()
	{
		String[] hueNames = { "red", "orange", "yellow", "lime", "green",
				"teal", "cyan", "azure", "blue", "indigo", "purple", "pink" };

		int r = (c >> 16) & 0xff;
		int g = (c >> 8) & 0xff;
		int b = c & 0xff;
		if (r == b && b == g) {
			if (r == 0) {
				return "black";
			} else if (r == 255) {
				return "white";
			} else {
				return "grey";
			}
		}

		String nearest = "";
		float d = 256;
		float hue = p.hue(c);
		for (int i = 0; i < hueNames.length; i++) {
			float i_d = Math.abs(hue - 255 / 12 * i);
			if (i_d < d) {
				nearest = hueNames[i];
				d = i_d;
			} else {
				break;
			}
		}
		return nearest;
	}
	
	/**
	 * Calculates the brightness difference between the swatch and the given
	 * color or swatch. The brightness is calculated by the following formula
	 * suggested by the <a href="http://www.w3.org/TR/AERT#color-contrast">W3C</a>:
	 * <code>((Red value * 299) + (Green value * 587) + (Blue value * 114)) / 1000</code>
	 * The result should be higher then 125 for a good readability.
	 * 
	 * @param i_color
	 * @return float, difference
	 */
	public float brightnessDiff(final int i_color)
	{
		return Math
				.abs(((c >> 16 & 0xFF) * 299 + (c >> 8 & 0xFF) * 587 + (c & 0xFF) * 114)
						/ 1000
						- ((i_color >> 16 & 0xFF) * 299 + (i_color >> 8 & 0xFF)
								* 587 + (i_color & 0xFF) * 114) / 1000);
	}

	/**
	 * @param i_swatch
	 */
	public float brightnessDiff(final Swatch i_swatch)
	{
		return brightnessDiff(i_swatch.getColor());
	}

	/**
	 * Calculated the color difference between the swatch and the given color or
	 * swatch by the following formula suggested by the <a
	 * href="http://www.w3.org/TR/AERT#color-contrast">W3C</a>:
	 * <code>(maximum (Red value 1, Red value 2) - minimum (Red value 1, Red value 2)) + (maximum (Green value 1, Green value 2) - minimum (Green value 1, Green value 2)) + (maximum (Blue value 1, Blue value 2) - minimum (Blue value 1, Blue value 2))</code>
	 * The result should be higher then 500 for a good readability.
	 * 
	 * @param i_color
	 *            color
	 * @return float, difference
	 */
	public float colorDiff(final int i_color)
	{
		return (Math.max(c >> 16 & 0xFF, i_color >> 16 & 0xFF) - Math.min(
				c >> 16 & 0xFF, i_color >> 16 & 0xFF))
				+ (Math.max(c >> 8 & 0xFF, i_color >> 8 & 0xFF) - Math.min(
						c >> 8 & 0xFF, i_color >> 8 & 0xFF))
				+ (Math.max(c & 0xFF, i_color & 0xFF) - Math.min(c & 0xFF,
						i_color & 0xFF));
	}

	/**
	 * @param i_swatch
	 *            Swatch, swatch
	 */
	public float colorDiff(final Swatch i_swatch)
	{
		return colorDiff(i_swatch.getColor());
	}

}