package com.menoia.lav.swing.util;

import javax.swing.table.AbstractTableModel;

import net.guts.gui.task.TaskInfo;
import net.guts.gui.task.TasksGroup;
import net.guts.gui.task.TasksGroupListener;

import com.google.inject.Singleton;

@Singleton
public class TasksTableModel extends AbstractTableModel implements TasksGroupListener {

    private static final long serialVersionUID = 1L;

    private static final int NUM_COLUMNS = 3;
    private static final int COLUMN_STATE = 0;
    private static final int COLUMN_PROGRESS = 1;
    private static final int COLUMN_FEEDBACK = 2;

    private TasksGroup group = null;
    private int endedTasks = 0;
    private TaskInfo.State groupState = TaskInfo.State.NOT_STARTED;

    public void setTasksGroup(TasksGroup group) {

        this.group = group;
        this.endedTasks = 0;
        this.groupState = TaskInfo.State.NOT_STARTED;
        this.group.addGroupListener(this);
        fireTableDataChanged();
    }

    public Class<?> getColumnClass(int columnIndex) {

        switch (columnIndex) {
            case COLUMN_STATE:
                return TaskInfo.State.class;
            case COLUMN_PROGRESS:
                return Integer.class;
            case COLUMN_FEEDBACK:
                return String.class;
        }
        return Object.class;
    }

    public int getColumnCount() {

        return NUM_COLUMNS;
    }

    public int getRowCount() {

        if (this.group != null) {
            return this.group.tasks().size() + 1;
        }

        return 0;
    }

    public Object getValueAt(int row, int column) {

        if (row == 0) {
            switch (column) {
                case 0:
                    return groupState();
                case 1:
                    return Integer.valueOf(groupProgress());
                case 2:
                    return "";
            }
        } else {
            TaskInfo info = (TaskInfo) this.group.tasks().get(row - 1);
            switch (column) {
                case 0:
                    return info.state();
                case 1:
                    return Integer.valueOf(info.progress());
                case 2:
                    return info.feedback();
            }
        }
        return null;
    }

    private int groupProgress() {

        return this.endedTasks * 100 / this.group.tasks().size();
    }

    private TaskInfo.State groupState() {

        return this.groupState;
    }

    public void cancelled(TasksGroup group, TaskInfo source) {

        this.groupState = TaskInfo.State.CANCELLED;
        updateTask(source);
    }

    public void failed(TasksGroup group, TaskInfo source, Throwable cause) {

        updateTask(source);
    }

    public void feedback(TasksGroup group, TaskInfo source, String note) {

        updateTask(source);
    }

    public void finished(TasksGroup group, TaskInfo source) {

        updateTask(source);
    }

    public void interrupted(TasksGroup group, TaskInfo source, InterruptedException cause) {

        updateTask(source);
    }

    public void progress(TasksGroup group, TaskInfo source, int rate) {

        updateTask(source);
    }

    public void succeeded(TasksGroup group, TaskInfo source, Object result) {

        updateTask(source);
    }

    public void allTasksEnded(TasksGroup group) {

        this.groupState = TaskInfo.State.FINISHED;
        fireTableDataChanged();
    }

    public void taskAdded(TasksGroup group, TaskInfo task) {

        int index = this.group.tasks().size();
        fireTableRowsInserted(index, index);
    }

    public void taskEnded(TasksGroup group, TaskInfo task) {

        this.endedTasks += 1;
        updateTask(task);
    }

    public void taskStarted(TasksGroup group, TaskInfo task) {

        this.groupState = TaskInfo.State.RUNNING;
        updateTask(task);
    }

    private void updateTask(TaskInfo task) {

        int index = findTask(task);
        if (index > 0) {
            fireTableRowsUpdated(index, index);
            fireTableRowsUpdated(0, 0);
        }
    }

    private int findTask(TaskInfo task) {

        int index = 0;
        for (TaskInfo info : this.group.tasks()) {
            if (info == task) {
                return index + 1;
            }
            index++;
        }
        return -1;
    }

    @Override
    public void started(TasksGroup arg0, TaskInfo arg1) {

    }
}
