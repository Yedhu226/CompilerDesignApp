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
package cd.prog.codegen;

import static cd.prog.standalone.Reversed.reversed;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author yedhu
 */
public class Intermediate_Code_Gen {

    private Map<Integer, expression> code = new HashMap<>();

    static int get_Precedence(Character op) {
        if (op.equals('(')) {
            return 2;
        }
        if (op.equals('*') || op.equals('/')) {
            return 3;
        }
        if (op.equals('+') || op.equals('-')) {
            return 4;
        }
        return 1;
    }

    public static Queue<Character> prefixgen(String s) {
        Queue<Character> prefix;
        StringBuilder exptemp = new StringBuilder(s);
        exptemp.reverse();
        prefix = postfixgen(exptemp.toString());
        List<Character> temp=new LinkedList<>();
        temp.addAll(prefix);
        prefix.clear();
        for(Character e: reversed(temp)){
            prefix.add(e);
        }
        return prefix;
    }

    public static Queue<Character> postfixgen(String s) {
        Queue<Character> postfix = new LinkedList<>();
        Stack<Character> ops = new Stack<>();
        char exptemp[] = s.toCharArray();
        Queue<Character> exp = new LinkedList<>();
        for (char i : exptemp) {
            exp.add(i);
        }
        Character t = exp.poll();
        while (t != null) {
            if (Character.isLetter(t)) {
                postfix.add(t);
            } else {
                if (!ops.isEmpty()) {
                    while (get_Precedence(ops.peek()) <= get_Precedence(t)) {
                        if (get_Precedence(ops.peek()) != 1 || get_Precedence(ops.peek()) != 2) {
                            postfix.add(ops.pop());
                        } else {
                            ops.pop();
                        }
                        if (ops.isEmpty()) {
                            break;
                        }
                    }
                    if (!t.equals('(') && !t.equals(')')) {
                        ops.add(t);
                    }
                } else {
                    if (!t.equals('(') && !t.equals(')')) {
                        ops.add(t);
                    }
                }
            }
            t = exp.poll();
        }
        while (!ops.isEmpty()) {
            postfix.add(ops.pop());
        }
        return postfix;
    }
}
