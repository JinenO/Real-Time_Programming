package Chapter11;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class GrayscaleImageAction extends RecursiveAction {
    private final int row;//base on the row of the image
    private final BufferedImage image;//call for the full image to work on

    public GrayscaleImageAction(int row, BufferedImage image){
        this.row=row;
        this.image=image;//constructor initiates thr row and image
    }

    @Override
    protected void compute() {//to compute based on recursive action
        for(int col=0; col<image.getWidth(); col++){
            int rgb=image.getRGB(col,row);
            int r=(rgb>>16)&0xff;
            int g = (rgb >> 8) & 0xFF;
            int b = rgb & 0xFF;
            int gray = (int)(0.2126 * r + 0.7152 * g + 0.0722 * b);
            int grayRGB = (0xFF << 24) | (gray << 16) | (gray << 8) | gray;
            image.setRGB(col, row, grayRGB);
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException{
        if(args.length==0){//the entry point of program, can throw exception of file reading
            System.err.println("Usage: java GrayscaleImageAction <image-file>");
            return;
        }

        BufferedImage image= ImageIO.read(new File(args[0]));//read input image file
        ForkJoinPool pool = new ForkJoinPool();//create thread pool to run parallel
        for(int row=0; row<image.getHeight(); row++){
            pool.execute(new GrayscaleImageAction(row,image));
        }

        pool.shutdown();
        while(!pool.isTerminated()){
            Thread.sleep(100);//wait for all task to finish
        }

        File output=new File("grayscale_output.png");
        ImageIO.write(image, "png", output);
        System.out.println("Grayscale image written to grayscale_output.png");

        if(Desktop.isDesktopSupported()){
            Desktop.getDesktop().open(output);
        }
    }
}
