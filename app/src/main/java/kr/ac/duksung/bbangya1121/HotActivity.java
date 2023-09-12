package kr.ac.duksung.bbangya1121;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class HotActivity extends AppCompatActivity implements MyRecyclerAdapter.MyRecyclerViewClickListener {

    public boolean swClick = false;
    ArrayList<String> items;
    ArrayList<String> places;
    ArrayList<ItemData> dataList = new ArrayList<>();
    RequestQueue requestQueue;
    MyRecyclerAdapter adapter = new MyRecyclerAdapter(dataList);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot);


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        items = new ArrayList<String>();
        places = new ArrayList<String>();
        recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(this);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        makeRequest();

    }

    public void makeRequest() {
        String url = "https://www.mangoplate.com/search/%EC%A0%84%EA%B5%AD%20%EB%B9%B5%EC%A7%91%20Top50";
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseHtml(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "network error",
                                Toast.LENGTH_LONG).show();
                    }
                }
        );
        request.setShouldCache(false);
        requestQueue.add(request);

    }


    @SuppressLint("NotifyDataSetChanged")
    public void parseHtml(String html) {
        Document doc = Jsoup.parse(html);
        dataList.removeAll(dataList);

        int [] img = {R.drawable.bread1, R.drawable.bread2, R.drawable.bread3, R.drawable.bread4, R.drawable.bread5, R.drawable.bread6, R.drawable.bread7,
                R.drawable.bread8,R.drawable.bread9, R.drawable.bread10, R.drawable.bread11, R.drawable.bread12, R.drawable.bread13, R.drawable.bread14 ,
                R.drawable.bread15, R.drawable.bread16, R.drawable.bread17,R.drawable.bread18,R.drawable.bread19, R.drawable.bread20};


        if(!swClick) {

            Elements itemElements = doc.select("a h2.title");
            Elements place = doc.select("p.etc");

            for(Element e : itemElements) {
                items.add(e.text().trim());
                android.util.Log.d("test: ", e.text());
            }

            for(Element e : place) {
                places.add(e.text().trim());
                android.util.Log.d("test: ", e.text());
            }

            for (int i=0; i<19; i++) {
                dataList.add(new ItemData (img[i], "  " + items.get(i),
                        "   " + places.get(i)));
            }

            swClick = true;

        } else {
            Elements itemElements = doc.select("a h2.title");
            Elements place = doc.select("p.etc");

            for(Element e : itemElements) {
                items.add(e.text().trim());
                android.util.Log.d("test: ", e.text());
            }

            for(Element e : place) {
                places.add(e.text().trim());
                android.util.Log.d("test: ", e.text());
            }
//20 6 9 13 14
// 19 15 3 8 10
//18 2 4 5 1
//7 16 17 11 12
            dataList.add(new ItemData (img[19], "  " + items.get(19),
                    "   " + places.get(19)));
            dataList.add(new ItemData (img[5], "  " + items.get(5),
                    "   " + places.get(5)));
            dataList.add(new ItemData (img[8], "  " + items.get(8),
                    "   " + places.get(8)));
            dataList.add(new ItemData (img[12], "  " + items.get(12),
                    "   " + places.get(12)));
            dataList.add(new ItemData (img[13], "  " + items.get(13),
                    "   " + places.get(13)));

            dataList.add(new ItemData (img[18], "  " + items.get(18),
                    "   " + places.get(18)));
            dataList.add(new ItemData (img[14], "  " + items.get(14),
                    "   " + places.get(14)));
            dataList.add(new ItemData (img[2], "  " + items.get(2),
                    "   " + places.get(2)));
            dataList.add(new ItemData (img[7], "  " + items.get(7),
                    "   " + places.get(7)));
            dataList.add(new ItemData (img[9], "  " + items.get(9),
                    "   " + places.get(9)));

            dataList.add(new ItemData (img[17], "  " + items.get(17),
                    "   " + places.get(17)));
            dataList.add(new ItemData (img[1], "  " + items.get(1),
                    "   " + places.get(1)));
            dataList.add(new ItemData (img[3], "  " + items.get(3),
                    "   " + places.get(3)));
            dataList.add(new ItemData (img[4], "  " + items.get(4),
                    "   " + places.get(4)));
            dataList.add(new ItemData (img[0], "  " + items.get(0),
                    "   " + places.get(0)));

            //7 16 17 11 12
            dataList.add(new ItemData (img[6], "  " + items.get(6),
                    "   " + places.get(6)));
            dataList.add(new ItemData (img[15], "  " + items.get(15),
                    "   " + places.get(15)));
            dataList.add(new ItemData (img[16], "  " + items.get(16),
                    "   " + places.get(16)));
            dataList.add(new ItemData (img[10], "  " + items.get(10),
                    "   " + places.get(10)));
            dataList.add(new ItemData (img[11], "  " + items.get(11),
                    "   " + places.get(11)));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClicked(int position) {

    }
}
