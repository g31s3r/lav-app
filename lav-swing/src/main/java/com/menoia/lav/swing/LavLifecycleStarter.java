package com.menoia.lav.swing;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;

import javax.inject.Inject;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

import net.guts.gui.action.GutsAction;
import net.guts.gui.application.AppLifecycleStarter;
import net.guts.gui.application.GutsApplicationActions;
import net.guts.gui.exception.HandlesException;
import net.guts.gui.exit.ExitController;
import net.guts.gui.menu.MenuFactory;
import net.guts.gui.message.MessageFactory;
import net.guts.gui.resource.ResourceInjector;
import net.guts.gui.window.BoundsPolicy;
import net.guts.gui.window.JFrameConfig;
import net.guts.gui.window.WindowController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.menoia.lav.swing.action.TaskTestActions;
import com.menoia.lav.swing.view.LavMainView;

public class LavLifecycleStarter implements AppLifecycleStarter {

    private static final Logger logger = LoggerFactory.getLogger(LavLifecycleStarter.class);

    private final GutsAction french = new GutsAction("french") {

        protected void perform() {

            LavLifecycleStarter.this.injector.setLocale(Locale.FRENCH);
        }
    };

    private final GutsAction english = new GutsAction("english") {

        protected void perform() {

            LavLifecycleStarter.this.injector.setLocale(Locale.ENGLISH);
        }
    };

    private final ResourceInjector injector;
    private final TaskTestActions taskTestActions;
    private final GutsApplicationActions appActions;
    private final LavMainView mainView;
    private final WindowController windowController;
    private final MenuFactory menuFactory;
    private final MessageFactory messageFactory;

    private final ExitController exitController;

    @Inject
    public LavLifecycleStarter(WindowController windowController, MenuFactory menuFactory,
        MessageFactory messageFactory, ResourceInjector injector, ExitController exitController,
        TaskTestActions taskTestActions, GutsApplicationActions appActions, LavMainView mainView) {

        this.appActions = appActions;
        this.taskTestActions = taskTestActions;
        this.windowController = windowController;
        this.menuFactory = menuFactory;
        this.messageFactory = messageFactory;
        this.injector = injector;
        this.exitController = exitController;
        this.mainView = mainView;
    }

    public void startup(String[] args) {

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(this.menuFactory.createMenu("fileMenu", new GutsAction[] { this.appActions.quit() }));
        menuBar.add(this.menuFactory.createMenu("editMenu",
            new GutsAction[] { this.appActions.cut(), this.appActions.copy(), this.appActions.paste() }));

        menuBar.add(this.menuFactory.createMenu("localeMenu", new GutsAction[] { this.french, this.english }));
        menuBar.add(this.menuFactory.createMenu("taskTestMenu", new GutsAction[] {
            this.taskTestActions._oneTaskNoBlocker, this.taskTestActions._oneTaskComponentBlocker,
            this.taskTestActions._oneTaskActionBlocker, this.taskTestActions._oneTaskWindowBlocker,
            this.taskTestActions._oneTaskDialogBlocker, this.taskTestActions._oneTaskProgressDialogBlocker,
            MenuFactory.ACTION_SEPARATOR, this.taskTestActions._oneTaskSerialExecutor,
            this.taskTestActions._fiveTasksDialogBlocker, this.taskTestActions._twoSerialTaskDialogBlocker,
            this.taskTestActions._twoSerialGroupsDialogBlocker }));

        JFrame mainFrame = new JFrame();
        mainFrame.setName("mainFrame");

        mainFrame.setDefaultCloseOperation(0);
        mainFrame.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent arg0) {

                LavLifecycleStarter.this.exitController.shutdown();
            }
        });
        mainFrame.setJMenuBar(menuBar);
        mainFrame.setContentPane(this.mainView);
        this.windowController.show(mainFrame, JFrameConfig.create().bounds(BoundsPolicy.PACK_AND_CENTER).config());
    }

    public void ready() {

    }

    @HandlesException
    public boolean handle(Throwable e) {

        logger.info("Exception has occurred!", e);

        this.messageFactory.showMessage("unexpected-error", new Object[] { e, e.getMessage() });
        return true;
    }
}
