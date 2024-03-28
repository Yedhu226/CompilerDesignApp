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
import static cd.prog.standalone.Reversed.reversed;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author yedhu
 */
public class Leading_Trailing {

    private static Grammar G;

    public static void main() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Production in the format [Symbol]>[prod]|[prod]... \n Epsilon is denoted by '-'");
        System.out.println("Enter no. of rules: ");
        int n = sc.nextInt();
        G = new Grammar(n);
        Map<Element, Set<Element>> Leading = getLeading();
        Map<Element, Set<Element>> Trailing = getTrailing();
        System.out.println("Leading of Grammar: ");
        for (Map.Entry<Element, Set<Element>> e : Leading.entrySet()) {
            System.out.printf("%s = %s\n", e.getKey(),e.getValue());
        }
        System.out.println("Trailing of Grammar: ");
        for (Map.Entry<Element, Set<Element>> e : Trailing.entrySet()) {
            System.out.printf("%s = %s\n", e.getKey(),e.getValue());
        }
    }

    private static boolean noTerminal(List<Element> l) {
        for (Element e : l) {
            if (e.isTerminal()) {
                return true;
            }
        }
        return false;
    }

    private static Map<Element, Set<Element>> getLeading() {
        Map<Element, Set<Element>> leading = new HashMap<>();
        for (Rule r : G.getRule_List().values()) {
            Set<Element> temp = new HashSet<>();
            for (List<Element> l : r.getProductions()) {
                if (noTerminal(l)) {
                    for (Element e : l) {
                        if (e.isTerminal()) {
                            temp.add(e);
                            break;
                        }
                    }
                } else {
                    temp.addAll(getLeading(l.get(0)));
                }
            }
            leading.put(r.getGen_Symbol(), temp);
        }
        return leading;
    }

    private static List<Element> getLeading(Element e) {
        List<Element> leading = new LinkedList<>();
        Rule r = G.getRule_List().get(e);
        for (List<Element> l : r.getProductions()) {
            if (noTerminal(l)) {
                for (Element et : l) {
                    if (et.isTerminal()) {
                        leading.add(et);
                        break;
                    }
                }
            } else {
                leading.addAll(getLeading(l.get(0)));
            }
        }
        return leading;
    }

    private static Map<Element, Set<Element>> getTrailing() {
        Map<Element, Set<Element>> trailing = new HashMap<>();
        for (Rule r : G.getRule_List().values()) {
            Set<Element> temp = new HashSet<>();
            for (List<Element> l : r.getProductions()) {
                if (noTerminal(l)) {
                    for (Element e : reversed(l)) {
                        if (e.isTerminal()) {
                            temp.add(e);
                            break;
                        }
                    }
                } else {
                    temp.addAll(getTrailing(l.get(0)));
                }
            }
            trailing.put(r.getGen_Symbol(), temp);
        }
        return trailing;
    }

    private static List<Element> getTrailing(Element e) {
        List<Element> trailing = new LinkedList<>();
        Rule r = G.getRule_List().get(e);
        for (List<Element> l : r.getProductions()) {
            if (noTerminal(l)) {
                for (Element et : reversed(l)) {
                    if (et.isTerminal()) {
                        trailing.add(et);
                        break;
                    }
                }
            } else {
                trailing.addAll(getTrailing(l.get(0)));
            }
        }
        return trailing;
    }
}
