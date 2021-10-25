
class Textid {
    int textid;
    String text;

    public Textid(int textid, String text) {
        this.textid = textid;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public int getTextid() {
        return textid;
    }

    public void setTextid(int textid) {
        this.textid = textid;
    }

    @Override
    public String toString() {
        return "Textid{" +
                "textid=" + textid +
                ", text='" + text + '\'' +
                '}';
    }
}