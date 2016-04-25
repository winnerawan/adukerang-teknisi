package co.ipb.adukerang.model;

/**
 * Created by winnerawan on 3/26/16.
 */
public class Keluhan {
    public String id_ruang;
    public String id_keluhan;
    public String id_barang;
    public String id_teknisi;
    public int unique_id;
    public String keluhan;
    public String foto;
    public String status;
    public String keterangan;
    public String profile_picture;
    public String name;

    public Keluhan() {

    }

    public Keluhan(String id_ruang, String id_keluhan, String id_barang, String id_teknisi,
                    int uid, String keluhan, String foto, String status, String keterangan,
                    String profile_picture, String name) {

        this.id_ruang=id_ruang;
        this.id_keluhan=id_keluhan;
        this.id_barang=id_barang;
        this.id_teknisi=id_teknisi;
        this.unique_id=unique_id;
        this.keluhan=keluhan;
        this.foto=foto;
        this.status=status;
        this.keterangan=keterangan;
        this.profile_picture=profile_picture;
        this.name=name;

    }

    public String getId_ruang() {
        return id_ruang;
    }

    public void setId_ruang(String id_ruang) {
        this.id_ruang = id_ruang;
    }

    public String getId_keluhan() {
        return id_keluhan;
    }

    public void setId_keluhan(String id_keluhan) {
        this.id_keluhan = id_keluhan;
    }

    public String getId_barang() {
        return id_barang;
    }

    public void setId_barang(String id_barang) {
        this.id_barang = id_barang;
    }

    public String getId_teknisi() {
        return id_teknisi;
    }

    public void setId_teknisi(String id_teknisi) {
        this.id_teknisi = id_teknisi;
    }

    public int getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(int unique_id) {
        this.unique_id = unique_id;
    }

    public String getKeluhan() {
        return keluhan;
    }

    public void setKeluhan(String keluhan) {
        this.keluhan = keluhan;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
