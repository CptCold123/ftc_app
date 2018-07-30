package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "JiaT")
public class JiaTele extends LinearOpMode
{
    //declrae drive motors
    private DcMotor lMotor;
    private DcMotor rMotor;


    @Override
    public  void runOpMode()  throws InterruptedException
    {

       //init Drive
       lMotor = hardwareMap.dcMotor.get("Left Motor");
       rMotor = hardwareMap.dcMotor.get("Right Motor");


        //waits for the button to be pressed
        waitForStart();


        //run this code while in the main OpMode
        while (opModeIsActive())
        {
            //Tank drive
            lMotor.setPower(-gamepad1.left_stick_y);
            rMotor.setPower(gamepad1.right_stick_y)




            //give hardware a chance to catch up
            // idle();
        }









    }
}