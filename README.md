# FiCLI

FiCLI is a simple and lightweight java command parser for use in creating CLIs.
This library's intent is to help create a straight-forward CLI quickly for a variety of applications.
It is distributed under the 3-clause BSD license.
Simply download the latest release or clone the repository and get to work!

## Getting Started

In this section we will learn how to implement FiCLI in a simple example project.
The example provided will start off simple and then continue on to more advanced topics.

Firstly, we'll create a new .java file named "ExampleApp.java" and then import FiCLI.
The default package will be used for everything.
We will then create our public class and our main method while we are there.

    import fiberous.fi.*;

    public class ExampleApp {
        public static void main(String[]args) {
        }
    }


Next, inside our main method, we will create a new FiCLI object.
The FiCLI class is what processes and executes commands.

    FiCLI cli = new FiCLI();


We need to create some commands for our app.
The first one will simply print an about message.
First, let's create a new class at the bottom of our ExampleApp.java file, extending the class FiCommand.
At a minimum, you must call the super's constructor and implement the execute method.
The AboutCommand class should look like this.

    class AboutCommand extends FiCommand {
        public AboutCommand(String commandString) {
            super(commandString);
        }

        @override
        public void execute() {

        }
    }

Next, we will actually implement our action, which is printing an about message to the console.
Inside the execute method, we will put a print statement about the application.

    System.out.println("Example application, ***Insert copyright info here***");

Next, inside our constructor, we will modify the parent class' (FiCommand) commandDescription String to match our command.
This String will be displayed when all of the commands are listed by the use of a special character (the character is '?' by default).

    this.commandDescription = "Displays a message about this application";

The AboutCommand class should now appear as follows:

    class AboutCommand extends FiCommand {
        public AboutCommand(String commandString) {
            super(commandString);
            this.commandDescription = "Displays a message about this application";
        }

        @Override
        public void execute() {
            System.out.println("Example application, ***Insert copyright info here***");
        }
    }

Now let's go to the bottom of our main method in the ExampleApp class and create an instance of the AboutCommand and add it to the FiCLI object we created earlier.
The AboutCommand constructor takes a String that will officially be the command's syntax.
When our FiCLI object sees "about" as input, it will execute the execute method in our AboutCommand.

    cli.addCommand(new AboutCommand("about"));

Let's create a processing loop that will accept commands and execute accordingly.
At the bottom of our main method, we will add a while loop.
The processCommand method will print a caret, and then wait for input.
When input is received, it will take the text data and compare it with it's currently configured commands.
The processCommand method will return a FiState object, which will give us info about what was input.
By default, if the text date matches a configured command, that command will be executed.
If not, nothing will happen, but the issue will be annotated for us in the FiState object that is returned.

    while(true) {
        FiState state = cli.processCommand();
    }

Right now, the entire ExampleApp.java file should look as follows:

    import fiberous.fi.*;

    public class ExampleApp {
        public static void main(String[]args) {
            FiCLI cli = new FiCLI();
            cli.addCommand(new AboutCommand("about"));

            while(true) {
                FiState state = cli.processCommand();
            }
        }
    }

    class AboutCommand extends FiCommand {
        public AboutCommand(String commandString) {
            super(commandString);
            this.commandDescription = "Displays a message about this application";
        }

        @Override
        public void execute() {
            System.out.println("Example application, ***Insert copyright info here***");
        }
    }

Go ahead and compile and run the application, it should display a caret.
When the caret appears, type 'about', and then press enter.  Everything should look as follows:

    > about
    Example application, ***Insert copyright info here***
    >

It should display the about message that we configured.
Next, type the question mark charecter ('?'), and press enter.

    > ?

    Available Commands:
    exit      Exits the application
    about     Displays a message about this application
    >

This lists all of the currently available commands.
Since exit is shown as a listed command, let's talk about FiState and the two special commands that are available.

## FiState

FiCLI uses two special commands.
They are 'exit' and 'back'.
Neither of these actually do anything.
They are simply application states that can be returned by the FiCLI.processCommand() method through the FiState object.
If you were to enter either of these commands into our current example application, nothing would happen as we do not have any actions configured with them.

FiStates have the following fields:
- input - a String containing the text data that was passed into FiCLI.
- state - an int that classifies what the data was (VALID, INVALID, EXIT, BACK).

The input field is exactly what it sounds like.
It is the exact String that was passed into FiCLI.
The state field is an int representing different command classifications.

