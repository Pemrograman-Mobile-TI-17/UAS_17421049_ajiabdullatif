package com.example.myapplication.server;

public class BaseURL {

    public static String baseUrl = "http://192.168.100.25:5050/";

    public static String login      = baseUrl + "user/login";
    public static String registrasi  = baseUrl + "user/registrasi";

    //buku
    public static String dataPancing = baseUrl + "pancing/datapancing";
    public static String editDataPancing = baseUrl + "pancing/ubah/";
    public static String hapusData = baseUrl + "pancing/hapus/";
    public static String inputPancing = baseUrl + "pancing/input";

}
