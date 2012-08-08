package colorLib.calculation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;

import processing.core.PApplet;
import processing.core.PImage;
import colorLib.Palette;

/**
 * Creates a MedianCut object
 * 
 * @author Andreas K&ouml;berle
 * @author Jan Vantomme
 * 
 */

public class MedianCut
{

	private Hashtable histogram;

	private Cube[] cubes;

	private PApplet p;

	int cnt = 0;

	/**
	 * Creates a MedianCut object.
	 * @param i_p
	 */
	
	public MedianCut(PApplet i_p)
	{
		p = i_p;
	}

	/**
	 * @param colors
	 * @param cnt
	 * @return result
	 */
	
	public int[] calc(int[] colors, int cnt)
	{
		histogram = new Hashtable();
		
		for (int i = 0; i < colors.length; i++) {
			Integer color = new Integer(colors[i]);
			if (histogram.containsKey(color)) {
				histogram.put(color, new Integer(((Integer) histogram
						.get(color)).intValue() + 1));
			} else {
				histogram.put(color, new Integer(1));
			}
		}
		
		HashSet h = new HashSet();
		
		for (int i = 0; i < colors.length; i++) {
			h.add(new Integer(colors[i]));
		}
		
		colors = new int[h.size()];
		Iterator iter = h.iterator();	
		int cnter = 0;
		while (iter.hasNext()) {
			colors[cnter] = ((Integer) iter.next()).intValue();
			cnter++;
		}
		
		int ncubes = 0;
		Cube cube = new Cube(colors, 0);
		cube.level = 0;
		shrink(cube);
		cubes = new Cube[cnt];
		cubes[ncubes++] = cube;
		
		while (ncubes < cnt) {
			int nextCube = -1, colorCnt = 1;
			for (int i = 0; i < ncubes; i++) {
				int length = cubes[i].count;

				if (length > colorCnt) {
					colorCnt = length;
					nextCube = i;
				}
			}
			if (nextCube == -1) {
				break;
			}
			cube = cubes[nextCube];
			int lr = cube.rmax - cube.rmin;
			int lg = cube.gmax - cube.gmin;
			int lb = cube.bmax - cube.bmin;
			if (lr > lg && lr > lb) {
				cube.sort(0);
			} else if (lg > lb) {
				cube.sort(1);
			} else {
				cube.sort(2);
			}
			cubes[ncubes++] = cube.split();
		}
		
		int[] result = new int[ncubes];
		for (int i = 0; i < ncubes; i++) {
			result[i] = cubes[i].getAverage();
		}
		
		return result;
	}

	/**
	 * 
	 * @param i_image
	 * @param cnt
	 * @return calc
	 */
	public int[] calc(PImage i_image, int cnt)
	{
		return calc(i_image.pixels, cnt);
	}

	/**
	 * 
	 * @param i_palette
	 * @param cnt
	 * @return calc
	 */
	public int[] calc(Palette i_palette, int cnt)
	{
		return calc(i_palette.getColors(), cnt);
	}

	/**
	 * 
	 * @param cube
	 */
	private void shrink(Cube cube)
	{
		int r, g, b;
		int color;
		int rmin, rmax, gmin, gmax, bmin, bmax;

		rmin = 255;
		rmax = 0;
		gmin = 255;
		gmax = 0;
		bmin = 255;
		bmax = 0;
		for (int i = 0; i < cube.colors.length; i++) {
			color = cube.colors[i];
			r = color >> 16 & 0xFF;
			g = color >> 8 & 0xFF;
			b = color & 0xFF;

			if (r > rmax)
				rmax = r;
			if (r < rmin)
				rmin = r;
			if (g > gmax)
				gmax = g;
			if (g < gmin)
				gmin = g;
			if (b > bmax)
				bmax = b;
			if (b < bmin)
				bmin = b;
		}
		cube.rmin = rmin;
		cube.rmax = rmax;
		cube.gmin = gmin;
		cube.gmax = gmax;
		cube.bmin = bmin;
		cube.bmax = bmax;
	}
	
	/**
	 * Cube class
	 *
	 */
	private class Cube
	{
		int level, count, rmin, rmax, gmin, gmax, bmin, bmax;

		int[] colors;

		/**
		 * 
		 * @param colors
		 * @param i_level
		 */
		Cube(int[] colors, int i_level)
		{
			this.colors = colors;
			countColor();
			level = i_level;
			for (int i = 0; i < colors.length; i++) {
				p.pushMatrix();
				p.fill(colors[i]);
				p.translate(i * 10, cnt * 10);
				p.rect(0, 0, 10, 10);
				p.popMatrix();
			}
			cnt++;
		}
		
		/**
		 * 
		 * @param shiftingStep
		 */
		public void sort(int shiftingStep)
		{
			ArrayList sort = new ArrayList();
			Hashtable sortTable= new Hashtable();
			for (int i = 0; i < colors.length; i++) {
				Integer sortKey=new Integer(colors[i]>>shiftingStep);
				if(sortTable.contains(sortKey)){
					ArrayList l = (ArrayList) sortTable.get(sortKey);
					l.add(new Integer(colors[i]));
					sortTable.put(sortKey, l);
				}else{
					ArrayList l = new ArrayList();
					l.add(new Integer(colors[i]));
					sortTable.put(sortKey, l);
					sort.add(sortKey);
				}
			}
			ArrayList a = new ArrayList(sortTable.keySet());
		    Collections.sort(a);
		    
		    Iterator iter = a.iterator();
		    cnt = 0;
		    while (iter.hasNext()) {
				Integer key = (Integer) iter.next();
				ArrayList l = (ArrayList) sortTable.get(key);
				Iterator iterL = l.iterator();
				while (iterL.hasNext()) {
					colors[cnt] = ((Integer) iterL.next()).intValue();
					cnt++;
				}
			}
		}

		/**
		 * 
		 * @return
		 */
		
		Cube split()
		{
			int cnt = 0, i = 0;
			for (; i < colors.length; i++) {
				if (cnt >= colors.length / 2)
					break;
				cnt += ((Integer) histogram.get(new Integer(colors[i])))
						.intValue();
				// PApplet.println("color: "+colors[i]+" count: "+((Integer)
				// histogram.get(new Integer(colors[i]))).intValue());
			}
			int median = i;
			Cube cube = new Cube(PApplet.subset(colors, 0, median), level + 1);
			colors = PApplet.subset(colors, median);
			countColor();
			return cube;
		}

		/**
		 * Counts the colors.
		 */
		
		void countColor()
		{
			 count = colors.length;
//			count = 0;
//			for (int i = 0; i < colors.length; i++) {
//				count += ((Integer) histogram.get(new Integer(colors[i])))
//						.intValue();
//			}
		}

		/**
		 * Returns the average.
		 */
		
		int getAverage() 
		{
			int a = 0;
			int r = 0;
			int g = 0;
			int b = 0;
			int l = colors.length;
			for (int i = 0; i < l; i++) {
				int c = colors[i];
				a += c >> 24 & 0xff;
				r += c >> 16 & 0xff;
				g += c >> 8 & 0xff;
				b += c & 0xff;
			}
			return ((int) (a / l) << 24) | ((int) (r / l) << 16)
					| ((int) (g / l) << 8) | (int) (b / l);
		}

	}
}
