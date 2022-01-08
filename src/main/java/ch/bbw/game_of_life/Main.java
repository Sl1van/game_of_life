package ch.bbw.game_of_life;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Main {
    public static void main(String[] args) {
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setText("Game of life");
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch())
            { display.sleep();}
        }
        display.dispose();
    }
}