package gridentertainment.net.popularmovies;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIHelper {

    @GET("movie/top_rated")
    Call<MovieHelper> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/popular")
    Call<MovieHelper> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}/videos")
    Call<TrailerHelper> getVideos(@Path("id") int movieID, @Query("api_key") String apiKey);

    @GET("movie/{id}/reviews")
    Call<ReviewsHelper> getReviews(@Path("id") int movieID, @Query("api_key") String apiKey);
}