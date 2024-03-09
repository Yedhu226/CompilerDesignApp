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
package cd.prog.parser;

import cd.prog.grammar.Element;
import cd.prog.grammar.Grammar;
import cd.prog.grammar.Rule;
import cd.prog.grammar.SingleRule;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author yedhu
 */
public class Predictive {

    private Grammar G;
    private final Element end_sym = Element.create('$');
    private Set<Element> terminals;
    private List<Element> non_terminals;
    private List<SingleRule> Parse_Rules = null;
    private final Map<Element, Set<Element>> First;
    private final Map<Element, Set<Element>> Follow;
    private Map<Element, Map<Element, Rule>> Parse_Table=new HashMap<>();
    private Element epsilon = Element.create('-');

    public Predictive(Grammar G) {
        this.G = G;
        this.terminals = G.getTerminals();
        this.First = G.getFirst();
        this.Follow = G.getFollow();
        terminals.add(end_sym);
        this.non_terminals = G.getNon_terminals();
        for (Element E : non_terminals) {
            Map<Element, Rule> temp = new HashMap<>();
            for (Element t : First.get(E)) {
                Rule r = check_single(G.getRule_List().get(E), t == epsilon);
                if (t == epsilon) {
                    for (Element u : Follow.get(E)) {
                        temp.put(u, r);
                    }
                } else {
                    temp.put(t, r);
                }
            }
            Parse_Table.put(E, temp);
        }
    }

    public void print_Table() {
        System.out.println();
        System.out.print("NT \\ T \t");
        List<Element> ter = new LinkedList<>(terminals);
        for (Element i : ter) {
            System.out.printf("%s \t", i);
        }
        System.out.println();
        for (Element E : non_terminals) {
            System.out.printf("%s \t", E);
            for (Element t : ter) {
                if (Parse_Table.get(E).containsKey(t)) {
                    System.out.printf("%s \t", Parse_Table.get(E).get(t));
                } else {
                    System.out.print("err \t");
                }
            }
            System.out.println();
        }
    }

    Rule check_single(Rule r, boolean ep) {
        if (Parse_Rules != null) {
            for (Rule i : Parse_Rules) {
                if (i.equals(r)) {
                    return i;
                }
            }
        }
        return new SingleRule(r, ep);
    }

}
