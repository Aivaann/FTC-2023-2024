package org.firstinspires.ftc.teamcode;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

public class cameratest1 extends Rect {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        VideoCapture cap = new VideoCapture(1);

        while (true) {
            Mat img = new Mat();
            cap.read(img);
            if (img.empty()) {
                break;
            }

            // Разделите изображение на две половины
            int halfWidth = img.width() / 2;
            Mat leftHalf = new Mat(img, new Rect(0, 0, halfWidth, img.height()));
            Mat rightHalf = new Mat(img, new Rect(halfWidth, 0, halfWidth, img.height()));

            // Создайте маску для синих пикселей
            Mat blueMask = new Mat();
            Core.inRange(leftHalf, new Scalar(0, 0, 0), new Scalar(100, 90, 200), blueMask);

            // Создайте маску для красных пикселей
            Mat redMask = new Mat();
            Core.inRange(rightHalf, new Scalar(0, 0, 0), new Scalar(130, 60, 40), redMask);

            // Подсчитайте количество пикселей в масках
            int blueCount = Core.countNonZero(blueMask);
            int redCount = Core.countNonZero(redMask);

            System.out.println("Количество синих пикселей в левой половине: " + blueCount);
            System.out.println("Количество красных пикселей в правой половине: " + redCount);

            Imgproc.resize(blueMask, blueMask, new Size(640, 480));
            Imgproc.resize(redMask, redMask, new Size(640, 480));

            Imgcodecs.imwrite("blue_mask.jpg", blueMask);
            Imgcodecs.imwrite("red_mask.jpg", redMask);

            // Ваш код для обработки изображения здесь

            Imgproc.resize(img, img, new Size(640, 480));
            Imgcodecs.imwrite("output.jpg", img);
        }

        cap.release();
        //Core.destroyAllWindows();
    }
}