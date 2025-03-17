package com.devchaves.toDoList.entitys.wrappers;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public class StatusWrapper {

    public enum Status {
        PENDING("Pendente"),
        IN_PROGRESS("Em Andamento"),
        COMPLETED("Concluído");

        private final String label;

        Status(String label) {
            this.label = label;
        }

        @JsonValue
        public String getLabel() {
            return label;
        }

        @JsonCreator
        public static Status fromString(String value) {
            for (Status status : Status.values()) {
                if (status.label.equalsIgnoreCase(value)) {
                    return status;
                }
            }
            throw new IllegalArgumentException("Status inválido: " + value);
        }
    }
}

