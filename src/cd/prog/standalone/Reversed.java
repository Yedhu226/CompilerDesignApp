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
package cd.prog.standalone;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author yedhu
 * @param <T>
 */
public class Reversed<T> implements Iterable<T>{
    private final List<T> original;
    
    public Reversed(List<T> original){
        this.original=original;
    }
    
    @Override
    public Iterator<T> iterator(){
        final ListIterator<T> i=original.listIterator(original.size());
        return new Iterator<T>(){
            @Override
            public boolean hasNext(){return i.hasPrevious();}
            @Override
            public T next() { return i.previous();}
            @Override
            public void remove(){ i.remove();}
        };
    }
    
    public static <T> Reversed<T> reversed(List<T> original){
        return new Reversed<T> (original);
    }

}
