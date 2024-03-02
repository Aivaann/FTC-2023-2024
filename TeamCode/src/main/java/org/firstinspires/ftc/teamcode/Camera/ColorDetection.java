package org.firstinspires.ftc.teamcode.Camera;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class ColorDetection extends LinearOpMode {
    StealledCamera camera = new StealledCamera(hardwareMap);

    @Override
    public void runOpMode() {
        camera = new StealledCamera(hardwareMap);
        telemetry.addLine("Status: Initialized");
        waitForStart();

        while (opModeIsActive()) {
            telemetry.addLine(camera.getPipeline1Output());
            telemetry.update();
        }
    }
}