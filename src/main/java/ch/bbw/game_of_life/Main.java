package ch.bbw.game_of_life;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class Main {

    public static void main(String[] args) {
        Display display = new Display();

        Shell shell = new Shell(display, SWT.CLOSE|SWT.TITLE|SWT.MIN);
        GridLayout shellLayout = new GridLayout(4, false);
//        shellLayout.
        shell.setLayout(shellLayout);
        shell.setText("Game of life");
//        shell.setSize(500,500);

        Canvas canvas = new Canvas(shell,SWT.NO_BACKGROUND | SWT.DOUBLE_BUFFERED);
        GridData canvasGridData =new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1);
        canvasGridData.heightHint =500;
        canvasGridData.widthHint =500;
        canvas.setLayoutData(canvasGridData);

        Button playButton = new Button(shell, SWT.NONE);
        playButton.setText("Play");
        playButton.setImage(new Image(display, Main.class.getClassLoader().getResourceAsStream("icons/play.png")));
        playButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));

        Button stopButton = new Button(shell, SWT.NONE);
        stopButton.setText("Stop");
        stopButton.setImage(new Image(display, Main.class.getClassLoader().getResourceAsStream("icons/stop.png")));

        Label speedLabel= new Label(shell, SWT.NONE);
        speedLabel.setText("Speed:");

        Slider slide = new Slider(shell, SWT.HORIZONTAL);
        slide.setMinimum(0);
        slide.setMaximum(2000);
        slide.setIncrement(10);

        shell.pack();
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch())
            { display.sleep();}
        }
        display.dispose();
    }
}