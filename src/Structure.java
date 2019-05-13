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
        }

        return res;
    }

/*
    private void printTable() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(table[i][j] + " ");
            }

            System.out.println();
        }
    }
*/

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
        boolean flag = true;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (table[i][j] != j || table[j][i] != j) {
                    flag = false;
                    break;
                }
            }

            if (flag) {
                e = i;
                return true;
            } else {
                flag = true;
            }
        }

        return false;
    }

    private boolean hasInverse() {
        boolean flag = false;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (table[i][j] == e && table[j][i] == e) {
                    flag = true;
                }
            }

            if (!flag) {
                return false;
            }

            flag = false;
        }

        return true;

    }

    public void typeOfStructure() {

        if (!isClosedSet()) {
            System.out.println(TypeOfStructure.Nothing);
            return;
        }

        if (hasNeutr() && hasInverse() && isCommutative() && isAssociative()) {
            System.out.println(TypeOfStructure.AbelianGroup);
        } else if (hasNeutr() && isCommutative() && isAssociative()) {
            System.out.println(TypeOfStructure.CommutativeMonoid);
        } else if (hasNeutr() && hasInverse() && isAssociative()) {
            System.out.println(TypeOfStructure.Group);
        } else if (isAssociative() && hasNeutr()) {
            System.out.println(TypeOfStructure.Monoid);
        } else if (isAssociative()) {
            System.out.println(TypeOfStructure.Semigroup);
        } else {
            System.out.println(TypeOfStructure.Magma);
        }
    }
}
