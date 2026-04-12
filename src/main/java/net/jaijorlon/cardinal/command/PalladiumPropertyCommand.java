package net.jaijorlon.cardinal.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.jaijorlon.cardinal.util.PalladiumPropertyUtil;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import org.apache.commons.lang3.Validate;

import java.util.Collection;
import java.util.List;

public class PalladiumPropertyCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> builder = Commands
                .literal("property")
                .requires(source -> source.hasPermission(2));

        builder.then(Commands.argument("modify_type", new OperationArgumentType())
                        .then(Commands.argument("property_name", StringArgumentType.word())
                                .suggests((ctx, suggestionsBuilder) -> {
                                    Entity entity = ctx.getSource().getEntity();
                                    Validate.isTrue(entity != null);
                                    List<String> propertyNames = PalladiumPropertyUtil.getAllProperties(entity);

                                    for (String propertyName : propertyNames) {
                                        suggestionsBuilder.suggest(propertyName);
                                    }

                                    return suggestionsBuilder.buildFuture();
                                })
                                .then(Commands.argument("value", StringArgumentType.word())
                                        .executes(context -> {
                                            Entity entity = context.getSource().getEntity();
                                            String propertyName = StringArgumentType.getString(context, "property_name");
                                            String modifyType = OperationArgumentType.getOperation(context, "modify_type");
                                            Validate.isTrue(entity != null);

                                            if (modifyType.equals("set")) {
                                                PalladiumPropertyUtil.setValue(entity, propertyName, stringToObject(StringArgumentType.getString(context, "value"), context.getSource(), entity), context.getSource());
                                            }
                                            if (modifyType.equals("add")) {
                                                PalladiumPropertyUtil.addValue(entity, propertyName, stringToObject(StringArgumentType.getString(context, "value"), context.getSource(), entity), context.getSource());
                                            }
                                            if (modifyType.equals("subtract")) {
                                                PalladiumPropertyUtil.subValue(entity, propertyName, stringToObject(StringArgumentType.getString(context, "value"), context.getSource(), entity), context.getSource());
                                            }
                                            if (modifyType.equals("multiply")) {
                                                PalladiumPropertyUtil.mulValue(entity, propertyName, stringToObject(StringArgumentType.getString(context, "value"), context.getSource(), entity), context.getSource());
                                            }
                                            if (modifyType.equals("divide")) {
                                                PalladiumPropertyUtil.divValue(entity, propertyName, stringToObject(StringArgumentType.getString(context, "value"), context.getSource(), entity), context.getSource());
                                            }

                                            return 1;
                                        })
                                        .then(Commands.argument("entities", EntityArgument.entities())
                                                .executes(context -> {
                                                    Collection<? extends Entity> entities = EntityArgument.getEntities(context, "entities");
                                                    String propertyName = StringArgumentType.getString(context, "property_name");
                                                    String modifyType = OperationArgumentType.getOperation(context, "modify_type");

                                                    for (Entity entity : entities) {
                                                        Validate.isTrue(entity != null);
                                                        if (modifyType.equals("set")) {
                                                            PalladiumPropertyUtil.setValue(entity, propertyName, stringToObject(StringArgumentType.getString(context, "value"), context.getSource(), entity), context.getSource());
                                                        }
                                                        if (modifyType.equals("add")) {
                                                            PalladiumPropertyUtil.addValue(entity, propertyName, stringToObject(StringArgumentType.getString(context, "value"), context.getSource(), entity), context.getSource());
                                                        }
                                                        if (modifyType.equals("subtract")) {
                                                            PalladiumPropertyUtil.subValue(entity, propertyName, stringToObject(StringArgumentType.getString(context, "value"), context.getSource(), entity), context.getSource());
                                                        }
                                                        if (modifyType.equals("multiply")) {
                                                            PalladiumPropertyUtil.mulValue(entity, propertyName, stringToObject(StringArgumentType.getString(context, "value"), context.getSource(), entity), context.getSource());
                                                        }
                                                        if (modifyType.equals("divide")) {
                                                            PalladiumPropertyUtil.divValue(entity, propertyName, stringToObject(StringArgumentType.getString(context, "value"), context.getSource(), entity), context.getSource());
                                                        }
                                                    }
                                                    return entities.size();
                                                })
                                        )
                                )
                        )
                )
                .then(Commands.literal("reset")
                        .then(Commands.argument("property_name", StringArgumentType.word())
                                .suggests((ctx, suggestionsBuilder) -> {
                                    Entity entity = ctx.getSource().getEntity();
                                    Validate.isTrue(entity != null);
                                    List<String> propertyNames = PalladiumPropertyUtil.getAllProperties(entity);

                                    for (String propertyName : propertyNames) {
                                        suggestionsBuilder.suggest(propertyName);
                                    }

                                    return suggestionsBuilder.buildFuture();
                                })
                                .executes(context -> {
                                    Entity entity = context.getSource().getEntity();

                                    Validate.isTrue(entity != null);

                                    PalladiumPropertyUtil.resetValue(entity, StringArgumentType.getString(context, "property_name"), context.getSource());

                                    return 1;
                                })
                                .then(Commands.argument("entities", EntityArgument.entities())
                                        .executes(context -> {
                                            Collection<? extends Entity> entities = EntityArgument.getEntities(context, "entities");

                                            for (Entity entity : entities) {
                                                Validate.isTrue(entity != null);

                                                PalladiumPropertyUtil.resetValue(entity, StringArgumentType.getString(context, "property_name"), context.getSource());
                                            }

                                            return entities.size();
                                        })
                                )
                        )
                ).then(Commands.literal("query")
                        .then(Commands.argument("property_name", StringArgumentType.word())
                                .suggests((ctx, suggestionsBuilder) -> {
                                    Entity entity = ctx.getSource().getEntity();
                                    Validate.isTrue(entity != null);
                                    List<String> propertyNames = PalladiumPropertyUtil.getAllProperties(entity);

                                    for (String propertyName : propertyNames) {
                                        suggestionsBuilder.suggest(propertyName);
                                    }

                                    return suggestionsBuilder.buildFuture();
                                })
                                .executes(context -> {
                                    Entity entity = context.getSource().getEntity();
                                    String propertyName = StringArgumentType.getString(context, "property_name");

                                    Validate.isTrue(entity != null);

                                    context.getSource().sendSuccess(() -> Component.literal(entity.getDisplayName().getString() + "'s " + propertyName + " property has a value of => " + PalladiumPropertyUtil.getValue(entity, propertyName)), true);

                                    return 1;
                                })
                                .then(Commands.argument("entities", EntityArgument.entities())
                                        .executes(context -> {
                                            Collection<? extends Entity> entities = EntityArgument.getEntities(context, "entities");
                                            String propertyName = StringArgumentType.getString(context, "property_name");

                                            for (Entity entity : entities) {
                                                Validate.isTrue(entity != null);

                                                context.getSource().sendSuccess(() -> Component.literal(entity.getDisplayName().getString() + "'s " + propertyName + " property has a value of => " + PalladiumPropertyUtil.getValue(entity, propertyName)), true);
                                            }

                                            return entities.size();
                                        })
                                )
                        )
                );

        dispatcher.register(builder);
    }

    private static Object stringToObject(String value, CommandSourceStack source, Entity entity) {
        String valueType = checkNumberType(value);

        if (value.equals("true") || value.equals("1b")) {
            return true;
        } else if (value.equals("false") || value.equals("0b")) {
            return false;
        } else if (valueType.equals("Integer")) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                source.sendFailure(Component.literal("Failed to change " + entity.getDisplayName().getString() + "'s property value"));
                return null;
            }
        } else if (valueType.equals("Float")) {
            try {
                return Float.parseFloat(value);
            } catch (NumberFormatException e) {
                source.sendFailure(Component.literal("Failed to change " + entity.getDisplayName().getString() + "'s property value"));
                return null;
            }
        } else if (valueType.equals("Double")) {
            try {
                return Double.parseDouble(value);
            } catch (NumberFormatException e) {
                source.sendFailure(Component.literal("Failed to change " + entity.getDisplayName().getString() + "'s property value"));
                return null;
            }
        }

        return value;
    }

    private static String checkNumberType(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }

        try {
            Integer.parseInt(str);
            return "Integer";
        } catch (NumberFormatException ignored) {}

        try {
            Double.parseDouble(str);
            if (str.contains(".")) {
                return "Float";
            } else {
                return "Integer";
            }
        } catch (NumberFormatException ignored) {
        }

        return null;
    }
}