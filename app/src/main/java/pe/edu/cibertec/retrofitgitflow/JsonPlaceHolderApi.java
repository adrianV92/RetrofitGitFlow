package pe.edu.cibertec.retrofitgitflow;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JsonPlaceHolderApi {
    @GET("posts")
    Call<List<Post>>getPosts();

    @GET("posts/{id}")
    Call<Post>getPostId(@Path("id") int postid);
}
