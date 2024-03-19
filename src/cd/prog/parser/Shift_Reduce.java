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
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author yedhu
 */
public class Shift_Reduce {

    private Grammar G;

    public Shift_Reduce(Grammar G) {
        this.G = G;
    }

    public void ParseString(String in) {
        String act="";
        Queue<Element> input = new LinkedList<>();
        Stack<Element> stack = new Stack<>();
        for (int i = 0; i < in.length(); i++) {
            input.add(Element.create(in.charAt(i)));
        }
        System.out.printf("\t| \t %-10s \t | \t %-10s \t | \t %-10s \t |\t\n","Stack","Input","Action");
        System.out.printf("\t|-\t%-10s\t-|-\t%-10s\t-|-\t%-10s\t-|\t\n","-------","-------","-------");
        while (true) {
            if (stack.isEmpty()) {
                act="Shift";
                System.out.printf("\t| \t %-10s \t | \t %-10s \t | \t %-10s \t |\t\n",stack,input,act);
                stack.push(input.poll());
                
            }
            Queue<Element> temp = new LinkedList();
            Element e = G.getStart_Symbol();
            boolean error = true;
            loop:
            for (Rule r : G.getRule_List().values()) {
                for (List<Element> l : r.getProductions()) {
                    temp = new LinkedList<>(stack);
                    while (!temp.isEmpty()) {
                        if (l.equals(temp)) {
                            e = r.getGen_Symbol();
                            error = false;
                            break loop;
                        }
                        temp.poll();
                    }
                }
            }
            if (error==false) {
                act="Reduce";
                System.out.printf("\t| \t %-10s \t | \t %-10s \t | \t %-10s \t |\t\n",stack,input,act);
                stack.removeAll(temp);
                stack.push(e);
                continue;
            } 
            if (stack.size() == 1 && stack.peek() == G.getStart_Symbol() && input.isEmpty()) {
                act="Accepted";
                System.out.printf("\t| \t %-10s \t | \t %-10s \t | \t %-10s \t |\t\n",stack,input,act);
                System.out.println("String Accepted");
                break;
            }
            if(input.isEmpty()){
                act="Reduce";
                System.out.printf("\t| \t %-10s \t | \t %-10s \t | \t %-10s \t |\t\n",stack,input,act);
                stack.removeAll(temp);
                stack.push(e);
            }
            if (error==true && input.isEmpty()) {
                System.out.println("String not Accepted");
                break;
            }
            if(!input.isEmpty()){
                act="Shift";
                System.out.printf("\t| \t %-10s \t | \t %-10s \t | \t %-10s \t |\t\n",stack,input,act);
                stack.push(input.poll());
                continue;
            }
        }
    }
}
