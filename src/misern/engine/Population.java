package misern.engine;

import misern.encrypt.Encrypt;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Represents whole population and actions on it
 * @author Sebastian Smolińśki
 * @version 1.0
 */
public class Population {
    private HashMap<Individual, Integer> population;

    /**
     * Initializes population with empty population map
     */
    Population() {
        this.population = new HashMap<>();
    }

    /**
     * Creates initial population based on raw string
     * @param populationRaw with integer values separated with commas
     */
    void createInitialPopulation(String populationRaw) {
        for(String num : populationRaw.split(",")) {
            population.put(new Individual(Integer.parseInt(num)), 0);
        }
    }

    /**
     * Returns number of the individuals in population
     * @return number of the individuals in population
     */
    public int size() {
        return population.size();
    }

    /**
     * Converts population array with individuals extended to chosen coding type
     * @param codingType gray or binary form
     * @return array with individuals
     */
    public String [] printPopulation(int codingType) {
        String [] individuals = new String[population.size()];

        int bits = getMaxBits();

        int i = 0;
        for(Individual individual : population.keySet()) {
            if(codingType == Algorithm.CODING_BINARY) {
                individuals[i] = individual.getExtendedBinaryForm(bits);
            } else {
                individuals[i] = individual.getExtendedGrayForm(bits);
            }

            i++;
        }

        return individuals;
    }

    /**
     * Gets bits number of maximum individual form
     * @return number of longest binary or gray individual from popluation
     */
    int getMaxBits() {
        int bits = 0;
        try {
            bits = Encrypt.toBinary(
                    population.keySet()
                            .stream()
                            .max(Comparator.comparingInt(Individual::getNumber))
                            .orElseThrow(Exception::new).getNumber())
                    .length();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bits;
    }

    /**
     * Calculates new individuals fitness
     */
    void calculateFitness() {
        population.forEach((k, v) -> population.put(k, k.getNumber()/2));
    }

    /**
     * Select random Individual based on their fitness
     * @return random Individual
     */
    Individual selectRandomWheelChoose() {
        Random rand = new Random();

        int totalFitness = population.values().stream().mapToInt(value -> value).sum();
        SortedSet<Individual> keys = new TreeSet<>(population.keySet());

        int val = rand.nextInt(totalFitness);

        Individual tmp = null;
        for(Individual key : keys) {
            if(val - population.get(key) <= 0) {
                return key;
            } else {
                tmp = key;
                val -= population.get(key);
            }
        }

        return tmp;
    }

    /**
     * Generates new individual based on genotypes of father and mother extended to bits-size using binary form
     * @param ind1 represents father of the new individual
     * @param ind2 represents mother of the new individual
     * @param bits length of the bits form that should be generated
     * @return brand new individual for new generation
     */
    Individual generateChildren(Individual ind1, Individual ind2, int bits) {
        int point = Encrypt.toBinary(ind1.getNumber(), bits).length() / 2;

        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < Encrypt.toBinary(ind1.getNumber(), bits).length(); i++) {
            if(i < point) {
                builder.append(Encrypt.toBinary(ind1.getNumber(), bits).charAt(i));
            } else {
                builder.append(Encrypt.toBinary(ind2.getNumber(), bits).charAt(i));
            }
        }

        return new Individual(Integer.parseInt(builder.toString(),2));
    }

    /**
     * Generates new individual based on genotypes of father and mother extended to bits-size using gray form
     * @param ind1 represents father of the new individual
     * @param ind2 represents mother of the new individual
     * @param bits length of the bits form that should be generated
     * @return brand new individual for new generation
     */
    Individual generateChildrenGray(Individual ind1, Individual ind2, int bits) {
        int point = Encrypt.toGray(ind1.getNumber(), bits).length() / 2;

        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < Encrypt.toGray(ind1.getNumber(), bits).length(); i++) {
            if(i < point) {
                builder.append(Encrypt.toGray(ind1.getNumber(), bits).charAt(i));
            } else {
                builder.append(Encrypt.toGray(ind2.getNumber(), bits).charAt(i));
            }
        }

        return new Individual(Integer.parseInt(builder.toString(),2));
    }

    /**
     * Tries to mutate every single individual in the population
     * @param pm probability of single mutation
     * @param codingType chosen by user type of the coding
     */
    void mutatePopulation(double pm, int codingType) {
        Random rnd = new Random();
        for(Individual individual : population.keySet()) {
            if(rnd.nextInt(100) <= pm * 100) {
                if(codingType == Algorithm.CODING_BINARY) {
                    individual.mutateBinary(getMaxBits());
                } else {
                    individual.mutateGray(getMaxBits());
                }
            }
        }
    }

    /**
     * Trims new generation by saving children and bs % of best individuals (by fitness) and randomly fill population to initial size
     * @param bs % of the best individuals that should be saved in next gen
     * @param children map with children that should be saved in next gen
     */
    void trimPopulation(double bs, HashMap<Individual, Integer> children) {
        int bestIndividuals = (int)(size() * bs);
        int initialSize = size();

        for(int i = 0; i < bestIndividuals; i++) {
            try {
                Individual ind = population.keySet()
                        .stream()
                        .max(Comparator.comparingInt(Individual::getNumber))
                        .orElseThrow(Exception::new);

                population.remove(ind);
                children.put(ind, ind.getNumber() / 2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        population = children;
        fillPopulation(initialSize);
    }

    private void fillPopulation(int size) {
        Random rnd = new Random();
        for(int i = population.size(); i < size; i++) {
            int val = rnd.nextInt(getBestIndividual().getNumber() * 2);

            population.put(new Individual(val), val / 2);
        }
    }

    private Individual getBestIndividual() {
        Individual ind = null;

        try {
            ind = population.keySet()
                    .stream()
                    .max(Comparator.comparingInt(Individual::getNumber))
                    .orElseThrow(Exception::new);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ind;
    }
}
