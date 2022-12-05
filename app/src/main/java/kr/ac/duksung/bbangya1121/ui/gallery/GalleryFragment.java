package kr.ac.duksung.bbangya1121.ui.gallery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import kr.ac.duksung.bbangya1121.R;

public class GalleryFragment extends AppCompatActivity {

    String [] professors = {"바게뜨", "소금빵", "크림빵", "식빵","구움과자"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_gallery);

        ListView professorList = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, professors);

        professorList.setAdapter(adapter);

        professorList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), professors[i], Toast.LENGTH_LONG).show();

            }
        });
    }
}