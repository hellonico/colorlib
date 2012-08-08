package colorLib;

import processing.core.PApplet;

/**
 * Create a Palette object with random colors.
 * This class extends the Palette class, so all the methods of the Palette class can be accessed from here.
 * @author Jan Vantomme
 */

public class RandomPalette extends Palette
{

	int numSwatches;

	/**
	 * Creates a Palette object with 5 random colors.
	 * @param i_p PApplet, normally you will use 'this'.
	 */
	public RandomPalette(PApplet i_p)
	{
		super(i_p);
		numSwatches = 5;
		randomSwatches();
	}
	
	/**
	 * Creates a Palette object with a chosen number of random colors.
	 * @param i_p PApplet, normally you will use 'this'.
	 * @param i_numSwatches Number of random colors you want.
	 */
	public RandomPalette(PApplet i_p, int i_numSwatches)
	{
		super(i_p);
		numSwatches = i_numSwatches;
		randomSwatches();
	}
	
	/**
	 * Generates new random colors.
	 */
	public void randomSwatches()
	{
		swatches = new Swatch[numSwatches];
		for (int i = 0; i < numSwatches; i++) {
			swatches[i] = new Swatch( p, p.color( p.random(255), p.random(255), p.random(255)) );
		}
	}
	
	/**
	 * Adds one color to the end of the Palette.
	 * @param i_color The color you want add to the Palette.
	 */
	public void addColor(final int i_color)
	{
		swatches = (Swatch[]) PApplet.append( swatches, new Swatch(p, i_color) );

		// set numSwatches to swatches.length. This is important because numSwatches
		// is used to generate random swatches. If a color is added to the palette and the
		// user calls randomSwatches() after that, a new palette is generated with the
		// same amount of colors as before.
		numSwatches = swatches.length;
	}
	
	/**
	 * Adds all passed colors to the end of the Palette.
	 * @param i_colors An array of colors.
	 */
	public void addColors(final int[] i_colors)
	{
		for (int i = 0; i < i_colors.length; i++) {
			addColor( i_colors[i]);
		}
		numSwatches = swatches.length;
	}

	/**
	 * Adds a random color to the end of the Palette.
	 */
	public void addRandomColor()
	{
		swatches = (Swatch[]) PApplet.append( swatches, new Swatch( p, p.color( p.random(255), p.random(255), p.random(255) ) ) );		
		numSwatches = swatches.length;
	}

	/**
	 * Adds a number of random colors to the end of the Palette.
	 * @param i_numColors Number of colors you want to add to the Palette.
	 */
	public void addRandomColors(final int i_numColors)
	{
		for (int i = 0; i < i_numColors; i++) {
			swatches = (Swatch[]) PApplet.append( swatches, new Swatch( p, p.color( p.random(255), p.random(255), p.random(255) ) ) );		
		}
		numSwatches = swatches.length;
	}

}