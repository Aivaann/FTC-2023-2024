package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Timer;

public class Autonomqa extends LinearOpMode {

    private DcMotor RightDrive_fr, RightDrive_ass;
    private DcMotor LeftDrive_fr, LeftDrive_ass;
    private DcMotor lift_right, lift_left;
    private Servo serv_hang_himself;
    private Servo servo_up;
    private Servo serv_right, serv_left;
    boolean bump_right = true;
    boolean bump_left = true;
    boolean t = true;
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
    HashMap<String, Object> user_data = new HashMap<String, Object>(){{
       put("left_stick_x", 0);
       put("right_stick_x", 0);
       put("right_stick_y", 0);
       put("right_bumper", false);
       put("left_bumper", false);
    }};
    private final LinkedList<String> console = new LinkedList<>();
    @Override
    public void runOpMode() {
        declare_variables();

        waitForStart();
        if (opModeIsActive()) {
            for (String instruction : get_instructions()) {
                // right x, right y, left x
                // gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.right_stick_y, gamepad1.left_bumper, gamepad1.right_bumper
                user_data.put("left_stick_x", Double.valueOf(instruction.split(" ")[0]));
                user_data.put("right_stick_x", Double.valueOf(instruction.split(" ")[1]));
                user_data.put("right_stick_y", Double.valueOf(instruction.split(" ")[2]));
                user_data.put("left_bumper", Boolean.valueOf(instruction.split(" ")[3]));
                user_data.put("right_bumper", Boolean.valueOf(instruction.split(" ")[4]));
                System.out.println(instruction);
                DcMotorPower((Double) user_data.get("right_stick_x"), (Double) user_data.get("right_stick_y"), (Double) user_data.get("left_stick_x"));
                use_servos();
                sleep(100);
            }
        }
    }


