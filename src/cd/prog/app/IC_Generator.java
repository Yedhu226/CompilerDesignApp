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
package cd.prog.app;

import cd.prog.codegen.Intermediate_Code_Gen;
import java.util.Scanner;

/**
 *
 * @author yedhu
 */
public class IC_Generator {
    public static void main(){
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter expression: ");
        String s=sc.nextLine();
        Intermediate_Code_Gen c=new Intermediate_Code_Gen(s);
        c.print();
    }
}
