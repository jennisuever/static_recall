package yiwejeje.staticrecallapp.Activity;

import android.app.ActionBar;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import yiwejeje.staticrecallapp.Model.CategoryManager;
import yiwejeje.staticrecallapp.Model.Item;
import yiwejeje.staticrecallapp.R;

/**
 * Created by Static Recall Heroes on 4/8/16.
 */
public class ListViewSearchActivity extends AppCompatActivity {

    CategoryManager categoryManager = CategoryManager.INSTANCE;

    List<Item> itemResultsList = new ArrayList<Item>();
    ListView listView;
    ArrayAdapter<Item> listAdapter;

    MediaPlayer mp = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureView();
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    private void configureView() {
        setContentView(R.layout.activity_search_view_list);

        // initiate the listadapter
        listAdapter = new ArrayAdapter <Item>(this,
                R.layout.list_item, R.id.lblListItem, new ArrayList<Item>(categoryManager.getAllItems()));

        listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListViewSearchActivity.this, SearchLocationScreen.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.options_menu, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        // these are declared final because they are used in an inner class
        // Android Studio said so.
        final MenuItem searchMenu = menu.findItem(R.id.search);
        final SearchView searchView =
                (SearchView) searchMenu.getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        // http://www.androidhub4you.com/2014/04/android-action-bar-search-inside.html
        SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextChange(String newText)
            {
                // this is your adapter that will be filtered
                listAdapter.getFilter().filter(newText);
                System.out.println("on text chnge text: "+newText);
                return true;
            }
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                // this is your adapter that will be filtered
                listAdapter.getFilter().filter(query);
                System.out.println("on query submit: "+query);
                return true;
            }
        };
        searchView.setOnQueryTextListener(textChangeListener);

        return true;
    }

    public boolean loadCategories () {
        // TODO: implement

        System.out.println("------> Attempt to reload categories!");
        //listAdapter.setItemCategories(new ArrayList<>(categoryManager.getAllCategories()));
        System.out.println("------> Item Categories: " + categoryManager.getAllCategories());
        //expListView.collapseGroup(0);
        return true;
    }

    private void showResults() {
        // TODO: implement
//        itemResultsList.setItems(itemsResultsList);
//        listAdapter.
//        refreshList();
    }

    public void refreshList() {
        listAdapter.notifyDataSetChanged();
    }

    public void searchItems (String query) {
        // TODO: should I have this?

        String regexQuery = "(.*)" + query.toLowerCase() + "(.*)";

        this.itemResultsList.clear();
        boolean foundMatch = false;
        for (Item item : categoryManager.getAllItems()) {
            foundMatch = Pattern.matches(regexQuery, item.getName().toLowerCase());
            if (foundMatch) {
                this.itemResultsList.add(item);
            }
        }
        System.out.println("------> Results: " + this.itemResultsList);
    }
}