package edu.neu.madcourse.numad21s_johnphilip;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LinkCollectorActivity extends AppCompatActivity implements LinkDialogFragment.LinkDialogListener {

    private List<ItemCard> links = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager rLayoutManager;

    private RViewAdapter rViewAdapter;
    private FloatingActionButton addButton;

    String SIZE_ID = "SIZE";
    String LINK_INSTANCE_ID = "LINK";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_collector);

        initialItemData(savedInstanceState);
        createRecyclerView();

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
    };

    private void initialItemData(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey(SIZE_ID)) {
            if (links == null || links.size() == 0) {
                int size = savedInstanceState.getInt(SIZE_ID);

                for (int i = 0; i < size; i++) {
                    String linkName = savedInstanceState.getString(LINK_INSTANCE_ID + i + "1");
                    String linkURL = savedInstanceState.getString(LINK_INSTANCE_ID + i + "2");
                    links.add(new ItemCard(linkName, linkURL));
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        int size = links == null ? 0 : links.size();
        outState.putInt(SIZE_ID, size);

        for (int i = 0; i < size; i++) {
            outState.putString(LINK_INSTANCE_ID + i + "1", links.get(i).getLinkName());
            outState.putString(LINK_INSTANCE_ID + i + "2", links.get(i).getLinkURL());
        }
        super.onSaveInstanceState(outState);
    }

    private void createRecyclerView() {

        rLayoutManager = new LinearLayoutManager(this);

        recyclerView = findViewById(R.id.link_recycler_view);
        recyclerView.setHasFixedSize(true);

        rViewAdapter = new RViewAdapter(links);

        LinkClickListener linkClickListener = new LinkClickListener() {
            @Override
            public void onLinkClick(String url) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        };
        rViewAdapter.setOnLinkClickListener(linkClickListener);
        recyclerView.setAdapter(rViewAdapter);
        recyclerView.setLayoutManager(rLayoutManager);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {

        Dialog dl = dialog.getDialog();

        String linkName = ((EditText) dl.findViewById(R.id.link_name)).getText().toString();
        String linkURL = ((EditText) dl.findViewById(R.id.link_url)).getText().toString();

        if (valid(linkURL)) {
            dialog.dismiss();
            linkURL = standardizeURL(linkURL);
            links.add(0, new ItemCard(linkName, linkURL));
            rViewAdapter.notifyItemInserted(0);
            View parentLayout = findViewById(android.R.id.content);
            Snackbar.make(parentLayout, R.string.link_add_confirmation, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            Toast.makeText(LinkCollectorActivity.this, R.string.link_add_invalid_url, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean valid(String url) {
        return url.length() > 4 &&
                Arrays.asList(new String[] { ".com", ".net", ".org", ".edu" }).contains(url.substring(url.length() - 4));
    }

    private String standardizeURL(String url) {
        if (url.endsWith("https://") || url.endsWith("http://")) {
            return url;
        } else {
            return "https://" + url;
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
       // do standard dialog cancelling.
    }
}
