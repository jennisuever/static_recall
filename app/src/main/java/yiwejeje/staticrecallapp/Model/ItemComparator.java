package yiwejeje.staticrecallapp.Model;

import java.util.Comparator;

import yiwejeje.staticrecallapp.Model.Item;

/**
 * Created by el-swug on 3/13/16.
 */
public class ItemComparator implements Comparator<Item> {

    @Override
    public int compare(final Item item1, final Item item2) {
        return item1.getName().toLowerCase()
                .compareTo(item2.getName().toLowerCase());
    }
}