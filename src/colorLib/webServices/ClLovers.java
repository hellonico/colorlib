package colorLib.webServices;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.data.XML;

/**
 * @author Andreas K&ouml;berle
 * @author Jan Vantomme
 */

public class ClLovers extends WebService
{
	private String lover, orderCol, sortBy;

	private int[] hueRange, briRange;
	
	private int DEFAULT = 0, POPULAR = 1, LATEST = 2, RANDOM = 3;

	/**
	 * The ClLovers object is a container to query the <a href="http://www.colourlovers.com/">COLOURLovers API</a>.
	 * @param i_p
	 */
	public ClLovers(PApplet i_p)
	{
		p = i_p;
		hueRange = new int[2];
		hueRange[0]=0;
		hueRange[1]=359;
		briRange = new int[2];
		briRange[0]=0;
		briRange[1]=99;
		numResults=20;
		resultOffset=0;
	}

	/**
	 * Returns the colors as a ClLoversTheme object.
	 * @param i_keywords
	 * @return ClLoversTheme: ColorLovers Theme
	 */
	public ClLoversTheme getColors(final String i_keywords)
	{
		return getColors(i_keywords, null);
	}
	
	/**
	 * @param filename  Filename to save the result xml, respectively load the xml if it still exists
	 */
	public ClLoversTheme getColors(final String i_keywords, final String filename)
	{
		
		StringBuffer url = new StringBuffer("http://www.colourlovers.com/api/colors?").
		append("hueRange=").append(hueRange[0]).append(",").append(hueRange[1]).
		append("&briRange=").append(briRange[0]).append(",").append(briRange[1]).
		append("&numResults=").append(numResults).
		append("&resultOffset=").append(resultOffset).
		append("&keywords=").append(i_keywords);

//		PApplet.println(url);
		
//		String[] s = p.loadStrings(url.toString());
//		
//		StringBuffer buf = new StringBuffer();
//		
//		for (int i = 0; i < s.length; i++) {
//			buf.append(s[i].replaceAll("CDATA.]", "CDATA[empty]"));
//		}
		
//		XMLElement xml = new XMLElement(buf.toString()); // This line is causing big trouble!

		XML xml = getXML(url.toString(), filename);

		XML[] colors = xml.getChildren("color");
		ClLoversTheme theme = new ClLoversTheme(p);
		if (colors.length > 0) {
			for (int i = 0; i < colors.length; i++) {
				XML color = colors[i];
				theme.setTitle(color.getChildren("title")[0].getContent());
				theme.setUserName(color.getChildren("userName")[0].getContent());
				theme.setNumViews(color.getChildren("numViews")[0].getContent());
				theme.setNumComments(color.getChildren("numComments")[0].getContent());
				theme.setNumHearts(color.getChildren("numHearts")[0].getContent());
				theme.setRank(color.getChildren("rank")[0].getContent());
				theme.setDateCreated(color.getChildren("dateCreated")[0].getContent());
				theme.setDescription(color.getChildren("description")[0].getContent());
				theme.addColor(PApplet.unhex("FF" +color.getChildren("hex")[0].getContent()));
			}
		}
		return theme;
	}
	
	/**
	 * Returns the colors for a given array of keywords.
	 * @param i_keywords
	 */
	public void getColors(final String[] i_keywords)
	{
		StringBuffer keywords = new StringBuffer();
		for (int i = 0; i < i_keywords.length; i++) {
			if (i > 0)
				keywords.append("+");
			keywords.append(i_keywords[i]);
		}
		getColors(keywords.toString());
	}
	
	/**
	 * Returns the palettes for a given keyword.
	 * @param i_keywords
	 * @param filename  Filename to save the result xml, respectively load the xml if it still exists
	 * @return ClLoversTheme 
	 */
	private ClLoversTheme[] searchForThemes(final String keywords, final String filename, int mode)
	{
		String[] modes = {"?", "/top?", "/new?", "/random"};
		StringBuffer url = new StringBuffer("http://www.colourlovers.com/api/palettes?").
		append(modes[mode]);
		if(mode!=3){
			url.append("hueRange=").append(hueRange[0]).append(",").append(hueRange[1]).
			append("&briRange=").append(briRange[0]).append(",").append(briRange[1]).
			append("&numResults=").append(numResults).
			append("&resultOffset=").append(resultOffset);
			if(keywords != null){
				url.append("&keywords=").append(keywords);
			}
		}
		XML xml = getXML(url.toString(), filename);
		XML[] palettes = xml.getChildren("palette");
		ArrayList themes = new ArrayList();
		if (palettes.length > 0) {
			for (int i = 0; i < palettes.length; i++) {
				ClLoversTheme theme = new ClLoversTheme(p);
				XML palette = palettes[i];
				theme.setTitle(palette.getChildren("title")[0].getContent());
				theme.setUserName(palette.getChildren("userName")[0].getContent());
				theme.setNumViews(palette.getChildren("numViews")[0].getContent());
				theme.setNumComments(palette.getChildren("numComments")[0].getContent());
				theme.setNumHearts(palette.getChildren("numHearts")[0].getContent());
				theme.setRank(palette.getChildren("rank")[0].getContent());
				theme.setDateCreated(palette.getChildren("dateCreated")[0].getContent());
				theme.setDescription(palette.getChildren("description")[0].getContent());
				XML[] colors = palette.getChildren("colors/hex");
				for (int j = 0; j < colors.length; j++) {
					theme.addColor(PApplet.unhex("FF" +colors[j].getContent()));
				}
				themes.add(theme);
			}
		}
		return (ClLoversTheme[]) themes.toArray(new ClLoversTheme[themes.size()]);
	}
	
