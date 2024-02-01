package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="blue bad position right", group="Test")
public class bluebad extends LinearOpMode
{
    static final double HD_COUNTS_PER_REV = 24;// скопированные из инета переменные (я верю что именно столько считывает енкодер за оборот)
    static final double DRIVE_GEAR_REDUCTION = 1;
    static final double WHEEL_CIRCUMFERENCE_MM = 141.5;
    static final double DRIVE_COUNTS_PER_MM = (DRIVE_GEAR_REDUCTION * HD_COUNTS_PER_REV / WHEEL_CIRCUMFERENCE_MM);
    @Override
    public void runOpMode() {
        waitForStart();
        DcMotor leftDrive_fr = hardwareMap.get(DcMotor.class, "left_fr"); // задаём названия моторов для хабов, потом в config на хабе их выставляем
        DcMotor leftDrive_ass = hardwareMap.get(DcMotor.class, "left_ass");
        DcMotor rightDrive_fr = hardwareMap.get(DcMotor.class, "right_fr");
        DcMotor rightDrive_ass = hardwareMap.get(DcMotor.class, "right_ass");
        leftDrive_fr.setDirection(DcMotor.Direction.REVERSE); // куда изначально крутятся моторы, тк моторы могут быть направлены в разные стороны
        leftDrive_ass.setDirection(DcMotor.Direction.REVERSE);
        rightDrive_fr.setDirection(DcMotor.Direction.REVERSE);
        rightDrive_ass.setDirection(DcMotor.Direction.REVERSE);
        rightDrive_fr.setPower(-0.3);
        leftDrive_fr.setPower(0.3);
        rightDrive_ass.setPower(-0.3);
        leftDrive_ass.setPower(0.3);
        sleep(1000);
        rightDrive_fr.setPower(0);
        leftDrive_fr.setPower(0);
        rightDrive_ass.setPower(0);
        leftDrive_ass.setPower(0);
        sleep(3000);
        rightDrive_fr.setPower(-0.3);
        leftDrive_fr.setPower(0.3);
        rightDrive_ass.setPower(0.3);
        leftDrive_ass.setPower(-0.3);
        sleep(2250);
        rightDrive_fr.setPower(0);
        leftDrive_fr.setPower(0);
        rightDrive_ass.setPower(0);
        leftDrive_ass.setPower(0);
        sleep(3000);
        rightDrive_fr.setPower(0.3);
        leftDrive_fr.setPower(-0.3);
        rightDrive_ass.setPower(-0.3);
        leftDrive_ass.setPower(0.3);
        sleep(1750);
        rightDrive_fr.setPower(0);
        leftDrive_fr.setPower(0);
        rightDrive_ass.setPower(0);
        leftDrive_ass.setPower(0);
        sleep(3000);
        rightDrive_fr.setPower(0.3);
        leftDrive_fr.setPower(0.3);
        rightDrive_ass.setPower(0.3);
        leftDrive_ass.setPower(0.3);
        sleep(500);
        rightDrive_fr.setPower(0);
        leftDrive_fr.setPower(0);
        rightDrive_ass.setPower(0);
        leftDrive_ass.setPower(0);
        sleep(3000);
    }
}