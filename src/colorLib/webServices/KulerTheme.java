package colorLib.webServices;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import processing.core.PApplet;
import colorLib.Palette;

/**
 * @author Andreas K&ouml;berle
 * @author Jan Vantomme
 */

// TODO: KulerTheme should extend Palette. Colr doesn't exist anymore?

public class KulerTheme extends Palette {
	
	private String themeID, themeTitle, authorLabel, authorID;

	private int themeRating;
	private int themeDownloadCount;
	private Date themeCreatedAt, themeEditedAt;

	protected String[] themeTags; // added
	
	protected KulerTheme(final PApplet i_p, int[] i_colors)
	{
		super(i_p, i_colors);
		this.themeTags = new String[0]; // added
	}

	/**
	 * Returns the id of the author. This function can be used to search for more themes
	 * by the same author.
	 * @return String: authorID
	 */
	public String getAuthorId()
	{
		return authorID;
	}

	protected void setAuthorID(final String authorID)
	{
		this.authorID = authorID;
	}

	/**
	 * Returns the name of the author.
	 * @return String: authorLabel
	 */
	public String getAuthorLabel()
	{
		return authorLabel;
	}

	protected void setAuthorLabel(final String authorLabel)
	{
		this.authorLabel = authorLabel;
	}

	/**
	 * Returns a string with the date the theme was created. You can use this string to change the format of the date.
	 * @return 	String: date, returns the date formated with the passed dateFormat, default is "yyyy-MM-dd",
	 * 		   	see <a href="http://java.sun.com/docs/books/tutorial/i18n/format/simpleDateFormat.html">Suns' Java tutorial</a> for 
	 * 			posibble formats.
	 */
	public String getThemeCreatedAt(String dateFormat)
	{
		SimpleDateFormat df = new SimpleDateFormat(dateFormat);
		return df.format(themeCreatedAt);
	}
	
	public String getThemeCreatedAt()
	{
		return getThemeCreatedAt("yyyy-MM-dd");
		
	}

	protected void setThemeCreatedAt(final String themeCreatedAt)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		try {
			this.themeCreatedAt = format.parse(themeCreatedAt);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Returns how often the theme was downloaded. 
	 * @return int: download count
	 */
	public int getThemeDownloadCount()
	{
		return themeDownloadCount;
	}

	protected void setThemeDownloadCount(final String themeDownloadCount)
	{
		this.themeDownloadCount = Integer.valueOf(themeDownloadCount).intValue();
	}

	/**
	 * Returns the date the theme was last modified.
	 * @return Date: date
	 */
	public Date getThemeEditedAt()
	{
		return themeEditedAt;
	}

	protected void setThemeEditedAt(final String themeEditedAt)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		try {
			this.themeEditedAt = format.parse(themeEditedAt);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the id of the theme, which can be used for another query against the Kuler service.
	 * @return String: ID
	 */
	public String getThemeID()
	{
		return themeID;
	}
	
	protected void setThemeTags(String i_themeTags)
	{
		System.out.println("themeTags: " + i_themeTags);
		if (i_themeTags != null && i_themeTags.contains(",")) {
			themeTags = PApplet.concat(themeTags, i_themeTags.split(","));
		//	System.out.println(i_themeTags.contains(",") ? "  - Found a comma" : "  - Didn't find a comma");
		}
		
		/*		if (themeTags != null || !i_themeTags.equalsIgnoreCase("")){
			themeTags = PApplet.concat(themeTags, i_themeTags.split(","));
	    } */
	}
	
	protected void setThemeID(final String themeID)
	{
		this.themeID = themeID;
	}

	/**
	 * Returns the rating of the theme.
	 * @return int: rating
	 */
	public float getThemeRating()
	{
		return themeRating;
	}

	protected void setThemeRating(final String themeRating)
	{
		this.themeRating = Integer.valueOf(themeRating).intValue();
	}

	/**
	 * Returns the title of the theme.
	 * @return String: title
	 */
	public String getThemeTitle()
	{
		return themeTitle;
	}

	protected void setThemeTitle(final String themeTitle)
	{
		this.themeTitle = themeTitle;
	}

}