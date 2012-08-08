package colorLib.webServices;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.data.XML;
import colorLib.Swatch;
/**
 * Colr is a container to query the <a href="http://www.colr.org">Colr service</a>. 
 * As the <a href="http://www.colr.org/api.html">API of Colr</a> is very limited, there are only three methods to use.
 * @author Andreas K&ouml;berle
 * @author Jan Vantomme
 */
public class Colr extends WebService
{
	
	private final int DEFAULT = 0, LATEST = 1, RANDOM = 2;
	
	/**
	 * @param i_p
	 */
	public Colr(final PApplet i_p){
		p=i_p;
	}
	
	/**
	 * Query the <a href="http://www.colr.org">Colr service</a> with a color and returns a string array with all related tags.
	 * @param hex String: hex value of a color
	 * @return String[]: string array with all tags associated  with this color
	 */
	
	public String[] searchTags(final String hex){
		return searchTags(hex, null);
	}
	
	public String[] searchTags(final String hex, final String filename){
		String url = "http://www.colr.org/rss/color/" + hex;
		XML xml = getXML(url, filename);
		String tags = (xml.getChildren("channel/items/item/description/tags"))[0].getContent();
		String[] tagArray = new String[0];
		if (tags != null || !tags.equalsIgnoreCase("")){
			tagArray= tags.split(",");
	    }else{
	    	p.println("There are no tags for the color");
	    }
		return tagArray;
	}
	
	/**
	 * @param i_color color: color 
	 */
	public String[] searchTags(final int i_color){
		return searchTags(PApplet.hex(i_color, 6), null);
	}
	
	/**
	 * @param filename  Filename to save the result xml, respectively load the xml if it still exists
	 */
	public String[] searchTags(final int i_color, final String filename){
		return searchTags(PApplet.hex(i_color, 6), filename);
	}
	
	/**
	 * @param i_color Swatch: swatch
	 */
	public String[] searchTags(final Swatch i_color){
		return searchTags(PApplet.hex(i_color.getColor(), 6), null);
	}
	
	/**
	 * @param filename  Filename to save the result xml, respectively load the xml if it still exists
	 */
	public String[] searchTags(final Swatch i_color, final String filename){
		return searchTags(PApplet.hex(i_color.getColor(), 6), filename);
	}
	

	
	/**
	 * Query the <a href="http://www.colr.org">Colr service</a> with a tag and 
	 * return a ColrTheme holding all colors and all tags associated with this tag.
	 * @param tag String: tag to query the service
	 * @return ColrTheme: a ColrTheme holding all colors and all tags associated with this colors
	 */
	public ColrTheme searchColors(String tag){
		return searchColors(tag, null);
	}
	
	
	
	/**
	 * @param filename  Filename to save the result xml, respectively load the xml if it still exists
	 */
	public ColrTheme searchColors(String tag, String filename){
		XML xml = getXML("http://www.colr.org/rss/tag/"+ tag, filename);
		
		ColrTheme theme = new ColrTheme(p);
		XML[] colors = (xml.getChildren("channel/items/item"));	
		for (int i = 0; i < colors.length; i++) {
			XML item= colors[i];
			String title = item.getChildren("title")[0].getContent();
			if(title.matches("[0-9A-Fa-f]{6}")){
				theme.addColor(PApplet.unhex("FF"+title));
				theme.addThemeTags(item.getChildren("description/tags")[0].getContent());
			}
		}
		return theme;
	}
	
	/**
	 * Query the Colr service with the given tag. 
	 * Returns an array with all returned schemes as ColrThemes which stores the colors and 
	 * the tags associated with the scheme on Colr. 
	 * @param tag
	 * @return ColrTheme[]: array contains all theme matching the query
	 */
	public ColrTheme[] searchForThemes(final String tag){
		return searchForThemes(tag, null, DEFAULT);
	}
	
	public ColrTheme[] searchForThemes(final String tag, final String filename){
		return searchForThemes(tag, filename, DEFAULT);
	}
	
	/**
	 * @param filename  Filename to save the result xml, respectively load the xml if it still exists
	 */
	public ColrTheme[] searchForThemes(final String tag, final String filename, final int mode){
		String[] modes = {"tag/", "latest", "random"};
		StringBuffer url = new StringBuffer("http://www.colr.org/rss/").append(modes[mode]);

		if(mode == DEFAULT){
			url.append(tag);
		}
		XML xml = getXML(url.toString(), filename);
		
		ArrayList themes = new ArrayList();
		XML[] items = (xml.getChildren("channel/items/item"));
		for (int i = 0; i < items.length; i++) {
			XML item = items[i];
			String title = item.getChildren("title")[0].getContent();
			if(title.startsWith("scheme")){
				ColrTheme theme = new ColrTheme(p);
				String[] colors = item.getChildren("description/colors")[0].getContent().split(" ");
				for (int j = 0; j < colors.length; j++) {
					String cl = colors[j];
					if(cl.matches("[0-9A-Fa-f]{6}")){
						theme.addColor(PApplet.unhex("FF"+cl));
					}
				}
				theme.addThemeTags(item.getChildren("description/tags")[0].getContent());
				themes.add(theme);
			}
		}
		return ((ColrTheme[]) themes.toArray(new ColrTheme[themes.size()]));
	}
	
	
	public ColrTheme[] getLatest(final String filename) {
		return searchForThemes(null, filename, LATEST);
	}
	
	public ColrTheme[] getRandom(final String filename) {
		return searchForThemes(null, filename, RANDOM);
	}
	
	/**
	 * @deprecated
	 */
	public ColrTheme[] searchForTag(final String tag,  final String filename){
		return searchForThemes(tag, filename, DEFAULT);
	}
	
	/**
	 * @deprecated
	 */
	public ColrTheme[] searchForTag(final String tag){
		return searchForThemes(tag, null, DEFAULT);
		
	}
}