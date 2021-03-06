package com.example.ashokafarmer;

import com.example.ashokafarmer.allradyjoinedpools.Getalreadyjoinpoolformat;
import com.example.ashokafarmer.digitiselandmodels.Getnewlandformat;
import com.example.ashokafarmer.digitiselandmodels.Sendnewlandformat;
import com.example.ashokafarmer.joinpoolrecyclerdata.Getallpoolformat;
import com.example.ashokafarmer.joinpoolrequestbyfarmermodel.Getformatjoinpool;
import com.example.ashokafarmer.joinpoolrequestbyfarmermodel.Sendformatjoinpool;
import com.example.ashokafarmer.loginmodels.Logingetdet;
import com.example.ashokafarmer.loginmodels.Loginsendformat;
import com.example.ashokafarmer.notpooledlandmodels.Notpoollandgetformat;
import com.example.ashokafarmer.pendingrequestsmodels.Pendinggetformat;
import com.example.ashokafarmer.registrationmodels.Registgetformat;
import com.example.ashokafarmer.registrationmodels.Registsendformat;
import com.example.ashokafarmer.rejectedrequestsmodel.Rejectedgetformat;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    //signin or login route
    @POST("signin")
    Call<Logingetdet> sendlogincredentials(@Body Loginsendformat loginsendformat);
    //signin or login route ends

    //registration
    @POST("signup")
    Call<Registgetformat> sendregiscredentials(@Body Registsendformat registsendformat);


    @GET("farmers/landsadminpending")
    Call<Pendinggetformat> getpendingrequests(@Header("Authorization") String header);

    @GET("farmers/landsadminrejected")
    Call<Rejectedgetformat> getrejectedrequests(@Header("Authorization") String header);

    //all pools he joined till now
    @GET("farmers/pools")
    Call<Getalreadyjoinpoolformat> getalreadyjoinpool(@Header("Authorization") String header);

    @POST("farmers/lands")
    Call<Getnewlandformat> getnewland(@Header("Authorization") String header, @Body Sendnewlandformat sendnewlandformat);

    //showing land which is verfifed and not been in any pool
    @GET("farmers/landsnotpooled")
    Call<Notpoollandgetformat> getmynotpooled(@Header("Authorization") String header);

    //send pid to create new pool by farmer
    @POST("farmers/reqpoolcreate")
    Call<Createreqgetformat> createnewpool(@Header("Authorization") String header,@Body Createreqsentformat createreqsentformat);

    //all existing pools till now
    @GET("admins/pools")
    Call<Getallpoolformat> getallpools(@Header("Authorization") String header);

    //get pool info and reports
    @GET("admins/pool/{poolid}")
    Call<Poolinfogetformat> getdetailsofpool(@Header("Authorization") String header, @Path("poolid") String poolid);

    //for joining a pool
    @POST("farmers/reqpooljoin")
    Call<Getformatjoinpool> joinpool(@Header("Authorization") String header, @Body Sendformatjoinpool sendformatjoinpool);

    @GET("farmers/mylands")
    Call<Mylandgetformat> getmylands(@Header("Authorization") String header);

}
