package org.firstinspires.ftc.teamcode;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgcodecs.Imgcodecs;

// Загрузите изображение
public class cameratest2 extends Rect{
    Mat img = Imgcodecs.imread("path_to_your_image.jpg");

    public boolean empty() {
        while (true) {
            // Разделите изображение на две половины
            int halfWidth = img.width() / 2;
            Mat leftHalf = img.submat(0, img.height(), 0, halfWidth);
            Mat rightHalf = img.submat(0, img.height(), halfWidth, img.width());

            // Создайте маску для синих и красных пикселей
            Mat blueMask = new Mat();
            Mat redMask = new Mat();
            //Imgproc.inRange(leftHalf, new Scalar(0, 0, 0), new Scalar(100, 90, 200), blueMask);
            // Imgproc.inRange(rightHalf, new Scalar(0, 0, 0), new Scalar(130, 60, 40), redMask);

            // Подсчитайте количество пикселей в масках
            double blueCount = Core.countNonZero(blueMask);
            double redCount = Core.countNonZero(redMask);

            System.out.println("Количество синих пикселей в левой половине: " + blueCount);
            System.out.println("Количество красных пикселей в правой половине: " + redCount);

            // Ваш код для обработки изображения здесь

            // Показываем изображение
            //Imgcodecs.imshow("Video", img);
        }
    }

        //Imgcodecs.destroyAllWindows();
}

