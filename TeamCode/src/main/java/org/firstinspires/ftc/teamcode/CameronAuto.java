package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.motors.RevRoboticsCoreHexMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "CameronT")

public class CameronTele extends LinearOpMode {
    //declare drive motors
    private DcMotor LMotor;
    private DcMotor RMotor;

    //declare servos
    private Servo clawServo;

    //servo constants
    private static final double grab = 0.4;
    private static final double release = 0.9;

    //variable
    private boolean bob;

    //bob default
    bob = false


    /*--------------------------------------------------------------------------------------------*/
    @Override

    public void runOpMode() throws InterruptedException{
        //init drive
        LMotor = hardwareMap.dcMotor.get("LeftMotor");
        RMotor = hardwareMap.dcMotor.get("RightMotor");

        //invert one drive motor so that the robot goes forward
        LMotor.setDirection(DcMotor.Direction.REVERSE);

        //servo
        clawServo = hardwareMap.servo.get("ClawServo");

        //default pos
        clawServo.setPosition(0.9);

        // waits for the start button to be pressed
        waitForStart();


        //run this code while in the man OpMode
        while(opModeIsActive()){
            //tank drive

            LMotor.setPower(-gamepad1.right_stick_y);
            RMotor.setPower(-gamepad1.right_stick_y);
            LMotor.setPower(gamepad1.right_stick_x);
            RMotor.setPower(-gamepad1.right_stick_x);

            if (gamepad1.y){
                clawServo.setPosition(grab);
            }
            if (!gamepad1.y){
                clawServo.setPosition(release);
            }
            if (gamepad1.a & gamepad1.left_bumper & gamepad1.right_bumper){
                bob = true;


            }
           //give hardware a chance to catch up//
            idle();
        }
    }
}
