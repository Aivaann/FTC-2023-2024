package org.firstinspires.ftc.teamcode;
//VAN 22.10.2023
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class Test extends LinearOpMode {
    private DcMotor DcMotor1;
    private DcMotor DcMotor2;
    private DcMotor DcMotor3;
    private DcMotor DcMotor4;
    double motorPower1;
    double motorPower2;
    double motorPower3;
    double power;
    double limitation;
    double upperlimit;
    double lowerlimit;

    public void DcMotorPower()
    {
        double X = gamepad1.left_stick_x;;
        double Y = gamepad1.left_stick_y;
        motorPower1 = -0.5 * X - Math.sqrt(3)/2 * Y + power;
        motorPower2 = -0.5 * X + Math.sqrt(3)/2 * Y + power;
        motorPower3 = X + power;
        DcMotor1.setPower(motorPower1);
        DcMotor2.setPower(motorPower2);
        DcMotor3.setPower(motorPower3);
        double rotation = gamepad1.right_stick_x;
        motorPower1 = rotation;
        motorPower2 = rotation;
        motorPower3 = rotation;


    }

    public void LiftMotor()
    {
        double lift = gamepad1.right_stick_y;
        limitation += lift;
        if (limitation < upperlimit && limitation > lowerlimit)
        {
            DcMotor4.setPower(lift);
        }


    }


    @Override
    public void runOpMode() {
        DcMotor1 = hardwareMap.get(DcMotor.class, "DcMotor1");
        DcMotor2 = hardwareMap.get(DcMotor.class, "DcMotor2");
        DcMotor3 = hardwareMap.get(DcMotor.class, "DcMotor3");
        DcMotor4 = hardwareMap.get(DcMotor.class, "DcMotor4");
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();


        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            telemetry.addData("Status", "Running");
            telemetry.update();
            DcMotorPower();

        }
    }
}
