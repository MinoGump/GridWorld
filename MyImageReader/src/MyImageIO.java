import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.awt.image.RenderedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import imagereader.IImageIO;

public class MyImageIO implements IImageIO {

	public static final byte[] BMP_IDENTIFIER_BYTES = "BM".getBytes();

	public static final int BMP_HEAD_SIZE = 14;
	public static final int BMP_INFO_SIZE = 40;
	
	@Override
	public Image myRead(String filePath) throws IOException {
		// write the bmpMessage by reading the stream
		BufferedInputStream stream = new BufferedInputStream(new FileInputStream(filePath));
		byte[] bmpMessage = new byte[BMP_HEAD_SIZE + BMP_INFO_SIZE];
		stream.read(bmpMessage, 0, bmpMessage.length);
		
		
		final int width = byteToInt(bmpMessage, 18);
		final int height = byteToInt(bmpMessage, 22);
		final int imageSize = byteToInt(bmpMessage, 34);
		
		int padding = (imageSize / height) - width * 3;
		
		int[] pix = new int[width * height];
		byte[] data = new byte[(width + padding) * height * 3];
		stream.read(data, 0, data.length);
		
		int curIdx = 0;
		for (int h = 0; h < height; ++h) {
			for (int w = 0; w < width; ++w, curIdx += 3) {
				pix[(height - h - 1) * width + w] = (data[curIdx + 2] & 0xff) << 16 
						| (data[curIdx + 1] & 0xff) << 8 
						| (data[curIdx + 0] & 0xff);

				// add alpha channels
				pix[(height - h - 1) * width + w] |= 0xff000000; 
			}
			curIdx += (imageSize / height) - width * 3;
		}
		
		Image result = Toolkit.getDefaultToolkit().createImage(
				new MemoryImageSource(width, height, pix, 0, width));
		stream.close();
		
		return result;
	}

	@Override
	public Image myWrite(Image img, String filePath) throws IOException {

		BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bi.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    
		try {

			File outputfile = new File(filePath);
			ImageIO.write(bi, "BMP", outputfile);
		} catch (IOException e) {
			throw new RuntimeException(e);
        }

		return img;
	}
	
	/*
	@Override
	public Image myWrite(Image img, String filePath) throws IOException {
		int width = img.getWidth(null);
		int height = img.getHeight(null);

		FileOutputStream stream = new FileOutputStream(filePath);
		byte[] bmpMessage = new byte[BMP_HEAD_SIZE + BMP_INFO_SIZE];

		bmpMessage[0] = 'B';
		bmpMessage[1] = 'M';
		
		int widthSize = (width * 24 + 31) / 32 * 4;
		int imageSize = widthSize * height;
		
		// the total size of the image.
		setIntToByteArray(bmpMessage, imageSize + BMP_HEAD_SIZE + BMP_INFO_SIZE, 2, 4);

		// the offset of the Message size
		setIntToByteArray(bmpMessage, BMP_HEAD_SIZE + BMP_INFO_SIZE, 10, 4);

		// the size of BitmapInfoHeader, which is 40
		setIntToByteArray(bmpMessage, 40, 14, 4);

		// the bitmap width (pixels)
		setIntToByteArray(bmpMessage, width, 18, 4);

		// the bitmap height (pixels)
		setIntToByteArray(bmpMessage, height, 22, 4);

		// the bit of the pixel, which is 24 meaning colorful.
		setIntToByteArray(bmpMessage, 24, 28, 2);

		// the compress operation
		setIntToByteArray(bmpMessage, 0, 30, 4);

		// the image size
		setIntToByteArray(bmpMessage, imageSize, 34, 4);

		// the resolution of the width.。
		setIntToByteArray(bmpMessage, 2835, 38, 4);

		// the resolution of the height
		setIntToByteArray(bmpMessage, 2835, 42, 4);

		// the number of color used.
		setIntToByteArray(bmpMessage, 0, 46, 4);

		// the number of important color used.
		setIntToByteArray(bmpMessage, 0, 50, 4);

		stream.write(bmpMessage);
		
		int padding = widthSize - width * 3;
		int curOffset = 0;
		PixelGrabber pg = new PixelGrabber(img, 0, 0, -1, -1, false);
		try {
			pg.grabPixels();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int[] pixels = (int[]) pg.getPixels();
		byte[] data = new byte[imageSize];
		for (int h = 0; h < height; ++h) {
			for (int w = 0; w < width; ++w, curOffset += 3) {
				setIntToByteArray(data, pixels[(height - h - 1) * width + w],
						curOffset, 3);
			}
			curOffset += padding;
		}
		stream.write(data);

		stream.close();
		
		return img;
	}
	*/
	
	public int byteToInt(byte[] src, int offset) {
		int value;    
	    value = (int) ((src[offset] & 0xFF)   
	            | ((src[offset+1] & 0xFF)<<8)   
	            | ((src[offset+2] & 0xFF)<<16)   
	            | ((src[offset+3] & 0xFF)<<24));  
	    return value;
	}
	
	public static byte[] intToBytes( int value )   
	{   
	    byte[] src = new byte[4];  
	    src[3] =  (byte) ((value>>24) & 0xFF);  
	    src[2] =  (byte) ((value>>16) & 0xFF);  
	    src[1] =  (byte) ((value>>8) & 0xFF);    
	    src[0] =  (byte) (value & 0xFF);                  
	    return src;   
	}
	
	private void setIntToByteArray(byte[] out, int num, int offset, int len) {
		byte[] byteArrayDummy = intToBytes(num);
		for (int i = 0; i < len; ++i) {
			out[offset + i] = byteArrayDummy[i];
		}
	}
}