    void rotate(int degrees) {
        double target = get_current_rotation() + degrees;
        if (target > 360) {
            target -= 360;
            while (get_current_rotation() > 10) {
                set_motors_power((360 - get_current_rotation() + target) / 150);
            }
        }
        double delta = target - get_current_rotation();

        set_motors_target_position((int) delta * 6);

    }
    void print(String output) {
        console.add(output);
        if (console.size() > 10) { console.remove(0); }
        for (String line : console) {
            telemetry.addLine(line);
        }
        telemetry.update();
    }
    void print(int output) {
        print(String.valueOf(output));
    }
    void print(double output) {
        print(String.valueOf(output));
    }
    void declare_variables() {
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

        //imu = hardwareMap.get(IMU.class, "imu");

        //IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
        //        RevHubOrientationOnRobot.LogoFacingDirection.UP,
        //        RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
        //));
        //imu.initialize(parameters);


        //for (DcMotor motor : new DcMotor[]{RightDrive_fr, RightDrive_ass, LeftDrive_ass, LeftDrive_fr, lift_right, lift_left}) {
        //    motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //}
    }
    double get_current_rotation() {
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) + 180;
    }
    void set_motors_power(double power) {
        for (DcMotor motor : new DcMotor[]{RightDrive_fr, RightDrive_ass, LeftDrive_ass, LeftDrive_fr}) {
            motor.setPower(power);
        }
    }
    void set_motors_target_position(int position) {
        for (DcMotor motor : new DcMotor[]{RightDrive_fr, RightDrive_ass, LeftDrive_ass, LeftDrive_fr}) {
            motor.setTargetPosition(motor.getCurrentPosition() + position);
        }
    }
    void set_mode(DcMotor.RunMode mode) {
        for (DcMotor motor : new DcMotor[]{RightDrive_fr, RightDrive_ass, LeftDrive_ass, LeftDrive_fr}) {
            motor.setMode(mode);
        }
    }
    String[] get_instructions() {
        // lx, rx, ry, left_bumper, right_bumper
        // String data = "";
        String data = "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 -0.007189324 0.0 0.0 false false/n"+
                "0.0 0.0 -0.02430675 0.0 0.0 false false/n"+
                "0.0 0.0 -0.032865457 0.0 0.0 false false/n"+
                "0.0 0.0 -0.1270113 0.0 0.0 false false/n"+
                "0.0 0.0 -0.195481 0.0 0.0 false false/n"+
                "0.0 0.0 -0.3238617 0.0 0.0 false false/n"+
                "0.0 0.0 -0.3324204 0.0 0.0 false false/n"+
                "0.0 0.0 -0.3324204 0.0 0.0 false false/n"+
                "0.0 0.0 -0.3324204 0.0 0.0 false false/n"+
                "0.0 0.0 -0.3324204 0.0 0.0 false false/n"+
                "0.0 0.0 -0.3324204 0.0 0.0 false false/n"+
                "0.0 0.0 -0.3238617 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.22971587 0.0 0.0 false false/n"+
                "0.0 0.0 0.70044506 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "-0.4008901 0.0 0.0 0.0 0.0 false false/n"+
                "-0.5207121 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.195481 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.35809657 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.28106812 0.0 0.0 false false/n"+
                "0.0 0.0 0.64909273 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+"0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 -0.007189324 0.0 0.0 false false/n"+
                "0.0 0.0 -0.02430675 0.0 0.0 false false/n"+
                "0.0 0.0 -0.032865457 0.0 0.0 false false/n"+
                "0.0 0.0 -0.1270113 0.0 0.0 false false/n"+
                "0.0 0.0 -0.195481 0.0 0.0 false false/n"+
                "0.0 0.0 -0.3238617 0.0 0.0 false false/n"+
                "0.0 0.0 -0.3324204 0.0 0.0 false false/n"+
                "0.0 0.0 -0.3324204 0.0 0.0 false false/n"+
                "0.0 0.0 -0.3324204 0.0 0.0 false false/n"+
                "0.0 0.0 -0.3324204 0.0 0.0 false false/n"+
                "0.0 0.0 -0.3324204 0.0 0.0 false false/n"+
                "0.0 0.0 -0.3238617 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.22971587 0.0 0.0 false false/n"+
                "0.0 0.0 0.70044506 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "-0.4008901 0.0 0.0 0.0 0.0 false false/n"+
                "-0.5207121 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.195481 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.35809657 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.28106812 0.0 0.0 false false/n"+
                "0.0 0.0 0.64909273 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n"+
                "0.0 0.0 0.0 0.0 0.0 false false/n";

        return data.split("/n");
    }
    public void DcMotorPower(double main_x, double main_y, double not_main_x) {
        main_x = -main_x;
        main_y = -main_y;
        not_main_x = -not_main_x / 2;
        not_main_x*=Math.max((Math.abs(main_y)+Math.abs(main_x))*4,1.3);
        for (DcMotor motor : new DcMotor[] { LeftDrive_fr, LeftDrive_ass, lift_left }) {
            motor.setDirection(DcMotor.Direction.REVERSE);
        }
        double RightDrive_fr_power = main_y - main_x + not_main_x;
        double RightDrive_ass_power = main_y + main_x + not_main_x;
        double LeftDrive_fr_power = main_y - main_x - not_main_x;
        double LeftDrive_ass_power = main_y + main_x - not_main_x;
        LeftDrive_ass.setPower(LeftDrive_ass_power);
        LeftDrive_fr.setPower(LeftDrive_fr_power);
        RightDrive_ass.setPower(RightDrive_ass_power);
        RightDrive_fr.setPower(RightDrive_fr_power);
        sleep(100);
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
    void check_buttons_down(boolean cross, boolean square, boolean right_bumper, boolean left_bumper, boolean dpad_up, boolean dpad_down) {
        for (String button : buttons_down.keySet()) {
            boolean btn_pressed = false;
            switch (button) {
                case "cross":
                    btn_pressed = cross;
                    break;
                case "square":
                    btn_pressed = square;
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
