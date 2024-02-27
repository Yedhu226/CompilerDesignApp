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

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author yedhu
 */
public class Production {

    private String Symbol;
    private List<String> Prods = new LinkedList<>();

    public Production(String in){
        String[] a=in.split(">");
        this.Symbol=a[0];
        String b=a[1];
        a=b.split("\\|");
        this.Prods.addAll(Arrays.asList(a));
    }

    public String getSymbol() {
        return Symbol;
    }

    public void setSymbol(String Symbol) {
        this.Symbol = Symbol;
    }

    public List<String> getProds() {
        return Prods;
    }

    public void setProds(List<String> Prods) {
        this.Prods = Prods;
    }
}
