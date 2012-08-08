import net.hellonico.colorlib.*;
import net.hellonico.potato.*;
import colorLib.webServices.*;

ColorLibrary k;
KulerTheme[] kt;

void setup()
{
  size(450, 400);
  
  k = new ColorLibrary(this);
  kt = (KulerTheme[]) k.search("blue");
}

void draw() {
  for (int i = 0; i < kt.length; i++) {
    for (int j = 0; j < kt[i].totalSwatches(); j++) {
      fill(kt[i].getColor(j));
      rect(j*90, i*20, 90, 20);
    }
  }
}

