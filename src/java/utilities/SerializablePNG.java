/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author Kate Nezdoly
 */
@Embeddable
public class SerializablePNG implements Serializable {

@Column(name="WIDTH")
private int width;
@Column(name="HEIGHT")
private int height;
@Column(name="IMAGETYPE")
private int imageType;
@Column(name="PIXELS")
private int[] pixels;

    public SerializablePNG() {
    }

public SerializablePNG(
final int w,
final int h,
final int imageType,
final int[] pixels
) {
this.width = w;
this.height = h;
this.imageType = imageType;
this.pixels = pixels;
}

public int getW() {
return width;
}

public int getH() {
return height;
}

public int getImageType() {
return imageType;
}

public int[] getPixels() {
return pixels;
}
    
}
