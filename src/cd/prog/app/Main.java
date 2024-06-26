
/*
 * Copyright (C) 2024 yedhu
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package cd.prog.app;

import java.util.Scanner;

/**
 * This is where the App starts, the program needed can be called here.
 *
 * @author yedhu
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Select the program to run: ");
        System.out.printf("""
                          1. First and Follow 
                          2. Predictive Parser 
                          3. Left Recursion and Factoring
                          4. Shift-Reduce Parser 
                          5. Leading and Trailing
                          6. Intermediate Code Generation
                          7. Infix to Prefix and Postfix
                          """);
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        switch (n) {
            case 1 -> {
                First_Follow.main();
            }
            case 2 -> {
                Predictive_Parser.main();
            }
            case 3 -> {
                LeftRecursion.main();
            }
            case 4 -> {
                Shift_Reduce_Parser.main();
            }
            case 5 -> {
                Leading_Trailing.main();
            }
            case 6 -> {
                IC_Generator.main();
            }
            case 7 -> {
                PrePostfix.main();
            }
            default -> {
                System.out.println("Option does not exist");
            }
        }
    }

}
