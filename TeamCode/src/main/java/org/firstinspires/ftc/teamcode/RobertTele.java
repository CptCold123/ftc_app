package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "RobertT")
public class RobertTele extends LinearOpMode
{
    //declare drive motors
    private DcMotor LMotor;
    private DcMotor RMotor;

    @Override
    public void runOpMode() throws InterruptedException
    {
        //Initialize Motor
        LMotor = hardwareMap.dcMotor.get("LeftMotor");
        RMotor = hardwareMap.dcMotor.get("RightMotor");

        //waits for the start button to be pressed
        waitForStart();

        //run this code while in main OpMode
        while (opModeIsActive())
        {
            //tank drive
            LMotor.setPower(-gamepad1.left_stick_y);
            RMotor.setPower(-gamepad1.right_stick_y);

            // give the hardware a chance to catch up
            idle();
        }
    }
}
