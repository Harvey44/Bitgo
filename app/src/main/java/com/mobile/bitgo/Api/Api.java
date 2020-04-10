package com.mobile.bitgo.Api;


import com.mobile.bitgo.models.BackupPhrase_Response;
import com.mobile.bitgo.models.CancelResponse;
import com.mobile.bitgo.models.CoinListResponse;
import com.mobile.bitgo.models.CreateResponse;
import com.mobile.bitgo.models.DefaultResponse;
import com.mobile.bitgo.models.HistoryResponse;
import com.mobile.bitgo.models.ListResponse;
import com.mobile.bitgo.models.LoginResponse;
import com.mobile.bitgo.models.MessageResponse;
import com.mobile.bitgo.models.RecoverResponse;
import com.mobile.bitgo.models.Recovercode_Response;
import com.mobile.bitgo.models.ResetResponse;
import com.mobile.bitgo.models.SetNewResponse;
import com.mobile.bitgo.models.Trans_Response;
import com.mobile.bitgo.models.ValidateResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("api.json")
    Call<DefaultResponse> createUser(
            @Field("cmd") String cmd,
            @Field("firstname") String first,
            @Field("lastname") String last,
            @Field("email") String emails,
            @Field("country") String country,
            @Field("password") String password,
            @Field("password2") String password2
    );

    @FormUrlEncoded
    @POST("api.json")
    Call<LoginResponse> userLogin(
            @Field("user") String email,
            @Field("password") String password,
            @Field("cmd") String cmd
    );

    @FormUrlEncoded
    @POST("api.json")
    Call<RecoverResponse> recover(
            @Field("user") String remail,
            @Field("cmd") String cmd
    );

    @FormUrlEncoded
    @POST("api.json")
    Call<Recovercode_Response> recover_code(
            @Field("email") String p_email,
            @Field("code") String Code,
            @Field("cmd") String Cmd
    );

    @FormUrlEncoded
    @POST("api.json")
    Call<ResetResponse> reset(
            @Field("email") String Resetemail,
            @Field("code") String Rcode,
            @Field("password") String Password1,
            @Field("password2") String Password2,
            @Field("cmd") String Rcmd
    );

    @FormUrlEncoded
    @POST("api.json")
    Call<BackupPhrase_Response> backup(
            @Field("id") int id,
            @Field("email") String Email,
            @Field("cmd") String Cmd,
            @Header("Authorization") String authToken
    );

    @FormUrlEncoded
    @POST("api.json")
    Call<ValidateResponse> validate(
            @Field("phrase") String Phrase,
            @Field("cmd") String Cmd
    );


    @FormUrlEncoded
    @POST("api.json")
    Call<SetNewResponse> setnew(
            @Field("email") String Setemail,
            @Field("code") String Recovery_code,
            @Field("password") String Password5,
            @Field("password2") String Password6,
            @Field("wallet_password") String Password7,
            @Field("wallet_password2") String Password8,
            @Field("cmd") String Rcmd
    );

    @FormUrlEncoded
    @POST("api.json")
    Call<CreateResponse> create2fa(
            @Field("id") int id,
            @Field("email") String Email,
            @Field("cmd") String Cmd,
            @Header("Authorization") String authToken
    );

    @FormUrlEncoded
    @POST("api.json")
    Call<CancelResponse> deactivate_2fa(
            @Field("id") int id,
            @Field("email") String Email,
            @Field("code") String Google_code,
            @Field("cmd") String Cmd,
            @Header("Authorization") String authToken
    );

    @FormUrlEncoded
    @POST("api.json")
    Call<LoginResponse> verify_2fa(
            @Field("id") int id,
            @Field("email") String Email,
            @Field("code") String Google_code,
            @Field("cmd") String Cmd,
            @Header("Authorization") String authToken
    );

    @FormUrlEncoded
    @POST("api.json")
    Call<CoinListResponse> getcoinlist(
            @Field("cmd") String cmd

    );

    @FormUrlEncoded
    @POST("api.json")
    Call<SetNewResponse> create_wallet(
            @Field("user_id") int ID,
            @Field("email") String Email,
            @Field("coin_id") int Coin,
            @Field("wallet_name") String Wallet_Name,
            @Field("wallet_password") String Wallet_Password,
            @Field("wallet_password2") String Wallet_Password2,
            @Field("cmd") String Cmd,
            @Header("Authorization") String authToken

    );

    @FormUrlEncoded
    @POST("api.json")
    Call<ListResponse> listwallet(
            @Field("id") int id,
            @Field("email") String Email,
            @Field("limit") int limit,
            @Field("cmd") String Cmd,
            @Header("Authorization") String authToken

    );
    @FormUrlEncoded
    @POST("api.json")
    Call<LoginResponse> activate_2fa(
            @Field("id") int id,
            @Field("email") String Email,
            @Field("code") String Google_code,
            @Field("cmd") String Cmd,
            @Header("Authorization") String authToken
    );

    @FormUrlEncoded
    @POST("api.json")
    Call<CancelResponse> verify_email(
            @Field("id") int id,
            @Field("email") String Vemail,
            @Field("code") String Code,
            @Field("cmd") String Cmd,
            @Header("Authorization") String authToken
    );
    @FormUrlEncoded
    @POST("api.json")
    Call<CancelResponse> resend_code(
            @Field("user") String vemail,
            @Field("id") int Id,
            @Field("cmd") String cmd,
            @Header("Authorization") String authToken

    );

    @FormUrlEncoded
    @POST("api.json")
    Call<DefaultResponse> all_balance(
            @Field("id") int Id,
            @Field("email") String email,
            @Field("cmd") String cmd,
            @Header("Authorization") String authToken
    );

    @FormUrlEncoded
    @POST("api.json")
    Call<DefaultResponse> edit_password(
            @Field("email") String Email,
            @Field("id") int Id,
            @Field("opassword") String Ppwd,
            @Field("password") String Npwd,
            @Field("password2") String Cnpwd,
            @Field("cmd") String Cmd,
            @Header("Authorization") String authToken
    );

    @FormUrlEncoded
    @POST("api.json")
    Call<ListResponse> showall(
            @Field("id") int id,
            @Field("email") String Email,
            @Field("cmd") String Cmd,
            @Header("Authorization") String authToken

    );

    @FormUrlEncoded
    @POST("api.json")
    Call<MessageResponse> noty(
            @Field("id") int id,
            @Field("email") String Email,
            @Field("type") String Type,
            @Field("cmd") String Cmd,
            @Header("Authorization") String authToken

    );

    @FormUrlEncoded
    @POST("api.json")
        Call<CancelResponse> view_noty(
            @Field("id") int id,
            @Field("email") String email,
            @Field("notify_id") String notify_id,
            @Field("cmd") String cmd,
            @Header("Authorization") String authToken
    );

    @FormUrlEncoded
    @POST("api.json")
    Call<Recovercode_Response> review(
            @Field("user_id") int id,
            @Field("email") String email,
            @Field("password") String password,
            @Field("wallet_id") String wallet_id,
            @Field("amount") String amount,
            @Field("wallet_address") String wallet_address,
            @Field("currency_type") String currency_type,
            @Field("wallet_type") String wallet_type,
            @Field("cmd") String cmd,
            @Header("Authorization") String authToken

    );

    @FormUrlEncoded
    @POST("api.json")
    Call<HistoryResponse>trans(
            @Field("id") int id,
            @Field("email") String email,
            @Field("wallet_id") String wallet_id,
            @Field("cmd") String cmd,
            @Header("Authorization") String authToken

    );

    @FormUrlEncoded
    @POST("api.json")
    Call<ResetResponse> receive(
            @Field("user_id") int id,
            @Field("email") String email,
            @Field("wallet_id") String wallet_id,
            @Field("cmd") String cmd,
            @Header("Authorization") String authToken

    );

    @FormUrlEncoded
    @POST("api.json")
    Call<Trans_Response> view_trans(
            @Field("id") int id,
            @Field("email") String email,
            @Field("transaction_id") String transaction_id,
            @Field("cmd") String cmd,
            @Header("Authorization") String authToken

    );
}


