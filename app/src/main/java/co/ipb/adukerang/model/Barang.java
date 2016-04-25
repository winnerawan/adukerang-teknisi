package co.ipb.adukerang.model;

/**
 * Created by winnerawan on 3/26/16.
 */
public class Barang {

    public String id_barang;
    public String nama_barang;
    public String id_jenis_barang;

    public Barang() {

    }

    public Barang(String id_barang, String nama_barang, String id_jenis_barang) {
        this.id_barang       = id_barang;
        this.nama_barang     = nama_barang;
        this.id_jenis_barang = id_jenis_barang;
    }

    public String getId_barang() {
        return id_barang;
    }

    public void setId_barang(String id_barang) {
        this.id_barang = id_barang;
    }

    public String getNama_barang() {
        return nama_barang;
    }

    public void setNama_barang(String nama_barang) {
        this.nama_barang = nama_barang;
    }

    public String getId_jenis_barang() {
        return id_jenis_barang;
    }

    public void setId_jenis_barang(String id_jenis_barang) {
        this.id_jenis_barang = id_jenis_barang;
    }
}
