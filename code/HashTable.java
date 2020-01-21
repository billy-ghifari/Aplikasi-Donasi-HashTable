package code;

import java.awt.TextField;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

public class HashTable {

    private Data[] hashArray;
    private String[][] temp;
    private int size = 100;
    private static int nItem = 0;

    public HashTable() {
        hashArray = new Data[size];
        loadFile();
    }

    public int hashFunc(long key) {
        int index = 0;
        index  = (int)key % size;
        return index;
    }

    public void insertGUI(long nik, String nama, String tanggal, String tujuan, String kategori, String satuan) {
        tujuan = tujuan.toLowerCase();
        kategori = kategori.toLowerCase();
        Data item = new Data (nik, nama,tanggal,tujuan, kategori, satuan);
        int hashVal = hashFunc(item.getNIK());
        int x = 1;
        while (hashArray[hashVal] != null) {
            if (hashArray[hashVal].getNIK() == nik) {
                System.out.println("Gagal insert -> " + nik + ", duplikat data!");
                JOptionPane.showMessageDialog(null, "Gagal insert -> " + nik + ", duplikat data!");
                return;
            } else {
                hashVal += x * x;
                x++;
                hashVal %= size;
            }
        }
        hashArray[hashVal] = item;
        nItem++;
        System.out.println("Berhasil menambahkan " + nik);
        JOptionPane.showMessageDialog(null, "Berhasil menambahkan!\n");
        writeFiles(nik, nama, tanggal, tujuan, kategori,satuan);
    }

    public void insert(long nik, String nama, String tanggal, String tujuan, String kategori, String satuan) {
        tujuan = tujuan.toLowerCase();
        kategori = kategori.toLowerCase();
        Data item = new Data (nik, nama,tanggal,tujuan, kategori, satuan);
        int hashVal = hashFunc(item.getNIK());
        int x = 1;
        while (hashArray[hashVal] != null) {
            if (hashArray[hashVal].getNIK() == 0) {
                JOptionPane.showMessageDialog(null, "Tidak dapat menambahkan data!\nNIK \'" + nik + "\' sudah ada.");
                return;
            } else {
                hashVal += x * x;
                x++;
                hashVal %= size;
            }
        }
        hashArray[hashVal] = item;
        nItem++;
    }

    public void loadFile() {
        File file = new File("C:\\Users\\USER\\Documents\\NetBeansProjects\\AplikasiDonasi\\donatur.txt");
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        DataInputStream dis = null;

        try {
            fis = new FileInputStream(file);

            // di sini BufferedInputStream ditambahkan untuk pembacaan secara cepat.
            bis = new BufferedInputStream(fis);
            dis = new DataInputStream(bis);

            // dis.available() akan mengembalikan nilai 0 jika file sudah tidak punya baris lagi.
            while (dis.available() != 0) {
                // statement ini membaca baris dari file dan menampilkannya ke console.
                String[] line = new String[5];
                line = dis.readLine().split(",");
                insert(Long.parseLong(line[0]), line[1], line[2], line[3], line[4], line[5]);
            }

            fis.close();
            bis.close();
            dis.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeFiles(long nik, String nama, String tanggal, String tujuan, String kategori, String satuan) {
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("C:\\Users\\USER\\Documents\\NetBeansProjects\\AplikasiDonasi\\donatur.txt", true)));
            out.println(nik + "," + nama + "," + tanggal + "," + tujuan + "," + kategori+","+satuan);
                System.out.println("write file --> donatur.txt");
            out.close();
        } catch (IOException e) {
            System.out.println("Gagal menulis ke file " + "C:\\Users\\USER\\Documents\\NetBeansProjects\\AplikasiDonasi\\donatur.txt");
            e.printStackTrace();
        }
    }

    public String[][] readData() {
        temp = new String[nItem][6];
        int j = 0;
        for (int i = 0; i < hashArray.length; i++) {
            if (hashArray[i] != null) {
                temp[j][0] = Long.toString(hashArray[i].getNIK());
                temp[j][1] = hashArray[i].getNama();
                temp[j][2] = hashArray[i].getTanggal();
                temp[j][3] = hashArray[i].getTujuan();
                temp[j][4] = hashArray[i].getKategori();
                temp[j][5] = hashArray[i].getSatuan();
                j++;
            }
        }
        return temp;
    }

    public Data find(long key) {
        int hashVal = hashFunc(key);
        int x = 1;
        while (hashArray[hashVal] != null) {
            if (hashArray[hashVal].getNIK() == key) {
                System.out.println("ketemu");
                return hashArray[hashVal];
            }
            hashVal += x * x;
            x++;
            hashVal %= size;
        }
        System.out.println("Tidak ditemukan:\n\t--> " + key);
        return null;
    }

    public Data delete(long key) {
        try {
            int hasVal = hashFunc(key);
            while (hashArray[hasVal] != null) {
                if (hashArray[hasVal].getNIK() == key) {
                    Data temp = hashArray[hasVal];
                    hashArray[hasVal] = null;
                    nItem--;
                    return temp;
                }
                hasVal++;
                hasVal %= size;
                System.out.println("Berhasil menghapus = " + key);
            }


        } catch (Exception e) {
            System.out.println("Gagal Menghapus");
            e.printStackTrace();
        }
        return null;
    }

    public void deleteTxt() {
        try {
            File mahasiswa = new File("donatur.txt");
            FileWriter fw = new FileWriter(mahasiswa);
            PrintWriter pw = new PrintWriter(fw);
            pw.print("");
            pw.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Delete txt failed");
            e.printStackTrace();
        }
    }

    public void deleteSelected(long key) {
        delete(key);
        deleteTxt();
        insertHashToTxt();
    }

    public void updateAt(long nik, String nama, String tanggal, String tujuan, String kategori, String satuan) {
        try {
            //deleteSelected(nim);
            insertGUI(nik, nama, tanggal, tujuan,kategori, satuan);
            JOptionPane.showMessageDialog(null, "Data Updated!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Update Failed");
        }
    }

    public void insertHashToTxt() {
        System.out.println("Insert Run!");
        for (int i = 0; i < size; i++) {
            if (hashArray[i] != null) {
                long nik = hashArray[i].getNIK();
                String nama = hashArray[i].getNama();
                String tanggal = hashArray[i].getTanggal();
                String tujuan = hashArray[i].getTujuan();
                String kategori = hashArray[i].getKategori();
                String satuan = hashArray[i].getSatuan();
                writeFiles(nik, nama, tanggal, tujuan, kategori, satuan);
            }
        }
    }

}