	/**
	 * @deprecated As of release beta2, replaced by {@link #searchForThemes()}
	 */
	public ClLoversTheme[] getPalettes(final String i_keywords, final String filename)
	{
		return searchForThemes(i_keywords, filename, DEFAULT);
	}
	
	/**
	 * @deprecated As of release beta2, replaced by {@link #searchForThemes()}
	 */
	public ClLoversTheme[] getPalettes(final String[] i_keywords, final String filename)
	{
		StringBuffer keywords = new StringBuffer();
		for (int i = 0; i < i_keywords.length; i++) {
			if (i > 0)
				keywords.append("+");
			keywords.append(i_keywords[i]);
		}
		return searchForThemes(keywords.toString(), filename, DEFAULT);
	}
	
	/**
	 * @deprecated As of release beta2, replaced by {@link #searchForThemes()}
	 */
	public ClLoversTheme[] getPalettes(final String[] i_keywords)
	{
		return searchForThemes(i_keywords, null);
	}
	
	public ClLoversTheme[] searchForThemes(final String keyword, final String filename)
	{
		return searchForThemes(keyword, filename, DEFAULT);
	}
	
	public ClLoversTheme[] searchForThemes(final String keyword)
	{
		return searchForThemes(keyword, null, DEFAULT);
	}
	
	/**
	 * Returns the palettes for a given array of keywords.
	 * @param i_keywords String: An array of keywords.
	 */
	public ClLoversTheme[] searchForThemes(final String[] i_keywords, final String filename)
	{
		StringBuffer keywords = new StringBuffer();
		for (int i = 0; i < i_keywords.length; i++) {
			if (i > 0)
				keywords.append("+");
			keywords.append(i_keywords[i]);
		}
		return searchForThemes(keywords.toString(), filename, DEFAULT);
	}
	
	public ClLoversTheme[] searchForThemes(final String[] i_keywords)
	{
		return searchForThemes(i_keywords, null);
	}
	
	public ClLoversTheme[] getPopular(final String keyword, final String filename)
	{
		return searchForThemes(keyword, filename, POPULAR);
	}
	
	public ClLoversTheme[] getPopular(final String filename)
	{
		return searchForThemes("", filename, POPULAR);
	}
	
	public ClLoversTheme[] getLatest(final String keyword, final String filename)
	{
		return searchForThemes(keyword, filename, LATEST);
	}
	
	public ClLoversTheme[] getRandom(final String keyword, final String filename)
	{
		return searchForThemes(keyword, filename, RANDOM);
	}
	

	/**
	 * Returns the brightness range.
	 * @return briRange: Brightness Range
	 */
	public int[] getBriRange()
	{
		return briRange;
	}

	/**
	 * Sets the brightness range to query the COLOURLovers API.
	 * This function takes a int[] array with a size of 2.
	 * The values of the integers should be in the range of 0 to 99.
	 * @param briRange int[]: An integer array with a size of two.
	 */
	public void setBriRange(int[] briRange)
	{
		if (briRange.length!=2) {
			throw new IllegalArgumentException( "The passed array hasn't the size of 2" ); 
		} else {
			this.briRange = briRange;
		}
	}

	/**
	 * Returns the hue range.
	 * @return hueRange: Hue Range
	 */
	public int[] getHueRange()
	{
		return hueRange;
	}

	/**
	 * Sets the hue range to query the COLOURLovers API.
	 * This function takes a int[] array with a size of 2.
	 * The values of the integers should be in the range of 0 to 359.
	 * @param hueRange int[]: An integer array with a size of two.
	 */
	public void setHueRange(int[] hueRange)
	{
		if (hueRange.length!=2) {
			throw new IllegalArgumentException( "The passed array hasn't the size of 2" ); 
		} else {
			this.hueRange = hueRange;
		}
	}

	/**
	 * @return lover: 
	 */
	public String getLover()
	{
		return lover;
	}

	/**
	 * @param lover
	 */
	public void setLover(String lover)
	{
		this.lover = lover;
	}

	/**
	 * @return orderCol
	 */
	public String getOrderCol()
	{
		return orderCol;
	}

	/**
	 * @param orderCol Either dateCreated, score, name, numVotes, numViews.
	 */
	public void setOrderCol(String orderCol)
	{
		if (orderCol.equals("dateCreated") || 
		   orderCol.equals("score") || 
		   orderCol.equals("name") || 
		   orderCol.equals("numVotes") || 
		   orderCol.equals("numViews")){
			this.orderCol = orderCol;
		} else {
			throw new IllegalArgumentException( "Only 'dateCreated', 'score', 'name', 'numVotes' or 'numViews' are allowed" ); 
		}
	}

	/**
	 * @return sortBy
	 */
	public String getSortBy()
	{
		return sortBy;
	}

	/**
	 * This function sets how the colors should be sorted.
	 * Allowed values are ASC for ascending and DESC for descending.
	 * @param sortBy Either ASC (ascending) or DESC (descending);
	 */
	public void setSortBy(String sortBy)
	{
		if (orderCol.equals("ASC") || orderCol.equals("DESC")) {
			this.sortBy = sortBy;
		} else {
			throw new IllegalArgumentException( "Only 'ASC' or 'DESC' are allowed" ); 
		}	
	}

	
	/**
	 * Use this method to print the resulting XML in the console. 
	 * @param b boolean: set this to true if you want to print the resulting XML in the console 
	 */
	public void printXML(boolean b)
	{
		printXML=b;
	}
}
