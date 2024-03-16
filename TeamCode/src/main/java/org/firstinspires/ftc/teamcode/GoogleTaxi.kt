package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor

@TeleOp(name="Google Taxi")
class GoogleTaxi : LinearOpMode() {

    private var RightDrive_fr: DcMotor? = null
    private var RightDrive_ass: DcMotor? = null
    private var LeftDrive_fr: DcMotor? = null
    private var LeftDrive_ass: DcMotor? = null
    private var lift_left: DcMotor? = null
    private var lift_right: DcMotor? = null

    override fun runOpMode() {
        RightDrive_fr = hardwareMap.get("RightDrive_fr") as DcMotor
        RightDrive_ass = hardwareMap.get("RightDrive_ass") as DcMotor
        LeftDrive_fr = hardwareMap.get("LeftDrive_fr") as DcMotor
        LeftDrive_ass = hardwareMap.get("LeftDrive_ass") as DcMotor
        lift_left = hardwareMap.get("lift_left") as DcMotor
        lift_right = hardwareMap.get("lift_right") as DcMotor

        for (motor in arrayOf(LeftDrive_fr, LeftDrive_ass, RightDrive_fr, RightDrive_ass)) {
            motor?.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        }

        waitForStart()

        while (opModeIsActive()) {
            motorMovement()
        }
    }

    fun motorMovement() {
        val x = gamepad1.right_stick_x as Float
        val y = gamepad1.right_stick_y as Float

        lift_left?.power = (x + y).toDouble()
    }
}