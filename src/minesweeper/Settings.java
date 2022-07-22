package minesweeper;

import java.io.*;

public class Settings implements Serializable {

    private final int rowCount;
    private final int columnCount;
    private final int mineCount;

    public static final Settings BEGINNER = new Settings(9, 9, 10);
    public static final Settings INTERMEDIATE = new Settings(16, 16, 40);
    public static final Settings EXPERT = new Settings(16, 30, 99);

    private static final String SETTING_FILE = System.getProperty("user.home")
            + System.getProperty("file.separator")
            + "minesweeper.settings";

    public Settings(int rowCount, int columnCount, int mineCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.mineCount = mineCount;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Settings)) {
            return false;
        }

        Settings s = (Settings) obj;
        if (s.rowCount == rowCount && s.columnCount == columnCount && s.mineCount == mineCount) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return rowCount * columnCount * mineCount;
    }

    public void save() {
        ObjectOutputStream outputStream = null;
        try {
            outputStream = new ObjectOutputStream(new FileOutputStream(SETTING_FILE));
            outputStream.writeObject(this);
        } catch (IOException e) {
            System.out.println("Settings sa nepodarilo zapisat");
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public static Settings load() {
        ObjectInputStream inputStream = null;

        try {
            inputStream = new ObjectInputStream(new FileInputStream(SETTING_FILE));
            Settings s = (Settings) inputStream.readObject();
            return s;
        } catch (IOException e) {
            System.out.println("Settings subor sa nepodarilo otvorit, nastavujem obtiaznost na BEGINNER");
        } catch (ClassNotFoundException e) {
            System.out.println("Settings subor sa nenasiel, nastavujem obtiaznost na BEGINNER");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
        }
        return BEGINNER;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public int getMineCount() {
        return mineCount;
    }
}

