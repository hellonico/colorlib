package colorLib.webServices;

import processing.core.PApplet;
import colorLib.Palette;

/**
 * @author Andreas K&ouml;berle
 * @author Jan Vantomme
 */
public class ColrTheme extends Palette
{

	protected String[] themeTags;
	
	/**
	 * @param i_p
	 * @param i_colors 
	 */
	protected ColrTheme(final PApplet i_p, int[] i_colors)
	{
		super(i_p, i_colors);
		themeTags = new String[0];
	}
	
	public ColrTheme(PApplet i_p)
	{
		super(i_p);
		themeTags = new String[0];
	}

	/**
	 * @return String[]: tags for this theme
	 */
	public String[] getThemeTags()
	{
		return themeTags;
	}

	protected void addThemeTags(final String i_themeTags)
	{
		if (themeTags != null || !i_themeTags.equalsIgnoreCase("")){
			themeTags=PApplet.concat(themeTags, i_themeTags.split(" "));
	    }
	}
	
}
