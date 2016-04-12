package yiwejeje.staticrecallapp.Activity;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import yiwejeje.staticrecallapp.Model.Item;
import yiwejeje.staticrecallapp.Model.ItemCategory;
import yiwejeje.staticrecallapp.R;

/**
 * Code taken from
 * http://www.androidhive.info/2013/07/android-expandable-list-view-tutorial/.
 */

public class CategoryListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<ItemCategory>  itemCategories;

    public CategoryListAdapter(Context context, List<ItemCategory> itemCategories) {
        this._context = context;
        if (itemCategories == null) {
            throw new IllegalArgumentException("itemCategories cannot be null");
        }
        this.itemCategories = itemCategories;
    }

    public void setSingleCategory(ItemCategory aCategory) {
        itemCategories = new ArrayList<>(1);
        itemCategories.add(aCategory);
        this.notifyDataSetChanged();
    }

    public void setItemCategories (List<ItemCategory> itemCategories) {
        this.itemCategories = itemCategories;
        this.notifyDataSetChanged();
    }

    @Override
    public Object getChild(int categoryPosition, int itemPosition) {
        ItemCategory itemCategory = itemCategories.get(categoryPosition);
        Item item = itemCategory.getItems().get(itemPosition);
        return item;
    }

    @Override
    public long getChildId(int categoryPosition, int itemPosition) {
        return itemPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        Item item = (Item) getChild(groupPosition, childPosition);
        final String childText = item.getName();

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);

        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int categoryPosition) {
        ItemCategory itemCategory = itemCategories.get(categoryPosition);
        return itemCategory.getItems().size();
    }

    @Override
    public Object getGroup(int categoryPosition) {
        return this.itemCategories.get(categoryPosition);
    }

    @Override
    public int getGroupCount() {
        return this.itemCategories.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        ItemCategory itemCategory = (ItemCategory) this.getGroup(groupPosition);
        String headerTitle = itemCategory.getName();

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    public boolean addItemCategoriesWithACategory(ItemCategory aCategory) {
        if(aCategory == null) {
            throw new IllegalArgumentException("aCategory is null");
        }
        return itemCategories.add(aCategory);
    }

    public boolean removeAnItemCategory(ItemCategory itemCategory) {
        return itemCategories.remove(itemCategory);
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}