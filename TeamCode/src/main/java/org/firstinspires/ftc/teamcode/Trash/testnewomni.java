package org.firstinspires.ftc.teamcode.Trash;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class testnewomni extends LinearOpMode {

    private DcMotor RightDrive_fr, RightDrive_ass;
    private DcMotor LeftDrive_fr, LeftDrive_ass;
    double dif_r_fr = 0, dif_r_ass = 0, dif_l_fr = 0, dif_l_ass = 0;

    public void DcMotorPower() {
        double main_x = -gamepad1.right_stick_x,
                main_y = -gamepad1.right_stick_y - gamepad1.left_stick_y,
                not_main_x =  -gamepad1.left_stick_x / 2;
        telemetry.addData("power", main_y);
        telemetry.update();
        not_main_x*=Math.max((Math.abs(main_y)+Math.abs(main_x))*4,1.3);
        //dif_r_fr = 0; dif_r_ass = 0; dif_l_fr = 0; dif_l_ass = 0;
        double RightDrive_fr_power = (main_y - main_x + not_main_x)*0.95 + dif_r_fr/70*0.05;
        double RightDrive_ass_power = (main_y + main_x + not_main_x)*0.95 + dif_r_ass/70*0.05;
        double LeftDrive_fr_power = (main_y - main_x - not_main_x)*0.95 + dif_l_fr/70*0.05;
        double LeftDrive_ass_power = (main_y + main_x - not_main_x)*0.95 + dif_l_ass/70*0.05;
        LeftDrive_ass.setPower(LeftDrive_ass_power);
        LeftDrive_fr.setPower(LeftDrive_fr_power);
        RightDrive_ass.setPower(RightDrive_ass_power);
        RightDrive_fr.setPower(RightDrive_fr_power);
    }
    @Override
    public void runOpMode() {

        RightDrive_fr = hardwareMap.get(DcMotor.class, "RightDrive_fr");
        LeftDrive_fr = hardwareMap.get(DcMotor.class, "LeftDrive_fr");
        RightDrive_ass = hardwareMap.get(DcMotor.class, "RightDrive_ass");
        LeftDrive_ass= hardwareMap.get(DcMotor.class, "LeftDrive_ass");
        for (DcMotor motor : new DcMotor[] { LeftDrive_fr, LeftDrive_ass}) {
            motor.setDirection(DcMotor.Direction.REVERSE);
        }
        for (DcMotor motor : new DcMotor[]{RightDrive_fr, RightDrive_ass, LeftDrive_ass, LeftDrive_fr}) {
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

        waitForStart();
        while (opModeIsActive()) {
            double RightDrive_fr_en_old = RightDrive_fr.getCurrentPosition(),
                    LeftDrive_fr_en_old = LeftDrive_fr.getCurrentPosition(),
                    RightDrive_ass_an_old = RightDrive_ass.getCurrentPosition(),
                    LeftDrive_ass_an_old = LeftDrive_ass.getCurrentPosition();
            DcMotorPower();
            sleep(30);
            double RightDrive_fr_en = RightDrive_fr.getCurrentPosition(),
                    LeftDrive_fr_en = LeftDrive_fr.getCurrentPosition(),
                    RightDrive_ass_an = RightDrive_ass.getCurrentPosition(),
                    LeftDrive_ass_an = LeftDrive_ass.getCurrentPosition();
            dif_r_fr = RightDrive_fr_en_old - RightDrive_fr_en;
            dif_r_ass = RightDrive_ass_an_old - RightDrive_ass_an;
            dif_l_fr = LeftDrive_fr_en_old - LeftDrive_fr_en;
            dif_l_ass = LeftDrive_ass_an_old - LeftDrive_ass_an;
        }
    }
}
