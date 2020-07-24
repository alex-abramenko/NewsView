package ru.alxabr.newsview.Presenter;

import android.content.Context;
import android.os.AsyncTask;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import ru.alxabr.newsview.ContractMVP;
import ru.alxabr.newsview.Model.MainModel;
import ru.alxabr.newsview.Model.Wrapper.News;

public class MainPresenter implements ContractMVP.Presenter {
    private final String[] sources_url = {
            "https://tayga.info/rss",
            "https://news.rambler.ru/rss/head/?limit=100",
            "https://www.vesti.ru/vesti.rss"};

    private final String[] sources_name = {
            "тайга.инфо",
            "Рамблер/Новости",
            "Вести.RU"};

    private ContractMVP.View view;
    private ContractMVP.Model model;
    private Context context;

    private ArrayList<News> newsList_full = new ArrayList<>();
    private ArrayList<News> newsList_curr = new ArrayList<>();
    private int count_items = 10;
    private int last_position = 0;

    public MainPresenter(ContractMVP.View view, Context context) {
        this.view = view;
        this.model = new MainModel();
        this.context = context;
    }

    @Override
    public void showNewsList() {
        view.hideError();
        view.showBigLoad();
        new TaskForLoad().execute();
    }

    @Override
    public void updateNewsList() {
        if (newsList_full.size() >= last_position + count_items - 1) {
            view.showBigLoad();
            for (int i = last_position; i < last_position + count_items; i++) {
                newsList_curr.add(newsList_full.get(i));
            }

            last_position += count_items;
            view.showUpdateMessage();
            view.hideBigLoad();
            view.updateNewsList(newsList_curr);
        }
    }

    class TaskForLoad extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                for (int i = 0; i < sources_url.length; i++) {
                    newsList_full.addAll(model.readRss(sources_url[i], sources_name[i]));
                }
                return false;
            }
            catch (IOException | SAXException | ParserConfigurationException e) {
                e.printStackTrace();
                return true;
            }
        }

        @Override
        protected void onPostExecute(Boolean isError) {
            super.onPostExecute(isError);

            if (isError) {
                view.showError();
            } else {
                ArrayList<News> sortedList = model.sortByDate(newsList_full);
                newsList_full.clear();
                newsList_full.addAll(sortedList);

                if (newsList_full.size() >= last_position + count_items - 1) {
                    for (int i = last_position; i < last_position + count_items; i++) {
                        newsList_curr.add(newsList_full.get(i));
                    }

                    last_position += count_items;
                    view.showUpdateMessage();
                    view.hideBigLoad();
                    view.updateNewsList(newsList_curr);
                }
            }
        }
    }
}
