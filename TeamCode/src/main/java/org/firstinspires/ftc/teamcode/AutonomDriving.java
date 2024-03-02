package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import java.util.LinkedList;

@Autonomous
public class AutonomDriving extends LinearOpMode {
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
            rotate(90);
            sleep(2000);
        }
    }


    void rotate(int degrees) {
        double target = get_current_rotation() + degrees;
        if (target > 360) {
            target = 360 - target;
        }

        double delta = target - get_current_rotation();

        while(get_current_rotation() * 0.9 < target || get_current_rotation() * 1.1 > target) {
            set_motors_power(-0.2);
        }
    }
    void print(String output) {
        console.add(output);
        if (console.size() > 10) { console.remove(0); }
        for (String line : console) {
            telemetry.addLine(line);
        }
        telemetry.update();
    }void print(int output) {
        print(String.valueOf(output));
    }void print(double output) {
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
        for (DcMotor motor : new DcMotor[]{RightDrive_fr, RightDrive_ass, LeftDrive_ass, LeftDrive_fr}) {
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
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

        set_motors_power(0.2);
        set_mode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    void set_mode(DcMotor.RunMode mode) {
        for (DcMotor motor : new DcMotor[]{RightDrive_fr, RightDrive_ass, LeftDrive_ass, LeftDrive_fr}) {
            motor.setMode(mode);
        }
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
