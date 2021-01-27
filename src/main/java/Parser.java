import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * A class that deals with making sense of the user command.
 */
public class Parser {
    /**
     * Parses the user inputs into commands that the program understands and perform actions accordingly.
     *
     * @param command User inputs.
     * @return Formatted date eg. changes 1/1/2011 to 01/01/2011.
     * @throws DukeException If an invalid command is given by the user. It also happens when there's lack of
     *                       information when task is created such as no description, date and time.
     * @throws IOException   If the named file exists but is a rather than a regular file, does not exist but
     *                       cannot be created, or cannot be opened for any other reason.
     */
    public static void parse(String command) throws DukeException, IOException {
        Ui ui = new Ui();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        if (command.equals("bye")) {
            ui.bye();
        } else if (command.equals("list")) {
            TaskList.list();
        } else if (command.substring(0, 4).equals("done")) {
            TaskList.done(Integer.parseInt(command.substring(5)));
        } else if (command.substring(0, 6).equals("delete")) {
            TaskList.delete(Integer.parseInt(command.substring(7)));
        } else if (command.substring(0, 4).equals("todo")) {
            ui.todo(command.substring(5));
        } else if (command.substring(0, 5).equals("event")) {
            String desc = "";
            String date = "";
            String time = "";
            String localTime = "";
            for (int i = 6; i < command.length(); i++) {
                if (command.charAt(i) == ' ' && command.charAt(i + 1) == '/') {
                    break;
                } else {
                    desc += command.charAt(i);
                }
            }
            for (int i = desc.length() + 11; i < command.length(); i++) {
                if (command.charAt(i) == ' ') {
                    break;
                } else {
                    date += command.charAt(i);
                }
            }
            time = command.substring(desc.length() + 12 + date.length());
            if (desc.equals("")) {
                ui.event("", null, null);
            } else if (date.equals("")) {
                ui.event(desc, null, null);
            } else if (time.equals("")) {
                ui.event(desc, LocalDate.parse(format(date), formatter), null);
            }
            localTime += time.substring(0, 2) + ":" + time.substring(2, 4);
            ui.event(desc, LocalDate.parse(format(date), formatter), LocalTime.parse(localTime));
        } else if (command.substring(0, 8).equals("deadline")) {
            String desc = "";
            String date = "";
            String time = "";
            String localTime = "";
            for (int i = 9; i < command.length(); i++) {
                if (command.charAt(i) == ' ' && command.charAt(i + 1) == '/') {
                    break;
                } else {
                    desc += command.charAt(i);
                }
            }
            for (int i = desc.length() + 14; i < command.length(); i++) {
                if (command.charAt(i) == ' ') {
                    break;
                } else {
                    date += command.charAt(i);
                }
            }
            time = command.substring(desc.length() + 15 + date.length());
            localTime += time.substring(0, 2) + ":" + time.substring(2, 4);
            if (desc.equals("")) {
                ui.deadline("", null, null);
            } else if (date.equals("")) {
                ui.deadline(desc, null, null);
            } else if (time.equals("")) {
                ui.deadline(desc, LocalDate.parse(format(date), formatter), null);
            }
            ui.deadline(desc, LocalDate.parse(format(date), formatter), LocalTime.parse(localTime));
        } else {
            throw (new DukeException("\n    ____________________________________________________________\n" +
                    "     ☹ OOPS!!! I'm sorry, but I don't know what that means :-(\n" +
                    "    ____________________________________________________________"));
        }
    }

    /**
     * Returns a formatted date of the given date string.
     *
     * @param date Date.
     * @return Formatted date eg. changes 1/1/2011 to 01/01/2011.
     */
    public static String format(String date) {
        if (date.charAt(1) == '/') {
            date = "0" + date;
        }
        if (date.charAt(4) == '/') {
            date = date.substring(0, 3) + "0" + date.substring(3);
        }
        return date;
    }
}