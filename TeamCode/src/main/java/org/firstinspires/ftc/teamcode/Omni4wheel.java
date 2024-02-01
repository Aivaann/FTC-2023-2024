package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class Omni4wheel extends LinearOpMode
{

    // задаём моторы, название мотров в коде, изначально они не объявлены
    private DcMotor rightDrive_ass, rightDrive_fr, leftDrive_ass, leftDrive_fr  = null;
    // а так как нам очень скучно, то мы заодно и указываем на то что они не объявлены
    boolean k = true;

    public void motors_setPower()
    { // публичная функция не возвращаяет значения - void
        double axial = -gamepad1.right_stick_y;
        double lateral =  gamepad1.right_stick_x;
        double yaw = gamepad1.left_stick_x;
        double rightFrontPower;
        double leftFrontPower;
        double leftBackPower;
        double rightBackPower;
        yaw *= 10;

        if ((lateral <= 0.1 && lateral > 0) || (lateral >= -0.1 && lateral < 0)){
            rightFrontPower = -Math.sqrt(45) * axial + yaw;
            leftFrontPower = Math.sqrt(45) * axial + yaw;
            leftBackPower = -Math.sqrt(45) * axial + yaw;
            rightBackPower = Math.sqrt(45) * axial + yaw;
        }
        else if ((axial <= 0.1 && axial > 0) || (axial >= -0.1 && axial < 0)){
            rightFrontPower = -Math.sqrt(45) * lateral + yaw;
            leftFrontPower = Math.sqrt(45) * lateral + yaw;
            leftBackPower = Math.sqrt(45) * lateral + yaw;
            rightBackPower = -Math.sqrt(45) * lateral + yaw;
        }
        else{
            rightFrontPower = -Math.sqrt(45) * axial - Math.sqrt(45) * lateral + yaw;
            leftFrontPower = Math.sqrt(45) * axial + Math.sqrt(45) * lateral + yaw;
            leftBackPower = -Math.sqrt(45) * axial + Math.sqrt(45) * lateral + yaw;
            rightBackPower = Math.sqrt(45) * axial - Math.sqrt(45) * lateral + yaw;
        }
        rightFrontPower *=2;
        leftFrontPower *=2;
        leftBackPower *=2;
        rightBackPower *=2;
        if (gamepad1.square){
            k=!k;
        }
        if (!k){
            rightFrontPower = rightFrontPower/Math.abs(rightFrontPower)*Math.min(Math.abs(rightFrontPower)/4, 0.5);
            leftFrontPower = leftFrontPower/Math.abs(leftFrontPower) * Math.min(Math.abs(leftFrontPower)/4, 0.5);
            leftBackPower = leftBackPower/Math.abs(leftBackPower)*Math.min(Math.abs(leftBackPower)/4, 0.5);
            rightBackPower = rightBackPower/Math.abs(rightBackPower)*Math.min(Math.abs(rightBackPower)/4, 0.5);
        }
        leftDrive_ass.setPower(leftBackPower); // подаём мощность на моторы
        leftDrive_fr.setPower(leftFrontPower);
        rightDrive_ass.setPower(rightBackPower);
        rightDrive_fr.setPower(rightFrontPower);
    }

    //начнём основную часть
    @Override
    public void runOpMode() {

        telemetry.addData("Status", "Initialized"); // выводим телеметрию
        telemetry.update(); // обновляем

        leftDrive_fr = hardwareMap.get(DcMotor.class, "left_fr"); // задаём названия моторов для хабов, потом в config на хабе их выставляем
        leftDrive_ass = hardwareMap.get(DcMotor.class, "left_ass");
        rightDrive_fr = hardwareMap.get(DcMotor.class, "right_fr");
        rightDrive_ass = hardwareMap.get(DcMotor.class, "right_ass");
        leftDrive_fr.setDirection(DcMotor.Direction.REVERSE); // куда изначально крутятся моторы, тк моторы могут быть направлены в разные стороны
        leftDrive_ass.setDirection(DcMotor.Direction.REVERSE);
        rightDrive_fr.setDirection(DcMotor.Direction.REVERSE);
        rightDrive_ass.setDirection(DcMotor.Direction.REVERSE);
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
