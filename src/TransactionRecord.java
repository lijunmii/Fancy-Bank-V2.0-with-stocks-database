public class TransactionRecord {
    private String recordSummary;
    private String recordContent;

    TransactionRecord(String recordSummary, String recordContent) {
        this.recordSummary = recordSummary;
        this.recordContent = recordContent;
    }

    public String getRecordContent() {
        return recordContent;
    }

    public String getRecordSummary() {
        return recordSummary;
    }
}
