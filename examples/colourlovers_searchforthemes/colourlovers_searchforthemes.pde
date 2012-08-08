import colorLib.calculation.*;
import colorLib.*;
import colorLib.webServices.*;

ClLovers c;
ClLoversTheme[] themes;

void setup()
{
    size(1000, 600);
    background(0);
    smooth();
    
    c = new ClLovers(this);
    c.setNumResults(50);
    
    themes = c.searchForThemes("spring");
    
    for (int i = 0; i < themes.length; i++) {
        pushMatrix();
        translate( 10 + (i%7) * 130, 10 + floor(i/7) * 70);
        themes[i].drawSwatches();
        popMatrix();
    }
    
}
