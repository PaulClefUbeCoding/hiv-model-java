import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.Math;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class Person {
    private boolean infected; // If true, the person is infected
    private boolean known; // If true, the person is infected (infected must be true) and knows he is
                           // infected
    private int infectionLength; // How long the person has been infected (weeks)
    private boolean couple; // If true, the person is in a sexually active couple (weeks)
    private int coupleLength; // How long the person has been in a couple (weeks)
    private int commitment; // How long the person will stay in a couple-relationship (weeks)
    private double couplingTendency; // How likely the person is to join in a couple (percent)
    private double condomUse; // The chance a person uses protection (percent by 10%s)
    private double testFrequency; // Number of times a person will get tested per year (single digit)
    private Person partner; // The person that is the partner in a couple
    private int biologicalSex; // 0 for male, 1 for female.

    /* Constructor that initializes Person's properties */
    public Person(boolean infected, boolean known, int infectionLength, boolean couple, int coupleLength,
            int commitment, int couplingTendency, int condomUse, int testFrequency, int biologicalSex) {
        this.infected = infected;
        this.known = known;
        this.infectionLength = infectionLength;
        this.couple = couple;
        this.coupleLength = coupleLength;
        this.commitment = commitment;
        this.couplingTendency = (double) couplingTendency * .1;
        this.condomUse = (double) condomUse * .1;
        this.testFrequency = testFrequency;
        this.biologicalSex = biologicalSex;
    }

    /* Getter and setter for the Person variables */
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

    public void setCondomUse(int condomUse) {
        this.condomUse = condomUse;
    }

    public double getTestFrequency() {
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

public class HIVBoard extends JPanel implements ActionListener {

    // Global variables
    double infectionChance; // Percent chance that an infected person will pass on infection duriong one
                            // week of couplehood
    int symptonsShow; // How long (in weeks) a person will be infected before symptoms occur which may
                      // cause the person to get tested
    int week; // the current week

    // Green = Not Infected, Blue = Infected (Not Known), Red = Infected and Knows
    int red;
    int green;
    int blue;

    ArrayList<Person> population; // Contains the initial amount of people

    JFrame frame = new JFrame("HIV Model in Java");

    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel panel3 = new JPanel();
    JPanel panel4 = new JPanel();
    JPanel panel5 = new JPanel();
    JTextArea results = new JTextArea(50, 50);

    Timer timer = new Timer();

    boolean stopped;
    boolean newSetup;

    public HIVBoard() {
        this.infectionChance = 0.5; // unprotected sex with an infected partner, you have a 50% chance of being
                                    // infected
        this.symptonsShow = 200; // symptoms show up 200 weeks after being infected
        this.week = 0;
        this.red = 0;
        this.green = 0;
        this.blue = 0;
        stopped = false;
        newSetup = true;

        setupBoard();
    }

    public void setupBoard() {

        this.frame.setSize(800, 800);
        this.frame.setLayout(new FlowLayout());

        this.panel1.setLayout(new GridLayout(5, 2));
        this.panel3.setLayout(new GridLayout(1, 3));
        // this.panel2.setLayout(new GridLayout(5, 0));

        JSlider initialPopulationSlider = new JSlider(JSlider.HORIZONTAL, 0, 500, 300);
        JSlider avgCouplingSlider = new JSlider(JSlider.HORIZONTAL, 0, 10, 5);
        JSlider avgCommitmentSlider = new JSlider(JSlider.HORIZONTAL, 0, 200, 50);
        JSlider avgCondomUseSilder = new JSlider(JSlider.HORIZONTAL, 0, 10, 0);
        JSlider avgTestFrequency = new JSlider(JSlider.HORIZONTAL, 0, 2, 0);

        JLabel label1 = new JLabel("Initial People: 300");
        JLabel label2 = new JLabel("Average Coupling Tendency: 5 ");
        JLabel label3 = new JLabel("Average Commitment: 50 ");
        JLabel label4 = new JLabel("Average Condom Use: 0");
        JLabel label5 = new JLabel("Average Test Frequency: 0");

        JButton buttonSetup = new JButton("Setup");
        JButton buttonStep = new JButton("Step Once");
        JButton buttonAuto = new JButton("Go Auto");

        // Adjust the major tick mark on sliders
        initialPopulationSlider.setMajorTickSpacing(100);
        avgCouplingSlider.setMajorTickSpacing(1);
        avgCommitmentSlider.setMajorTickSpacing(50);
        avgCondomUseSilder.setMajorTickSpacing(1);
        avgTestFrequency.setMajorTickSpacing(1);

        // Turn on tick marks and labels
        initialPopulationSlider.setPaintTicks(true);
        avgCouplingSlider.setPaintTicks(true);
        avgCommitmentSlider.setPaintTicks(true);
        avgCondomUseSilder.setPaintTicks(true);
        avgTestFrequency.setPaintTicks(true);

        initialPopulationSlider.setPaintLabels(true);
        avgCouplingSlider.setPaintLabels(true);
        avgCommitmentSlider.setPaintLabels(true);
        avgCondomUseSilder.setPaintLabels(true);
        avgTestFrequency.setPaintLabels(true);

        // Add change listeners to the sliders
        initialPopulationSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                label1.setText("Initial People: " + ((JSlider) e.getSource()).getValue());
            }
        });
        avgCouplingSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                label2.setText("Average Coupling Tendency: " + ((JSlider) e.getSource()).getValue());
            }
        });
        avgCommitmentSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                label3.setText("Average Commitment: " + ((JSlider) e.getSource()).getValue());
            }
        });
        avgCondomUseSilder.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                label4.setText("Average Condom Use: " + ((JSlider) e.getSource()).getValue());
            }
        });
        avgTestFrequency.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                label5.setText("Average Test Frequency: " + ((JSlider) e.getSource()).getValue());
            }
        });

        // Add Action Listeners to buttons when pressed
        buttonSetup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Setup button is working!");
                setup(initialPopulationSlider.getValue(), avgCouplingSlider.getValue(), avgCommitmentSlider.getValue(),
                        avgCondomUseSilder.getValue(), avgTestFrequency.getValue());
            }
        });

        buttonStep.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                next();
            }
        });

        buttonAuto.addActionListener(new ActionListener() {
            private boolean stopped = true;
            private Timer timer = new Timer();
            public void actionPerformed(ActionEvent e) {
                if(!this.stopped) {
                    stopped = true;
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            next();
                        }
                    }, 0 , 1000);
                }
            }
        });

        // Add sliders to panel
        this.panel1.add(initialPopulationSlider);
        this.panel1.add(label1);
        this.panel1.add(avgCouplingSlider);
        this.panel1.add(label2);
        this.panel1.add(avgCommitmentSlider);
        this.panel1.add(label3);
        this.panel1.add(avgCondomUseSilder);
        this.panel1.add(label4);
        this.panel1.add(avgTestFrequency);
        this.panel1.add(label5);

        this.panel3.add(buttonSetup);
        this.panel3.add(buttonStep);
        this.panel3.add(buttonAuto);

        this.results.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(results);
        this.panel4.add(scrollPane);

        frame.add(this.getPanel1());
        frame.add(this.getPanel3());
        frame.add(this.getPanel4());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        /* Make frame visible, disable resizing */
        frame.setVisible(true);
        frame.setResizable(false);

    }

    public JPanel getPanel1() {
        return this.panel1;
    }

    public JPanel getPanel2() {
        return this.panel2;
    }

    public JPanel getPanel3() {
        return this.panel3;
    }

    public JPanel getPanel4() {
        return this.panel4;
    }

    public JPanel getPanel5() {
        return this.panel5;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Over here");
    }

    public void setup(int people, int coupling, int commitment, int condom, int testing) {
        System.out.println("Inside setup method. Creating people");
        this.red = 0;
        this.green = 0;
        this.blue = 0;
        this.week = 0;
        createPeople(people, coupling, commitment, condom, testing);
        System.out.println("Done creating people");
        setupPrint();
    }

    /*
     * public Person(boolean infected, boolean known, int infectionLength, boolean
     * couple, int coupleLength, int commitment, double couplingTendency, int
     * condomUse, int testFrequency)
     */
    public void createPeople(int people, int coupling, int commitment, int condom, int testing) {
        System.out.println("Inside createPeople method");
        int initialInfected = (int) ((double) people * 0.025); // 2.5% of the people start out infected, but does not
                                                               // know they are. Rounded down.
        System.out.println("initial infected: " + initialInfected);
        int counter = 0;

        System.out.println((double) coupling * 0.01);

        population = new ArrayList<Person>();

        for (int i = 0; i < people; i++) {
            if (counter <= initialInfected) {
                population.add(new Person(true, false, (int) ((Math.random() * 100) + 1), false, 0, commitment,
                        coupling, condom, testing, (int) Math.round(Math.random())));
                counter++;
            } else {
                population.add(new Person(false, false, 0, false, 0, commitment, coupling, condom, testing,
                        (int) Math.round(Math.random())));
            }

        }
        System.out.println("End of createPeople method");
    }

    public void setupPrint() {
        System.out.println("Inside, at the start setupPrint");
        this.results.append("Green = Not Infected, Blue = Infected (Not Known), Red = Infected and Knows\n\n");

        // calculate starting population state
        for (int i = 0; i < this.population.size(); i++) {
            if (this.population.get(i).getInfected() && this.population.get(i).getKnown()) {
                this.red++;
            } else if (this.population.get(i).getInfected()) {
                this.blue++;
            } else {
                this.green++;
            }
        }

        this.results.append("---Week 0---\nGreen: " + green + "  Blue: " + blue + "  Red: " + red + "\n\n");
        System.out.println("Inside, at the end of setupPrint");
    }

    public void next() {
        System.out.println("Inside, at the start of next");
        this.week++;
        ArrayList<Person> temp = new ArrayList<Person>();
        int max = 0;
        int min = 0;
        int range = 0;

        System.out.println("Starting while loop");
        while (population.size() != 0) {
            System.out.println("\nInside the while loop start");
            max = this.population.size();
            range = max - min;
            int random1 = 0;
            int random2 = 0;

            System.out.println("At the start of picking a random person. Range: " + range);
            random1 = (int) Math.floor(Math.random() * range); // gets a number from 0 to population max - 1
            System.out.println("Random1: " + random1);
            System.out.println("About to put that random integer to find the person in the population array");
            Person x = population.get(random1); // random person 1

            System.out.println("About to find out if that random person 1 has a couple");
            // if random person 1 is in a relationship
            if (x.getCouple() == true) {
                Person y = x.getPartner();
                areTheyInHeat(x, y);
                temp.add(x);
                temp.add(y);
                this.population.remove(random1);
                if(random1 > random2) {
                    this.population.remove(this.population.indexOf((this.population.get(random1).getPartner())));
                }
                else {
                    this.population.remove(this.population.indexOf((this.population.get(random1).getPartner()))-1);
                }

                // If random person 1 is not in a relationship
            } else {
                System.out.println("Person 1 is not in a relationship");

                // If there's more than 1 person left, find a second person
                if (population.size() > 1) {
                    System.out.println("Getting random number to get person 2");
                    while(random1 == random2) {
                        random2 = (int) Math.floor(Math.random() * range);
                    }
                    System.out.println("Random2: " + random2);
                    Person m = population.get(random2);

                    boolean chanceCouple1 = false; // default false for random person 1
                    boolean chanceCouple2 = false; // default false for random person 2
                    if (m.getCouple() == false) { // if random person 2 is not in a couple
                        System.out.println("Person 2 is not in a couple");
                        // Random 1-100, if the number is greater than couple tendency in int then no
                        // couple
                        if ((int) (x.getCouplingTendency() * 10) < (int) Math.floor((Math.random() * 100) + 1)) {
                            chanceCouple1 = false;
                        } else {
                            chanceCouple1 = true;
                        }

                        // Random 1-100, if the number is greater than couple tendency in int then no
                        // couple
                        if ((int) (m.getCouplingTendency() * 10) < (int) Math.floor((Math.random() * 100) + 1)) {
                            chanceCouple2 = false;
                        } else {
                            chanceCouple2 = true;
                        }

                        // They become a couple and start their couple length
                        System.out.println("Do both person want to become a couple?");
                        if (chanceCouple1 && chanceCouple2) {
                            x.setCouple(true); // now a couple
                            m.setCouple(true);
                            System.out.println("Person 1 and Person 2 are now a couple");
                            x.setCoupleLength(0); // starting couple commitment to 0 week
                            m.setCoupleLength(0);
                            System.out.println("Set Couple length to 0 for new relationships");
                            System.out.println("Check if they are in heat");
                            areTheyInHeat(x, m);
                            temp.add(x);
                            temp.add(m);
                            this.population.remove(random1);
                            if(random1 > random2) {
                                this.population.remove(random2);
                            }
                            else {
                                this.population.remove(random2-1);
                            }
                        } else {
                            System.out.println("They don't want to be in a relationship");
                            System.out.println("Adding person 1 and person 2 to the temporary arrayList");
                            temp.add(x);
                            temp.add(m);
                            System.out.println("Remove random person 1 and person 2 from the original population arraylist");
                            this.population.remove(random1);
                            if(random1 > random2) {
                                this.population.remove(random2);
                            }
                            else {
                                this.population.remove(random2-1);
                            }
                            System.out.println("Person 1 and person 2 removed from the original population arraylist");
                        }
                    } else {
                        System.out.println("Random person 2 has a couple");
                        temp.add(x);
                        temp.add(m);
                        this.population.remove(random1);
                        if(random1 > random2) {
                            this.population.remove(random2);
                        }
                        else {
                            this.population.remove(random2-1);
                        }
                    }

                    // If random person 1 is the only one left
                } else {
                    temp.add(x);
                    this.population.remove(random1);
                }
            }

            System.out.println("About to remove 1 from max, Current max: " + max);
            max--;
            System.out.println("After removing 1 from max, Current max: " + max);
        }


        /*Repopulate original population list from temp list and remove from temp list*/
        for(int i = 0; i < temp.size(); i++) {
            population.add(temp.get(i));
            temp.remove(i);
        }

        nextPrint();
        System.out.println("Inside, at the end of next");
    }

    public void areTheyInHeat(Person x, Person y) {
        System.out.println("Inside, at the start of areTheyInHeat");
        boolean chanceSex1 = false; // default false for random person 1
        boolean chanceSex2 = false; // default false for random person 2

        if ((int) (x.getCouplingTendency() * 10) < (int) Math.floor((Math.random() * 100) + 1)) { // Random 1-100, if the number is greater than couple
                                                                                                  // tendency in int then person x does not want sex
            chanceSex1 = false;
        } else {
            chanceSex1 = true;
        }

        if ((int) (y.getCouplingTendency() * 10) < (int) Math.floor((Math.random() * 100) + 1)) { // Random 1-100, if
                                                                                                  // the number is
                                                                                                  // greater than couple
                                                                                                  // tendency in int
                                                                                                  // then person y does
                                                                                                  // not want sex
            chanceSex2 = false;
        } else {
            chanceSex2 = true;
        }

        System.out.println("Having sex! Person 1 is " + x.getInfected() + ", Person 2 is " + y.getInfected());
        if (chanceSex1 && chanceSex2) {
            if (x.getInfected() || y.getInfected()) {
                if(!x.getInfected()) {
                    this.green -= 1;
                    this.blue += 1;
                    x.setInfected(true);
                }
                if (!y.getInfected()) {
                    this.green -= 1;
                    this.blue += 1;
                    y.setInfected(true);
                }
            }
        }

        checkCoupleLength(x, y);
        System.out.println("Inside, at the end of areTheyInHeat");
    }

    public void checkCoupleLength(Person x, Person y) {
        System.out.println("Inside, at the start of checkCoupleLength");
        // Check couple length is not greater than commitment
        if (x.getCoupleLength() <= x.getCommitment()) {
            x.setCoupleLength(x.getCoupleLength() + 1);
            y.setCoupleLength(y.getCoupleLength() + 1);
        } else { // Couple breaks up if greater than commitment
            x.setCouple(false);
            y.setCouple(false);

            x.setCoupleLength(0);
            y.setCoupleLength(0);

            x.setPartner(null);
            y.setPartner(null);
        }
        System.out.println("Inside, at the endof checkCoupleLength");
    }

    public void nextPrint() {
        System.out.println("Inside, at the start of nextPrint");
        this.results.append(
                "---Week " + this.week + " ---\nGreen: " + green + "  Blue: " + blue + "  Red: " + red + "\n\n");
        System.out.println("Inside, at the end of nextPrint");
    }

    public static void main(String[] args) {
        HIVBoard b = new HIVBoard();
    }

}