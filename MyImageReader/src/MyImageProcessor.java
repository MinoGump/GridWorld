import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.FilteredImageSource;
import java.awt.image.RGBImageFilter;

import imagereader.IImageProcessor;

public class MyImageProcessor implements IImageProcessor {

	private static Toolkit toolkit = Toolkit.getDefaultToolkit();
	
	@Override
	public Image showChanelB(Image img) {
		FilteredImageSource src = new FilteredImageSource(img.getSource(),new RGBFilter(3));
		return toolkit.createImage(src);
	}

	@Override
	public Image showChanelG(Image img) {
		FilteredImageSource src = new FilteredImageSource(img.getSource(),new RGBFilter(2));
		return toolkit.createImage(src);
	}

	@Override
	public Image showChanelR(Image img) {
		FilteredImageSource src = new FilteredImageSource(img.getSource(),new RGBFilter(1));
		return toolkit.createImage(src);
	}

	@Override
	public Image showGray(Image img) {
		RGBImageFilter filter = new RGBImageFilter() {

			@Override
			public int filterRGB(int x, int y, int rgb) {
				int r = (rgb & 0xff0000) >> 16;
				int g = (rgb & 0xff00) >> 8;
				int b = rgb & 0xff;
				int gray = (int) (0.299 * r + 0.587 * g + 0.114 * b);
				gray = 0xff000000 | (gray << 16) | (gray << 8) | gray;
				return gray;
			}
		};
		FilteredImageSource src = new FilteredImageSource(img.getSource(), filter);
		return toolkit.createImage(src);
	}
	
	
}

class RGBFilter extends RGBImageFilter {
	private int filter;
	
	public RGBFilter(int n) {
		if (n == 1) {
			filter = 0xffff0000;
		} else if (n == 2) {
			filter = 0xff00ff00;
		} else if (n == 3) {
			filter = 0xff0000ff;
		}
	}

	@Override
	public int filterRGB(int x, int y, int rgb) {
		return (rgb & filter);
	}
}
