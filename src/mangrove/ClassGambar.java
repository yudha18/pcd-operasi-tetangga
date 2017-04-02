/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mangrove;

import com.jhlabs.image.GaussianFilter;
import com.jhlabs.image.UnsharpFilter;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import java.awt.image.ConvolveOp;
import java.awt.image.ImageObserver;
import java.awt.image.Kernel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


/**
 *
 * @author Hasan Mangrove
 */
public class ClassGambar {
    // variable global
    
    public ImageIcon SourceIcon;
    public Image SourceImage;
    public ImageIcon ScaleIcon;
    public Image ScaledImage;
    public Image ResultImage;
    public Image ScaleResultImage;
    public ImageIcon ScaleResultIcon;
    public String URLImage;
    public boolean ScaledFlag = false;
    public BufferedImage SourceBuffer;
    public BufferedImage ResultBuffer;
    public long sWidth;
    public long sHeight;
    
    double derajat;
    
    private int x;
    private int y;
    
    
    
    // konstruktor
    ClassGambar (String url, long width, long height) {
        URLImage = url;
        if (width <= 0 || height <= 0) {
            ScaledFlag = false;
        } else {
            ScaledFlag = true;
            sWidth = width;
            sHeight = height;
        }
        
    }
    
    public ImageIcon GetIcon() {
        if(!URLImage.equals("")) {
            SourceIcon = new ImageIcon(URLImage);
            SourceImage = SourceIcon.getImage();
            
            try {
                SourceBuffer = ImageIO.read(new File(URLImage));
            } catch (IOException x) {
                JOptionPane.showMessageDialog(null, x.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
            }
            System.out.println(SourceIcon.getIconWidth());
            if(ScaledFlag) {
                ScaledImage = SourceImage.getScaledInstance((int) sWidth, (int) sHeight, Image.SCALE_DEFAULT);
                ScaleIcon = new ImageIcon(ScaledImage);
                return ScaleIcon;
            } else {
                return SourceIcon;
            }
        }
        return null;
    }
    
    // method Grayscale
    public void Grayscale(){
        ResultBuffer = deepCopy(SourceBuffer);
        long tWidth = ResultBuffer.getWidth();
        long tHeight = ResultBuffer.getHeight();
        
        long x, y;
        int RGB, Red, Green, Blue, Gray;
        
        Color tWarna;
        for(x = 0; x < tWidth; x++) {
            for(y = 0; y < tHeight; y++){
                RGB = ResultBuffer.getRGB((int) x, (int) y);
                tWarna = new Color(RGB);
                
                Red = tWarna.getRed();
                Green = tWarna.getGreen();
                Blue = tWarna.getBlue();
                Gray = tWarna.getBlue();
                
                tWarna = new Color(Gray, Gray, Gray);
                ResultBuffer.setRGB((int) x, (int) y, tWarna.getRGB());
            }
        }
        
        ResultImage = (Image) ResultBuffer;
        ScaleResultImage = ResultImage.getScaledInstance((int)sWidth, (int)sHeight, Image.SCALE_DEFAULT);
        ScaleResultIcon = new ImageIcon(ScaleResultImage);
    }
    
    //fungsi biner
    public void Biner() {
        ResultBuffer = deepCopy(SourceBuffer);
        long tWidth = ResultBuffer.getWidth();
        long tHeight = ResultBuffer.getHeight();
        
        long x, y;
        int RGB, Red, Blue, Gray, Green;
        
        Color tWarna;
        for (x = 0; x < tWidth; x++){
            for(y = 0; y < tHeight; y++) {
                RGB = ResultBuffer.getRGB((int) x, (int) y);
                tWarna = new Color(RGB);
                
                Red = tWarna.getRed();
                Green = tWarna.getGreen();
                Blue = tWarna.getBlue();
                Gray = (Red + Green + Blue)/3;
                
                if (Gray <= 128) {
                    Gray = 0;
                } else {
                    Gray = 255;
                }
                
                tWarna = new Color(Gray, Gray, Gray);
                ResultBuffer.setRGB((int) x, (int) y, tWarna.getRGB());
            }
        }
        
        ResultImage = (Image) ResultBuffer;
        ScaleResultImage = ResultImage.getScaledInstance((int) sWidth, (int) sHeight, Image.SCALE_DEFAULT);
        ScaleResultIcon = new ImageIcon(ScaleResultImage);
    }
    
    //fungsi brightness
    public void Brightness(float input){
        ResultBuffer = deepCopy(SourceBuffer);
        long tWidth = ResultBuffer.getWidth();
        long tHeight = ResultBuffer.getHeight();
        
        
        for (int x = 0; x < tWidth; x++){
            for (int y = 0; y < tHeight; y++){
                
                Color color = new Color(ResultBuffer.getRGB(x, y));
                
                int r, g, b;
                
                r = colorRange((int) (color.getRed() + input));
                g = colorRange((int) (color.getGreen() + input));
                b = colorRange((int) (color.getBlue() + input));
                
                color = new Color(r, g, b);
                ResultBuffer.setRGB(x, y, color.getRGB());
                
            }
        }
        ResultImage = (Image) ResultBuffer;
        ScaleResultImage = ResultImage.getScaledInstance((int)sWidth, (int)sHeight, Image.SCALE_DEFAULT);
        ScaleResultIcon = new ImageIcon(ScaleResultImage);
    }
    
    // membatasi warna
    public int colorRange(int newColor){
        if(newColor > 255) {
            newColor = 255;
        } else if (newColor < 0) {
            newColor = 0;
        }
        return newColor;
    }
    
    
    //fungsi default
    public void Default(){
        ResultBuffer = deepCopy(SourceBuffer);
        long tWidth = ResultBuffer.getWidth();
        long tHeight = ResultBuffer.getHeight();
        
        long x, y;
        int RGB, Green, Blue, Red;
        
        Color tWarna;
        for(x = 0; x < tWidth; x++){
            for(y = 0; y <tHeight; y++) {
                RGB = ResultBuffer.getRGB((int)x, (int)y);
                tWarna = new Color(RGB);
                
                Red = tWarna.getRed();
                Green = tWarna.getGreen();
                Blue = tWarna.getBlue();
                
                tWarna = new Color(Red, Green, Blue);
                ResultBuffer.setRGB((int)x, (int)y, tWarna.getRGB());
                
            }
        }
        
        ResultImage = (Image) ResultBuffer;
        ScaleResultImage = ResultImage.getScaledInstance((int)sWidth, (int)sHeight, Image.SCALE_DEFAULT);
        ScaleResultIcon = new ImageIcon(ScaleResultImage);
    }
    
    static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
    
    public void SaveImage(String url) {
        File tFile = new File(url);
        System.out.println(url);
        try{
            String fileName = tFile.getCanonicalPath();
            if(!fileName.endsWith(".jpeg")) {
                tFile = new File(fileName + ".jpeg");
            }
            ImageIO.write(ResultBuffer, "jpeg", tFile);
            System.out.println("sukses");
        } catch(IOException x) {
            JOptionPane.showMessageDialog(null, x.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // method flip vertikal
    public void flipV(){
        ResultBuffer = deepCopy(SourceBuffer);
        BufferedImage img = new BufferedImage(SourceBuffer.getWidth(), SourceBuffer.getHeight(), BufferedImage.TYPE_INT_RGB);
        
        for (int y = 0; y < SourceBuffer.getHeight(); y++) {
            for (int x = 0; x < SourceBuffer.getWidth(); x++) {
                img.setRGB(x, y, SourceBuffer.getRGB(x, SourceBuffer.getHeight()-1-y));
            }
        }
        ResultImage = img;
        ScaleResultImage = ResultImage.getScaledInstance((int) sWidth, (int) sHeight, Image.SCALE_DEFAULT);
        ScaleResultIcon = new ImageIcon(ScaleResultImage);
    }
    
    // method flip horizontal
    public void flipH() {
        ResultBuffer = deepCopy(SourceBuffer);
        BufferedImage img = new BufferedImage(SourceBuffer.getWidth(), SourceBuffer.getHeight(), BufferedImage.TYPE_INT_RGB);
        
        for (int y = 0; y < SourceBuffer.getHeight(); y++) {
            for (int x = 0; x < SourceBuffer.getWidth(); x++) {
                img.setRGB(x, y, SourceBuffer.getRGB(SourceBuffer.getWidth()-1-x, y));
            }
        }
        ResultImage = img;
        ScaleResultImage = ResultImage.getScaledInstance((int) sWidth, (int) sHeight, Image.SCALE_DEFAULT);
        ScaleResultIcon = new ImageIcon(ScaleResultImage);
    }
    
    // method zoom in
    public void zoomIn(){
        ResultBuffer = deepCopy(SourceBuffer);
        ResultImage = (Image) ResultBuffer;
        sWidth = sWidth * 2;
        sHeight = sHeight * 2;
        ScaleResultImage = ResultImage.getScaledInstance((int) sWidth, (int) sHeight, Image.SCALE_DEFAULT);
        ScaleResultIcon = new ImageIcon(ScaleResultImage);
    }
    
    // method zoom out
    public void zoomOut(){
        ResultBuffer = deepCopy(SourceBuffer);
        ResultImage = (Image) ResultBuffer;
        sWidth = sWidth / 2;
        sHeight = sHeight / 2;
        ScaleResultImage = ResultImage.getScaledInstance((int) sWidth, (int) sHeight, Image.SCALE_DEFAULT);
        ScaleResultIcon = new ImageIcon(ScaleResultImage);
    }
    
    
    /*
    
    public void rotasiCitra(int derajat){
        
        //ResultBuffer = deepCopy(SourceBuffer);
        //BufferedImage img = new BufferedImage(SourceBuffer.getWidth(), SourceBuffer.getHeight(), BufferedImage.TYPE_INT_ARGB);
        
        double sin = Math.sin(derajat);
        double cos = Math.cos(derajat);
        
        int newW = (int) Math.floor(SourceBuffer.getWidth() * cos + SourceBuffer.getHeight() * sin);
        int newH = (int) Math.floor(SourceBuffer.getHeight() * cos + SourceBuffer.getWidth() * sin);
        
        ResultBuffer = new BufferedImage(newW, newH, SourceBuffer.getType());
        Graphics2D g = ResultBuffer.createGraphics();
        g.translate((newW - SourceBuffer.getWidth())/2, (newH - SourceBuffer.getHeight())/2);
        g.rotate(Math.toRadians(derajat), SourceBuffer.getWidth()/2, SourceBuffer.getHeight()/2);
        g.drawRenderedImage(SourceBuffer, null);
        
        ResultImage = (Image) ResultBuffer;
        ScaleResultImage = ResultImage.getScaledInstance((int) sWidth, (int) sHeight, Image.SCALE_DEFAULT);
        ScaleResultIcon = new ImageIcon(ScaleResultImage);
    }
    
    */
    
    // Image Translasi
    public void translasiHorizontal (int value) {
        ResultBuffer = deepCopy(SourceBuffer);
        long tWidth = ResultBuffer.getWidth();
        long tHeight = ResultBuffer.getHeight();
        
        long x, y;
        int RGB, Green, Blue, Red;
        
        Color tWarna;
        for(x = 0; x < tWidth; x++){
            for(y = 0; y <tHeight; y++) {
                RGB = ResultBuffer.getRGB((int)x, (int)y);
                tWarna = new Color(RGB);
                
                Red = tWarna.getRed();
                Green = tWarna.getGreen();
                Blue = tWarna.getBlue();
                
                tWarna = new Color(Red, Green, Blue);
                
                long temp = x;
                
                if (temp > tWidth) {
                    temp = tWidth;
                }
                if (temp > tHeight) {
                    temp = tHeight;
                }
                
                ResultBuffer.setRGB((int)temp, (int)y, tWarna.getRGB());
                
            }
        }
        
        ResultImage = (Image) ResultBuffer;
        ScaleResultImage = ResultImage.getScaledInstance((int)sWidth, (int)sHeight, Image.SCALE_DEFAULT);
        ScaleResultIcon = new ImageIcon(ScaleResultImage);
    }
    
    // Image Translasi
    public void translasiVertikal (int value) {
        ResultBuffer = deepCopy(SourceBuffer);
        BufferedImage img = new BufferedImage(SourceBuffer.getWidth(), SourceBuffer.getHeight(), BufferedImage.TYPE_INT_RGB);
        
        for (int y = 0; y < SourceBuffer.getHeight(); y++) {
            
            for (int x = 0; x < SourceBuffer.getWidth(); x++) {
                
                img.setRGB(x, y, SourceBuffer.getRGB(x, y));
                
            }
        }
        
        ResultImage = img;
        ScaleResultImage = ResultImage.getScaledInstance((int) sWidth, (int) sHeight, Image.SCALE_DEFAULT);
        ScaleResultIcon = new ImageIcon(ScaleResultImage);
    }
    
    
    public void rotasiCitra (double derajat, ImageObserver o) {
        ImageIcon icon = new ImageIcon(SourceBuffer);
        ResultBuffer = deepCopy(SourceBuffer);
        BufferedImage blankCanvas = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
        
        Graphics2D g2 = (Graphics2D) blankCanvas.getGraphics();
        g2.rotate(Math.toRadians(derajat), icon.getIconWidth()/2, icon.getIconHeight()/2);
        g2.drawImage(SourceBuffer, 0, 0, o);
        
        
        
        ResultBuffer = blankCanvas;
        
        ScaleResultImage = ResultBuffer.getScaledInstance((int) sWidth, (int) sHeight, Image.SCALE_DEFAULT);
        ScaleResultIcon = new ImageIcon(ScaleResultImage);
        
        
    }
    
    public void sharpenFilter() {
        ResultBuffer = deepCopy(SourceBuffer);
        
        //double factor   = 1.0;
        //double bias     = 0.0;
        
        float[] identityKernel = {
             0, -1,   0,
            -1,  5,  -1,
             0, -1,   0
        };
        
        BufferedImageOp op = new ConvolveOp(new Kernel(3,3,identityKernel));
        BufferedImage filteredImage = new BufferedImage(SourceBuffer.getWidth(), SourceBuffer.getHeight(), SourceBuffer.getType());
        op.filter(SourceBuffer, filteredImage);
        
        ResultImage = filteredImage;
        ScaleResultImage = ResultImage.getScaledInstance((int)sWidth, (int)sHeight, Image.SCALE_DEFAULT);
        ScaleResultIcon = new ImageIcon(ScaleResultImage);
    }
    
    public void blur() {
        ResultBuffer = deepCopy(SourceBuffer);
        
        //double factor   = 1.0;
        //double bias     = 0.0;
        
        float[] identityKernel = {
            1/9f,  1/9f,   1/9f,
            1/9f,  1/9f,   1/9f,
            1/9f,  1/9f,   1/9f,
        };
        
        BufferedImageOp op = new ConvolveOp(new Kernel(3,3,identityKernel));
        BufferedImage filteredImage = new BufferedImage(SourceBuffer.getWidth(), SourceBuffer.getHeight(), SourceBuffer.getType());
        op.filter(SourceBuffer, filteredImage);
        
        ResultImage = filteredImage;
        ScaleResultImage = ResultImage.getScaledInstance((int)sWidth, (int)sHeight, Image.SCALE_DEFAULT);
        ScaleResultIcon = new ImageIcon(ScaleResultImage);
    }
    
    public void edgeDetection() {
        ResultBuffer = deepCopy(SourceBuffer);
        
        //double factor   = 1.0;
        //double bias     = 0.0;
        
        float[] identityKernel = {
             1,  0, -1,
             0,  0,  0,
            -1,  0,  1
        };
        
        BufferedImageOp op = new ConvolveOp(new Kernel(3,3,identityKernel));
        BufferedImage filteredImage = new BufferedImage(SourceBuffer.getWidth(), SourceBuffer.getHeight(), SourceBuffer.getType());
        op.filter(SourceBuffer, filteredImage);
        
        ResultImage = filteredImage;
        ScaleResultImage = ResultImage.getScaledInstance((int)sWidth, (int)sHeight, Image.SCALE_DEFAULT);
        ScaleResultIcon = new ImageIcon(ScaleResultImage);
    }
    
    public void emboss() {
        ResultBuffer = deepCopy(SourceBuffer);
        
       float[] identityKernel = {
             -2,  -1,  0,
              -1,  1,  1,
              0,  1,  2
        };
        
        BufferedImageOp op = new ConvolveOp(new Kernel(3,3,identityKernel));
        BufferedImage filteredImage = new BufferedImage(SourceBuffer.getWidth(), SourceBuffer.getHeight(), SourceBuffer.getType());
        op.filter(SourceBuffer, filteredImage);
        
        ResultImage = filteredImage;
        ScaleResultImage = ResultImage.getScaledInstance((int)sWidth, (int)sHeight, Image.SCALE_DEFAULT);
        ScaleResultIcon = new ImageIcon(ScaleResultImage);
    }
    
    public void sharp() {
        ResultBuffer = deepCopy(SourceBuffer);
        
       float[] identityKernel = {
             -1,  -1,  -1,
              -1,  9,  -1,
              -1,  -1,  -1
        };
        
        BufferedImageOp op = new ConvolveOp(new Kernel(3,3,identityKernel));
        BufferedImage filteredImage = new BufferedImage(SourceBuffer.getWidth(), SourceBuffer.getHeight(), SourceBuffer.getType());
        op.filter(SourceBuffer, filteredImage);
        
        ResultImage = filteredImage;
        ScaleResultImage = ResultImage.getScaledInstance((int)sWidth, (int)sHeight, Image.SCALE_DEFAULT);
        ScaleResultIcon = new ImageIcon(ScaleResultImage);
    }
    
    public void highPass() {
        ResultBuffer = deepCopy(SourceBuffer);
        
       float[] identityKernel = {
             -1,  -1,  -1,
              -1,  16,  -1,
              -1,  -1,  -1
        };
        
        BufferedImageOp op = new ConvolveOp(new Kernel(3,3,identityKernel));
        BufferedImage filteredImage = new BufferedImage(SourceBuffer.getWidth(), SourceBuffer.getHeight(), SourceBuffer.getType());
        op.filter(SourceBuffer, filteredImage);
        
        ResultImage = filteredImage;
        ScaleResultImage = ResultImage.getScaledInstance((int)sWidth, (int)sHeight, Image.SCALE_DEFAULT);
        ScaleResultIcon = new ImageIcon(ScaleResultImage);
    }
    
//    public void unSharp() {
//        ResultBuffer = deepCopy(SourceBuffer);
//        
//        
//       float[] identityKernel = {
//             1, 4, 6, 4, 1,
//             4, 16, 24, 16, 4,
//             6, 24, -476, 24, 6,
//             4, 16, 24, 16, 4,
//             1, 4, 6, 4, 1
//        };
//        
//        BufferedImageOp op = new ConvolveOp(new Kernel(5,5,identityKernel));
//        BufferedImage filteredImage = new BufferedImage(SourceBuffer.getWidth(), SourceBuffer.getHeight(), SourceBuffer.getType());
//        op.filter(SourceBuffer, filteredImage);
//        
//        ResultImage = filteredImage;
//        ScaleResultImage = ResultImage.getScaledInstance((int)sWidth, (int)sHeight, Image.SCALE_DEFAULT);
//        ScaleResultIcon = new ImageIcon(ScaleResultImage);
//    }
    
    public void unSharp2() {
        ResultBuffer = deepCopy(SourceBuffer);
        // library JHLABS
        UnsharpFilter unsharp = new UnsharpFilter();
        
        
        
//       float[] identityKernel = {
//             1, 4, 6, 4, 1,
//             4, 16, 24, 16, 4,
//             6, 24, -476, 24, 6,
//             4, 16, 24, 16, 4,
//             1, 4, 6, 4, 1
//        };
        
        //BufferedImageOp op = new ConvolveOp(new Kernel(5,5,identityKernel));
        BufferedImage filteredImage = new BufferedImage(SourceBuffer.getWidth(), SourceBuffer.getHeight(), SourceBuffer.getType());
        //op.filter(SourceBuffer, filteredImage);
        unsharp.filter(SourceBuffer, filteredImage); // default radiusnya 2;
        ResultImage = filteredImage;
        ScaleResultImage = ResultImage.getScaledInstance((int)sWidth, (int)sHeight, Image.SCALE_DEFAULT);
        ScaleResultIcon = new ImageIcon(ScaleResultImage);
    }
    
    public void gaussian() {
        ResultBuffer = deepCopy(SourceBuffer);
        // library JHLABS
        GaussianFilter gauss = new GaussianFilter();
        BufferedImage filteredImage = new BufferedImage(SourceBuffer.getWidth(), SourceBuffer.getHeight(), SourceBuffer.getType());
        
        gauss.filter(SourceBuffer, filteredImage);
        ResultImage = filteredImage;
        ScaleResultImage = ResultImage.getScaledInstance((int)sWidth, (int)sHeight, Image.SCALE_DEFAULT);
        ScaleResultIcon = new ImageIcon(ScaleResultImage);
    }
    
    /*
    public void rotation (double derajat) {
        ResultBuffer = deepCopy(SourceBuffer);
        BufferedImage img = new BufferedImage(SourceBuffer.getWidth(), SourceBuffer.getHeight(), BufferedImage.TYPE_INT_RGB);
        
        double sin = Math.abs(Math.sin(derajat));
        double cos = Math.abs(Math.cos(derajat));
        
        int w = img.getWidth();
        int h = img.getHeight();
        
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);
        
        GraphicsConfiguration gc = getDefaultConfiguration();
        BufferedImage result = gc.createCompatibleImage(newWidth, newHeight, Transparency.TRANSLUCENT);
        Graphics2D g = result.createGraphics();
        g.translate((newWidth - w) / 2, (newHeight - h) / 2);
        g.rotate(derajat, w / 2, h / 2);
        
        RenderedImage image = img;
        
       
        ScaleResultIcon = new ImageIcon(ScaleResultImage);
        
    }
    
    private static GraphicsConfiguration getDefaultConfiguration(){
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        return gd.getDefaultConfiguration();
    }
    */

}
