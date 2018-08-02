package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.Range;


@TeleOp(name="ZenTele")
//@Disabled
public class DriverControl extends LinearOpMode {
    private DcMotor lMotor, rMotor, pulleyMotor, extenderMotor;
    Servo grab1, grab2;
    CRServo slider1, slider2;
    float lPower, rPower, xValue, yValue;
    double grab1Pos, grab2Pos, slider1power, slider2power;

    //called when init button is  pressed.
    @Override
    public void runOpMode() throws InterruptedException {
        lMotor = hardwareMap.dcMotor.get("LeftMotor");
        rMotor = hardwareMap.dcMotor.get("RightMotor");
        pulleyMotor = hardwareMap.dcMotor.get("PulleyMotor");
        extenderMotor = hardwareMap.dcMotor.get("ExtenderMotor");
        rMotor.setDirection(DcMotor.Direction.REVERSE);

        grab1 = hardwareMap.servo.get("Grab1");
        grab2 = hardwareMap.servo.get("Grab2");
        slider1 = hardwareMap.crservo.get("Slider1");
        slider2 = hardwareMap.crservo.get("Slider2");
        grab2.setDirection(Servo.Direction.REVERSE);
        slider2.setDirection(CRServo.Direction.REVERSE);

        telemetry.addData("Mode", "waiting");
        telemetry.update();

        waitForStart();

        grab1Pos = 0.5;
        grab2Pos = 0.5;

        while (opModeIsActive()) {
            yValue = gamepad1.right_stick_y;
            xValue = gamepad1.right_stick_x;

            lPower = yValue - xValue;
            rPower = yValue + xValue;

            lMotor.setPower(Range.clip(lPower, -1.0, 1.0));
            rMotor.setPower(Range.clip(rPower, -1.0, 1.0));

            // open the grabber on X button if not already at most open position.
            if (gamepad1.x && grab1Pos < Servo.MAX_POSITION) grab1Pos = grab1Pos + .01;
            if (gamepad1.x && grab2Pos < Servo.MAX_POSITION) grab2Pos = grab2Pos + .01;

            // close the grabber on Y button if not already at the closed position.
            if (gamepad1.y && grab1Pos > Servo.MIN_POSITION) grab1Pos = grab1Pos - .01;
            if (gamepad1.y && grab2Pos > Servo.MIN_POSITION) grab2Pos = grab2Pos - .01;

            // Set continuous servo power level and direction.
            if (gamepad1.a) {
                slider1power = .20;
                slider2power = .20;
            }else if (gamepad1.b) {
                slider1power = -.20;
                slider2power = -.20;
            }else
                slider1power = 0.0;
                slider2power = 0.0;

            // set the servo position/power values as we have computed them.
            grab1.setPosition(Range.clip(grab1Pos, Servo.MIN_POSITION, Servo.MAX_POSITION));
            grab2.setPosition(Range.clip(grab2Pos, Servo.MIN_POSITION, Servo.MAX_POSITION));
            slider1.setPower(slider1power);
            slider2.setPower(slider2power);

            //Turn Pulley
            if (gamepad1.left_bumper){
                pulleyMotor.setPower(1);
            } if (gamepad1.right_bumper){
                pulleyMotor.setPower(-1);
            } else pulleyMotor.setPower(0);

            //Extend Arm
            if (gamepad1.dpad_up){
                extenderMotor.setPower(1);
            }if (gamepad1.dpad_down){
                extenderMotor.setPower(-1);
            }else extenderMotor.setPower(0);

            telemetry.addData("Mode", "running");
            telemetry.addData("stick", "  y=" + yValue + "  x=" + xValue);
            telemetry.addData("power", "  left=" + lPower + "  right=" + rPower);
            telemetry.addData("Grab1Servo", "position=" + grab1Pos + "  actual=" + grab1.getPosition());
            telemetry.addData("Grab2Servo", "position=" + grab2Pos + "    actual=" + grab2.getPosition());
            telemetry.update();
            idle();
        }
    }
}
////////////////////////////////////////////////////////////////////////////////////////////////////