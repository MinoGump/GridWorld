import static org.junit.Assert.*;

import imagereader.*;

import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.imageio.*;

import java.io.*;

import org.junit.Test;


public class MyImageTest {

    private String goalDir = "/home/Administrator/workspace/MyImageReader/bmptest/goal/";
    private String outputDir = "/home/Administrator/workspace/MyImageReader/bmptest/output/";
    private String srcPic1 = "/home/Administrator/workspace/MyImageReader/bmptest/1.bmp";
    
	@Test
	public void redTestR() {
		String outPath = outputDir + "1_red_output";
        String goalPath = goalDir + "1_red_goal.bmp";
        MyImageIO io = new MyImageIO();
        MyImageProcessor pro = new MyImageProcessor();

       
		try {
			Image outImg = io.myRead(srcPic1);
	        outImg = pro.showChanelR(outImg);
	        io.myWrite(outImg, outPath+".bmp");
		} catch (IOException e1) {
            throw new RuntimeException(e1);
		}

		
        try {
            outPath += ".bmp";
            File outFile = new File(outPath);
            File goalFile = new File(goalPath);
            BufferedImage outBi = ImageIO.read(outFile);
            BufferedImage goalBi = ImageIO.read(goalFile);
            
            assertEquals(outBi.getWidth() == goalBi.getWidth(), true);
            assertEquals(outBi.getHeight() == goalBi.getHeight(), true);
            
            for (int w = 0; w < outBi.getWidth(); w++) {
            	for (int h = 0; h < outBi.getHeight(); h++) {
            		assertEquals(outBi.getRGB(w, h) == goalBi.getRGB(w, h), true);
            	}
            }
        
        } catch (IOException e) {
            assert(true);
        }
		
	}
	
	@Test
	public void redTestG() {
		String outPath = outputDir + "1_green_output";
        String goalPath = goalDir + "1_green_goal.bmp";
        MyImageIO io = new MyImageIO();
        MyImageProcessor pro = new MyImageProcessor();

		try {
			Image outImg = io.myRead(srcPic1);
	        outImg = pro.showChanelG(outImg);
	        io.myWrite(outImg, outPath+".bmp");
		} catch (IOException e1) {
            throw new RuntimeException(e1);
		}

		
        try {
            outPath += ".bmp";
            File outFile = new File(outPath);
            File goalFile = new File(goalPath);
            BufferedImage outBi = ImageIO.read(outFile);
            BufferedImage goalBi = ImageIO.read(goalFile);
            
            assertEquals(outBi.getWidth() == goalBi.getWidth(), true);
            assertEquals(outBi.getHeight() == goalBi.getHeight(), true);
            
            for (int w = 0; w < outBi.getWidth(); w++) {
            	for (int h = 0; h < outBi.getHeight(); h++) {
            		assertEquals(outBi.getRGB(w, h) == goalBi.getRGB(w, h), true);
            	}
            }
        
        } catch (IOException e) {
            assert(true);
        }
		
	}
	
	@Test
	public void redTestB() {
		String outPath = outputDir + "1_blue_output";
        String goalPath = goalDir + "1_blue_goal.bmp";
        MyImageIO io = new MyImageIO();
        MyImageProcessor pro = new MyImageProcessor();

		try {
			Image outImg = io.myRead(srcPic1);
	        outImg = pro.showChanelB(outImg);
	        io.myWrite(outImg, outPath+".bmp");
		} catch (IOException e1) {
            throw new RuntimeException(e1);
		}

        try {
            outPath += ".bmp";
            File outFile = new File(outPath);
            File goalFile = new File(goalPath);
            BufferedImage outBi = ImageIO.read(outFile);
            BufferedImage goalBi = ImageIO.read(goalFile);
            
            assertEquals(outBi.getWidth() == goalBi.getWidth(), true);
            assertEquals(outBi.getHeight() == goalBi.getHeight(), true);
            
            for (int w = 0; w < outBi.getWidth(); w++) {
            	for (int h = 0; h < outBi.getHeight(); h++) {
            		assertEquals(outBi.getRGB(w, h) == goalBi.getRGB(w, h), true);
            	}
            }
        
        } catch (IOException e) {
            assert(true);
        }
		
	}

}
