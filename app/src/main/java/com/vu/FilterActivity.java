package com.vu;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.chip.Chip;
import android.support.design.chip.ChipGroup;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vu.Adapter.MyComicAdapter;
import com.vu.Common.Common;
import com.vu.Models.Comic;
import com.vu.comicreader.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Filter;

public class FilterActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private RecyclerView rcv_filter;
    private AutoCompleteTextView completeTextView;
    private ChipGroup chipGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        // recyclte filter
        rcv_filter = findViewById(R.id.rcv_filter);
        rcv_filter.setHasFixedSize(true);
        rcv_filter.setLayoutManager(new GridLayoutManager(this, 2));
        // bottom nav
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.inflateMenu(R.menu.main_mennu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_filter:
                        showFilterDialog();
                        break;
                    case R.id.action_search:
                        showSearchDialog();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    private void showFilterDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(FilterActivity.this);
        alertDialog.setTitle("Select Category");

        final LayoutInflater inflater = this.getLayoutInflater();
        View filter_layout = inflater.inflate(R.layout.dialog_option, null);
        completeTextView = (AutoCompleteTextView) filter_layout.findViewById(R.id.text_category);
        chipGroup = (ChipGroup) filter_layout.findViewById(R.id.chipGroup);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, Common.categories);
        completeTextView.setAdapter(adapter);
        completeTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                completeTextView.setText("");
                Chip chip = (Chip) inflater.inflate(R.layout.chip_item, null, false);
                chip.setText(((TextView) view).getText());
                chip.setOnCloseIconClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chipGroup.removeView(v);
                    }
                });
                chipGroup.addView(chip);
            }
        });
        alertDialog.setView(filter_layout);
        alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.setPositiveButton("FILTER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                List<String> filter_key = new ArrayList<>();
                StringBuilder filter_query = new StringBuilder();
                for (int j = 0; j < chipGroup.getChildCount(); j++) {
                    Chip chip = (Chip) chipGroup.getChildAt(j);
                    filter_key.add(chip.getText().toString());
                }
                Collections.sort(filter_key);
                for (String t : filter_key) {
                    filter_query.append(t).append(",");
                }
                filter_query.setLength(filter_query.length() - 1);
                fetchFilterCategory(filter_query.toString());
            }
        });
        alertDialog.show();
    }

    private void fetchFilterCategory(String category) {
        List<Comic> comic_filtered = new ArrayList<>();
        for (Comic comic : Common.comicList) {
            if (comic.getCategory() != null) {
                if (comic.getCategory().contains(category)) {
                    comic_filtered.add(comic);
                }
            }
        }
        if (comic_filtered.size() > 0) {
            rcv_filter.setAdapter(new MyComicAdapter(getBaseContext(), comic_filtered));
        } else {
            Toast.makeText(this, "No result", Toast.LENGTH_SHORT).show();
        }
    }

    private void showSearchDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(FilterActivity.this);
        alertDialog.setTitle("Search");
        final LayoutInflater inflater = this.getLayoutInflater();
        View search_layout = inflater.inflate(R.layout.dialog_search, null);
        final EditText editText = (EditText) search_layout.findViewById(R.id.edit_text);
        alertDialog.setView(search_layout);
        alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.setPositiveButton("SEARCH", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                fetchSearchCaterogy(editText.getText().toString());
            }
        });
        alertDialog.show();
    }

    private void fetchSearchCaterogy(String search) {
        List<Comic> comic_search = new ArrayList<>();
        for (Comic comic : Common.comicList) {
            if (comic.getName().contains(search)) {
                comic_search.add(comic);
            }
        }
        if (comic_search.size() > 0) {
            rcv_filter.setAdapter(new MyComicAdapter(getBaseContext(), comic_search));
        } else {
            Toast.makeText(this, "No result", Toast.LENGTH_SHORT).show();
        }
    }
}
