package net.ilexiconn.llibrary.server.command;

import com.google.common.collect.Lists;
import net.ilexiconn.llibrary.server.command.argument.Argument;
import net.ilexiconn.llibrary.server.command.argument.ArgumentTypes;
import net.ilexiconn.llibrary.server.command.argument.CommandArguments;
import net.ilexiconn.llibrary.server.util.ListHashMap;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author iLexiconn
 * @since 1.0.0
 */
public class Command extends CommandBase {
    private String name;
    private String commandUsage;
    private int permissionLevel = 4;
    private ListHashMap<String, ArgumentTypes> requiredArguments = new ListHashMap<>();
    private ListHashMap<String, ArgumentTypes> optionalArguments = new ListHashMap<>();
    private ICommandExecutor executor;

    private Command(String name) {
        this.name = name;
    }

    public static Command create(String name) {
        return new Command(name);
    }

    public Command setPermissionLevel(int permissionLevel) {
        this.permissionLevel = permissionLevel;
        return this;
    }

    public Command addRequiredArgument(String argument, ArgumentTypes argumentType) {
        this.requiredArguments.put(argument, argumentType);
        return this;
    }

    public Command addOptionalArgument(String argument, ArgumentTypes argumentType) {
        this.optionalArguments.put(argument, argumentType);
        return this;
    }

    Command setExector(ICommandExecutor executor) {
        this.executor = executor;
        return this;
    }

    @Override
    public String getCommandName() {
        return this.name;
    }

    @Override
    public int getRequiredPermissionLevel() {
        return this.permissionLevel;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        if (this.commandUsage == null) {
            StringBuilder builder = new StringBuilder();
            builder.append("/");
            builder.append(getCommandName());
            for (String requiredArgument : this.requiredArguments.keySet()) {
                builder.append(" ");
                builder.append("<");
                builder.append(requiredArgument);
                builder.append(">");
            }
            for (String optionalArgument : this.optionalArguments.keySet()) {
                builder.append(" ");
                builder.append("[");
                builder.append(optionalArgument);
                builder.append("]");
            }
            this.commandUsage = builder.toString();
            return this.commandUsage;
        } else {
            return this.commandUsage;
        }
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length < this.requiredArguments.size()) {
            throw new WrongUsageException(getCommandUsage(sender));
        } else if (args.length > this.requiredArguments.size() + this.optionalArguments.size()) {
            throw new WrongUsageException(getCommandUsage(sender));
        } else {
            List<Argument> arguments = Lists.newArrayList();
            for (int i = 0; i < args.length; i++) {
                if (i < this.requiredArguments.size()) {
                    Map.Entry<String, ArgumentTypes> entry = this.requiredArguments.getEntry(i);
                    arguments.add(new Argument(entry.getKey(), args[i], entry.getValue()));
                } else {
                    Map.Entry<String, ArgumentTypes> entry = this.optionalArguments.getEntry(i - this.requiredArguments.size());
                    arguments.add(new Argument(entry.getKey(), args[i], entry.getValue()));
                }
            }
            this.executor.execute(server, sender, new CommandArguments(arguments, sender));
        }
    }

    @Override
    public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) {
        if (args.length <= this.requiredArguments.size()) {
            return this.requiredArguments.getValue(args.length - 1).getTabCompletion(server, sender, args, pos);
        } else if (args.length <= this.requiredArguments.size() + this.optionalArguments.size()) {
            return this.optionalArguments.getValue(args.length - this.requiredArguments.size() - 1).getTabCompletion(server, sender, args, pos);
        } else {
            return Collections.emptyList();
        }
    }
}
