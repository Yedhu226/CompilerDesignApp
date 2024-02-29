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

import java.util.LinkedList;
import java.util.List;

/**
 * This is the Rule class which contains each individual Rule in a grammar.
 *
 * @author yedhu
 */
public class Rule {

    private final Element Gen_Symbol;
    private List<List<Element>> Productions = new LinkedList<>();

    public Rule(String in) {
        String[] a = in.split(">");
        Character t = a[0].charAt(0);
        this.Gen_Symbol = new Element(t);
        String b = a[1];
        a = b.split("\\|");
        int n;
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

    public void print_Rule() {
        System.out.print(Gen_Symbol.getSymbol() + ">");
        for (List<Element> rule : Productions) {
            for (Element ele : rule) {
                System.out.print(ele.getSymbol());
            }
            System.out.print("|");
        }
    }

    public void addRule(String j) {
        int n = j.length();
        List<Element> tempprod = new LinkedList<>();
        Character tempsym;
        for (int i = 0; i < n; i++) {
            tempsym = j.charAt(i);
            tempprod.add(new Element(tempsym));
        }
        Productions.add(tempprod);
    }
}
