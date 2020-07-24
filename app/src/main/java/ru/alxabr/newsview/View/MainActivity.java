package ru.alxabr.newsview.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ru.alxabr.newsview.ContractMVP;
import ru.alxabr.newsview.Model.Wrapper.News;
import ru.alxabr.newsview.Presenter.MainPresenter;
import ru.alxabr.newsview.R;
import ru.alxabr.newsview.View.Adapter.ListAdapter;

public class MainActivity extends AppCompatActivity implements ContractMVP.View {
    private ContractMVP.Presenter presenter;

    private RecyclerView recyclerView;
    private LinearLayout layout_big_load;
    private TextView txtView_error;
    private Button btn_refresh;

    private Context thisContext;
    private LinearLayoutManager layoutManager;
    private Parcelable mLayoutManagerState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        layout_big_load = findViewById(R.id.layout_big_load);
        txtView_error = findViewById(R.id.error);
        btn_refresh = findViewById(R.id.refresh);

        thisContext = this;

        layoutManager = new LinearLayoutManager(thisContext);
        recyclerView.setLayoutManager(layoutManager);

        presenter = new MainPresenter(this, thisContext);
        presenter.showNewsList();

        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.showNewsList();
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE) {
                    mLayoutManagerState = recyclerView.getLayoutManager().onSaveInstanceState();
                    presenter.updateNewsList();
                }
            }
        });
    }

    @Override
    public void showError() {
        recyclerView.setVisibility(View.GONE);
        layout_big_load.setVisibility(View.GONE);
        txtView_error.setVisibility(View.VISIBLE);
        btn_refresh.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideError() {
        recyclerView.setVisibility(View.VISIBLE);
        layout_big_load.setVisibility(View.GONE);
        txtView_error.setVisibility(View.GONE);
        btn_refresh.setVisibility(View.GONE);
    }

    @Override
    public void showBigLoad() {
        recyclerView.setVisibility(View.GONE);
        layout_big_load.setVisibility(View.VISIBLE);
        txtView_error.setVisibility(View.GONE);
        btn_refresh.setVisibility(View.GONE);
    }

    @Override
    public void hideBigLoad() {
        recyclerView.setVisibility(View.VISIBLE);
        layout_big_load.setVisibility(View.GONE);
        txtView_error.setVisibility(View.GONE);
        btn_refresh.setVisibility(View.GONE);
    }

    @Override
    public void showUpdateMessage() {
        Toast toast = Toast.makeText(thisContext,
                "News are loaded. Scroll...",
                Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void updateNewsList(ArrayList<News> newsArrayList) {
        recyclerView.setAdapter(new ListAdapter(thisContext, newsArrayList));
        if (mLayoutManagerState != null) {
            recyclerView.getLayoutManager().onRestoreInstanceState(mLayoutManagerState);
        }
        //recyclerView.getAdapter().notifyDataSetChanged();
    }
}
