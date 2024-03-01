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
package cd.prog.grammar;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * This is the Grammar class which allows objects to be created to describe a
 * grammar with set rules.
 *
 * @author yedhu
 */
public class Grammar {

    private final Map<Element, Rule> Rule_List;
    private final Element Start_Symbol;
    private final Map<Element, Set<Element>> First = new HashMap<>();
    private final Map<Element, Set<Element>> Follow = new HashMap<>();

    public Element getStart_Symbol() {
        return Start_Symbol;
    }

    public Map<Element, Rule> getRule_List() {
        return Rule_List;
    }

    public Grammar(Element start, Map<Element, Rule> rules) {
        this.Start_Symbol = start;
        this.Rule_List = rules;
        for (Map.Entry<Element, Rule> ru : Rule_List.entrySet()) {
            Rule t = ru.getValue();
            Set<Element> f = t.getFirst();
            First.put(ru.getKey(), f);
        }
        for (Map.Entry<Element, Set<Element>> e : First.entrySet()) {
            for (Element x : e.getValue()) {
                if (!x.isTerminal()) {
                    e.getValue().remove(x);
                    for (Element y : First.keySet()) {
                        if (y.equals(x)) {
                            e.getValue().addAll(First.get(y));
                        }
                    }
                }
            }
        }
    }

    public Grammar(int rule_no) {
        Scanner sc = new Scanner(System.in);
        Map<Element, Rule> temp = new HashMap<>();
        String r;
        Element start = new Element('S');
        for (int i = 0; i < rule_no; i++) {
//            System.out.println(i);
            r = sc.nextLine();
            if (i == 0) {
                start = new Element(r.charAt(0));
            }
            Element e = new Element(r.charAt(0));
            Rule tr = new Rule(r);
            Set<Element> f = tr.getFirst();
            First.put(e, f);
            temp.put(e, new Rule(r));
        }
        this.Rule_List = temp;
        this.Start_Symbol = start;
        for (Map.Entry<Element, Set<Element>> e : First.entrySet()) {
            for (Element x : e.getValue()) {
                if (!x.isTerminal()) {
                    e.getValue().remove(x);
                    for (Element y : First.keySet()) {
                        if (y.equals(x)) {
                            e.getValue().addAll(First.get(y));
                        }
                    }
                }
            }
        }
    }

    public void print_First() {
        System.out.println();
        for (Map.Entry<Element, Set<Element>> e : First.entrySet()) {
            System.out.print("First(");
            e.getKey().print();
            System.out.print(")->{");
            for (Element x : e.getValue()) {
                System.out.print(" '");
                x.print();
                System.out.print("' ");
            }
            System.out.print("}\n");
        }
    }
}
