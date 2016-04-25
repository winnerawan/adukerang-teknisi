package co.ipb.adukerang.model;

/**
 * Created by winnerawan on 4/17/16.
 */
public class Comment {
    public int id_komentar;
    public int id_keluhan;
    public String email;
    public String komentar;
    public String name;
    public String profilePicture;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public int getId_komentar() {
        return id_komentar;
    }

    public void setId_komentar(int id_komentar) {
        this.id_komentar = id_komentar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId_keluhan() {
        return id_keluhan;
    }

    public void setId_keluhan(int id_keluhan) {
        this.id_keluhan = id_keluhan;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }
}
