import java.util.List;

public class ImageText {
    String imageid;
    List<Textid> textid;

    public ImageText(String imageid, List<Textid> textid) {
        this.imageid = imageid;
        this.textid = textid;
    }

    public List<Textid> getTextid() {
        return textid;
    }

    public void setTextid(List<Textid> textid) {
        this.textid = textid;
    }


    public String getImageid() {
        return imageid;
    }

    public void setImageid(String imageid) {
        this.imageid = imageid;
    }

    @Override
    public String toString() {
        return "ImageText{" +
                "imageid='" + imageid + '\'' +
                ", textid=" + textid +
                '}';
    }
}

