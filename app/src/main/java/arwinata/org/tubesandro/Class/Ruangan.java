package arwinata.org.tubesandro.Class;

public class Ruangan {
    private String nama, lokasi;
    private int lantai;

    public Ruangan(){}

    public Ruangan(String nama, String lokasi, int lantai) {
        this.nama = nama;
        this.lokasi = lokasi;
        this.lantai = lantai;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public int getLantai() {
        return lantai;
    }

    public void setLantai(int lantai) {
        this.lantai = lantai;
    }
}