package ru.alxabr.newsview.Presenter;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;

import ru.alxabr.newsview.ContractMVP;
import ru.alxabr.newsview.Model.Wrapper.News;

public class MainPresenter implements ContractMVP.Presenter {
    private final String[] sources_url = {
            "https://tayga.info/rss",
            "https://nsk.aif.ru/rss/all.php",
            "https://news.ngs.ru/rss/"};

    private final String[] sources_name = {
            "тайга.инфо",
            "Аргументы и Факты",
            "NGS"};

    private ContractMVP.View view;
    private ContractMVP.Model model;
    private Context context;

    private ArrayList<News> newsList_full = new ArrayList<>();
    private ArrayList<News> newsList_curr = new ArrayList<>();
    private int last_position = -1;

    public MainPresenter(ContractMVP.View view, ContractMVP.Model model, Context context) {
        this.view = view;
        this.model = model;
        this.context = context;
    }

    @Override
    public void showNewsList() {
        view.showBigLoad();

    }

    @Override
    public void updateNewsList() {

    }

    class TaskForLoad extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }
    }
}
