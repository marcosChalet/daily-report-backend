package com.mchalet.dailyreport.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "TODO_LISTS")
@Relation(collectionRelation = "tasklists", itemRelation = "tasklist")
public class TaskListModel extends RepresentationModel<TaskListModel>  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private Integer taskType;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "task_list_tags_id")
    private List<TagModel> tags;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "task_list_id")
    private List<TaskModel> tasks;
}
