package pl.edu.pw.sortingvisualizer.generators;

public enum GeneratorType {
    Random,
    Ascending,
    Descending,
    Constant;

    public static ArrayGenerator getGeneratorFromValue(GeneratorType e) {
        return switch (e) {
            case Random -> new RandomArrayGenerator();
            case Ascending -> new AscendingArrayGenerator();
            case Descending -> new DescendingArrayGenerator();
            case Constant -> new ConstantArrayGenerator();
        };
    }
}
