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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
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
//            System.out.println(i);
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
                        } else if (!pointer.isTerminal()&&pointer!=select) {
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
}
