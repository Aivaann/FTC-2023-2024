package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class Omni4wheel extends LinearOpMode
{

    // задаём моторы, название мотров в коде, изначально они не объявлены
    private DcMotor rightDrive_ass, rightDrive_fr, leftDrive_ass, leftDrive_fr  = null;
    // а так как нам очень скучно, то мы заодно и указываем на то что они не объявлены

    public void motors_setPower()
    { // публичная функция не возвращаяет значения - void
        double axial   = -gamepad1.right_stick_y;
        double lateral =  gamepad1.right_stick_x;
        double yaw     =  gamepad1.left_stick_x;
        double leftFrontPower  = Math.sin(45) * axial + Math.cos(45) * lateral + yaw;
        double rightFrontPower = Math.sin(135) * axial - Math.cos(135) * lateral - yaw;
        double leftBackPower   = Math.sin(225) * axial - Math.cos(225) * lateral + yaw;
        double rightBackPower  = Math.sin(315) * axial + Math.cos(315) * lateral - yaw;
        leftDrive_ass.setPower(leftBackPower); // подаём мощность на моторы
        leftDrive_fr.setPower(rightFrontPower);
        rightDrive_ass.setPower(rightBackPower);
        rightDrive_fr.setPower(leftFrontPower);
    }

    //начнём оснавную часть
    @Override
    public void runOpMode() {

        telemetry.addData("Status", "Initialized"); // выводим телеметрию
        telemetry.update(); // обновляем

        leftDrive_fr = hardwareMap.get(DcMotor.class, "left_fr"); // задаём названия моторов для хабов, потом в config на хабе их выставляем
        leftDrive_ass = hardwareMap.get(DcMotor.class, "left_ass");
        rightDrive_fr = hardwareMap.get(DcMotor.class, "right_fr");
        rightDrive_ass = hardwareMap.get(DcMotor.class, "right_ass");
        leftDrive_fr.setDirection(DcMotor.Direction.FORWARD); // куда изначально крутятся моторы, тк моторы могут быть направлены в разные стороны
        leftDrive_ass.setDirection(DcMotor.Direction.FORWARD);
        rightDrive_fr.setDirection(DcMotor.Direction.FORWARD);
        rightDrive_ass.setDirection(DcMotor.Direction.FORWARD);
        waitForStart(); // ожидаем запуск программы
        //runtime.reset();

        while (opModeIsActive()) {

            motors_setPower(); // вызываем функцию, передаём значения с джойстика

            telemetry.addData("Enc_left", leftDrive_fr.getCurrentPosition()); //вывод телеметрии
            //telemetry.addData("Status", "Run Time: " + runtime.toString()); // вывод телеметрии
            telemetry.update(); // обновление
        }
    }
}
