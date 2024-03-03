package org.firstinspires.ftc.teamcode.Trash;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

public class cringe4 extends LinearOpMode {
    @Override
    public void runOpMode() {
        try {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

            VideoCapture cap = new VideoCapture(31659760);

            int fl = 0;
            Mat frame = new Mat();
            cap.read(frame);

            int half_width = frame.width() / 2;
            Mat left_half = frame.colRange(0, half_width);
            Mat right_half = frame.colRange(half_width, frame.width());

            Mat hsv_left = new Mat();
            Imgproc.cvtColor(left_half, hsv_left, Imgproc.COLOR_BGR2HSV);

            Mat hsv_right = new Mat();
            Imgproc.cvtColor(right_half, hsv_right, Imgproc.COLOR_BGR2HSV);

            // красный цвет:
            Scalar lower_red = new Scalar(128, 33, 9);
            Scalar upper_red = new Scalar(255, 255, 255);

            Mat mask_left = new Mat();
            Core.inRange(hsv_left, lower_red, upper_red, mask_left);

            Mat mask_right = new Mat();
            Core.inRange(hsv_right, lower_red, upper_red, mask_right);

            int left_count = Core.countNonZero(mask_left);
            int right_count = Core.countNonZero(mask_right);
            //System.out.println(String.format("Left count: %d, Right count: %d", left_count, right_count));
            if (left_count > 10000) {
                fl = 1;
            } else if (right_count > 10000) {
                fl = 2;
            } else {
                fl = 0;
            }
            System.out.println(fl);
            telemetry.addData("position", fl);
            telemetry.update();
        }
        catch (Exception e) {
            System.out.println("Err. " + e.getClass() + ": " + e.getMessage());
            telemetry.addLine("Err. " + e.getClass() + ": " + e.getMessage());
            telemetry.update();
        }
    }
}

