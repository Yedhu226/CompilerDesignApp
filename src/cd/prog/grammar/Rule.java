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

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * This is the Rule class which contains each individual Rule in a grammar.
 *
 * @author yedhu
 */
public class Rule {

    private Element Gen_Symbol;
    private List<List<Element>> Productions = new LinkedList<>();
    private Set<Element> First = new HashSet<>();
    private Set<Element> terminals = new HashSet<>();
    private Set<Element> non_terminals = new HashSet<>();
    private Element epsilon = null;

    public Rule(String in) {
        String[] a = in.split(">");
        Character t = a[0].charAt(0);
        this.Gen_Symbol = Element.create(t);
        String b = a[1];
        a = b.split("\\|");
        for (String j : a) {
            addRule(j);
        }
    }

    public Rule(Element Gen, List<List<Element>> Prod, boolean ep) {
        this.Gen_Symbol = Gen;
        for (List<Element> l : Prod) {
            if (l.contains(epsilon) && !ep) {
                Prod.remove(l);
                break;
            }
            else if(!l.contains(epsilon)&& ep){
                Prod.remove(l);
            }
        }
        this.Productions = Prod;

    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.Gen_Symbol);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Rule other = (Rule) obj;
        return Objects.equals(this.Gen_Symbol, other.Gen_Symbol);
    }

    @Override
    public String toString() {
        String s="";
        for(List<Element> l: Productions){
            s=s+l;
        }
        return Gen_Symbol + "->" + s;
    }

    public Rule() {
        //def
    }

    public void append(String in) {
        String[] a = in.split(">");
        String b = a[1];
        a = b.split("\\|");
        for (String j : a) {
            addRule(j);

        }
    }

    public Element getGen_Symbol() {
        return Gen_Symbol;
    }

    public List<List<Element>> getProductions() {
        return Productions;
    }

    public Set<Element> getFirst() {
        return First;
    }

    public void print_Rule() {
        int i = 0;
        System.out.print(Gen_Symbol.getSymbol() + ">");
        for (List<Element> rule : Productions) {
            if (i > 0) {
                System.out.print("|");
            }
            for (Element ele : rule) {
                System.out.print(ele.getSymbol());
            }
            i++;
        }
        System.out.println();
    }

    public void addRule(String j) {
        int n = j.length();
        List<Element> tempprod = new LinkedList<>();
        Character tempsym;
        for (int i = 0; i < n; i++) {
            tempsym = j.charAt(i);
            Element el = Element.create(tempsym);
            if (i == 0) {
                First.add(el);
            }
            tempprod.add(el);
            if (el.isTerminal()) {
                terminals.add(el);
            } else {
                non_terminals.add(el);
            }
            if (el.isEpsilon()) {
                epsilon = el;
            }
        }
        Productions.add(tempprod);
    }

    public Element getEpsilon() {
        return epsilon;
    }

    public Set<Element> getTerminals() {
        return terminals;
    }

    public Set<Element> getNon_terminals() {
        return non_terminals;
    }
}
