package ru.alxabr.newsview;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import ru.alxabr.newsview.Model.Wrapper.News;

public interface ContractMVP {
    interface View {
        void showBigLoad();
        void hideBigLoad();
        void showError();
        void hideError();
        void updateNewsList(ArrayList<News> newsArrayList);
        void showUpdateMessage();
    }

    interface Presenter {
        void showNewsList();
        void updateNewsList();
    }

    interface Model {
        ArrayList<News> readRss(String feedUrl, String source) throws IOException, ParserConfigurationException, SAXException;
        ArrayList<News> sortByDate(ArrayList<News> newsList);
    }
}
