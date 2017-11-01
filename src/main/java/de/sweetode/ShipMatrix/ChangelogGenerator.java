package de.sweetode.ShipMatrix;

import de.sweetode.ShipMatrix.diff.Ship;
import de.sweetode.ShipMatrix.diff.report.DiffReport;
import de.sweetode.ShipMatrix.diff.types.DataFields;
import de.sweetode.ShipMatrix.diff.types.DiffTypes;

import java.io.IOException;
import java.util.Map;

public class ChangelogGenerator {

    public static void main(String[] args) throws IOException {
        CompareMatrix compareMatrix = new CompareMatrix();
        Map<String, Map<Ship, DiffReport>> report = compareMatrix.diffAll();

        //--- Filter
        report.forEach((key, value) -> value.entrySet().removeIf(e -> e.getValue().getShipFieldChanges().isEmpty() && e.getValue().getCompiledChanges().isEmpty() && e.getValue().getTotalShipStatus() == null));

        System.out.println(new ChangelogGenerator(report).generateHTML());
    }

    //---
    private final Map<String, Map<Ship, DiffReport>> reports;

    public ChangelogGenerator(Map<String, Map<Ship, DiffReport>> reports) {
        this.reports = reports;
    }


    public String generateHTML() {

        StringBuilder builder = new StringBuilder();

        builder.append("<ul>");

        reports.forEach((date, ships) -> {

            builder.append("<li>").append(date.substring(0, date.indexOf('.')));
            builder.append("<ul>");

            ships.forEach((ship, report) -> {

                builder.append(String.format("<li><a href=\"https:/robertsspaceindustries.com%s\">%s</a></li>", ship.getValues().get(DataFields.URL), ship.getValues().get(DataFields.NAME)));
                builder.append("<ul>");

                //--- NEW SHIP ADDED
                if (!(report.getTotalShipStatus() == null)) {
                    if (report.getTotalShipStatus() == DiffTypes.ADDED) {
                        builder.append("<li>This is a completely new ship!</li>");
                    } else {
                        builder.append("<li>This ship got removed!</li>");
                    }
                } else {

                    //--- Ship Fields
                    report.getShipFieldChanges().forEach(field -> {

                        builder.append("<li>");
                        switch (field.getDiffType()) {

                            case ADDED:
                                builder.append("Added ").append(field.getDataType().getName()).append(" ").append(field.getChanged());
                                break;
                            case MODIFIED:
                                builder.append("Modified ").append(field.getDataType().getName()).append(" from ").append(field.getOriginal()).append(" to ").append(field.getChanged());
                                break;
                            case REMOVED:
                                builder.append("Removed ").append(field.getDataType().getName());
                                break;
                        }

                        builder.append("</li>");

                    });

                    //--- Components
                    report.getCompiledChanges().forEach((parent, componentType) -> {

                        builder.append("<ul>");
                        builder.append("<li>").append(parent).append("</li>");

                        componentType.forEach((componentTypeField, list) -> {

                            builder.append("<ul>");
                            builder.append("<li>").append(componentTypeField.getName()).append("</li>");

                            builder.append("<ul>");
                            list.forEach((k, dataPointList) -> {

                                builder.append("<li>").append(k).append("</li>");
                                builder.append("<ul>");

                                dataPointList.forEach(b -> {
                                    builder.append("<li>");
                                    switch (b.getDiffType()) {
                                        case ADDED:

                                            switch (b.getDataType()) {

                                                case PARENT:
                                                    builder.append("Added ").append(b.getParent()).append(" ").append(b.getChanged());
                                                    break;
                                                case COMPONENT_TYPE:
                                                    builder.append("Added ").append(b.getComponentType()).append(" ").append(b.getChanged());
                                                    break;
                                                case COMPONENT:
                                                    builder.append("Added ").append(b.getComponentName());
                                                    break;
                                            }

                                            break;
                                        case MODIFIED:

                                            switch (b.getDataType()) {

                                                case PARENT:
                                                    builder.append("Modified ").append(b.getParent()).append(" from ").append(b.getOriginal()).append(" to ").append(b.getChanged());
                                                    break;
                                                case COMPONENT_TYPE:
                                                    builder.append("Modified ").append(b.getComponentType()).append(" from ").append(b.getOriginal()).append(" to ").append(b.getChanged());
                                                    break;
                                                case COMPONENT:
                                                    builder.append("Modified ").append(b.getField().getName()).append(" from ").append(b.getOriginal()).append(" to ").append(b.getChanged());
                                                    break;
                                            }

                                            break;

                                        case REMOVED:

                                            switch (b.getDataType()) {

                                                case PARENT:
                                                    builder.append("Removed ").append(b.getParent());
                                                    break;
                                                case COMPONENT_TYPE:
                                                    builder.append("Removed ").append(b.getComponentType());
                                                    break;
                                                case COMPONENT:
                                                    builder.append("Removed ").append(b.getComponentName());
                                                    break;
                                            }

                                            break;
                                    }
                                    builder.append("</li>");

                                });

                                builder.append("</ul>");

                            });
                            builder.append("</ul>");

                            builder.append("</ul>");

                        });

                        builder.append("</ul>");
                    });

                }

                builder.append("</ul>");

            });

            builder.append("</ul>");

        });

        builder.append("</ul>");

        return builder.toString();

    }

