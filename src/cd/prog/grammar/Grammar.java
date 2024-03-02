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
import java.util.HashSet;
import java.util.List;
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
            this.First.put(ru.getKey(), f);
            cons_Follow(t);
        }
        calc_First();
    }

    public Grammar(int rule_no) {
        Scanner sc = new Scanner(System.in);
        Map<Element, Rule> temp = new HashMap<>();
        String r;
        Element start = new Element('S');
        for (int x = 0; x < rule_no; x++) {
//            System.out.println(i);
            r = sc.nextLine();
            if (x == 0) {
                start = new Element(r.charAt(0));
            }
            Element e = new Element(r.charAt(0));
            Rule tr = new Rule(r);
            Set<Element> f = tr.getFirst();
            this.First.put(e, f);
            cons_Follow(tr);
            temp.put(e, new Rule(r));
        }
        this.Rule_List = temp;
        this.Start_Symbol = start;
        calc_First();
    }

    void calc_First() {
        for (Map.Entry<Element, Set<Element>> e : this.First.entrySet()) {
            for (Element x : e.getValue()) {
                if (!x.isTerminal()) {
                    e.getValue().remove(x);
                    for (Element y : this.First.keySet()) {
                        if (y.equals(x)) {
                            e.getValue().addAll(this.First.get(y));
                        }
                    }
                }
            }
        }
    }

    void cons_Follow(Rule t) {
        for (List<Element> l : t.getProductions()) {
            Element i = null;
            for (Element j : l) {
                if (i != null) {
                    if (!i.isTerminal()) {
                        if (this.Follow.containsKey(i)) {
                            Set<Element> temp = Follow.get(i);
                            temp.add(j);
                            this.Follow.put(i, temp);
                        } else {
                            Set<Element> temp = new HashSet<>();
                            temp.add(j);
                            this.Follow.put(i, temp);
                        }
                    }
                }
                i = j;
            }
            if (!i.isTerminal()) {
                Element j=t.getGen_Symbol();
                if (this.Follow.containsKey(i)) {
                    for (Element y : this.Follow.keySet()) {
                        if (y.equals(i)) {
                            Set<Element> temp = Follow.get(t.getGen_Symbol());
                            temp.add(j);
                        }
                    }
                    Set<Element> temp = Follow.get(t.getGen_Symbol());
                    temp.add(j);
                    this.Follow.put(i, temp);
                } else {
                    Set<Element> temp = new HashSet<>();
                    temp.add(j);
                    this.Follow.put(i, temp);
                }
            }
        }
    }

    void calc_Follow() {
        for (Map.Entry<Element, Rule> e : this.Rule_List.entrySet()) {

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
