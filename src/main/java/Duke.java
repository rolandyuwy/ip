/**
 * Duke, more commonly known as Duck, is a Personal Assistant Chat Bot that
 * helps a person to keep track of various tasks.
 * Contains static attribute stored_task which stores task input from user.
 **/
public class Duke {

    private Storage storage;
    private TaskList taskList;
    private Ui ui;

    public Duke(String filePath) {
        this.storage = new Storage(filePath);
        this.ui = new Ui();
        try {
            this.taskList = new TaskList(storage.load(), storage);
        } catch (DukeException e) {
            ui.printError(e.getMessage());
        }
    }

    public void run() {
        boolean isInProgram = true;

        ui.greet();
        while (isInProgram) {
            try {
                String input = ui.nextInput();
                Command command = Parser.parse(input);
                command.execute(taskList, ui);
                isInProgram = command.isInProgram();
            } catch (DukeException e) {
                ui.printError(e.getMessage());
            }
        }
    }

    /**
     * Prints greeting message.
     * Scans for commands entered by the user, then stores input task into stored_task for 3 types of tasks:
     * ToDo, Deadline and Event.
     * Upon user command input "done " followed by the task number, task will be marked as done.
     * Upon user command input "list", stored task will be listed.
     * Upon user command input "bye", system is exited.
     * Upon user command input "delete", task is deleted.
     **/
    public static void main(String[] args) {
        Duke duke = new Duke("data/duke.txt");
        duke.run();
    }
}
