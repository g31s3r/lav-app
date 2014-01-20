package com.menoia.lav.swing.util;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import net.guts.gui.action.GutsAction;
import net.guts.gui.action.TaskAction;
import net.guts.gui.task.FeedbackController;
import net.guts.gui.task.Task;
import net.guts.gui.task.TaskInfo;
import net.guts.gui.task.TasksGroup;
import net.guts.gui.task.blocker.BlockerDialogPane;
import net.guts.gui.task.blocker.InputBlockers;
import net.guts.gui.util.EnumIconRenderer;

@Singleton
public class TasksGroupProgressPanel extends JPanel implements BlockerDialogPane {

    private static final long serialVersionUID = 1L;
    private static final String NAME = "MyBlockerDialog";

    private final TasksTableModel model;
    private final JTable tasks;
    private final EnumIconRenderer<TaskInfo.State> stateRenderer = new EnumIconRenderer<TaskInfo.State>(
        TaskInfo.State.class);
    private final JButton cancelBtn = new JButton(this.cancel);

    private final GutsAction cancel = new TaskAction("MyBlockerDialog-action-cancel") {

        protected void perform() {

            Task<Void> task = new Task<Void>() {

                public Void execute(FeedbackController controller) throws Exception {

                    TasksGroupProgressPanel.this.group.cancel();
                    return null;
                }
            };
            submit(task, InputBlockers.actionBlocker(this));
        }
    };

    private TasksGroup group;

    @Inject
    TasksGroupProgressPanel(TasksTableModel model) {

        setLayout(new BorderLayout());
        setName(NAME);
        this.model = model;
        this.tasks = new JTable(this.model);

        this.stateRenderer.mapIcon(TaskInfo.State.CANCELLED, new ImageIcon(
            "net/guts/gui/examples/addressbook/icons/cross.png"));
        this.stateRenderer.mapIcon(TaskInfo.State.FAILED, new ImageIcon(
            "net/guts/gui/examples/addressbook/icons/cog_error.png"));
        this.stateRenderer.mapIcon(TaskInfo.State.FINISHED, new ImageIcon(
            "net/guts/gui/examples/addressbook/icons/tick.png"));
        this.stateRenderer.mapIcon(TaskInfo.State.NOT_STARTED, new ImageIcon(
            "net/guts/gui/examples/addressbook/icons/cog.png"));
        this.stateRenderer.mapIcon(TaskInfo.State.RUNNING, new ImageIcon(
            "net/guts/gui/examples/addressbook/icons/cog_go.png"));
        this.tasks.setDefaultRenderer(TaskInfo.State.class, this.stateRenderer);
        this.tasks.getColumnModel().getColumn(1).setCellRenderer(new ProgressCellRenderer());

        int width = this.tasks.getPreferredSize().width;
        int height = 6 * this.tasks.getRowHeight();
        this.tasks.setPreferredScrollableViewportSize(new Dimension(width, height));

        JScrollPane scroller = new JScrollPane(this.tasks);
        add(scroller, "Center");
        add(this.cancelBtn, "South");
    }

    public void setTasksGroup(TasksGroup group) {

        this.group = group;
        this.model.setTasksGroup(group);
    }

    public boolean canClose() {

        return false;
    }

    public JComponent getPane() {

        return this;
    }

}
