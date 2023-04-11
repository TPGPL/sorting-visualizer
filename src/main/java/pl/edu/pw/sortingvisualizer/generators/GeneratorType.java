package pl.edu.pw.sortingvisualizer.generators;

/**
 * Obsługiwane typy generatorów wektorów liczb rzeczywistych.
 */
public enum GeneratorType {
    /**
     * Generator wektorów liczb pseudolosowych.
     */
    Random,
    /**
     * Generator posortowanych rosnąco wektorów.
     */
    Ascending,
    /**
     * Generator posortowanych malejąco wektorów.
     */
    Descending,
    /**
     * Generator wektorów o stałej wartości.
     */
    Constant;

    /**
     * Zwraca odpowiadający generator dla określonego typu.
     *
     * @param e typ generatora
     * @return generator przyporządkowany do określonego typu
     */
    public static ArrayGenerator getGeneratorFromValue(GeneratorType e) {
        return switch (e) {
            case Random -> new RandomArrayGenerator();
            case Ascending -> new AscendingArrayGenerator();
            case Descending -> new DescendingArrayGenerator();
            case Constant -> new ConstantArrayGenerator();
        };
    }
}
