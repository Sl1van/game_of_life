package ch.bbw.game_of_life;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.util.Arrays;
import java.util.Random;

public class GameOfLife {
    private static final int MIN_SPEED = 20;

    private final int canvasCellHeight = 50;
    private final int canvasCellWidth = 50;
    private final int canvasHeight = 500;
    private final int canvasWidth = 500;
    private final boolean useGui;

    private Display display;
    private Shell shell;
    private int speed = MIN_SPEED;
    private Status status;
    private boolean[][] cells = new boolean[canvasCellWidth][canvasCellHeight]; //x and y
    private Canvas canvas;


    public GameOfLife(boolean useGui) {
        this.useGui = useGui;
        if (useGui) {
            status = Status.PAUSED;
            initComponents();
        }
    }

    private void initComponents() {
        display = new Display();

        shell = new Shell(display, SWT.CLOSE | SWT.TITLE | SWT.MIN);
        GridLayout shellLayout = new GridLayout(4, false);
        shell.setLayout(shellLayout);
        shell.setText("Game of life");

        canvas = new Canvas(shell, SWT.DOUBLE_BUFFERED);
        GridData canvasGridData = new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1);
        canvasGridData.heightHint = canvasHeight;
        canvasGridData.widthHint = canvasWidth;
        canvas.setLayoutData(canvasGridData);
        canvas.setBackground(new Color(display, new RGB(0, 0, 0)));
        canvas.addPaintListener(event -> {
            int cellWidth = canvasWidth / canvasCellWidth;
            int cellHeight = canvasHeight / canvasCellHeight;
            for (int x = 0; x < canvasCellWidth; x++) {
                for (int y = 0; y < canvasCellHeight; y++) {
                    if (cells[x][y]) {
                        event.gc.setBackground(new Color(display, new RGB(255, 255, 255)));
                        event.gc.fillRectangle(x * cellWidth, y * cellHeight, cellWidth, cellHeight);
                    } else {
                        event.gc.setBackground(new Color(display, new RGB(0, 0, 0)));
                        event.gc.fillRectangle(x * cellWidth, y * cellHeight, cellWidth, cellHeight);
                    }
                }
            }
        });

        Button playButton = new Button(shell, SWT.NONE);
        playButton.setText("Play");
        playButton.setImage(new Image(display, Main.class.getClassLoader().getResourceAsStream("icons/play.png")));
        playButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
        playButton.addListener(SWT.Selection, event -> status = Status.PLAYING);

        Button stopButton = new Button(shell, SWT.NONE);
        stopButton.setText("Stop");
        stopButton.setImage(new Image(display, Main.class.getClassLoader().getResourceAsStream("icons/stop.png")));
        stopButton.addListener(SWT.Selection, event -> status = Status.PAUSED);

        Button resetButton = new Button(shell, SWT.NONE);
        resetButton.setText("Restart");
        resetButton.setImage(new Image(display, Main.class.getClassLoader().getResourceAsStream("icons/restart.png")));
        resetButton.addListener(SWT.Selection, event -> {
            initCells();
            displayCells();
        });

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
        if (useGui) {
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
        } else {
            initCells();
            status = Status.PLAYING;
            new MainLoop().run();
        }
    }


    private void initCells() {
        Random random = new Random();
        for (int x = 0; x < canvasCellWidth; x++) {
            for (int y = 0; y < canvasCellHeight; y++) {
                cells[x][y] = random.nextBoolean();
            }
        }
    }

    private void displayCells() {
        if (useGui) {
            canvas.redraw();
            canvas.layout();
            canvas.update();
        } else {
            printCells();
        }
    }

    private void updateCells() {
        //create a clone of each array in the 2d array
        boolean[][] updatedCells = Arrays.stream(cells).map(boolean[]::clone).toArray(boolean[][]::new);
        for (int x = 0; x < canvasCellWidth; x++) {
            for (int y = 0; y < canvasCellHeight; y++) {
                if (amountOfLivingCellsAroundCell(x, y) < 2 || amountOfLivingCellsAroundCell(x, y) > 3) {
                    updatedCells[x][y] = false;
                    continue;
                } else if (amountOfLivingCellsAroundCell(x, y) == 3) {
                    updatedCells[x][y] = true;
                }
                //cell just stays alive if no other if statement was true
            }
        }
        cells = updatedCells;
    }

    private int amountOfLivingCellsAroundCell(int x, int y) {
        int livingCells = 0;

        if (x != 0 && cells[x - 1][y]) {
            livingCells++;
        }
        if (y != 0 && cells[x][y - 1]) {
            livingCells++;
        }
        if (x != 0 && y != 0 && cells[x - 1][y - 1]) {
            livingCells++;
        }
        if (x != canvasCellWidth - 1 && cells[x + 1][y]) {
            livingCells++;
        }
        if (y != canvasCellHeight - 1 && cells[x][y + 1]) {
            livingCells++;
        }
        if (x != canvasCellWidth - 1 && y != canvasCellHeight - 1 && cells[x + 1][y + 1]) {
            livingCells++;
        }
        if (x != 0 && y != canvasCellHeight - 1 && cells[x - 1][y + 1]) {
            livingCells++;
        }
        if (x != canvasCellWidth - 1 && y != 0 && cells[x + 1][y - 1]) {
            livingCells++;
        }
        return livingCells;
    }

    private void printCells() {
        System.out.println("#################################################");

        for (int y = 0; y < canvasCellWidth; y++) {
            for (int x = 0; x < canvasCellHeight; x++) {
                System.out.print(cells[x][y] ? "X" : " ");
            }
            System.out.println();
        }
    }

    private class MainLoop implements Runnable {
        @Override
        public void run() {
            if(shell.isDisposed()){
                return;
            }
            if (status == Status.PLAYING) {
                displayCells();
                updateCells();
                if (useGui) {
                    display.timerExec(speed, this);
                } else {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    this.run();
                }
            } else {
                display.timerExec(100, this);
            }
        }
    }
}
