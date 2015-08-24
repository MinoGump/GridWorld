import imagereader.Runner;

public final class MyRunner {
	public static void main(String[] args) {
		MyImageIO imgIO = new MyImageIO();
		MyImageProcessor imgPro = new MyImageProcessor();
		Runner.run(imgIO, imgPro);
	}
	private MyRunner() {
		// default constructor.
	}
}
