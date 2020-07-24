package ru.alxabr.newsview.Presenter;

import android.content.Context;

import ru.alxabr.newsview.ContractMVP;

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

    public MainPresenter(ContractMVP.View view, ContractMVP.Model model, Context context) {
        this.view = view;
        this.model = model;
        this.context = context;
    }

    @Override
    public void showNewsList() {

    }

    @Override
    public void updateNewsList() {

    }
}
