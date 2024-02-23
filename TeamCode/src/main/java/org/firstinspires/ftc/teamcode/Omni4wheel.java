package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name = "Pumbiwe")
public class Omni4wheel extends LinearOpMode
{
    private DcMotor rightDrive_ass,
            rightDrive_fr,
            leftDrive_ass,
            leftDrive_fr;
    private DcMotorEx lift_1,
            lift_2;
    boolean k = true;
    double y = 0;
    double         rightFrontPower,
            leftFrontPower,
            leftBackPower,
            rightBackPower;

    public void joystickMovement()
    {
        double axial = -gamepad1.right_stick_x,
        lateral = -gamepad1.right_stick_y,
        yaw =  -gamepad1.left_stick_x / 2;

        yaw*=Math.max((Math.abs(lateral)+Math.abs(axial))*4,1.3);

        rightFrontPower  = (-axial - lateral - yaw);
        leftFrontPower = (axial + lateral - yaw);
        leftBackPower   = (-axial + lateral - yaw);
        rightBackPower  = (axial - lateral - yaw);

        rightFrontPower *=1.4;
        leftFrontPower *=1.4;
        leftBackPower *=1.4;
        rightBackPower *=1.4;

        if (gamepad1.square){
            k=!k;
        }
        if (!k){
            rightFrontPower = rightFrontPower/Math.abs(rightFrontPower)*Math.min(Math.abs(rightFrontPower)/4, 0.5);
            leftFrontPower = leftFrontPower/Math.abs(leftFrontPower) * Math.min(Math.abs(leftFrontPower)/4, 0.5);
            leftBackPower = leftBackPower/Math.abs(leftBackPower)*Math.min(Math.abs(leftBackPower)/4, 0.5);
            rightBackPower = rightBackPower/Math.abs(rightBackPower)*Math.min(Math.abs(rightBackPower)/4, 0.5);
        }


        if (Math.abs(y)>Math.abs(yaw)){
            rightFrontPower *= 0;
            leftFrontPower *= 0;
            leftBackPower *= 0;
            rightBackPower *= 0;
        }
        leftDrive_ass.setPower(leftBackPower);
        leftDrive_fr.setPower(leftFrontPower);
        rightDrive_ass.setPower(rightBackPower);
        rightDrive_fr.setPower(rightFrontPower);

        update_lifts_values();

        //lift_1.setPower(gamepad1.right_trigger - gamepad1.left_trigger);
        //lift_2.setPower(gamepad1.right_trigger - gamepad1.left_trigger);

        y=yaw;
    }

    void update_lifts_values() {
        float value = lift_1.getCurrentPosition() + gamepad1.right_trigger - gamepad1.left_trigger;
        int min_value = 50;
        int max_value = (int) (6700 - lift_1.getVelocity() / 5.125);
        if (gamepad1.ps || (value >= min_value && value <= max_value) ||
                (lift_1.getCurrentPosition() <= min_value && (gamepad1.right_trigger - gamepad1.left_trigger > 0)) ||
                (lift_1.getCurrentPosition() >= max_value && (gamepad1.right_trigger - gamepad1.left_trigger < 0)))
        {
            lift_1.setPower(gamepad1.right_trigger - gamepad1.left_trigger);
            lift_2.setPower(gamepad1.right_trigger - gamepad1.left_trigger);
        }

    }


    @Override
    public void runOpMode() {
        leftDrive_fr = hardwareMap.get(DcMotor.class, "left_fr");
        leftDrive_ass = hardwareMap.get(DcMotor.class, "right_fr");
        rightDrive_fr = hardwareMap.get(DcMotor.class, "right_ass");
        rightDrive_ass = hardwareMap.get(DcMotor.class, "left_ass");
        lift_1 = hardwareMap.get(DcMotorEx.class, "lift_1");
        lift_2 = hardwareMap.get(DcMotorEx.class, "lift_2");

        leftDrive_fr.setDirection(DcMotor.Direction.REVERSE);
        leftDrive_ass.setDirection(DcMotor.Direction.REVERSE);
        rightDrive_fr.setDirection(DcMotor.Direction.REVERSE);
        rightDrive_ass.setDirection(DcMotor.Direction.REVERSE);
        lift_1.setDirection(DcMotor.Direction.FORWARD);
        lift_2.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive()) {
            joystickMovement();
            get_lifts_encoders();
        }
    }

    void get_lifts_encoders() {
        //lift_1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //lift_2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        int lift_1_position = lift_1.getCurrentPosition(),
                lift_2_position = lift_2.getCurrentPosition();
        printer("Lift 1 position: " + lift_1_position + ". Lift velocity: " + lift_1.getVelocity());

        // lift_1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        // lift_2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    void printer(String output) {
        telemetry.addLine(output);
        telemetry.update();
    }
}