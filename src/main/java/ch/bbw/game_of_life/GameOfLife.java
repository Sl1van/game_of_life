package ch.bbw.game_of_life;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class GameOfLife {
    private static final int MIN_SPEED = 20;

    private Display display;
    private Shell shell;
    private int speed = MIN_SPEED;


    public GameOfLife() {
        initComponents();
    }

    private void initComponents() {
        display = new Display();

        shell = new Shell(display, SWT.CLOSE | SWT.TITLE | SWT.MIN);
        GridLayout shellLayout = new GridLayout(4, false);
        shell.setLayout(shellLayout);
        shell.setText("Game of life");

        Canvas canvas = new Canvas(shell, SWT.NO_BACKGROUND | SWT.DOUBLE_BUFFERED);
        GridData canvasGridData = new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1);
        canvasGridData.heightHint = 500;
        canvasGridData.widthHint = 500;
        canvas.setLayoutData(canvasGridData);

        Button playButton = new Button(shell, SWT.NONE);
        playButton.setText("Play");
        playButton.setImage(new Image(display, Main.class.getClassLoader().getResourceAsStream("icons/play.png")));
        playButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));

        Button stopButton = new Button(shell, SWT.NONE);
        stopButton.setText("Stop");
        stopButton.setImage(new Image(display, Main.class.getClassLoader().getResourceAsStream("icons/stop.png")));

        Label speedLabel = new Label(shell, SWT.NONE);
        speedLabel.setText("Speed:");

        Slider slider = new Slider(shell, SWT.HORIZONTAL);
        slider.setMinimum(MIN_SPEED);
        slider.setMaximum(2000);
        slider.setIncrement(10);
        slider.getData();
        slider.addListener(SWT.Selection, event -> speed = slider.getSelection());
    }

    public void start() {
        shell.pack();
        shell.open();

        initCells();

        display.syncExec(new MainLoop());

        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }

    private void initCells(){
        //TODO
    }

    private void displayCells() {
        //TODO
    }

    private void updateCells() {
        //TODO
    }

    private class MainLoop implements Runnable {
        @Override
        public void run() {
            System.out.println(speed);
            displayCells();
            updateCells();
            display.timerExec(speed,this);
        }
    }
}
