package profplan.model.task;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import profplan.commons.util.CollectionUtil;
import profplan.commons.util.ToStringBuilder;
import profplan.model.tag.Tag;

/**
 * Represents a Task in the task list.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Task implements Comparable<Task> {

    // Identity fields
    private final Name name;
    private final Priority priority;
    private final Email email;

    // Data fields
    private final Link link;
    private Status status;
    private final Set<Tag> tags = new HashSet<>();
    private final Set<Task> children = new HashSet<>();
    private final DueDate dueDate;
    private final Description description;

    /**
     * Every field except status must be present and not null.
     */
    public Task(Name name, Priority priority, Email email,
                Set<Tag> tags, DueDate dueDate, Set<Task> children, Link link, Description description) {
        CollectionUtil.requireAllNonNull(name, priority, tags, dueDate);
        this.name = name;
        this.priority = priority;
        this.email = email;
        this.status = Status.UNDONE_STATUS;
        this.tags.addAll(tags);
        this.children.addAll(children);
        this.dueDate = dueDate;
        this.link = link;
        this.description = description;
    }

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, Priority priority, Email email, Status status, Set<Tag> tags,
                DueDate dueDate, Set<Task> children, Link link, Description description) {
        CollectionUtil.requireAllNonNull(name, priority, tags, dueDate);
        this.name = name;
        this.priority = priority;
        this.email = email;
        this.status = status;
        this.tags.addAll(tags);
        this.dueDate = dueDate;
        this.children.addAll(children);
        this.link = link;
        this.description = description;
    }

    /**
     * Overloaded constructor to create a Task given another Task
     * @param task The task to copy from.
     */
    public Task(Task task) {
        this.name = task.name;
        this.priority = task.priority;
        this.email = task.email;
        this.tags.addAll(task.getTags());
        this.children.addAll(task.getChildren());
        this.status = task.status;
        this.dueDate = task.dueDate;
        this.link = task.link;
        this.description = task.description;
    }

    public Name getName() {
        return name;
    }

    public Priority getPriority() {
        return priority;
    }

    public Email getEmail() {
        return email;
    }

    public Link getLink() {
        return link;
    }

    public DueDate getDueDate() {
        return dueDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public Set<Task> getChildren() {
        return Collections.unmodifiableSet(children);
    }

    public Description getDescription() {
        return description;
    }

    /**
     * Returns true if both tasks have the same name.
     * This defines a weaker notion of equality between two tasks.
     */
    public boolean isSameTask(Task otherTask) {
        if (otherTask == this) {
            return true;
        }

        return otherTask != null
                && otherTask.getName().equals(getName());
    }

    /**
     * Returns true if both tasks have the same identity and data fields.
     * This defines a stronger notion of equality between two tasks.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Task)) {
            return false;
        }

        Task otherTask = (Task) other;
        return name.equals(otherTask.name)
                && priority.equals(otherTask.priority)
                && email.equals(otherTask.email)
                && tags.equals(otherTask.tags)
                && children.equals(otherTask.children)
                && dueDate.equals(otherTask.dueDate)
                && status.equals(otherTask.status)
                && link.equals(otherTask.link)
                && description.equals(otherTask.description);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, priority, email, status, tags, dueDate, link, description);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("priority", priority)
                .add("email", email)
                .add("status", status)
                .add("tags", tags)
                .add("dueDate", dueDate)
                .add("link", link)
                .add("description", description)
                .toString();
    }


    /**
     * Returns a beautified string representation of the task.
     * @return  a string representation of the task with name, priority and dueDate.
     */
    public String beautifyString() {
        return this.getName().toString() + ", Priority: " + this.getPriority().toString()
                + ", DueDate: " + this.getDueDate().toString();
    }


    @Override
    public int compareTo(Task o) {
        return this.dueDate.compareTo(o.dueDate);
    }

}
