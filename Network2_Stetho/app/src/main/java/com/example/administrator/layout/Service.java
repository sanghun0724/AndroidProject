package com.example.administrator.layout;

import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

//통신 메소드 기능은 class가아닌 interface로
public interface Service {
    //이제 여기서 베이스url말고 변경되는부분 작성
    //https://api.github.com/users/{user}/repos {user}이부분은 다른 스트링으로 대체될거다를 의미함
    //https://api.github.com/users/abc/repos {user} //밑에 userName 에 abc를 만약 넣는다면  이 주소로 이동!


    @GET("users/{user}/repos") //get은 데이터요청 레트로핏 라이브러리
    //요청한것에대한 타입을 적어줘야함
    Call<JsonArray> getUserResponsitories(@Path("user") String userName); //네트워크는 어쩃든 Json 형태로 교류되기때문에 jsonArray나 jsonObject로 먼저타입설정
                                        //위에 주소 {user}이부분 @Path로 처리가능한것
      //요건 요청!

    //POSTMAN 을이용하여 통신을 할때 추가로 내용물(BODY)을 같이보내는법 //여러가지 방식이 있지만 여기선 x-www.form-uniencoded 방식 사용
    @FormUrlEncoded              //form-uniencoded 방식으로 BODY실을떈 이거써줘야댕
    @POST("users/repos") //서버에게 정보제출요구>>POST
   //   Call<JsonArray> PostUser(@Field("username")String name); //field == 값의 한줄 //PostUser 라는 메소드는 첫번째 파라미터로 String name을 받게되고 이 name 은 Field로서 username으로 사용이된다!
    Call<JsonArray> PostUser(@Field("username")String name,@Field("age")int age);    // 이값으로 사용을 하겠다고 선언
  //이제 이 메소드들 사용하러 MainAcrivity gogogogo
}
