package edu.neu.madcourse.numad21s_johnphilip;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        String linkName = ((EditText) dl.findViewById(R.id.link_name)).getText().toString();
        String linkURL = ((EditText) dl.findViewById(R.id.link_url)).getText().toString();

        if (validateURL(linkURL)) {
            links.add(0, new ItemCard(linkName, linkURL));
            rViewAdapter.notifyItemInserted(0);
            View parentLayout = findViewById(android.R.id.content);
            Snackbar.make(parentLayout, R.string.link_add_confirmation, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            Toast.makeText(LinkCollectorActivity.this, R.string.link_add_invalid_url, Toast.LENGTH_SHORT).show();
        }
    }

    // returns true if URL is valid
    private boolean validateURL(String url) {
        Pattern pattern = Pattern.compile("((([A-Za-z]{3,9}:(?:\\/\\/)?)(?:[-;:&=\\+\\$,\\w]+@)?[A-Za-z0-9.-]+|(?:www.|[-;:&=\\+\\$,\\w]+@)[A-Za-z0-9.-]+)((?:\\/[\\+~%\\/.\\w-_]*)?\\??(?:[-\\+=&;%@.\\w_]*)#?(?:[\\w]*))?)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(url);
        return matcher.find();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
