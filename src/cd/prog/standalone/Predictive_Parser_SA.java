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
package cd.prog.standalone;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author yedhu
 */
public class Predictive_Parser_SA {

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Production in the format [Symbol]>[prod]|[prod]... \n Epsilon is denoted by '-'");
        System.out.println("Enter the no. of rules in grammar: ");
        int n = sc.nextInt();
        Grammar Gin = new Grammar(n);
        Gin.print_First();
        Gin.print_Follow();
        Predictive P = new Predictive(Gin);
        P.print_Table();
    }

    private static class Grammar {

        private final Map<Element, Rule> Rule_List;
        private Element Start_Symbol;
        private final Element end_sym = Element.create('$');
        private final Map<Element, Set<Element>> First = new HashMap<>();
        private final Map<Element, List<Set<Element>>> Follow_temp = new HashMap<>();
        private final Map<Element, Set<Element>> Follow = new HashMap<>();
        private Set<Element> terminals = new HashSet<>();
        private List<Element> non_terminals = new LinkedList<>();

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
//            cons_Follow(t);
                this.terminals.addAll(t.getTerminals());
                this.non_terminals.addAll(t.getNon_terminals());
            }
            calc_First();
        }

        public Grammar(int rule_no) {
            Scanner sc = new Scanner(System.in);
            Map<Element, Rule> temp = new HashMap<>();
            String r;
            for (int x = 0; x < rule_no; x++) {
                r = sc.nextLine();
                if (x == 0) {
                    this.Start_Symbol = Element.create(r.charAt(0));
                }
                Element e = Element.create(r.charAt(0));
                if (!non_terminals.contains(e)) {
                    non_terminals.add(e);
                }
                if (!temp.containsKey(e)) {
                    Rule tr = new Rule(r);
                    this.terminals.addAll(tr.getTerminals());
                    Set<Element> f = tr.getFirst();
                    this.First.put(e, f);
                    List<Set<Element>> templist = new LinkedList<>();
                    if (x == 0) {
                        Set<Element> tempfollow = new HashSet<>();
                        tempfollow.add(Element.create('$'));
                        templist.add(tempfollow);
                    }
                    this.Follow_temp.put(e, templist);
                    temp.put(e, tr);
                } else {
                    Rule tr = temp.get(e);
                    tr.append(r);
                    this.terminals.addAll(tr.getTerminals());
                    Set<Element> f = tr.getFirst();
                    this.First.put(e, f);
                    temp.put(e, tr);
                }
            }
            this.Rule_List = temp;
            calc_First();
            for (Element e : non_terminals) {
                calc_Follow(Rule_List.get(e));
            }
            for (Element e : non_terminals) {
                calc_Follow(Rule_List.get(e));
            }
            setFollow();
        }

        void calc_First() {
            for (Map.Entry<Element, Set<Element>> e : this.First.entrySet()) {
                for (Element x : e.getValue()) {
                    if (!x.isTerminal()) {
                        e.getValue().remove(x);
                        if (Rule_List.get(x).getEpsilon() != null) {
                            Set<Element> set = recur_First(Rule_List.get(e.getKey()), x);
                            e.getValue().addAll(set);
                        }
                        e.getValue().addAll(this.First.get(x));
                    }
                }
            }
        }

        Set<Element> recur_First(Rule r, Element e) {
            Set<Element> set = new HashSet<>();
            for (List<Element> l : r.getProductions()) {
                Queue<Element> q = new LinkedList<>(l);
                if (q.peek() != e) {
                    continue;
                }
                q.remove();
                boolean next = true;
                while (next && !q.isEmpty()) {
                    Element x = q.peek();
                    if (q.peek().isTerminal()) {
                        set.add(q.remove());
                        next = false;
                        break;
                    } else if (!x.isTerminal()) {
                        if (Rule_List.get(x).getEpsilon() != null) {
                            set.addAll(First.get(x));
                        } else {
                            set.addAll(First.get(x));
                            next = false;
                            break;
                        }
                    }
                    q.remove();
                }
            }
            return set;
        }

        void calc_Follow(Rule r) {
            for (List<Element> l : r.getProductions()) {
                Queue<Element> q = new LinkedList<>(l);
                Element select;
                select = q.peek();
                int x = 0;
                while (select != null && select.isTerminal()) {
                    select = q.poll();
                }
                if (select == null) {
                    continue;
                }
                if (!select.isTerminal()) {
                    while (true) {
                        Set<Element> tempfollow = new HashSet<>();
                        if (x > 0) {
                            select = q.poll();
                        }
                        x = 1;
                        if (select == null) {
                            break;
                        }
                        if (select.isTerminal()) {
                            continue;
                        }
                        if (q.isEmpty()) {
                            if (r.getGen_Symbol() != select) {
                                if (Follow_temp.containsKey(r.getGen_Symbol())) {
                                    add_Follow(select, Follow_temp.get(r.getGen_Symbol()));
                                    break;
                                } else {
                                    add_Follow(select, tempfollow);
                                    break;
                                }
                            } else {
                                break;
                            }
                        }
                        int i = q.size();
                        for (Element pointer : q) {
//                        if(i==q.size()){
//                            continue;
//                        }
                            if (pointer.isTerminal()) {
                                tempfollow.add(pointer);
                                break;
                            } else if (!pointer.isTerminal() && pointer != select) {
                                tempfollow.addAll(First.get(pointer));
                                if (tempfollow.contains(Element.getEp())) {
                                    tempfollow.remove(Element.getEp());
                                } else {
                                    add_Follow(select, tempfollow);
                                    break;
                                }
                            }
                            i--;
                            if (i == 0) {
                                if (r.getGen_Symbol() != select) {
                                    add_Follow(select, Follow_temp.get(r.getGen_Symbol()));
                                }
                            }
                        }
                        add_Follow(select, tempfollow);
                    }
                }
            }
        }

        void setFollow() {
            for (Map.Entry<Element, List<Set<Element>>> e : Follow_temp.entrySet()) {
                Set<Element> temp = new HashSet<>();
                for (Set<Element> s : e.getValue()) {
                    temp.addAll(s);
                }
                Follow.put(e.getKey(), temp);
            }
        }

        void add_Follow(Element e, Set<Element> temp) {
            Follow_temp.get(e).add(temp);
        }

        void add_Follow(Element e, List<Set<Element>> temp) {
            List<Set<Element>> l = Follow_temp.get(e);
            if (l == null) {
                l = new LinkedList<>();
                for (Set<Element> s : temp) {
                    l.add(s);
                }
                Follow_temp.put(e, l);
            } else {
                Follow_temp.get(e).addAll(temp);
            }
        }

        public void print_Grammar() {
            for (Rule r : Rule_List.values()) {
                r.print_Rule();
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

        public void print_Follow() {
            System.out.println();
            for (Map.Entry<Element, Set<Element>> e : this.Follow.entrySet()) {
                System.out.print("Follow(");
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

        public Map<Element, Set<Element>> getFirst() {
            return First;
        }

        public Map<Element, Set<Element>> getFollow() {
            return Follow;
        }

        public Set<Element> getTerminals() {
            return terminals;
        }

        public List<Element> getNon_terminals() {
            return non_terminals;
        }
    }

    private static class Predictive {

        private Grammar G;
        private final Element end_sym = Element.create('$');
        private Set<Element> terminals = new HashSet<>();
        private List<Element> non_terminals = new LinkedList<>();
        private List<SingleRule> Parse_Rules = new LinkedList<>();
        private final Map<Element, Set<Element>> First;
        private final Map<Element, Set<Element>> Follow;
        private Map<Element, Map<Element, Rule>> Parse_Table = new HashMap<>();
        private Element epsilon = Element.create('-');

        public Predictive(Grammar G) {
            this.G = G;
            this.terminals.addAll(G.getTerminals());
            this.First = G.getFirst();
            this.Follow = G.getFollow();
            this.terminals.add(end_sym);
            this.non_terminals.addAll(G.getNon_terminals());
            for (Element E : non_terminals) {
                Map<Element, Rule> temp = new HashMap<>();
                for (Element t : First.get(E)) {
                    Rule r = check_single(G.getRule_List().get(E), t == epsilon, t);
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
            terminals.remove(epsilon);
        }

        public void print_Table() {
            System.out.println();
            System.out.printf("%-10s \t", "NT \\ T");
            List<Element> ter = new LinkedList<>(terminals);
            for (Element i : ter) {
                System.out.printf("%-10s \t", i);
            }
            System.out.println();
            for (Element E : non_terminals) {
                System.out.printf("%-10s \t", E);
                for (Element t : ter) {
                    if (Parse_Table.get(E).containsKey(t)) {
                        System.out.printf("%-10s \t", Parse_Table.get(E).get(t));
                    } else {
                        System.out.printf("%-10s \t", "err");
                    }
                }
                System.out.println();
            }
        }

        Rule check_single(Rule r, boolean ep, Element e) {
            for (SingleRule i : Parse_Rules) {
                if (i.equals(r)) {
                    return i;
                }
            }
            SingleRule sr = new SingleRule(r, ep, e);
            Parse_Rules.add(sr);
            return sr;
        }

    }

    private static class Element {

        private Character symbol;
        private boolean Terminal;
        private boolean epsilon;
        private static Map<Character, Element> Element_Memory = new HashMap<>();
        private static Element ep = Element.create('-');

        public static Element getEp() {
            return ep;
        }

        public Element(Character in) {
            this.symbol = in;
            this.epsilon = in.equals('-');
            this.Terminal = this.epsilon || !Character.isUpperCase(in);
        }

        static void addElement(Element e) {
            Element_Memory.put(e.getSymbol(), e);
        }

        static Element getElement(Character in) {
            return Element_Memory.get(in);
        }

        public static Element create(Character in) {
            if (!Element.exist(in)) {
                Element e = new Element(in);
                Element_Memory.put(in, e);
                return e;
            }
            return Element.getElement(in);
        }

        public static boolean exist(Character in) {
            return Element_Memory.containsKey(in);
        }

        public boolean isEpsilon() {
            return epsilon;
        }

        public Character getSymbol() {
            return symbol;
        }

        public boolean isTerminal() {
            return Terminal;
        }

        public boolean equals(Element e) {
            return this.symbol.equals(e.getSymbol());
        }

        public void print() {
            System.out.print(this.symbol);
        }

        @Override
        public String toString() {
            return "" + symbol;
        }
    }

    private static class Rule {

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

        public Rule(Element Gen, List<List<Element>> Prod, boolean ep, Element f) {
            Element epe = Element.create('-');
            this.Gen_Symbol = Gen;
            this.Productions.addAll(Prod);
            List<Element> x;
            for (List<Element> l : Productions) {
                if (!ep) {
                    if (l.contains(epe)) {
                        Productions.remove(l);
                    }
                    break;
                } else if (ep) {
                    if (l.contains(epe)) {
                        continue;
                    }
                    Productions.remove(l);
                }
            }
            if (Productions.size() > 1) {
                for (List<Element> l : Productions) {
                    Queue<Element> q = new LinkedList<>(l);
                    if (!q.peek().equals(f)) {
                        Productions.remove(l);
                    }
                }
            }
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

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 41 * hash + Objects.hashCode(this.Gen_Symbol);
            return hash;
        }

        @Override
        public String toString() {
            String s = "";
            int i = Productions.size();
            for (List<Element> l : Productions) {
                if (i != Productions.size() && i != 1) {
                    s = s + "|";
                }
                for (Element e : l) {
                    s = s + e;
                }
            }
            return Gen_Symbol + "->" + s;
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
            if (!Objects.equals(this.Gen_Symbol, other.Gen_Symbol)) {
                return false;
            }
            return Objects.equals(this.Productions, other.Productions);
        }
    }

    public static class SingleRule extends Rule {
        public SingleRule(Rule r, boolean ep, Element e) {
            super(r.getGen_Symbol(), r.getProductions(), ep, e);
        }
    }
}
