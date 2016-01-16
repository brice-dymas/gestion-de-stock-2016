/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.web.util;

import java.util.Iterator;
import java.util.List;
import org.apache.commons.collections.Factory;
import org.apache.commons.collections.list.LazyList;

/**
 *
 * @author gervais
 */
public class ShrinkableLazyList extends LazyList {

    protected ShrinkableLazyList(List list, Factory factory) {
        super(list, factory);
    }

    /**
     * Decorates list with shrinkable lazy list.
     * @param list
     * @param factory
     * @return 
     */
    public static List decorate(List list, Factory factory) {
        return new ShrinkableLazyList(list, factory);
    }

    public void shrink() {
        for (Iterator i = getList().iterator(); i.hasNext();) {
            if (i.next() == null) {
                i.remove();
            }
        }
    }

    @Override
    public Iterator iterator() {
        shrink();
        return super.iterator();
    }

}
