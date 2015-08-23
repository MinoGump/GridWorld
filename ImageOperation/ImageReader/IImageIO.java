import imagereader.*;

public interface IImageIO {
	Image myRead(String filePath) {
		File f = new File(filePath);
		BufferedImage bi = 
	}

	Image myWrite(Image image, String filePath) {

	}
}