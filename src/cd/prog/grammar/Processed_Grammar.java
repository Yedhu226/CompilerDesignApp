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

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author yedhu
 */
public class Processed_Grammar extends Grammar {

    private final Map<Element, Rule> Rule_List_new = new ConcurrentHashMap<>();
    private List<Element> non_terminals_new = new LinkedList<>();

    public Processed_Grammar(Element start, Map<Element, Rule> rules) {
        super(start, rules);
        this.non_terminals_new = super.getNon_terminals();
    }

    public Processed_Grammar(int rule_no) {
        super(rule_no);
        for (Map.Entry<Element, Rule> e : super.getRule_List().entrySet()) {
            Left_Factoring(e.getKey(), e.getValue());
        }
        super.getNon_terminals().addAll(non_terminals_new);
        super.setRule_List(Rule_List_new);
        System.out.println("Grammar after Left Factoring: ");
        this.print_Grammar();
        Collection<Rule> rules = super.getRule_List().values();
        for (Rule r : rules) {
            Left_Recursion(r);
        }
        System.out.println("Grammar after removing Left Recursion: ");
        this.print_Grammar();
    }

    void Left_Factoring(Element e, Rule r) {
        int i = r.getProductions().size();
        if (i <= 1 || (i == 2 && r.getEpsilon() != null)) {
            Rule_List_new.put(e, r);
            return;
        }
        int x = 0;
        Queue<Element> qtemp = new LinkedList<>(r.getProductions().get(0));

        List<List<Element>> factorProd = new LinkedList<>();
        outer:
        for (List<Element> l : r.getProductions()) {
            if (qtemp.containsAll(l)) {
                continue;
            }
            if (qtemp.peek() == l.get(0)) {
                Stack<Element> inter = new Stack<Element>();
                Stack<Element> comp = new Stack<Element>();
                inter.addAll(qtemp);
                comp.addAll(l);
                inner:
                while (true) {
                    if (!inter.isEmpty() && !comp.isEmpty()) {
                        if (inter.peek() != comp.peek() && !inter.equals(comp)) {
                            if (inter.size() > comp.size()) {
                                inter.pop();
                            } else if (inter.size() < comp.size()) {
                                comp.pop();
                            } else {
                                inter.pop();
                                comp.pop();
                            }
                        } else {
                            qtemp.removeAll(qtemp);
                            qtemp.addAll(inter);
                            break;
                        }
                    } else {
                        x++;
                        continue outer;
                    }
                }
            } else {
                factorProd.add(l);
            }
        }
        if (x == r.getProductions().size()) {
            return; //Exit function if no factor is found;
        }
        List<Element> factor = new LinkedList<>(qtemp);
        Element Sym_new = Element_Gen();
        factor.add(Sym_new);
        factorProd.add(factor);
        List<Rule> ruletemp = new LinkedList<>();
        ruletemp.add(new Rule(e, factorProd));
        factorProd.clear();
        for (List<Element> l : r.getProductions()) {
            if (l.get(0) != qtemp.peek()) {
                continue;
            }
            if (l.get(0) == qtemp.peek()) {
                int n = qtemp.size();
                for (int j = 0; j < n; j++) {
                    l.remove(0);
                }
            }
//            l.removeAll(qtemp);
            if (!l.isEmpty()) {
                factorProd.add(l);
            }
            else{
                l.add(Element.create('-'));
                 factorProd.add(l);
            }
        }
        ruletemp.add(new Rule(Sym_new, factorProd));
        for (Rule t : ruletemp) {
            Rule_List_new.put(t.getGen_Symbol(), t);
        }

    }

    void Left_Recursion(Rule r) {
        List<List<Element>> factorProd = new LinkedList<>();
        int x = 0;
        for (List<Element> l : r.getProductions()) {
            if (l.get(0) == r.getGen_Symbol()) {
                x++;
            }
        }
        if (x > 0) {
            Element enew = Element_Gen();
            for (List<Element> l : r.getProductions()) {
                if (l.get(0) == r.getGen_Symbol()) {
                    l.remove(r.getGen_Symbol());
                    l.add(enew);
                    factorProd.add(l);
                    r.getProductions().remove(l);
                } else {
                    l.add(enew);
                }
            }
            List<Element> t = new LinkedList<>();
            t.add(Element.create('-'));
            factorProd.add(t);
            Rule_List_new.put(enew, new Rule(enew, factorProd));
        }
    }

    Element Element_Gen() {
        while (true) {
            Character x = (char) Math.floor(Math.random() * (90 - 65 + 1) + 65);
            Element e = Element.create(x);
            if (super.getNon_terminals().contains(e)) {
                continue;
            } else {
                non_terminals_new.add(e);
                return e;
            }
        }
    }

}
