package ru.alxabr.newsview;

import java.util.ArrayList;

import ru.alxabr.newsview.Model.Wrapper.News;

public interface ContractMVP {
    interface View {
        void showBigLoad();
        void hideBigLoad();
        void showError();
        void hideError();
        void updateNewsList();
        void showUpdateMessage();
    }

    interface Presenter {
        void showNewsList();
        void updateNewsList();
    }

    interface Model {
        ArrayList<News> readRss();
    }
}
