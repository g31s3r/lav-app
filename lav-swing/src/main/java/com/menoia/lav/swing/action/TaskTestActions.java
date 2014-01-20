package com.menoia.lav.swing.action;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.guts.gui.action.GutsAction;
import net.guts.gui.action.TaskAction;
import net.guts.gui.action.TasksGroupAction;
import net.guts.gui.task.AbstractTask;
import net.guts.gui.task.FeedbackController;
import net.guts.gui.task.Task;
import net.guts.gui.task.TaskInfo;
import net.guts.gui.task.TasksGroup;
import net.guts.gui.task.TasksGroup.Execution;
import net.guts.gui.task.blocker.InputBlockers;

import com.google.inject.Singleton;

@Singleton
public class TaskTestActions
{
  public final GutsAction _oneTaskNoBlocker = new TaskAction("oneTaskNoBlocker")
  {
    protected void perform()
    {
      submit(new TaskTestActions.LongTask("oneTaskNoBlocker"));
    }
  };

  public final GutsAction _oneTaskActionBlocker = new TaskAction("oneTaskActionBlocker")
  {
    protected void perform()
    {
      submit(new TaskTestActions.LongTask("oneTaskActionBlocker"), InputBlockers.actionBlocker(this));
    }
  };

  public final GutsAction _oneTaskComponentBlocker = new TaskAction("oneTaskComponentBlocker")
  {
    protected void perform()
    {
      submit(new TaskTestActions.LongTask("oneTaskComponentBlocker"), InputBlockers.componentBlocker(this));
    }
  };

  public final GutsAction _oneTaskWindowBlocker = new TaskAction("oneTaskWindowBlocker")
  {
    protected void perform()
    {
      submit(new TaskTestActions.LongTask("oneTaskWindowBlocker"), InputBlockers.windowBlocker(this));
    }
  };

  public final GutsAction _oneTaskDialogBlocker = new TaskAction("oneTaskDialogBlocker")
  {
    protected void perform()
    {
      submit(new TaskTestActions.LongTask("oneTaskDialogBlocker"), InputBlockers.dialogBlocker());
    }
  };

  public final GutsAction _oneTaskProgressDialogBlocker = new TaskAction("oneTaskProgressDialogBlocker")
  {
    protected void perform()
    {
      submit(new TaskTestActions.LongProgressTask("oneTaskProgressDialogBlocker"), InputBlockers.dialogBlocker());
    }
  };

  public final GutsAction _oneTaskSerialExecutor = new TaskAction("oneTaskNoBlockerSerialExecutor")
  {
    protected void perform()
    {
      submit(new TaskTestActions.LongTask("oneTaskDialogBlocker"), InputBlockers.noBlocker(), TaskTestActions.this._serialExecutor);
    }
  };

  public final GutsAction _fiveTasksDialogBlocker = new TasksGroupAction("fiveTasksDialogBlocker")
  {
    protected void perform()
    {
      TasksGroup group = newTasksGroup("fiveTasksDialogBlocker", Execution.CANCELLABLE, InputBlockers.dialogBlocker());

      for (int i = 0; i < 5; i++)
      {
        group.add(new TaskTestActions.LongTask("fiveTasksDialogBlocker #" + i));
      }
      group.execute();
    }
  };

  public final GutsAction _twoSerialTaskDialogBlocker = new TasksGroupAction("twoSerialTaskDialogBlocker")
  {
    protected void perform()
    {
      final TasksGroup theGroup = newTasksGroup("twoSerialTaskDialogBlocker", Execution.CANCELLABLE, InputBlockers.dialogBlocker());

      Task<Void> task1 = new TaskTestActions.LongProgressTask("twoSerialTaskDialogBlocker #1")
      {
        public Void execute(FeedbackController controller) throws Exception
        {
          super.execute(controller);
          theGroup.add(new TaskTestActions.LongProgressTask("twoSerialTaskDialogBlocker #2"));
          return null;
        }
      };
      theGroup.add(task1);
      theGroup.execute();
    }
  };

  public final GutsAction _twoSerialGroupsDialogBlocker = new TasksGroupAction("twoSerialGroupsDialogBlocker")
  {
    protected void perform()
    {
      TasksGroup group1 = newTasksGroup("twoSerialGroupsDialogBlocker #1", Execution.CANCELLABLE, InputBlockers.dialogBlocker());

      final TasksGroup group2 = newTasksGroup("twoSerialGroupsDialogBlocker #2", Execution.CANCELLABLE, InputBlockers.dialogBlocker());

      final Task<?> task2 = new TaskTestActions.LongTask("twoSerialGroupsDialogBlocker #2");
      Task<Void> task1 = new TaskTestActions.LongTask("twoSerialGroupsDialogBlocker #1")
      {
        public Void execute(FeedbackController controller) throws Exception
        {
          super.execute(controller);
          group2.add(task2).execute();
          return null;
        }
      };
      group1.add(task1).execute();
    }
  };

  private static final Random _randomizer = new Random();
  private final ExecutorService _serialExecutor = Executors.newFixedThreadPool(1);

  private static class LongProgressTask extends AbstractTask<Void>
  {
    private final String _name;

    LongProgressTask(String name)
    {
      this._name = name;
    }

    public Void execute(FeedbackController controller) throws Exception
    {
      int totalSleepChunks = 10 + TaskTestActions._randomizer.nextInt(10);
      Long sleep = Long.valueOf(500L * totalSleepChunks);
      System.out.printf("Task `%s` starting execute() for %d ms\n", new Object[] { this._name, sleep });
      for (int i = 0; i < totalSleepChunks; i++)
      {
        controller.setFeedback("Sleep chunk #" + (i + 1) + "...");
        Thread.sleep(500L);
        controller.setProgress((i + 1) * 100 / totalSleepChunks);
      }
      controller.setFeedback("Task done!");
      System.out.printf("Task `%s` ending execute() after %d ms\n", new Object[] { this._name, sleep });
      return null;
    }

    public void finished(TasksGroup group, TaskInfo source)
    {
      System.out.printf("Task `%s` finished\n", new Object[] { this._name });
    }
  }

  private static class LongTask extends AbstractTask<Void>
  {
    private final String _name;

    LongTask(String name)
    {
      this._name = name;
    }

    public Void execute(FeedbackController controller) throws Exception
    {
      Long sleep = Long.valueOf(5000L + TaskTestActions._randomizer.nextInt(10) * 500L);
      System.out.printf("Task `%s` starting execute() for %d ms\n", new Object[] { this._name, sleep });
      Thread.sleep(sleep.longValue());
      System.out.printf("Task `%s` ending execute() after %d ms\n", new Object[] { this._name, sleep });
      return null;
    }

    public void finished(TasksGroup group, TaskInfo source)
    {
      System.out.printf("Task `%s` finished\n", new Object[] { this._name });
    }
  }
}
