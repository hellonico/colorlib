import colorLib.calculation.*;
import colorLib.*;
import colorLib.webServices.*;

Palette p1;
Palette p2;
Palette p3;
PImage  img;

void setup()
{
    size(400, 400);
    
    background(0);
    
    translate(20, 20);
    
    p1 = new Palette(this, "test.act");
    p1.deleteDuplicate();
    p1.drawSwatches();
    
    translate(0, 60);

    p2 = new Palette(this, "Pumpkin.cs");
    p2.deleteDuplicate();
    p2.drawSwatches();
    
    translate(0, 60);
    
    img = loadImage("test32.gif");
    p3 = new Palette(this, img);
    p3.drawSwatches();
}
