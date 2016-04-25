package co.ipb.adukerang.controller;

/**
 * Created by winnerawan on 3/23/16.
 */
public class AppConfig {

    public static String URL_REGIST         = "http://api.winnerawan.net/adukerang/";
    public static String URL_GET_KELUHAN    = "http://api.winnerawan.net/adukerang/get/keluhan/";
    //public static String URL_GET_RUANG    = "http://api.winnerawan.net/adukerang/get/ruang/";
    public static String URL_GET_ID_RUANG   = "http://api.winnerawan.net/adukerang/get/ruang/index.php?id_ruang=";
    public static String URL_GET_BARANG     = "http://api.winnerawan.net/adukerang/get/barang/index.php?id_barang=";
    public static String URL_SET_KELUHAN    = "http://api.winnerawan.net/adukerang/set/foto/upload.php";
    public static String URL_GET_KELUHANKU  = "http://api.winnerawan.net/adukerang/get/keluhan/by/user/?unique_id=";
    public static String URL_TEKNISI_LOGIN  = "http://api.winnerawan.net/adukerang/log/teknisi/";
    public static String URL_GET_TEKNISI_ID = "http://api.winnerawan.net/adukerang/get/teknisi/by/barang/?id_barang=";
    public static String URL_SEND_NOTIF     = "http://api.winnerawan.net/adukerang/gcm/notif/?id=";
    public static String URL_TEKNISI_NOTIF  = "http://api.winnerawan.net/adukerang/teknisi/get/keluhan/?keluhan=";
    public static String URL_GET_GCM_USER   = "http://api.winnerawan.net/adukerang/get/gcm/user/?name=";
    //
    public static String URL_UPDATE_KELUHAN = "http://api.winnerawan.net/adukerang/update/keluhan/";
    public static String URL_TUTUP_ADUAN    = "http://api.winnerawan.net/adukerang/tutup/keluhan/";
    public static String URL_GET_USER_FOTO  = "http://api.winnerawan.net/adukerang/get/user/?email=";
    public static String url_last           = "http://api.winnerawan.net/adukerang/teknisi/get/keluhan/last.php";
    public static String URL_SET_AVATAR     = "http://api.winnerawan.net/adukerang/update/user/upload.php";
    public static String URL_HOME_TEKNISI   = "http://api.winnerawan.net/adukerang/get/keluhan/teknisi/?teknisi_id=";
    public static String UPDATE_USER_DETAILS= "http://api.winnerawan.net/adukerang/update/user/details/?unique_id=";
    public static String URL_GET_K_DETAILS  = "http://api.winnerawan.net/adukerang/get/details/?id_keluhan=";
    public static String URL_LIKE           = "http://api.winnerawan.net/adukerang/set/liked/";
    public static String URL_GET_LIKER      = "http://api.winnerawan.net/adukerang/get/liker/?id_keluhan=";
    public static String URL_CHECK_LIKE     = "http://api.winnerawan.net/adukerang/get/liked/?id_keluhan=";
    public static String URL_GET_COMMENT    = "http://api.winnerawan.net/adukerang/get/comment/?id_keluhan=";
    public static String URL_SET_COMMENT    = "http://api.winnerawan.net/adukerang/set/comment/";
    //get teknisi id by id_barang
    //gcm test
    public static String URL_GCM            = "http://api.winnerawan.net/adukerang/GCM.php";
    public static final String SENDER_ID    = "280597259588";
    public static final String SERVER_URL   = "http://api.winnerawan.net/adukerang/";
}
