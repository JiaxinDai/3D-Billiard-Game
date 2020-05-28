package billiard.renderer3d.parser.wavefront;

import java.util.ArrayList;
import java.util.List;

import billiard.renderer3d.core.Material;
import billiard.renderer3d.parser.wavefront.WavefrontParser.Face;

public class Obj {
    
    public List<Face> faces = new ArrayList<Face>();
    public Material material;
    
}
