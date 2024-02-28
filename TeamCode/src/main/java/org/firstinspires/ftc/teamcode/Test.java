package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.Camera;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.internal.camera.names.WebcamNameImpl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

@TeleOp
public class Test extends LinearOpMode {
    private DcMotor RightDrive_fr, RightDrive_ass;
    private DcMotor LeftDrive_fr, LeftDrive_ass;
    private DcMotor lift_right, lift_left;

    boolean slow_mode = true;
    boolean t = true;
    boolean bump_right = true;
    boolean bump_left = true;
    private Servo serv_hang_himself;
    private Servo servo_up;
    private Servo serv_right, serv_left;

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

    public void DcMotorPower() {
        double main_x = -gamepad1.right_stick_x,
                main_y = -gamepad1.right_stick_y,
                not_main_x =  -gamepad1.left_stick_x / 2;
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
    void update_lifts_values() {
        lift_right.setPower(gamepad1.left_trigger - gamepad1.right_trigger);
        lift_left.setPower(gamepad1.left_trigger - gamepad1.right_trigger);

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
            if (bump_right){
                serv_right.setPosition(0.485); // Closed
            }else{
                serv_right.setPosition(0.25); // Opened
            }
            bump_right =!bump_right;
        }
        if (buttons_down.get("left_bumper")){
            if (bump_left){
                serv_left.setPosition(0.4); // Opened
            }else{
                serv_left.setPosition(0.1); // Closed
            }
            bump_left =!bump_left;
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

        waitForStart();

        while (opModeIsActive()) {
            check_buttons_down();
            DcMotorPower();
            update_lifts_values();
            use_servos();
            write_autonom();
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
        WebcamName camera = hardwareMap.get(WebcamName.class, "camera");
        telemetry.setMsTransmissionInterval(50);
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
        ));
        imu.initialize(parameters);

        serv_hang_himself.setPosition(1);
        serv_right.setPosition(0.485);
        serv_left.setPosition(0.1);
        servo_up.setPosition(0.44);


        for (DcMotor motor : new DcMotor[]{RightDrive_fr, RightDrive_ass, LeftDrive_ass, LeftDrive_fr, lift_right, lift_left}) {
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

    }
    void write_autonom() {
        sleep(100);

        for (Object obj : new Object[] { gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.right_stick_y, gamepad1.left_bumper, gamepad1.right_bumper }) {
            System.out.print(String.valueOf(obj) + " ");
        }
        System.out.println();
    }
    void check_buttons_down() {
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
                    btn_pressed = gamepad1.right_bumper;
                    break;
                case "left_bumper":
                    btn_pressed = gamepad1.left_bumper;
                    break;
                case "dpad_up":
                    btn_pressed = gamepad1.dpad_up;
                    break;
                case "dpad_down":
                    btn_pressed = gamepad1.dpad_down;
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
