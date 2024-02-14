package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Pumbiwe")
public class Omni4wheel extends LinearOpMode
{
    private DcMotor
            rightDrive_ass,
            rightDrive_fr,
            leftDrive_ass,
            leftDrive_fr,
            lift_1,
            lift_2;
    boolean k = true;
    double y = 0;

    public void motors_setPower()
    {
        double axial   = -gamepad1.right_stick_y;
        double lateral =  gamepad1.right_stick_x;
        double yaw     =  -gamepad1.left_stick_x;
        double rightFrontPower;
        double leftFrontPower;
        double leftBackPower;
        double rightBackPower;
        yaw /= 4;
        if ((lateral <= 0.05 && lateral > 0) ||  (lateral >= -0.05 && lateral < 0)){
        rightFrontPower  = -Math.sin(45) * axial - yaw;
        leftFrontPower = Math.sin(45) * axial - yaw;
        leftBackPower   = -Math.sin(45) * axial - yaw;
        rightBackPower  = Math.sin(45) * axial - yaw;
    }else if ((axial <= 0.05 && axial > 0) || (axial >= -0.05 && axial < 0)){
        rightFrontPower  = - Math.sin(45) * lateral - yaw;
        leftFrontPower = Math.sin(45) * lateral - yaw;
        leftBackPower   = Math.sin(45) * lateral - yaw;
        rightBackPower  = - Math.sin(45) * lateral - yaw;
    }else{
        rightFrontPower  = -Math.sin(45) * axial - Math.sin(45) * lateral - yaw;
        leftFrontPower = Math.sin(45) * axial + Math.sin(45) * lateral - yaw;
        leftBackPower   = -Math.sin(45) * axial + Math.sin(45) * lateral - yaw;
        rightBackPower  = Math.sin(45) * axial - Math.sin(45) * lateral - yaw;
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
        if (Math.abs(y)>Math.abs(yaw)){
            rightFrontPower *= 0;
            leftFrontPower *= 0;
            leftBackPower *= 0;
            rightBackPower *= 0;
        }
        leftDrive_ass.setPower(leftBackPower); // подаём мощность на моторы
        leftDrive_fr.setPower(leftFrontPower);
        rightDrive_ass.setPower(rightBackPower);
        rightDrive_fr.setPower(rightFrontPower);
        lift_1.setPower(gamepad1.right_trigger - gamepad1.left_trigger);
        lift_2.setPower(gamepad1.right_trigger - gamepad1.left_trigger);

        y=yaw;
    }

    //начнём оснавную часть
    @Override
    public void runOpMode() {

        telemetry.addData("Status", "Initialized"); // выводим телеметрию
        telemetry.update(); // обновляем

        leftDrive_fr = hardwareMap.get(DcMotor.class, "left_fr"); // задаём названия моторов для хабов, потом в config на хабе их выставляем
        leftDrive_ass = hardwareMap.get(DcMotor.class, "right_fr");
        rightDrive_fr = hardwareMap.get(DcMotor.class, "right_ass");
        rightDrive_ass = hardwareMap.get(DcMotor.class, "left_ass");
        lift_1 = hardwareMap.get(DcMotor.class, "lift_1");
        lift_2 = hardwareMap.get(DcMotor.class, "lift_2");

        leftDrive_fr.setDirection(DcMotor.Direction.REVERSE); // куда изначально крутятся моторы, тк моторы могут быть направлены в разные стороны
        leftDrive_ass.setDirection(DcMotor.Direction.REVERSE);
        rightDrive_fr.setDirection(DcMotor.Direction.REVERSE);
        rightDrive_ass.setDirection(DcMotor.Direction.REVERSE);
        lift_1.setDirection(DcMotor.Direction.FORWARD);
        lift_2.setDirection(DcMotor.Direction.REVERSE);
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