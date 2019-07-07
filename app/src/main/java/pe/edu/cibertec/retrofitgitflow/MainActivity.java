package pe.edu.cibertec.retrofitgitflow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import pe.edu.cibertec.retrofitgitflow.TriggerClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    //private TextView tvResult;
    private RecyclerView rvResult;
    private PostAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Post> postList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //tvResult= findViewById(R.id.tvResult);
        rvResult= findViewById(R.id.rvResult);
        layoutManager = new LinearLayoutManager(this);
        rvResult.setLayoutManager(layoutManager);
        postList = new ArrayList<>();
        mAdapter = new PostAdapter(postList);
        rvResult.setAdapter(mAdapter);

        callService();
    }

    private void callService() {
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build() ;
        JsonPlaceHolderApi jsonPlaceHolderApi= retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Post>> call= jsonPlaceHolderApi.getPosts();
        call.enqueue(new Callback<List<Post>>(){

                         @Override
                         public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                             if (!response.isSuccessful()){
                                 //tvResult.setText("Code: " + response.code());

                             }else{
                                 final List<Post> posts= response.body();
                                 mAdapter = new PostAdapter(posts);
                                 mAdapter.setOnItemClickListener(new PostAdapter.ClickListener() {
                                     @Override
                                     public void onItemClick(int position) {
                                         TriggerClick.selectItem(posts.get(position).getId(), MainActivity.this);
                                     }
                                 });
                                 rvResult.setAdapter(mAdapter);

                             }
                         }

                         @Override
                         public void onFailure(Call<List<Post>> call, Throwable t) {
                            //tvResult.setText(t.getMessage());
                         }
                     }
        );
    }
}
