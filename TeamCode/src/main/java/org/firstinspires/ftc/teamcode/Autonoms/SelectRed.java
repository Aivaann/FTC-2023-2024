package org.firstinspires.ftc.teamcode.Autonoms;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

import java.util.HashMap;
import java.util.LinkedList;

@Autonomous(name="Select Red")
public class SelectRed extends LinearOpMode {
    private DcMotor RightDrive_fr, RightDrive_ass;
    private DcMotor LeftDrive_fr, LeftDrive_ass;
    private DcMotor lift_right, lift_left;

    boolean slow_mode = true;
    boolean t = true;
    private Servo serv_hang_himself;
    private Servo servo_up;
    private Servo serv_right, serv_left;
    HashMap<String, Object> user_data = new HashMap<String, Object>(){{
        put("left_stick_x", 0);
        put("right_stick_x", 0);
        put("right_stick_y", 0);
        put("right_bumper", false);
        put("left_bumper", false);
    }};

    private final LinkedList<String> console = new LinkedList<>();
    IMU imu;
    HashMap<String, Boolean> buttons_down = new HashMap<String, Boolean>(){{
        put("square", false);
        put("cross", false);
        put("right_bumper", false);
        put("left_bumper", false);
        put("dpad_up", false);
        put("dpad_down", false);
    }};
    HashMap<String, Boolean> buttons_pressed = new HashMap<String, Boolean>(){{
        put("square", false);
        put("cross", false);
        put("right_bumper", false);
        put("left_bumper", false);
        put("dpad_up", false);
        put("dpad_down", false);
    }};

    CVCamera pipeline;
    OpenCvWebcam webcam;
    static final int STREAM_WIDTH = 1280; // modify for your camera
    static final int STREAM_HEIGHT = 720; // modify for your camera
    int location = 0;


    public void DcMotorPower(double main_x, double main_y, double not_main_x) {
        main_x = -main_x;
        main_y = -main_y;
        not_main_x =  -not_main_x / 2;
        not_main_x*=Math.max((Math.abs(main_y)+Math.abs(main_x))*4,1.3);

        double RightDrive_fr_power = main_y - main_x + not_main_x;
        double RightDrive_ass_power = main_y + main_x + not_main_x;
        double LeftDrive_fr_power = main_y - main_x - not_main_x;
        double LeftDrive_ass_power = main_y + main_x - not_main_x;
        if (buttons_pressed.get("square")){
            slow_mode =! slow_mode;
        }
        if (!slow_mode){
            RightDrive_fr_power = RightDrive_fr_power/Math.abs(RightDrive_fr_power)*Math.min(Math.abs(RightDrive_fr_power)/2, 0.5);
            LeftDrive_fr_power = LeftDrive_fr_power/Math.abs(LeftDrive_fr_power) * Math.min(Math.abs(LeftDrive_fr_power)/2, 0.5);
            LeftDrive_ass_power = LeftDrive_ass_power/Math.abs(LeftDrive_ass_power)*Math.min(Math.abs(LeftDrive_ass_power)/2, 0.5);
            RightDrive_ass_power = RightDrive_ass_power/Math.abs(RightDrive_ass_power)*Math.min(Math.abs(RightDrive_ass_power)/2, 0.5);
        }
        LeftDrive_ass.setPower(LeftDrive_ass_power);
        LeftDrive_fr.setPower(LeftDrive_fr_power);
        RightDrive_ass.setPower(RightDrive_ass_power);
        RightDrive_fr.setPower(RightDrive_fr_power);
    }
    void update_lifts_values(double right_trigger, double left_trigger) {
        lift_right.setPower(left_trigger - right_trigger);
        lift_left.setPower(left_trigger - right_trigger);
    }
    void use_servos(){
        if (buttons_down.get("cross")){
            if (t){
                serv_hang_himself.setPosition(0.9);
            }else{
                serv_hang_himself.setPosition(1);
            }
            t=!t;
        }
        if (buttons_down.get("right_bumper")){
            if (serv_right.getPosition() <= 0.35){
                serv_right.setPosition(0.485); // Closed
            }else{
                serv_right.setPosition(0.25); // Opened
            }
        }
        if (buttons_down.get("left_bumper")){
            if (serv_left.getPosition() <= 0.25){
                serv_left.setPosition(0.4); // Opened
            }else{
                serv_left.setPosition(0.1); // Closed
            }
        }
        if (buttons_down.get("dpad_up")){
            servo_up.setPosition(0.19);
        }
        if (buttons_down.get("dpad_down")){
            servo_up.setPosition(0.44);
        }
    }

