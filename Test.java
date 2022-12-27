import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Test {
    double[][][] probability = new double[256][256][256];
    double [][] confusionMatrix=new double[2][2];
    double precision, recall,fScore;
    File directoryPath = new File("/home/maymuna/dbms/Mask");
    File filesList[] = directoryPath.listFiles();
    File directoryPath1 = new File("/home/maymuna/dbms/ibtd");
    File filesList1[] = directoryPath1.listFiles();
    File outputfile = new File("/home/maymuna/dbms/output.jpg");

    public void test(int K, int fold, ArrayList<Integer> list) throws Exception
    {


        for(int i=0;i<2;i++)
        {
            for(int j=0;j<2;j++)
            {
                confusionMatrix[i][j]=0;
            }
        }
        BufferedReader br=new BufferedReader(new FileReader("/home/maymuna/dbms/filename.txt"));

        int start=fold*(filesList.length/K);
        int end=start+((filesList.length/K)-1);

        Arrays.sort(filesList);
        Arrays.sort(filesList1);

        for (int i = 0; i < 256; i++)
        {

            for (int j = 0; j < 256; j++)
            {

                for (int k = 0; k < 256; k++)
                {
                    probability[i][j][k] = Double.parseDouble(br.readLine());
                }
            }
        }

        for(int i=start;i<=end;i++)
        {
            File testFile = new File(filesList1[list.get(i)].getAbsolutePath());

            BufferedImage testImg = ImageIO.read(testFile);

            for (int y = 0; y < testImg.getHeight(); y++) {

                for (int x = 0; x < testImg.getWidth(); x++) {

                    //Retrieving contents of a pixel

                    int testPixel = testImg.getRGB(x, y);

                    //Creating a Color object from pixel value

                    Color testColor = new Color(testPixel, true);
                    Color myWhite = new Color(255, 255, 255); // Color white
                    int rgb = myWhite.getRGB();

                    //Retrieving the R G B values

                    int testRed = testColor.getRed();

                    int testGreen = testColor.getGreen();

                    int testBlue = testColor.getBlue();

                    if (probability[testRed][testGreen][testBlue] < 0.4) {
                        testImg.setRGB(x, y, rgb);
                    }
                }
            }
            ImageIO.write(testImg, "jpg", outputfile);
            accuracy(i,list);


        }
        precision=confusionMatrix[0][0]/(confusionMatrix[0][0]+confusionMatrix[0][1]);
        recall=confusionMatrix[0][0]/(confusionMatrix[0][0]+confusionMatrix[1][0]);
        fScore=(2*precision*recall)/(precision+recall);
        System.out.println("Done testing for fold : "+fold+" and the accuracy is "+fScore);
    }

    public  void accuracy(int i,ArrayList<Integer> list) throws IOException
    {
        File maskFile = new File(filesList[list.get(i)].getAbsolutePath());
        BufferedImage maskImg = ImageIO.read(maskFile);
        BufferedImage outImg = ImageIO.read(outputfile);
        for (int y = 0; y < maskImg.getHeight(); y++)
        {

            for (int x = 0; x < maskImg.getWidth(); x++)
            {
                int maskPixel = maskImg.getRGB(x,y);
                Color maskColor = new Color(maskPixel, true);
                int pixel = outImg.getRGB(x,y);
                Color outColor = new Color(pixel, true);
                Color myWhite = new Color(255, 255, 255);
                if(!(maskColor.equals(myWhite)) && !(outColor.equals(myWhite)))
                {
                    confusionMatrix[0][0]++;
                }
                else if((maskColor.equals(myWhite)) && !(outColor.equals(myWhite)))
                {
                    confusionMatrix[0][1]++;
                }
                else if(!(maskColor.equals(myWhite)) && (outColor.equals(myWhite)))
                {
                    confusionMatrix[1][0]++;
                }
                else if((maskColor.equals(myWhite)) && (outColor.equals(myWhite)))
                {
                    confusionMatrix[1][1]++;
                }
            }
        }
    }
}
