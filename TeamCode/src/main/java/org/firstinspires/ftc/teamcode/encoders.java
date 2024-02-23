package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous (name = "cringe", group="Test")
public class encoders extends LinearOpMode {

    private DcMotor RightDrive_fr, RightDrive_ass;
    private DcMotor LeftDrive_fr, LeftDrive_ass;


    //Convert from the counts per revolution of the encoder to counts per inch
    static final double HD_COUNTS_PER_REV = 28;
    static final double DRIVE_GEAR_REDUCTION = 2;
    static final double WHEEL_CIRCUMFERENCE_MM = 90 * Math.PI;
    static final double DRIVE_COUNTS_PER_MM = (HD_COUNTS_PER_REV * DRIVE_GEAR_REDUCTION) / WHEEL_CIRCUMFERENCE_MM;
    static final double DRIVE_COUNTS_PER_IN = DRIVE_COUNTS_PER_MM * 25.4;

    @Override
    public void runOpMode() {

        RightDrive_fr = hardwareMap.get(DcMotor.class, "right_fr");
        LeftDrive_fr = hardwareMap.get(DcMotor.class, "left_fr");
        RightDrive_ass = hardwareMap.get(DcMotor.class, "right_ass");
        LeftDrive_ass= hardwareMap.get(DcMotor.class, "left_ass");
        waitForStart();
        if (opModeIsActive()) {
            int rdf = RightDrive_fr.getCurrentPosition(),
                    ldf = LeftDrive_fr.getCurrentPosition(),
                    rda = RightDrive_ass.getCurrentPosition(),
                    lda = LeftDrive_ass.getCurrentPosition();
            rdf+=6000; ldf += 6000; rda += 6000; lda += 6000;
            RightDrive_fr.setTargetPosition(rdf);
            RightDrive_fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RightDrive_fr.setPower(0.8);
            LeftDrive_fr.setTargetPosition(ldf);
            LeftDrive_fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            LeftDrive_fr.setPower(0.8);
            RightDrive_ass.setTargetPosition(rda);
            RightDrive_ass.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RightDrive_ass.setPower(0.8);
            LeftDrive_ass.setTargetPosition(lda);
            LeftDrive_ass.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            LeftDrive_ass.setPower(0.8);
        }
    }
}