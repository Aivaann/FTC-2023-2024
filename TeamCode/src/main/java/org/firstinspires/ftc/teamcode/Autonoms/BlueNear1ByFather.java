package org.firstinspires.ftc.teamcode.Autonoms;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

public class BlueNear1ByFather extends LinearOpMode {
    private DcMotor RightDrive_fr, RightDrive_ass;
    private DcMotor LeftDrive_fr, LeftDrive_ass;
    private DcMotor lift_right, lift_left;

    boolean slow_mode = true;
    boolean t = true;
    private Servo serv_hang_himself;
    private Servo servo_up;
    private Servo serv_right, serv_left;
    IMU imu;
    @Override
    public void runOpMode() {
        declare_variables();
        AutonomFather father = new AutonomFather(RightDrive_fr,
                LeftDrive_fr,
                LeftDrive_ass,
                lift_right,
                lift_left,
                serv_hang_himself,
                servo_up,
                serv_right,
                serv_left,
                "0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 -0.02430675 false false 0.0 0.0 false false;0.0 0.0 -0.02430675 false false 0.0 0.0 false false;0.0 0.0 -0.02430675 false false 0.0 0.0 false false;0.0 0.0 -0.049982883 false false 0.0 0.0 false false;0.0 0.0 -0.2725094 false false 0.0 0.0 false false;0.0 -0.007189324 -0.30674428 false false 0.0 0.0 false false;0.0 -0.007189324 -0.3409791 false false 0.0 0.0 false false;0.0 -0.007189324 -0.5121533 false false 0.0 0.0 false false;0.0 -0.015748033 -0.6234166 false false 0.0 0.0 false false;0.0 -0.015748033 -0.6234166 false false 0.0 0.0 false false;0.0 -0.015748033 -0.5891818 false false 0.0 0.0 false false;0.0 -0.02430675 -0.5720644 false false 0.0 0.0 false false;0.0 -0.015748033 -0.5121533 false false 0.0 0.0 false false;0.0 -0.015748033 -0.5121533 false false 0.0 0.0 false false;-0.007189324 -0.02430675 -0.50359464 false false 0.0 0.0 false false;-0.015748033 -0.02430675 -0.50359464 false false 0.0 0.0 false false;-0.015748033 -0.02430675 -0.44368368 false false 0.0 0.0 false false;-0.02430675 -0.02430675 -0.3923314 false false 0.0 0.0 false false;-0.032865457 -0.02430675 -0.36665526 false false 0.0 0.0 false false;-0.13557002 -0.02430675 -0.28106812 false false 0.0 0.0 false false;-0.2725094 -0.02430675 -0.084217735 false false 0.0 0.0 false false;-0.30674428 -0.02430675 -0.007189324 false false 0.0 0.0 false false;-0.30674428 -0.015748033 0.0 false false 0.0 0.0 false false;-0.30674428 -0.007189324 0.0 false false 0.0 0.0 false false;-0.34953782 -0.007189324 0.0 false false 0.0 0.0 false false;-0.37521398 -0.007189324 0.0 false false 0.0 0.0 false false;-0.37521398 -0.007189324 0.0 false false 0.0 0.0 false false;-0.37521398 -0.007189324 0.0 false false 0.0 0.0 false false;-0.3923314 -0.007189324 -0.015748033 false false 0.0 0.0 false false;-0.3923314 -0.007189324 -0.015748033 false false 0.0 0.0 false false;-0.3409791 -0.007189324 -0.02430675 false false 0.0 0.0 false false;-0.1270113 -0.007189324 -0.041424174 false false 0.0 0.0 false false;-0.06710032 -0.007189324 -0.10133517 false false 0.0 0.0 false false;-0.015748033 -0.032865457 -0.28962687 false false 0.0 0.0 false false;-0.015748033 -0.041424174 -0.31530297 false false 0.0 0.0 false false;-0.015748033 -0.058541592 -0.35809657 false false 0.0 0.0 false false;-0.015748033 -0.06710032 -0.3923314 false false 0.0 0.0 false false;0.0 -0.09277644 -0.3923314 false false 0.0 0.0 false false;0.0 -0.10989387 -0.3923314 false false 0.0 0.0 false false;0.0 -0.10989387 -0.3923314 false false 0.0 0.0 false false;-0.007189324 -0.10989387 -0.3923314 false false 0.0 0.0 false false;-0.007189324 -0.118452586 -0.3923314 false false 0.0 0.0 false false;-0.007189324 -0.118452586 -0.3923314 false false 0.0 0.0 false false;-0.007189324 -0.1270113 -0.3923314 false false 0.0 0.0 false false;-0.007189324 -0.1270113 -0.3923314 false false 0.0 0.0 false false;-0.007189324 -0.1270113 -0.36665526 false false 0.0 0.0 false false;-0.007189324 -0.15268743 -0.29818556 false false 0.0 0.0 false false;0.0 -0.15268743 -0.17836358 false false 0.0 0.0 false false;0.0 -0.007189324 0.0 false false 0.0 0.0 false false;0.0 -0.007189324 0.0 false false 0.0 0.0 false false;0.0 -0.007189324 0.0 false false 0.0 0.0 false false;0.0 -0.007189324 0.0 false true 0.0 0.0 false false;0.0 -0.007189324 0.0 false true 0.0 0.0 false false;0.0 -0.007189324 0.0 false false 0.0 0.0 false false;0.0 -0.007189324 0.0 false false 0.0 0.0 false false;0.0 -0.007189324 0.0 false false 0.0 0.0 false false;0.0 -0.007189324 0.0 false false 0.0 0.0 false false;0.0 -0.007189324 0.0 false false 0.0 0.0 false false;0.0 -0.007189324 0.0 false false 0.0 0.0 false false;0.0 -0.007189324 0.0 false false 0.0 0.0 false false;0.0 -0.007189324 0.0 false false 0.0 0.0 false false;0.0 -0.007189324 0.0 false false 0.0 0.0 false false;0.0 -0.007189324 0.0 false false 0.0 0.0 false false;0.0 -0.007189324 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.06710032 false false 0.0 0.0 false false;0.0 0.0 0.06710032 false false 0.0 0.0 false false;0.0 0.0 0.09277644 false false 0.0 0.0 false false;0.0 0.20403971 0.35809657 false false 0.0 0.0 false false;0.0 0.3324204 0.45224237 false false 0.0 0.0 false false;0.0 0.36665526 0.4779185 false false 0.0 0.0 false false;0.0 0.36665526 0.4608011 false false 0.0 0.0 false false;0.0 0.36665526 0.4608011 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;-0.041424174 0.0 0.0 false false 0.0 0.0 false false;-0.10989387 0.0 0.0 false false 0.0 0.0 false false;-0.10989387 0.0 0.0 false false 0.0 0.0 false false;-0.3409791 0.0 0.0 false false 0.0 0.0 false false;-0.34953782 0.0 0.0 false false 0.0 0.0 false false;-0.28106812 0.0 0.0 false false 0.0 0.0 false false;-0.38377267 0.0 0.0 false false 0.0 0.0 false false;-0.36665526 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;-0.007189324 0.0 -0.02430675 false false 0.0 0.0 false false;0.0 0.0 -0.118452586 false false 0.0 0.0 false false;0.0 0.0 -0.36665526 false false 0.0 0.0 false false;0.0 0.0 -0.36665526 false false 0.0 0.0 false false;0.0 0.0 -0.3409791 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false true 0.0 0.0 false false;0.0 0.0 0.0 false true 0.0 0.0 false false;0.0 0.0 0.0 false true 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 true false;0.0 0.0 0.0 false false 0.0 0.0 true false;0.0 0.0 0.0 false false 0.0 0.0 true false;0.0 0.0 0.0 false false 0.0 0.0 true false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.15268743 false false 0.0 0.0 false false;0.0 0.0 0.4008901 false false 0.0 0.0 false false;0.0 0.007189324 0.6833277 false false 0.0 0.0 false false;0.0 0.09277644 0.8202671 false false 0.0 0.0 false false;0.0 0.09277644 0.7945909 false false 0.0 0.0 false false;0.0 0.09277644 0.70044506 false false 0.0 0.0 false false;0.0 0.09277644 0.49503592 false false 0.0 0.0 false false;0.0 0.0 0.06710032 false false 0.0 0.0 false false;-0.31530297 0.0 0.0 false false 0.0 0.0 false false;-0.7261212 0.0 0.0 false false 0.0 0.0 false false;-0.78603214 0.0 0.0 false false 0.0 0.0 false false;-0.7346799 0.0 0.0 false false 0.0 0.0 false false;-0.5977405 0.0 0.0 false false 0.0 0.0 false false;-0.10989387 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 -0.041424174 false false 0.0 0.0 false false;0.0 0.0 -0.195481 false false 0.0 0.0 false false;0.0 -0.02430675 -0.255392 false false 0.0 0.0 false false;0.0 -0.18692228 -0.41800752 false false 0.0 0.0 false false;0.0 -0.23827457 -0.50359464 false false 0.0 0.0 false false;0.0 -0.24683328 -0.50359464 false false 0.0 0.0 false false;0.0 -0.24683328 -0.5121533 false false 0.0 0.0 false false;0.0 -0.24683328 -0.5121533 false false 0.0 0.0 false false;0.0 -0.24683328 -0.54638827 false false 0.0 0.0 false false;0.0 -0.24683328 -0.5806231 false false 0.0 0.0 false false;0.0 -0.24683328 -0.5806231 false false 0.0 0.0 false false;0.0 -0.24683328 -0.5806231 false false 0.0 0.0 false false;0.0 -0.21259841 -0.48647723 false false 0.0 0.0 false false;0.0 -0.13557002 -0.45224237 false false 0.0 0.0 false false;0.0 0.0 -0.45224237 false false 0.0 0.0 false false;0.0 0.0 -0.44368368 false false 0.0 0.0 false false;0.0 0.0 -0.44368368 false false 0.0 0.0 false false;0.0 0.0 -0.44368368 false false 0.0 0.0 false false;0.0 0.09277644 -0.44368368 false false 0.0 0.0 false false;0.0 0.17836358 -0.44368368 false false 0.0 0.0 false false;0.0 0.18692228 -0.44368368 false false 0.0 0.0 false false;0.0 0.17836358 -0.44368368 false false 0.0 0.0 false false;0.0 0.18692228 -0.43512496 false false 0.0 0.0 false false;0.0 0.18692228 -0.43512496 false false 0.0 0.0 false false;0.0 0.18692228 -0.43512496 false false 0.0 0.0 false false;0.0 0.195481 -0.44368368 false false 0.0 0.0 false false;0.0 0.20403971 -0.44368368 false false 0.0 0.0 false false;0.0 0.20403971 -0.44368368 false false 0.0 0.0 false false;0.0 0.20403971 -0.44368368 false false 0.0 0.0 false false;-0.084217735 0.20403971 -0.4094488 false false 0.0 0.0 false false;-0.23827457 0.13557002 -0.3409791 false false 0.0 0.0 false false;-0.28106812 0.1270113 -0.3324204 false false 0.0 0.0 false false;-0.6234166 0.0 0.0 false false 0.0 0.0 false false;-0.70044506 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.3254902 0.0 false false;0.0 0.0 0.0 false false 0.7529412 0.0 false false;0.0 0.0 0.0 false false 0.90588236 0.0 false false;0.0 0.0 0.0 false false 0.92156863 0.0 false false;0.0 0.0 0.0 false false 0.92156863 0.0 false false;0.0 0.0 0.0 false false 0.9254902 0.0 false false;0.0 0.0 0.0 false false 0.9254902 0.0 false false;0.0 0.0 0.0 false false 0.92941177 0.0 false false;0.0 0.0 0.0 false false 0.92941177 0.0 false false;0.0 0.0 0.0 false false 0.92941177 0.0 false false;0.0 0.0 0.0 false false 0.92941177 0.0 false false;0.0 0.0 0.0 false false 0.92941177 0.0 false false;0.0 0.0 0.0 false false 0.92941177 0.0 false false;0.0 0.0 0.0 false false 0.93333334 0.0 false false;0.0 0.0 0.0 false false 0.93333334 0.0 false false;0.0 0.0 0.0 false false 0.93333334 0.0 false false;0.0 0.0 0.0 false false 0.93333334 0.0 false false;0.0 0.0 0.0 false false 0.92941177 0.0 false false;0.0 0.0 0.0 false false 0.9019608 0.0 false false;0.0 0.0 0.0 false false 0.75686276 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 -0.015748033 false false 0.0 0.0 false false;0.0 0.0 -0.02430675 false false 0.0 0.0 false false;0.0 -0.041424174 -0.17836358 false false 0.0 0.0 false false;0.0 -0.084217735 -0.24683328 false false 0.0 0.0 false false;0.0 -0.10133517 -0.2639507 false false 0.0 0.0 false false;0.0 -0.10989387 -0.2725094 false false 0.0 0.0 false false;0.0 -0.10989387 -0.2725094 false false 0.0 0.0 false false;0.0 -0.118452586 -0.28962687 false false 0.0 0.0 false false;0.0 -0.13557002 -0.28106812 false false 0.0 0.0 false false;0.0 -0.1270113 -0.2639507 false false 0.0 0.0 false false;0.0 -0.1270113 -0.15268743 false false 0.0 0.0 false false;0.0 -0.007189324 0.0 false false 0.0 0.0 false false;0.0 -0.007189324 0.0 false false 0.0 0.0 false false;0.0 -0.007189324 0.0 false false 0.0 0.0 false false;0.0 -0.007189324 0.0 false false 0.19607843 0.0 false false;0.0 -0.007189324 0.0 false false 0.6431373 0.0 false false;0.0 -0.007189324 0.0 false false 0.85490197 0.0 false false;0.0 -0.007189324 0.0 false false 0.9372549 0.0 false false;0.0 -0.007189324 0.0 false false 0.99607843 0.0 false false;0.0 -0.007189324 0.0 false false 0.95686275 0.0 false false;0.0 -0.007189324 0.0 false false 0.85882354 0.0 false false;0.0 -0.007189324 0.0 false false 0.84313726 0.0 false false;0.0 -0.007189324 0.0 false false 0.0 0.0 false false;0.0 -0.007189324 0.0 false false 0.0 0.0 false false;0.0 -0.007189324 0.0 false false 0.0 0.0 false false;0.0 -0.007189324 0.0 false false 0.0 0.0 false false;0.0 -0.007189324 -0.015748033 false false 0.0 0.0 false false;0.0 -0.007189324 -0.10133517 false false 0.0 0.0 false false;0.0 -0.06710032 -0.17836358 false false 0.0 0.0 false false;0.0 -0.09277644 -0.21259841 false false 0.0 0.0 false false;0.0 -0.15268743 -0.22971587 false false 0.0 0.0 false false;0.0 -0.16124615 -0.24683328 false false 0.0 0.0 false false;0.0 -0.16124615 -0.2639507 false false 0.0 0.0 false false;0.0 -0.16124615 -0.2639507 false false 0.0 0.0 false false;0.0 -0.16124615 -0.2639507 false false 0.0 0.0 false false;0.0 -0.16124615 -0.2639507 false false 0.0 0.0 false false;0.0 -0.16124615 -0.2639507 false false 0.0 0.0 false false;0.0 -0.16124615 -0.2639507 false false 0.0 0.0 false false;0.0 -0.16124615 -0.2639507 false false 0.0 0.0 false false;0.0 -0.007189324 0.0 false false 0.0 0.0 false false;0.0 -0.007189324 0.0 false false 0.0 0.0 false false;0.0 -0.007189324 0.0 false false 0.0 0.0 false false;0.0 -0.007189324 0.0 false false 0.0 0.0 false false;0.0 -0.007189324 0.0 false false 0.0 0.0 false false;0.0 -0.007189324 0.0 false false 0.0 0.0 false false;0.0 -0.007189324 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.18692228 -0.118452586 false false 0.0 0.0 false false;0.0 0.2725094 -0.195481 false false 0.0 0.0 false false;0.0 0.2725094 -0.195481 false false 0.0 0.0 false false;0.0 0.3238617 -0.195481 false false 0.0 0.0 false false;0.0 0.3324204 -0.195481 false false 0.0 0.0 false false;0.0 0.015748033 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 -0.032865457 false false 0.0 0.0 false false;0.0 0.23827457 -0.18692228 false false 0.0 0.0 false false;0.0 0.3324204 -0.22971587 false false 0.0 0.0 false false;0.0 0.3324204 -0.13557002 false false 0.0 0.0 false false;0.0 0.06710032 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.16124615 0.0 0.0 false false 0.0 0.0 false false;0.24683328 0.0 0.0 false false 0.0 0.0 false false;0.24683328 0.0 0.0 false false 0.0 0.0 false false;0.24683328 0.0 0.0 false false 0.0 0.0 false false;0.24683328 0.0 0.0 false false 0.0 0.0 false false;0.22115715 0.0 0.0 false false 0.0 0.0 false false;0.22115715 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.24683328 0.0 0.0 false false 0.0 0.0 false false;0.30674428 0.0 0.0 false false 0.0 0.0 false false;0.30674428 0.0 0.0 false false 0.0 0.0 false false;0.30674428 0.0 0.0 false false 0.0 0.0 false false;0.30674428 0.0 0.0 false false 0.0 0.0 false false;0.17836358 0.0 0.0 false false 0.0 0.0 false false;0.15268743 0.0 0.0 false false 0.0 0.0 false false;0.16124615 0.0 0.0 false false 0.0 0.0 false false;0.118452586 0.0 0.0 false false 0.0 0.0 false false;0.10133517 0.0 0.0 false false 0.0 0.0 false false;0.10133517 0.0 0.0 false false 0.0 0.0 false false;0.10133517 0.0 0.0 false false 0.0 0.0 false false;0.14412872 0.0 0.0 false false 0.0 0.0 false false;0.35809657 0.0 0.0 false false 0.0 0.0 false false;0.5121533 0.0 0.0 false false 0.0 0.0 false false;0.5720644 0.0 0.0 false false 0.0 0.0 false false;0.5720644 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 true false 0.0 0.0 false false;0.0 0.0 0.0 true false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.18692228 false false 0.0 0.0 false false;0.0 0.0 0.23827457 false false 0.0 0.0 false false;0.0 0.07565903 0.35809657 false false 0.0 0.0 false false;0.0 0.18692228 0.5121533 false false 0.0 0.0 false false;0.0 0.20403971 0.6062992 false false 0.0 0.0 false false;0.0 0.22115715 0.63197535 false false 0.0 0.0 false false;0.0 0.22115715 0.63197535 false false 0.0 0.0 false false;0.0 0.22115715 0.6234166 false false 0.0 0.0 false false;0.0 0.22971587 0.6062992 false false 0.0 0.0 false false;0.0 0.22115715 0.50359464 false false 0.0 0.0 false false;0.0 0.22115715 0.46935982 false false 0.0 0.0 false false;0.0 0.21259841 0.4008901 false false 0.0 0.0 false false;0.0 0.058541592 0.13557002 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.07450981 false false;0.0 0.0 0.0 false false 0.0 0.12156863 false false;0.0 0.0 0.0 false false 0.0 0.29803923 false false;0.0 0.0 0.0 false false 0.0 0.49411765 false false;0.0 0.0 0.0 false false 0.0 0.70980394 false false;0.0 0.0 0.0 false false 0.0 1.0 false false;0.0 0.0 0.0 false false 0.0 1.0 false false;0.0 0.0 0.0 false false 0.0 1.0 false false;0.0 0.0 0.0 false false 0.0 1.0 false false;0.0 0.0 0.0 false false 0.0 1.0 false false;0.0 0.0 0.0 false false 0.0 1.0 false false;0.0 0.0 0.0 false false 0.0 1.0 false false;0.0 0.0 0.0 false false 0.0 1.0 false false;0.0 0.0 0.0 false false 0.0 1.0 false false;0.0 0.0 0.0 false false 0.0 1.0 false false;0.0 0.0 0.0 false false 0.0 1.0 false false;0.0 0.0 0.0 false false 0.0 1.0 false false;0.0 0.0 0.0 false false 0.0 1.0 false false;0.0 0.0 0.0 false false 0.0 1.0 false false;0.0 0.0 0.0 false false 0.0 1.0 false false;0.0 0.0 0.0 false false 0.0 1.0 false false;0.0 0.0 0.0 false false 0.0 1.0 false false;0.0 0.0 0.0 false false 0.0 1.0 false false;0.0 0.0 0.0 false false 0.0 1.0 false false;0.0 0.0 0.0 false false 0.0 1.0 false false;0.0 0.0 0.0 false false 0.0 1.0 false false;0.0 0.0 0.0 false false 0.0 1.0 false false;0.0 0.0 0.0 false false 0.0 1.0 false false;0.0 0.0 0.0 false false 0.0 0.74509805 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 -0.058541592 0.0 false false 0.0 0.0 false false;0.0 -0.6148579 0.10989387 false false 0.0 0.0 false false;0.0 -0.8202671 0.14412872 false false 0.0 0.0 false false;0.0 -0.9572064 0.16124615 false false 0.0 0.0 false false;0.0 -1.0 0.16124615 false false 0.0 0.0 false false;0.0 -1.0 0.16124615 false false 0.0 0.0 false false;0.0 -1.0 0.15268743 false false 0.0 0.0 false false;0.0 -1.0 0.15268743 false false 0.0 0.0 false false;0.0 -0.8202671 0.07565903 false false 0.0 0.0 false false;-0.195481 0.0 0.0 false false 0.0 0.0 false false;-0.3923314 0.0 0.0 false false 0.0 0.0 false false;-0.41800752 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 -0.007189324 false false 0.0 0.0 false false;0.0 0.0 -0.058541592 false false 0.0 0.0 false false;0.0 -0.007189324 -0.10989387 false false 0.0 0.0 false false;0.0 -0.049982883 -0.22971587 false false 0.0 0.0 false false;0.0 -0.058541592 -0.255392 false false 0.0 0.0 false false;0.0 -0.058541592 -0.255392 false false 0.0 0.0 false false;0.0 -0.058541592 -0.255392 false false 0.0 0.0 false false;0.0 -0.058541592 -0.255392 false false 0.0 0.0 false false;0.0 -0.058541592 -0.255392 false false 0.0 0.0 false false;0.0 -0.007189324 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 -0.007189324 false false 0.0 0.0 false false;0.0 0.0 -0.032865457 false false 0.0 0.0 false false;0.0 0.0 -0.032865457 false false 0.0 0.0 false false;0.0 0.0 -0.032865457 false false 0.0 0.0 false false;0.0 0.0 -0.032865457 false false 0.0 0.0 false false;0.0 0.0 -0.032865457 false false 0.0 0.0 false false;0.0 0.0 -0.10133517 false false 0.0 0.0 false false;0.0 0.0 -0.10133517 false false 0.0 0.0 false false;0.0 0.0 -0.16980487 false false 0.0 0.0 false false;0.0 0.0 -0.18692228 false false 0.0 0.0 false false;0.0 -0.032865457 -0.4094488 false false 0.0 0.0 false false;0.0 -0.058541592 -0.49503592 false false 0.0 0.0 false false;0.0 -0.058541592 -0.49503592 false false 0.0 0.0 false false;0.0 -0.058541592 -0.49503592 false false 0.0 0.0 false false;0.0 -0.058541592 -0.49503592 false false 0.0 0.0 false false;0.0 -0.058541592 -0.49503592 false false 0.0 0.0 false false;0.0 -0.058541592 -0.49503592 false false 0.0 0.0 false false;0.0 -0.058541592 -0.49503592 false false 0.0 0.0 false false;0.0 -0.058541592 -0.49503592 false false 0.0 0.0 false false;0.0 -0.058541592 -0.49503592 false false 0.0 0.0 false false;0.0 -0.06710032 -0.49503592 false false 0.0 0.0 false false;0.0 -0.06710032 -0.49503592 false false 0.0 0.0 false false;0.0 -0.06710032 -0.49503592 false false 0.0 0.0 false false;0.0 -0.06710032 -0.4779185 false false 0.0 0.0 false false;0.0 -0.06710032 -0.4779185 false false 0.0 0.0 false false;0.0 -0.06710032 -0.46935982 false false 0.0 0.0 false false;0.0 -0.06710032 -0.4608011 false false 0.0 0.0 false false;0.0 -0.032865457 -0.049982883 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;0.0 0.0 0.0 false false 0.0 0.0 false false;");
        waitForStart();

        father.startMoving();
    }


