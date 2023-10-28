package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.opencv.core.Mat;

@TeleOp
public class upd_test extends LinearOpMode {
    private DcMotor DcMotor1;
    private DcMotor DcMotor2;
    private DcMotor DcMotor3;
    private DcMotor handUp;
    double motorPower1;
    double motorPower2;
    double motorPower3;
    double power;

    public void DcMotorPower()
    {
        double X = gamepad1.right_stick_x;
        double Y = gamepad1.right_stick_y;
        motorPower1 = -0.5 * X - Math.sqrt(3)/2 * Y + power;
        motorPower2 = -0.5 * X + Math.sqrt(3)/2 * Y + power;
        motorPower3 = X + power;
        DcMotor1.setPower(motorPower1);
        DcMotor2.setPower(motorPower2);
        DcMotor3.setPower(motorPower3);
    }

    public void motorTurn(){
        double x = gamepad1.left_stick_x;
        DcMotor1.setPower(-x);
        DcMotor2.setPower(-x);
        DcMotor3.setPower(-x);
    }

    public void take(){
        boolean x = gamepad1.right_bumper;
        double y=0;
        if (x == true){
            y=1;
        }
        handUp.setPower(y);
        boolean x1 = gamepad1.left_bumper;
        double y1 = 0;
        if (x1 == true){
            y1 = -1;
        }
        if (y1 != 0.0){
            handUp.setPower(y1);
        }
    }


    @Override
    public void runOpMode() {
        DcMotor1 = hardwareMap.get(DcMotor.class, "DcMotor1");
        DcMotor2 = hardwareMap.get(DcMotor.class, "DcMotor2");
        DcMotor3 = hardwareMap.get(DcMotor.class, "DcMotor3");
        handUp = hardwareMap.get(DcMotor.class, "handUp");
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();


        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            telemetry.addData("Status", "Running");
            telemetry.update();
            DcMotorPower();
            motorTurn();
            take();
        }
    }
}
