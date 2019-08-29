package misern.engine;

import misern.ui.CrossoverBox;
import misern.ui.FailBox;

import java.util.HashMap;
import java.util.Random;

/**
 * Whole engine of the Genetic Algorithm
 * @author Sebastian Smoliński
 * @version 1.1
 */
public class Algorithm {
    /**
     * Using coding in binary form
     */
    public static final int CODING_BINARY = 0;

    /**
     * Using coding in gray form
     */
    public static final int CODING_GRAY = 1;

    /**
     * Using one point type of crossing (exactly in the middle)
     */
    public static final int CROSSING_ONE_POINT = 0;

    /**
     * Using two points type of crossing (1/3 and 2/3 point)
     */
    public static final int CROSSING_TWO_POINTS = 1;

    private Population pop;
    private double pc, pm, bs;

    /**
     * Creates empty algorithm object with population map
     */
    public Algorithm() {
        pop = new Population();
    }

    /**
     * Gets population object
     * @return population using inside Algorithm
     */
    public Population getPop() {
        return pop;
    }

    /**
     * Sets initial population
     * @param raw numbers of individuals separated by commas
     */
    public void setInitialPopulation(String raw) {
        pop.createInitialPopulation(raw);
    }

    /**
     * Sets probability of the crossing individuals
     * @param pc 0.0 to 1.0 probability
     */
    public void setPc(double pc) {
        this.pc = pc;
    }

    /**
     * Sets probability of the mutation
     * @param pm 0.0 to 1.0 probability
     */
    public void setPm(double pm) {
        this.pm = pm;
    }

    /**
     * Sets percentage part of best individuals to save in next generation
     * @param bs 0.0 to 1.0 probability
     */
    public void setBs(double bs) {
        this.bs = bs;
    }

    /**
     * Simulates one iteration of the genetic algorithm
     * @param crossingType type of the crossing, one or two points
     * @param codingType type of the coding, binary or gray
     */
    public void nextStep(int crossingType, int codingType) {
        pop.calculateFitness();
        HashMap<Individual, Integer> children = simulateCrossover(crossingType, codingType);
        pop.mutatePopulation(pm, codingType);
        pop.trimPopulation(bs, children);
    }

    private HashMap<Individual, Integer> simulateCrossover(int crossingType, int codingType) {
        Random rnd = new Random();
        HashMap<Individual, Integer> children = new HashMap<>();

        for(int i = 0; i < pop.size()/2; i++) {
            Individual father = pop.selectRandomWheelChoose();
            Individual mother = pop.selectRandomWheelChoose();

            int bits = pop.getMaxBits();
            if(rnd.nextInt(100) <= pc * 100) {
                Individual child;
                if(codingType == Algorithm.CODING_BINARY) {
                    child = pop.generateChildren(father, mother, bits);
                } else {
                    child = pop.generateChildrenGray(father, mother, bits);
                }

                children.put(child, 0);

                if(crossingType == Algorithm.CROSSING_ONE_POINT) {
                    CrossoverBox.displayOnePoint(father, mother, child, bits);
                } else {
                    CrossoverBox.displayTwoPoints(father, mother, child, bits);
                }
            } else {
                FailBox.display();
            }
        }

        return children;
    }
}
