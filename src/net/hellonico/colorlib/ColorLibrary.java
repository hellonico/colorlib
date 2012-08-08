/**
 * ##library.name##
 * ##library.sentence##
 * ##library.url##
 *
 * Copyright ##copyright## ##author##
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA  02111-1307  USA
 * 
 * @author      ##author##
 * @modified    ##date##
 * @version     ##library.prettyVersion## (##library.version##)
 */

package net.hellonico.colorlib;


import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;

import processing.core.PApplet;
import colorLib.webServices.Kuler;

public class ColorLibrary extends Kuler {
	
	public final static String VERSION = "##library.prettyVersion##";
	
	public ColorLibrary(PApplet theParent) {
		super(theParent);
		
		try {
			Class klass = Class.forName("net.hellonico.potato.Potato");
			Constructor c = klass.getConstructor(PApplet.class);
			Object potato = c.newInstance(theParent);
			Method m = klass.getMethod("getSettings", String.class);
			HashMap settings = (HashMap) m.invoke(potato, "kuler");
			
			super.key = (String) settings.get("appKey");
		} catch (Exception e) {
			throw new RuntimeException("This is carrot day."+e.getMessage());
		}
		
	}
	
}

