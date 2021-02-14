package edu.neu.madcourse.numad21s_johnphilip;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class LinkCollectorActivity extends AppCompatActivity implements LinkDialogFragment.LinkDialogListener {

    private List<ItemCard> links = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager rLayoutManger;
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

    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {

        Dialog dl = dialog.getDialog();
        // validation

        String link_name = ((EditText) dl.findViewById(R.id.link_name)).getText().toString();
        String link_url = ((EditText) dl.findViewById(R.id.link_url)).getText().toString();

        Log.v("Link Name", link_name);
        Log.v("Link URL", link_url);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
