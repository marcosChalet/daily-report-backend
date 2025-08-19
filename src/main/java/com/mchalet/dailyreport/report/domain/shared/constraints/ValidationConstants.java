package com.mchalet.dailyreport.report.domain.shared.constraints;

public final class ValidationConstants {
    private ValidationConstants() {}
    public static final int TAG_MAX_LENGTH = 25;
    public static final int TITLE_MAX_LENGTH = 50;
    public static final int TITLE_MIN_LENGTH = 5;
    public static final int TYPE_MAX_VALUE = 6;
    public static final int TYPE_MIN_VALUE = 1;
    public static final int TASK_MAX_LENGTH = 100;
    public static final String TAG_PATTERN = "^(?!.*--)[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?$";
}
