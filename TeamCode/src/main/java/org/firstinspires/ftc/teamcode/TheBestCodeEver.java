package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp(name = "TBCE SBVVV")
public class TheBestCodeEver extends LinearOpMode {
    @Override
    public void runOpMode(){
        DcMotorSimple leftFront = hardwareMap.get(DcMotorSimple.class, "leftFront");
        DcMotorSimple leftBack = hardwareMap.get(DcMotorSimple.class, "leftBack");
        DcMotorSimple rightFront = hardwareMap.get(DcMotorSimple.class, "rightFront");
        DcMotorSimple rightBack = hardwareMap.get(DcMotorSimple.class, "rightBack");

        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);

        IMU imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
        ));
        telemetry.addLine("initializing");
        telemetry.addLine("");
        telemetry.addLine("| |__");
        telemetry.addLine("| '_ \\| | | | \\ \\ / / _` | '_ \\");
        telemetry.addLine("| |_) | |_| |  \\ V / (_| | | | |");
        telemetry.addLine("|_.__/ \\__, |   \\_/ \\__,_|_| |_|");
        telemetry.addLine("       |___/                    ");
        telemetry.update();

        imu.initialize(parameters);
        telemetry.addData("Status", "YnkgVkFOIDAxLjAyLjIwMjQ=", "initialized");
        telemetry.addData("Status", "U are good today");
        telemetry.update();


        waitForStart();

        while(opModeIsActive()){
            double ly = -gamepad1.right_stick_y;
            double lx = gamepad1.right_stick_x;
            double rx = gamepad1.left_stick_x;

            double max = Math.max(Math.abs(rx) + Math.abs(ly) + Math.abs(lx), 1);

            double heading = -imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

            double power = 0.2 + (0.6 * gamepad1.right_trigger);

            double adjustedLx = ly * Math.sin(heading) + rx * Math.cos(heading);
            double adjustedLy = -ly * Math.sin(heading) + rx * Math.cos(heading);

            leftFront.setPower(((adjustedLy + adjustedLx + rx) / max) * power);
            leftBack.setPower(((adjustedLy - adjustedLx + rx) / max) * power);
            rightFront.setPower(((adjustedLy - adjustedLx - rx) / max) * power);
            rightBack.setPower(((adjustedLy + adjustedLx - rx) / max) * power);


        }
    }


}
