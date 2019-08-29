package misern.engine;

import misern.encrypt.Encrypt;
import misern.ui.MutationBox;

import java.util.Random;

/**
 * Represents single individual form population
 * @author Sebastian Smoliński
 * @version 1.1
 */
public class Individual implements Comparable<Individual> {
    private int number;

    /**
     * Initializes individual with their number as main trait
     * @param number positive number
     */
    Individual(int number) {
        this.number = number;
    }

    /**
     * Gets number that determines individual
     * @return positive number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Gets number that determines individual in extended binary form
     * @param bits how long binary form will be returned
     * @return binary form of the individual's number
     */
    String getExtendedBinaryForm(int bits) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < bits - Encrypt.toBinary(number).length(); i++) {
            builder.append("0");
        }

        builder.append(Encrypt.toBinary(number));
        return builder.toString();
    }

    /**
     * Gets number that determines individual in extended gray form
     * @param bits how long gray form will be returned
     * @return gray form of the individual's number
     */
    String getExtendedGrayForm(int bits) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < bits - Encrypt.toGray(number).length(); i++) {
            builder.append("0");
        }

        builder.append(Encrypt.toGray(number));
        return builder.toString();
    }

    /**
     * Do mutation (negation) on one, random chosen bit in binary form of the individual's number
     * @param maxBits how long binary form should be mutated
     */
    void mutateBinary(int maxBits) {
        String before = Encrypt.toBinary(number, maxBits);

        Random rnd = new Random();
        int position = rnd.nextInt(maxBits-1);

        if(Encrypt.toBinary(number, maxBits).charAt(maxBits-position-1) == '0') {
            number += Math.pow(2, (position));
        } else {
            number -= Math.pow(2, (position));
        }

        String after = Encrypt.toBinary(number, maxBits);

        MutationBox.display(before, after, position);
    }

    /**
     * Do mutation on one, random chosen bit in gray form of the individual's number
     * @param maxBits how long gray form should be mutated
     */
    void mutateGray(int maxBits) {
        String before = Encrypt.toGray(number, maxBits);

        Random rnd = new Random();
        int position = rnd.nextInt(maxBits-1);

        if(Encrypt.toGray(number, maxBits).charAt(maxBits-position-1) == '0') {
            number += Math.pow(2, (position));
        } else {
            number -= Math.pow(2, (position));
        }

        String after = Encrypt.toGray(number, maxBits);

        MutationBox.display(before, after, position);
    }

    /**
     * Compares two individuals based on theirs numbers
     * @param o other individual
     * @return difference of individual's numbers
     */
    @Override
    public int compareTo(Individual o) {
        return number - o.getNumber();
    }
}
