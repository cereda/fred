/**
 * Fred.
 */
package com.github.cereda.fred.model;

import java.util.List;

/**
 * Options.
 *
 * @author Paulo Roberto Massa Cereda
 */
public class PrintingOptions {

    private List<PrintingModel> types;

    public List<PrintingModel> getTypes() {
        return types;
    }

    public void setTypes(List<PrintingModel> types) {
        this.types = types;
    }

    public PrintingModel getModel(String type) {
        for (PrintingModel pm : types) {
            if (pm.getType().toLowerCase().equals(type.toLowerCase())) {
                return pm;
            }
        }
        return null;
    }
}
