package ru.alxabr.newsview.Model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import ru.alxabr.newsview.ContractMVP;
import ru.alxabr.newsview.Model.Wrapper.News;

public class MainModel implements ContractMVP.Model {
    private final String ITEM = "item";
    private final String ENCLOSURE= "enclosure";
    private final String TITLE = "title";
    private final String DESCRIPTION = "description";
    private final String PUB_DATE = "pubDate";

    public ArrayList<News> readRss(String feedUrl, String source)
            throws IOException, ParserConfigurationException, SAXException {
        ArrayList<News> newsList = new ArrayList<>();

        URL url = new URL(feedUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStream is = conn.getInputStream();

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document document = db.parse(is);
            Element element = document.getDocumentElement();

            NodeList nodeList_items = element.getElementsByTagName(ITEM);
            NodeList nodeList_enclosure = element.getElementsByTagName(ENCLOSURE);

            if (nodeList_items.getLength() > 0) {
                for (int i = 0; i < nodeList_items.getLength(); i++) {
                    Element entry = (Element) nodeList_items.item(i);

                    Element _titleE = (Element) entry.getElementsByTagName(
                            TITLE).item(0);
                    Element _descriptionE = (Element) entry
                            .getElementsByTagName(DESCRIPTION).item(0);
                    Element _pubDateE = (Element) entry
                            .getElementsByTagName(PUB_DATE).item(0);

                    Node item = nodeList_enclosure.item(i);
                    NamedNodeMap attr = item.getAttributes();

                    String title = _titleE.getFirstChild().getNodeValue();
                    String description = _descriptionE.getFirstChild().getNodeValue();
                    Date pubDate = new Date(_pubDateE.getFirstChild().getNodeValue());
                    String image_url = attr.getNamedItem("url").getNodeValue();

                    News news = new News(title, description, pubDate, image_url, source);

                    newsList.add(news);
                }
            }
        }

        return newsList;
    }

    @Override
    public ArrayList<News> sortByDate(ArrayList<News> newsList) {
        ArrayList<News> arrayList = new ArrayList<>();
        arrayList.addAll(newsList);
        quickSort(arrayList, 0, arrayList.size()-1);
        return arrayList;
    }

    private static void quickSort(ArrayList<News> array, int low, int high) {
        if (array.size() == 0)
            return;//завершить выполнение если длина массива равна 0

        if (low >= high)
            return;//завершить выполнение если уже нечего делить

        // выбрать опорный элемент
        int middle = low + (high - low) / 2;
        long opora = array.get(middle).getPubDate().getTime();

        // разделить на подмассивы, который больше и меньше опорного элемента
        int i = low, j = high;
        while (i <= j) {
            while (array.get(i).getPubDate().getTime() > opora) {
                i++;
            }

            while (array.get(j).getPubDate().getTime() < opora) {
                j--;
            }

            if (i <= j) {//меняем местами
                News temp_i = array.get(i);
                News temp_j = array.get(j);

                array.remove(i);
                array.add(i, temp_j);

                array.remove(j);
                array.add(j, temp_i);
                i++;
                j--;
            }
        }

        // вызов рекурсии для сортировки левой и правой части
        if (low < j)
            quickSort(array, low, j);

        if (high > i)
            quickSort(array, i, high);
    }
}
