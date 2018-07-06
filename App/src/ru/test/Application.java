package ru.test;

public class Application {
    static Integer sumMassive (String[][] mass) throws MyArraySizeException, MyArrayDataException {
        if (mass.length != 4) throw new MyArraySizeException("Неверное число строк");
        int j = 0;
        int i = 0;
        for (i = 0; i < 4; i++) {
            if (mass[i].length != 4) throw new MyArraySizeException("Неверное число столбцов в строке " + (i + 1));
        }
        try {
            int result = 0;
            for (i = 0; i < 4; i++) {
                for (j = 0; j < 4; j++) {
                    result += Integer.parseInt(mass[i][j]);
                }
            }
            return result;
        } catch (NumberFormatException e) {
            throw new MyArrayDataException("Не могу преобразовать элемент " + mass[i][j] + " в число. Он лежит в ячейке (" + (i+1) + "," + (j+1) + ")");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return 0;
    }

    public static void main(String[] args) {
        String[][] base = {{"0","1","2","3"},{"0","1","2","3"},{"0","1","23","3"},{"0","1","2","3"}};
        int x = 0;
        try {
            x = sumMassive(base);
        } catch (MyArraySizeException | MyArrayDataException e) {
            System.out.println(e.toString());
        }
        System.out.println(x);


    }
}
