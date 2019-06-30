package pe.edu.cibertec.retrofitgitflow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    //private TextView tvResult;
    private RecyclerView rvResult;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //tvResult= findViewById(R.id.tvResult);
        rvResult= findViewById(R.id.rvResult);
        layoutManager = new LinearLayoutManager(this);
        rvResult.setLayoutManager(layoutManager);

        mAdapter = new PostAdapter(new ArrayList<Post>());
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
                                 List<Post> posts= response.body();
                                 mAdapter = new PostAdapter(posts);
                                 rvResult.setAdapter(mAdapter);
                                 for (Post post : posts){
                                     String content="";
                                     content+= "Id: "+ post.getId()+"\n";
                                     content+= "userId: "+ post.getUserId()+"\n";
                                     content+= "Title: "+ post.getTitle()+"\n";
                                     content+= "Body: "+ post.getText()+"\n";
                                     //tvResult.append(content);
                                 }
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
