package com.example.lab1_maryasin.relays;

//Параллельный
public class PIDregulator {
    private double P = 10, D = 0.2, I = 1;
    private double setpoint = 0, eastActual = 0, maxOutput = 0, minOutput = 0, errorSum = 0, maxError = 0, lastOutput = 0, maxOutputRampRate = 0;
    public PIDregulator(double p, double i, double d){
        P = p;
        D = d;
        I = i;
    }
    public void SetD(double d){
        D = d;
    }
    public void SetP(double p){
        P = p;
    }
    public void SetI(double i){
        I = i;
    }
    public void SetSetPoint(double setpoint){
        this.setpoint = setpoint;
    }
    public void setOutputLimits(double minimum, double maximum){
        if (maximum < minimum) return;
        maxOutput = maximum;
        minOutput = minimum;
    }
    public void setMaxOutputRampRate(double rate){
        maxOutputRampRate = rate;
    }
    public double getOutput(double actual, double setpoint){
        double output, Poutput, Doutput, Ioutput;
        double error = setpoint - actual;
        Poutput = error * P;
        if (Bounded(lastOutput, minOutput, maxOutput)){
            errorSum += error;
            maxError = errorSum;
        }
        else{
            errorSum = maxError;
        }
        Ioutput = I * errorSum;
        Doutput = D * (actual - eastActual);
        eastActual = actual;
        output = P * (Poutput + Doutput + Ioutput);
        output = constrain(output, minOutput, maxOutput);
        if (maxOutputRampRate != 0){
            output = constrain(output - lastOutput, eastActual - maxOutputRampRate, eastActual + maxOutputRampRate);
        }
        lastOutput = output;
        return output;
    }
    private double constrain(double value, double min, double max){
        if (value > max) return max;
        if (value < min) return min;
        return value;
    }
    private boolean Bounded(double value, double min, double max){
        return (min < value) && (max > value);
    }
}