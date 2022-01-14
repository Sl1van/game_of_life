package ch.bbw.game_of_life;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.util.Arrays;

public class GameOfLife {
    private static final int MIN_SPEED = 20;

    private Display display;
    private Shell shell;
    private int speed = MIN_SPEED;
    private Status status;
    private boolean[][] cells = new boolean[50][50]; //x and y


    public GameOfLife() {
//        status = Status.PAUSED;
//        initComponents();


        initCells();
        status = Status.PLAYING;
        new MainLoop().run();
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
        playButton.addListener(SWT.Selection, event -> status = Status.PLAYING);

        Button stopButton = new Button(shell, SWT.NONE);
        stopButton.setText("Stop");
        stopButton.setImage(new Image(display, Main.class.getClassLoader().getResourceAsStream("icons/stop.png")));
        stopButton.addListener(SWT.Selection, event -> status = Status.PAUSED);

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
        displayCells();

        display.timerExec(speed, new MainLoop());

        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }

    private void initCells(){
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                cells[x][y] = true;
            }
        }
    }

    private void displayCells() {
        //TODO
        printCells();
    }

    private void updateCells() {
        //TODO
    }

    private void printCells(){
        System.out.println("#################################################");
        for (boolean[] x : cells)
        {
            for (boolean y : x)
            {
                System.out.print(y?"X":" ");
            }
            System.out.println();
        }
    }

    private class MainLoop implements Runnable {
        @Override
        public void run() {
            if (status == Status.PLAYING) {
                displayCells();
                updateCells();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.run();
//                display.timerExec(speed, this);
            } else {
                display.timerExec(100, this);
            }
        }
    }
}
