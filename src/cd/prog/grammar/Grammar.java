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
 * This is the Grammar class which allows objects to be created to describe a
 * grammar with set rules.
 *
 * @author yedhu
 */
public class Grammar {

    private List<Rule> Rule_List = new LinkedList<>();
    private Element Start_Symbol = new Element('S');

    public List<Rule> getRule_List() {
        return Rule_List;
    }

    public void setRule_List(List<Rule> Rule_List) {
        this.Rule_List = Rule_List;
    }

}
