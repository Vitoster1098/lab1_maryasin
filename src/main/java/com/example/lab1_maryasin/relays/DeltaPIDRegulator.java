package com.example.lab1_maryasin.relays;

//Рекурсивный
public class DeltaPIDRegulator {
    double p;
    double ti;
    double td;
    double dt;

    double minOutput;
    double maxOutput;
    double maxOutputRampRate;

    public DeltaPIDRegulator(double p, double ti, double td, double dt){
        this.p = p;
        this.ti = ti;
        this.td = td;
        this.dt = dt;
    }

    public void setOutputLimits(double minOutput, double maxOutput){
        this.minOutput = minOutput;
        this.maxOutput = maxOutput;
    }

    public void setMaxOutputRampRate(double maxOutputRampRate){
        this.maxOutputRampRate = maxOutputRampRate;
    }

    public double getOutput(double y1, double r) {
        double output;
        double deadzone = 0.2;
        double error = r - y1;
        if (error >= deadzone){
            output = 2;
        }
        else if ((error >= -deadzone) && (error <= deadzone)) {
            output = 0;
        }
        else {
            output = -2;
        }
        return output;
    }
}