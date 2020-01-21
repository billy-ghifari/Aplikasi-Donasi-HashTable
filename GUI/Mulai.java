package GUI;

public class Mulai {

    public static void main(String[] args) {
        Loading masuk = new Loading();
        Login halamanutama = new Login();
        masuk.setVisible(true);
        try {
            for (int i = 0; i <= 100; i++) {
                Thread.sleep(50);
                masuk.LabelPersen.setText(Integer.toString(i) + "%");
                masuk.progressBar.setValue(i);
                if (i == 100) {
                    halamanutama.setVisible(true);
                    masuk.dispose();
                }
            }
        } catch (Exception e) {
        }
    }
}
