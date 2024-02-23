package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.LinkedList;

@Autonomous
public class Autonomqa extends LinearOpMode {

    private DcMotor RightDrive_fr, RightDrive_ass;
    private DcMotor LeftDrive_fr, LeftDrive_ass;
    private DcMotor lift_right, lift_left;
    private Servo serv_hang_himself;
    private Servo servo_up;
    private Servo serv_right, serv_left;

    private final LinkedList<String> console = new LinkedList<>();
    @Override
    public void runOpMode() {
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


        for (DcMotor motor : new DcMotor[] { LeftDrive_fr, LeftDrive_ass, lift_left }) {
            //motor.setDirection(DcMotor.Direction.REVERSE);
        }

        waitForStart();
        if (opModeIsActive()) {
            print(String.format("Right FR: %s. Right B: %s. Left FR: %s, Left B: %s",
                    RightDrive_fr.getCurrentPosition(),
                    RightDrive_ass.getCurrentPosition(),
                    LeftDrive_fr.getCurrentPosition(),
                    LeftDrive_ass.getCurrentPosition()));
            rotate(9999);
        }
    }


    void rotate(int degrees) {
        int current_pos = RightDrive_fr.getCurrentPosition(), target_pos = RightDrive_fr.getCurrentPosition() + degrees * 10;
        int spread = target_pos - current_pos;
        while (RightDrive_fr.getCurrentPosition() < target_pos) {
            print(String.format("Position: %s. Percent: %s", RightDrive_fr.getCurrentPosition(), (target_pos - RightDrive_fr.getCurrentPosition()) / spread));
            for (DcMotor motor : new DcMotor[] { RightDrive_fr, RightDrive_ass, LeftDrive_ass, LeftDrive_fr}) {
                motor.setPower(
                        (target_pos - RightDrive_fr.getCurrentPosition()) / spread
                );
            }
        }


        for (DcMotor motor : new DcMotor[] { RightDrive_fr, RightDrive_ass, LeftDrive_ass, LeftDrive_fr}) {
            motor.setPower(0);
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
}