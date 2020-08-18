package ImgWork.Converter;

public class RgbHsvConverter {

     public static int[] HSVtoRGB (float h, float s, float v) {
         float H = h, S = s, V = v; // H, S, V ∈ [0, 1]
         float r = 0, g = 0, b = 0;
         float hh = (6 * H) % 6;
         int c1 = (int) hh; // c1 ← ⌊h

         float c2 = hh - c1;
         float x = (1 - S) * V;
         float y = (1 - (S * c2)) * V;
         float z = (1 - (S * (1 - c2))) * V;
         switch (c1) {
             case 0: r = V; g = z; b = x; break;
             case 1: r = y; g = V; b = x; break;
             case 2: r = x; g = V; b = z; break;
             case 3: r = x; g = y; b = V; break;
             case 4: r = z; g = x; b = V; break;
             case 5: r = V; g = x; b = y; break;
             }
         int R = Math.min((int)(r * 255), 255);
         int G = Math.min((int)(g * 255), 255);
         int B = Math.min((int)(b * 255), 255);
         return new int[] {R, G, B};
         }



    public static float[] RGBtoHSV(int red, int green, int blue) {
        int R = red, G = green, B = blue; // R, G, B ∈ [0, 255]
        int cHi = Math.max(R, Math.max(G, B)); // max. comp. value
        int cLo = Math.min(R, Math.min(G, B)); // min. comp. value
        int cRng = cHi - cLo; // component range
        float H = 0, S = 0, V = 0;
        float cMax = 255.0f;

        // compute value V
         V = cHi / cMax;

         // compute saturation S
         if (cHi > 0)
             S = (float) cRng / cHi;
         // compute hue H
         if (cRng > 0) { // hue is defined only for color pixels
             float rr = (float) (cHi - R) / cRng;
             float gg = (float) (cHi - G) / cRng;
             float bb = (float) (cHi - B) / cRng;
             float hh;
             if (R == cHi) // R is largest component value
                 hh = bb - gg;
             else if (G == cHi) // G is largest component value
                 hh = rr - bb + 2.0f;
            else // B is largest component value
             hh = gg - rr + 4.0f;
             if (hh < 0)
                 hh = hh + 6;
             H = hh / 6;
        }
        return new float[]{H, S, V};
    }


}
