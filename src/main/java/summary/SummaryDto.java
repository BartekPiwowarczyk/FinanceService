package summary;

import lombok.Data;

@Data
public class SummaryDto {
    private String summary;

    public SummaryDto(String summary) {
        this.summary = summary;
    }
}
