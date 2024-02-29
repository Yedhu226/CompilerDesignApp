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

import java.util.Map;

/**
 * This is the Grammar class which allows objects to be created to describe a
 * grammar with set rules.
 *
 * @author yedhu
 */
public class Grammar {

    private final Map<Element, Rule> Rule_List;
    private final Element Start_Symbol;

    public Element getStart_Symbol() {
        return Start_Symbol;
    }

    public Map<Element,Rule> getRule_List() {
        return Rule_List;
    }
    
    public Grammar(Element start, Map<Element, Rule> rules){
        this.Start_Symbol=start;
        this.Rule_List=rules;
    }
}
