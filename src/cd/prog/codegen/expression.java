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

import java.util.Objects;

/**
 *
 * @author yedhu
 */
public class expression {

    private String result;
    private Character op;
    private Character arg1;
    private Character arg2 = null;

    public expression(String result, Character arg1, Character op, Character arg2) {
        this.result = result;
        this.op = op;
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    public expression(String result, Character op, Character arg1) {
        this.result = result;
        this.op = op;
        this.arg1 = arg1;
    }

    @Override
    public String toString() {
        String arg = "";
        String arga = this.arg1.toString();
        String argb = "";
        if (arg2 != null) {
            argb = this.arg2.toString();
        }
        if (Character.isUpperCase(arg1)) {
            char x = arg1;
            arga = "t" + ((int) (x) - 65);
        }
        if (arg2 != null && Character.isUpperCase(arg2)) {
            char x = arg2;
            argb = "t" + ((int) (x) - 65);
        }
        if (arg2 != null) {
            return result + "=" + arga + op + argb;
        }
        if (op.equals('=')) {
            return result + "" + op + arga;
        }
        return result + "= (u" + op + ") " + arga;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.result);
        hash = 79 * hash + Objects.hashCode(this.op);
        hash = 79 * hash + Objects.hashCode(this.arg1);
        return hash;
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
        final expression other = (expression) obj;
        if (!Objects.equals(this.result, other.result)) {
            return false;
        }
        if (!Objects.equals(this.op, other.op)) {
            return false;
        }
        if (!Objects.equals(this.arg1, other.arg1)) {
            return false;
        }
        if (this.arg2 == null || other.arg2 == null) {
            return false;
        }
        return true;
    }

}
