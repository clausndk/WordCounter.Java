public interface IWord {
    void incrementCount();
    int getCount();
    String getKey();
    int compare(IWord other);
    String getLetterIndex();
}
