package edu.neu.madcourse.numad21s_johnphilip;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class LinkCollectorActivity extends AppCompatActivity implements LinkDialogFragment.LinkDialogListener {

    private List<ItemCard> links = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager rLayoutManager;

    private RViewAdapter rViewAdapter;
    private FloatingActionButton addButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_collector);

        init(savedInstanceState);

        addButton = findViewById(R.id.add_link_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLinkDialog();
            }
        });
    }

    public void showLinkDialog() {
        DialogFragment dialog = new LinkDialogFragment();
        dialog.show(getSupportFragmentManager(), "LinkDialogFragment");
    }

    private void init(Bundle savedInstanceState) {
        initialItemData(savedInstanceState);
        createRecyclerView();
    }

    private void initialItemData(Bundle savedInstanceState) {

    }

    private void createRecyclerView() {

        rLayoutManager = new LinearLayoutManager(this);

        recyclerView = findViewById(R.id.link_recycler_view);
        recyclerView.setHasFixedSize(true);

        rViewAdapter = new RViewAdapter(links);

        ItemClickListener itemClickListener = new ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //attributions bond to the item has been changed
                links.get(position).onItemClick(position);

                rViewAdapter.notifyItemChanged(position);
            }
        };
        rViewAdapter.setOnItemClickListener(itemClickListener);

        recyclerView.setAdapter(rViewAdapter);
        recyclerView.setLayoutManager(rLayoutManager);


    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {

        Dialog dl = dialog.getDialog();
        // validation

        String linkName = ((EditText) dl.findViewById(R.id.link_name)).getText().toString();
        String linkURL = ((EditText) dl.findViewById(R.id.link_url)).getText().toString();

        links.add(0, new ItemCard(linkName, linkURL));
        rViewAdapter.notifyItemInserted(0);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
