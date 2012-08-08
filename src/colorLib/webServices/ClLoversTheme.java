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
public class ClLoversTheme extends Palette
{
	
	private String title, userName, description, url;
	private int  numViews, numVotes, numComments, rank;
	private float numHearts;
	private Date dateCreated;
	
	/**
	 * @param i_p PApplet
	 */		
	public ClLoversTheme(PApplet i_p)
	{
		super(i_p);
	}

	/**
	 * Returns the date when the theme was created.
	 * @return the dateCreated
	 */
	public Date getDateCreated()
	{
		return dateCreated;
	}

	/**
	 * @param dateCreated the dateCreated to set
	 */
	protected void setDateCreated(String dateCreated)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
		try {
			this.dateCreated = format.parse(dateCreated);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the description of the theme.
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param description the description to set
	 */
	protected void setDescription(String description) 
	{
		this.description = description;
	}

	/**
	 * Returns the number of comments for the theme.
	 * @return the numComments
	 */
	public int getNumComments()
	{
		return numComments;
	}

	/**
	 * @param numComments the numComments to set
	 */
	protected void setNumComments(String numComments)
	{
		this.numComments = Integer.valueOf(numComments).intValue();
	}

	/**
	 * Returns the number of hearts for the theme.
	 * @return the numHearts
	 */
	public float getNumHearts()
	{
		return numHearts;
	}

	/**
	 * @param numHearts the numHearts to set
	 */
	protected void setNumHearts(String numHearts)
	{
		this.numHearts = Float.valueOf(numHearts).intValue();
	}

	/**
	 * Returns the number of views for the theme.
	 * @return the numViews
	 */
	public int getNumViews()
	{
		return numViews;
	}

	/**
	 * @param numViews the numViews to set
	 */
	protected void setNumViews(String numViews)
	{
		this.numViews = Integer.valueOf(numViews).intValue();
	}

	/**
	 * Returns the number of votes for the theme.
	 * @return the numVotes
	 */
	public int getNumVotes()
	{
		return numVotes;
	}

	/**
	 * @param numVotes the numVotes to set
	 */
	protected void setNumVotes(int numVotes)
	{
		this.numVotes = numVotes;
	}

	/**
	 * Returns the rank for the theme.
	 * @return the rank
	 */
	public int getRank()
	{
		return rank;
	}

	/**
	 * @param string the rank to set
	 */
	protected void setRank(String rank)
	{
		this.rank = Integer.valueOf(rank).intValue();
	}

	/**
	 * Returns the title of the theme.
	 * @return the title
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * @param title the title to set
	 */
	protected void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * Returns the url of the theme.
	 * @return the url
	 */
	public String getUrl()
	{
		return url;
	}

	/**
	 * @param url the url to set
	 */
	protected void setUrl(String url)
	{
		this.url = url;
	}

	/**
	 * Returns the username of the creator of the theme.
	 * @return the userName
	 */
	public String getUserName()
	{
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	protected void setUserName(String userName)
	{
		this.userName = userName;
	}
}