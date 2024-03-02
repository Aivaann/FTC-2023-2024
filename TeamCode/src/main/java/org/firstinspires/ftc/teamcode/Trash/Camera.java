package org.firstinspires.ftc.teamcode.Trash;

import static java.lang.Math.random;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;

public class Camera extends LinearOpMode {
    private final ElapsedTime runtime = new ElapsedTime();
    Servo ser;
    DigitalChannel test;
    boolean tagFound = false;
    OpenCvCamera camera;
    AprilTagDetectionPipeline aprilTagDetectionPipeline;

    static final double FEET_PER_METER = 3.28084;
    double fx = 578.272;
    double fy = 578.272;
    double cx = 402.145;
    double cy = 221.506;

    // UNITS ARE METERS
    double tagsize = 0.166;

    // Tag ID 1,2,3 from the 36h11 family
    /*EDIT IF NEEDED!!!*/

    int LEFT = 1;
    int MIDDLE = 2;
    int RIGHT = 3;

    AprilTagDetection tagOfInterest = null;

    @SuppressLint("DefaultLocale")
    @Override
    public void runOpMode() {
        double ratio = 0.1; // ratio for all speed
        DcMotor rightDrive_fr = hardwareMap.get(DcMotor.class, "RightDrive_fr");
        DcMotor leftDrive_fr = hardwareMap.get(DcMotor.class, "LeftDrive_fr");
        DcMotor rightDrive_ass = hardwareMap.get(DcMotor.class, "RightDrive_ass");
        DcMotor leftDrive_ass = hardwareMap.get(DcMotor.class, "LeftDrive_ass");
        DcMotor lift_right = hardwareMap.get(DcMotor.class, "lift_right");
        DcMotor lift_left = hardwareMap.get(DcMotor.class, "lift_left");
        Servo serv_hang_himself = hardwareMap.get(Servo.class, "serv_hang_himself");
        Servo servo_up = hardwareMap.get(Servo.class, "servo_up");
        Servo serv_right = hardwareMap.get(Servo.class, "serv_right");
        Servo serv_left = hardwareMap.get(Servo.class, "serv_left");

        // for angles
        BNO055IMU imu;
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.loggingEnabled = true;
        parameters.loggingTag = "imu";

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        //for camera
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "camera"), cameraMonitorViewId);
        aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagsize, fx, fy, cx, cy);

        camera.setPipeline(aprilTagDetectionPipeline);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(640, 360, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {

            }
        });

        telemetry.setMsTransmissionInterval(50);

        while (!isStarted() && !isStopRequested()) {
            ArrayList<AprilTagDetection> currentDetections = aprilTagDetectionPipeline.getLatestDetections();

            if (!currentDetections.isEmpty()) {
                boolean tagFound = false;

                for (AprilTagDetection tag : currentDetections) {
                    if (tag.id == LEFT || tag.id == MIDDLE || tag.id == RIGHT) {
                        tagOfInterest = tag;
                        tagFound = true;
                        break;
                    }
                }


                if (tagFound) {
                    telemetry.addLine("Last tag:");
                    telemetry.addLine(String.format("Detected tag ID=%d", tagOfInterest.id));

                }
            }
            telemetry.update();
            sleep(20);
        }

        if (tagFound)
        {
            if (tagOfInterest.id == LEFT) {
                telemetry.addLine("Doing task one");
            } else if (tagOfInterest.id == MIDDLE) {
                telemetry.addLine("Doing task two");
            } else if (tagOfInterest.id == RIGHT) {
                telemetry.addLine("Doing task three");

            }
        }
        else {

            int task = (int) (random() % 3);
            telemetry.addLine(String.format("random rask %s", task));
            telemetry.update();
            if (task == 0) {
            } else if (task == 1) {
                telemetry.addLine(String.format("random task, kdnjsvkjdfvibdvhbjdfbhj.v cnkldvnkmv cx %s", task));
                telemetry.update();
            } else if (task == 2) {

            }

        }

    }

    @SuppressLint("DefaultLocale")
    void tagToTelemetry(@NonNull AprilTagDetection detection) {
        telemetry.addLine(String.format("Detected tag ID=%d", detection.id));
    }
}

