package arwinata.org.tubesandro.Class;

public class Mahasiswa {

    private String nama;
    private String nim;
    private String alamat;
    private String username;
    private String password;
    private String nomorhp;

    private String imageUrl;

    public Mahasiswa() {
    }

    public Mahasiswa(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Mahasiswa(String nama, String nim,
                     String alamat, String username,
                     String password, String nomorhp,
                     String imageUrl) {
        this.nama = nama;
        this.nim = nim;
        this.alamat = alamat;
        this.username = username;
        this.password = password;
        this.nomorhp = nomorhp;
        this.imageUrl = imageUrl;
    }

    public String getNama() {
        return nama;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNim() {
        return nim;
    }
    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getAlamat() {
        return alamat;
    }
    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getNomorhp() { return nomorhp; }
    public void setNomorhp(String nomorhp) {this.nomorhp = nomorhp;}

    public String getImageUrl() {return imageUrl;}
    public void setImageUrl(String imageUrl) {this.imageUrl = imageUrl;}
}
