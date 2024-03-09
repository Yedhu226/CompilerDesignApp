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

/**
 * This is the Element class. It describes each individual character
 * (Terminal/NonTerminal) in a production of a rule. Upper Case characters are
 * considered Non Terminals.
 *
 * @author yedhu
 */
public class Element {

    private Character symbol;
    private boolean Terminal;
    private boolean epsilon;
    private static Map<Character, Element> Element_Memory = new HashMap<>();
    private static Element ep=Element.create('-');

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
