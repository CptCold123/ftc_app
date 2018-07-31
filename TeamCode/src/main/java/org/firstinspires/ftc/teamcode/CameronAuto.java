package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "CameronA")

public class CameronAuto extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    //motor encoder stuffs
    static final double COUNTS_PER_MOTOR_REV    = 1440 ;    // eg: TETRIX Motor Encoder
    static final double DRIVE_GEAR_REDUCTION    = 2.0 ;     // This is < 1.0 if geared UP
    static final double WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double DRIVE_SPEED             = 0.6;
    static final double TURN_SPEED              = 0.5;

    //declare drive motors
    private DcMotor LMotor;
    private DcMotor RMotor;

    //declare servos
    private Servo clawServo;
    private Servo block;

    //servo constants
    private static final double grab = 0.4;
    private static final double release = 0.9;

    //variable
    private boolean bob;

    //bob default
    bob = false;


    /*--------------------------------------------------------------------------------------------*/
    @Override

    public void runOpMode() {
        telemetry.addData("Status", "Resetting Encoders");    //
        telemetry.update();

        //init drive
        LMotor = hardwareMap.dcMotor.get("LeftMotor");
        RMotor = hardwareMap.dcMotor.get("RightMotor");

        LMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        LMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //invert one drive motor so that the robot goes forward
        LMotor.setDirection(DcMotor.Direction.REVERSE);

        //servo
        clawServo = hardwareMap.servo.get("ClawServo");

        //default pos
        clawServo.setPosition(0.9);

        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Path0", "Starting at %7d :%7d",
                LMotor.getCurrentPosition(), 
                RMotor.getCurrentPosition());
        telemetry.update();

        // waits for the start button to be pressed
        waitForStart();


        // Step through each leg of the path,
        // Note: Reverse movement is obtained by setting a negative distance (not speed)
        //this is the robots actual driving code. Edit this to change how it drives auto
        encoderDrive(DRIVE_SPEED, 48, 48, 5.0);  // S1: Forward 47 Inches with 5 Sec timeout
        encoderDrive(TURN_SPEED, 12, -12, 4.0);  // S2: Turn Right 12 Inches with 4 Sec timeout
        encoderDrive(DRIVE_SPEED, -24, -24, 4.0);  // S3: Reverse 24 Inches with 4 Sec timeout

        telemetry.addData("Path", "Complete");
        telemetry.update();
    }
    /*
     *  Method to perfmorm a relative move, based on encoder counts.
     *  Encoders are not reset as the move is based on the current position.
     *  Move will stop if any of three conditions occur:
     *  1) Move gets to the desired position
     *  2) Move runs out of time
     *  3) Driver stops the opmode running.
     */
    public void encoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = LMotor.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget = RMotor.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            LMotor.setTargetPosition(newLeftTarget);
            RMotor.setTargetPosition(newRightTarget);

            // Turn On RUN_TO_POSITION
            LMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            LMotor.setPower(Math.abs(speed));
            RMotor.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (LMotor.isBusy() && RMotor.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        LMotor.getCurrentPosition(),
                        RMotor.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            LMotor.setPower(0);
            RMotor.setPower(0);

            // Turn off RUN_TO_POSITION
            LMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            RMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(250);   // optional pause after each move
        }
    }
}
