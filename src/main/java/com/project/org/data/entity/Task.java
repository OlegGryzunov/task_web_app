package com.project.org.data.entity;

import java.util.Calendar;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.project.org.data.other.Importance;
import com.project.org.data.other.Status;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
@Entity
@Table(name = "task")
@Getter
@Setter
@ToString
public class Task
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private Long parentId;
	
	@NonNull
	@Column(nullable = false)
	@Size(min=3, max=45)
	private String name;
	
	@OneToOne(mappedBy = "task", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Description description;
	
	public void setDescription(Description description) {
		if (this.description == null && description != null) {
			description.setTask(this);
			this.description = description;
		}	
		else if (description == null) {
			this.description.setTarget(null);
			this.description.setResult(null);
		}
		else if (this.description.getId() == description.getId()) {			
			this.description = description;
		}
	}
	
	@NonNull
	@Column
	@Enumerated(EnumType.ORDINAL)
	private Importance importance = Importance.MID;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	@DateTimeFormat(pattern = "MM/dd/yyyy hh:mm a")
	private Calendar date;
	
	@NonNull
	@Column
	@Enumerated(EnumType.ORDINAL)
 	private Status status = Status.INPROGRESS;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinTable(name="task_watchers",
    joinColumns=
        @JoinColumn(name="TASK_ID", referencedColumnName="id"),
    inverseJoinColumns=
        @JoinColumn(name="employee_id", referencedColumnName="id")
    )
	private Set<Employee> watchers;
	
	public void setWatchers(Set<Employee> watchers) {
		this.watchers = watchers;
		addWatcher(this.supervisor);
		addWatcher(this.performer);
	}
	
	private void addWatcher(Employee watcher) {
		if (watcher.getId() == null || this.watchers == null)
			return;
		for (Employee w : watchers) {
			if (w.getId() == watcher.getId())
				return;
		}
		watchers.add(watcher);
	}
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name="employee_id")
	private Employee performer;
	
	public void setPerformer(Employee performer) {
		if (performer == null)
			return;
		if (performer.getId() == null)
			return;
		else if (this.performer == null || this.performer.getId() == null)
			this.performer = performer;
		else {
			this.performer.setTaskId(performer.getTaskId());
			this.performer.setUser(performer.getUser());
		}
		addWatcher(performer);
	}
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinTable(name = "task_supervisor", 
			joinColumns=@JoinColumn(name="TASK_ID", referencedColumnName="id"),
	        inverseJoinColumns=@JoinColumn(name="supervisor_id", referencedColumnName="id")
	)
	private Employee supervisor;
	
	public void setSupervisor(Employee supervisor) {
		if (supervisor == null)
			return;
		if (supervisor.getId() == null)
			return;
		else if (this.supervisor == null || this.supervisor.getId() == null)
			this.supervisor = supervisor;
		else {
			this.supervisor.setTaskId(supervisor.getTaskId());
			this.supervisor.setUser(supervisor.getUser());
		}
		addWatcher(supervisor);
	}
}