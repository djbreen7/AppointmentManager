package managers;

public class DataManager {
    private static DataManager instance = null;
    private int dataId;

    private DataManager() {
        dataId = -1;
    }

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public int getAndClearDataId() {
        var result = dataId;
        this.dataId = -1;
        return result;
    }

    public void setDataId(int id) {
        this.dataId = id;
    }
}
