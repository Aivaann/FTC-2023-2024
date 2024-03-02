package org.firstinspires.ftc.teamcode.Trash;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(group="Concept")
public class TestNewMotors extends LinearOpMode {
    private DcMotor motor;

    private void mor_power(double power) {
        motor.setPower(power);
    }
    @Override
    public void runOpMode(){
        motor = hardwareMap.get(DcMotor.class, "motor");
        motor.setDirection(DcMotor.Direction.FORWARD);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        waitForStart();
        while (opModeIsActive()){
            mor_power(1);
            telemetry.addData("motor :", motor.getCurrentPosition());
            telemetry.update();
        }
    }
}
