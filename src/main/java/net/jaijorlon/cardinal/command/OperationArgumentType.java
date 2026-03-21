package net.jaijorlon.cardinal.command;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class OperationArgumentType implements ArgumentType<String> {
    public static final DynamicCommandExceptionType exceptionType =
        new DynamicCommandExceptionType(object ->
            Component.literal("Invalid Operation " + object)
        );

    public static String getOperation(CommandContext<?> context, String operation) {
        return context.getArgument(operation, String.class);
    }

    @Override
    public String parse(StringReader reader) throws CommandSyntaxException {
        String s = reader.readUnquotedString().toLowerCase();
        return switch (s) {
            case "set" -> "set";
            case "add" -> "add";
            case "subtract" -> "subtract";
            case "multiply" -> "multiply";
            case "divide" -> "divide";
            default -> throw exceptionType.createWithContext(reader, s);
        };
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return SharedSuggestionProvider.suggest(
                new ArrayList<>(Arrays.asList("set", "add", "subtract", "multiply", "divide")),
                builder
        );
    }
    
    @Override
    public Collection<String> getExamples() {
        return new ArrayList<>(Arrays.asList("set", "add", "subtract", "multiply", "divide"));
    }
}
