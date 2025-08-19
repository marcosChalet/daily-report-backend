package com.mchalet.dailyreport.report.domain;

import com.mchalet.dailyreport.report.domain.shared.vo.ID;
import lombok.Getter;

@Getter
public class ReportTask {
    private final ID id;
    private final String value;

    private ReportTask(ID id, String value) {
        this.id = id;
        this.value = value;
    }

    public static ReportTask create(String value) {
        return new ReportTask(ID.generate(), value);
    }

    public static ReportTask with(ID id, String value) {
        return new ReportTask(id, value);
    }
}
