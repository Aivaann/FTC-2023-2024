package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.LinkedList;

@TeleOp
public class Test extends LinearOpMode {
    private DcMotor RightDrive_fr, RightDrive_ass;
    private DcMotor LeftDrive_fr, LeftDrive_ass;
    private DcMotor lift_right, lift_left;


    boolean slow_mode = true,
            t = true,
            bump_right = true,
            bump_left = true;

    private Servo serv_hang_himself,
            servo_up,
            serv_right,
            serv_left;


    private final LinkedList<String> console = new LinkedList<>();
    IMU imu;

    @Override
    public void runOpMode() {
        declare_variables();

        for (DcMotor motor : new DcMotor[] { LeftDrive_fr, LeftDrive_ass, lift_left }) {
            motor.setDirection(DcMotor.Direction.REVERSE);
        }

        waitForStart();

        while (opModeIsActive()) {
            print(
                    "Imu value: " + get_current_rotation()
            );
            DcMotorPower();
            update_lifts_values();
            use_servos();
        }
    }

    public void DcMotorPower() {
        double main_x = -gamepad1.right_stick_x,
                main_y = gamepad1.right_stick_y,
                not_main_x =  -gamepad1.left_stick_x / 2;
        not_main_x*=Math.max((Math.abs(main_y)+Math.abs(main_x))*4,1.3);
        double RightDrive_fr_power = main_y + main_x + not_main_x;
        double RightDrive_ass_power = main_y - main_x + not_main_x;
        double LeftDrive_fr_power = main_y - main_x - not_main_x;
        double LeftDrive_ass_power = main_y + main_x - not_main_x;
        if (gamepad1.square){
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
        if (gamepad1.cross){
            if (t){
                serv_hang_himself.setPosition(0.9);
            }else{
                serv_hang_himself.setPosition(1);
            }
            t=!t;
        }

        if (gamepad1.right_bumper){
            if (bump_right){
                serv_right.setPosition(0.5);  // Closed
            }else{
                serv_right.setPosition(0.2); // Opened
            }
            bump_right =!bump_right;
        }

        if (gamepad1.left_bumper){
            if (bump_left){
                serv_left.setPosition(0.4); // Opened
            }else{
                serv_left.setPosition(0.1); // Closed
            }
            bump_left =!bump_left;
        }

        if (gamepad1.circle) {
            if (serv_left.getPosition() >= 0.2 && serv_right.getPosition() <= 0.3) {
                serv_left.setPosition(0.1);
                serv_right.setPosition(0.5);
            }
            else {
                serv_left.setPosition(0.4);
                serv_right.setPosition(0.2);
            }
        }

        if (gamepad1.dpad_up){
            servo_up.setPosition(0.19);

        }

        if (gamepad1.dpad_down){
            servo_up.setPosition(0.44);
        }

        if (gamepad1.circle || gamepad1.square || gamepad1.cross || gamepad1.right_bumper || gamepad1.left_bumper || gamepad1.dpad_up || gamepad1.dpad_down) {
            sleep(200);
        }
    }


    void print(String output) {
        console.add(output);
        if (console.size() > 10) { console.remove(0); }
        for (String line : console) {
            telemetry.addLine(line);
        }
        telemetry.update();
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

        servo_up.setPosition(0.44);
    }

    double get_current_rotation() {
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
    }
}
