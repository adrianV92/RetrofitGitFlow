package pe.edu.cibertec.retrofitgitflow.paid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import pe.edu.cibertec.retrofitgitflow.JsonPlaceHolderApi;
import pe.edu.cibertec.retrofitgitflow.Post;
import pe.edu.cibertec.retrofitgitflow.PostAdapter;
import pe.edu.cibertec.retrofitgitflow.R;
import pe.edu.cibertec.retrofitgitflow.TriggerClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostDetailActivity extends AppCompatActivity {

    private TextView tvPostBody;
    private TextView tvPostTitle;
    private int postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        tvPostTitle= findViewById(R.id.tvPostTitle);
        tvPostBody= findViewById(R.id.tvPostBody);
        Intent intent = getIntent();
        postId= intent.getIntExtra("post_id",-1);
        callService();
    }

    private void callService() {
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build() ;
        JsonPlaceHolderApi jsonPlaceHolderApi= retrofit.create(JsonPlaceHolderApi.class);
        Call<Post> call= jsonPlaceHolderApi.getPostId(postId);
        call.enqueue(new Callback<Post>(){

                         @Override
                         public void onResponse(Call<Post> call, Response<Post> response) {
                             if (!response.isSuccessful()){
                                 //tvResult.setText("Code: " + response.code());

                             }else{
                                 final Post posts= response.body();
                                 tvPostBody.setText(posts.getText());
                                 tvPostTitle.setText(posts.getTitle());
                             }
                         }

                         @Override
                         public void onFailure(Call<Post> call, Throwable t) {
                             //tvResult.setText(t.getMessage());
                         }
                     }
        );
    }
}
