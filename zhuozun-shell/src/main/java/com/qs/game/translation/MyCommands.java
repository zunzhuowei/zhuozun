package com.qs.game.translation;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

/**
 * Created by zun.wei on 2019/6/3 14:16.
 * Description:
 */
@ShellComponent
public class MyCommands {

    private boolean connected;

    @ShellMethod("Connect to the server.")
    public void connect(String user, String password) {
        //
        connected = true;
        System.out.println("connected = " + connected);
    }

    @ShellMethod("Download the nuclear codes.")
    public void download() {
        //
        System.out.println("download = " + "download");
    }

    @ShellMethod("Disconnect from the server.")
    public void disconnect() {
        //
        System.out.println("disconnect = " + "disconnect");
    }

    @ShellMethodAvailability({"download", "disconnect"})
    public Availability availabilityCheck() {
        return connected
                ? Availability.available()
                : Availability.unavailable("you are not connected");
    }

}
