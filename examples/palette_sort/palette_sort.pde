import colorLib.calculation.*;
import colorLib.*;
import colorLib.webServices.*;

Palette p;
Palette ps;
PFont font;

String theText;

void setup()
{
    size(360, 300);
    
    font = createFont("Verdana", 16);
    textFont(font, 16);
    textLeading(16);
    
    generate();
    
}

void draw()
{
    background(0);
    
    pushMatrix();
    translate(20, 20);
    p.drawSwatches();
    text("Original Palette", 140, 16);
    popMatrix();
    
    pushMatrix();
    translate(20, 100);
    ps.drawSwatches();
    text(theText, 140, 16);
    popMatrix();

    pushMatrix();
    translate(20, 220);
    fill(255);
    text("Press 'r' to generate a new palette. ", 0, 0);
    text("Press 'h' to sort by Hue. ", 0, 20);
    text("Press 's' to sort by Saturation. ", 0, 40);
    text("Press 'l' to sort by Luminance. ", 0, 60);
    popMatrix();

}

void generate()
{
    theText = "";
    
    p = new Palette(this);
    
    for (int i = 0; i < 5; i++) {
        p.addColor( color(random(255), random(255), random(255) ) );
    }
    
    ps = new Palette(this);
    for (int i = 0; i < 5; i++) {
        ps.addColor( p.getColor(i) );
    }
}

void keyPressed()
{
    if (key == 'r') {
        generate();
    }
    
    if (key == 'h') {
        ps.sortByHue();
        theText = "Sorted by Hue";
    }
    
    if (key == 's') {
        ps.sortBySaturation();
        theText = "Sorted by Saturation";
    }
    
    if (key == 'l') {
        ps.sortByLuminance();
        theText = "Sorted by Luminance";
    }
}