There are final integers defined within the FiState class, and they are as follows:
- VALID - it was a valid command, and the command was executed. No issues.
- INVLAID - it was not a valid command, and no action was taken. This allows custom syntax error handling.
- EXIT - the input matched the exit string. This allows us to create custom exiting proceedures
- BACK - the input matched the back string. If we are utilizing a nested command structure, this can allow us to differentiate between backing out of a command menu or exiting the application, or any other desired functionality.

By default, the EXIT state is enabled, but the BACK state is disabled.
This is configurable with the FiCLI.allowAdditionalStates() method.
Let's utilize the EXIT state in our application.
We will add an if statement after our call to processCommand().

    if(state.state == FiState.EXIT) {
        System.exit(0);
    }

This purely exits our application when 'exit' is input.
Now let's add an error message when an invalid command is received.
We will add another if statement after our previous one for exiting.

    if(state.state == FiState.INVALID) {
        System.out.println("Invalid command");
    }

Now when you type exit, the application will exit, and when an unknown command is entered, an error message is displayed.
It's as simple as that!

## Command Arguments

Now let's add a command that can utilize arguments.  We will make a simple command that takes two integers and adds them together.
Since we already went over basic command structuring with the FiCommand, we will only go over the additional features that this new command utilizes.

    class AddCommand extends FiCommand {
        public AddCommand(String commandString) {
            super(commandString);
            this.commandDescription = "Adds two integers together.  Usage example: add 2 7";
        }

        @Override
        public void execute() {
            int int1 = Integer.parseInt(this.arguments.get(0));
            int int2 = Integer.parseInt(this.arguments.get(1));

            System.out.println("Answer: " + (int1 + int2));
        }
    }

As you can see, the parent class has an 'arguments' type that we did not explicitly configure, but have access to.
'arguments' is an ArrayList that contains individual text "tokens": Strings that are separated by a space.
Everything you enter after the command is stored as an argument.
It's up to you whether or not you want to access them.
Obviously, in the real world, don't neglect your exception handling.
We are just keeping everything simple here to stay concise.

Don't forget to add the command to our instantiated FiCLI in the main method.

    cli.addCommand(new AddCommand("add"));

Our entire ExampleApp.java file should now look like this:

    import fiberous.fi.*;

    public class ExampleApp {
        public static void main(String[]args) {
            FiCLI cli = new FiCLI();
            cli.addCommand(new AboutCommand("about"));
            cli.addCommand(new AddCommand("add"));

            while(true) {
                FiState state = cli.processCommand();

                if(state.state == FiState.EXIT) {
                    System.exit(0);
                }

                if(state.state == FiState.INVALID) {
                    System.out.println("Invalid command");
                }
            }
        }
    }

    class AboutCommand extends FiCommand {
        public AboutCommand(String commandString) {
            super(commandString);
            this.commandDescription = "Displays a message about this application";
        }

        @Override
        public void execute() {
            System.out.println("Example application, ***Insert copyright info here***");
        }
    }

    class AddCommand extends FiCommand {
        public AddCommand(String commandString) {
            super(commandString);
            this.commandDescription = "Adds two integers together.  Usage example: add 2 7";
        }

        @Override
        public void execute() {
            int int1 = Integer.parseInt(this.arguments.get(0));
            int int2 = Integer.parseInt(this.arguments.get(1));

            System.out.println("Answer: " + (int1 + int2));
        }
    }

## Nested Commands
Let's say you wanted to create some sort of menued structure for your commands.
The easiest way to do this is to nest another parser within a commands execute method.
We'll create another command as an example.

    class MenuCommand extends FiCommand {
        public MenuCommand(String commandString) {
            super(commandString);
            this.commandDescription = "A menu with more commands";
        }

        @Override
        public void execute() {
            FiCLI menuCLI = new FiCLI();
            menuCLI.setCaret("Menu > ");

            //Enables the exit command/state, enables the back command/state.
            menuCLI.allowAdditionalStates(true, true);
            menuCLI.addCommand(new AboutCommand("about"));

            while(true) {
                FiState state = menuCLI.processCommand();

                if(state.state == FiState.EXIT) {
                    System.exit(0);
                }

                //Gets us out of this menu...
                if(state.state == FiState.BACK) {
                    break;
                }

                if(state.state == FiState.INVALID) {
                    System.out.println("Invalid command");
                }
            }
        }
    }