    @Override
    public void runOpMode() {
        declare_variables();

        for (DcMotor motor : new DcMotor[] { LeftDrive_fr, LeftDrive_ass, lift_left }) {
            motor.setDirection(DcMotor.Direction.REVERSE);
        }
        servo_up.setPosition(0.15);
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        WebcamName webcamName = null;
        webcamName = hardwareMap.get(WebcamName.class, "camera"); // put your camera's name here
        webcam = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMonitorViewId);
        pipeline = new CVCamera();
        pipeline.CenterStageCVDetection(telemetry);
        pipeline.setRedColor();
        webcam.setPipeline(pipeline);
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                webcam.startStreaming(STREAM_WIDTH, STREAM_HEIGHT, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                telemetry.addData("Camera Failed","");
                telemetry.update();
            }
        });

        waitForStart();
        if (opModeIsActive()) {
        }
    }

    void print(String output) {
        console.add(output);
        if (console.size() > 10) {
            console.remove(0);
        }
        for (String line : console) {
            telemetry.addLine(line);
        }
        telemetry.update();
    } void print(int output) {print(String.valueOf(output));} void print(double output) {print(String.valueOf(output));}
    double get_current_rotation() {
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) + 180;
    }
    void declare_variables()  {
        RightDrive_fr = hardwareMap.get(DcMotor.class, "RightDrive_fr");
        LeftDrive_fr = hardwareMap.get(DcMotor.class, "LeftDrive_fr");
        RightDrive_ass = hardwareMap.get(DcMotor.class, "RightDrive_ass");
        LeftDrive_ass= hardwareMap.get(DcMotor.class, "LeftDrive_ass");
        lift_right = hardwareMap.get(DcMotor.class, "lift_right");
        lift_left = hardwareMap.get(DcMotor.class, "lift_left");
        serv_hang_himself=hardwareMap.get(Servo.class, "serv_hang_himself");
        servo_up=hardwareMap.get(Servo.class, "servo_up");
        serv_right = hardwareMap.get(Servo.class, "serv_right");
        serv_left = hardwareMap.get(Servo.class, "serv_left");
        imu = hardwareMap.get(IMU.class, "imu");
        // WebcamName camera = hardwareMap.get(WebcamName.class, "camera");
        telemetry.setMsTransmissionInterval(50);
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
        ));
        imu.initialize(parameters);

        serv_hang_himself.setPosition(1);
        serv_right.setPosition(0.485);
        serv_left.setPosition(0.1);
        servo_up.setPosition(0.19);


        for (DcMotor motor : new DcMotor[]{RightDrive_fr, RightDrive_ass, LeftDrive_ass, LeftDrive_fr, lift_right, lift_left}) {
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

    }

    void check_buttons_down(boolean dpad_up, boolean dpad_down, boolean right_bumper, boolean left_bumper) {
        for (String button : buttons_down.keySet()) {
            boolean btn_pressed = false;
            switch (button) {
                case "cross":
                    btn_pressed = gamepad1.cross;
                    break;
                case "square":
                    btn_pressed = gamepad1.square;
                    break;
                case "right_bumper":
                    btn_pressed = right_bumper;
                    break;
                case "left_bumper":
                    btn_pressed = left_bumper;
                    break;
                case "dpad_up":
                    btn_pressed = dpad_up;
                    break;
                case "dpad_down":
                    btn_pressed = dpad_down;
                    break;
            }
            if (btn_pressed) {
                buttons_down.put(button, !buttons_pressed.get(button));
                buttons_pressed.put(button, true);
            }
            else {
                buttons_down.put(button, false);
                buttons_pressed.put(button, false);
            }
        }
    }

}