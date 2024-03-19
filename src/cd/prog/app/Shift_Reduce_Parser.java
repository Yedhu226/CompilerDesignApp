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

import cd.prog.grammar.Grammar;
import cd.prog.parser.Shift_Reduce;
import java.util.Scanner;

/**
 *
 * @author yedhu
 */
public class Shift_Reduce_Parser {

    public static void main() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Production in the format [Symbol]>[prod]|[prod]... \n Epsilon is denoted by '-'");
        System.out.println("Enter no. of rules: ");
        int n = sc.nextInt();
        Grammar G = new Grammar(n);
        Shift_Reduce SR1=new Shift_Reduce(G);
        System.out.println("Enter String to be parsed:");
        String str=sc.next();
        SR1.ParseString(str);
    }
}