Add this MenuCommand to the FiCLI object in the main method and explore it's functionality.
You should quickly notice that you can exit the menu with the 'back' command, and you can still exit the program with the 'exit' command.
The allowAdditionalStates method, in this case, allows the returned FiState object to have the EXIT and BACK states availble for use.
The setCaret command in the example is used to indicate that you are currently in a menued command structure.

## Hidden Commands

Suppose you have a command that you don't normally want an end user to use, but you still need the functionality for whatever reason.
That is when hidden commands become useful.
When a command is marked as hidden, and the list commands string is input, the command will not be listed.
Let's set the MenuCommand to a hidden command.

Add the following line to the MenuCommand's constructor.

    this.isVisible = false;

Now the MenuCommand won't be listed, but it can still be executed when 'menu' is still passed as a command.
Run the application to see for yourself.

The following is the entire ExampleApp.java source file for reference.

    import fiberous.fi.*;

    public class ExampleApp {
        public static void main(String[]args) {
            FiCLI cli = new FiCLI();
            cli.addCommand(new AboutCommand("about"));
            cli.addCommand(new AddCommand("add"));
            cli.addCommand(new MenuCommand("menu"));

            while(true) {
                FiState state = cli.processCommand();

                if(state.state == FiState.EXIT) {
                    System.exit(0);
                }

                if(state.state == FiState.INVALID) {
                    System.out.println("Invalid command");
                }
            }
        }
    }

    class AboutCommand extends FiCommand {
        public AboutCommand(String commandString) {
            super(commandString);
            this.commandDescription = "Displays a message about this application";
        }

        @Override
        public void execute() {
            System.out.println("Example application, ***Insert copyright info here***");
        }
    }

    class AddCommand extends FiCommand {
        public AddCommand(String commandString) {
            super(commandString);
            this.commandDescription = "Adds two integers together.  Usage example: add 2 7";
        }

        @Override
        public void execute() {
            int int1 = Integer.parseInt(this.arguments.get(0));
            int int2 = Integer.parseInt(this.arguments.get(1));

            System.out.println("Answer: " + (int1 + int2));
        }
    }

    class MenuCommand extends FiCommand {
        public MenuCommand(String commandString) {
            super(commandString);
            this.commandDescription = "A menu with more commands";
            this.isVisible = false;
        }

        @Override
        public void execute() {
            FiCLI menuCLI = new FiCLI();
            menuCLI.setCaret("Menu > ");

            //Enables the exit command/state, enables the back command/state.
            menuCLI.allowAdditionalStates(true, true);
            menuCLI.addCommand(new AboutCommand("about"));

            while(true) {
                FiState state = menuCLI.processCommand();

                if(state.state == FiState.EXIT) {
                    System.exit(0);
                }

                //Gets us out of this menu...
                if(state.state == FiState.BACK) {
                    break;
                }

                if(state.state == FiState.INVALID) {
                    System.out.println("Invalid command");
                }
            }
        }
    }

You should now have a solid understanding of the functionality and the majority of features provided by FiCLI!
Now go out and make great CLIs!

## I/O Routing

By default, FiCLI's input/output is from/to the console.
For most applications, this is all that is needed.
However, FiCLI allows you to direct I/O in different ways if needed.
To do this, we need to create classes that implement the FiInputStream and FiOutputStream interfaces.

Below is the default class that is utilized by FiCLI as of version 1.0.0.
All you need to do is implement the readLine, print, and println abstract methods.

    import java.util.Scanner;

    public class FiConsoleIO implements FiInputStream, FiOutputStream {

        Scanner console;

        public FiConsoleIO() {
            console = new Scanner(System.in);
        }

        @Override
        public String readLine() {
            return console.nextLine();
        }

        @Override
        public void print(String s) {
            System.out.print(s);
        }

        @Override
        public void println(String s) {
            System.out.println(s);
        }
    }    

The readLine method is what FiCLI will use for input.
Give it any String that you want it to examine for commands.
The print and println methods are what the caret is output to, as well as listing commands and command descriptions.
As you can see, you have practically an unlimited amount of options as far as routing goes.
This feature could be useful if you are remoting into some sort of server or service and require a CLI.
You can then tell FiCLI to use this new IO system with the setInput and setOutput methods, or with one of the FiCLI constructors that have IO parameters.
