package net.jaijorlon.cardinal.util;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.jaijorlon.cardinal.Cardinal;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.threetag.palladium.util.property.EntityPropertyHandler;
import net.threetag.palladium.util.property.PalladiumProperty;
import net.threetag.palladium.util.property.PalladiumPropertyLookup;

import javax.smartcardio.Card;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class PalladiumPropertyUtil {
    public static void registerProperty(Entity entity, String propertyName, String ValueType, Object defaultValue) {
        PalladiumProperty property = PalladiumPropertyLookup.get(ValueType, propertyName);

        if (property != null) {
            EntityPropertyHandler.getHandler(entity).ifPresent(handler -> {
                handler.register(property, PalladiumProperty.fixValues(property, defaultValue));
            });
        }
    }

    public static List<String> getAllProperties(Entity entity) {
        List<String> result = new ArrayList<>();
        CompoundTag entityData = new CompoundTag();

        entity.saveWithoutId(entityData);

        for (String property : entityData.getCompound("Palladium").getCompound("Properties").toString().split(",")) {
            if (property != null) {
                if (!property.split(":")[0].contains("forward_key_down") && !property.split(":")[0].contains("backwards_key_down") && !property.split(":")[0].contains("left_key_down") && !property.split(":")[0].contains("right_key_down") && !property.split(":")[0].contains("jump_key_down") && !property.split(":")[0].contains("superpowers")) {
                    result.add(property.split(":")[0]);
                }
            }
        }

        return result;
    }

    public static void setValue(Entity entity, String propertyName, Object value) {
        if (value == null) return;
        EntityPropertyHandler.getHandler(entity).ifPresent(handler -> {
            PalladiumProperty property = handler.getPropertyByName(propertyName);

            if (handler.get(property) != null) {
                handler.set(property, PalladiumProperty.fixValues(property, value));
            }
        });
    }

    public static void addValue(Entity entity, String propertyName, Object value) {
        if (value == null) return;
        EntityPropertyHandler.getHandler(entity).ifPresent(handler -> {
            PalladiumProperty property = handler.getPropertyByName(propertyName);

            if (handler.get(property) != null) {
                Number numValue = null;

                if (value instanceof Number valueNumber) {
                    numValue = addNumbers((Number) handler.get(property), valueNumber);
                }

                if (numValue != null) {
                    handler.set(property, PalladiumProperty.fixValues(property, numValue));
                } else {
                    handler.set(property, PalladiumProperty.fixValues(property, value));
                }
            }
        });
    }

    public static void subValue(Entity entity, String propertyName, Object value) {
        if (value == null) return;
        EntityPropertyHandler.getHandler(entity).ifPresent(handler -> {
            PalladiumProperty property = handler.getPropertyByName(propertyName);

            if (handler.get(property) != null) {
                Number numValue = null;

                if (value instanceof Number valueNumber) {
                    numValue = subNumbers((Number) handler.get(property), valueNumber);
                }

                if (numValue != null) {
                    handler.set(property, PalladiumProperty.fixValues(property, numValue));
                } else {
                    handler.set(property, PalladiumProperty.fixValues(property, value));
                }
            }
        });
    }

    public static void divValue(Entity entity, String propertyName, Object value) {
        if (value == null) return;
        EntityPropertyHandler.getHandler(entity).ifPresent(handler -> {
            PalladiumProperty property = handler.getPropertyByName(propertyName);

            if (handler.get(property) != null) {
                Number numValue = null;

                if (value instanceof Number valueNumber) {
                    numValue = divNumbers((Number) handler.get(property), valueNumber);
                }

                if (numValue != null) {
                    handler.set(property, PalladiumProperty.fixValues(property, numValue));
                } else {
                    handler.set(property, PalladiumProperty.fixValues(property, value));
                }
            }
        });
    }

    public static void mulValue(Entity entity, String propertyName, Object value) {
        if (value == null) return;
        EntityPropertyHandler.getHandler(entity).ifPresent(handler -> {
            PalladiumProperty property = handler.getPropertyByName(propertyName);

            if (handler.get(property) != null) {
                Number numValue = null;

                if (value instanceof Number valueNumber) {
                    numValue = mulNumbers((Number) handler.get(property), valueNumber);
                }

                if (numValue != null) {
                    handler.set(property, PalladiumProperty.fixValues(property, numValue));
                } else {
                    handler.set(property, PalladiumProperty.fixValues(property, value));
                }
            }
        });
    }

    public static void resetValue(Entity entity, String propertyName) {
        EntityPropertyHandler.getHandler(entity).ifPresent(handler -> {
            PalladiumProperty property = handler.getPropertyByName(propertyName);

            if (handler.get(property) != null) {
                handler.reset(property);
            }
        });
    }
// add the error catch
    public static void setValue(Entity entity, String propertyName, Object value, CommandSourceStack source) {
        EntityPropertyHandler.getHandler(entity).ifPresent(handler -> {
            PalladiumProperty property = handler.getPropertyByName(propertyName);

            if (handler.get(property) != null) {
                if (value == null || !property.getType().getClass().equals(value.getClass())) {
                    Cardinal.LOGGER.info(String.valueOf(value.getClass()));
                    Cardinal.LOGGER.info(String.valueOf(property.getType().getClass()));
                    source.sendFailure(Component.literal("Failed to change " + entity.getDisplayName().getString() + "'s property value"));
                    return;
                }
                handler.set(property, PalladiumProperty.fixValues(property, value));
                source.sendSuccess(() -> Component.literal(entity.getDisplayName().getString() + "'s " + propertyName + " property value has change to => " + PalladiumPropertyUtil.getValue(entity, propertyName)),true);
            } else {
                source.sendFailure(Component.literal(entity.getDisplayName().getString() + " Has no such property: " + propertyName));
            }
        });
    }

    public static void addValue(Entity entity, String propertyName, Object value, CommandSourceStack source) {
        EntityPropertyHandler.getHandler(entity).ifPresent(handler -> {
            PalladiumProperty property = handler.getPropertyByName(propertyName);

            if (handler.get(property) != null) {
                if (value == null || !property.getType().getClass().equals(value.getClass())) {
                    source.sendFailure(Component.literal("Failed to change " + entity.getDisplayName().getString() + "'s property value"));
                    return;
                }
                Number numValue = null;

                if (value instanceof Number valueNumber) {
                    numValue = addNumbers((Number) handler.get(property), valueNumber);
                }

                if (numValue != null) {
                    handler.set(property, PalladiumProperty.fixValues(property, numValue));
                    source.sendSuccess(() -> Component.literal(entity.getDisplayName().getString() + "'s " + propertyName + " property value has change to => " + PalladiumPropertyUtil.getValue(entity, propertyName)),true);
                } else {
                    handler.set(property, PalladiumProperty.fixValues(property, value));
                    source.sendSuccess(() -> Component.literal(entity.getDisplayName().getString() + "'s " + propertyName + " property value has change to => " + PalladiumPropertyUtil.getValue(entity, propertyName)),true);
                }
            } else {
                source.sendFailure(Component.literal(entity.getDisplayName().getString() + " Has no such property: " + propertyName));
            }
        });
    }

    public static void subValue(Entity entity, String propertyName, Object value, CommandSourceStack source) {
        EntityPropertyHandler.getHandler(entity).ifPresent(handler -> {
            PalladiumProperty property = handler.getPropertyByName(propertyName);

            if (handler.get(property) != null) {
                if (value == null || !property.getType().getClass().equals(value.getClass())) {
                    source.sendFailure(Component.literal("Failed to change " + entity.getDisplayName().getString() + "'s property value"));
                    return;
                }
                Number numValue = null;

                if (value instanceof Number valueNumber) {
                    numValue = subNumbers((Number) handler.get(property), valueNumber);
                }

                if (numValue != null) {
                    handler.set(property, PalladiumProperty.fixValues(property, numValue));
                    source.sendSuccess(() -> Component.literal(entity.getDisplayName().getString() + "'s " + propertyName + " property value has change to => " + PalladiumPropertyUtil.getValue(entity, propertyName)),true);
                } else {
                    handler.set(property, PalladiumProperty.fixValues(property, value));
                    source.sendSuccess(() -> Component.literal(entity.getDisplayName().getString() + "'s " + propertyName + " property value has change to => " + PalladiumPropertyUtil.getValue(entity, propertyName)),true);
                }
            } else {
                source.sendFailure(Component.literal(entity.getDisplayName().getString() + " Has no such property: " + propertyName));
            }
        });
    }

    public static void divValue(Entity entity, String propertyName, Object value, CommandSourceStack source) {
        EntityPropertyHandler.getHandler(entity).ifPresent(handler -> {
            PalladiumProperty property = handler.getPropertyByName(propertyName);

            if (handler.get(property) != null) {
                if (value == null || !property.getType().getClass().equals(value.getClass())) {
                    source.sendFailure(Component.literal("Failed to change " + entity.getDisplayName().getString() + "'s property value"));
                    return;
                }
                Number numValue = null;

                if (value instanceof Number valueNumber) {
                    numValue = divNumbers((Number) handler.get(property), valueNumber);
                }

                if (numValue != null) {
                    handler.set(property, PalladiumProperty.fixValues(property, numValue));
                    source.sendSuccess(() -> Component.literal(entity.getDisplayName().getString() + "'s " + propertyName + " property value has change to => " + PalladiumPropertyUtil.getValue(entity, propertyName)),true);
                } else {
                    handler.set(property, PalladiumProperty.fixValues(property, value));
                    source.sendSuccess(() -> Component.literal(entity.getDisplayName().getString() + "'s " + propertyName + " property value has change to => " + PalladiumPropertyUtil.getValue(entity, propertyName)),true);
                }
            } else {
                source.sendFailure(Component.literal(entity.getDisplayName().getString() + " Has no such property: " + propertyName));
            }
        });
    }

    public static void mulValue(Entity entity, String propertyName, Object value, CommandSourceStack source) {
        EntityPropertyHandler.getHandler(entity).ifPresent(handler -> {
            PalladiumProperty property = handler.getPropertyByName(propertyName);

            if (handler.get(property) != null) {
                if (value == null || !property.getType().getClass().equals(value.getClass())) {
                    source.sendFailure(Component.literal("Failed to change " + entity.getDisplayName().getString() + "'s property value"));
                    return;
                }
                Number numValue = null;

                if (value instanceof Number valueNumber) {
                    numValue = mulNumbers((Number) handler.get(property), valueNumber);
                }

                if (numValue != null) {
                    handler.set(property, PalladiumProperty.fixValues(property, numValue));
                    source.sendSuccess(() -> Component.literal(entity.getDisplayName().getString() + "'s " + propertyName + " property value has change to => " + PalladiumPropertyUtil.getValue(entity, propertyName)),true);
                } else {
                    handler.set(property, PalladiumProperty.fixValues(property, value));
                    source.sendSuccess(() -> Component.literal(entity.getDisplayName().getString() + "'s " + propertyName + " property value has change to => " + PalladiumPropertyUtil.getValue(entity, propertyName)),true);
                }
            } else {
                source.sendFailure(Component.literal(entity.getDisplayName().getString() + " Has no such property: " + propertyName));
            }
        });
    }

    public static void resetValue(Entity entity, String propertyName, CommandSourceStack source) {
        EntityPropertyHandler.getHandler(entity).ifPresent(handler -> {
            PalladiumProperty property = handler.getPropertyByName(propertyName);

            if (handler.get(property) != null) {
                handler.reset(property);
                source.sendSuccess(() -> Component.literal(entity.getDisplayName().getString() + "'s " + propertyName + " property has been reset"),true);
            } else {
                source.sendFailure(Component.literal(entity.getDisplayName().getString() + " Has no such property: " + propertyName));
            }
        });
    }

    public static Object getValue(Entity entity, String propertyName) {
        AtomicReference<Object> value = new AtomicReference<>();

        EntityPropertyHandler.getHandler(entity).ifPresent(handler -> {
            PalladiumProperty<?> property = handler.getPropertyByName(propertyName);

            if (handler.get(property) != null) {
                value.set(PalladiumProperty.fixValues(property, handler.get(property)));
            }
        });

        if (value.get() != null) {
            return value.get();
        }

        return null;
    }

    public static String getString(Entity entity, String propertyName) {
        AtomicReference<Object> value = new AtomicReference<>();

        EntityPropertyHandler.getHandler(entity).ifPresent(handler -> {
            PalladiumProperty<?> property = handler.getPropertyByName(propertyName);

            if (handler.get(property) != null) {
                value.set(PalladiumProperty.fixValues(property, handler.get(property)));
            }
        });

        if (value.get() != null) {
            return (String) value.get();
        }

        return null;
    }

    public static Integer getInt(Entity entity, String propertyName) {
        AtomicReference<Object> value = new AtomicReference<>();

        EntityPropertyHandler.getHandler(entity).ifPresent(handler -> {
            PalladiumProperty<?> property = handler.getPropertyByName(propertyName);

            if (handler.get(property) != null) {
                value.set(PalladiumProperty.fixValues(property, handler.get(property)));
            }
        });

        if (value.get() != null) {
            return (Integer) value.get();
        }

        return null;
    }

    public static Float getFloat(Entity entity, String propertyName) {
        AtomicReference<Object> value = new AtomicReference<>();

        EntityPropertyHandler.getHandler(entity).ifPresent(handler -> {
            PalladiumProperty<?> property = handler.getPropertyByName(propertyName);

            if (handler.get(property) != null) {
                value.set(PalladiumProperty.fixValues(property, handler.get(property)));
            }
        });

        if (value.get() != null) {
            return (Float) value.get();
        }

        return null;
    }

    public static Double getDouble(Entity entity, String propertyName) {
        AtomicReference<Object> value = new AtomicReference<>();

        EntityPropertyHandler.getHandler(entity).ifPresent(handler -> {
            PalladiumProperty<?> property = handler.getPropertyByName(propertyName);

            if (handler.get(property) != null) {
                value.set(PalladiumProperty.fixValues(property, handler.get(property)));
            }
        });

        if (value.get() != null) {
            return (Double) value.get();
        }

        return null;
    }

    public static Boolean getBoolean(Entity entity, String propertyName) {
        AtomicReference<Object> value = new AtomicReference<>();

        EntityPropertyHandler.getHandler(entity).ifPresent(handler -> {
            PalladiumProperty<?> property = handler.getPropertyByName(propertyName);

            if (handler.get(property) != null) {
                value.set(PalladiumProperty.fixValues(property, handler.get(property)));
            }
        });

        if (value.get() != null) {
            return (Boolean) value.get();
        }

        return null;
    }

    private static Number addNumbers(Number current, Number addition) {
        if (current instanceof Integer) {
            return current.intValue() + addition.intValue();
        }
        if (current instanceof Float) {
            return current.floatValue() + addition.floatValue();
        }
        if (current instanceof Double) {
            return current.doubleValue() + addition.doubleValue();
        }
        if (current instanceof Long) {
            return current.longValue() + addition.longValue();
        }
        if (current instanceof Short) {
            return (short) (current.shortValue() + addition.shortValue());
        }
        if (current instanceof Byte) {
            return (byte) (current.byteValue() + addition.byteValue());
        }

        return current.doubleValue() + addition.doubleValue();
    }

    private static Number subNumbers(Number current, Number subtraction) {
        if (current instanceof Integer) {
            return current.intValue() - subtraction.intValue();
        }
        if (current instanceof Float) {
            return current.floatValue() - subtraction.floatValue();
        }
        if (current instanceof Double) {
            return current.doubleValue() - subtraction.doubleValue();
        }
        if (current instanceof Long) {
            return current.longValue() - subtraction.longValue();
        }
        if (current instanceof Short) {
            return (short) (current.shortValue() - subtraction.shortValue());
        }
        if (current instanceof Byte) {
            return (byte) (current.byteValue() - subtraction.byteValue());
        }

        return current.doubleValue() - subtraction.doubleValue();
    }

    private static Number divNumbers(Number current, Number divide) {
        if (current instanceof Integer) {
            return current.intValue() / divide.intValue();
        }
        if (current instanceof Float) {
            return current.floatValue() / divide.floatValue();
        }
        if (current instanceof Double) {
            return current.doubleValue() / divide.doubleValue();
        }
        if (current instanceof Long) {
            return current.longValue() / divide.longValue();
        }
        if (current instanceof Short) {
            return (short) (current.shortValue() / divide.shortValue());
        }
        if (current instanceof Byte) {
            return (byte) (current.byteValue() / divide.byteValue());
        }

        return current.doubleValue() / divide.doubleValue();
    }

    private static Number mulNumbers(Number current, Number multiply) {
        if (current instanceof Integer) {
            return current.intValue() / multiply.intValue();
        }
        if (current instanceof Float) {
            return current.floatValue() / multiply.floatValue();
        }
        if (current instanceof Double) {
            return current.doubleValue() / multiply.doubleValue();
        }
        if (current instanceof Long) {
            return current.longValue() / multiply.longValue();
        }
        if (current instanceof Short) {
            return (short) (current.shortValue() / multiply.shortValue());
        }
        if (current instanceof Byte) {
            return (byte) (current.byteValue() / multiply.byteValue());
        }

        return current.doubleValue() / multiply.doubleValue();
    }
}
