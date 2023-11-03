package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="Test Encoder", group="Test")
public class Omni4WheelAutonomus extends LinearOpMode
{
    private DcMotor rightDrive_ass, rightDrive_fr, leftDrive_ass, leftDrive_fr  = null;
    static final double HD_COUNTS_PER_REV = 28;// скопированные из инета переменные (я верю что именно столько считывает енкодер за оборот)
    static final double DRIVE_GEAR_REDUCTION = 1;
    static final double WHEEL_CIRCUMFERENCE_MM = 140;
    static final double DRIVE_COUNTS_PER_MM = (DRIVE_GEAR_REDUCTION * HD_COUNTS_PER_REV / WHEEL_CIRCUMFERENCE_MM);
    @Override
    public void runOpMode() {
        leftDrive_fr = hardwareMap.get(DcMotor.class, "left_fr"); // задаём названия моторов для хабов, потом в config на хабе их выставляем
        leftDrive_ass = hardwareMap.get(DcMotor.class, "left_ass");
        rightDrive_fr = hardwareMap.get(DcMotor.class, "right_fr");
        rightDrive_ass = hardwareMap.get(DcMotor.class, "right_ass");
        leftDrive_fr.setDirection(DcMotor.Direction.FORWARD); // куда изначально крутятся моторы, тк моторы могут быть направлены в разные стороны
        leftDrive_ass.setDirection(DcMotor.Direction.FORWARD);
        rightDrive_fr.setDirection(DcMotor.Direction.FORWARD);
        rightDrive_ass.setDirection(DcMotor.Direction.FORWARD);

        int rightFrTarget = rightDrive_fr.getCurrentPosition() + (int)(180 * DRIVE_COUNTS_PER_MM);// здесь мы объявляем сколько нам надо проехать (мм) в данном случае 180
        int leftFrTarget = leftDrive_fr.getCurrentPosition() - (int)(180 * DRIVE_COUNTS_PER_MM);
        int rightAssTarget = rightDrive_ass.getCurrentPosition() + (int)(180 * DRIVE_COUNTS_PER_MM);
        int leftAssTarget = leftDrive_ass.getCurrentPosition() - (int)(180 * DRIVE_COUNTS_PER_MM);

        rightDrive_fr.setTargetPosition(rightFrTarget);
        leftDrive_fr.setTargetPosition(leftFrTarget);
        rightDrive_ass.setTargetPosition(rightAssTarget);
        leftDrive_ass.setTargetPosition(leftAssTarget);
    }
}
