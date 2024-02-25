package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class Omni4WheelAutonomus extends LinearOpMode
{
    static final double HD_COUNTS_PER_REV = 24;// скопированные из инета переменные (я верю что именно столько считывает енкодер за оборот)
    static final double DRIVE_GEAR_REDUCTION = 1;
    static final double WHEEL_CIRCUMFERENCE_MM = 141.5;
    static final double DRIVE_COUNTS_PER_MM = (DRIVE_GEAR_REDUCTION * HD_COUNTS_PER_REV / WHEEL_CIRCUMFERENCE_MM);
    @Override
    public void runOpMode() {
        DcMotor leftDrive_fr = hardwareMap.get(DcMotor.class, "left_fr"); // задаём названия моторов для хабов, потом в config на хабе их выставляем
        DcMotor leftDrive_ass = hardwareMap.get(DcMotor.class, "left_ass");
        DcMotor rightDrive_fr = hardwareMap.get(DcMotor.class, "right_fr");
        DcMotor rightDrive_ass = hardwareMap.get(DcMotor.class, "right_ass");

        leftDrive_fr.setDirection(DcMotor.Direction.FORWARD); // куда изначально крутятся моторы, тк моторы могут быть направлены в разные стороны
        leftDrive_ass.setDirection(DcMotor.Direction.REVERSE);
        rightDrive_fr.setDirection(DcMotor.Direction.REVERSE);
        rightDrive_ass.setDirection(DcMotor.Direction.REVERSE);

        rightDrive_fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftDrive_fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive_ass.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftDrive_ass.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        rightDrive_fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftDrive_fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive_ass.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftDrive_ass.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        int rightFrTarget = rightDrive_fr.getCurrentPosition() - (int)(100 * DRIVE_COUNTS_PER_MM);// здесь мы объявляем сколько нам надо проехать (мм) в данном случае 180
        int leftFrTarget = leftDrive_fr.getCurrentPosition() + (int)(100 * DRIVE_COUNTS_PER_MM);
        int rightAssTarget = rightDrive_ass.getCurrentPosition() - (int)(100 * DRIVE_COUNTS_PER_MM);
        int leftAssTarget = leftDrive_ass.getCurrentPosition() + (int)(100 * DRIVE_COUNTS_PER_MM);

        rightDrive_fr.setTargetPosition(rightFrTarget);
        leftDrive_fr.setTargetPosition(leftFrTarget);
        rightDrive_ass.setTargetPosition(rightAssTarget);
        leftDrive_ass.setTargetPosition(leftAssTarget);


        rightDrive_fr.setPower(1);
        leftDrive_fr.setPower(0);
        rightDrive_ass.setPower(0);
        leftDrive_ass.setPower(0);

        rightDrive_fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftDrive_fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDrive_ass.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftDrive_ass.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while (rightDrive_fr.isBusy()){
            telemetry.addData("Right front drive pos:", rightDrive_fr.getCurrentPosition());
            telemetry.addData("Left front drive pos:", leftDrive_fr.getCurrentPosition());
            telemetry.addData("Right back drive pos:", rightDrive_ass.getCurrentPosition());
            telemetry.addData("left back drive pos:", leftDrive_ass.getCurrentPosition());
        }
        rightDrive_fr.setPower(0);
        leftDrive_fr.setPower(0);
        rightDrive_ass.setPower(0);
        leftDrive_ass.setPower(0);
        /*rightDrive_fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftDrive_fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive_ass.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftDrive_ass.setMode(DcMotor.RunMode.RUN_USING_ENCODER);*/
    }
}