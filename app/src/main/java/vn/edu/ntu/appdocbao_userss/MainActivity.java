package vn.edu.ntu.appdocbao_userss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView lvTieuDe;
    ArrayList<String> arrayTitle, arrayLink;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvTieuDe = findViewById(R.id.listviewTieuDe);
        arrayTitle = new ArrayList<>();
        arrayLink = new ArrayList<>();

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayTitle);
        lvTieuDe.setAdapter(adapter);

        new ReadRSS().execute("https://vnexpress.net/rss/giai-tri.rss");
        lvTieuDe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, NewSActivity.class);
                intent.putExtra("linkTinTuc", arrayLink.get(position));
                startActivity(intent);
            }
        });
    }

    private class ReadRSS extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... strings) {
            //bien chua du lieu
            StringBuilder content = new StringBuilder();
            try {
                //khoi tao duong dan
                URL url = new URL(strings[0]);
                //lay du lieu
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                //
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                //bien chua du lieu
                String line ="";
                //dat duoc dong tiep theo
                while ((line = bufferedReader.readLine())!=null){
                    content.append(line);
                }
                bufferedReader.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            XMLDOMParser parser = new XMLDOMParser();
            //chứa toàn bộ nội dung RSS,
            Document document = parser.getDocument(s);
            //chứa ds item từ RSS
            NodeList nodeList = document.getElementsByTagName("item");

            String tieuDe = "" ;

            for (int i = 0; i < nodeList.getLength() ; i++ )
            {
                Element element = (Element) nodeList.item(i);
                //emement: node thứ bao nhiêu, tên: title
                tieuDe = parser.getValue(element,"title");
                arrayTitle.add(tieuDe);
                arrayLink.add(parser.getValue(element, "link"));
            }
            //cập nhật lại dữ liệu đã thay đổi
            adapter.notifyDataSetChanged();

        }
    }

}
