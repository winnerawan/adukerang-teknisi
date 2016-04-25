package co.ipb.adukerang.model;

/**
 * Created by winnerawan on 3/26/16.
 */
public class IDRuangan {

    public String id_ruang;
    public String nama_ruang;

    public IDRuangan() {

    }

    public IDRuangan(String id_ruang, String nama_ruang) {
        this.id_ruang=id_ruang;
        this.nama_ruang=nama_ruang;
    }

    public String getId_ruang() {
        return id_ruang;
    }

    public void setId_ruang(String id_ruang) {
        this.id_ruang = id_ruang;
    }

    public String getNama_ruang() {
        return nama_ruang;
    }

    public void setNama_ruang(String nama_ruang) {
        this.nama_ruang = nama_ruang;
    }
}
