package downloader;

public class DownloadInformation {
    private String name;
    private String downloadUrl;
    private String date;

    public DownloadInformation() {
    }

    public DownloadInformation(String name, String downloadUrl, String date) {
        this.name = name;
        this.downloadUrl = downloadUrl;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
