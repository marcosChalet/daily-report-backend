package com.mchalet.dailyreport.report.domain;

import com.mchalet.dailyreport.report.domain.vo.ReportType;
import com.mchalet.dailyreport.report.domain.vo.Tag;
import com.mchalet.dailyreport.report.domain.vo.Title;
import com.mchalet.dailyreport.report.domain.shared.vo.ID;
import lombok.Getter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
public class Report {
    private final ID id;
    private Title title;
    private ReportType type;
    private List<Tag> tags;
    private List<ReportTask> tasks;

    // Audit
    private final Instant createdAt;
    private Instant updatedAt;

    private Report(ID id, Title title, ReportType type, List<Tag> tags, List<ReportTask> tasks, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.tags = new ArrayList<>(tags);
        this.tasks = new ArrayList<>(tasks);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Static factory method for reconstruction (used by persistence layer).
     */
    public static Report with(ID id, Title title, ReportType type, List<Tag> tags, List<ReportTask> reportTasks, Instant createdAt, Instant updatedAt) {
        return new Report(id, title, type, tags, reportTasks, createdAt, updatedAt);
    }

    /**
     * Private helper to update the timestamp.
     */
    private void touch() {
        this.updatedAt = Instant.now();
    }

    public void addTask(ReportTask task) {
        Objects.requireNonNull(task, "Task cannot be null.");
        this.tasks.add(task);
        this.touch();
    }

    public void changeTitle(Title newTitle) {
        Objects.requireNonNull(newTitle, "Title cannot be null.");
        this.title = newTitle;
        this.touch();
    }

    public void changeType(ReportType newType) {
        Objects.requireNonNull(newType, "Type cannot be null.");
        this.type = newType;
        this.touch();
    }

    public void changeTags(List<Tag> newTags) {
        if (newTags == null) return;
        this.tags.clear();
        this.tags.addAll(newTags);
        this.touch();
    }

    public void addTag(Tag newTag) {
        this.tags.add(newTag);
        this.touch();
    }

    public void removeTask(ID taskId) {
        Objects.requireNonNull(taskId, "Task Id cannot be null.");
        boolean removed = this.tasks.removeIf(task -> task.getId().equals(taskId));
        if (removed) touch();
    }

    public List<Tag> getTags() {
        return Collections.unmodifiableList(this.tags);
    }

    public List<ReportTask> getReportTasks() {
        return Collections.unmodifiableList(this.tasks);
    }

    public static Builder builder(Title title, ReportType type) {
        return new Builder(title, type);
    }

    public static class Builder {
        private final Title title;
        private final ReportType type;
        private List<Tag> tags = new ArrayList<>();
        private List<ReportTask> tasks = new ArrayList<>();

        private Builder(Title title, ReportType type) {
            this.title = Objects.requireNonNull(title, "Report must have a title");
            this.type = Objects.requireNonNull(type, "Report must have a type");
        }

        public Builder withTags(List<Tag> tags) {
            this.tags = tags;
            return this;
        }

        public Builder withTasks(List<ReportTask> tasks) {
            this.tasks = tasks;
            return this;
        }

        public Report build() {
            Instant now = Instant.now();
            return new Report(ID.generate(), this.title, this.type, this.tags, this.tasks, now, now);
        }
    }
}
