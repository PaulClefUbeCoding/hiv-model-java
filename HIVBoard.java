import java.util.*;
import java.io.*;
import java.awt.*;
import java.lang.Math;
import javax.swing.JPanel;

class Person {
    private boolean infected; //If true, the person is infected
    private boolean known;  //If true, the person is infected (infected must be true) and knows he is infected 
    private int infectionLength; //How long the person has been infected (weeks)
    private boolean couple; //If true, the person is in a sexually active couple (weeks)
    private int coupleLength;   //How long the person has been in a couple (weeks)
    private int commitment; //How long the person will stay in a couple-relationship (weeks)
    private double couplingTendency;  //How likely the person is to join in a couple (percent)
    private double condomUse;  //The chance a person uses protection (percent)
    private int testFrequency; //Number of times a person will get tested per year (single digit)
    private Person partner; //The person that is the partner in a couple

     /*Constructor that initializes Person's properties*/
     public Person(boolean infected, boolean known, int infectionLength, boolean couple, int coupleLength, 
     int commitment, double couplingTendency, double condomUse, int testFrequency, Person partner) {
         this.infected = infected;
         this.known = known;
         this.infectionLength = infectionLength;
         this.couple = couple;
         this.coupleLength = coupleLength;
         this.commitment = commitment;
         this.couplingTendency = couplingTendency;
         this.condomUse = condomUse;
         this.testFrequency = testFrequency;
         this.partner = partner;
     }
    
     /*Getter and setter for the Person variables*/
    public boolean getInfected() {
        return this.infected;
    }

    public void setInfected(boolean infected) {
        this.infected = infected;
    }

    public boolean getKnown() {
        return this.known;
    }

    public void setKnown(boolean known) {
        this.known = known;
    }

    public int getInfectionLength() {
        return this.infectionLength;
    }

    public void setInfectionLength(int infectionLength) {
        this.infectionLength = infectionLength;
    }

    public boolean getCouple() {
        return this.couple;
    }

    public void setCouple(boolean couple) {
        this.couple = couple;
    }

    public int getCoupleLength() {
        return this.coupleLength;
    }

    public void setCoupleLength(int coupleLength) {
        this.coupleLength = coupleLength;
    }

    public int getCommitment() {
        return this.commitment;
    }

    public void setCommitment(int commitment) {
        this.commitment = commitment;
    }

    public double getCouplingTendency() {
        return this.couplingTendency;
    }

    public void setCouplingTendency(double couplingTendency) {
        this.couplingTendency = couplingTendency;
    }

    public double getCondomUse() {
        return this.condomUse;
    }

    public void setCondomUse(double condomUse) {
        this.condomUse = condomUse;
    }

    public int getTestFrequency() {
        return this.testFrequency;
    }

    public void setTestFrequency(int testFrequency) {
        this.testFrequency = testFrequency;
    }

    public Person getPartner() {
        return this.partner;
    }

    public void setPartner(Person partner) {
        this.partner = partner;
    }

   




}

public class HIVBoard {

}