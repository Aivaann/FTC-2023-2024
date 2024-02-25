package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.util.LinkedList;
import java.util.Timer;

@Autonomous
public class Autonomqa extends LinearOpMode {

    private DcMotor RightDrive_fr, RightDrive_ass;
    private DcMotor LeftDrive_fr, LeftDrive_ass;
    private DcMotor lift_right, lift_left;
    private Servo serv_hang_himself;
    private Servo servo_up;
    private Servo serv_right, serv_left;
    boolean bump_right = true;
    boolean bump_left = true;
    IMU imu;

    private final LinkedList<String> console = new LinkedList<>();
    @Override
    public void runOpMode() {
        declare_variables();

        waitForStart();
        if (opModeIsActive()) {
            for (String instruction : get_instructions()) {
                // right x, right y, left x
                DcMotorPower(-Double.valueOf(instruction.split(" ")[1]), Double.valueOf(instruction.split(" ")[2]), Double.valueOf(instruction.split(" ")[0]));
                use_serv(Boolean.valueOf(instruction.split(" ")[3]), Boolean.valueOf(instruction.split(" ")[4]));
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

        imu = hardwareMap.get(IMU.class, "imu");

        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
        ));
        imu.initialize(parameters);
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
        String data = "0.0 0.0 0.0 false false\n" +
                "0.0 0.0 0.0 false false\n" +
                "0.0 0.0 0.0 false false\n" +
                "0.0 0.0 0.0 false false\n" +
                "0.0 0.0 0.0 false false\n" +
                "0.0 0.0 0.0 false false\n" +
                "0.0 0.0 0.0 false false\n" +
                "0.0 0.0 0.0 false false\n" +
                "0.0 0.0 0.0 false false\n" +
                "0.0 0.0 0.0 false false\n" +
                "0.0 0.0 0.0 false false\n" +
                "0.0 0.0 0.0 false false\n" +
                "0.0 0.0 0.0 false false\n" +
                "0.0 0.0 0.0 false false\n" +
                "0.0 0.0 0.0 false false\n" +
                "0.0 0.0 0.0 false false\n" +
                "0.0 0.0 0.0 false false\n" +
                "0.0 0.0 0.0 false false\n" +
                "0.0 0.0 -0.46935982 false false\n" +
                "0.0 0.0 -1.0 false false\n" +
                "0.0 -0.07565903 -1.0 false false\n" +
                "0.0 -0.07565903 -1.0 false false\n" +
                "0.0 -0.084217735 -1.0 false false\n" +
                "0.0 -0.07565903 -1.0 false false\n" +
                "0.0 -0.07565903 -1.0 false false\n" +
                "0.0 -0.041424174 -1.0 false false\n" +
                "-0.70900375 -0.032865457 -1.0 false false\n" +
                "-1.0 -0.032865457 -0.3923314 false false\n" +
                "-0.28106812 0.0 0.0 false false\n" +
                "0.0 0.0 0.2639507 false false\n" +
                "0.0 0.0 0.9657652 false false\n" +
                "0.0 0.0 1.0 false false\n" +
                "0.0 0.0 1.0 false false\n" +
                "-0.30674428 0.0 1.0 false false\n" +
                "0.0 0.0 1.0 false false\n" +
                "0.0 0.0 1.0 false false\n" +
                "0.0 0.0 1.0 false false\n" +
                "0.0 0.0 1.0 false false\n" +
                "0.0 0.0 1.0 false false\n" +
                "0.28962687 0.0 1.0 false false\n" +
                "0.5977405 0.0 0.98288256 false false\n" +
                "0.6062992 0.0 0.98288256 false false\n" +
                "0.66621023 0.0 0.97432387 false false\n" +
                "0.8630606 0.0 0.94864774 false false\n" +
                "1.0 0.0 0.8887367 false false\n" +
                "1.0 0.0 0.56350565 false false\n" +
                "1.0 0.0 -0.195481 false false\n" +
                "0.84594315 0.36665526 -1.0 false false\n" +
                "0.0 0.5207121 -0.92297155 false false\n" +
                "0.0 0.5207121 -0.92297155 false false\n" +
                "-0.64053404 0.4779185 -0.9572064 false false\n" +
                "-0.5207121 0.46935982 -0.9657652 false false\n" +
                "-0.29818556 0.38377267 -1.0 false false\n" +
                "0.0 0.30674428 -1.0 false false\n" +
                "0.0 0.28962687 -1.0 false false\n" +
                "0.0 0.28962687 -0.98288256 false false\n" +
                "0.0 0.28106812 -0.98288256 false false\n" +
                "0.0 0.28962687 -0.8972954 false false\n" +
                "0.0 0.16980487 -0.36665526 false false\n" +
                "0.0 0.0 0.058541592 false false\n" +
                "0.0 0.0 0.041424174 false false\n" +
                "0.0 -0.07565903 0.16980487 false false\n" +
                "0.0 0.0 0.0 false false\n" +
                "0.0 0.0 -0.084217735 false false\n" +
                "0.0 -0.058541592 -1.0 false false\n" +
                "0.0 0.0 0.0 false false\n" +
                "0.0 0.0 0.0 false false\n" +
                "0.0 0.0 0.0 false false\n" +
                "0.0 0.0 0.0 false false\n" +
                "0.0 0.0 0.0 false false\n" +
                "0.0 0.0 0.0 false false\n" +
                "0.0 0.0 0.0 false false\n" +
                "0.0 0.0 0.0 false false\n" +
                "0.0 0.0 0.0 false false\n" +
                "0.0 0.0 0.0 false false\n" +
                "0.0 0.0 0.0 false false\n" +
                "0.0 0.0 0.0 false false";
        return data.split("\n");
    }


    public void DcMotorPower(double main_x, double main_y, double not_main_x) {
        for (DcMotor motor : new DcMotor[] { LeftDrive_fr, LeftDrive_ass, lift_left }) {
            motor.setDirection(DcMotor.Direction.REVERSE);
        }
        not_main_x /= -2;
        not_main_x*=Math.max((Math.abs(main_y)+Math.abs(main_x))*4,1.3);
        double RightDrive_fr_power = main_y + main_x + not_main_x;
        double RightDrive_ass_power = main_y - main_x + not_main_x;
        double LeftDrive_fr_power = main_y - main_x - not_main_x;
        double LeftDrive_ass_power = main_y + main_x - not_main_x;
        LeftDrive_ass.setPower(LeftDrive_ass_power);
        LeftDrive_fr.setPower(LeftDrive_fr_power);
        RightDrive_ass.setPower(RightDrive_ass_power);
        RightDrive_fr.setPower(RightDrive_fr_power);
    }

    void use_serv(boolean right, boolean left) {
        if (right) {
            if (bump_right){
                serv_right.setPosition(0.5);
            }else{
                serv_right.setPosition(0.2);
            }
            bump_right =!bump_right;
        }
        if (left){
            if (bump_left){
                serv_left.setPosition(0.4);
            }else{
                serv_left.setPosition(0.1);
            }
            bump_left =!bump_left;
        }
    }
}