    void declare_variables()  {
        RightDrive_fr = hardwareMap.get(DcMotor.class, "RightDrive_fr");
        LeftDrive_fr = hardwareMap.get(DcMotor.class, "LeftDrive_fr");
        RightDrive_ass = hardwareMap.get(DcMotor.class, "RightDrive_ass");
        LeftDrive_ass= hardwareMap.get(DcMotor.class, "LeftDrive_ass");
        lift_right = hardwareMap.get(DcMotor.class, "lift_right");
        lift_left = hardwareMap.get(DcMotor.class, "lift_left");
        serv_hang_himself=hardwareMap.get(Servo.class, "serv_hang_himself");
        servo_up=hardwareMap.get(Servo.class, "servo_up");
        serv_right = hardwareMap.get(Servo.class, "serv_right");
        serv_left = hardwareMap.get(Servo.class, "serv_left");
        imu = hardwareMap.get(IMU.class, "imu");
        WebcamName camera = hardwareMap.get(WebcamName.class, "camera");
        telemetry.setMsTransmissionInterval(50);
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
        ));
        imu.initialize(parameters);

        serv_hang_himself.setPosition(1);
        serv_right.setPosition(0.485);
        serv_left.setPosition(0.1);
        servo_up.setPosition(0.44);


        for (DcMotor motor : new DcMotor[]{RightDrive_fr, RightDrive_ass, LeftDrive_ass, LeftDrive_fr, lift_right, lift_left}) {
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

    }
}