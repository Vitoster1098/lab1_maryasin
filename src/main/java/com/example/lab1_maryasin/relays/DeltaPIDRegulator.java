package com.example.lab1_maryasin.relays;

//Рекурсивный
public class DeltaPIDRegulator {
    private double P=0,I=0, D=0,Dt=0, maxOutput=3,minOutput=-3, errorSum=0,lastActual=0;
    private double setpoint=0;
    private double maxError=0;
    private double last0=0;
    private double last2Error=0;
    private double lastError=0;
    private double lastOutput=0;
    private double maxOutputRampRate=0;
    private boolean firstRun=true;

    public DeltaPIDRegulator (double p, double i, double d, double dt){
        P=p;
        I=i;
        D=d;
        Dt=dt;
    }

    public void setP (double p){
        P=p;
    }

    public void setI (double i){
        I=i;
    }

    public void setD (double d){
        D=d;
    }

    public void setdt (double dt){
        Dt=dt;
    }

    public void setSetpoint(double setpoint){
        this.setpoint=setpoint;
    }

    public void setOutputLimits(double minimum, double maximum){
        if(maximum<minimum)return;
        maxOutput=maximum;
        minOutput=minimum;
    }

    public double constrain (double value,double min, double max){
        if(value>max)return max;
        if(value<min)return min;
        return value;
    }

    private boolean bounded(double value, double min, double max){
        return (min < value) && (value < max);
    }

    public double getOutput (double actual, double setpoint){
        double output, q0,q1,q2;
        double error=setpoint-actual;
        q0=P+I*Dt+D/Dt;
        q1=-P-2*D/Dt;
        q2=D/Dt;
        output=q0*error+q1*lastError*q2*last2Error;
        output=constrain(output, minOutput, maxOutput);
        if (maxOutputRampRate!=0){
            output=constrain (output,lastOutput-maxOutputRampRate, lastOutput+maxOutputRampRate);
        }
        last2Error=lastError;
        lastError=error;
        lastOutput=output;
        return output;
    }

    public void setMaxOutputRampRate(double rate){
        maxOutputRampRate=rate;
    }
}