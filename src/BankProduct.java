/**
 * Created by inf117182 on 10.03.2017.
 */
abstract public class BankProduct {
    protected HistoryManager historyManager;

    public BankProduct() {
        historyManager = new HistoryManager();
    }
}