    public String generateText() {

        StringBuilder builder = new StringBuilder();

        reports.forEach((date, ships) -> {

            builder.append("- ").append(date).append("\n");

            ships.forEach((ship, report) -> {

                builder.append("\t - [").append(ship.getValues().get(DataFields.NAME)).append("](https:/robertsspaceindustries.com/").append(ship.getValues().get(DataFields.URL)).append(")\n");


                //--- NEW SHIP ADDED
                if (!(report.getTotalShipStatus() == null)) {
                    if (report.getTotalShipStatus() == DiffTypes.ADDED) {
                        builder.append("\t\t").append("This is a completely new ship!");
                    } else {
                        builder.append("\t\t").append("This ship got removed!");
                    }
                } else {

                    //--- Ship Fields
                    report.getShipFieldChanges().forEach(field -> {

                        builder.append("\t\t");
                        switch (field.getDiffType()) {

                            case ADDED:
                                builder.append("- Added ").append(field.getDataType().getName()).append(" ").append(field.getChanged());
                                break;
                            case MODIFIED:
                                builder.append("- Modified ").append(field.getDataType().getName()).append(" from ").append(field.getOriginal()).append(" to ").append(field.getChanged());
                                break;
                            case REMOVED:
                                builder.append("- Removed ").append(field.getDataType().getName());
                                break;
                        }

                        builder.append("\n");

                    });

                    //--- Components
                    report.getCompiledChanges().forEach((parent, componentType) -> {

                        builder.append("\t\t - ").append(parent).append("\n");

                        componentType.forEach((componentTypeName, list) -> {

                            builder.append("\t\t\t - ").append(componentTypeName).append("\n");

                            list.forEach((k, dataPointList) -> {

                                builder.append("\t\t\t\t - ").append(k).append("\n");
                                dataPointList.forEach(b -> {
                                    builder.append("\t\t\t\t\t");
                                    switch (b.getDiffType()) {
                                        case ADDED:

                                            switch (b.getDataType()) {

                                                case PARENT:
                                                    builder.append("- Added ").append(b.getParent()).append(" ").append(b.getChanged());
                                                    break;
                                                case COMPONENT_TYPE:
                                                    builder.append("- Added ").append(b.getComponentType()).append(" ").append(b.getChanged());
                                                    break;
                                                case COMPONENT:
                                                    builder.append("- Added ").append(b.getComponentName());
                                                    break;
                                            }

                                            break;
                                        case MODIFIED:

                                            switch (b.getDataType()) {

                                                case PARENT:
                                                    builder.append("- Modified ").append(b.getParent()).append(" from ").append(b.getOriginal()).append(" to ").append(b.getChanged());
                                                    break;
                                                case COMPONENT_TYPE:
                                                    builder.append("- Modified ").append(b.getComponentType()).append(" from ").append(b.getOriginal()).append(" to ").append(b.getChanged());
                                                    break;
                                                case COMPONENT:
                                                    builder.append("- Modified ").append(b.getField().getName()).append(" from ").append(b.getOriginal()).append(" to ").append(b.getChanged());
                                                    break;
                                            }

                                            break;

                                        case REMOVED:

                                            switch (b.getDataType()) {

                                                case PARENT:
                                                    builder.append("- Removed ").append(b.getParent());
                                                    break;
                                                case COMPONENT_TYPE:
                                                    builder.append("- Removed ").append(b.getComponentType());
                                                    break;
                                                case COMPONENT:
                                                    builder.append("- Removed ").append(b.getComponentName());
                                                    break;
                                            }

                                            break;
                                    }
                                    builder.append("\n");

                                });

                            });

                        });

                    });

                }

            });

        });

        return builder.toString();

    }

}
