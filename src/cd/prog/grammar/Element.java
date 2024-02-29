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

/**
 * This is the Element class. It describes each individual character
 * (Terminal/NonTerminal) in a production of a rule. Upper Case characters are
 * considered Non Terminals.
 *
 * @author yedhu
 */
public class Element {
    
    private final Character symbol;
    private final boolean Terminal;
    private final boolean epsilon;
    
    public Element(Character in) {
        this.symbol = in;
        this.Terminal = Character.isLowerCase(in) || in.equals('-');
        this.epsilon = in.equals('-');
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
}
