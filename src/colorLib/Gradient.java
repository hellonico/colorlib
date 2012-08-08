package colorLib;

import processing.core.PApplet;

/**
 * Create a Palette object with all colors of a gradient.
 * This class extends the Palette class, so all the methods of the Palette class can be accessed from here.
 * @author Andreas K&ouml;berle
 * @author Jan Vantomme
 */
public class Gradient extends Palette
{

	/**
	 * Creates a Gradient object which holds all colors to create a gradient.
	 * @param i_p PApplet, normally you will use 'this'.
	 * @param i_colors int[], an int array which holds the colors in the processing way
	 * @param i_size int, the size of the gradient
	 */
	public Gradient(PApplet i_p, final int[] i_colors, final int i_size)
	{
		this(i_p,  i_colors,  i_size, false);
	}

	/**
	 * Creates a gradient with the colors of the passed Palette object and the specified number of steps.
	 * @param i_palette Palette, a Palette object
	 * @param i_step int, the number of steps
	 */
	public Gradient(Palette i_palette, int i_step)
	{
		this(i_palette, i_step, false);
	}
	
	/**
	 * Creates a gradient with the passed colors array and the specified number of steps.
	 * @param i_colors an array of colors.
	 * @param i_wrap boolean, if its true the gradient ends with the same colors as its begin (default is false)
	 */
	public Gradient(PApplet i_p, final int[] i_colors, final int i_size, boolean i_wrap)
	{
		super(i_p, i_size);
		createGradient(i_colors, i_size, i_wrap);
	}

	public Gradient(Palette i_palette, int i_size, boolean i_wrap)
	{
		super(i_palette.p, i_size);
		createGradient(i_palette.getColors(), i_size, i_wrap);
	}

	/**
	 * 
	 * @param i_colors an array of colors.
	 * @param i_stepSize
	 * @param wrap
	 */
	private void createGradient(int[] i_colors, int i_stepSize, boolean wrap)
	{
		for (int i = 0; i < i_stepSize; i++) {
			swatches[i] = new Swatch(p,(int) colorsBetween(i_colors, (float) i / i_stepSize, wrap));
		}
	}

	private int colorBetween(final int startColor, final int endColor, final float step)
	{
		int startAlpha = startColor >> 24 & 0xFF;
		int startRed = startColor >> 16 & 0xFF;
		int startGreen = startColor >> 8 & 0xFF;
		int startBlue = startColor & 0xFF;

		int endAlpha = endColor >> 24 & 0xFF;
		int endRed = endColor >> 16 & 0xFF;
		int endGreen = endColor >> 8 & 0xFF;
		int endBlue = endColor & 0xFF;

		int returnAlpha = (int) (startAlpha + (endAlpha - startAlpha) * step);
		int returnRed = (int) (startRed + (endRed - startRed) * step);
		int returnGreen = (int) (startGreen + (endGreen - startGreen) * step);
		int returnBlue = (int) (startBlue + (endBlue - startBlue) * step);

		int returnColor = (returnAlpha << 24) + (returnRed << 16) + (returnGreen << 8) + (returnBlue);
		
		return returnColor;
	}

	private int colorsBetween(final int[] i_colors, final float step, boolean wrap)
	{
		int length = i_colors.length - 1;
		
		if (wrap) {
			length = i_colors.length;
		}

		if (step <= 0) {
			return i_colors[0];
		}
		
		if (step >= 1) {
			return i_colors[i_colors.length - 1];
		}
		
		int a = (int) Math.floor(length * step);
		float f = 1f / (length);
		float newStep = (step - (a * f)) / f;
		int nextA = Math.min(a + 1, length);

		if (wrap) {
			if (nextA >= i_colors.length) {
				nextA = 0;
			}
		}

		return colorBetween(i_colors[a], i_colors[nextA], newStep);
	}

}
