package softuni.gamestore.demo.command;

import org.springframework.stereotype.Component;
import softuni.gamestore.demo.command.commandModels.Command;
import softuni.gamestore.demo.command.commandModels.LoginUser;
import softuni.gamestore.demo.command.commandModels.LogoutUser;
import softuni.gamestore.demo.command.commandModels.RegisterUser;
import softuni.gamestore.demo.service.gameService.GameService;
import softuni.gamestore.demo.service.roleService.RoleService;
import softuni.gamestore.demo.service.userService.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public final class CommandFactory {

    private final Map<String, Command> commands;

    public CommandFactory() {
        this.commands = new HashMap<>();
    }

    private void addCommand(final String name, final Command command){
        commands.putIfAbsent(name, command);
    }

    public void executeCommand(String[] tokens){
        String command = tokens[0];
        if (commands.containsKey(command)) {
            commands.get(command).applay(tokens);
        }
    }

    public void listCommands(){
        System.out.println(
                "Enable commands: "
                        + commands.keySet().stream().collect(Collectors.joining(", ")));
    }

    public static CommandFactory init(GameService gameService, UserService userService, RoleService roleService){
        final CommandFactory cf = new CommandFactory();
        // It is possible to dynamically add commands without editing the code.
        cf.addCommand("RegisterUser", new RegisterUser(userService));
        cf.addCommand("LoginUser", new LoginUser(userService));
        cf.addCommand("LogoutUser", new LogoutUser(userService));

        return cf;
    }
}
