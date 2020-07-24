package ru.alxabr.newsview.View.Adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ru.alxabr.newsview.Model.Wrapper.News;
import ru.alxabr.newsview.R;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{
    private LayoutInflater inflater;
    private List<News> newsList;
    private Context mainContext;

    public ListAdapter(Context context, List<News> newsList) {
        this.mainContext = context;
        this.newsList = newsList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListAdapter.ViewHolder holder, int position) {
        News news = newsList.get(position);

        holder.title.setText(Html.fromHtml(news.getTitle()));
        Picasso.get().load(news.getImage_url()).into(holder.imageView);
        holder.description.setText(Html.fromHtml(news.getDescription()));

        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd.MM.yy   HH:mm", Locale.getDefault());
        String str = news.getSource() + "   " + newDateFormat.format(news.getPubDate());
        holder.source_date.setText(str);

//        String mDrawableName = "non_image";
//        int resID = mainContext.getResources().getIdentifier(mDrawableName ,
//                "drawable", mainContext.getPackageName());
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public void addNews(ArrayList<News> list) {
        newsList.addAll(list);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView imageView;
        private TextView description;
        private TextView source_date;

        ViewHolder(View view){
            super(view);

            title = view.findViewById(R.id.main_title);
            imageView = view.findViewById(R.id.main_image);
            description = view.findViewById(R.id.main_description);
            source_date = view.findViewById(R.id.main_source_date);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(description.getVisibility() == View.GONE)
                        description.setVisibility(View.VISIBLE);
                    else
                        description.setVisibility(View.GONE);
                }
            });
        }
    }
}
