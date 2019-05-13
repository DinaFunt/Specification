import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Structure {
    private int[][] table;
    private int size;
    private int e;

    public Structure(String fileName) {
        table = getArrayFromFile(fileName);
    }

    private int[][] getArrayFromFile(String file) {
        Scanner in;
        int[][] res = null;

        try {
            in = new Scanner(new File(file));
            size = in.nextInt();
            res = new int[size][size];

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j ++) {
                    res[i][j] = in.nextInt();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return res;
    }

    private boolean isClosedSet() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (table[i][j] >= size) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean isAssociative() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int t = 0; t < size; t ++) {
                    if (table[table[i][j]][t] != table[i][table[j][t]]) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private boolean isCommutative() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (table[i][j] != table[j][i]) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean hasNeutr() {
        for (int i = 0; i < size; i++) {
            boolean flag = true;

            for (int j = 0; j < size; j++) {
                if (table[i][j] != j || table[j][i] != j) {
                    flag = false;
                    break;
                }
            }

            if (flag) {
                e = i;
                return true;
            }
        }
        return false;
    }

    private boolean hasInverse() {
        for (int i = 0; i < size; i++) {
            boolean flag = false;

            for (int j = 0; j < size; j++) {
                if (table[i][j] == e && table[j][i] == e) {
                    flag = true;
                    break;
                }
            }

            if (!flag) {
                return false;
            }
        }
        return true;
    }

    public TypeOfStructure typeOfStructure() {

        if (!isClosedSet()) {
            return TypeOfStructure.Nothing;
        }

        if (hasNeutr() && hasInverse() && isCommutative() && isAssociative()) {
            return TypeOfStructure.AbelianGroup;
        } else if (hasNeutr() && isCommutative() && isAssociative()) {
            return TypeOfStructure.CommutativeMonoid;
        } else if (hasNeutr() && hasInverse() && isAssociative()) {
            return TypeOfStructure.Group;
        } else if (isAssociative() && hasNeutr()) {
            return TypeOfStructure.Monoid;
        } else if (isAssociative()) {
            return TypeOfStructure.Semigroup;
        } else {
            return TypeOfStructure.Magma;
        }
    }
}
