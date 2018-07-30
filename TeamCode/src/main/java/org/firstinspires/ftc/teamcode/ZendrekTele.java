package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "ZenT")
public class ZendrekTele extends LinearOpMode {
    //Declare drive motors
    private DcMotor lMotor;
    private DcMotor rMotor;

    @Override
    public void runOpMode () throws InterruptedException {
        //Initialize Drive
        lMotor = hardwareMap.dcMotor.get("LeftMotor");
        rMotor = hardwareMap.dcMotor.get("RightMotor");
        //Waits for the start button to be pressed
        waitForStart();

        //Run this code while in the main op mode
        while (opModeIsActive()){
            //Tank Drive
            lMotor.setPower(-gamepad1.left_stick_y);
            rMotor.setPower(-gamepad1.right_stick_y);

            //Give hardware a chance to catch up
            idle();
        }
    }
}
