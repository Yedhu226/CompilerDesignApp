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

import cd.prog.grammar.Element;
import cd.prog.grammar.Grammar;
import cd.prog.grammar.Rule;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * This is the First_Follow Class.
 *
 * @author yedhu
 */
public class First_Follow {

    private static Map<Element, List<Element>> First = new HashMap<>();
    private static Map<Element, List<Element>> Follow = new HashMap<>();

    public static void main() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Production in the format [Symbol]>[prod]|[prod]... \n Epsilon is denoted by '-'");
        System.out.println("Enter the no. of rules in grammar: ");
        int n = sc.nextInt();
        Map<Element, Rule> temp = new HashMap<>();
        String r;
        Element start = new Element('S');
        for (int i = 0; i < n; i++) {
            r = sc.nextLine();
            if (i == 0) {
                start = new Element(r.charAt(0));
            }
            temp.put(new Element(r.charAt(0)), new Rule(r));
        }
        Grammar Gin = new Grammar(start, temp);
        get_First(Gin, true);
    }

    public static void get_First(Grammar G, boolean print) {
        for (Map.Entry<Element, Rule> r : G.getRule_List().entrySet()) {
            Rule t=r.getValue();
        }
    }

}
