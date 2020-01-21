/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;


public class Data {
    private long NIK;
    private String nama;
    private String tanggal;
    private String tujuan;
    private String kategori;
    private String satuan;

    public Data(long NIK, String nama, String tanggal, String tujuan, String kategori, String satuan) {
        this.NIK = NIK;
        this.nama = nama;
        this.tanggal = tanggal;
        this.tujuan = tujuan;
        this.kategori = kategori;
        this.satuan = satuan;
    }
    
    public long getNIK() {
        return NIK;
    }

    public String getNama() {
        return nama;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getTujuan() {
        return tujuan;
    }

    public String getKategori() {
        return kategori;
    }

    public String getSatuan() {
        return satuan;
    }
}
    

