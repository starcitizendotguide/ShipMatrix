package de.sweetode.ShipMatrix;

import de.sweetode.ShipMatrix.diff.Ship;
import de.sweetode.ShipMatrix.diff.report.DiffReport;
import de.sweetode.ShipMatrix.diff.types.DataField;
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
                        DataField _type = field.getDataType();
                        switch (field.getDiffType()) {

                            case ADDED:
                                builder.append(String.format("Added <i>%s</i> (<code>%s%s</code>)", _type.getName(), field.getChanged(), _type.getUnit()));
                                break;
                            case MODIFIED:
                                builder.append(String.format("Modified <i>%s</i> <code>%s%s</code> -> <code>%s%s</code>", _type.getName(), field.getOriginal(), _type.getUnit(), field.getChanged(), _type.getUnit()));
                                break;
                            case REMOVED:
                                builder.append(String.format("Removed <i>%s</i> (<code>%s%s</code>)", field.getDataType().getName(), field.getOriginal(), field.getDataType().getUnit()));
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
                                                case COMPONENT_DATA:
                                                    builder.append(String.format("Added <i>%s</i> (<code>%s</code>)", b.getComponentName(), b.getChanged()));
                                                    break;

                                                case COMPONENT:
                                                    builder.append(String.format("Added a new <i>component<i> (<code>%s</code>)", b.getComponentName()));
                                                    break;
                                                default: throw new IllegalStateException(b.getDataType().name());
                                            }
                                            break;

                                        case MODIFIED:
                                            switch (b.getDataType()) {
                                                case COMPONENT_DATA:
                                                    builder.append(String.format("Modified <i>%s</i> <code>%s%s</code> -> <code>%s%s</code>", b.getField().getName(), b.getOriginal(), b.getField().getUnit(), b.getChanged(), b.getField().getUnit()));
                                                    break;

                                                default: throw new IllegalStateException(b.getDataType().name());
                                            }

                                            break;

                                        case REMOVED:
                                            switch (b.getDataType()) {
                                                case COMPONENT_DATA:
                                                    builder.append(String.format("Removed <i>%s</i> (<code>%s%s</code>)", b.getComponentName(), b.getOriginal(), b.getField().getUnit()));
                                                    break;
                                                case COMPONENT:
                                                    builder.append(String.format("Removed component <i>%s</i>", b.getComponentType()));
                                                    break;

                                                default: throw new IllegalStateException(b.getDataType().name());
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

}
