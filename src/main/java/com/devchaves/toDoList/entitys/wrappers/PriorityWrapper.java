package com.devchaves.toDoList.entitys.wrappers;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public class PriorityWrapper {

    public enum Priority {
        LOW("Baixa"),
        MEDIUM("Média"),
        HIGH("Alta");

        private final String label;

        Priority(String label) {
            this.label = label;
        }

        @JsonValue
        public String getLabel() {
            return label;
        }

        @JsonCreator
        public static Priority fromString(String value) {
            for (Priority priority : Priority.values()) {
                if (priority.label.equalsIgnoreCase(value)) {
                    return priority;
                }
            }
            throw new IllegalArgumentException("Prioridade inválida: " + value);
        }
    }

}
