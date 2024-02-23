package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
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
    IMU imu;

    private final LinkedList<String> console = new LinkedList<>();
    @Override
    public void runOpMode() {
        declare_variables();

        for (DcMotor motor : new DcMotor[] { LeftDrive_fr, LeftDrive_ass, lift_left }) {
            //motor.setDirection(DcMotor.Direction.REVERSE);
        }

        waitForStart();
        if (opModeIsActive()) {
            rotate(90);
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
        double delta = 150;

        while (get_current_rotation() < target) {
            if (target - get_current_rotation() <= 180) {
                set_motors_power(((target - get_current_rotation()) / delta));
            }
            else {
                set_motors_power(1);
            }
        }
        while (get_current_rotation() > target) {
            if (target - get_current_rotation() <= 180) {
                set_motors_power(((target - get_current_rotation()) / delta));
            }
            else {
                set_motors_power(-1);
            }
        }
        set_motors_power(0);

    }


    void print(String output) {
        console.add(output);
        if (console.size() > 10) { console.remove(0); }
        for (String line : console) {
            telemetry.addLine(line);
        }
        telemetry.update();
    } void print(int output) {print(String.valueOf(output));} void print(double output) {print(String.valueOf(output));}


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
}