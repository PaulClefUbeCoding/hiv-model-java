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




}

public class HIVBoard {

}